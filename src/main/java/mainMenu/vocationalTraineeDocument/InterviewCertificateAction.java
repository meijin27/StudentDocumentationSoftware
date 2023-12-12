package mainMenu.vocationalTraineeDocument;

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

public class InterviewCertificateAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(InterviewCertificateAction.class);

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
		String jobSearch = request.getParameter("jobSearch");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String startHour = request.getParameter("startHour");
		String endHour = request.getParameter("endHour");

		//　午前・午後を入力するための変数
		String startForenoonOrMidday = "";
		String endForenoonOrMidday = "";

		// 必須項目に未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(jobSearch)) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "interview-certificate.jsp";
		}

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 日付に未入力項目がある場合は、印刷する書類に日時を記入しない
		if (ValidationUtil.isNullOrEmpty(year, month, day)) {
			year = "";
			month = "";
			day = "";
			startHour = "";
			endHour = "";
		} else {
			// 年月日が１・２桁になっていることを検証し、違う場合はエラーを返す
			if (ValidationUtil.isOneOrTwoDigit(year, month, day)) {
				request.setAttribute("dayError", "年月日は正規の桁数で入力してください。");
			} else {
				if (ValidationUtil.validateDate(year, month, day)) {
					request.setAttribute("dayError", "存在しない日付です。");
				}
			}
		}

		// 開始終了時刻に未記載がある場合は、印刷する書類に時間を記入しない
		if (ValidationUtil.isNullOrEmpty(startHour, endHour)) {
			startHour = "";
			endHour = "";
			// 時間は半角2桁以下でなければエラーを返す
		} else if (ValidationUtil.isOneOrTwoDigit(startHour, endHour)) {
			request.setAttribute("timeError", "時間は半角数字２桁以下で入力してください。");
		} else {
			// 開始時刻が終了時刻よりも前かどうかをチェックする
			int checkStartHour = Integer.parseInt(startHour);
			int checkEndHour = Integer.parseInt(endHour);

			// 時刻が整合取れるか確認する
			if (checkStartHour > checkEndHour) {
				request.setAttribute("logicalError", "開始時刻は終了時刻よりも前でなければなりません。");
			}

			if (checkStartHour < 12) {
				startForenoonOrMidday = "午前";
			} else {
				startForenoonOrMidday = "午後";
				startHour = String.valueOf(checkStartHour - 12);
			}

			if (checkEndHour < 12) {
				endForenoonOrMidday = "午前";
			} else {
				endForenoonOrMidday = "午後";
				endHour = String.valueOf(checkEndHour - 12);
			}
		}

		// 文字数が32文字より多い場合はエラーを返す
		if (ValidationUtil.areValidLengths(32, jobSearch)) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// 入力値に特殊文字が入っていないか確認する
		if (ValidationUtil.containsForbiddenChars(jobSearch)) {
			request.setAttribute("validationError", "使用できない特殊文字が含まれています");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "interview-certificate.jsp";
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

			// 公共職業安定所名のデータベースからの取り出し
			String reEncryptedNamePESO = dao.getNamePESO(id);
			String namePESO = decrypt.getDecryptedDate(result, reEncryptedNamePESO);

			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(lastName, firstName, studentType, className)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// もし学生種類が職業訓練生でなければエラーを返す
			if (!studentType.equals("職業訓練生")) {
				request.setAttribute("innerError", "当該書類は職業訓練生のみが発行可能です。");
				return "interview-certificate.jsp";
			}

			// データベースから取り出した職業訓練生データにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(namePESO)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// 姓名を結合する
			String name = lastName + " " + firstName;

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/vocationalTraineePDF/面接証明書.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));
			// PDFへの書き込み
			// クラス名・名前
			editor.writeText(font, className, 230f, 645f, 230f, "center", 12);
			editor.writeText(font, name, 230f, 600f, 230f, "center", 12);
			// 求人職種
			editor.writeText(font, jobSearch, 230f, 560f, 230f, "center", 12);
			// 期間（自）
			editor.writeText(font, year, 290f, 508f, 70f, "left", 12);
			editor.writeText(font, month, 323f, 508f, 70f, "left", 12);
			editor.writeText(font, day, 355f, 508f, 70f, "left", 12);
			editor.writeText(font, startHour, 430f, 508f, 70f, "left", 12);
			// 期間（至）
			editor.writeText(font, year, 290f, 436f, 70f, "left", 12);
			editor.writeText(font, month, 323f, 436f, 70f, "left", 12);
			editor.writeText(font, day, 355f, 436f, 70f, "left", 12);
			editor.writeText(font, endHour, 430f, 436f, 70f, "left", 12);

			if (startForenoonOrMidday.equals("午前")) {
				editor.writeText(font, "〇", 396f, 518f, 50f, "left", 32);
			} else if (startForenoonOrMidday.equals("午後")) {
				editor.writeText(font, "〇", 396f, 483f, 50f, "left", 32);
			}

			if (endForenoonOrMidday.equals("午前")) {
				editor.writeText(font, "〇", 396f, 446f, 50f, "left", 32);
			} else if (endForenoonOrMidday.equals("午後")) {
				editor.writeText(font, "〇", 396f, 410f, 50f, "left", 32);
			}
			// 公共職業安定所名
			editor.writeText(font, namePESO, 150f, 285f, 70f, "center", 12);

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Interview Certificate");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "面接証明書");
			// Close and save
			editor.close("Interview_Certificate.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "interview-certificate.jsp";
		}
	}
}
