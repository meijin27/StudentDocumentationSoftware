package firstSetting;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class VocationalTraineeSettingAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションの作成
		HttpSession session = request.getSession();

		// セッションの有効期限切れの場合はエラーとして処理
		if (session.getAttribute("master_key") == null || session.getAttribute("id") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "セッションエラーが発生しました。ログインしてください。");
			String contextPath = request.getContextPath();
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
			return "vocational-trainee-setting.jsp";
		}
		// 雇用保険「有」の場合は支給番号を記載する必要あり
		else if (employmentInsurance.equals("有") && supplyNumber.isEmpty()) {
			request.setAttribute("nullError", "雇用保険「有」の場合は支給番号を記載してください。");
			return "vocational-trainee-setting.jsp";
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
			return "vocational-trainee-setting.jsp";
		}

		// エラーがない場合は確認画面に進む
		return "vocational-trainee-setting-check.jsp";

	}

}
