package firstSetting;

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
import tool.RequestAndSessionUtil;
import tool.ValidationUtil;

public class FirstSettingAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(FirstSettingAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークン及びログイン状態の確認
		if (RequestAndSessionUtil.validateSession(request, response, "master_key", "id", "firstSetting")) {
			// ログイン状態が不正ならば処理を終了
			return null;
		}

		// 入力された値を変数に格納
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String lastNameRuby = request.getParameter("lastNameRuby");
		String firstNameRuby = request.getParameter("firstNameRuby");
		String tel = request.getParameter("tel");
		String postCode = request.getParameter("postCode");
		String address = request.getParameter("address");
		String birthYear = request.getParameter("birthYear");
		String birthMonth = request.getParameter("birthMonth");
		String birthDay = request.getParameter("birthDay");
		String admissionYear = request.getParameter("admissionYear");
		String admissionMonth = request.getParameter("admissionMonth");
		String admissionDay = request.getParameter("admissionDay");
		String studentType = request.getParameter("studentType");
		String className = request.getParameter("className");
		String studentNumber = request.getParameter("studentNumber");
		String schoolYear = request.getParameter("schoolYear");
		String classNumber = request.getParameter("classNumber");
		String agree = request.getParameter("agree");

		// 入力された値をセッションに格納
		RequestAndSessionUtil.storeParametersInSession(request);

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

		// ふりがな（姓）のエラー処理
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

		// ふりがな（名）のエラー処理
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

		// 電話番号のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(tel)) {
			request.setAttribute("telError", "入力必須項目です。");
		}
		// 電話番号が半角10~11桁でなければエラーを返す
		else if (ValidationUtil.isTenOrElevenDigit(tel)) {
			request.setAttribute("telError", "電話番号は半角数字10桁～11桁で入力してください。");
		}

		// 郵便番号のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(postCode)) {
			request.setAttribute("postCodeError", "入力必須項目です。");
		}
		// 郵便番号が半角7桁でなければエラーを返す
		else if (ValidationUtil.isSevenDigit(postCode)) {
			request.setAttribute("postCodeError", "郵便番号は半角数字7桁で入力してください。");
		}

		// 住所のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(address)) {
			request.setAttribute("addressError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(address)) {
			request.setAttribute("addressError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(64, address)) {
			request.setAttribute("addressError", "住所は64文字以下で入力してください。");
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

		// 「同意する」ボタンが押されていない場合はエラーにする。
		if (ValidationUtil.isNullOrEmpty(agree)) {
			request.setAttribute("agreeError", "「利用規約及びプライバシーポリシーに同意する」をチェックしない限り登録できません。");
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

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "first-setting.jsp";
		}

		try {
			// データベース操作用クラス
			UserDAO dao = new UserDAO();
			// 復号とIDやIV等の取り出しクラスの設定
			Decrypt decrypt = new Decrypt(dao);
			DecryptionResult result = decrypt.getDecryptedMasterKey(session);
			// IDの取り出し
			String id = result.getId();

			// 秘密の質問のデータベースからの取り出し
			String reEncryptedSecretQuestion = dao.getSecretQuestion(id);
			// データベースから取り出したデータがnullの場合、初期設定をしていないためログインページにリダイレクト
			if (reEncryptedSecretQuestion == null) {
				session.setAttribute("otherError", "秘密の質問の設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "first-setting.jsp";
		}

		// セッションに初期設定未チェック情報を持たせる				
		session.setAttribute("firstSettingCheck", "unchecked");
		// トークンの削除
		request.getSession().removeAttribute("csrfToken");

		// エラーがない場合は確認画面へリダイレクト
		// 初期設定確認ページへリダイレクト
		response.sendRedirect(contextPath + "/firstSetting/first-setting-check.jsp");
		return null;

	}

}
