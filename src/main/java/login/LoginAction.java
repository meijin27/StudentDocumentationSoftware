package login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.PasswordUtil;

public class LoginAction extends Action {
	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションの作成
		HttpSession session = request.getSession();
		// セッションタイムアウト設定 (1時間)
		session.setMaxInactiveInterval(3600);
		// 入力されたアカウント名とパスワードを変数に格納
		String account = request.getParameter("account");
		String password = request.getParameter("password");

		// ログイン名とパスワードが両方とも入力されているときの処理
		if (account != null && !account.isEmpty() && password != null && !password.isEmpty()) {
			// 文字数が32文字より多い場合はエラーを返す
			if (account.length() > 32 || password.length() > 32) {
				request.setAttribute("loginError", "32文字以下で入力してください。");
				return "login.jsp";
			}
			// データベース操作用クラス
			UserDAO dao = new UserDAO();
			// 入力されたアカウント名をサーバー共通暗号で暗号化
			String encryptedAccount = CipherUtil.commonEncrypt(account);
			// 暗号化したアカウント名でデータベースを検索
			User user = dao.loginSearch(encryptedAccount);
			// データベースにアカウント名が存在し、パスワードが一致した場合の処理
			if (user != null && PasswordUtil.isPasswordMatch(password, user.getPassword())) {
				// ユーザーIDを変数に格納
				int id = user.getId();
				// ivを変数に格納
				String iv = user.getIv();
				// クライアントのIPアドレスを取得
				String ipAddress = request.getRemoteAddr();
				// データベースにログイン記録を追加
				dao.addLoginLog(id, ipAddress);
				// マスターキーの共通暗号からの復号
				String encryptedMasterKey = CipherUtil.commonDecrypt(user.getMasterKey());
				// 暗号化されたマスターキーを復号する
				String masterKey = CipherUtil.decrypt(password + account, iv, encryptedMasterKey);
				// 復号したマスターキーをアカウントとIDを暗号キーにして暗号化する（なるべく文字列をランダム化するためにアカウントが先でIDが後）
				String encryptedKey = CipherUtil.encrypt(account + id, iv, masterKey);
				// 暗号化したマスターキーをさらに共通暗号キーで暗号化する
				String reEncryptedKey = CipherUtil.commonEncrypt(encryptedKey);
				// ユーザーの学生の種類を変数に格納(この時点で暗号化されている)
				String studentType = user.getStudentType();
				// IDを共通暗号キーで暗号化する
				String strId = CipherUtil.commonEncrypt(String.valueOf(id));
				// セッションにユーザー識別用の暗号化したIDを持たせる				
				session.setAttribute("id", strId);
				// セッションに再暗号化したマスターキーを持たせる
				session.setAttribute("master_key", reEncryptedKey);

				// ユーザーのデータベースに秘密の質問が登録されていなければ秘密の質問と答え登録ページに移動
				if (user.getSecretQuestion() == null) {
					String contextPath = request.getContextPath();
					response.sendRedirect(contextPath + "/firstSetting/secret-setting.jsp");
					return null;
					// ユーザーのデータベースに名前等が登録されていなければ初期登録ページに移動
				} else if (studentType == null) {
					String contextPath = request.getContextPath();
					response.sendRedirect(contextPath + "/firstSetting/first-setting.jsp");
					return null;
					// メインメニューに移動
				} else {
					// セッションに暗号化した学生の種類を持たせる
					session.setAttribute("student_type", studentType);
					// メインページにリダイレクト
					String contextPath = request.getContextPath();
					response.sendRedirect(contextPath + "/mainMenu/main-menu.jsp");
					return null;
				}
				// 入力されたアカウント名またはパスワードが違う場合の処理
			} else {
				request.setAttribute("loginError", "アカウント名またはパスワードが違います");
				return "login.jsp";
			}
		}
		// アカウント名もしくはパスワードが入力されていなければログイン画面に戻す
		request.setAttribute("loginError", "アカウント名およびパスワードの入力は必須です");
		return "login.jsp";
	}
}
