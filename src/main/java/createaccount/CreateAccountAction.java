package createaccount;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;

public class CreateAccountAction extends Action {
	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String account = request.getParameter("account");

		if (account != null && !account.isEmpty()) {
			UserDAO dao = new UserDAO();
			String encryptedAccount = CipherUtil.commonEncrypt(account);
			User user = dao.search(encryptedAccount);

			// もしアカウントがデータベースに登録されていなければエラーメッセージを表示
			if (user == null) {
				// Save the User object in request scope
				session.setAttribute("account", account);

				return "createpassword.jsp";
			} else {
				request.setAttribute("accountError", "このアカウントは使用できません。");
				return "createaccount.jsp";
			}
		}
		request.setAttribute("accountError", "アカウント名の入力は必須です");
		return "createaccount.jsp";
	}
}
