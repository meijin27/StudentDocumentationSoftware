package mainMenu.vocationalTraineeDocument;

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

public class CertificateVocationalTrainingAction extends Action {

	private static final Logger logger = CustomLogger.getLogger(CertificateVocationalTrainingAction.class);

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

		Map<Integer, String> calendar = new HashMap<>();
		for (int i = 1; i <= 31; i++) {
			String day = "day" + i;
			String marker = request.getParameter(day);
			if (marker != null) {
				calendar.put(i, marker);
			}
		}

		// 入力された値を変数に格納
		String subjectYear = request.getParameter("subjectYear");
		String subjectMonth = request.getParameter("subjectMonth");
		String problems = request.getParameter("problems");
		String income = request.getParameter("income");

		// 入力された値をリクエストに格納		
		request.setAttribute("subjectYear", subjectYear);
		request.setAttribute("subjectMonth", subjectMonth);
		request.setAttribute("problems", problems);
		request.setAttribute("income", income);

		// 未入力項目があればエラーを返す
		if (subjectYear == null || subjectMonth == null || problems == null
				|| income == null || subjectYear.isEmpty() || subjectMonth.isEmpty() || problems.isEmpty()
				|| income.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "certificate-vocational-training.jsp";
		}

		// リクエストのデータ削除
		request.removeAttribute("subjectYear");
		request.removeAttribute("subjectMonth");
		request.removeAttribute("problems");
		request.removeAttribute("income");

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
				request.setAttribute("errorMessage", "当該書類は職業訓練生のみが発行可能です。");
				return "certificate-vocational-training.jsp";
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
			// 支給番号のデータベースからの取り出し
			String reEncryptedSupplyNumber = dao.getSupplyNumber(id);
			String encryptedSupplyNumber = CipherUtil.commonDecrypt(reEncryptedSupplyNumber);
			String supplyNumber = CipherUtil.decrypt(masterKey, iv, encryptedSupplyNumber);
			// 雇用保険のデータベースからの取り出し
			String reEncryptedEmploymentInsurance = dao.getEmploymentInsurance(id);
			String encryptedEmploymentInsurance = CipherUtil.commonDecrypt(reEncryptedEmploymentInsurance);
			String employmentInsurance = CipherUtil.decrypt(masterKey, iv, encryptedEmploymentInsurance);
			// もし雇用保険が無ければエラーを返す
			if (employmentInsurance.equals("無")) {
				request.setAttribute("errorMessage", "当該書類は雇用保険が「有」の場合のみ発行可能です。");
				return "certificate-vocational-training.jsp";
			}

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/vocationalTraineePDF/公共職業訓練等受講証明書.pdf";
			String fontPath = "/font/MS-PMincho-02.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));

			editor.writeText(font, name, 135f, 493f, 200f, "left", 12);
			editor.writeText(font, subjectYear, 485f, 493f, 30f, "left", 12);
			editor.writeText(font, subjectMonth, 518f, 493f, 30f, "left", 12);
			editor.writeText(font, className, 135f, 470f, 200f, "left", 12);
			editor.writeSymbolsOnCalendar(font, calendar, 408f, 447f, 21f, 21f, 20);

			if (problems.equals("した")) {
				editor.writeText(font, "〇", 445f, 255f, 50f, "left", 16);
			} else {
				editor.writeText(font, "〇", 484f, 255f, 50f, "left", 16);
			}

			if (income.equals("得た")) {
				editor.writeText(font, "〇", 445f, 233f, 50f, "left", 16);
			} else {
				editor.writeText(font, "〇", 484f, 233f, 50f, "left", 16);
			}

			editor.writeText(font, namePESO, 30f, 150f, 40f, "left", 16);
			editor.writeText(font, name, 310f, 165f, 200f, "left", 12);
			editor.writeText(font, supplyNumber, 310f, 147f, 200f, "left", 12);

			// Close and save
			editor.close("公共職業訓練等受講証明書.pdf");
			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Certificate Vocational Training");
			// PDF作成成功画面に遷移
			request.setAttribute("createPDF", "「公共職業訓練等受講証明書」を作成しました。");
			return "create-pdf-success.jsp";
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("errorMessage", "内部エラーが発生しました。");
			return "certificate-vocational-training.jsp";
		}
	}
}
