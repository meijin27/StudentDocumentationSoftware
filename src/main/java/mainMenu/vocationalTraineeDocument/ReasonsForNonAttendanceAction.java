package mainMenu.vocationalTraineeDocument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import dao.UserDAO;
import tool.Action;
import tool.CustomLogger;
import tool.Decrypt;
import tool.DecryptionResult;
import tool.EditPDF;
import tool.RequestAndSessionUtil;
import tool.ValidationUtil;

public class ReasonsForNonAttendanceAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(ReasonsForNonAttendanceAction.class);

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
		String relativeName = request.getParameter("relativeName");
		String birthYear = request.getParameter("birthYear");
		String birthMonth = request.getParameter("birthMonth");
		String birthDay = request.getParameter("birthDay");
		String relativeAddress = request.getParameter("relativeAddress");
		String nonAttendanceReason = request.getParameter("nonAttendanceReason");
		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String startDay = request.getParameter("startDay");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String endDay = request.getParameter("endDay");
		String requestYear = request.getParameter("requestYear");
		String requestMonth = request.getParameter("requestMonth");
		String requestDay = request.getParameter("requestDay");

		// 必須項目に未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(relativeName, birthYear, birthMonth, birthDay, relativeAddress, requestYear,
				requestMonth, requestDay, nonAttendanceReason, startYear, startMonth, startDay, endYear, endMonth,
				endDay)) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "certificate-issuance.jsp";
		}

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		if (ValidationUtil.isFourDigit(birthYear, requestYear, startYear, endYear) ||
				ValidationUtil.isOneOrTwoDigit(birthMonth, birthDay, requestMonth, requestDay, startMonth, startDay,
						endMonth, endDay)) {
			request.setAttribute("dayError", "年月日は正規の桁数で入力してください。");
		} else {
			if (ValidationUtil.validateDate(birthYear, birthMonth, birthDay) ||
					ValidationUtil.validateDate(requestYear, requestMonth, requestDay)
					|| ValidationUtil.validateDate(startYear, startMonth, startDay) ||
					ValidationUtil.validateDate(endYear, endMonth, endDay)) {
				request.setAttribute("dayError", "存在しない日付です。");
			} else if (ValidationUtil.isBefore(startYear, startMonth, startDay, endYear, endMonth, endDay)) {
				request.setAttribute("dayError", "期間年月日（自）は期間年月日（至）より前の日付でなければなりません。");
			}
		}

		// 入力値に特殊文字が入っていないか確認する
		if (ValidationUtil.containsForbiddenChars(relativeName, relativeAddress, nonAttendanceReason)) {
			request.setAttribute("validationError", "使用できない特殊文字が含まれています");
		}

		// 文字数が多い場合はエラーを返す。
		if (ValidationUtil.areValidLengths(32, relativeName)) {
			request.setAttribute("valueLongError", "名前は32文字以下で入力してください。");
		}

		if (ValidationUtil.areValidLengths(64, relativeAddress)) {
			request.setAttribute("valueLongAddressError", "住所は64文字以下で入力してください。");
		}

		// セレクトボックスの有効範囲画外の場合もエラーを返す。
		if (ValidationUtil.areValidLengths(5, nonAttendanceReason)) {
			request.setAttribute("valueLongError", "欠席理由は5文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "reasons-for-non-attendance.jsp";
		}

		// 日付フォーマットの定義
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		// 文字列からLocalDateに変換
		LocalDate startDate = LocalDate.parse(startYear + "-" + startMonth + "-" + startDay, formatter);
		LocalDate endDate = LocalDate.parse(endYear + "-" + endMonth + "-" + endDay, formatter);
		// 間の日数を計算
		String daysBetween = String.valueOf(ChronoUnit.DAYS.between(startDate, endDate) + 1);

		try {
			// データベース操作用クラス
			UserDAO dao = new UserDAO();
			// 復号とIDやIV等の取り出しクラスの設定
			Decrypt decrypt = new Decrypt(dao);
			DecryptionResult result = decrypt.getDecryptedMasterKey(session);
			// IDの取り出し
			String id = result.getId();

			// 姓のデータベース空の取り出し
			String reEncryptedLastName = dao.getLastName(id);
			String lastName = decrypt.getDecryptedDate(result, reEncryptedLastName);

			// 名のデータベースからの取り出し
			String reEncryptedFirstName = dao.getFirstName(id);
			String firstName = decrypt.getDecryptedDate(result, reEncryptedFirstName);

			// クラス名のデータベースからの取り出し
			String reEncryptedClassName = dao.getClassName(id);
			String className = decrypt.getDecryptedDate(result, reEncryptedClassName);

			// 学生種類のデータベースからの取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			String studentType = decrypt.getDecryptedDate(result, reEncryptedStudentType);

			// 公共職業安定所名のデータベースからの取り出し
			String reEncryptedNamePESO = dao.getNamePESO(id);
			String namePESO = decrypt.getDecryptedDate(result, reEncryptedNamePESO);

			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(lastName, firstName, className, studentType)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// もし学生種類が職業訓練生でなければエラーを返す
			if (!studentType.equals("職業訓練生")) {
				request.setAttribute("innerError", "当該書類は職業訓練生のみが発行可能です。");
				return "reasons-for-non-attendance.jsp";
			}

			// データベースから取り出した職業訓練生データにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(namePESO)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// クラス名の末尾に「科」がついていた場合は削除する
			if (className.endsWith("科")) {
				className = className.substring(0, className.length() - 1);
			}

			// 姓名を結合する			
			String name = lastName + " " + firstName;

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/vocationalTraineePDF/欠席理由申立書.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));

			// PDFへの記載
			// 親族氏名・親族住所・親族生年月日			
			editor.writeText(font, relativeName, 95f, 656f, 130f, "center", 12);
			editor.writeText(font, relativeAddress, 270f, 658f, 220f, "left", 12);
			editor.writeText(font, birthYear, 135f, 620f, 40f, "left", 12);
			editor.writeText(font, birthMonth, 180f, 620f, 40f, "left", 12);
			editor.writeText(font, birthDay, 215f, 620f, 40f, "left", 12);
			// 期間（自）
			editor.writeText(font, startYear, 130f, 477f, 40f, "left", 12);
			editor.writeText(font, startMonth, 190f, 477f, 40f, "left", 12);
			editor.writeText(font, startDay, 237f, 477f, 40f, "left", 12);
			// 期間（至）
			editor.writeText(font, endYear, 130f, 440f, 40f, "left", 12);
			editor.writeText(font, endMonth, 190f, 440f, 40f, "left", 12);
			editor.writeText(font, endDay, 237f, 440f, 40f, "left", 12);
			editor.writeText(font, daysBetween, 310f, 440f, 40f, "left", 12);
			// 公共職業安定所名
			editor.writeText(font, namePESO, 362f, 257f, 50f, "center", 12);
			// 申請年月日・クラス名・名前
			editor.writeText(font, requestYear, 100f, 207f, 40f, "left", 12);
			editor.writeText(font, requestMonth, 155f, 207f, 40f, "left", 12);
			editor.writeText(font, requestDay, 192f, 207f, 40f, "left", 12);
			editor.writeText(font, className, 110f, 169f, 130f, "center", 12);
			editor.writeText(font, name, 377f, 169f, 140f, "center", 12);
			// 欠席理由
			switch (nonAttendanceReason) {
			case "看護":
				editor.drawEllipse(125f, 543f, 40f, 20f);
				break;
			case "危篤":
				editor.drawEllipse(191f, 543f, 40f, 20f);
				break;
			case "結婚式":
				editor.drawEllipse(252f, 543f, 50f, 20f);
				break;
			case "葬儀":
				editor.drawEllipse(320f, 543f, 40f, 20f);
				break;
			case "命日の法事":
				editor.drawEllipse(385f, 543f, 80f, 20f);
				break;
			case "入園式":
				editor.drawEllipse(121f, 507f, 50f, 20f);
				break;
			case "入学式":
				editor.drawEllipse(186f, 507f, 50f, 20f);
				break;
			case "卒園式":
				editor.drawEllipse(250f, 507f, 50f, 20f);
				break;
			case "卒業式":
				editor.drawEllipse(317f, 507f, 50f, 20f);
				break;
			default:
				break;
			}

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Reasons for Non Attendance");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "欠席理由申立書");
			// Close and save
			editor.close("Reasons_for_Non_Attendance.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "reasons-for-non-attendance.jsp";
		}
	}
}
