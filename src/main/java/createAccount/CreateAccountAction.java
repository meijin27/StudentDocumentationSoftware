package createAccount;

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

public class CreateAccountAction extends Action {

	private static final Logger logger = CustomLogger.getLogger(CreateAccountAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// セッションからトークンを取得
		String sessionToken = (String) session.getAttribute("csrfToken");
		// リクエストパラメータからトークンを取得
		String requestToken = request.getParameter("csrfToken");
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークンが一致しない、またはどちらかがnullの場合はエラー
		if (sessionToken == null || requestToken == null || !sessionToken.equals(requestToken)) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "セッションエラーが発生しました。最初からやり直してください。");
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// 入力されたアカウント名を変数に格納
		String account = request.getParameter("account");

		// アカウント名が入力されている場合の処理
		if (account != null && !account.isEmpty()) {
			// 文字数が32文字より多い場合はエラーを返す
			if (account.length() > 32) {
				request.setAttribute("accountError", "32文字以下で入力してください。");
				return "create-account.jsp";
			}

			// データベース操作用クラス
			UserDAO dao = new UserDAO();
			// ユーザークラスの作成
			User user = null;
			// 暗号化されたアカウント名
			String encryptedAccount = null;
			try {
				// 入力されたアカウント名を共通暗号キーで暗号化
				encryptedAccount = CipherUtil.commonEncrypt(account);
				// データベースに暗号化されたアカウント名が登録されているか確認するため
				// 登録されているアカウント名と一致した場合は一致したユーザーデータを格納する
				user = dao.createSearch(encryptedAccount);
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				request.setAttribute("accountError", "内部エラーが発生しました。");
				return "create-account.jsp";
			}
			// エラーがあれば元のページに戻る
			if (request.getAttribute("accountError") != null) {
				return "create-account.jsp";
			}

			// ユーザーデータがnullである　＝　未登録アカウントの場合の処理
			if (user == null) {
				// 現在のセッションを無効化
				session.invalidate();
				// 新しいセッションを作成
				session = request.getSession(true);
				// セッションに共通暗号キーで暗号化されたアカウント名を格納
				session.setAttribute("encryptedAccount", encryptedAccount);
				// トークンの削除
				request.getSession().removeAttribute("csrfToken");
				// パスワード登録画面にリダイレクト
				response.sendRedirect(contextPath + "/createAccount/create-password.jsp");
				return null;
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
