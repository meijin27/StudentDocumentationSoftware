package firstSetting;

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

public class VocationalTraineeSettingCheckAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(VocationalTraineeSettingCheckAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// セッションからトークンを取得
		String sessionToken = (String) session.getAttribute("csrfToken");
		// リクエストパラメータからトークンを取得
		String requestToken = (String) session.getAttribute("csrfToken");
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークンが一致しない、またはセッションの有効期限切れの場合はエラーとして処理
		if (session.getAttribute("master_key") == null || session.getAttribute("id") == null || sessionToken == null
				|| requestToken == null || !sessionToken.equals(requestToken)) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "セッションエラーが発生しました。ログインしてください。");
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// セッションからデータの取り出し
		String namePESO = (String) session.getAttribute("namePESO");
		String supplyNumber = (String) session.getAttribute("supplyNumber");
		String attendanceNumber = (String) session.getAttribute("attendanceNumber");
		String employmentInsurance = (String) session.getAttribute("employmentInsurance");

		String goBack = request.getParameter("goBack");

		// 「戻る」ボタンが押された場合は入力フォームへ戻る
		if (goBack != null) {
			return "vocational-trainee-setting.jsp";
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

			// 姓のデータベースからの取り出し
			String reEncryptedLastName = dao.getLastName(id);
			// データベースから取り出したデータがnullの場合、初期設定をしていないためログインページにリダイレクト
			if (reEncryptedLastName == null) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

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

			// 職業訓練生登録情報のセッションからの削除
			request.getSession().removeAttribute("namePESO");
			request.getSession().removeAttribute("supplyNumber");
			request.getSession().removeAttribute("attendanceNumber");
			request.getSession().removeAttribute("employmentInsurance");
			// 職業訓練生未登録情報及び職業訓練生未チェック情報のセッションからの削除
			request.getSession().removeAttribute("vocationalSetting");
			request.getSession().removeAttribute("vocationalSettingCheck");
			// トークンの削除
			request.getSession().removeAttribute("csrfToken");

			// メインページにリダイレクト
			response.sendRedirect(contextPath + "/mainMenu/main-menu.jsp");
			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "vocational-trainee-setting.jsp";
		}
	}

}
