package createaccount;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class CreateAccountAction extends Action {

	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		request.removeAttribute("accountError");

		String account = request.getParameter("account");

		if (account != null && !account.isEmpty()) {

			// もしアカウントがデータベースに登録されていなければパスワード入力画面に遷移
			if (true) {
				return "createpassword.jsp";
			} else {
				request.setAttribute("accountError", "すでに使用されているアカウント名です");
				return "createaccount.jsp";
			}
		}
		request.setAttribute("accountError", "アカウント名の入力は必須です");
		return "createaccount.jsp";
	}

}
