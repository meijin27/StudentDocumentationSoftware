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

public class ChangeVocationalTraineeAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(ChangeVocationalTraineeAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークン及びログイン状態の確認
		if (!RequestAndSessionUtil.validateSession(request, response, "master_key", "id")) {
			// ログイン状態が不正ならば処理を終了
			return null;
		}

		// 入力された値を変数に格納
		String namePESO = request.getParameter("namePESO");
		String supplyNumber = request.getParameter("supplyNumber");
		String attendanceNumber = request.getParameter("attendanceNumber");
		String employmentInsurance = request.getParameter("employmentInsurance");

		// 未入力項目があればエラーを返す(雇用保険「無」の場合は支給番号は未記載でOK)
		if (ValidationUtil.isNullOrEmpty(namePESO, attendanceNumber, employmentInsurance)) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "vocational-trainee-setting.jsp";
		}

		// 入力された値をリクエストに格納
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 雇用保険「有」の場合は支給番号を記載する必要あり
		if (employmentInsurance.equals("有") && ValidationUtil.isNullOrEmpty(supplyNumber)) {
			request.setAttribute("nullError", "雇用保険「有」の場合は支給番号を記載してください。");
			return "vocational-trainee-setting.jsp";
		}
		// 雇用保険「無」の場合で支給番号を記載している場合は支給番号を強制的に下記文字列にする。
		else if (employmentInsurance.equals("無")) {
			supplyNumber = "支給番号無し";
			session.setAttribute("supplyNumber", supplyNumber);
		}
		// 雇用保険が「有」「無」以外の場合はエラーを返す
		else if (!(employmentInsurance.equals("有") || employmentInsurance.equals("無"))) {
			request.setAttribute("employmentInsuranceError", "雇用保険は「有」「無」から選択してください");
		}

		// 出席番号が半角2桁以下でなければエラーを返す
		if (!ValidationUtil.isOneOrTwoDigit(attendanceNumber)) {
			request.setAttribute("attendanceNumberError", "出席番号は半角数字2桁以下で入力してください。");
		}

		// 文字数が32文字より多い場合はエラーを返す。
		if (!ValidationUtil.areValidLengths(32, namePESO, supplyNumber)) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// 入力値に特殊文字が入っていないか確認する
		if (ValidationUtil.containsForbiddenChars(namePESO, supplyNumber)) {
			request.setAttribute("validationError", "使用できない特殊文字が含まれています");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "change-vocational-trainee.jsp";
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

			// 学生種類のデータベースからの取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			String encryptedStudentType = CipherUtil.commonDecrypt(reEncryptedStudentType);
			String studentType = CipherUtil.decrypt(masterKey, iv, encryptedStudentType);
			// もし学生種類が職業訓練生でなければエラーを返す
			if (!studentType.equals("職業訓練生")) {
				request.setAttribute("innerError", "学生種別が職業訓練生の場合のみ変更可能です。");
				return "change-vocational-trainee.jsp";
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