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
			response.sendRedirect(contextPath + "/login/login-in.jsp");
			return null;
		}

		// 入力された値を変数に格納
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String studentType = request.getParameter("studentType");
		String className = request.getParameter("className");
		String studentNumber = request.getParameter("studentNumber");
		String birthYear = request.getParameter("birthYear");
		String birthMonth = request.getParameter("birthMonth");
		String birthDay = request.getParameter("birthDay");

		// 入力された値をセッションに格納
		session.setAttribute("lastName", lastName);
		session.setAttribute("firstName", firstName);
		session.setAttribute("studentType", studentType);
		session.setAttribute("className", className);
		session.setAttribute("studentNumber", studentNumber);
		session.setAttribute("birthYear", birthYear);
		session.setAttribute("birthMonth", birthMonth);
		session.setAttribute("birthDay", birthDay);

		// 学籍番号が数字にできない場合はエラーを返す
		try {
			int number = Integer.parseInt(studentNumber);
		} catch (NumberFormatException e) {
			request.setAttribute("studentNumberError", "学籍番号は数字で入力してください。");
		}

		// 「同意する」ボタンが押されていない場合はエラーにする。
		if (request.getParameter("agree") == null) {
			request.setAttribute("agreeError", "「同意する」をチェックしない限り登録できません。");
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

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("studentNumberError") != null || request.getAttribute("agreeError") != null
				|| request.getAttribute("birthDayError") != null) {
			return "first-setting.jsp";
		}

		// エラーがない場合は確認画面に進む
		return "first-setting-confirmation.jsp";

	}

}
