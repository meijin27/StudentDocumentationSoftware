package mainMenu.vocationalTraineeDocument;

import java.time.DateTimeException;
import java.time.LocalDate;
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

public class PetitionForRelativesAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(PetitionForRelativesAction.class);

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
		String relativeName = request.getParameter("relativeName");
		String birthYear = request.getParameter("birthYear");
		String birthMonth = request.getParameter("birthMonth");
		String birthDay = request.getParameter("birthDay");
		String relativeAddress = request.getParameter("relativeAddress");
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
		if (relativeName == null || birthYear == null || birthMonth == null || birthDay == null
				|| relativeAddress == null || requestYear == null
				|| requestMonth == null || requestDay == null || relativeName.isEmpty() || birthYear.isEmpty()
				|| birthMonth.isEmpty() || birthDay.isEmpty()
				|| relativeAddress.isEmpty() || requestYear.isEmpty() || requestMonth.isEmpty()
				|| requestDay.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "petition-for-relatives.jsp";
		}

		// 年月日が存在しない日付の場合はエラーにする
		try {

			// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
			if (!birthYear.matches("^\\d{4}$") || !birthMonth.matches("^\\d{1,2}$")
					|| !birthDay.matches("^\\d{1,2}$") || !requestYear.matches("^\\d{4}$")
					|| !requestMonth.matches("^\\d{1,2}$")
					|| !requestDay.matches("^\\d{1,2}$")) {
				request.setAttribute("dayError", "年月日は正規の桁数で入力してください。");
			} else {

				int checkYear = Integer.parseInt(birthYear);
				int checkMonth = Integer.parseInt(birthMonth);
				int checkDay = Integer.parseInt(birthDay);

				// 日付の妥当性チェック
				LocalDate date = LocalDate.of(checkYear, checkMonth, checkDay);

				checkYear = Integer.parseInt(requestYear);
				checkMonth = Integer.parseInt(requestMonth);
				checkDay = Integer.parseInt(requestDay);

				date = LocalDate.of(checkYear, checkMonth, checkDay);
			}
		} catch (NumberFormatException e) {
			request.setAttribute("dayError", "年月日は数字で入力してください。");
		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
		}

		// 文字数が多い場合はエラーを返す
		if (relativeName.length() > 32 || relativeAddress.length() > 64) {
			request.setAttribute("valueLongError", "名前は32文字以下、住所は64文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("dayError") != null || request.getAttribute("valueLongError") != null) {
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
				return "petition-for-relatives.jsp";
			}

			// 公共職業安定所名のデータベースからの取り出し
			String reEncryptedNamePESO = dao.getNamePESO(id);
			// 最初にデータベースから取り出した職業訓練生のデータがnullの場合、初期設定をしていないためログインページにリダイレクト
			if (reEncryptedNamePESO == null) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}
			String encryptedNamePESO = CipherUtil.commonDecrypt(reEncryptedNamePESO);
			String namePESO = CipherUtil.decrypt(masterKey, iv, encryptedNamePESO);

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
