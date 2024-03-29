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

public class ChangeNameDateofBirthAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(ChangeNameDateofBirthAction.class);

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
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String lastNameRuby = request.getParameter("lastNameRuby");
		String firstNameRuby = request.getParameter("firstNameRuby");
		String birthYear = request.getParameter("birthYear");
		String birthMonth = request.getParameter("birthMonth");
		String birthDay = request.getParameter("birthDay");

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 姓のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(lastName)) {
			request.setAttribute("lastNameError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(lastName)) {
			request.setAttribute("lastNameError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, lastName)) {
			request.setAttribute("lastNameError", "32文字以下で入力してください。");
		}

		// 名のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(firstName)) {
			request.setAttribute("firstNameError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(firstName)) {
			request.setAttribute("firstNameError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, firstName)) {
			request.setAttribute("firstNameError", "32文字以下で入力してください。");
		}

		// ふりがなのエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(lastNameRuby)) {
			request.setAttribute("lastNameRubyError", "入力必須項目です。");
		}
		// 「ふりがな」が「ひらがな」で記載されていなければエラーを返す
		else if (ValidationUtil.isHiragana(lastNameRuby)) {
			request.setAttribute("lastNameRubyError", "「ふりがな」は「ひらがな」で入力してください。");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, lastNameRuby)) {
			request.setAttribute("lastNameRubyError", "32文字以下で入力してください。");
		}

		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(firstNameRuby)) {
			request.setAttribute("firstNameRubyError", "入力必須項目です。");
		}
		// 「ふりがな」が「ひらがな」で記載されていなければエラーを返す
		else if (ValidationUtil.isHiragana(firstNameRuby)) {
			request.setAttribute("firstNameRubyError", "「ふりがな」は「ひらがな」で入力してください。");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, firstNameRuby)) {
			request.setAttribute("firstNameRubyError", "32文字以下で入力してください。");
		}

		// 生年月日のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(birthYear, birthMonth, birthDay)) {
			request.setAttribute("birthError", "入力必須項目です。");
		}
		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isFourDigit(birthYear) ||
				ValidationUtil.isOneOrTwoDigit(birthMonth, birthDay)) {
			request.setAttribute("birthError", "年月日は正規の桁数で入力してください。");
		}
		// 生年月日が存在しない日付の場合はエラーにする
		else if (ValidationUtil.validateDate(birthYear, birthMonth, birthDay)) {
			request.setAttribute("birthError", "存在しない日付です。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "change-name-date-of-birth.jsp";
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
			String encryptedLastName = CipherUtil.encrypt(masterKey, iv, lastName);
			String encryptedFirstName = CipherUtil.encrypt(masterKey, iv, firstName);
			String encryptedLastNameRuby = CipherUtil.encrypt(masterKey, iv, lastNameRuby);
			String encryptedFirstNameRuby = CipherUtil.encrypt(masterKey, iv, firstNameRuby);
			String encryptedBirthYear = CipherUtil.encrypt(masterKey, iv, birthYear);
			String encryptedBirthMonth = CipherUtil.encrypt(masterKey, iv, birthMonth);
			String encryptedBirthDay = CipherUtil.encrypt(masterKey, iv, birthDay);

			// 共通暗号キーによる暗号化
			String reEncryptedLastName = CipherUtil.commonEncrypt(encryptedLastName);
			String reEncryptedFirstName = CipherUtil.commonEncrypt(encryptedFirstName);
			String reEncryptedLastNameRuby = CipherUtil.commonEncrypt(encryptedLastNameRuby);
			String reEncryptedFirstNameRuby = CipherUtil.commonEncrypt(encryptedFirstNameRuby);
			String reEncryptedBirthYear = CipherUtil.commonEncrypt(encryptedBirthYear);
			String reEncryptedBirthMonth = CipherUtil.commonEncrypt(encryptedBirthMonth);
			String reEncryptedBirthDay = CipherUtil.commonEncrypt(encryptedBirthDay);

			// ユーザー情報の作成
			User user = new User();
			user.setId(id);
			user.setLastName(reEncryptedLastName);
			user.setFirstName(reEncryptedFirstName);
			user.setLastNameRuby(reEncryptedLastNameRuby);
			user.setFirstNameRuby(reEncryptedFirstNameRuby);
			user.setBirthYear(reEncryptedBirthYear);
			user.setBirthMonth(reEncryptedBirthMonth);
			user.setBirthDay(reEncryptedBirthDay);
			// データベースへの登録
			dao.updateLastName(user);
			dao.updateFirstName(user);
			dao.updateLastNameRuby(user);
			dao.updateFirstNameRuby(user);
			dao.updateBirthYear(user);
			dao.updateBirthMonth(user);
			dao.updateBirthDay(user);
			// アップデート内容のデータベースへの登録
			dao.addOperationLog(id, "Change Name & Date of Birth");

			// セッションに変更情報を持たせる				
			session.setAttribute("action", "名前と生年月日を変更しました。");
			// トークンの削除
			request.getSession().removeAttribute("csrfToken");

			// エラーがない場合は行為成功表示用JSPへリダイレクト
			response.sendRedirect(contextPath + "/mainMenu/action-success.jsp");
			return null;

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "change-name-date-of-birth.jsp";
		}
	}

}