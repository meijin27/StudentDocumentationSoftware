package mainMenu.vocationalTraineeDocument;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
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

	// 曜日を漢字一文字に変換するヘルパーメソッド
	private static String convertToKanji(DayOfWeek dayOfWeek) {
		switch (dayOfWeek) {
		case MONDAY:
			return "月";
		case TUESDAY:
			return "火";
		case WEDNESDAY:
			return "水";
		case THURSDAY:
			return "木";
		case FRIDAY:
			return "金";
		case SATURDAY:
			return "土";
		case SUNDAY:
			return "日";
		default:
			throw new IllegalArgumentException("Unexpected day of the week: " + dayOfWeek);
		}
	}

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// セッションからトークンを取得
		String sessionToken = (String) session.getAttribute("csrfToken");
		// リクエストパラメータからトークンを取得
		String requestToken = request.getParameter("csrfToken");
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// IDやマスターキーのセッションがない、トークンが一致しない、またはセッションの有効期限切れの場合はエラーとして処理
		if (session.getAttribute("master_key") == null || session.getAttribute("id") == null || sessionToken == null
				|| requestToken == null || !sessionToken.equals(requestToken)) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "セッションエラーが発生しました。ログインしてください。");
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// 入力された値を変数に格納
		String subjectYear = request.getParameter("subjectYear");
		String subjectMonth = request.getParameter("subjectMonth");

		// 入力された値をリクエストに格納	
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			request.setAttribute(paramName, paramValue);
		}

		// 未入力項目があればエラーを返す
		if (subjectYear == null || subjectMonth == null
				|| subjectYear.isEmpty() || subjectMonth.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "notification-absence-of-training.jsp";
		}

		// 入力値の数が不明なため、自動計算される値はMAPに格納する
		Map<String, String> parameters = new HashMap<>();

		// 作成する行数のカウント
		int count = 0;

		// 累計時限（欠席・遅刻・早退した時限数の合計）
		int totalHours = 0;

		// 作成する行ごとにデータを取り出す
		for (int i = 1; i <= 10; i++) {

			// 末尾に添付する番号のString
			String num = String.valueOf(i);

			// 入力された値を変数に格納
			String restedDayStart = request.getParameter("restedDayStart" + num);
			String restedDayEnd = request.getParameter("restedDayEnd" + num);
			String reason = request.getParameter("reason" + num);
			String allDayOff = request.getParameter("allDayOff" + num);
			String deadTime = request.getParameter("deadTime" + num);
			String latenessTime = request.getParameter("latenessTime" + num);
			String leaveEarlyTime = request.getParameter("leaveEarlyTime" + num);
			String attachmentOfCertificate = request.getParameter("attachmentOfCertificate" + num);

			// 未入力項目があればエラーを返す
			if (attachmentOfCertificate == null || restedDayStart == null
					|| restedDayEnd == null || reason == null || allDayOff == null
					|| attachmentOfCertificate.isEmpty()
					|| restedDayStart.isEmpty() || restedDayEnd.isEmpty()
					|| reason.isEmpty()
					|| allDayOff.isEmpty()) {
				// 最初の行(1行目)が空ならばエラーを返す
				if (i == 1) {
					request.setAttribute("nullError", "未入力項目があります。");
					return "notification-absence-of-training.jsp";
				} else {
					break;
				}
			}

			// 全日休みは「はい」「いいえ」以外の場合はエラーを返す
			if (!(allDayOff.equals("はい") || allDayOff.equals("いいえ"))) {
				request.setAttribute("innerError", "全日休確認は「はい」「いいえ」から選択してください");
			}

			// 証明添付有無は「有」「無」以外の場合はエラーを返す
			if (!(attachmentOfCertificate.equals("有") || attachmentOfCertificate.equals("無"))) {
				request.setAttribute("innerError", "証明添付有無は「有」「無」から選択してください");
			}

			// 休業時限数を適切に選択していない場合、エラーを返す。適切な場合は累計時限に追加する
			if (allDayOff.equals("はい") && (deadTime == null || deadTime.isEmpty())) {
				request.setAttribute("nullError", "欠席期間時限数を入力してください。");
			} else if (allDayOff.equals("いいえ") && ((latenessTime == null || latenessTime.isEmpty())
					&& (leaveEarlyTime == null || leaveEarlyTime.isEmpty()))) {
				request.setAttribute("nullError", "遅刻時限数時限数か早退時限数を入力してください。両方の入力も可能です。");
				// 終日休業が「いいえ」で複数の日付をまたぐ場合はエラーを返す
			} else if (allDayOff.equals("いいえ") && !restedDayStart.equals(restedDayEnd)) {
				request.setAttribute("logicalError", "複数の日付をまたぐ場合は終日休業になります。");
				// 終日休業が「はい」かつ休業時限数が数字以外の場合はエラーを返す
			} else if (allDayOff.equals("はい") && !deadTime.matches("^\\d{1,3}$")) {
				request.setAttribute("numberError", "時間は半角数字で入力してください。");
				// 終日休業が「はい」ならば欠席時限数を累計時限に追加する
			} else if (allDayOff.equals("いいえ")
					&& (!latenessTime.matches("^\\d{1,2}$") || !leaveEarlyTime.matches("^\\d{1,2}$"))) {
				totalHours += Integer.parseInt(deadTime);
				// 終日休業が「いいえ」かつ遅刻時限もしくは早退時限の入力されている値が数字以外の場合はエラーを返す
				request.setAttribute("numberError", "時間は半角数字で入力してください。");
				// 終日休業が「いいえ」ならば遅刻時限及び早退時限の入力されている値を累計時限に追加する
			} else if (allDayOff.equals("いいえ")) {
				// 遅刻時限数が入力されていた場合
				if (latenessTime != null && !latenessTime.isEmpty()) {
					totalHours += Integer.parseInt(latenessTime);
				}
				// 早退時限数が入力されていた場合
				if (leaveEarlyTime != null && !leaveEarlyTime.isEmpty()) {
					totalHours += Integer.parseInt(leaveEarlyTime);
				}
			}

			// 年月日が存在しない日付の場合はエラーにする
			try {
				int checkYear = Integer.parseInt(subjectYear) + 2018;
				int checkMonth = Integer.parseInt(subjectMonth);

				int checkStartDay = Integer.parseInt(restedDayStart);
				int checkEndDay = Integer.parseInt(restedDayEnd);
				// 日付の妥当性チェック
				LocalDate date = LocalDate.of(checkYear, checkMonth, checkStartDay);

				// 休業開始日が休業終了日よりも前かどうかをチェックする
				if (checkStartDay > checkEndDay) {
					request.setAttribute("logicalError", "休業開始日は休業終了日よりも前でなければなりません。");
				}

			} catch (NumberFormatException e) {
				request.setAttribute("dayError", "年月日は数字で入力してください。");
			} catch (DateTimeException e) {
				request.setAttribute("dayError", "存在しない日付です。");
			}

			// 文字数が22文字より多い場合はエラーを返す
			if (reason.length() > 22) {
				request.setAttribute("valueLongError", "22文字以下で入力してください。");
			}

			// エラーが発生している場合は元のページに戻す
			if (request.getAttribute("nullError") != null || request.getAttribute("logicalError") != null
					|| request.getAttribute("dayError") != null
					|| request.getAttribute("valueLongError") != null || request.getAttribute("numberError") != null) {
				return "notification-absence-of-training.jsp";
			}

			// 日付フォーマットの定義
			String checkYear = String.valueOf(Integer.parseInt(subjectYear) + 2018);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
			// 文字列からLocalDateに変換
			LocalDate startDate = LocalDate.parse(checkYear + "-" + subjectMonth + "-" + restedDayStart, formatter);
			LocalDate endDate = LocalDate.parse(checkYear + "-" + subjectMonth + "-" + restedDayEnd, formatter);

			// startDateおよびendDateの曜日を取得し、漢字一文字に変換
			String startDayOfWeekKanji = convertToKanji(startDate.getDayOfWeek());
			String endDayOfWeekKanji = convertToKanji(endDate.getDayOfWeek());

			// 曜日をparametersのMAPに格納
			parameters.put("startDayOfWeek" + num, startDayOfWeekKanji);
			parameters.put("endDayOfWeek" + num, endDayOfWeekKanji);

			// 間の日数を計算
			String daysBetween = String.valueOf(ChronoUnit.DAYS.between(startDate, endDate) + 1);
			parameters.put("daysBetween" + num, daysBetween);

			// 作成する行数カウントの追加
			count++;

			// 行ごとの累計時限をMAPに格納
			parameters.put("totalHours" + num, String.valueOf(totalHours));
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
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// 出席番号のデータベースからの取り出し
			String reEncryptedAttendanceNumber = dao.getAttendanceNumber(id);
			String encryptedAttendanceNumber = CipherUtil.commonDecrypt(reEncryptedAttendanceNumber);
			String attendanceNumber = CipherUtil.decrypt(masterKey, iv, encryptedAttendanceNumber);

			// 雇用保険のデータベースからの取り出し
			String reEncryptedEmploymentInsurance = dao.getEmploymentInsurance(id);
			String encryptedEmploymentInsurance = CipherUtil.commonDecrypt(reEncryptedEmploymentInsurance);
			String employmentInsurance = CipherUtil.decrypt(masterKey, iv, encryptedEmploymentInsurance);

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/vocationalTraineePDF/委託訓練欠席（遅刻・早退）届.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));

			// PDFへの記載
			// クラス名・出席番号・名前・雇用保険区分
			editor.writeText(font, className, 85f, 469f, 135f, "center", 12);
			editor.writeText(font, attendanceNumber, 305, 469f, 75f, "center", 12);
			editor.writeText(font, name, 460f, 469f, 145f, "center", 12);
			if (employmentInsurance.equals("有")) {
				editor.writeText(font, "〇", 731f, 467f, 75f, "left", 18);
			} else {
				editor.writeText(font, "〇", 761f, 467f, 75f, "left", 18);
			}
			// 申請年月
			editor.writeText(font, "令和" + subjectYear, 55f, 442f, 95f, "left", 12);
			editor.writeText(font, subjectMonth, 115f, 442f, 40f, "left", 12);

			float row = 0;
			for (int i = 1; i <= count; i++) {
				// 末尾に添付する番号のString
				String num = String.valueOf(i);

				// 入力された値を変数に格納
				String restedDayStart = request.getParameter("restedDayStart" + num);
				String restedDayEnd = request.getParameter("restedDayEnd" + num);
				String reason = request.getParameter("reason" + num);
				String allDayOff = request.getParameter("allDayOff" + num);
				String deadTime = request.getParameter("deadTime" + num);
				String latenessTime = request.getParameter("latenessTime" + num);
				String leaveEarlyTime = request.getParameter("leaveEarlyTime" + num);
				String attachmentOfCertificate = request.getParameter("attachmentOfCertificate" + num);
				// 月日・欠席日数
				editor.writeText(font, subjectMonth, 44f, 386f - row, 40f, "left", 12);
				editor.writeText(font, restedDayStart, 73f, 386f - row, 40f, "left", 12);
				editor.writeText(font, parameters.get("startDayOfWeek" + num), 112f, 386f - row, 40f, "left", 12);

				// 休業日が複数にまたがる場合のみ下段を表示する
				if (!restedDayStart.equals(restedDayEnd)) {
					editor.writeText(font, subjectMonth, 44f, 370f - row, 40f, "left", 12);
					editor.writeText(font, restedDayEnd, 73f, 370f - row, 40f, "left", 12);
					editor.writeText(font, parameters.get("endDayOfWeek" + num), 112f, 370f - row, 40f, "left", 12);
				}

				// 休業理由の文字数によって表示する位置を変える
				if (reason.length() < 12) {
					editor.writeText(font, reason, 165f, 378f - row, 138f, "center", 12);
				} else {
					editor.writeText(font, reason.substring(0, 11), 165f, 386f - row, 138f, "left", 12);
					editor.writeText(font, reason.substring(11, reason.length()), 165f, 370f - row, 138f, "left", 12);
				}

				// 休業が終日の場合は欠席日数とその下の時限を表示する。
				if (allDayOff.equals("はい")) {
					editor.writeText(font, parameters.get("daysBetween" + num), 330f, 386f - row, 40f, "left", 12);
					editor.writeText(font, deadTime, 330f, 370f - row, 40f, "left", 12);
					// 休業が終日でない場合は遅刻時限か早退時限を表示する。
				} else {
					// 遅刻時限数が入力されていた場合
					if (latenessTime != null && !latenessTime.isEmpty()) {
						editor.writeText(font, latenessTime, 384f, 378f - row, 70f, "center", 12);
					}
					// 早退時限数が入力されていた場合
					if (leaveEarlyTime != null && !leaveEarlyTime.isEmpty()) {
						editor.writeText(font, leaveEarlyTime, 459f, 378f - row, 70f, "center", 12);
					}
				}
				// 累計時限
				editor.writeText(font, parameters.get("totalHours" + num), 535f, 378f - row, 70f, "center", 12);

				// 雇用保険の有無に応じて〇の位置を変える
				if (attachmentOfCertificate.equals("有")) {
					editor.writeText(font, "〇", 737f, 384f - row, 40f, "left", 16);
				} else {
					editor.writeText(font, "〇", 755f, 384f - row, 40f, "left", 16);
				}
				row += 33;
			}

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Notification Absence Of Ttraining");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "委託訓練欠席（遅刻・早退）届");
			// Close and save
			editor.close("Notification_Absence_Of_Ttraining.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "notification-absence-of-training.jsp";
		}
	}

}
