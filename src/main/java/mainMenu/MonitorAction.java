package mainMenu;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import tool.Action;
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

			// データベースから照合用データの取り出しと復号とセッションに格納
			// 姓の取り出し
			String reEncryptedLastName = dao.getLastName(id);
			String lastName = decrypt.getDecryptedDate(result, reEncryptedLastName);
			request.setAttribute("lastName", lastName);

			// 名の取り出し
			String reEncryptedFirstName = dao.getFirstName(id);
			String firstName = decrypt.getDecryptedDate(result, reEncryptedFirstName);
			request.setAttribute("firstName", firstName);
			// 姓のふりがなの取り出し
			String reEncryptedLastNameRuby = dao.getLastNameRuby(id);
			String lastNameRuby = decrypt.getDecryptedDate(result, reEncryptedLastNameRuby);
			request.setAttribute("lastNameRuby", lastNameRuby);
			// 名のふりがなの取り出し
			String reEncryptedFirstNameRuby = dao.getFirstNameRuby(id);
			String firstNameRuby = decrypt.getDecryptedDate(result, reEncryptedFirstNameRuby);
			request.setAttribute("firstNameRuby", firstNameRuby);
			// 電話番号の取り出し
			String reEncryptedTel = dao.getTel(id);
			String tel = decrypt.getDecryptedDate(result, reEncryptedTel);
			// 電話番号を3分割してハイフンをつけてくっつける
			if (tel != null && tel.length() == 11) {
				tel = tel.substring(0, 3) + "-" + tel.substring(3, 7) + "-" + tel.substring(7, 11);
			} else if (tel != null && tel.length() == 10) {
				tel = tel.substring(0, 3) + "-" + tel.substring(3, 6) + "-" + tel.substring(6, 10);
			}
			request.setAttribute("tel", tel);
			// 郵便番号の取り出し
			String reEncryptedPostCode = dao.getPostCode(id);
			String postCode = decrypt.getDecryptedDate(result, reEncryptedPostCode);
			// 郵便番号を2分割してハイフンをつけてくっつける
			if (postCode != null && postCode.length() == 7)
				postCode = postCode.substring(0, 3) + "-" + postCode.substring(3, 7);
			request.setAttribute("postCode", postCode);
			// 住所の取り出し
			String reEncryptedAddress = dao.getAddress(id);
			String address = decrypt.getDecryptedDate(result, reEncryptedAddress);
			request.setAttribute("address", address);
			// 生年の取り出し
			String reEncryptedBirthYear = dao.getBirthYear(id);
			String birthYear = decrypt.getDecryptedDate(result, reEncryptedBirthYear);
			request.setAttribute("birthYear", birthYear);
			// 生月の取り出し
			String reEncryptedBirthMonth = dao.getBirthMonth(id);
			String birthMonth = decrypt.getDecryptedDate(result, reEncryptedBirthMonth);
			request.setAttribute("birthMonth", birthMonth);
			// 生日の取り出し
			String reEncryptedBirthDay = dao.getBirthDay(id);
			String birthDay = decrypt.getDecryptedDate(result, reEncryptedBirthDay);
			request.setAttribute("birthDay", birthDay);
			// 入学年の取り出し
			String reEncryptedAdmissionYear = dao.getAdmissionYear(id);
			String admissionYear = decrypt.getDecryptedDate(result, reEncryptedAdmissionYear);
			request.setAttribute("admissionYear", admissionYear);
			// 入学月の取り出し
			String reEncryptedAdmissionMonth = dao.getAdmissionMonth(id);
			String admissionMonth = decrypt.getDecryptedDate(result, reEncryptedAdmissionMonth);
			request.setAttribute("admissionMonth", admissionMonth);
			// 入学日の取り出し
			String reEncryptedAdmissionDay = dao.getAdmissionDay(id);
			String admissionDay = decrypt.getDecryptedDate(result, reEncryptedAdmissionDay);
			request.setAttribute("admissionDay", admissionDay);
			// 学生種別の取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			String studentType = decrypt.getDecryptedDate(result, reEncryptedStudentType);
			request.setAttribute("studentType", studentType);
			// クラス名の取り出し
			String reEncryptedClassName = dao.getClassName(id);
			String className = decrypt.getDecryptedDate(result, reEncryptedClassName);
			request.setAttribute("className", className);
			// 学籍番号の取り出し
			String reEncryptedStudentNumber = dao.getStudentNumber(id);
			String studentNumber = decrypt.getDecryptedDate(result, reEncryptedStudentNumber);
			request.setAttribute("studentNumber", studentNumber);
			// 学年の取り出し
			String reEncryptedSchoolYear = dao.getSchoolYear(id);
			String schoolYear = decrypt.getDecryptedDate(result, reEncryptedSchoolYear);
			request.setAttribute("schoolYear", schoolYear);
			// 組の取り出し
			String reEncryptedClassNumber = dao.getClassNumber(id);
			String classNumber = decrypt.getDecryptedDate(result, reEncryptedClassNumber);
			request.setAttribute("classNumber", classNumber);
			// 公共職業安定所名の取り出し
			String reEncryptedNamePESO = dao.getNamePESO(id);
			String namePESO = decrypt.getDecryptedDate(result, reEncryptedNamePESO);
			request.setAttribute("namePESO", namePESO);
			// 支給番号の取り出し
			String reEncryptedSupplyNumber = dao.getSupplyNumber(id);
			String supplyNumber = decrypt.getDecryptedDate(result, reEncryptedSupplyNumber);
			request.setAttribute("supplyNumber", supplyNumber);
			// 出席番号の取り出し
			String reEncryptedAttendanceNumber = dao.getAttendanceNumber(id);
			String attendanceNumber = decrypt.getDecryptedDate(result, reEncryptedAttendanceNumber);
			request.setAttribute("attendanceNumber", attendanceNumber);
			// 雇用保険有無の取り出し
			String reEncryptedEmploymentInsurance = dao.getEmploymentInsurance(id);
			String employmentInsurance = decrypt.getDecryptedDate(result, reEncryptedEmploymentInsurance);
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
