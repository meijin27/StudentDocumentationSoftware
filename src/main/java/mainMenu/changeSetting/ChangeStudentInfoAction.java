package mainMenu.changeSetting;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Enumeration;
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

public class ChangeStudentInfoAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(ChangeStudentInfoAction.class);

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
		String studentType = request.getParameter("studentType");
		String className = request.getParameter("className");
		String studentNumber = request.getParameter("studentNumber");
		String schoolYear = request.getParameter("schoolYear");
		String classNumber = request.getParameter("classNumber");
		String admissionYear = request.getParameter("admissionYear");
		String admissionMonth = request.getParameter("admissionMonth");
		String admissionDay = request.getParameter("admissionDay");

		// 入力された値をリクエストに格納	
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			request.setAttribute(paramName, paramValue);
		}

		// 未入力項目があればエラーを返す
		if (studentType == null || className == null || studentNumber == null || schoolYear == null
				|| classNumber == null || admissionYear == null
				|| admissionMonth == null || admissionDay == null || studentType.isEmpty() || className.isEmpty()
				|| studentNumber.isEmpty()
				|| schoolYear.isEmpty()
				|| classNumber.isEmpty() || admissionYear.isEmpty()
				|| admissionMonth.isEmpty() || admissionDay.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "change-student-info.jsp";
		}

		// 生年月日が存在しない日付の場合はエラーにする
		try {
			int year = Integer.parseInt(admissionYear);
			int month = Integer.parseInt(admissionMonth);
			int day = Integer.parseInt(admissionDay);

			// 日付の妥当性チェック
			LocalDate Date = LocalDate.of(year, month, day);
		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
		}

		// 学籍番号が半角6桁でなければエラーを返す
		if (!studentNumber.matches("^\\d{6}$")) {
			request.setAttribute("studentNumberError", "学籍番号は半角数字6桁で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("dayError") != null || request.getAttribute("valueLongError") != null
				|| request.getAttribute("studentNumberError") != null) {
			return "change-student-info.jsp";
		}

		// リクエストのデータ全削除
		Enumeration<String> attributeNames = request.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			request.removeAttribute(attributeName);
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

			// 登録するデータの暗号化
			String encryptedStudentType = CipherUtil.encrypt(masterKey, iv, studentType);
			String encryptedClassName = CipherUtil.encrypt(masterKey, iv, className);
			String encryptedStudentNumber = CipherUtil.encrypt(masterKey, iv, studentNumber);
			String encryptedSchoolYear = CipherUtil.encrypt(masterKey, iv, schoolYear);
			String encryptedClassNumber = CipherUtil.encrypt(masterKey, iv, classNumber);
			String encryptedAdmissionYear = CipherUtil.encrypt(masterKey, iv, admissionYear);
			String encryptedAdmissionMonth = CipherUtil.encrypt(masterKey, iv, admissionMonth);
			String encryptedAdmissionDay = CipherUtil.encrypt(masterKey, iv, admissionDay);

			// 共通暗号キーによる暗号化
			String reEncryptedStudentType = CipherUtil.commonEncrypt(encryptedStudentType);
			String reEncryptedClassName = CipherUtil.commonEncrypt(encryptedClassName);
			String reEncryptedStudentNumber = CipherUtil.commonEncrypt(encryptedStudentNumber);
			String reEncryptedSchoolYear = CipherUtil.commonEncrypt(encryptedSchoolYear);
			String reEncryptedClassNumber = CipherUtil.commonEncrypt(encryptedClassNumber);
			String reEncryptedAdmissionYear = CipherUtil.commonEncrypt(encryptedAdmissionYear);
			String reEncryptedAdmissionMonth = CipherUtil.commonEncrypt(encryptedAdmissionMonth);
			String reEncryptedAdmissionDay = CipherUtil.commonEncrypt(encryptedAdmissionDay);

			// ユーザー情報の作成
			User user = new User();
			user.setId(id);
			user.setStudentType(reEncryptedStudentType);
			user.setClassName(reEncryptedClassName);
			user.setStudentNumber(reEncryptedStudentNumber);
			user.setSchoolYear(reEncryptedSchoolYear);
			user.setClassNumber(reEncryptedClassNumber);
			user.setAdmissionYear(reEncryptedAdmissionYear);
			user.setAdmissionMonth(reEncryptedAdmissionMonth);
			user.setAdmissionDay(reEncryptedAdmissionDay);

			// データベースへの登録
			dao.updateStudentType(user);
			dao.updateClassName(user);
			dao.updateStudentNumber(user);
			dao.updateSchoolYear(user);
			dao.updateClassNumber(user);
			dao.updateAdmissionYear(user);
			dao.updateAdmissionMonth(user);
			dao.updateAdmissionDay(user);
			// アップデート内容のデータベースへの登録
			dao.addOperationLog(id, "Change Student Infometion");
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "change-student-info.jsp";
		}
		// 学生情報変更成功画面に遷移
		request.setAttribute("changes", "学生情報を変更しました。");
		return "change-success.jsp";

	}

}