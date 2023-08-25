package mainMenu.vocationalTraineeDocument;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.CustomLogger;
import tool.Decrypt;
import tool.DecryptionResult;
import tool.EditPDF;

public class AbsenceDueToInjuryOrIllnessAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(AbsenceDueToInjuryOrIllnessAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();

		// セッションの有効期限切れや直接初期設定入力ページにアクセスした場合はエラーとして処理
		if (session.getAttribute("master_key") == null || session.getAttribute("id") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "セッションエラーが発生しました。ログインしてください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login.jsp");
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
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			request.setAttribute(paramName, paramValue);
		}

		// 未入力項目があればエラーを返す
		if (disease == null || reason == null || requestYear == null
				|| requestMonth == null || requestDay == null || startYear == null
				|| startMonth == null || startDay == null || endYear == null || endMonth == null || endDay == null
				|| disease.isEmpty() || reason.isEmpty()
				|| requestYear.isEmpty() || requestMonth.isEmpty()
				|| requestDay.isEmpty() || startYear.isEmpty() || startMonth.isEmpty()
				|| startDay.isEmpty()
				|| endYear.isEmpty() || endMonth.isEmpty()
				|| endDay.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "absence-due-to-injury-or-illness.jsp";
		}

		// 年月日が存在しない日付の場合はエラーにする
		try {
			int checkYear = Integer.parseInt(requestYear);
			int checkMonth = Integer.parseInt(requestMonth);
			int checkDay = Integer.parseInt(requestDay);
			// 日付の妥当性チェック
			LocalDate date = LocalDate.of(checkYear, checkMonth, checkDay);

			checkYear = Integer.parseInt(startYear);
			checkMonth = Integer.parseInt(startMonth);
			checkDay = Integer.parseInt(startDay);
			// 日付の妥当性チェック
			date = LocalDate.of(checkYear, checkMonth, checkDay);

			checkYear = Integer.parseInt(endYear);
			checkMonth = Integer.parseInt(endMonth);
			checkDay = Integer.parseInt(endDay);
			// 日付の妥当性チェック
			date = LocalDate.of(checkYear, checkMonth, checkDay);

		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
		}

		// 開始時刻が終了時刻よりも前かどうかをチェックする
		int checkStartYear = Integer.parseInt(startYear);
		int checkStartMonth = Integer.parseInt(startMonth);
		int checkStartDay = Integer.parseInt(startDay);

		int checkEndYear = Integer.parseInt(endYear);
		int checkEndMonth = Integer.parseInt(endMonth);
		int checkEndDay = Integer.parseInt(endDay);

		// 年度が整合とれるか確認する
		if (checkStartYear > checkEndYear) {
			request.setAttribute("logicalError", "開始年は終了年よりも前でなければなりません。");
			// 月が整合取れるか確認する
		} else if (checkStartYear == checkEndYear && checkStartMonth > checkEndMonth) {
			request.setAttribute("logicalError", "開始月は終了月よりも前でなければなりません。");
			// 日付が整合取れるか確認する
		} else if (checkStartYear == checkEndYear && checkStartMonth == checkEndMonth && checkStartDay > checkEndDay) {
			request.setAttribute("logicalError", "開始日は終了日よりも前でなければなりません。");
		}

		// 文字数が32文字より多い場合はエラーを返す
		if (disease.length() > 32 || reason.length() > 32) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("logicalError") != null || request.getAttribute("dayError") != null
				|| request.getAttribute("valueLongError") != null) {
			return "absence-due-to-injury-or-illness.jsp";
		}

		// 日付フォーマットの定義
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		// 文字列からLocalDateに変換
		LocalDate startDate = LocalDate.parse(startYear + "-" + startMonth + "-" + startDay, formatter);
		LocalDate endDate = LocalDate.parse(endYear + "-" + endMonth + "-" + endDay, formatter);
		// 間の日数を計算
		String daysBetween = String.valueOf(ChronoUnit.DAYS.between(startDate, endDate) + 1);

		// リクエストのデータ全削除
		Enumeration<String> attributeNames = request.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			request.removeAttribute(attributeName);
		}

		try {
			// データベース操作用クラス
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

			// 姓のデータベース空の取り出し
			String reEncryptedLastName = dao.getLastName(id);
			// 最初にデータベースから取り出したデータがnullの場合、初期設定をしていないためログインページにリダイレクト
			if (reEncryptedLastName == null) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}
			String encryptedLastName = CipherUtil.commonDecrypt(reEncryptedLastName);
			String lastName = CipherUtil.decrypt(masterKey, iv, encryptedLastName);
			// 名のデータベースからの取り出し
			String reEncryptedFirstName = dao.getFirstName(id);
			String encryptedFirstName = CipherUtil.commonDecrypt(reEncryptedFirstName);
			String firstName = CipherUtil.decrypt(masterKey, iv, encryptedFirstName);

			String name = lastName + " " + firstName;

			// クラス名のデータベースからの取り出し
			String reEncryptedClassName = dao.getClassName(id);
			String encryptedClassName = CipherUtil.commonDecrypt(reEncryptedClassName);
			String className = CipherUtil.decrypt(masterKey, iv, encryptedClassName);
			// クラス名の末尾に「科」がついていた場合は削除する
			if (className.endsWith("科")) {
				className = className.substring(0, className.length() - 1);
			}

			// 学生種類のデータベースからの取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			String encryptedStudentType = CipherUtil.commonDecrypt(reEncryptedStudentType);
			String studentType = CipherUtil.decrypt(masterKey, iv, encryptedStudentType);
			// もし学生種類が職業訓練生でなければエラーを返す
			if (!studentType.equals("職業訓練生")) {
				request.setAttribute("innerError", "当該書類は職業訓練生のみが発行可能です。");
				return "absence-due-to-injury-or-illness.jsp";
			}

			// 公共職業安定所名のデータベースからの取り出し
			String reEncryptedNamePESO = dao.getNamePESO(id);
			// 最初にデータベースから取り出した職業訓練生のデータがnullの場合、初期設定をしていないためログインページにリダイレクト
			if (reEncryptedNamePESO == null) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}
			String encryptedNamePESO = CipherUtil.commonDecrypt(reEncryptedNamePESO);
			String namePESO = CipherUtil.decrypt(masterKey, iv, encryptedNamePESO);

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
			// Close and save
			editor.close("Absence_Due_to_Injury_or_Illness.pdf", response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "absence-due-to-injury-or-illness.jsp";
		}
	}
}
