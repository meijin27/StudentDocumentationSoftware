package mainMenu;

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
		// セッションからトークンを取得
		String sessionToken = (String) session.getAttribute("csrfToken");
		// リクエストパラメータからトークンを取得
		String requestToken = request.getParameter("csrfToken");
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// IDやマスターキーのセッションがない、トークンが一致しない、またはセッションの有効期限切れの場合はエラーとして処理
		if (session.getAttribute("master_key") == null || session.getAttribute("id") == null || sessionToken == null
				|| requestToken == null || !sessionToken.equals(requestToken)) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "セッションエラーが発生しました。ログインしてください。");
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
			// マスターキーの取り出し			
			String masterKey = result.getMasterKey();
			// ivの取り出し
			String iv = result.getIv();

			// データベースから照合用データの取り出しと復号とセッションに格納
			// 姓の取り出し
			String reEncryptedLastName = dao.getLastName(id);
			String encryptedLastName = (reEncryptedLastName != null) ? CipherUtil.commonDecrypt(reEncryptedLastName)
					: null;
			String lastName = (encryptedLastName != null) ? CipherUtil.decrypt(masterKey, iv, encryptedLastName) : null;
			request.setAttribute("lastName", lastName);
			// 名の取り出し
			String reEncryptedFirstName = dao.getFirstName(id);
			String encryptedFirstName = (reEncryptedFirstName != null) ? CipherUtil.commonDecrypt(reEncryptedFirstName)
					: null;
			String firstName = (encryptedFirstName != null) ? CipherUtil.decrypt(masterKey, iv, encryptedFirstName)
					: null;
			request.setAttribute("firstName", firstName);
			// 姓のふりがなの取り出し
			String reEncryptedLastNameRuby = dao.getLastNameRuby(id);
			String encryptedLastNameRuby = (reEncryptedLastNameRuby != null)
					? CipherUtil.commonDecrypt(reEncryptedLastNameRuby)
					: null;
			String lastNameRuby = (encryptedLastNameRuby != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedLastNameRuby)
					: null;
			request.setAttribute("lastNameRuby", lastNameRuby);
			// 名のふりがなの取り出し
			String reEncryptedFirstNameRuby = dao.getFirstNameRuby(id);
			String encryptedFirstNameRuby = (reEncryptedFirstNameRuby != null)
					? CipherUtil.commonDecrypt(reEncryptedFirstNameRuby)
					: null;
			String firstNameRuby = (encryptedFirstNameRuby != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedFirstNameRuby)
					: null;
			request.setAttribute("firstNameRuby", firstNameRuby);
			// 電話番号の取り出し
			String reEncryptedTel = dao.getTel(id);
			String encryptedTel = (reEncryptedTel != null) ? CipherUtil.commonDecrypt(reEncryptedTel) : null;
			String tel = (encryptedTel != null) ? CipherUtil.decrypt(masterKey, iv, encryptedTel) : null;
			// 電話番号を3分割してハイフンをつけてくっつける
			if (tel != null && tel.length() == 11) {
				tel = tel.substring(0, 3) + "-" + tel.substring(3, 7) + "-" + tel.substring(7, 11);
			} else if (tel != null && tel.length() == 10) {
				tel = tel.substring(0, 3) + "-" + tel.substring(3, 6) + "-" + tel.substring(6, 10);
			}
			request.setAttribute("tel", tel);
			// 郵便番号の取り出し
			String reEncryptedPostCode = dao.getPostCode(id);
			String encryptedPostCode = (reEncryptedPostCode != null) ? CipherUtil.commonDecrypt(reEncryptedPostCode)
					: null;
			String postCode = (encryptedPostCode != null) ? CipherUtil.decrypt(masterKey, iv, encryptedPostCode) : null;
			// 郵便番号を2分割してハイフンをつけてくっつける
			if (postCode != null && postCode.length() == 7)
				postCode = postCode.substring(0, 3) + "-" + postCode.substring(3, 7);
			request.setAttribute("postCode", postCode);
			// 住所の取り出し
			String reEncryptedAddress = dao.getAddress(id);
			String encryptedAddress = (reEncryptedAddress != null) ? CipherUtil.commonDecrypt(reEncryptedAddress)
					: null;
			String address = (encryptedAddress != null) ? CipherUtil.decrypt(masterKey, iv, encryptedAddress) : null;
			request.setAttribute("address", address);
			// 生年の取り出し
			String reEncryptedBirthYear = dao.getBirthYear(id);
			String encryptedBirthYear = (reEncryptedBirthYear != null) ? CipherUtil.commonDecrypt(reEncryptedBirthYear)
					: null;
			String birthYear = (encryptedBirthYear != null) ? CipherUtil.decrypt(masterKey, iv, encryptedBirthYear)
					: null;
			request.setAttribute("birthYear", birthYear);
			// 生月の取り出し
			String reEncryptedBirthMonth = dao.getBirthMonth(id);
			String encryptedBirthMonth = (reEncryptedBirthMonth != null)
					? CipherUtil.commonDecrypt(reEncryptedBirthMonth)
					: null;
			String birthMonth = (encryptedBirthMonth != null) ? CipherUtil.decrypt(masterKey, iv, encryptedBirthMonth)
					: null;
			request.setAttribute("birthMonth", birthMonth);
			// 生日の取り出し
			String reEncryptedBirthDay = dao.getBirthDay(id);
			String encryptedBirthDay = (reEncryptedBirthDay != null) ? CipherUtil.commonDecrypt(reEncryptedBirthDay)
					: null;
			String birthDay = (encryptedBirthDay != null) ? CipherUtil.decrypt(masterKey, iv, encryptedBirthDay) : null;
			request.setAttribute("birthDay", birthDay);
			// 入学年の取り出し
			String reEncryptedAdmissionYear = dao.getAdmissionYear(id);
			String encryptedAdmissionYear = (reEncryptedAdmissionYear != null)
					? CipherUtil.commonDecrypt(reEncryptedAdmissionYear)
					: null;
			String admissionYear = (encryptedAdmissionYear != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedAdmissionYear)
					: null;
			request.setAttribute("admissionYear", admissionYear);
			// 入学月の取り出し
			String reEncryptedAdmissionMonth = dao.getAdmissionMonth(id);
			String encryptedAdmissionMonth = (reEncryptedAdmissionMonth != null)
					? CipherUtil.commonDecrypt(reEncryptedAdmissionMonth)
					: null;
			String admissionMonth = (encryptedAdmissionMonth != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedAdmissionMonth)
					: null;
			request.setAttribute("admissionMonth", admissionMonth);
			// 入学日の取り出し
			String reEncryptedAdmissionDay = dao.getAdmissionDay(id);
			String encryptedAdmissionDay = (reEncryptedAdmissionDay != null)
					? CipherUtil.commonDecrypt(reEncryptedAdmissionDay)
					: null;
			String admissionDay = (encryptedAdmissionDay != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedAdmissionDay)
					: null;
			request.setAttribute("admissionDay", admissionDay);
			// 学生種別の取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			String encryptedStudentType = (reEncryptedStudentType != null)
					? CipherUtil.commonDecrypt(reEncryptedStudentType)
					: null;
			String studentType = (encryptedStudentType != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedStudentType)
					: null;
			request.setAttribute("studentType", studentType);
			// クラス名の取り出し
			String reEncryptedClassName = dao.getClassName(id);
			String encryptedClassName = (reEncryptedClassName != null) ? CipherUtil.commonDecrypt(reEncryptedClassName)
					: null;
			String className = (encryptedClassName != null) ? CipherUtil.decrypt(masterKey, iv, encryptedClassName)
					: null;
			request.setAttribute("className", className);
			// 学籍番号の取り出し
			String reEncryptedStudentNumber = dao.getStudentNumber(id);
			String encryptedStudentNumber = (reEncryptedStudentNumber != null)
					? CipherUtil.commonDecrypt(reEncryptedStudentNumber)
					: null;
			String studentNumber = (encryptedStudentNumber != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedStudentNumber)
					: null;
			request.setAttribute("studentNumber", studentNumber);
			// 学年の取り出し
			String reEncryptedSchoolYear = dao.getSchoolYear(id);
			String encryptedSchoolYear = (reEncryptedSchoolYear != null)
					? CipherUtil.commonDecrypt(reEncryptedSchoolYear)
					: null;
			String schoolYear = (encryptedSchoolYear != null) ? CipherUtil.decrypt(masterKey, iv, encryptedSchoolYear)
					: null;
			request.setAttribute("schoolYear", schoolYear);
			// 組の取り出し
			String reEncryptedClassNumber = dao.getClassNumber(id);
			String encryptedClassNumber = (reEncryptedClassNumber != null)
					? CipherUtil.commonDecrypt(reEncryptedClassNumber)
					: null;
			String classNumber = (encryptedClassNumber != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedClassNumber)
					: null;
			request.setAttribute("classNumber", classNumber);
			// 公共職業安定所名の取り出し
			String reEncryptedNamePESO = dao.getNamePESO(id);
			String encryptedNamePESO = (reEncryptedNamePESO != null) ? CipherUtil.commonDecrypt(reEncryptedNamePESO)
					: null;
			String namePESO = (encryptedNamePESO != null) ? CipherUtil.decrypt(masterKey, iv, encryptedNamePESO) : null;
			request.setAttribute("namePESO", namePESO);
			// 支給番号の取り出し
			String reEncryptedSupplyNumber = dao.getSupplyNumber(id);
			String encryptedSupplyNumber = (reEncryptedSupplyNumber != null)
					? CipherUtil.commonDecrypt(reEncryptedSupplyNumber)
					: null;
			String supplyNumber = (encryptedSupplyNumber != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedSupplyNumber)
					: null;
			request.setAttribute("supplyNumber", supplyNumber);
			// 出席番号の取り出し
			String reEncryptedAttendanceNumber = dao.getAttendanceNumber(id);
			String encryptedAttendanceNumber = (reEncryptedAttendanceNumber != null)
					? CipherUtil.commonDecrypt(reEncryptedAttendanceNumber)
					: null;
			String attendanceNumber = (encryptedAttendanceNumber != null)
					? CipherUtil.decrypt(masterKey, iv, encryptedAttendanceNumber)
					: null;
			request.setAttribute("attendanceNumber", attendanceNumber);
			// 雇用保険有無の取り出し
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

		// トークンの削除
		request.getSession().removeAttribute("csrfToken");

		return "monitor.jsp";
	}
}
