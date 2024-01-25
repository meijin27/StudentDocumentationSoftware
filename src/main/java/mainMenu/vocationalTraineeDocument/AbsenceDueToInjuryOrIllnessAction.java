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

public class AbsenceDueToInjuryOrIllnessAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(AbsenceDueToInjuryOrIllnessAction.class);

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
		String disease = request.getParameter("disease");
		String reason = request.getParameter("reason");
		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String startDay = request.getParameter("startDay");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String endDay = request.getParameter("endDay");
		String requestYear = request.getParameter("requestYear");
		String requestMonth = request.getParameter("requestMonth");
		String requestDay = request.getParameter("requestDay");

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 申請年月日のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "入力必須項目です。");
		}
		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isFourDigit(requestYear) || ValidationUtil.isOneOrTwoDigit(requestMonth, requestDay)) {
			request.setAttribute("requestError", "年月日は正規の桁数で入力してください。");
		}
		// 申請年月日が存在しない日付の場合はエラーにする
		else if (ValidationUtil.validateDate(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "存在しない日付です。");
		}

		// 期間年月日（自）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(startYear, startMonth, startDay)) {
			request.setAttribute("startError", "入力必須項目です。");
		}
		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isFourDigit(startYear) || ValidationUtil.isOneOrTwoDigit(startMonth, startDay)) {
			request.setAttribute("startError", "年月日は正規の桁数で入力してください。");
		}
		// 期間年月日（自）が存在しない日付の場合はエラーにする
		else if (ValidationUtil.validateDate(startYear, startMonth, startDay)) {
			request.setAttribute("startError", "存在しない日付です。");
		}

		// 期間年月日（至）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(endYear, endMonth, endDay)) {
			request.setAttribute("endError", "入力必須項目です。");
		}
		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isFourDigit(endYear) || ValidationUtil.isOneOrTwoDigit(endMonth, endDay)) {
			request.setAttribute("endError", "年月日は正規の桁数で入力してください。");
		}
		// 期間年月日（至）が存在しない日付の場合はエラーにする
		else if (ValidationUtil.validateDate(endYear, endMonth, endDay)) {
			request.setAttribute("endError", "存在しない日付です。");
		}

		// 病状のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(disease)) {
			request.setAttribute("diseaseError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(disease)) {
			request.setAttribute("diseaseError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, disease)) {
			request.setAttribute("diseaseError", "病状は32文字以下で入力してください。");
		}

		// 理由のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(reason)) {
			request.setAttribute("reasonError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(reason)) {
			request.setAttribute("reasonError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, reason)) {
			request.setAttribute("reasonError", "理由は32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "absence-due-to-injury-or-illness.jsp";
		}

		// 年月日入力にnullがないことを確認した後に日付の順序のエラーをチェックする
		// 期間年月日（自）が申請年月日より前の日付の場合はエラーにする
		if (ValidationUtil.isBefore(requestYear, requestMonth, requestDay, startYear, startMonth,
				startDay)) {
			request.setAttribute("startError", "期間年月日（自）は申請年月日より後の日付でなければなりません。");
		}

		if (ValidationUtil.isBefore(startYear, startMonth, startDay, endYear, endMonth, endDay)) {
			request.setAttribute("endError", "期間年月日（至）は期間年月日（自）より後の日付でなければなりません。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "absence-due-to-injury-or-illness.jsp";
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
				return "absence-due-to-injury-or-illness.jsp";
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
			String pdfPath = "/pdf/vocationalTraineePDF/傷病による欠席理由申立書.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));

			// PDFへの記載
			// 名前
			editor.writeText(font, name, 198f, 643f, 75f, "center", 12);
			// 病状
			editor.writeText(font, disease, 357f, 643f, 95f, "center", 12);
			// 理由
			editor.writeText(font, reason, 165f, 597f, 300f, "center", 12);
			// 期間（自）
			editor.writeText(font, startYear, 195f, 551f, 40f, "left", 16);
			editor.writeText(font, startMonth, 263f, 551f, 40f, "left", 16);
			editor.writeText(font, startDay, 310f, 551f, 40f, "left", 16);
			// 期間（至）
			editor.writeText(font, endYear, 195f, 514f, 40f, "left", 16);
			editor.writeText(font, endMonth, 262f, 514f, 40f, "left", 16);
			editor.writeText(font, endDay, 310f, 514f, 40f, "left", 16);
			editor.writeText(font, daysBetween, 380f, 514f, 40f, "left", 16);
			// 公共職業安定所名
			editor.writeText(font, namePESO, 322f, 309f, 50f, "center", 12);
			// 申請日・クラス名・名前
			editor.writeText(font, requestYear, 145f, 249f, 40f, "left", 12);
			editor.writeText(font, requestMonth, 194f, 249f, 40f, "left", 12);
			editor.writeText(font, requestDay, 227f, 249f, 40f, "left", 12);
			editor.writeText(font, className, 145f, 220f, 110f, "center", 12);
			editor.writeText(font, name, 382f, 218f, 105f, "center", 12);

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Absence Due to Injury or Illness");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "傷病による欠席理由申立書");
			// Close and save
			editor.close("Absence_Due_to_Injury_or_Illness.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "absence-due-to-injury-or-illness.jsp";
		}
	}
}
