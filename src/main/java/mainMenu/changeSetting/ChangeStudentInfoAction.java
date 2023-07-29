package mainMenu.changeSetting;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.Decrypt;
import tool.DecryptionResult;

public class ChangeStudentInfoAction extends Action {

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

		// 入力された値をリクエストに格納	
		request.setAttribute("studentType", studentType);
		request.setAttribute("className", className);
		request.setAttribute("studentNumber", studentNumber);
		request.setAttribute("schoolYear", schoolYear);
		request.setAttribute("classNumber", classNumber);

		// 未入力項目があればエラーを返す
		if (studentType == null || className == null || studentNumber == null || schoolYear == null
				|| classNumber == null || studentType.isEmpty() || className.isEmpty() || studentNumber.isEmpty()
				|| schoolYear.isEmpty()
				|| classNumber.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "change-studento-info.jsp";
		}

		// 学籍番号が半角6桁でなければエラーを返す
		if (!studentNumber.matches("^\\d{6}$")) {
			request.setAttribute("studentNumberError", "学籍番号は半角数字6桁で入力してください。");
		}

		// 文字数が64文字より多い場合はエラーを返す
		if (className.length() > 64) {
			request.setAttribute("valueLongError", "64文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("valueLongError") != null || request.getAttribute("studentNumberError") != null) {
			return "change-studento-info.jsp";
		}

		// リクエストのデータ削除
		request.removeAttribute("studentType");
		request.removeAttribute("className");
		request.removeAttribute("studentNumber");
		request.removeAttribute("schoolYear");
		request.removeAttribute("classNumber");

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

		// 共通暗号キーによる暗号化
		String reEncryptedStudentType = CipherUtil.commonEncrypt(encryptedStudentType);
		String reEncryptedClassName = CipherUtil.commonEncrypt(encryptedClassName);
		String reEncryptedStudentNumber = CipherUtil.commonEncrypt(encryptedStudentNumber);
		String reEncryptedSchoolYear = CipherUtil.commonEncrypt(encryptedSchoolYear);
		String reEncryptedClassNumber = CipherUtil.commonEncrypt(encryptedClassNumber);

		// ユーザー情報の作成
		User user = new User();
		user.setId(id);
		user.setStudentType(reEncryptedStudentType);
		user.setClassName(reEncryptedClassName);
		user.setStudentNumber(reEncryptedStudentNumber);
		user.setSchoolYear(reEncryptedSchoolYear);
		user.setClassNumber(reEncryptedClassNumber);

		// データベースへの登録
		dao.updateStudentType(user);
		dao.updateClassName(user);
		dao.updateStudentNumber(user);
		dao.updateSchoolYear(user);
		dao.updateClassNumber(user);
		// アップデート内容のデータベースへの登録
		dao.addOperationLog(id, "Change Student Infometion");
		// 学生情報変更成功画面に遷移
		request.setAttribute("changes", "学生情報を変更しました。");
		return "change-success.jsp";

	}

}