package createaccount;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class CreatePasswordAction extends Action {

	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		request.removeAttribute("passwordError");

		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		if (!password1.equals(password2)) {
			request.setAttribute("passwordError", "パスワードが一致しません。再度入力してください。");
			return "createpassword.jsp";
		}

		if (password1 != null && !password1.isEmpty()) {

			// もしパスワード形式が適切ならばアカウント作成成功画面に遷移

			if (Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$", password1)) {
				return "create.jsp";
			} else {
				request.setAttribute("passwordError", "パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください");
				return "createpassword.jsp";
			}
		}
		request.setAttribute("passwordError", "パスワードの入力は必須です");
		return "createpassword.jsp";
	}

}
