package forgotPassword;

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

public class RecreatePasswordAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションの作成
		HttpSession session = request.getSession();
		// 入力されたパスワードと再確認用パスワードを変数に格納
		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("passwordCheck");

		// セッションの有効期限切れの場合はエラーとして処理
		if (session.getAttribute("id") == null || session.getAttribute("master_key") == null) {
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
			return "recreate-password.jsp";
		}

		// パスワードが英大文字・小文字・数字をすべて含み８文字以上の場合の処理
		if (Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$", password)) {
			// データベース操作用クラス
			UserDAO dao = new UserDAO();
			// セッションから暗号化されたIDの取り出し
			String strId = (String) session.getAttribute("id");
			// IDの復号
			int id = Integer.parseInt(CipherUtil.commonDecrypt(strId));
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
			// セッションの全削除
			session.invalidate();
			// アップデート内容のデータベースへの登録
			dao.addOperationLog(id, "Forgot Password Recreate");
			// パスワード再作成成功画面に遷移
			return "recreate-success.jsp";

			// パスワードの入力形式が不適切ならエラー処理
		} else {
			request.setAttribute("passwordError", "パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください");
			return "recreate-password.jsp";
		}

	}

}