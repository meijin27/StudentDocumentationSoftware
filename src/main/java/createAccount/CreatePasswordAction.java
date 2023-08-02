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

public class CreatePasswordAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(CreatePasswordAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションの作成
		HttpSession session = request.getSession();

		// 入力されたパスワードと再確認用パスワードを変数に格納
		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("passwordCheck");

		// リクエストの有効期限切れの場合はエラーとして処理
		if (request.getParameter("encryptedAccount") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "セッションエラーが発生しました。最初からやり直してください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// ユーザー情報を格納するクラスの作成
		User user = new User();
		// リクエストから暗号化されたアカウント名を取り出す
		String encryptedAccount = request.getParameter("encryptedAccount");
		// リクエストに共通暗号キーで暗号化されたアカウント名を格納
		request.setAttribute("encryptedAccount", encryptedAccount);

		// パスワードの入力チェック
		// 未入力及び不一致はエラー処理		
		if (password == null || password.isEmpty()) {
			request.setAttribute("passwordError", "パスワードの入力は必須です");
		} else if (!password.equals(passwordCheck)) {
			request.setAttribute("passwordError", "パスワードが一致しません。再度入力してください。");
		} else if (password.length() > 32) {
			request.setAttribute("passwordError", "32文字以下で入力してください。");
		}

		// パスワードエラーが発生していたら元のページに戻りやり直し
		if (request.getAttribute("passwordError") != null) {
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
				// リクエストにアカウント名を格納する
				request.setAttribute("accountName", account);
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				request.setAttribute("passwordError", "内部エラーが発生しました。");
				return "create-password.jsp";
			}
			// アカウント作成成功画面に遷移
			return "create-success.jsp";

			// パスワードの入力形式が不適切ならエラー処理
		} else {

			request.setAttribute("passwordError", "パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください");
			return "create-password.jsp";
		}

	}

}