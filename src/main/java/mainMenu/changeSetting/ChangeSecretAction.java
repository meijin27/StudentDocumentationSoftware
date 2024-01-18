package mainMenu.changeSetting;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.CustomLogger;
import tool.Decrypt;
import tool.DecryptionResult;
import tool.PasswordUtil;
import tool.RequestAndSessionUtil;
import tool.ValidationUtil;

public class ChangeSecretAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(ChangeSecretAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークン及びログイン状態の確認
		if (RequestAndSessionUtil.validateSession(request, response, "master_key", "id")) {
			// ログイン状態が不正ならば処理を終了
			return null;
		}

		// 入力された現在のパスワードと秘密の質問と答えを変数に格納
		String password = request.getParameter("password");
		String secretQuestion = request.getParameter("secretQuestion");
		String secretAnswer = request.getParameter("secretAnswer");

		// 旧パスワードの入力チェック
		// 未入力及び不一致はエラー処理		
		if (ValidationUtil.isNullOrEmpty(password)) {
			request.setAttribute("passwordError", "現在のパスワードの入力は必須です");
		} else if (ValidationUtil.areValidLengths(32, password)) {
			request.setAttribute("passwordError", "パスワードが一致しません。再度入力してください。");
		} else if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$", password)) {
			request.setAttribute("passwordError", "パスワードが一致しません。再度入力してください。");
		}

		// 秘密の質問のエラー処理
		// もしも入力値が無し、もしくは空の場合はエラーを返す
		if (ValidationUtil.isNullOrEmpty(secretQuestion)) {
			request.setAttribute("secretQuestionError", "秘密の質問の選択は必須です");
		}
		// 文字数が32文字より多い場合はエラーを返す
		else if (ValidationUtil.areValidLengths(32, secretQuestion)) {
			request.setAttribute("secretQuestionError", "秘密の質問は選択肢から選択してください。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(secretQuestion)) {
			request.setAttribute("secretQuestionError", "使用できない特殊文字が含まれています");
		}

		// 秘密の質問の答えのエラー処理
		// もしも入力値が無し、もしくは空の場合はエラーを返す
		if (ValidationUtil.isNullOrEmpty(secretAnswer)) {
			request.setAttribute("secretAnswerError", "秘密の質問の答えの入力は必須です");
		}
		// 文字数が32文字より多い場合はエラーを返す
		else if (ValidationUtil.areValidLengths(32, secretAnswer)) {
			request.setAttribute("secretAnswerError", "32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "change-secret.jsp";
		}

		try {
			// データベース操作用クラス
			UserDAO dao = new UserDAO();
			// セッションから暗号化されたIDの取り出し
			String encryptedId = (String) session.getAttribute("id");
			// IDの復号
			String id = CipherUtil.commonDecrypt(encryptedId);
			// IDでデータベースを検索
			String registerPassword = dao.getPassword(id);
			// 現在のパスワードの一致確認
			if (PasswordUtil.isPasswordMatch(password, registerPassword)) {
				// 復号とIDやIV等の取り出しクラスの設定
				Decrypt decrypt = new Decrypt(dao);
				DecryptionResult result = decrypt.getDecryptedMasterKey(session);
				// アカウントの取り出し
				String account = result.getAccount();
				// マスターキーの取り出し			
				String masterKey = result.getMasterKey();
				// ivの取り出し
				String iv = result.getIv();
				// 秘密の質問はアカウント名とIDをキーにして暗号化（なるべく文字列をランダム化するためにアカウントが先でIDが後）
				String encryptedSecretQuestion = CipherUtil.encrypt(account + id, iv, secretQuestion);
				// 暗号化した秘密の質問をさらに共通暗号キーで暗号化する
				String reEncryptedSecretQuestion = CipherUtil.commonEncrypt(encryptedSecretQuestion);
				// 秘密の質問の答えはパスワードと同じくハッシュ化
				String hashedSecretAnswer = PasswordUtil.getHashedPassword(secretAnswer);
				// マスターキーを秘密の答えと質問で暗号化（なるべく文字列をランダム化するために答えが先で質問が後）
				String encryptedKey = CipherUtil.encrypt(secretAnswer + secretQuestion, iv, masterKey);
				// 暗号化したマスターキーをさらに共通暗号キーで暗号化する
				String reEncryptedKey = CipherUtil.commonEncrypt(encryptedKey);
				// 秘密の質問関連のユーザー情報の作成
				User user = new User();
				user.setId(id);
				user.setSecretQuestion(reEncryptedSecretQuestion);
				user.setSecretAnswer(hashedSecretAnswer);
				user.setSecondMasterKey(reEncryptedKey);
				// 秘密の質問と答えとパスワード忘れたとき用のマスターキーのデータベースへの登録
				dao.updateSecret(user);
				// アップデート内容のデータベースへの登録
				dao.addOperationLog(id, "Change SecretQuestion & Answer");

				// セッションに変更情報を持たせる				
				session.setAttribute("action", "秘密の質問と答えを変更しました。");
				// トークンの削除
				request.getSession().removeAttribute("csrfToken");

				// エラーがない場合は行為成功表示用JSPへリダイレクト
				response.sendRedirect(contextPath + "/mainMenu/action-success.jsp");
				return null;
			} else {
				request.setAttribute("passwordError", "パスワードが一致しません。再度入力してください。");
				return "change-secret.jsp";
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "change-secret.jsp";
		}
	}
}