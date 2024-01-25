package mainMenu.vocationalTraineeDocument;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
import tool.CustomLogger;
import tool.Decrypt;
import tool.DecryptionResult;
import tool.EditPDF;
import tool.RequestAndSessionUtil;
import tool.ValidationUtil;

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
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークン及びログイン状態の確認
		if (RequestAndSessionUtil.validateSession(request, response, "master_key", "id")) {
			// ログイン状態が不正ならば処理を終了
			return null;
		}

		// 入力された値を変数に格納
		String subjectYear = request.getParameter("subjectYear");
		String subjectMonth = request.getParameter("subjectMonth");

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 対象年のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(subjectYear)) {
			request.setAttribute("subjectYearError", "入力必須項目です。");
		}
		// 証明書対象期間は半角2桁以下でなければエラーを返す
		else if (ValidationUtil.isOneOrTwoDigit(subjectYear)) {
			request.setAttribute("subjectYearError", "対象年は半角2桁以下で入力してください。");
		}

		// 対象月のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(subjectMonth)) {
			request.setAttribute("subjectMonthError", "入力必須項目です。");
		}
		// 証明書対象月は半角2桁以下でなければエラーを返す
		else if (ValidationUtil.isOneOrTwoDigit(subjectMonth)) {
			request.setAttribute("subjectMonthError", "対象月は半角2桁以下で入力してください。");
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
			String restedDayStartError = "restedDayStart" + num + "Error";
			String restedDayEnd = request.getParameter("restedDayEnd" + num);
			String restedDayEndError = "restedDayEnd" + num + "Error";
			String reason = request.getParameter("reason" + num);
			String reasonError = "reason" + num + "Error";
			String allDayOff = request.getParameter("allDayOff" + num);
			String allDayOffError = "allDayOff" + num + "Error";
			String deadTime = request.getParameter("deadTime" + num);
			String deadTimeError = "deadTime" + num + "Error";
			String latenessTime = request.getParameter("latenessTime" + num);
			String latenessTimeError = "latenessTime" + num + "Error";
			String leaveEarlyTime = request.getParameter("leaveEarlyTime" + num);
			String leaveEarlyTimeError = "leaveEarlyTime" + num + "Error";
			String attachmentOfCertificate = request.getParameter("attachmentOfCertificate" + num);
			String attachmentOfCertificateError = "attachmentOfCertificate" + num + "Error";

			// 未入力項目があればエラーを返す
			if (ValidationUtil.isNullOrEmpty(attachmentOfCertificate, restedDayStart, restedDayEnd, reason,
					allDayOff)) {
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
				request.setAttribute("allDayOffError", "全日休確認は「はい」「いいえ」から選択してください");
				return "notification-absence-of-training.jsp";
			}

			// 証明添付有無は「有」「無」以外の場合はエラーを返す
			if (!(attachmentOfCertificate.equals("有") || attachmentOfCertificate.equals("無"))) {
				request.setAttribute("attachmentOfCertificateError", "証明添付有無は「有」「無」から選択してください");
			}

			// 終日休業が「はい」の場合の処理
			if (allDayOff.equals("はい")) {
				// 休業時限数を適切に選択していない場合、エラーを返す。適切な場合は累計時限に追加する
				if (ValidationUtil.isNullOrEmpty(deadTime)) {
					request.setAttribute("deadTimeError", "欠席期間時限数を入力してください。");
					// 終日休業が「はい」かつ休業時限数が数字以外の場合はエラーを返す
				} else if (ValidationUtil.isOneOrTwoOrThreeDigit(deadTime)) {
					request.setAttribute("numberError", "時間は半角数字3桁以下で入力してください。");
					// 終日休業が「はい」ならば欠席時限数を累計時限に追加する
				} else {
					totalHours += Integer.parseInt(deadTime);
				}
			}

			// 終日休業が「いいえ」の場合の処理
			if (allDayOff.equals("いいえ")) {
				// 遅刻時限もしくは早退時限数を適切に選択していない場合、エラーを返す。適切な場合は累計時限に追加する
				if (ValidationUtil.areAllNullOrEmpty(latenessTime, leaveEarlyTime)) {
					request.setAttribute("timeError", "遅刻時限数時限数か早退時限数を入力してください。両方の入力も可能です。");
					// 遅刻時限もしくは早退時限の入力されている値が数字以外の場合はエラーを返す
				} else if (!ValidationUtil.isNullOrEmpty(latenessTime)
						&& ValidationUtil.isOneOrTwoDigit(latenessTime)) {
					request.setAttribute("numberError", "時間は半角数字で入力してください。");
				} else if (!ValidationUtil.isNullOrEmpty(leaveEarlyTime)
						&& ValidationUtil.isOneOrTwoDigit(leaveEarlyTime)) {
					// 遅刻時限もしくは早退時限の入力されている値が数字以外の場合はエラーを返す
					request.setAttribute("numberError", "時間は半角数字で入力してください。");
					// 終日休業が「いいえ」で複数の日付をまたぐ場合はエラーを返す
				} else if (!restedDayStart.equals(restedDayEnd)) {
					request.setAttribute("logicalError", "複数の日付をまたぐ場合は終日休業になります。");
				} else {
					// 遅刻時限数が入力されていた場合
					if (!ValidationUtil.isNullOrEmpty(latenessTime)) {
						totalHours += Integer.parseInt(latenessTime);
					}
					// 早退時限数が入力されていた場合
					if (!ValidationUtil.isNullOrEmpty(leaveEarlyTime)) {
						totalHours += Integer.parseInt(leaveEarlyTime);
					}
				}
			}

			// 年月日が１・２桁になっていることを検証し、違う場合はエラーを返す
			if (ValidationUtil.isOneOrTwoDigit(subjectYear, subjectMonth, restedDayStart, restedDayEnd)) {
				request.setAttribute("dayError", "年月日は正規の桁数で入力してください。");
			} else {
				if (ValidationUtil.validateDate(subjectYear, subjectYear, restedDayStart)
						|| ValidationUtil.validateDate(subjectYear, subjectYear, restedDayEnd)) {
					request.setAttribute("dayError", "存在しない日付です。");
				} else {
					int checkStartDay = Integer.parseInt(restedDayStart);
					int checkEndDay = Integer.parseInt(restedDayEnd);
					// 休業開始日が休業終了日よりも前かどうかをチェックする
					if (checkStartDay > checkEndDay) {
						request.setAttribute("logicalError", "休業開始日は休業終了日よりも前でなければなりません。");
					}
				}
			}

			// 文字数が22文字より多い場合はエラーを返す
			if (ValidationUtil.areValidLengths(22, reason)) {
				request.setAttribute("valueLongError", "22文字以下で入力してください。");
			}

			// 入力値に特殊文字が入っていないか確認する
			if (ValidationUtil.containsForbiddenChars(reason)) {
				request.setAttribute("validationError", "使用できない特殊文字が含まれています");
			}

			// エラーが発生している場合は元のページに戻す
			if (RequestAndSessionUtil.hasErrorAttributes(request)) {
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

			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(lastName, firstName, className, studentType)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// もし学生種類が職業訓練生でなければエラーを返す
			if (!studentType.equals("職業訓練生")) {
				request.setAttribute("innerError", "当該書類は職業訓練生のみが発行可能です。");
				return "notification-absence-of-training.jsp";
			}

			// 出席番号のデータベースからの取り出し
			String reEncryptedAttendanceNumber = dao.getAttendanceNumber(id);
			String attendanceNumber = decrypt.getDecryptedDate(result, reEncryptedAttendanceNumber);
			// 雇用保険のデータベースからの取り出し
			String reEncryptedEmploymentInsurance = dao.getEmploymentInsurance(id);
			String employmentInsurance = decrypt.getDecryptedDate(result, reEncryptedEmploymentInsurance);

			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			// 職業訓練生情報のデータベースへの登録とそれ以外の情報の登録が異なるためnullチェックを分ける
			if (ValidationUtil.isNullOrEmpty(attendanceNumber, employmentInsurance)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// 姓名を結合する
			String name = lastName + " " + firstName;

			// クラス名の末尾に「科」がついていた場合は削除する
			if (className.endsWith("科")) {
				className = className.substring(0, className.length() - 1);
			}

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
