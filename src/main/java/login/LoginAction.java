package login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LoginAction extends Action {
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		request.removeAttribute("loginError");

		String login = request.getParameter("login");
		String password = request.getParameter("password");

		if (login != null && !login.isEmpty() && password != null && !password.isEmpty()) {
			// ログイン名とパスワードが両方とも入力されているときの処理
			//			CustomerDAO dao = new CustomerDAO();
			//			Customer customer = dao.search(login, password);

			//			if (customer != null) {
			//				session.setAttribute("customer", customer);
			if (false) {
				return "login-succsess.jsp";
			} else {
				request.setAttribute("loginError", "ログイン名またはパスワードが違います");
				return "login-in.jsp";
			}
		}
		request.setAttribute("loginError", "ログイン名またはパスワードの入力は必須です");
		return "login-in.jsp";
	}
}
