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

	// トークン及びログイン状態の確認メソッド
	public static boolean validateToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String sessionToken = (String) session.getAttribute("csrfToken");
		String requestToken = request.getParameter("csrfToken");
		String contextPath = request.getContextPath();
		// トークンが一致しない、またはセッションにIDとマスターキーが格納されていない場合はエラーとして処理
		if (session.getAttribute("master_key") == null || session.getAttribute("id") == null || sessionToken == null
				|| requestToken == null || !sessionToken.equals(requestToken)) {
			session.setAttribute("otherError", "セッションエラーが発生しました。ログインしてください。");
			// ログインページにリダイレクト
			response.sendRedirect(contextPath + "/login/login.jsp");
			return false;
		}
		return true;
	}
}
