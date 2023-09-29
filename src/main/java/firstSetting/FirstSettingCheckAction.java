package firstSetting;

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
import tool.Decrypt;
import tool.DecryptionResult;
import tool.RequestAndSessionUtil;
import tool.ValidationUtil;

public class FirstSettingCheckAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(FirstSettingCheckAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークン及びログイン状態の確認
		if (!RequestAndSessionUtil.validateSession(request, response, "master_key", "id", "firstSetting",
				"firstSettingCheck")) {
			// ログイン状態が不正ならば処理を終了
			return null;
		}

		// セッションからデータの取り出し
		String lastName = (String) session.getAttribute("lastName");
		String firstName = (String) session.getAttribute("firstName");
		String lastNameRuby = (String) session.getAttribute("lastNameRuby");
		String firstNameRuby = (String) session.getAttribute("firstNameRuby");
		String tel = (String) session.getAttribute("tel");
		String postCode = (String) session.getAttribute("postCode");
		String address = (String) session.getAttribute("address");
		String birthYear = (String) session.getAttribute("birthYear");
		String birthMonth = (String) session.getAttribute("birthMonth");
		String birthDay = (String) session.getAttribute("birthDay");
		String admissionYear = (String) session.getAttribute("admissionYear");
		String admissionMonth = (String) session.getAttribute("admissionMonth");
		String admissionDay = (String) session.getAttribute("admissionDay");
		String studentType = (String) session.getAttribute("studentType");
		String className = (String) session.getAttribute("className");
		String studentNumber = (String) session.getAttribute("studentNumber");
		String schoolYear = (String) session.getAttribute("schoolYear");
		String classNumber = (String) session.getAttribute("classNumber");

		String goBack = request.getParameter("goBack");

		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(lastName, firstName, lastNameRuby, firstNameRuby, tel, birthYear, birthMonth,
				birthDay, admissionYear, admissionMonth, admissionDay, studentType, className, studentNumber,
				schoolYear, classNumber)) {
			// 初期設定ページへリダイレクト
			response.sendRedirect(contextPath + "/firstSetting/first-setting.jsp");
			return null;
		}

		// 「戻る」ボタンが押された場合は入力フォームへ戻る
		if (goBack != null) {
			// 初期設定ページへリダイレクト
			response.sendRedirect(contextPath + "/firstSetting/first-setting.jsp");
			return null;
		}

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

			// 秘密の質問のデータベースからの取り出し
			String reEncryptedSecretQuestion = dao.getSecretQuestion(id);
			// データベースから取り出したデータがnullの場合、初期設定をしていないためログインページにリダイレクト
			if (reEncryptedSecretQuestion == null) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// 登録するデータの暗号化
			String encryptedLastName = CipherUtil.encrypt(masterKey, iv, lastName);
			String encryptedFirstName = CipherUtil.encrypt(masterKey, iv, firstName);
			String encryptedLastNameRuby = CipherUtil.encrypt(masterKey, iv, lastNameRuby);
			String encryptedFirstNameRuby = CipherUtil.encrypt(masterKey, iv, firstNameRuby);
			String encryptedTel = CipherUtil.encrypt(masterKey, iv, tel);
			String encryptedPostCode = CipherUtil.encrypt(masterKey, iv, postCode);
			String encryptedAddress = CipherUtil.encrypt(masterKey, iv, address);
			String encryptedBirthYear = CipherUtil.encrypt(masterKey, iv, birthYear);
			String encryptedBirthMonth = CipherUtil.encrypt(masterKey, iv, birthMonth);
			String encryptedBirthDay = CipherUtil.encrypt(masterKey, iv, birthDay);
			String encryptedAdmissionYear = CipherUtil.encrypt(masterKey, iv, admissionYear);
			String encryptedAdmissionMonth = CipherUtil.encrypt(masterKey, iv, admissionMonth);
			String encryptedAdmissionDay = CipherUtil.encrypt(masterKey, iv, admissionDay);
			String encryptedStudentType = CipherUtil.encrypt(masterKey, iv, studentType);
			String encryptedClassName = CipherUtil.encrypt(masterKey, iv, className);
			String encryptedStudentNumber = CipherUtil.encrypt(masterKey, iv, studentNumber);
			String encryptedSchoolYear = CipherUtil.encrypt(masterKey, iv, schoolYear);
			String encryptedClassNumber = CipherUtil.encrypt(masterKey, iv, classNumber);

			// 共通暗号キーによる暗号化
			String reEncryptedLastName = CipherUtil.commonEncrypt(encryptedLastName);
			String reEncryptedFirstName = CipherUtil.commonEncrypt(encryptedFirstName);
			String reEncryptedLastNameRuby = CipherUtil.commonEncrypt(encryptedLastNameRuby);
			String reEncryptedFirstNameRuby = CipherUtil.commonEncrypt(encryptedFirstNameRuby);
			String reEncryptedTel = CipherUtil.commonEncrypt(encryptedTel);
			String reEncryptedPostCode = CipherUtil.commonEncrypt(encryptedPostCode);
			String reEncryptedAddress = CipherUtil.commonEncrypt(encryptedAddress);
			String reEncryptedBirthYear = CipherUtil.commonEncrypt(encryptedBirthYear);
			String reEncryptedBirthMonth = CipherUtil.commonEncrypt(encryptedBirthMonth);
			String reEncryptedBirthDay = CipherUtil.commonEncrypt(encryptedBirthDay);
			String reEncryptedAdmissionYear = CipherUtil.commonEncrypt(encryptedAdmissionYear);
			String reEncryptedAdmissionMonth = CipherUtil.commonEncrypt(encryptedAdmissionMonth);
			String reEncryptedAdmissionDay = CipherUtil.commonEncrypt(encryptedAdmissionDay);
			String reEncryptedStudentType = CipherUtil.commonEncrypt(encryptedStudentType);
			String reEncryptedClassName = CipherUtil.commonEncrypt(encryptedClassName);
			String reEncryptedStudentNumber = CipherUtil.commonEncrypt(encryptedStudentNumber);
			String reEncryptedSchoolYear = CipherUtil.commonEncrypt(encryptedSchoolYear);
			String reEncryptedClassNumber = CipherUtil.commonEncrypt(encryptedClassNumber);

			// ユーザー情報の作成
			User user = new User();
			user.setId(id);
			user.setLastName(reEncryptedLastName);
			user.setFirstName(reEncryptedFirstName);
			user.setLastNameRuby(reEncryptedLastNameRuby);
			user.setFirstNameRuby(reEncryptedFirstNameRuby);
			user.setTel(reEncryptedTel);
			user.setPostCode(reEncryptedPostCode);
			user.setAddress(reEncryptedAddress);
			user.setBirthYear(reEncryptedBirthYear);
			user.setBirthMonth(reEncryptedBirthMonth);
			user.setBirthDay(reEncryptedBirthDay);
			user.setAdmissionYear(reEncryptedAdmissionYear);
			user.setAdmissionMonth(reEncryptedAdmissionMonth);
			user.setAdmissionDay(reEncryptedAdmissionDay);
			user.setStudentType(reEncryptedStudentType);
			user.setClassName(reEncryptedClassName);
			user.setStudentNumber(reEncryptedStudentNumber);
			user.setSchoolYear(reEncryptedSchoolYear);
			user.setClassNumber(reEncryptedClassNumber);

			// 初期設定のデータベースへの登録
			dao.updateFirstSetting(user);
			// アップデート内容のデータベースへの登録
			dao.addOperationLog(id, "Create First Setting");
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			// 初期設定ページへリダイレクト
			response.sendRedirect(contextPath + "/firstSetting/first-setting.jsp");
		}

		// 初期設定登録情報のセッションからの削除
		request.getSession().removeAttribute("lastName");
		request.getSession().removeAttribute("firstName");
		request.getSession().removeAttribute("lastNameRuby");
		request.getSession().removeAttribute("firstNameRuby");
		request.getSession().removeAttribute("tel");
		request.getSession().removeAttribute("postCode");
		request.getSession().removeAttribute("address");
		request.getSession().removeAttribute("birthYear");
		request.getSession().removeAttribute("birthMonth");
		request.getSession().removeAttribute("birthDay");
		request.getSession().removeAttribute("admissionYear");
		request.getSession().removeAttribute("admissionMonth");
		request.getSession().removeAttribute("admissionDay");
		request.getSession().removeAttribute("studentType");
		request.getSession().removeAttribute("className");
		request.getSession().removeAttribute("studentNumber");
		request.getSession().removeAttribute("schoolYear");
		request.getSession().removeAttribute("classNumber");
		// 初期設定未登録情報及び初期設定未チェック情報のセッションからの削除
		request.getSession().removeAttribute("firstSetting");
		request.getSession().removeAttribute("firstSettingCheck");
		// トークンの削除
		request.getSession().removeAttribute("csrfToken");

		// 学生区分によってページ遷移先を変更する
		if (studentType.equals("職業訓練生")) {
			// セッションに職業訓練生未登録情報を持たせる				
			session.setAttribute("vocationalSetting", "unregistered");
			// 職業訓練生登録ページへリダイレクト
			response.sendRedirect(contextPath + "/firstSetting/vocational-trainee-setting.jsp");
		} else {
			// メインページにリダイレクト
			response.sendRedirect(contextPath + "/mainMenu/main-menu.jsp");
		}

		return null;

	}

}
