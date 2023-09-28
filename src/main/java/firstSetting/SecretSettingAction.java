package firstSetting;

import java.util.logging.Level;
import java.util.logging.Logger;

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

public class SecretSettingAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(SecretSettingAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションの作成
		HttpSession session = request.getSession();
		// セッションからトークンを取得
		String sessionToken = (String) session.getAttribute("csrfToken");
		// リクエストパラメータからトークンを取得
		String requestToken = request.getParameter("csrfToken");
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークンが一致しない、またはセッションの有効期限切れの場合はエラーとして処理
		if (session.getAttribute("master_key") == null || session.getAttribute("id") == null || sessionToken == null
				|| requestToken == null || !sessionToken.equals(requestToken)) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "セッションエラーが発生しました。ログインしてください。");
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// 入力された値の変数への格納
		String secretQuestion = request.getParameter("secretQuestion");
		String secretAnswer = request.getParameter("secretAnswer");

		// もしも入力値が無し、もしくは空の場合はエラーを返す
		if (secretQuestion != null && !secretQuestion.isEmpty() && secretAnswer != null && !secretAnswer.isEmpty()) {
			// 文字数が32文字より多い場合はエラーを返す
			if (secretAnswer.length() > 32 || secretQuestion.length() > 32) {
				request.setAttribute("secretError", "32文字以下で入力してください。");
				return "secret-setting.jsp";
			}
			try {
				// データベース操作用クラス
				UserDAO dao = new UserDAO();
				// 復号とIDやIV等の取り出しクラスの設定
				Decrypt decrypt = new Decrypt(dao);
				DecryptionResult result = decrypt.getDecryptedMasterKey(session);
				// IDの取り出し
				String id = result.getId();
				// アカウント名の取り出し			
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
				dao.addOperationLog(id, "Create SecretQuestion & Answer");
				// 秘密の質問未登録情報のセッションからの削除
				request.getSession().removeAttribute("secretSetting");
				// トークンの削除
				request.getSession().removeAttribute("csrfToken");
				// セッションに初期設定未登録情報を格納する
				session.setAttribute("firstSetting", "unregistered");
				// 初期設定画面へ遷移する				
				response.sendRedirect(contextPath + "/firstSetting/first-setting.jsp");
				return null;
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				request.setAttribute("secretError", "内部エラーが発生しました。");
				return "secret-setting.jsp";
			}
		}
		request.setAttribute("secretError", "秘密の質問の選択と答えの入力は必須です");
		return "secret-setting.jsp";

	}

}
