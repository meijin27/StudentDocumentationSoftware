package setting;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class FirstSettingAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String studentType = request.getParameter("studentType");
		String className = request.getParameter("className");
		String secretQuestion = request.getParameter("secretQuestion");
		String studentNumber = request.getParameter("studentNumber");
		String secretAnswer = request.getParameter("secretAnswer");
		String birthYear = request.getParameter("birthYear");
		String birthMonth = request.getParameter("birthMonth");
		String birthDay = request.getParameter("birthDay");

		// セッションの有効期限切れや直接初期設定入力ページにアクセスした場合はエラーとして処理
		if (session.getAttribute("master_key") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "エラーが発生しました。やり直してください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login-in.jsp");
			return null;
		}

		// 学籍番号が数字にできない場合はエラーを返す
		try {
			int number = Integer.parseInt(studentNumber);
		} catch (NumberFormatException e) {
			request.setAttribute("studentNumberError", "学籍番号は数字で入力してください。再度入力してください。");
		}

		if (request.getParameter("agree") == null) {
			request.setAttribute("agreeError", "「同意する」をチェックしない限り登録できません。");
		}

		if (request.getAttribute("studentNumberError") != null || request.getAttribute("agreeError") != null) {
			return "firstsetting.jsp";
		}

		// Save the updated User object back into request scope
		request.setAttribute("accountName", session.getAttribute("account"));
		session.removeAttribute("account");
		return "createsuccess.jsp";

	}

}
