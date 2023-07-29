package mainMenu.changeSetting;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.Decrypt;
import tool.DecryptionResult;
import tool.PasswordUtil;

public class ChangePasswordAction extends Action {

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

		// 入力された現在のパスワードと新パスワードと再確認用パスワードを変数に格納
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		String passwordCheck = request.getParameter("passwordCheck");

		// パスワードの入力チェック
		// 未入力及び不一致はエラー処理		
		if (password == null || password.isEmpty() || newPassword == null || newPassword.isEmpty()) {
			request.setAttribute("passwordError", "パスワードの入力は必須です");
		} else if (!newPassword.equals(passwordCheck)) {
			request.setAttribute("passwordError", "新パスワードと確認用パスワードが一致しません。再度入力してください。");
		} else if (password.length() > 32 || newPassword.length() > 32) {
			request.setAttribute("passwordError", "32文字以下で入力してください。");
		}

		// パスワードエラーが発生していたら元のページに戻りやり直し
		if (request.getAttribute("passwordError") != null) {
			return "change-password.jsp";
		}

		// パスワードが英大文字・小文字・数字をすべて含み８文字以上の場合の処理
		if (Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$", newPassword)) {
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
				// 復号とIDやIV等の取り出しクラスの設定
				Decrypt decrypt = new Decrypt(dao);
				DecryptionResult result = decrypt.getDecryptedMasterKey(session);
				// アカウントの取り出し
				String account = result.getAccount();
				// マスターキーの取り出し			
				String masterKey = result.getMasterKey();
				// ivの取り出し
				String iv = result.getIv();
				// マスターキーの新暗号化
				String encryptedKey = CipherUtil.encrypt(newPassword + account, iv, masterKey);
				// 暗号化したマスターキーをさらに共通暗号キーで暗号化する
				String reEncryptedKey = CipherUtil.commonEncrypt(encryptedKey);
				// PasswordUtilクラスにてパスワードをハッシュ化する
				String changePassword = PasswordUtil.getHashedPassword(newPassword);
				// 更新する情報をUserクラスを作成し格納する
				User user = new User();
				user.setId(id);
				user.setPassword(changePassword);
				user.setMasterKey(reEncryptedKey);
				// データベースのパスワードとマスターキーを更新する
				dao.updatePassword(user);
				dao.updateMasterKey(user);
				// アップデート内容のデータベースへの登録
				dao.addOperationLog(id, "Change Password");
				// パスワード変更成功画面に遷移
				request.setAttribute("changes", "パスワードを変更しました。");
				return "change-success.jsp";
			} else {
				request.setAttribute("passwordError", "パスワードが一致しません。");
				return "change-password.jsp";
			}
			// パスワードの入力形式が不適切ならエラー処理
		} else {
			request.setAttribute("passwordError", "パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください。");
			return "change-password.jsp";
		}

	}

}