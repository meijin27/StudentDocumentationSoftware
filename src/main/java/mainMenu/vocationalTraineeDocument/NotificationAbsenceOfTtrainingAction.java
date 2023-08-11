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

public class NotificationAbsenceOfTtrainingAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(NotificationAbsenceOfTtrainingAction.class);

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
		String subjectYear = request.getParameter("subjectYear");
		String subjectMonth = request.getParameter("subjectMonth");
		String restedDayStart1 = request.getParameter("restedDayStart1");
		String restedDayEnd1 = request.getParameter("restedDayEnd1");
		String reason1 = request.getParameter("reason1");
		String allDayOff1 = request.getParameter("allDayOff1");
		String deadTime1 = request.getParameter("deadTime1");
		String latenessTime1 = request.getParameter("latenessTime1");
		String leaveEarlyTime1 = request.getParameter("leaveEarlyTime1");
		String AttachmentOfCertificate1 = request.getParameter("AttachmentOfCertificate1");

		// 入力された値をリクエストに格納		
		request.setAttribute("subjectYear", subjectYear);
		request.setAttribute("subjectMonth", subjectMonth);
		request.setAttribute("restedDayStart1", restedDayStart1);
		request.setAttribute("restedDayEnd1", restedDayEnd1);
		request.setAttribute("reason1", reason1);
		request.setAttribute("allDayOff1", allDayOff1);
		request.setAttribute("deadTime1", deadTime1);
		request.setAttribute("latenessTime1", latenessTime1);
		request.setAttribute("leaveEarlyTime1", leaveEarlyTime1);
		request.setAttribute("AttachmentOfCertificate1", AttachmentOfCertificate1);

		// 未入力項目があればエラーを返す
		if (subjectYear == null || subjectMonth == null
				|| AttachmentOfCertificate1 == null || restedDayStart1 == null
				|| restedDayEnd1 == null || reason1 == null || allDayOff1 == null
				|| subjectYear.isEmpty() || subjectMonth.isEmpty()
				|| AttachmentOfCertificate1.isEmpty()
				|| restedDayStart1.isEmpty() || restedDayEnd1.isEmpty()
				|| reason1.isEmpty()
				|| allDayOff1.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "notification-absence-of-training.jsp";
		}

		// 休業時限数を適切に選択していない場合、エラーを返す
		if (allDayOff1.equals("はい") && (deadTime1 == null || deadTime1.isEmpty())) {
			request.setAttribute("nullError", "欠席期間時限数を入力してください。");
			return "notification-absence-of-training.jsp";

		} else if (allDayOff1.equals("いいえ") && ((latenessTime1 == null || latenessTime1.isEmpty())
				&& (leaveEarlyTime1 == null || leaveEarlyTime1.isEmpty()))) {
			request.setAttribute("nullError", "遅刻時限数時限数か早退時限数を入力してください。両方の入力も可能です。");
			return "notification-absence-of-training.jsp";
		}

		// 年月日が存在しない日付の場合はエラーにする
		try {
			int checkYear = Integer.parseInt(subjectYear);
			int checkMonth = Integer.parseInt(subjectMonth);

			int checkStartDay = Integer.parseInt(restedDayStart1);
			int checkEndDay = Integer.parseInt(restedDayEnd1);
			// 日付の妥当性チェック
			LocalDate date = LocalDate.of(checkYear, checkMonth, checkStartDay);
			date = LocalDate.of(checkYear, checkMonth, checkEndDay);
		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
		}

		// 休業開始日が休業終了日よりも前かどうかをチェックする
		int checkStartDay = Integer.parseInt(restedDayStart1);
		int checkEndDay = Integer.parseInt(restedDayEnd1);
		// 日付が整合とれるか確認する
		if (checkStartDay > checkEndDay) {
			request.setAttribute("logicalError", "休業開始日は休業終了日よりも前でなければなりません。");
		}

		// 文字数が32文字より多い場合はエラーを返す
		if (reason1.length() > 32) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("logicalError") != null || request.getAttribute("dayError") != null
				|| request.getAttribute("valueLongError") != null) {
			return "notification-absence-of-training.jsp";
		}

		// 日付フォーマットの定義
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		// 文字列からLocalDateに変換
		LocalDate startDate = LocalDate.parse(restedDayStart1 + "-" + restedDayEnd1 + "-" + reason1, formatter);
		LocalDate endDate = LocalDate.parse(allDayOff1 + "-" + deadTime1 + "-" + latenessTime1, formatter);
		// 間の日数を計算
		String daysBetween = String.valueOf(ChronoUnit.DAYS.between(startDate, endDate) + 1);

		// リクエストのデータ全削除
		Enumeration<String> attributeNames = request.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			System.out.println(attributeName);
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
				return "notification-absence-of-training.jsp";
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
			String pdfPath = "/pdf/vocationalTraineePDF/委託訓練欠席（遅刻・早退）届.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));

			// PDFへの記載
			editor.writeText(font, name, 198f, 643f, 75f, "center", 12);
			editor.writeText(font, subjectYear, 357f, 643f, 95f, "center", 12);
			editor.writeText(font, subjectMonth, 165f, 597f, 300f, "center", 12);
			editor.writeText(font, restedDayStart1, 195f, 550f, 40f, "left", 12);
			editor.writeText(font, restedDayEnd1, 263f, 550f, 40f, "left", 12);
			editor.writeText(font, reason1, 310f, 550f, 40f, "left", 12);
			editor.writeText(font, allDayOff1, 195f, 513f, 40f, "left", 12);
			editor.writeText(font, deadTime1, 262f, 513f, 40f, "left", 12);
			editor.writeText(font, latenessTime1, 310f, 513f, 40f, "left", 12);
			editor.writeText(font, daysBetween, 380f, 513f, 40f, "left", 12);
			editor.writeText(font, namePESO, 322f, 309f, 50f, "center", 12);
			editor.writeText(font, leaveEarlyTime1, 145f, 249f, 40f, "left", 12);
			editor.writeText(font, AttachmentOfCertificate1, 194f, 249f, 40f, "left", 12);
			editor.writeText(font, className, 145f, 220f, 110f, "center", 12);
			editor.writeText(font, name, 382f, 218f, 105f, "center", 12);

			// Close and save
			editor.close("委託訓練欠席（遅刻・早退）届.pdf");
			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Notification Absence Of Ttraining");
			// PDF作成成功画面に遷移
			request.setAttribute("createPDF", "「委託訓練欠席（遅刻・早退）届」を作成しました。");
			return "create-pdf-success.jsp";
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "notification-absence-of-training.jsp";
		}
	}
}
