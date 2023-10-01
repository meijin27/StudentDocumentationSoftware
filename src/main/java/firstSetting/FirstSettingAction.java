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

		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(lastName, firstName, lastNameRuby, firstNameRuby, tel, birthYear, birthMonth,
				birthDay, admissionYear, admissionMonth, admissionDay, studentType, className, studentNumber,
				schoolYear, classNumber)) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "first-setting.jsp";
		}

		// 入力された値をセッションに格納
		RequestAndSessionUtil.storeParametersInSession(request);

		// 「ふりがな」が「ひらがな」で記載されていなければエラーを返す
		if (ValidationUtil.isHiragana(lastNameRuby, firstNameRuby)) {
			request.setAttribute("rubyError", "「ふりがな」は「ひらがな」で入力してください。");
		}

		// 電話番号が半角10~11桁でなければエラーを返す
		if (ValidationUtil.isTenOrElevenDigit(tel)) {
			request.setAttribute("telError", "電話番号は半角数字10桁～11桁で入力してください。");
		}

		// 郵便番号が半角7桁でなければエラーを返す
		if (ValidationUtil.isSevenDigit(postCode)) {
			request.setAttribute("postCodeError", "郵便番号は半角数字7桁で入力してください。");
		}

		// 学籍番号が半角6桁でなければエラーを返す
		if (ValidationUtil.isSixDigit(studentNumber)) {
			request.setAttribute("studentNumberError", "学籍番号は半角数字6桁で入力してください。");
		}

		// 学年・クラスが半角1桁でなければエラーを返す
		if (ValidationUtil.isSingleDigit(schoolYear, classNumber)) {
			request.setAttribute("numberError", "学年・クラスは半角数字1桁で入力してください。");
		}

		// 「同意する」ボタンが押されていない場合はエラーにする。
		if (request.getParameter("agree") == null || request.getParameter("agree").isEmpty()) {
			request.setAttribute("agreeError", "「利用規約及びプライバシーポリシーに同意する」をチェックしない限り登録できません。");
		}

		// 生年月日が存在しない日付の場合はエラーにする
		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		if (ValidationUtil.isFourDigit(birthYear, admissionYear) ||
				ValidationUtil.isOneOrTwoDigit(birthMonth, birthDay, admissionMonth, admissionDay)) {
			request.setAttribute("dayError", "年月日は正規の桁数で入力してください。");
		} else {
			if (ValidationUtil.validateDate(birthYear, birthMonth, birthDay) ||
					ValidationUtil.validateDate(admissionYear, admissionMonth, admissionDay)) {
				request.setAttribute("dayError", "存在しない日付です。");
			}
		}

		// 入力値に特殊文字が入っていないか確認する
		if (ValidationUtil.containsForbiddenChars(firstName, lastName, address, className, studentType)) {
			request.setAttribute("validationError", "使用できない特殊文字が含まれています");
		}

		// 文字数が多い場合はエラーを返す。セレクトボックスの有効範囲画外の場合もエラーを返す。
		if (ValidationUtil.areValidLengths(32, lastName, firstName, lastNameRuby, firstNameRuby)) {
			request.setAttribute("valueLongError", "名前およびふりがなは32文字以下で入力してください。");
		} else if (ValidationUtil.areValidLengths(64, address)) {
			request.setAttribute("valueLongAddressError", "住所は64文字以下で入力してください。");
		} else if (ValidationUtil.areValidLengths(5, studentType)) {
			request.setAttribute("valueLongStudentTypeError", "学生種別は5文字以下で入力してください。");
		} else if (ValidationUtil.areValidLengths(16, className)) {
			request.setAttribute("valueLongClassNameError", "クラス名は16文字以下で入力してください。");
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
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
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
