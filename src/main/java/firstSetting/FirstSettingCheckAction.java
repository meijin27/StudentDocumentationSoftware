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
		String studentType = (String) session.getAttribute("studentType");
		String className = (String) session.getAttribute("className");
		String schoolYear = (String) session.getAttribute("schoolYear");
		String classNumber = (String) session.getAttribute("classNumber");
		String studentNumber = (String) session.getAttribute("studentNumber");
		String birthYear = (String) session.getAttribute("birthYear");
		String birthMonth = (String) session.getAttribute("birthMonth");
		String birthDay = (String) session.getAttribute("birthDay");

		// セッションのデータ削除
		session.removeAttribute("lastName");
		session.removeAttribute("firstName");
		session.removeAttribute("studentType");
		session.removeAttribute("className");
		session.removeAttribute("schoolYear");
		session.removeAttribute("classNumber");
		session.removeAttribute("studentNumber");
		session.removeAttribute("birthYear");
		session.removeAttribute("birthMonth");
		session.removeAttribute("birthDay");

		// データベースとの接続用
		UserDAO dao = new UserDAO();
		// セッションから暗号化されたIDの取り出し
		String strId = (String) session.getAttribute("id");
		// IDの復号
		int id = Integer.parseInt(CipherUtil.commonDecrypt(strId));
		// 復号とIDやIV等の取り出しクラスの設定
		Decrypt decrypt = new Decrypt(dao);
		DecryptionResult result = decrypt.getDecryptedMasterKey(session);
		// マスターキーの取り出し			
		String masterKey = result.getMasterKey();
		// ivの取り出し
		String iv = result.getIv();

		// 登録するデータの暗号化
		String encryptedLastName = CipherUtil.encrypt(masterKey, iv, lastName);
		String encryptedFirstName = CipherUtil.encrypt(masterKey, iv, firstName);
		String encryptedStudentType = CipherUtil.encrypt(masterKey, iv, studentType);
		String encryptedClassName = CipherUtil.encrypt(masterKey, iv, className);
		String encryptedSchoolYear = CipherUtil.encrypt(masterKey, iv, schoolYear);
		String encryptedClassNumber = CipherUtil.encrypt(masterKey, iv, classNumber);
		String encryptedStudentNumber = CipherUtil.encrypt(masterKey, iv, studentNumber);
		String encryptedBirthYear = CipherUtil.encrypt(masterKey, iv, birthYear);
		String encryptedBirthMonth = CipherUtil.encrypt(masterKey, iv, birthMonth);
		String encryptedBirthDay = CipherUtil.encrypt(masterKey, iv, birthDay);

		// 共通暗号キーによる暗号化
		String reEncryptedLastName = CipherUtil.commonEncrypt(encryptedLastName);
		String reEncryptedFirstName = CipherUtil.commonEncrypt(encryptedFirstName);
		String reEncryptedStudentType = CipherUtil.commonEncrypt(encryptedStudentType);
		String reEncryptedClassName = CipherUtil.commonEncrypt(encryptedClassName);
		String reEncryptedSchoolYear = CipherUtil.commonEncrypt(encryptedSchoolYear);
		String reEncryptedClassNumber = CipherUtil.commonEncrypt(encryptedClassNumber);
		String reEncryptedStudentNumber = CipherUtil.commonEncrypt(encryptedStudentNumber);
		String reEncryptedBirthYear = CipherUtil.commonEncrypt(encryptedBirthYear);
		String reEncryptedBirthMonth = CipherUtil.commonEncrypt(encryptedBirthMonth);
		String reEncryptedBirthDay = CipherUtil.commonEncrypt(encryptedBirthDay);

		// ユーザー情報の作成
		User user = new User();
		user.setId(id);
		user.setLastName(reEncryptedLastName);
		user.setFirstName(reEncryptedFirstName);
		user.setStudentType(reEncryptedStudentType);
		user.setClassName(reEncryptedClassName);
		user.setSchoolYear(reEncryptedSchoolYear);
		user.setClassNumber(reEncryptedClassNumber);
		user.setStudentNumber(reEncryptedStudentNumber);
		user.setBirthYear(reEncryptedBirthYear);
		user.setBirthMonth(reEncryptedBirthMonth);
		user.setBirthDay(reEncryptedBirthDay);

		// 初期設定のデータベースへの登録
		dao.updateFirstSetting(user);
		// アップデート内容のデータベースへの登録
		dao.addOperationLog(id, "Create First Setting");
		// メインページにリダイレクト
		String contextPath = request.getContextPath();
		response.sendRedirect(contextPath + "/mainMenu/main-menu.jsp");
		return null;

	}

}
