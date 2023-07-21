package login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.PasswordUtil;

public class LoginAction extends Action {
	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションの作成
		HttpSession session = request.getSession();
		// セッションタイムアウト設定 (1時間)
		session.setMaxInactiveInterval(3600);
		// 入力されたアカウント名とパスワードを変数に格納
		String account = request.getParameter("account");
		String password = request.getParameter("password");

		// ログイン名とパスワードが両方とも入力されているときの処理
		if (account != null && !account.isEmpty() && password != null && !password.isEmpty()) {
			// データベース操作用クラス
			UserDAO dao = new UserDAO();
			// 入力されたアカウント名をサーバー共通暗号で暗号化
			String encryptedAccount = CipherUtil.commonEncrypt(account);
			// 暗号化したアカウント名でデータベースを検索
			User user = dao.loginSearch(encryptedAccount);

			// データベースにアカウント名が存在し、パスワードが一致した場合の処理
			if (user != null && PasswordUtil.isPasswordMatch(password, user.getPassword())) {
				// ユーザーIDを変数に格納
				int id = user.getId();
				// ivを変数に格納
				String iv = user.getIv();
				// クライアントのIPアドレスを取得
				String ipAddress = request.getRemoteAddr();
				// データベースにログイン記録を追加
				dao.addLoginLog(id, ipAddress);
				// 暗号化されたマスターキーを復号する
				String masterKey = CipherUtil.decrypt(account + password, iv, user.getMasterKey());
				// 復号したマスターキーをアカウントとIDを暗号キーにして暗号化する（なるべく文字列をランダム化するためにアカウントが先でIDが後）
				String encryptedKey = CipherUtil.encrypt(account + id, iv, masterKey);
				// 暗号化したマスターキーをさらに共通暗号キーで暗号化する
				String reEncryptedKey = CipherUtil.commonEncrypt(encryptedKey);
				// セッションにユーザー識別用のIDを持たせる				
				session.setAttribute("id", id);
				// セッションに再暗号化したマスターキーを持たせる
				session.setAttribute("master_key", reEncryptedKey);

				// ユーザーのデータベースに秘密の質問が登録されていなければ秘密の質問と答え登録ページに移動
				if (user.getSecretQuestion() == null) {
					String contextPath = request.getContextPath();
					response.sendRedirect(contextPath + "/firstSetting/secret-setting.jsp");
					return null;
					// ユーザーのデータベースに名前等が登録されていなければ初期登録ページに移動
				} else if (user.getStudentType() == null) {
					String contextPath = request.getContextPath();
					response.sendRedirect(contextPath + "/firstSetting/first-setting.jsp");
					return null;
					// メインメニューに移動
				} else {
					return "login-succsess.jsp";
				}
				// 入力されたアカウント名またはパスワードが違う場合の処理
			} else {
				request.setAttribute("loginError", "アカウント名またはパスワードが違います");
				return "login-in.jsp";
			}
		}
		// アカウント名もしくはパスワードが入力されていなければログイン画面に戻す
		request.setAttribute("loginError", "アカウント名およびパスワードの入力は必須です");
		return "login-in.jsp";
	}
}
