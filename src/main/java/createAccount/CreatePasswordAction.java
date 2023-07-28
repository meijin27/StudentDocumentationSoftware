package createAccount;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.PasswordUtil;

public class CreatePasswordAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションの作成
		HttpSession session = request.getSession();
		// 入力されたパスワードと再確認用パスワードを変数に格納
		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("passwordCheck");

		// セッションの有効期限切れの場合はエラーとして処理
		if (session.getAttribute("encryptedAccount") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "エラーが発生しました。やり直してください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

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
			// セッションから暗号化されたアカウント名を取り出す
			String encryptedAccount = (String) session.getAttribute("encryptedAccount");
			// 暗号化されたアカウント名を復号する
			String account = CipherUtil.commonDecrypt(encryptedAccount);
			// ユーザー情報を格納するクラスの作成
			User user = new User();
			// PasswordUtilクラスにてユーザー情報を自動入力する
			//（暗号化されたアカウント、ハッシュ化されたパスワード、暗号化されたマスターキー、iv）
			user = PasswordUtil.register(account, password);
			// データベース操作用クラス
			UserDAO dao = new UserDAO();
			// データベースにアカウント登録する
			dao.accountInsert(user);
			// リクエストにアカウント名を格納する
			request.setAttribute("accountName", account);
			// セッションの全削除
			session.invalidate();
			// アカウント作成成功画面に遷移
			return "create-success.jsp";

			// パスワードの入力形式が不適切ならエラー処理
		} else {
			request.setAttribute("passwordError", "パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください");
			return "create-password.jsp";
		}

	}

}