package forgotPassword;

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
import tool.ErrorCheckUtil;
import tool.PasswordUtil;
import tool.RequestAndSessionUtil;
import tool.ValidationUtil;

public class RecreatePasswordAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(RecreatePasswordAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークン及びログイン状態の確認
		if (!RequestAndSessionUtil.validateSession(request, response, "master_key", "encryptedId")) {
			// ログイン状態が不正ならば処理を終了
			return null;
		}

		// セッションから暗号化されたマスターキーを取り出す
		String reEncryptedMasterkey = (String) session.getAttribute("master_key");
		// セッションから暗号化されたIDを取り出す
		String encryptedId = (String) session.getAttribute("encryptedId");

		// 入力されたパスワードと再確認用パスワードを変数に格納
		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("passwordCheck");

		// IDの復号
		String id = CipherUtil.commonDecrypt(encryptedId);

		// パスワードの入力チェック
		// 未入力及び不一致はエラー処理		
		if (ValidationUtil.isNullOrEmpty(password)) {
			request.setAttribute("nullError", "パスワードの入力は必須です");
		} else if (!password.equals(passwordCheck)) {
			request.setAttribute("passwordError", "パスワードが一致しません。再度入力してください。");
		} else if (!ValidationUtil.areValidLengths(32, password)) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (ErrorCheckUtil.hasErrorAttributes(request)) {
			return "recreate-password.jsp";
		}

		// パスワードが英大文字・小文字・数字をすべて含み８文字以上の場合の処理
		if (Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$", password)) {
			try {
				// データベース操作用クラス
				UserDAO dao = new UserDAO();
				// データベースから暗号化されたアカウント名の取り出し
				String encryptedAccount = dao.getAccount(id);
				// 暗号化されたアカウント名の復号
				String account = CipherUtil.commonDecrypt(encryptedAccount);
				// データベースからivの取り出し
				String iv = dao.getIv(id);
				// リクエストから取り出した共通暗号キーで再暗号化したマスターキーの復号	
				String encryptedMasterkey = CipherUtil.commonDecrypt(reEncryptedMasterkey);
				// リクエストから取り出した暗号化したマスターキーの復号	
				String masterKey = CipherUtil.decrypt(account + id, iv, encryptedMasterkey);
				// マスターキーの新暗号化
				String encryptedKey = CipherUtil.encrypt(password + account, iv, masterKey);
				// 暗号化したマスターキーをさらに共通暗号キーで暗号化する
				String reEncryptedKey = CipherUtil.commonEncrypt(encryptedKey);
				// PasswordUtilクラスにてパスワードをハッシュ化する
				String newPassword = PasswordUtil.getHashedPassword(password);

				// 更新する情報をUserクラスを作成し格納する
				User user = new User();
				user.setId(id);
				user.setPassword(newPassword);
				user.setMasterKey(reEncryptedKey);
				// データベースのパスワードとマスターキーを更新する
				dao.updatePassword(user);
				dao.updateMasterKey(user);
				// アップデート内容のデータベースへの登録
				dao.addOperationLog(id, "Forgot Password Recreate");

				// セッションからマスターキーを削除
				request.getSession().removeAttribute("master_key");
				// セッションから暗号化されたIDを削除
				request.getSession().removeAttribute("encryptedId");
				// トークンの削除
				request.getSession().removeAttribute("csrfToken");
				// セッションにパスワード再作成確認用変数を格納する
				session.setAttribute("recreateSuccess", "Recreate_Success");
				// パスワード再作成成功画面にリダイレクト
				response.sendRedirect(contextPath + "/forgotPassword/recreate-success.jsp");
				return null;
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				request.setAttribute("passwordError", "内部エラーが発生しました。");
				return "recreate-password.jsp";
			}
			// パスワードの入力形式が不適切ならエラー処理
		} else {
			request.setAttribute("passwordError", "パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください");
			return "recreate-password.jsp";
		}

	}

}