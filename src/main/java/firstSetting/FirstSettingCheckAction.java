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
			session.setAttribute("otherError", "エラーが発生しました。やり直してください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// セッションからデータの取り出し
		String lastName = (String) session.getAttribute("lastName");
		String firstName = (String) session.getAttribute("firstName");
		String tel = (String) session.getAttribute("tel");
		String postCode = (String) session.getAttribute("postCode");
		String address = (String) session.getAttribute("address");
		String birthYear = (String) session.getAttribute("birthYear");
		String birthMonth = (String) session.getAttribute("birthMonth");
		String birthDay = (String) session.getAttribute("birthDay");
		String studentType = (String) session.getAttribute("studentType");
		String className = (String) session.getAttribute("className");
		String studentNumber = (String) session.getAttribute("studentNumber");
		String schoolYear = (String) session.getAttribute("schoolYear");
		String classNumber = (String) session.getAttribute("classNumber");

		// セッションのデータ削除
		session.removeAttribute("lastName");
		session.removeAttribute("firstName");
		session.removeAttribute("tel");
		session.removeAttribute("postCode");
		session.removeAttribute("address");
		session.removeAttribute("birthYear");
		session.removeAttribute("birthMonth");
		session.removeAttribute("birthDay");
		session.removeAttribute("studentType");
		session.removeAttribute("className");
		session.removeAttribute("studentNumber");
		session.removeAttribute("schoolYear");
		session.removeAttribute("classNumber");

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
