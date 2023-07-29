package firstSetting;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.Decrypt;
import tool.DecryptionResult;

public class FirstSettingCheckAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		// セッションの有効期限切れや直接初期設定入力ページにアクセスした場合はエラーとして処理
		if (session.getAttribute("master_key") == null || session.getAttribute("id") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "セッションエラーが発生しました。ログインしてください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// リクエストからデータの取り出し
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String tel = request.getParameter("tel");
		String postCode = request.getParameter("postCode");
		String address = request.getParameter("address");
		String birthYear = request.getParameter("birthYear");
		String birthMonth = request.getParameter("birthMonth");
		String birthDay = request.getParameter("birthDay");
		String studentType = request.getParameter("studentType");
		String className = request.getParameter("className");
		String studentNumber = request.getParameter("studentNumber");
		String schoolYear = request.getParameter("schoolYear");
		String classNumber = request.getParameter("classNumber");
		String goBack = request.getParameter("goBack");

		// 入力された値をリクエストに格納
		request.setAttribute("lastName", lastName);
		request.setAttribute("firstName", firstName);
		request.setAttribute("tel", tel);
		request.setAttribute("postCode", postCode);
		request.setAttribute("address", address);
		request.setAttribute("birthYear", birthYear);
		request.setAttribute("birthMonth", birthMonth);
		request.setAttribute("birthDay", birthDay);
		request.setAttribute("studentType", studentType);
		request.setAttribute("className", className);
		request.setAttribute("studentNumber", studentNumber);
		request.setAttribute("schoolYear", schoolYear);
		request.setAttribute("classNumber", classNumber);

		// 「戻る」ボタンが押された場合は入力フォームへ戻る
		if (goBack != null) {
			return "first-setting.jsp";
		}

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
		String encryptedTel = CipherUtil.encrypt(masterKey, iv, tel);
		String encryptedPostCode = CipherUtil.encrypt(masterKey, iv, postCode);
		String encryptedAddress = CipherUtil.encrypt(masterKey, iv, address);
		String encryptedBirthYear = CipherUtil.encrypt(masterKey, iv, birthYear);
		String encryptedBirthMonth = CipherUtil.encrypt(masterKey, iv, birthMonth);
		String encryptedBirthDay = CipherUtil.encrypt(masterKey, iv, birthDay);
		String encryptedStudentType = CipherUtil.encrypt(masterKey, iv, studentType);
		String encryptedClassName = CipherUtil.encrypt(masterKey, iv, className);
		String encryptedStudentNumber = CipherUtil.encrypt(masterKey, iv, studentNumber);
		String encryptedSchoolYear = CipherUtil.encrypt(masterKey, iv, schoolYear);
		String encryptedClassNumber = CipherUtil.encrypt(masterKey, iv, classNumber);

		// 共通暗号キーによる暗号化
		String reEncryptedLastName = CipherUtil.commonEncrypt(encryptedLastName);
		String reEncryptedFirstName = CipherUtil.commonEncrypt(encryptedFirstName);
		String reEncryptedTel = CipherUtil.commonEncrypt(encryptedTel);
		String reEncryptedPostCode = CipherUtil.commonEncrypt(encryptedPostCode);
		String reEncryptedAddress = CipherUtil.commonEncrypt(encryptedAddress);
		String reEncryptedBirthYear = CipherUtil.commonEncrypt(encryptedBirthYear);
		String reEncryptedBirthMonth = CipherUtil.commonEncrypt(encryptedBirthMonth);
		String reEncryptedBirthDay = CipherUtil.commonEncrypt(encryptedBirthDay);
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
		user.setTel(reEncryptedTel);
		user.setPostCode(reEncryptedPostCode);
		user.setAddress(reEncryptedAddress);
		user.setBirthYear(reEncryptedBirthYear);
		user.setBirthMonth(reEncryptedBirthMonth);
		user.setBirthDay(reEncryptedBirthDay);
		user.setStudentType(reEncryptedStudentType);
		user.setClassName(reEncryptedClassName);
		user.setStudentNumber(reEncryptedStudentNumber);
		user.setSchoolYear(reEncryptedSchoolYear);
		user.setClassNumber(reEncryptedClassNumber);

		// 初期設定のデータベースへの登録
		dao.updateFirstSetting(user);
		// アップデート内容のデータベースへの登録
		dao.addOperationLog(id, "Create First Setting");
		if (studentType.equals("職業訓練生")) {
			return "vocational-trainee-setting.jsp";
		} else {
			// メインページにリダイレクト
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/mainMenu/main-menu.jsp");
			return null;
		}
	}

}
