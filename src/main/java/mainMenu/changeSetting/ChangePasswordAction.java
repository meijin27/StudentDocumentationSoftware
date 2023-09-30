package mainMenu.changeSetting;

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
import tool.Decrypt;
import tool.DecryptionResult;
import tool.PasswordUtil;
import tool.RequestAndSessionUtil;
import tool.ValidationUtil;

public class ChangePasswordAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(ChangePasswordAction.class);

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

		// 入力された現在のパスワードと新パスワードと再確認用パスワードを変数に格納
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("password");
		String passwordCheck = request.getParameter("passwordCheck");

		// パスワードの入力チェック
		// 未入力及び不一致はエラー処理		
		if (ValidationUtil.isNullOrEmpty(oldPassword, newPassword, passwordCheck)) {
			request.setAttribute("nullError", "パスワードの入力は必須です");
		} else if (!newPassword.equals(passwordCheck)) {
			request.setAttribute("passwordError", "パスワードが一致しません。再度入力してください。");
		} else if (!ValidationUtil.areValidLengths(32, newPassword)) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "change-password.jsp";
		}

		// パスワードが英大文字・小文字・数字をすべて含み８文字以上の場合の処理
		if (Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$", newPassword)) {
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
				if (PasswordUtil.isPasswordMatch(oldPassword, registerPassword)) {
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

					// セッションに変更情報を持たせる				
					session.setAttribute("action", "パスワードを変更しました。");
					// トークンの削除
					request.getSession().removeAttribute("csrfToken");

					// エラーがない場合は行為成功表示用JSPへリダイレクト
					response.sendRedirect(contextPath + "/mainMenu/action-success.jsp");
					return null;
				} else {
					request.setAttribute("passwordError", "パスワードが一致しません。");
					return "change-password.jsp";
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				request.setAttribute("passwordError", "内部エラーが発生しました。");
				return "change-password.jsp";
			}
			// パスワードの入力形式が不適切ならエラー処理
		} else {
			request.setAttribute("passwordError", "パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください。");
			return "change-password.jsp";
		}

	}

}