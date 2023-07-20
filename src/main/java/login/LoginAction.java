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

		HttpSession session = request.getSession();
		// セッションタイムアウト設定 (1時間)
		session.setMaxInactiveInterval(3600);

		String account = request.getParameter("account");
		String password = request.getParameter("password");

		if (account != null && !account.isEmpty() && password != null && !password.isEmpty()) {
			// ログイン名とパスワードが両方とも入力されているときの処理
			UserDAO dao = new UserDAO();
			String encryptedAccount = CipherUtil.commonEncrypt(account);
			User user = dao.loginSearch(encryptedAccount);

			if (user != null && PasswordUtil.isPasswordMatch(password, user.getPassword())) {
				int id = user.getId();
				dao.addLoginHistory(id);
				System.out.println("暗号化されたマスターキー" + user.getEncryptedKey());
				String encryptionKey = CipherUtil.decrypt(account + password, user.getIv(), user.getEncryptedKey());
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
		request.setAttribute("loginError", "ログイン名およびパスワードの入力は必須です");
		return "login-in.jsp";
	}
}
