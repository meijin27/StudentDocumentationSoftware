package mainMenu.changeSetting;

import java.time.DateTimeException;
import java.time.LocalDate;
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

public class ChangeNameDateofBirthAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(ChangeNameDateofBirthAction.class);

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

		// 入力された値を変数に格納
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String lastNameRuby = request.getParameter("lastNameRuby");
		String firstNameRuby = request.getParameter("firstNameRuby");
		String birthYear = request.getParameter("birthYear");
		String birthMonth = request.getParameter("birthMonth");
		String birthDay = request.getParameter("birthDay");

		// 入力された値をリクエストに格納		
		request.setAttribute("lastName", lastName);
		request.setAttribute("firstName", firstName);
		request.setAttribute("lastNameRuby", lastNameRuby);
		request.setAttribute("firstNameRuby", firstNameRuby);
		request.setAttribute("birthYear", birthYear);
		request.setAttribute("birthMonth", birthMonth);
		request.setAttribute("birthDay", birthDay);

		// 未入力項目があればエラーを返す
		if (lastName == null || firstName == null || lastNameRuby == null || firstNameRuby == null || birthYear == null
				|| birthMonth == null || birthDay == null
				|| lastName.isEmpty() || firstName.isEmpty() || lastNameRuby.isEmpty() || firstNameRuby.isEmpty()
				|| birthYear.isEmpty() || birthMonth.isEmpty()
				|| birthDay.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
		}

		// 「ふりがな」が「ひらがな」で記載されていなければエラーを返す
		Pattern pattern = Pattern.compile("^[\u3040-\u309F]+$");
		if (!pattern.matcher(lastNameRuby).matches() || !pattern.matcher(firstNameRuby).matches()) {
			request.setAttribute("rubyError", "「ふりがな」は「ひらがな」で入力してください。");
		}

		// 生年月日が存在しない日付の場合はエラーにする
		try {
			int year = Integer.parseInt(birthYear);
			int month = Integer.parseInt(birthMonth);
			int day = Integer.parseInt(birthDay);

			// 日付の妥当性チェック
			LocalDate date = LocalDate.of(year, month, day);
		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
		}

		// 文字数が32文字より多い場合はエラーを返す
		if (lastName.length() > 32 || firstName.length() > 32 || lastNameRuby.length() > 32
				|| firstNameRuby.length() > 32) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("nullError") != null || request.getAttribute("rubyError") != null
				|| request.getAttribute("dayError") != null
				|| request.getAttribute("valueLongError") != null) {
			return "change-name-date-of-birth.jsp";
		}

		// リクエストのデータ削除
		request.removeAttribute("lastName");
		request.removeAttribute("firstName");
		request.removeAttribute("lastNameRuby");
		request.removeAttribute("firstNameRuby");
		request.removeAttribute("birthYear");
		request.removeAttribute("birthMonth");
		request.removeAttribute("birthDay");

		try {
			// データベースとの接続用
			UserDAO dao = new UserDAO();
			// 復号とIDやIV等の取り出しクラスの設定
			Decrypt decrypt = new Decrypt(dao);
			DecryptionResult result = decrypt.getDecryptedMasterKey(session);
			// IDの取り出し
			String id = result.getId();
			// マスターキーの取り出し			
			String masterKey = result.getMasterKey();
			// ivの取り出し
			String iv = result.getIv();

			// 登録するデータの暗号化
			String encryptedLastName = CipherUtil.encrypt(masterKey, iv, lastName);
			String encryptedFirstName = CipherUtil.encrypt(masterKey, iv, firstName);
			String encryptedLastNameRuby = CipherUtil.encrypt(masterKey, iv, lastNameRuby);
			String encryptedFirstNameRuby = CipherUtil.encrypt(masterKey, iv, firstNameRuby);
			String encryptedBirthYear = CipherUtil.encrypt(masterKey, iv, birthYear);
			String encryptedBirthMonth = CipherUtil.encrypt(masterKey, iv, birthMonth);
			String encryptedBirthDay = CipherUtil.encrypt(masterKey, iv, birthDay);

			// 共通暗号キーによる暗号化
			String reEncryptedLastName = CipherUtil.commonEncrypt(encryptedLastName);
			String reEncryptedFirstName = CipherUtil.commonEncrypt(encryptedFirstName);
			String reEncryptedLastNameRuby = CipherUtil.commonEncrypt(encryptedLastNameRuby);
			String reEncryptedFirstNameRuby = CipherUtil.commonEncrypt(encryptedFirstNameRuby);
			String reEncryptedBirthYear = CipherUtil.commonEncrypt(encryptedBirthYear);
			String reEncryptedBirthMonth = CipherUtil.commonEncrypt(encryptedBirthMonth);
			String reEncryptedBirthDay = CipherUtil.commonEncrypt(encryptedBirthDay);

			// ユーザー情報の作成
			User user = new User();
			user.setId(id);
			user.setLastName(reEncryptedLastName);
			user.setFirstName(reEncryptedFirstName);
			user.setLastNameRuby(reEncryptedLastNameRuby);
			user.setFirstNameRuby(reEncryptedFirstNameRuby);
			user.setBirthYear(reEncryptedBirthYear);
			user.setBirthMonth(reEncryptedBirthMonth);
			user.setBirthDay(reEncryptedBirthDay);
			// データベースへの登録
			dao.updateLastName(user);
			dao.updateFirstName(user);
			dao.updateLastNameRuby(user);
			dao.updateFirstNameRuby(user);
			dao.updateBirthYear(user);
			dao.updateBirthMonth(user);
			dao.updateBirthDay(user);
			// アップデート内容のデータベースへの登録
			dao.addOperationLog(id, "Change Name & Date of Birth");
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "change-name-date-of-birth.jsp";
		}
		// 名前と生年月日変更成功画面に遷移
		request.setAttribute("changes", "名前と生年月日を変更しました。");
		return "change-success.jsp";

	}

}