package logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class Logout extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// セッションを取得し、無効化
		HttpSession session = request.getSession();
		session.invalidate();

		// ログインページにリダイレクト
		String contextPath = request.getContextPath();
		response.sendRedirect(contextPath + "/login/login.jsp");
	}
}
