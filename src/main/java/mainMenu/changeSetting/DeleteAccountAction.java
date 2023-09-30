package mainMenu.changeSetting;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.CustomLogger;
import tool.PasswordUtil;
import tool.RequestAndSessionUtil;
import tool.ValidationUtil;

public class DeleteAccountAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(DeleteAccountAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークン及びログイン状態の確認
		if (!RequestAndSessionUtil.validateSession(request, response, "master_key", "id")) {
			// ログイン状態が不正ならば処理を終了
			return null;
		}

		// 入力されたパスワードを変数に格納
		String password = request.getParameter("password");

		// パスワードの入力チェック
		// 未入力及び不一致はエラー処理		
		if (ValidationUtil.isNullOrEmpty(password)) {
			request.setAttribute("nullError", "パスワードの入力は必須です");
		} else if (!ValidationUtil.areValidLengths(32, password)) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "delete-account.jsp";
		}

		try {
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
				// データベースからアカウントの削除（削除フラグを立てるだけ、データは削除しない）
				dao.accountDeleted(id);
				// IDとマスターキーのセッションを消去
				session.removeAttribute("id");
				session.removeAttribute("master_key");
				// アカウント削除メッセージをセッションに格納
				session.setAttribute("otherError", "アカウントが削除されました。");

				// トークンの削除
				request.getSession().removeAttribute("csrfToken");
				// アカウント削除後、ログインページにリダイレクト
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			} else {
				request.setAttribute("passwordError", "パスワードが一致しません。");
				return "delete-account.jsp";
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "delete-account.jsp";
		}
	}

}