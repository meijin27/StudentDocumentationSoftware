package mainMenu.vocationalTraineeDocument;

import java.time.DateTimeException;
import java.time.LocalDate;
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

public class InterviewCertificateAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(InterviewCertificateAction.class);

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
		String jobSearch = request.getParameter("jobSearch");
		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String startDay = request.getParameter("startDay");
		String startForenoonOrMidday = request.getParameter("startForenoonOrMidday");
		String startHour = request.getParameter("startHour");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String endDay = request.getParameter("endDay");
		String endForenoonOrMidday = request.getParameter("endForenoonOrMidday");
		String endHour = request.getParameter("endHour");

		// 入力された値をリクエストに格納		
		request.setAttribute("jobSearch", jobSearch);
		request.setAttribute("startYear", startYear);
		request.setAttribute("startMonth", startMonth);
		request.setAttribute("startDay", startDay);
		request.setAttribute("startForenoonOrMidday", startForenoonOrMidday);
		request.setAttribute("startHour", startHour);
		request.setAttribute("endYear", endYear);
		request.setAttribute("endMonth", endMonth);
		request.setAttribute("endDay", endDay);
		request.setAttribute("endForenoonOrMidday", endForenoonOrMidday);
		request.setAttribute("endHour", endHour);

		// 未入力項目があればエラーを返す
		if (jobSearch == null || startYear == null || startMonth == null || startDay == null
				|| startForenoonOrMidday == null || startHour == null
				|| endYear == null || endMonth == null || endDay == null || endForenoonOrMidday == null
				|| endHour == null || jobSearch.isEmpty() || startYear.isEmpty() || startMonth.isEmpty()
				|| startDay.isEmpty()
				|| startForenoonOrMidday.isEmpty() || startHour.isEmpty() || endYear.isEmpty() || endMonth.isEmpty()
				|| endDay.isEmpty() || endForenoonOrMidday.isEmpty() || endHour.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "interview-certificate.jsp";
		}

		// 年月日が存在しない日付の場合はエラーにする
		try {
			int checkYear = Integer.parseInt(startYear) + 2018;
			int checkMonth = Integer.parseInt(startMonth);
			int checkDay = Integer.parseInt(startDay);

			// 日付の妥当性チェック
			LocalDate Date = LocalDate.of(checkYear, checkMonth, checkDay);

			checkYear = Integer.parseInt(endYear) + 2018;
			checkMonth = Integer.parseInt(endMonth);
			checkDay = Integer.parseInt(endDay);

			Date = LocalDate.of(checkYear, checkMonth, checkDay);

		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
		}

		// 開始時刻が終了時刻よりも前かどうかをチェックする
		int checkStartYear = Integer.parseInt(startYear);
		int checkStartMonth = Integer.parseInt(startMonth);
		int checkStartDay = Integer.parseInt(startDay);
		int checkStartHour = Integer.parseInt(startHour);

		int checkEndYear = Integer.parseInt(endYear);
		int checkEndMonth = Integer.parseInt(endMonth);
		int checkEndDay = Integer.parseInt(endDay);
		int checkEndHour = Integer.parseInt(endHour);

		// 年度が整合とれるか確認する
		if (checkStartYear > checkEndYear) {
			request.setAttribute("logicalError", "開始年は終了年よりも前でなければなりません。");
			// 月が整合取れるか確認する
		} else if (checkStartYear == checkEndYear && checkStartMonth > checkEndMonth) {
			request.setAttribute("logicalError", "開始月は終了月よりも前でなければなりません。");
			// 日付が整合取れるか確認する
		} else if (checkStartYear == checkEndYear && checkStartMonth == checkEndMonth && checkStartDay > checkEndDay) {
			request.setAttribute("logicalError", "開始日は終了日よりも前でなければなりません。");
			// 時刻が整合取れるか確認する
		} else if (checkStartYear == checkEndYear && checkStartMonth == checkEndMonth && checkStartDay == checkEndDay) {
			if (startForenoonOrMidday.equals("午後") && endForenoonOrMidday.equals("午前")) {
				request.setAttribute("logicalError", "開始時刻は終了時刻よりも前でなければなりません。");
			} else if (startForenoonOrMidday.equals(endForenoonOrMidday) && checkStartHour > checkEndHour) {
				request.setAttribute("logicalError", "開始時刻は終了時刻よりも前でなければなりません。");
			}
		}

		// 文字数が64文字より多い場合はエラーを返す
		if (jobSearch.length() > 32) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("logicalError") != null || request.getAttribute("dayError") != null
				|| request.getAttribute("valueLongError") != null) {
			return "interview-certificate.jsp";
		}

		// リクエストのデータ削除
		request.removeAttribute("jobSearch");
		request.removeAttribute("startYear");
		request.removeAttribute("startMonth");
		request.removeAttribute("startDay");
		request.removeAttribute("startForenoonOrMidday");
		request.removeAttribute("startHour");
		request.removeAttribute("endYear");
		request.removeAttribute("endMonth");
		request.removeAttribute("endDay");
		request.removeAttribute("endForenoonOrMidday");
		request.removeAttribute("endHour");

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

			// 学生種類のデータベースからの取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			String encryptedStudentType = CipherUtil.commonDecrypt(reEncryptedStudentType);
			String studentType = CipherUtil.decrypt(masterKey, iv, encryptedStudentType);
			// もし学生種類が職業訓練生でなければエラーを返す
			if (!studentType.equals("職業訓練生")) {
				request.setAttribute("innerError", "当該書類は職業訓練生のみが発行可能です。");
				return "interview-certificate.jsp";
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
			String pdfPath = "/pdf/vocationalTraineePDF/面接証明書.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));

			editor.writeText(font, className, 230f, 645f, 230f, "center", 12);
			editor.writeText(font, name, 230f, 600f, 230f, "center", 12);
			editor.writeText(font, jobSearch, 230f, 560f, 230f, "center", 12);
			editor.writeText(font, startYear, 290f, 508f, 70f, "left", 12);
			editor.writeText(font, startMonth, 323f, 508f, 70f, "left", 12);
			editor.writeText(font, startDay, 355f, 508f, 70f, "left", 12);
			editor.writeText(font, startHour, 430f, 508f, 70f, "left", 12);

			editor.writeText(font, endYear, 290f, 436f, 70f, "left", 12);
			editor.writeText(font, endMonth, 323f, 436f, 70f, "left", 12);
			editor.writeText(font, endDay, 355f, 436f, 70f, "left", 12);
			editor.writeText(font, endHour, 430f, 436f, 70f, "left", 12);

			if (startForenoonOrMidday.equals("午前")) {
				editor.writeText(font, "〇", 396f, 518f, 50f, "left", 32);
			} else {
				editor.writeText(font, "〇", 396f, 483f, 50f, "left", 32);
			}

			if (endForenoonOrMidday.equals("午前")) {
				editor.writeText(font, "〇", 396f, 446f, 50f, "left", 32);
			} else {
				editor.writeText(font, "〇", 396f, 410f, 50f, "left", 32);
			}

			editor.writeText(font, namePESO, 150f, 285f, 70f, "center", 12);

			// Close and save
			editor.close("面接証明書.pdf");
			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Interview Certificate");
			// PDF作成成功画面に遷移
			request.setAttribute("createPDF", "「面接証明書」を作成しました。");
			return "create-pdf-success.jsp";
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "interview-certificate.jsp";
		}
	}
}
