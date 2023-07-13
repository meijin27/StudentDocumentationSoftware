package login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class LogoutAction extends Action {
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		//		HttpSession session = request.getSession();
		//
		//		if (session.getAttribute("customer") != null) {
		//			session.removeAttribute("customer");
		//			request.setAttribute("loginError", "");
		//			return "logout-out.jsp";
		//		}
		request.removeAttribute("loginError");
		return "logout-error.jsp";
	}
}
