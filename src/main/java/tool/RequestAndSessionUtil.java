package tool;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestAndSessionUtil {

	// 入力されたリクエストを全てセッションに格納するメソッド
	public static void storeParametersInSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			session.setAttribute(paramName, paramValue);
		}
	}

	// 入力されたリクエストを全てリクエストに再格納するメソッド	
	public static void storeParametersInRequest(HttpServletRequest request) {
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			request.setAttribute(paramName, paramValue);
		}
	}

	// トークン及びセッションの確認メソッド
	public static boolean validateSession(HttpServletRequest request, HttpServletResponse response,
			String... attributeNames) throws IOException {
		HttpSession session = request.getSession();
		String sessionToken = (String) session.getAttribute("csrfToken");
		String requestToken = request.getParameter("csrfToken");
		String contextPath = request.getContextPath();

		// トークンがnull、またはトークンが一致しない場合はエラーとして処理
		if (sessionToken == null || requestToken == null || !sessionToken.equals(requestToken)) {
			session.setAttribute("otherError", "セッションエラーが発生しました。");
			// ログインページにリダイレクト
			response.sendRedirect(contextPath + "/login/login.jsp");
			return false;
		}

		// その他の指定されたセッション属性がnullの場合はエラーとして処理
		for (String attributeName : attributeNames) {
			if (session.getAttribute(attributeName) == null) {
				session.setAttribute("otherError", "セッションエラーが発生しました。");
				// ログインページにリダイレクト
				response.sendRedirect(contextPath + "/login/login.jsp");
				return false;
			}
		}
		return true;
	}
}
