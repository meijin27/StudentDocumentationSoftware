package login;

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
import tool.PasswordUtil;

public class LoginAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(LoginAction.class);

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
			// セッションにエラーメッセージを格納
			session.setAttribute("otherError", "セッションエラーが発生しました。最初からやり直してください。");
			// ログインページにリダイレクト
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

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

			try {
				// データベース操作用クラス
				UserDAO dao = new UserDAO();
				// 入力されたアカウント名をサーバー共通暗号で暗号化
				String encryptedAccount = CipherUtil.commonEncrypt(account);
				// 暗号化したアカウント名でデータベースを検索
				User user = dao.loginSearch(encryptedAccount);
				// データベースにアカウント名が存在し、パスワードが一致した場合の処理
				if (user != null && PasswordUtil.isPasswordMatch(password, user.getPassword())) {
					// 現在のセッションを無効化
					session.invalidate();
					// 新しいセッションを作成
					session = request.getSession(true);
					// ユーザーIDを変数に格納
					String id = user.getId();
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
					// ユーザーの学生の種類
					String studentType = null;
					// ユーザーの学生の種類が登録されている場合
					if (user.getStudentType() != null) {
						// ユーザーの学生の種類を共通暗号からの復号
						String encryptedStudentType = CipherUtil.commonDecrypt(user.getStudentType());
						// 暗号化された学生の種類を復号する
						studentType = CipherUtil.decrypt(masterKey, iv, encryptedStudentType);
					}
					// IDを共通暗号キーで暗号化する
					String encryptedId = CipherUtil.commonEncrypt(id);
					// セッションにユーザー識別用の暗号化したIDを持たせる				
					session.setAttribute("id", encryptedId);
					// セッションに再暗号化したマスターキーを持たせる
					session.setAttribute("master_key", reEncryptedKey);
					// トークンの削除
					request.getSession().removeAttribute("csrfToken");

					// ユーザーのデータベースに秘密の質問が登録されていなければ秘密の質問と答え登録ページに移動
					if (user.getSecretQuestion() == null) {
						// セッションに秘密の質問未登録情報を持たせる				
						session.setAttribute("secretSetting", "unregistered");
						// 秘密の質問設定ページへリダイレクト
						response.sendRedirect(contextPath + "/firstSetting/secret-setting.jsp");
						return null;
						// ユーザーのデータベースに学生種別が登録されていなければ初期登録ページに移動
					} else if (studentType == null) {
						// セッションに初期設定未登録情報を持たせる				
						session.setAttribute("firstSetting", "unregistered");
						// 初期設定ページへリダイレクト
						response.sendRedirect(contextPath + "/firstSetting/first-setting.jsp");
						return null;
						// ユーザーのデータベースにハローワーク名が登録されていなければ職業訓練生登録ページに移動
					} else if (studentType.equals("職業訓練生") && user.getNamePESO() == null) {
						// セッションに職業訓練生未登録情報を持たせる				
						session.setAttribute("vocationalSetting", "unregistered");
						// 職業訓練生登録ページへリダイレクト
						response.sendRedirect(contextPath + "/firstSetting/vocational-trainee-setting.jsp");
						return null;
						// メインメニューに移動
					} else {
						// メインページにリダイレクト
						response.sendRedirect(contextPath + "/mainMenu/main-menu.jsp");
						return null;
					}
					// 入力されたアカウント名またはパスワードが違う場合の処理
				} else {
					request.setAttribute("loginError", "アカウント名またはパスワードが違います");
					return "login.jsp";
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				request.setAttribute("loginError", "内部エラーが発生しました。");
				return "login.jsp";
			}
		}
		// アカウント名もしくはパスワードが入力されていなければログイン画面に戻す
		request.setAttribute("loginError", "アカウント名およびパスワードの入力は必須です");
		return "login.jsp";
	}
}
