package mainMenu;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.CustomLogger;
import tool.Decrypt;
import tool.DecryptionResult;

public class MonitorAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(MonitorAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();

		// セッションの有効期限切れや直接初期設定入力ページにアクセスした場合はエラーとして処理
		if (session.getAttribute("master_key") == null || session.getAttribute("id") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "セッションエラーが発生しました。ログインしてください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		try {
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
			request.setAttribute("monitorId", id);
			request.setAttribute("account", account);
			String password = dao.getPassword(id);
			request.setAttribute("password", password);
			request.setAttribute("monitorMasterKey", masterKey);
			String secondMasterKey = dao.getSecondMasterKey(id);
			request.setAttribute("secondMasterKey", secondMasterKey);
			request.setAttribute("iv", iv);
			String secretAnswer = dao.getSecretAnswer(id);
			request.setAttribute("secretAnswer", secretAnswer);

			String reEncryptedSecretQuestion = dao.getSecretQuestion(id);
			// 暗号化した秘密の質問を共通暗号キーで復号化する
			String encryptedSecretQuestion = (reEncryptedSecretQuestion != null)
					? CipherUtil.commonDecrypt(reEncryptedSecretQuestion)
					: null;
			// 秘密の質問はアカウント名とIDをキーにして復号化
			String secretQuestion = (encryptedSecretQuestion != null)
					? CipherUtil.decrypt(account + id, iv, encryptedSecretQuestion)
					: null;
			// セッションに秘密の質問を持たせる				
			request.setAttribute("secretQuestion", secretQuestion);

			String reEncryptedLastName = dao.getLastName(id);
			String encryptedLastName = (reEncryptedLastName != null) ? CipherUtil.commonDecrypt(reEncryptedLastName)
					: null;
			String lastName = (encryptedLastName != null) ? CipherUtil.decrypt(masterKey, iv, encryptedLastName) : null;
			request.setAttribute("lastName", lastName);

			String reEncryptedFirstName = dao.getFirstName(id);
			String encryptedFirstName = (reEncryptedFirstName != null) ? CipherUtil.commonDecrypt(reEncryptedFirstName)
					: null;
			String firstName = (encryptedFirstName != null) ? CipherUtil.decrypt(masterKey, iv, encryptedFirstName)
					: null;
			request.setAttribute("firstName", firstName);

			String reEncryptedLastNameRuby = dao.getLastNameRuby(id);
			String encryptedLastNameRuby = (reEncryptedLastNameRuby != null)
					? CipherUtil.commonDecrypt(reEncryptedLastNameRuby)
					: null;
			String lastNameRuby = (encryptedLastNameRuby != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedLastNameRuby)
					: null;
			request.setAttribute("lastNameRuby", lastNameRuby);

			String reEncryptedFirstNameRuby = dao.getFirstNameRuby(id);
			String encryptedFirstNameRuby = (reEncryptedFirstNameRuby != null)
					? CipherUtil.commonDecrypt(reEncryptedFirstNameRuby)
					: null;
			String firstNameRuby = (encryptedFirstNameRuby != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedFirstNameRuby)
					: null;
			request.setAttribute("firstNameRuby", firstNameRuby);

			String reEncryptedTel = dao.getTel(id);
			String encryptedTel = (reEncryptedTel != null) ? CipherUtil.commonDecrypt(reEncryptedTel) : null;
			String tel = (encryptedTel != null) ? CipherUtil.decrypt(masterKey, iv, encryptedTel) : null;
			request.setAttribute("tel", tel);

			String reEncryptedPostCode = dao.getPostCode(id);
			String encryptedPostCode = (reEncryptedPostCode != null) ? CipherUtil.commonDecrypt(reEncryptedPostCode)
					: null;
			String postCode = (encryptedPostCode != null) ? CipherUtil.decrypt(masterKey, iv, encryptedPostCode) : null;
			request.setAttribute("postCode", postCode);

			String reEncryptedAddress = dao.getAddress(id);
			String encryptedAddress = (reEncryptedAddress != null) ? CipherUtil.commonDecrypt(reEncryptedAddress)
					: null;
			String address = (encryptedAddress != null) ? CipherUtil.decrypt(masterKey, iv, encryptedAddress) : null;
			request.setAttribute("address", address);

			String reEncryptedBirthYear = dao.getBirthYear(id);
			String encryptedBirthYear = (reEncryptedBirthYear != null) ? CipherUtil.commonDecrypt(reEncryptedBirthYear)
					: null;
			String birthYear = (encryptedBirthYear != null) ? CipherUtil.decrypt(masterKey, iv, encryptedBirthYear)
					: null;
			request.setAttribute("birthYear", birthYear);

			String reEncryptedBirthMonth = dao.getBirthMonth(id);
			String encryptedBirthMonth = (reEncryptedBirthMonth != null)
					? CipherUtil.commonDecrypt(reEncryptedBirthMonth)
					: null;
			String birthMonth = (encryptedBirthMonth != null) ? CipherUtil.decrypt(masterKey, iv, encryptedBirthMonth)
					: null;
			request.setAttribute("birthMonth", birthMonth);

			String reEncryptedBirthDay = dao.getBirthDay(id);
			String encryptedBirthDay = (reEncryptedBirthDay != null) ? CipherUtil.commonDecrypt(reEncryptedBirthDay)
					: null;
			String birthDay = (encryptedBirthDay != null) ? CipherUtil.decrypt(masterKey, iv, encryptedBirthDay) : null;
			request.setAttribute("birthDay", birthDay);

			String reEncryptedAdmissionYear = dao.getAdmissionYear(id);
			String encryptedAdmissionYear = (reEncryptedAdmissionYear != null)
					? CipherUtil.commonDecrypt(reEncryptedAdmissionYear)
					: null;
			String admissionYear = (encryptedAdmissionYear != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedAdmissionYear)
					: null;
			request.setAttribute("admissionYear", admissionYear);

			String reEncryptedAdmissionMonth = dao.getAdmissionMonth(id);
			String encryptedAdmissionMonth = (reEncryptedAdmissionMonth != null)
					? CipherUtil.commonDecrypt(reEncryptedAdmissionMonth)
					: null;
			String admissionMonth = (encryptedAdmissionMonth != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedAdmissionMonth)
					: null;
			request.setAttribute("admissionMonth", admissionMonth);

			String reEncryptedAdmissionDay = dao.getAdmissionDay(id);
			String encryptedAdmissionDay = (reEncryptedAdmissionDay != null)
					? CipherUtil.commonDecrypt(reEncryptedAdmissionDay)
					: null;
			String admissionDay = (encryptedAdmissionDay != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedAdmissionDay)
					: null;
			request.setAttribute("admissionDay", admissionDay);

			String reEncryptedStudentType = dao.getStudentType(id);
			String encryptedStudentType = (reEncryptedStudentType != null)
					? CipherUtil.commonDecrypt(reEncryptedStudentType)
					: null;
			String studentType = (encryptedStudentType != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedStudentType)
					: null;
			request.setAttribute("studentType", studentType);

			String reEncryptedClassName = dao.getClassName(id);
			String encryptedClassName = (reEncryptedClassName != null) ? CipherUtil.commonDecrypt(reEncryptedClassName)
					: null;
			String className = (encryptedClassName != null) ? CipherUtil.decrypt(masterKey, iv, encryptedClassName)
					: null;
			request.setAttribute("className", className);

			String reEncryptedStudentNumber = dao.getStudentNumber(id);
			String encryptedStudentNumber = (reEncryptedStudentNumber != null)
					? CipherUtil.commonDecrypt(reEncryptedStudentNumber)
					: null;
			String studentNumber = (encryptedStudentNumber != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedStudentNumber)
					: null;
			request.setAttribute("studentNumber", studentNumber);

			String reEncryptedSchoolYear = dao.getSchoolYear(id);
			String encryptedSchoolYear = (reEncryptedSchoolYear != null)
					? CipherUtil.commonDecrypt(reEncryptedSchoolYear)
					: null;
			String schoolYear = (encryptedSchoolYear != null) ? CipherUtil.decrypt(masterKey, iv, encryptedSchoolYear)
					: null;
			request.setAttribute("schoolYear", schoolYear);

			String reEncryptedClassNumber = dao.getClassNumber(id);
			String encryptedClassNumber = (reEncryptedClassNumber != null)
					? CipherUtil.commonDecrypt(reEncryptedClassNumber)
					: null;
			String classNumber = (encryptedClassNumber != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedClassNumber)
					: null;
			request.setAttribute("classNumber", classNumber);

			String reEncryptedNamePESO = dao.getNamePESO(id);
			String encryptedNamePESO = (reEncryptedNamePESO != null) ? CipherUtil.commonDecrypt(reEncryptedNamePESO)
					: null;
			String namePESO = (encryptedNamePESO != null) ? CipherUtil.decrypt(masterKey, iv, encryptedNamePESO) : null;
			request.setAttribute("namePESO", namePESO);

			String reEncryptedSupplyNumber = dao.getSupplyNumber(id);
			String encryptedSupplyNumber = (reEncryptedSupplyNumber != null)
					? CipherUtil.commonDecrypt(reEncryptedSupplyNumber)
					: null;
			String supplyNumber = (encryptedSupplyNumber != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedSupplyNumber)
					: null;
			request.setAttribute("supplyNumber", supplyNumber);

			String reEncryptedAttendanceNumber = dao.getAttendanceNumber(id);
			String encryptedAttendanceNumber = (reEncryptedAttendanceNumber != null)
					? CipherUtil.commonDecrypt(reEncryptedAttendanceNumber)
					: null;
			String attendanceNumber = (encryptedAttendanceNumber != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedAttendanceNumber)
					: null;
			request.setAttribute("attendanceNumber", attendanceNumber);

			String reEncryptedEmploymentInsurance = dao.getEmploymentInsurance(id);
			String encryptedEmploymentInsurance = (reEncryptedEmploymentInsurance != null)
					? CipherUtil.commonDecrypt(reEncryptedEmploymentInsurance)
					: null;
			String employmentInsurance = (encryptedEmploymentInsurance != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedEmploymentInsurance)
					: null;
			request.setAttribute("employmentInsurance", employmentInsurance);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "monitor.jsp";
		}
		if (session != null) {
			Enumeration<String> attributeNames = session.getAttributeNames();

			while (attributeNames.hasMoreElements()) {
				String attributeName = attributeNames.nextElement();
				Object attributeValue = session.getAttribute(attributeName);

				System.out.println(attributeName + " : " + attributeValue);
			}
		}
		return "monitor.jsp";
	}
}
