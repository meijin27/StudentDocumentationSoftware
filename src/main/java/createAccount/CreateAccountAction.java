package createAccount;

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
		// セッションの作成
		HttpSession session = request.getSession();
		// 入力されたアカウント名を変数に格納
		String account = request.getParameter("account");

		// アカウント名が入力されている場合の処理
		if (account != null && !account.isEmpty()) {
			// 文字数が32文字より多い場合はエラーを返す
			if (account.length() > 32) {
				System.out.println(account.length());
				request.setAttribute("accountError", "32文字以下で入力してください。");
				return "create-account.jsp";
			}
			// データベース操作用クラス
			UserDAO dao = new UserDAO();
			// 入力されたアカウント名を共通暗号キーで暗号化
			String encryptedAccount = CipherUtil.commonEncrypt(account);
			// データベースに暗号化されたアカウント名が登録されているか確認するため
			// 登録されているアカウント名と一致した場合は一致したユーザーデータを格納する
			User user = dao.createSearch(encryptedAccount);
			// ユーザーデータがnullである　＝　未登録アカウントの場合の処理
			if (user == null) {
				// セッションに共通暗号キーで暗号化されたアカウント名を格納
				session.setAttribute("encryptedAccount", encryptedAccount);
				// パスワード登録画面に移動
				return "create-password.jsp";
				// もしアカウントがデータベースに登録されていればエラーメッセージを表示			
			} else {
				request.setAttribute("accountError", "このアカウントは使用できません。");
				return "create-account.jsp";
			}
		}
		// アカウント名未入力の場合は元のページに戻す
		request.setAttribute("accountError", "アカウント名の入力は必須です。");
		return "create-account.jsp";
	}
}
