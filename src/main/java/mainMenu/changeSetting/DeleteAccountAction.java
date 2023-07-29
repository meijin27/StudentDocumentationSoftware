package mainMenu.changeSetting;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.PasswordUtil;

public class DeleteAccountAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションの作成
		HttpSession session = request.getSession();

		// セッションの有効期限切れの場合はエラーとして処理
		if (session.getAttribute("id") == null || session.getAttribute("master_key") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "セッションエラーが発生しました。ログインしてください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// 入力されたパスワードを変数に格納
		String password = request.getParameter("password");

		// パスワードの入力チェック
		// 未入力及び不一致はエラー処理		
		if (password == null || password.isEmpty()) {
			request.setAttribute("passwordError", "パスワードの入力は必須です");
		} else if (password.length() > 32) {
			request.setAttribute("passwordError", "32文字以下で入力してください。");
		}

		// パスワードエラーが発生していたら元のページに戻りやり直し
		if (request.getAttribute("passwordError") != null) {
			return "delete-account.jsp";
		}

		// データベース操作用クラス
		UserDAO dao = new UserDAO();
		// セッションから暗号化されたIDの取り出し
		String encryptedId = (String) session.getAttribute("id");
		// IDの復号
		String id = CipherUtil.commonDecrypt(encryptedId);
		// IDでデータベースを検索
		String registerPassword = dao.getPassword(id);
		// 現在のパスワードの一致確認
		if (PasswordUtil.isPasswordMatch(password, registerPassword)) {
			// 削除するID情報をUserクラスを作成し格納する
			User user = new User();
			user.setId(id);
			// アップデート内容のデータベースへの登録
			dao.addOperationLog(id, "Delete Account");
			// データベースからアカウントの削除
			dao.accountDeleted(id);
			// セッションを消去
			session.removeAttribute("id");
			session.removeAttribute("master_key");
			// アカウント削除後、ログインページにリダイレクト
			session.setAttribute("otherError", "アカウントが削除されました。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		} else {
			request.setAttribute("passwordError", "パスワードが一致しません。");
			return "delete-account.jsp";
		}
	}

}