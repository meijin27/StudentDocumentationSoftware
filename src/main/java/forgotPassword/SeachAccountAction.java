package forgotPassword;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.CustomLogger;

public class SeachAccountAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(SeachAccountAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 入力されたアカウント名を変数に格納
		String account = request.getParameter("account");

		// アカウント名が入力されている場合の処理		
		if (account != null && !account.isEmpty()) {
			// 文字数が32文字より多い場合はエラーを返す
			if (account.length() > 32) {
				request.setAttribute("accountError", "32文字以下で入力してください。");
				return "seach-account.jsp";
			}

			try {
				// データベース操作用クラス
				UserDAO dao = new UserDAO();
				// 入力されたアカウント名を共通暗号キーで暗号化
				String encryptedAccount = CipherUtil.commonEncrypt(account);
				// データベースに暗号化されたアカウント名が登録されているか確認するため
				// 登録されているアカウント名と一致した場合は一致したユーザーデータを格納する
				User user = dao.loginSearch(encryptedAccount);
				// ユーザーデータがnullでない　＝　登録済アカウントの場合の処理
				if (user != null) {
					// 秘密の質問を取り出す
					String reEncryptedSecretQuestion = user.getSecretQuestion();
					// 秘密の質問と生年の登録を確認し、登録済みならパスワード再設定にすすむ
					if (reEncryptedSecretQuestion != null && user.getStudentType() != null) {
						// ユーザーIDを変数に格納
						String id = user.getId();
						// データベースからivの取り出し
						String iv = dao.getIv(id);
						// IDを共通暗号キーで暗号化する
						String encryptedId = CipherUtil.commonEncrypt(String.valueOf(id));
						// リクエストにユーザー識別用のIDを持たせる				
						request.setAttribute("encryptedId", encryptedId);
						// 暗号化した秘密の質問を共通暗号キーで復号化する
						String encryptedSecretQuestion = CipherUtil.commonDecrypt(reEncryptedSecretQuestion);
						// 秘密の質問はアカウント名とIDをキーにして復号化
						String secretQuestion = CipherUtil.decrypt(account + id, iv, encryptedSecretQuestion);
						// リクエストに秘密の質問を持たせる				
						request.setAttribute("secretQuestion", secretQuestion);
						// 秘密の質問と答え確認画面に移動
						return "secret-check.jsp";
						// もしアカウントがデータベースに登録されていればエラーメッセージを表示			
					} else {
						request.setAttribute("accountError", "このアカウントはパスワードの再設定ができません");
						return "seach-account.jsp";
					}
				} else {
					request.setAttribute("accountError", "存在しないアカウントです");
					return "seach-account.jsp";
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				request.setAttribute("accountError", "内部エラーが発生しました。");
				return "seach-account.jsp";
			}
		}
		// アカウント名未入力の場合は元のページに戻す
		request.setAttribute("accountError", "アカウント名の入力は必須です");
		return "seach-account.jsp";
	}
}
