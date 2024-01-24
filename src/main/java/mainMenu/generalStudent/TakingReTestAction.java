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

public class TakingReTestAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(TakingReTestAction.class);

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

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 申請年月日のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "入力必須項目です。");
		}
		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isFourDigit(requestYear) ||
				ValidationUtil.isOneOrTwoDigit(requestMonth, requestDay)) {
			request.setAttribute("requestError", "年月日は正規の桁数で入力してください。");
		}
		// 申請年月日が存在しない日付の場合はエラーにする
		else if (ValidationUtil.validateDate(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "存在しない日付です。");
		}

		// 再試験年度のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(fiscalYear)) {
			request.setAttribute("fiscalYearError", "入力必須項目です。");
		}
		// 年度は２桁以下でなければエラーを返す
		else if (ValidationUtil.isOneOrTwoDigit(fiscalYear)) {
			request.setAttribute("fiscalYearError", "再試験年度は半角数字2桁以下で入力してください。");
		}

		// 再試験学期のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(semester)) {
			request.setAttribute("semesterError", "入力必須項目です。");
		}
		// 学期は半角1桁でなければエラーを返す
		else if (ValidationUtil.isSingleDigit(semester)) {
			request.setAttribute("semesterError", "再試験学期は半角数字1桁で入力してください。");
		}

		// 担当教員名のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(teacher)) {
			request.setAttribute("teacherError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(teacher)) {
			request.setAttribute("teacherError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, teacher)) {
			request.setAttribute("teacherError", "担当教員名は32文字以下で入力してください。");
		}

		// 教科名のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(subjectName)) {
			request.setAttribute("subjectNameError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(subjectName)) {
			request.setAttribute("subjectNameError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, subjectName)) {
			request.setAttribute("subjectNameError", "教科名は32文字以下で入力してください。");
		}

		// 受講事由のエラー処理
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
			request.setAttribute("reasonError", "受講事由は32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "taking-re-test.jsp";
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
			String pdfPath = "/pdf/generalStudentPDF/再試験受験申請書.pdf";
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
			editor.writeText(font, name, 193f, 649f, 187f, "center", 12);
			editor.writeText(font, studentNumber, 433f, 649f, 90f, "center", 12);
			editor.writeText(font, className, 193f, 627f, 192f, "center", 12);
			editor.writeText(font, schoolYear, 465f, 627f, 132f, "left", 12);
			editor.writeText(font, classNumber, 497f, 627f, 132f, "left", 12);
			// 受講する年度・学期・先生名・教科名・理由
			editor.writeText(font, fiscalYear, 160f, 541.5f, 238f, "left", 10);
			editor.writeText(font, semester, 205f, 541.5f, 238f, "left", 10);
			editor.writeText(font, teacher, 393f, 541.5f, 67f, "center", 10);
			editor.writeText(font, subjectName, 125f, 522f, 398f, "center", 12);
			editor.writeText(font, reason, 125f, 483f, 398f, "center", 12);

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Taking Re Test");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "再試験受験申請書");
			// Close and save
			editor.close("Taking_Re_Test.pdf", request, response);

			return null;
		} catch (

		Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "taking-re-test.jsp";
		}
	}
}
