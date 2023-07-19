package api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/api/getInitialSelectionValues")
public class InitialSelectionValuesServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String studentType = (String) request.getSession().getAttribute("studentType");
		String secretQuestion = (String) request.getSession().getAttribute("secretQuestion");
		String birthYear = (String) request.getSession().getAttribute("birthYear");
		String birthMonth = (String) request.getSession().getAttribute("birthMonth");
		String birthDay = (String) request.getSession().getAttribute("birthDay");

		JSONObject json = new JSONObject();
		json.put("studentType", studentType);
		json.put("secretQuestion", secretQuestion);
		json.put("birthYear", birthYear);
		json.put("birthMonth", birthMonth);
		json.put("birthDay", birthDay);

		response.setContentType("application/json");
		response.getWriter().write(json.toString());
	}
}