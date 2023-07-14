package createaccount;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import tool.Action;
import tool.PasswordUtil;

public class CreatePasswordAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("passwordCheck");
		String secretQuestion = request.getParameter("secretQuestion");
		String secretAnswer = request.getParameter("secretAnswer");
		String secretAnswerCheck = request.getParameter("secretAnswerCheck");

		// セッションの有効期限切れや直接パスワード入力ページにアクセスした場合はエラーとして処理
		if (session.getAttribute("account") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "エラーが発生しました。やり直してください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login-in.jsp");
			return null;
		}

		// パスワード及び秘密の質問への入力チェック
		// 未入力及び不一致はエラー処理		
		if (password == null || password.isEmpty()) {
			request.setAttribute("passwordError", "パスワードの入力は必須です");
		} else if (!password.equals(passwordCheck)) {
			request.setAttribute("passwordError", "パスワードが一致しません。再度入力してください。");
		}

		if (secretAnswer == null || secretAnswer.isEmpty()) {
			request.setAttribute("secretAnswerError", "秘密の質問の答えの入力は必須です");
		} else if (!secretAnswer.equals(secretAnswerCheck)) {
			request.setAttribute("secretAnswerError", "秘密の質問の答えが一致しません。再度入力してください。");
		}

		if (request.getAttribute("passwordError") != null || request.getAttribute("secretAnswerError") != null) {
			return "createpassword.jsp";
		}

		// もしパスワード形式が適切ならばアカウント作成成功画面に遷移
		if (Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$", password)) {

			System.out.println(password);
			System.out.println(session.getAttribute("account"));
			System.out.println(secretQuestion);
			System.out.println(secretAnswer);

			String account = (String) session.getAttribute("account");

			User user = new User();
			user = PasswordUtil.register(account, password, secretQuestion, secretAnswer);

			System.out.println(user.getAccount());
			System.out.println(user.getPassword());
			System.out.println(user.getSecretQuestion());
			System.out.println(user.getSecretAnswer());
			System.out.println(user.getEncryptedKey());
			System.out.println(user.getIv());
			;

			// Save the updated User object back into request scope
			request.setAttribute("accountName", session.getAttribute("account"));
			session.removeAttribute("account");
			return "createsuccess.jsp";

			// パスワードの入力形式が不適切ならエラー処理
		} else {
			request.setAttribute("passwordError", "パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください");
			return "createpassword.jsp";
		}

	}

}
