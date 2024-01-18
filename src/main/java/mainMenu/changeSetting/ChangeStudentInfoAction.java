package mainMenu.changeSetting;

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

public class ChangeStudentInfoAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(ChangeStudentInfoAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークン及びログイン状態の確認
		if (RequestAndSessionUtil.validateSession(request, response, "master_key", "id")) {
			// ログイン状態が不正ならば処理を終了
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
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 入学年月日のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(admissionYear, admissionMonth, admissionDay)) {
			request.setAttribute("admissionError", "入力必須項目です。");
		}
		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isFourDigit(admissionYear) ||
				ValidationUtil.isOneOrTwoDigit(admissionMonth, admissionDay)) {
			request.setAttribute("admissionError", "年月日は正規の桁数で入力してください。");
		}
		// 入学年月日が存在しない日付の場合はエラーにする
		else if (ValidationUtil.validateDate(admissionYear, admissionMonth, admissionDay)) {
			request.setAttribute("admissionError", "存在しない日付です。");
		}

		// クラス名のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(className)) {
			request.setAttribute("classNameError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(className)) {
			request.setAttribute("classNameError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(16, className)) {
			request.setAttribute("classNameError", "クラス名は16文字以下で入力してください。");
		}

		// 学生種別のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(studentType)) {
			request.setAttribute("studentTypeError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(studentType)) {
			request.setAttribute("studentTypeError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(5, studentType)) {
			request.setAttribute("studentTypeError", "学生種別は5文字以下で入力してください。");
		}

		// 学籍番号のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(studentNumber)) {
			request.setAttribute("studentNumberError", "入力必須項目です。");
		}
		// 学籍番号が半角6桁でなければエラーを返す
		else if (ValidationUtil.isSixDigit(studentNumber)) {
			request.setAttribute("studentNumberError", "学籍番号は半角数字6桁で入力してください。");
		}

		// 学年のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(schoolYear)) {
			request.setAttribute("schoolYearError", "入力必須項目です。");
		}
		// 学年が半角1桁でなければエラーを返す
		else if (ValidationUtil.isSingleDigit(schoolYear)) {
			request.setAttribute("schoolYearError", "学年は半角数字1桁で入力してください。");
		}

		// クラスのエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(classNumber)) {
			request.setAttribute("classNumberError", "入力必須項目です。");
		}
		// クラスが半角1桁でなければエラーを返すclassNumber
		else if (ValidationUtil.isSingleDigit(classNumber)) {
			request.setAttribute("classNumberError", "クラスは半角数字1桁で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "change-student-info.jsp";
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

			// 変更した学生種類が職業訓練生ではなく職業訓練生情報が登録済みの場合は登録情報をnullにする
			if (!studentType.equals("職業訓練生") && dao.getNamePESO(id) != null) {
				user.setNamePESO(null);
				user.setSupplyNumber(null);
				user.setAttendanceNumber(null);
				user.setEmploymentInsurance(null);

				// 職業訓練生設定のデータベースへの登録
				dao.updateVocationalTraineeSetting(user);
			}

			// アップデート内容のデータベースへの登録
			dao.addOperationLog(id, "Change Student Infometion");

			// セッションに変更情報を持たせる				
			session.setAttribute("action", "学生情報を変更しました。");
			// トークンの削除
			request.getSession().removeAttribute("csrfToken");

			// 変更した学生種類が職業訓練生かつ未登録の場合は情報登録画面に遷移する
			if (studentType.equals("職業訓練生") && user.getNamePESO() == null) {
				// セッションに職業訓練生未登録情報を持たせる				
				session.setAttribute("vocationalSetting", "unregistered");
				// 職業訓練生登録ページへリダイレクト
				response.sendRedirect(contextPath + "/firstSetting/vocational-trainee-setting.jsp");
				return null;
			} else {

				// エラーがない場合は行為成功表示用JSPへリダイレクト
				response.sendRedirect(contextPath + "/mainMenu/action-success.jsp");
				return null;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "change-student-info.jsp";
		}
	}

}