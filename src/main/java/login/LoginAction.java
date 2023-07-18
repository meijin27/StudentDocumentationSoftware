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
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String login = request.getParameter("login");
		String password = request.getParameter("password");

		if (login != null && !login.isEmpty() && password != null && !password.isEmpty()) {
			// ログイン名とパスワードが両方とも入力されているときの処理
			UserDAO dao = new UserDAO();
			User user = dao.search(login);
			int id = user.getId();

			if (user != null && PasswordUtil.isPasswordMatch(password, user.getPassword())) {

				dao.updateLastLogin(id);

				String encryptionKey = CipherUtil.decrypt(login + password, user.getIv(), user.getEncryptedKey());
				String iv = user.getIv();
				session.setAttribute("master_key", encryptionKey);
				session.setAttribute("iv", iv);

				return "login-succsess.jsp";
			} else {
				request.setAttribute("loginError", "ログイン名またはパスワードが違います");
				return "login-in.jsp";
			}
		}
		request.setAttribute("loginError", "ログイン名およびパスワードの入力は必須です");
		return "login-in.jsp";
	}
}
