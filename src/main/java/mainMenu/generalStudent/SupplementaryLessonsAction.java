package mainMenu.generalStudent;

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

public class SupplementaryLessonsAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(SupplementaryLessonsAction.class);

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
		String requestYear = request.getParameter("requestYear");
		String requestMonth = request.getParameter("requestMonth");
		String requestDay = request.getParameter("requestDay");
		String fiscalYear = request.getParameter("fiscalYear");
		String semester = request.getParameter("semester");
		String subjectName = request.getParameter("subjectName");
		String teacher = request.getParameter("teacher");
		String reason = request.getParameter("reason");

		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(requestYear, requestMonth, requestDay, fiscalYear, semester, subjectName,
				teacher, reason)) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "supplementary-lessons.jsp";
		}

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		if (ValidationUtil.isFourDigit(requestYear) ||
				ValidationUtil.isOneOrTwoDigit(requestMonth, requestDay)) {
			request.setAttribute("dayError", "年月日は正規の桁数で入力してください。");
		} else {
			if (ValidationUtil.validateDate(requestYear, requestMonth, requestDay)) {
				request.setAttribute("dayError", "存在しない日付です。");
			}
		}

		// 年度は２桁以下でなければエラーを返す
		if (ValidationUtil.isOneOrTwoDigit(fiscalYear)) {
			request.setAttribute("fiscalYearError", "年度は半角数字2桁以下で入力してください。");
		}

		// 学期は半角1桁でなければエラーを返す
		if (ValidationUtil.isSingleDigit(semester)) {
			request.setAttribute("semesterError", "学期は半角数字1桁で入力してください。");
		}

		// 文字数が32文字より多い場合はエラーを返す。
		if (ValidationUtil.areValidLengths(32, subjectName, teacher, reason)) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// 入力値に特殊文字が入っていないか確認する
		if (ValidationUtil.containsForbiddenChars(subjectName, teacher, reason)) {
			request.setAttribute("validationError", "使用できない特殊文字が含まれています");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "supplementary-lessons.jsp";
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
			// 学年のデータベースからの取り出し
			String reEncryptedSchoolYear = dao.getSchoolYear(id);
			String schoolYear = decrypt.getDecryptedDate(result, reEncryptedSchoolYear);
			// クラス番号のデータベースからの取り出し
			String reEncryptedClassNumber = dao.getClassNumber(id);
			String classNumber = decrypt.getDecryptedDate(result, reEncryptedClassNumber);
			// 学籍番号のデータベースからの取り出し
			String reEncryptedStudentNumber = dao.getStudentNumber(id);
			String studentNumber = decrypt.getDecryptedDate(result, reEncryptedStudentNumber);
			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(lastName, firstName, className, schoolYear,
					classNumber,
					studentNumber)) {
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
			String pdfPath = "/pdf/generalStudentPDF/補習受講申請書.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));
			// PDFへの書き込み
			// 申請年月日
			editor.writeText(font, requestYear, 400f, 750f, 70f, "left", 12);
			editor.writeText(font, requestMonth, 455f, 750f, 70f, "left", 12);
			editor.writeText(font, requestDay, 490f, 750f, 70f, "left", 12);
			// 名前・学籍番号・クラス・学年・組
			editor.writeText(font, name, 193f, 646f, 187f, "center", 12);
			editor.writeText(font, studentNumber, 433f, 646f, 90f, "center", 12);
			editor.writeText(font, className, 193f, 622f, 192f, "center", 12);
			editor.writeText(font, schoolYear, 465f, 622f, 132f, "left", 12);
			editor.writeText(font, classNumber, 497f, 622f, 132f, "left", 12);
			// 受講する年度・学期・先生名・教科名・理由
			editor.writeText(font, fiscalYear, 160f, 546f, 238f, "left", 10);
			editor.writeText(font, semester, 205f, 546f, 238f, "left", 10);
			editor.writeText(font, teacher, 375f, 546f, 84f, "center", 10);
			editor.writeText(font, subjectName, 127f, 525f, 396f, "center", 12);
			editor.writeText(font, reason, 127f, 486f, 396f, "center", 12);

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Supplementary Lessons");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "補習受講申請書");
			// Close and save
			editor.close("Supplementary_Lessons.pdf", request, response);

			return null;
		} catch (

		Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "supplementary-lessons.jsp";
		}
	}
}
