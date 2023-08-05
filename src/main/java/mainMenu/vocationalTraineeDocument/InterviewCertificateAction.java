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
		String startManth = request.getParameter("startManth");
		String startDay = request.getParameter("startDay");
		String startForenoonOrMidday = request.getParameter("startForenoonOrMidday");
		String startHour = request.getParameter("startHour");
		String endYear = request.getParameter("endYear");
		String endManth = request.getParameter("endManth");
		String endDay = request.getParameter("endDay");
		String endForenoonOrMidday = request.getParameter("endForenoonOrMidday");
		String endHour = request.getParameter("endHour");

		// 未入力項目があればエラーを返す
		if (jobSearch.isEmpty() || startYear.isEmpty() || startManth.isEmpty() || startDay.isEmpty()
				|| startForenoonOrMidday.isEmpty() || startHour.isEmpty() || endYear.isEmpty() || endManth.isEmpty()
				|| endDay.isEmpty() || endForenoonOrMidday.isEmpty() || endHour.isEmpty()
				|| jobSearch == null || startYear == null || startManth == null || startDay == null
				|| startForenoonOrMidday == null || startHour == null
				|| endYear == null || endManth == null || endDay == null || endForenoonOrMidday == null
				|| endHour == null) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "interview-certificate.jsp";
		}
		System.out.println(11111);
		// 年月日が存在しない日付の場合はエラーにする
		try {
			int checkYear = Integer.parseInt(startYear) + 2018;
			int checkMonth = Integer.parseInt(startManth);
			int checkDay = Integer.parseInt(startDay);

			// 日付の妥当性チェック
			LocalDate Date = LocalDate.of(checkYear, checkMonth, checkDay);

			checkYear = Integer.parseInt(endYear) + 2018;
			checkMonth = Integer.parseInt(endManth);
			checkDay = Integer.parseInt(endDay);

			Date = LocalDate.of(checkYear, checkMonth, checkDay);

		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
		}

		// 文字数が64文字より多い場合はエラーを返す
		if (jobSearch.length() > 64) {
			request.setAttribute("valueLongError", "64文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("dayError") != null || request.getAttribute("valueLongError") != null) {
			return "interview-certificate.jsp";
		}
		System.out.println(21111);

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
			// もし学生種類が職業訓練生出なければエラーを返す
			if (!studentType.equals("職業訓練生")) {
				request.setAttribute("errorMessage", "当該書類は職業訓練生のみが発行可能です。");
				return "interview-certificate.jsp";
			}
			System.out.println(31111);

			// 公共職業安定所名のデータベースからの取り出し
			String reEncryptedNamePESO = dao.getNamePESO(id);
			String encryptedNamePESO = CipherUtil.commonDecrypt(reEncryptedNamePESO);
			String namePESO = CipherUtil.decrypt(masterKey, iv, encryptedNamePESO);

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/vocationalTraineePDF/面接証明書.pdf";
			String fontPath = "/font/MS-PMincho-02.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));

			editor.writeText(font, className, 135f, 493f, 200f, "left", 12);
			editor.writeText(font, name, 485f, 493f, 30f, "left", 12);
			editor.writeText(font, jobSearch, 518f, 493f, 30f, "left", 12);
			editor.writeText(font, className, 135f, 470f, 200f, "left", 12);
			editor.writeText(font, className, 135f, 470f, 200f, "left", 12);

			if (startForenoonOrMidday.equals("午前")) {
				editor.writeText(font, "〇", 445f, 255f, 50f, "left", 16);
			} else {
				editor.writeText(font, "〇", 484f, 255f, 50f, "left", 16);
			}

			if (endForenoonOrMidday.equals("午前")) {
				editor.writeText(font, "〇", 445f, 233f, 50f, "left", 16);
			} else {
				editor.writeText(font, "〇", 484f, 233f, 50f, "left", 16);
			}

			editor.writeText(font, namePESO, 30f, 150f, 40f, "left", 16);

			// Close and save
			editor.close("面接証明書.pdf");
			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Interview Certificate");
			// PDF作成成功画面に遷移
			request.setAttribute("createPDF", "「面接証明書」を作成しました。");
			return "create-pdf-success.jsp";
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("errorMessage", "内部エラーが発生しました。");
			return "interview-certificate.jsp";
		}
	}
}
