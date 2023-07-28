package firstSetting;

import java.time.DateTimeException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class FirstSettingAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションの作成
		HttpSession session = request.getSession();

		// セッションの有効期限切れの場合はエラーとして処理
		if (session.getAttribute("master_key") == null || session.getAttribute("id") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "エラーが発生しました。やり直してください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// 入力された値を変数に格納
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String tel = request.getParameter("tel");
		String postCode = request.getParameter("postCode");
		String address = request.getParameter("address");
		String birthYear = request.getParameter("birthYear");
		String birthMonth = request.getParameter("birthMonth");
		String birthDay = request.getParameter("birthDay");
		String studentType = request.getParameter("studentType");
		String className = request.getParameter("className");
		String studentNumber = request.getParameter("studentNumber");
		String schoolYear = request.getParameter("schoolYear");
		String classNumber = request.getParameter("classNumber");

		// 未入力項目があればエラーを返す
		if (lastName == null || firstName == null || tel == null || postCode == null || address == null ||
				birthYear == null || birthMonth == null || birthDay == null || studentType == null || className == null
				|| studentNumber == null || schoolYear == null || classNumber == null) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "first-setting.jsp";
		}

		// 入力された値をセッションに格納
		session.setAttribute("lastName", lastName);
		session.setAttribute("firstName", firstName);
		session.setAttribute("tel", tel);
		session.setAttribute("postCode", postCode);
		session.setAttribute("address", address);
		session.setAttribute("birthYear", birthYear);
		session.setAttribute("birthMonth", birthMonth);
		session.setAttribute("birthDay", birthDay);
		session.setAttribute("studentType", studentType);
		session.setAttribute("className", className);
		session.setAttribute("studentNumber", studentNumber);
		session.setAttribute("schoolYear", schoolYear);
		session.setAttribute("classNumber", classNumber);

		// 電話番号が半角10~11桁でなければエラーを返す
		if (!tel.matches("^\\d{10,11}$")) {
			request.setAttribute("telError", "電話番号は半角数字10桁～11桁で入力してください。");
		}

		// 郵便番号が半角7桁でなければエラーを返す
		if (!postCode.matches("^\\d{7}$")) {
			request.setAttribute("postCodeError", "郵便番号は半角数字7桁で入力してください。");
		}

		// 学籍番号が半角6桁でなければエラーを返す
		if (!studentNumber.matches("^\\d{6}$")) {
			request.setAttribute("studentNumberError", "学籍番号は半角数字6桁で入力してください。");
		}

		// 「同意する」ボタンが押されていない場合はエラーにする。
		if (request.getParameter("agree") == null) {
			request.setAttribute("agreeError", "「利用規約及びプライバシーポリシーに同意する」をチェックしない限り登録できません。");
		}

		// 生年月日が存在しない日付の場合はエラーにする
		try {
			int year = Integer.parseInt(birthYear);
			int month = Integer.parseInt(birthMonth);
			int day = Integer.parseInt(birthDay);

			// 日付の妥当性チェック
			LocalDate birthDate = LocalDate.of(year, month, day);
		} catch (DateTimeException e) {
			request.setAttribute("birthDayError", "存在しない日付です。");
		}

		// 文字数が32文字より多い場合はエラーを返す
		if (lastName.length() > 64 || firstName.length() > 64 || className.length() > 64 || address.length() > 64) {
			request.setAttribute("valueLongError", "64文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("studentNumberError") != null || request.getAttribute("agreeError") != null
				|| request.getAttribute("birthDayError") != null || request.getAttribute("valueLongError") != null
				|| request.getAttribute("telError") != null || request.getAttribute("postCodeError") != null) {
			return "first-setting.jsp";
		}

		// エラーがない場合は確認画面に進む
		return "first-setting-check.jsp";

	}

}
