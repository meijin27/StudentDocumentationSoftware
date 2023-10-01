package createAccount;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

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

public class CreatePasswordAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(CreatePasswordAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションの作成
		HttpSession session = request.getSession();
		// セッションから暗号化されたアカウント名を取り出す
		String encryptedAccount = (String) session.getAttribute("encryptedAccount");
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークン及び暗号化されたアカウント名の確認
		if (RequestAndSessionUtil.validateSession(request, response, "encryptedAccount")) {
			// ログイン状態が不正ならば処理を終了
			return null;
		}

		// 入力されたパスワードと再確認用パスワードを変数に格納
		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("passwordCheck");

		// ユーザー情報を格納するクラスの作成
		User user = new User();

		// パスワードの入力チェック
		// 未入力及び不一致はエラー処理		
		if (ValidationUtil.isNullOrEmpty(password, passwordCheck)) {
			request.setAttribute("nullError", "パスワードの入力は必須です");
		} else if (!password.equals(passwordCheck)) {
			request.setAttribute("passwordError", "パスワードが一致しません。再度入力してください。");
		} else if (ValidationUtil.areValidLengths(32, password)) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "create-password.jsp";
		}

		// パスワードが英大文字・小文字・数字をすべて含み８文字以上の場合の処理
		if (Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$", password)) {
			try {
				// 暗号化されたアカウント名を復号する
				String account = CipherUtil.commonDecrypt(encryptedAccount);
				// PasswordUtilクラスにてユーザー情報を自動入力する
				//（暗号化されたアカウント、ハッシュ化されたパスワード、暗号化されたマスターキー、iv）
				user = PasswordUtil.register(account, password);
				// データベース操作用クラス
				UserDAO dao = new UserDAO();
				// データベースにアカウント登録する
				dao.accountInsert(user);
				// 暗号化されたアカウント名のセッションからの削除
				request.getSession().removeAttribute("encryptedAccount");
				// トークンの削除
				request.getSession().removeAttribute("csrfToken");
				// セッションにアカウント名を格納する
				session.setAttribute("accountName", account);
				// アカウント作成成功画面にリダイレクト
				response.sendRedirect(contextPath + "/createAccount/create-success.jsp");
				return null;
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				request.setAttribute("passwordError", "内部エラーが発生しました。");
				return "create-password.jsp";
			}
			// パスワードの入力形式が不適切ならエラー処理
		} else {

			request.setAttribute("passwordError", "パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください");
			return "create-password.jsp";
		}

	}

}