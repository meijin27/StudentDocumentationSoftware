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
		if (!RequestAndSessionUtil.validateSession(request, response, "master_key", "id")) {
			// ログイン状態が不正ならば処理を終了
			return null;
		}

		// 入力された現在のパスワードと秘密の質問と答えを変数に格納
		String password = request.getParameter("password");
		String secretQuestion = request.getParameter("secretQuestion");
		String secretAnswer = request.getParameter("secretAnswer");

		// 未入力はエラー処理		
		if (ValidationUtil.isNullOrEmpty(password, secretQuestion, secretAnswer)) {
			request.setAttribute("nullError", "未選択・未入力項目があります。");
			return "change-secret.jsp";
		}

		// 文字数が多い場合はエラーを返す。
		if (!ValidationUtil.areValidLengths(32, password, secretAnswer, secretQuestion)) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// 入力値に特殊文字が入っていないか確認する
		if (ValidationUtil.containsForbiddenChars(secretQuestion)) {
			request.setAttribute("secretError", "使用できない特殊文字が含まれています");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "change-secret.jsp";
		}

		// パスワードが英大文字・小文字・数字をすべて含み８文字以上の場合の処理
		if (Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$", password)) {
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
					request.setAttribute("secretError", "パスワードが一致しません。");
					return "change-secret.jsp";
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				request.setAttribute("secretError", "内部エラーが発生しました。");
				return "change-secret.jsp";
			}
			// パスワードの入力形式が不適切ならエラー処理
		} else {
			request.setAttribute("secretError", "パスワードが一致しません。");
			return "change-secret.jsp";
		}

	}

}