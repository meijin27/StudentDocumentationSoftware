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
import tool.RequestAndSessionUtil;
import tool.ValidationUtil;

public class CreateAccountAction extends Action {

	private static final Logger logger = CustomLogger.getLogger(CreateAccountAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークンの確認
		if (RequestAndSessionUtil.validateSession(request, response)) {
			// トークンが不正ならば処理を終了
			return null;
		}

		// 入力されたアカウント名を変数に格納
		String account = request.getParameter("account");

		// アカウント名が入力されている場合の処理
		if (ValidationUtil.isNullOrEmpty(account)) {
			// アカウント名未入力の場合は元のページに戻す
			request.setAttribute("nullError", "アカウント名の入力は必須です。");
			return "create-account.jsp";
		}

		// 文字数が32文字より多い場合はエラーを返す
		if (ValidationUtil.areValidLengths(32, account)) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
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
			request.setAttribute("innerError", "内部エラーが発生しました。");
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

}
