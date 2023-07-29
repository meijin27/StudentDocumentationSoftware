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

public class VocationalTraineeSettingCheckAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		// セッションの有効期限切れの場合はエラーとして処理
		if (session.getAttribute("master_key") == null || session.getAttribute("id") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "セッションエラーが発生しました。ログインしてください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// セッションからデータの取り出し
		String namePESO = (String) session.getAttribute("namePESO");
		String supplyNumber = (String) session.getAttribute("supplyNumber");
		String attendanceNumber = (String) session.getAttribute("attendanceNumber");
		String employmentInsurance = (String) session.getAttribute("employmentInsurance");

		// セッションのデータ削除
		session.removeAttribute("namePESO");
		session.removeAttribute("supplyNumber");
		session.removeAttribute("attendanceNumber");
		session.removeAttribute("employmentInsurance");

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
		String encryptedNamePESO = CipherUtil.encrypt(masterKey, iv, namePESO);
		String encryptedSupplyNumber = CipherUtil.encrypt(masterKey, iv, supplyNumber);
		String encryptedAttendanceNumber = CipherUtil.encrypt(masterKey, iv, attendanceNumber);
		String encryptedEmploymentInsurance = CipherUtil.encrypt(masterKey, iv, employmentInsurance);

		// 共通暗号キーによる暗号化
		String reEncryptedNamePESO = CipherUtil.commonEncrypt(encryptedNamePESO);
		String reEncryptedSupplyNumber = CipherUtil.commonEncrypt(encryptedSupplyNumber);
		String reEncryptedAttendanceNumber = CipherUtil.commonEncrypt(encryptedAttendanceNumber);
		String reEncryptedEmploymentInsurance = CipherUtil.commonEncrypt(encryptedEmploymentInsurance);

		// ユーザー情報の作成
		User user = new User();
		user.setId(id);
		user.setNamePESO(reEncryptedNamePESO);
		user.setSupplyNumber(reEncryptedSupplyNumber);
		user.setAttendanceNumber(reEncryptedAttendanceNumber);
		user.setEmploymentInsurance(reEncryptedEmploymentInsurance);

		// 職業訓練生設定のデータベースへの登録
		dao.updateVocationalTraineeSetting(user);
		// アップデート内容のデータベースへの登録
		dao.addOperationLog(id, "Create Vocational Trainee Setting");

		String contextPath = request.getContextPath();
		response.sendRedirect(contextPath + "/mainMenu/main-menu.jsp");
		return null;
	}

}
