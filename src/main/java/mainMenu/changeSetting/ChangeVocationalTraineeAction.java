package mainMenu.changeSetting;

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

public class ChangeVocationalTraineeAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(ChangeVocationalTraineeAction.class);

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

		// 入力された値を変数に格納
		String namePESO = request.getParameter("namePESO");
		String supplyNumber = request.getParameter("supplyNumber");
		String attendanceNumber = request.getParameter("attendanceNumber");
		String employmentInsurance = request.getParameter("employmentInsurance");

		// 入力された値をリクエストに格納	
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			request.setAttribute(paramName, paramValue);
		}

		// 未入力項目があればエラーを返す(雇用保険「無」の場合は支給番号は未記載でOK)
		if (namePESO == null
				|| supplyNumber == null || attendanceNumber == null || employmentInsurance == null || namePESO.isEmpty()
				|| attendanceNumber.isEmpty() || employmentInsurance.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "change-vocational-trainee.jsp";
		}
		// 雇用保険「有」の場合は支給番号を記載する必要あり
		else if (employmentInsurance.equals("有") && supplyNumber.isEmpty()) {
			request.setAttribute("nullError", "雇用保険「有」の場合は支給番号を記載してください。");
			return "change-vocational-trainee.jsp";
		}
		// 雇用保険「無」の場合で支給番号を記載している場合は支給番号を強制的に下記文字列にする。
		else if (employmentInsurance.equals("無")) {
			supplyNumber = "支給番号無し";
		}

		// 出席番号が半角2桁以下でなければエラーを返す
		if (!attendanceNumber.matches("^\\d{1,2}$")) {
			request.setAttribute("attendanceNumberError", "出席番号は半角数字2桁以下で入力してください。");
		}

		// 文字数が32文字より多い場合はエラーを返す。
		if (namePESO.length() > 32 || supplyNumber.length() > 32) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("attendanceNumberError") != null || request.getAttribute("valueLongError") != null) {
			return "change-vocational-trainee.jsp";
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
			dao.addOperationLog(id, "Chage Vocational Trainee Setting");

			// セッションに変更情報を持たせる				
			session.setAttribute("action", "職業訓練生情報を変更しました。");
			// トークンの削除
			request.getSession().removeAttribute("csrfToken");

			// エラーがない場合は行為成功表示用JSPへリダイレクト
			response.sendRedirect(contextPath + "/mainMenu/action-success.jsp");
			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "change-vocational-trainee.jsp";
		}
	}
}