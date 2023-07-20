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
				// データベースにログイン記録を追加
				dao.addLoginLog(id);

				System.out.println("暗号化されたマスターキー" + user.getMasterKey());
				String encryptionKey = CipherUtil.decrypt(account + password, user.getIv(), user.getMasterKey());
				System.out.println("復号化されたマスターキー" + encryptionKey);
				String encryptedKey = CipherUtil.commonEncrypt(encryptionKey);
				System.out.println("共通暗号化されたマスターキー" + encryptedKey);
				session.setAttribute("id", id);
				session.setAttribute("master_key", encryptedKey);

				if (user.getSecretQuestion() == null) {
					String contextPath = request.getContextPath();
					response.sendRedirect(contextPath + "/firstsetting/secret-setting.jsp");
					return null;
				} else if (user.getStudentType() == null) {
					String contextPath = request.getContextPath();
					response.sendRedirect(contextPath + "/firstsetting/first-setting.jsp");
					return null;

				} else {
					return "login-succsess.jsp";
				}
			} else {
				request.setAttribute("loginError", "ログイン名またはパスワードが違います");
				return "login-in.jsp";
			}
		}
		// アカウントもしくはパスワードが入力されていなければログイン画面に戻す
		request.setAttribute("loginError", "ログイン名およびパスワードの入力は必須です");
		return "login-in.jsp";
	}
}
