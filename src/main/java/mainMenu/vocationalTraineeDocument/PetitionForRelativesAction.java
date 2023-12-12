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

public class PetitionForRelativesAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(PetitionForRelativesAction.class);

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
		String requestYear = request.getParameter("requestYear");
		String requestMonth = request.getParameter("requestMonth");
		String requestDay = request.getParameter("requestDay");

		// 必須項目に未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(relativeName, birthYear, birthMonth, birthDay, relativeAddress, requestYear,
				requestMonth, requestDay)) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "petition-for-relatives.jsp";
		}

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		if (ValidationUtil.isFourDigit(birthYear, requestYear) ||
				ValidationUtil.isOneOrTwoDigit(birthMonth, birthDay, requestMonth, requestDay)) {
			request.setAttribute("dayError", "年月日は正規の桁数で入力してください。");
		} else {
			if (ValidationUtil.validateDate(birthYear, birthMonth, birthDay) ||
					ValidationUtil.validateDate(requestYear, requestMonth, requestDay)) {
				request.setAttribute("dayError", "存在しない日付です。");
			}
		}

		// 入力値に特殊文字が入っていないか確認する
		if (ValidationUtil.containsForbiddenChars(relativeName, relativeAddress)) {
			request.setAttribute("validationError", "使用できない特殊文字が含まれています");
		}

		// 文字数が多い場合はエラーを返す。
		if (ValidationUtil.areValidLengths(32, relativeName)) {
			request.setAttribute("valueLongError", "名前は32文字以下で入力してください。");
		}

		if (ValidationUtil.areValidLengths(64, relativeAddress)) {
			request.setAttribute("valueLongAddressError", "住所は64文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "petition-for-relatives.jsp";
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
				request.setAttribute("studentTypeError", "当該書類は職業訓練生のみが発行可能です。");
				return "petition-for-relatives.jsp";
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
			String pdfPath = "/pdf/vocationalTraineePDF/親族続柄申立書.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));

			// PDFへの記載
			// 親族氏名・親族住所・親族生年月日
			editor.writeText(font, relativeName, 100f, 271f, 130f, "center", 12);
			editor.writeText(font, relativeAddress, 265f, 271f, 220f, "left", 12);
			editor.writeText(font, birthYear, 120f, 240f, 70f, "left", 12);
			editor.writeText(font, birthMonth, 163f, 240f, 70f, "left", 12);
			editor.writeText(font, birthDay, 193f, 240f, 70f, "left", 12);
			// 公共職業安定所名
			editor.writeText(font, namePESO, 365f, 96f, 50f, "center", 12);
			// 申請年月日・クラス名・名前
			editor.writeText(font, requestYear, 105f, 62f, 40f, "left", 12);
			editor.writeText(font, requestMonth, 155f, 62f, 40f, "left", 12);
			editor.writeText(font, requestDay, 195f, 62f, 40f, "left", 12);
			editor.writeText(font, className, 100f, 32f, 130f, "center", 12);
			editor.writeText(font, name, 377f, 32f, 140f, "center", 12);

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Petition For Relatives");
			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "親族続柄申立書");
			// Close and save
			editor.close("Petition_For_Relatives.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "petition-for-relatives.jsp";
		}
	}
}
