package mainMenu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.Decrypt;
import tool.DecryptionResult;

public class MonitorAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();

		// セッションの有効期限切れや直接初期設定入力ページにアクセスした場合はエラーとして処理
		if (session.getAttribute("master_key") == null || session.getAttribute("id") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "エラーが発生しました。やり直してください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// データベース操作用クラス
		UserDAO dao = new UserDAO();
		// 復号とIDやIV等の取り出しクラスの設定
		Decrypt decrypt = new Decrypt(dao);
		DecryptionResult result = decrypt.getDecryptedMasterKey(session);
		// IDの取り出し
		String id = result.getId();
		// アカウント名の取り出し			
		String account = result.getAccount();
		// マスターキーの取り出し			
		String masterKey = result.getMasterKey();
		// ivの取り出し
		String iv = result.getIv();

		// データベースから照合用データの取り出しと復号とセッションに格納
		session.setAttribute("monitorId", id);
		session.setAttribute("account", account);
		String password = dao.getPassword(id);
		session.setAttribute("password", password);
		session.setAttribute("monitorMasterKey", masterKey);
		String secondMasterKey = dao.getSecondMasterKey(id);
		session.setAttribute("secondMasterKey", secondMasterKey);
		session.setAttribute("iv", iv);
		String secretAnswer = dao.getSecretAnswer(id);
		session.setAttribute("secretAnswer", secretAnswer);

		String reEncryptedSecretQuestion = dao.getSecretQuestion(id);
		// 暗号化した秘密の質問を共通暗号キーで復号化する
		String encryptedSecretQuestion = CipherUtil.commonDecrypt(reEncryptedSecretQuestion);
		// 秘密の質問はアカウント名とIDをキーにして復号化
		String secretQuestion = CipherUtil.decrypt(account + id, iv, encryptedSecretQuestion);
		// セッションに秘密の質問を持たせる				
		session.setAttribute("secretQuestion", secretQuestion);

		String reEncryptedLastName = dao.getLastName(id);
		String encryptedLastName = (reEncryptedLastName != null) ? CipherUtil.commonDecrypt(reEncryptedLastName) : null;
		String lastName = (encryptedLastName != null) ? CipherUtil.decrypt(masterKey, iv, encryptedLastName) : null;
		session.setAttribute("lastName", lastName);

		String reEncryptedFirstName = dao.getFirstName(id);
		String encryptedFirstName = (reEncryptedFirstName != null) ? CipherUtil.commonDecrypt(reEncryptedFirstName)
				: null;
		String firstName = (encryptedFirstName != null) ? CipherUtil.decrypt(masterKey, iv, encryptedFirstName) : null;
		session.setAttribute("firstName", firstName);

		String reEncryptedTel = dao.getTel(id);
		String encryptedTel = (reEncryptedTel != null) ? CipherUtil.commonDecrypt(reEncryptedTel) : null;
		String tel = (encryptedTel != null) ? CipherUtil.decrypt(masterKey, iv, encryptedTel) : null;
		session.setAttribute("tel", tel);

		String reEncryptedPostCode = dao.getPostCode(id);
		String encryptedPostCode = (reEncryptedPostCode != null) ? CipherUtil.commonDecrypt(reEncryptedPostCode) : null;
		String postCode = (encryptedPostCode != null) ? CipherUtil.decrypt(masterKey, iv, encryptedPostCode) : null;
		session.setAttribute("postCode", postCode);

		String reEncryptedAddress = dao.getAddress(id);
		String encryptedAddress = (reEncryptedAddress != null) ? CipherUtil.commonDecrypt(reEncryptedAddress) : null;
		String address = (encryptedAddress != null) ? CipherUtil.decrypt(masterKey, iv, encryptedAddress) : null;
		session.setAttribute("address", address);

		String reEncryptedBirthYear = dao.getBirthYear(id);
		String encryptedBirthYear = (reEncryptedBirthYear != null) ? CipherUtil.commonDecrypt(reEncryptedBirthYear)
				: null;
		String birthYear = (encryptedBirthYear != null) ? CipherUtil.decrypt(masterKey, iv, encryptedBirthYear) : null;
		session.setAttribute("birthYear", birthYear);

		String reEncryptedBirthMonth = dao.getBirthMonth(id);
		String encryptedBirthMonth = (reEncryptedBirthMonth != null) ? CipherUtil.commonDecrypt(reEncryptedBirthMonth)
				: null;
		String birthMonth = (encryptedBirthMonth != null) ? CipherUtil.decrypt(masterKey, iv, encryptedBirthMonth)
				: null;
		session.setAttribute("birthMonth", birthMonth);

		String reEncryptedBirthDay = dao.getBirthDay(id);
		String encryptedBirthDay = (reEncryptedBirthDay != null) ? CipherUtil.commonDecrypt(reEncryptedBirthDay) : null;
		String birthDay = (encryptedBirthDay != null) ? CipherUtil.decrypt(masterKey, iv, encryptedBirthDay) : null;
		session.setAttribute("birthDay", birthDay);

		String reEncryptedStudentType = dao.getStudentType(id);
		String encryptedStudentType = (reEncryptedStudentType != null)
				? CipherUtil.commonDecrypt(reEncryptedStudentType)
				: null;
		String studentType = (encryptedStudentType != null) ? CipherUtil.decrypt(masterKey, iv, encryptedStudentType)
				: null;
		session.setAttribute("studentType", studentType);

		String reEncryptedClassName = dao.getClassName(id);
		String encryptedClassName = (reEncryptedClassName != null) ? CipherUtil.commonDecrypt(reEncryptedClassName)
				: null;
		String className = (encryptedClassName != null) ? CipherUtil.decrypt(masterKey, iv, encryptedClassName) : null;
		session.setAttribute("className", className);

		String reEncryptedStudentNumber = dao.getStudentNumber(id);
		String encryptedStudentNumber = (reEncryptedStudentNumber != null)
				? CipherUtil.commonDecrypt(reEncryptedStudentNumber)
				: null;
		String studentNumber = (encryptedStudentNumber != null)
				? CipherUtil.decrypt(masterKey, iv, encryptedStudentNumber)
				: null;
		session.setAttribute("studentNumber", studentNumber);

		String reEncryptedSchoolYear = dao.getSchoolYear(id);
		String encryptedSchoolYear = (reEncryptedSchoolYear != null) ? CipherUtil.commonDecrypt(reEncryptedSchoolYear)
				: null;
		String schoolYear = (encryptedSchoolYear != null) ? CipherUtil.decrypt(masterKey, iv, encryptedSchoolYear)
				: null;
		session.setAttribute("schoolYear", schoolYear);

		String reEncryptedClassNumber = dao.getClassNumber(id);
		String encryptedClassNumber = (reEncryptedClassNumber != null)
				? CipherUtil.commonDecrypt(reEncryptedClassNumber)
				: null;
		String classNumber = (encryptedClassNumber != null) ? CipherUtil.decrypt(masterKey, iv, encryptedClassNumber)
				: null;
		session.setAttribute("classNumber", classNumber);

		String reEncryptedNamePESO = dao.getNamePESO(id);
		String encryptedNamePESO = (reEncryptedNamePESO != null) ? CipherUtil.commonDecrypt(reEncryptedNamePESO) : null;
		String namePESO = (encryptedNamePESO != null) ? CipherUtil.decrypt(masterKey, iv, encryptedNamePESO) : null;
		session.setAttribute("namePESO", namePESO);

		String reEncryptedSupplyNumber = dao.getSupplyNumber(id);
		String encryptedSupplyNumber = (reEncryptedSupplyNumber != null)
				? CipherUtil.commonDecrypt(reEncryptedSupplyNumber)
				: null;
		String supplyNumber = (encryptedSupplyNumber != null) ? CipherUtil.decrypt(masterKey, iv, encryptedSupplyNumber)
				: null;
		session.setAttribute("supplyNumber", supplyNumber);

		String reEncryptedAttendanceNumber = dao.getAttendanceNumber(id);
		String encryptedAttendanceNumber = (reEncryptedAttendanceNumber != null)
				? CipherUtil.commonDecrypt(reEncryptedAttendanceNumber)
				: null;
		String attendanceNumber = (encryptedAttendanceNumber != null)
				? CipherUtil.decrypt(masterKey, iv, encryptedAttendanceNumber)
				: null;
		session.setAttribute("attendanceNumber", attendanceNumber);

		String reEncryptedEmploymentInsurance = dao.getEmploymentInsurance(id);
		String encryptedEmploymentInsurance = (reEncryptedEmploymentInsurance != null)
				? CipherUtil.commonDecrypt(reEncryptedEmploymentInsurance)
				: null;
		String employmentInsurance = (encryptedEmploymentInsurance != null)
				? CipherUtil.decrypt(masterKey, iv, encryptedEmploymentInsurance)
				: null;
		session.setAttribute("employmentInsurance", employmentInsurance);

		return "monitor.jsp";
	}
}
