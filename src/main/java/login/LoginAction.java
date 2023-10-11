package login;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import tool.RequestAndSessionUtil;
import tool.ValidationUtil;

public class LoginAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(LoginAction.class);

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

		// 入力されたアカウント名とパスワードを変数に格納
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		// ログイン名とパスワードが両方とも入力されているときの処理
		if (ValidationUtil.isNullOrEmpty(account, password)) {
			// アカウント名もしくはパスワードが入力されていなければログイン画面に戻す
			request.setAttribute("nullError", "アカウント名およびパスワードの入力は必須です");
			return "login.jsp";
		}

		// 文字数が32文字より多い場合はエラーを返す
		if (ValidationUtil.areValidLengths(32, account, password)) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
			return "login.jsp";
		}

		try {
			// データベース操作用クラス
			UserDAO dao = new UserDAO();
			// 入力されたアカウント名をサーバー共通暗号で暗号化
			String encryptedAccount = CipherUtil.commonEncrypt(account);
			// 暗号化したアカウント名でデータベースを検索
			User user = dao.loginSearch(encryptedAccount);
			// クライアントのIPアドレスを取得
			String ipAddress = request.getRemoteAddr();
			// データベースにアカウント名が存在しない場合の処理
			if (user == null) {
				request.setAttribute("loginError", "アカウント名またはパスワードが違います");
				// データベースにログイン記録を追加
				dao.addLoginLog("NoAccount", ipAddress, "NoAccount");
				return "login.jsp";
			}
			// ユーザーIDを変数に格納
			String id = user.getId();
			// ログイン失敗回数を確認
			String num = user.getLoginFailureCount();
			int number = Integer.parseInt(num);
			String accountLockTime = user.getAccountLockTime();
			// ログイン失敗回数とアカウントロック時間を確認する
			if (number < 10) {
				// ログイン失敗回数が10回以下なら何もしない
			} else if (accountLockTime == null) {
				// アカウントロック時間がNULLなら何もしない
			} else {
				// 取得した時間と現在の時刻とを比較して30分経過しているか確認
				// データベースの日付・時間のフォーマットを適切に設定する
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // このフォーマットはデータベースのフォーマットに合わせて変更してください
				// String形式のタイムスタンプをLocalDateTimeに変換
				LocalDateTime lockTime = LocalDateTime.parse(accountLockTime, formatter);
				// 現在の時間を取得
				LocalDateTime now = LocalDateTime.now();
				// 2つのLocalDateTimeの間の差分を計算
				Duration duration = Duration.between(lockTime, now);
				// 差分が5分以上かどうかを返す
				if (duration.toMinutes() < 5) {
					// ロック時間から5分経っていなかったらエラーを返す
					request.setAttribute("loginError", "しばらく時間をおいてからログインしてください");
					// データベースにログイン記録を追加
					dao.addLoginLog(id, ipAddress, "LockTimeAccsess");
					return "login.jsp";
				} else {
					// ここに来ている時点でアカウントロック解除された初回ログイン
					// アカウントロック回数を０にする
					number = 0;
					user.setLoginFailureCount("0");
					// ログイン失敗回数をデータベースへ登録する
					dao.updateLoginFailureCount(user);

				}
			}

			// パスワードが一致しない場合の処理
			if (!PasswordUtil.isPasswordMatch(password, user.getPassword())) {
				// ログイン失敗回数を１回追加
				number++;
				num = String.valueOf(number);
				user.setLoginFailureCount(num);
				// ログイン失敗回数が10回になった場合はアカウントをロックする
				if (number >= 10) {
					dao.accountLock(id);
				}
				// ログイン失敗回数をデータベースへ登録する
				dao.updateLoginFailureCount(user);
				request.setAttribute("loginError", "アカウント名またはパスワードが違います");
				// データベースにログイン記録を追加
				dao.addLoginLog(id, ipAddress, "WrongPassword");
				return "login.jsp";
			}
			// 現在のセッションを無効化
			session.invalidate();
			// 新しいセッションを作成
			session = request.getSession(true);
			// アカウントロック回数を０にする
			user.setLoginFailureCount("0");
			// ログイン失敗回数をデータベースへ登録する
			dao.updateLoginFailureCount(user);
			// ivを変数に格納
			String iv = user.getIv();
			// データベースにログイン記録を追加
			dao.addLoginLog(id, ipAddress, "LoginSuccess");
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
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("loginError", "内部エラーが発生しました。");
			return "login.jsp";
		}
	}

}
