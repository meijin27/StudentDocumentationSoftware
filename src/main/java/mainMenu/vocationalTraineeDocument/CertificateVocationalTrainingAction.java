package mainMenu.vocationalTraineeDocument;

import java.time.YearMonth;
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

public class CertificateVocationalTrainingAction extends Action {

	private static final Logger logger = CustomLogger.getLogger(CertificateVocationalTrainingAction.class);

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
		String problems = request.getParameter("problems");
		String income = request.getParameter("income");

		// 必須項目に未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(subjectYear, subjectMonth, problems, income)) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "certificate-vocational-training.jsp";
		}

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 証明書対象期間は半角2桁以下でなければエラーを返す
		if (ValidationUtil.isOneOrTwoDigit(subjectYear, subjectMonth)) {
			request.setAttribute("numberError", "証明書対象期間は半角2桁以下で入力してください。");
			return "certificate-vocational-training.jsp";
		}

		// 日付毎に入力された記号をMAPに格納する
		int year = Integer.parseInt(subjectYear);
		int month = Integer.parseInt(subjectMonth);
		int daysInMonth = YearMonth.of(year + 2018, month).lengthOfMonth(); // 令和年を西暦に変換
		Map<Integer, String> calendar = new HashMap<>();
		for (int i = 1; i <= 31; i++) {
			String day = "day" + i;
			String marker = request.getParameter(day);
			// カレンダーに存在しない日付であれば強制的に「/」にする
			if (i > daysInMonth) {
				calendar.put(i, "／");
				// 入力された値がnullでなけく、２文字以上であればエラーを返す。
			} else if (marker != null) {
				if (marker.length() > 1) {
					request.setAttribute("innerError", "日付には一文字の記号を入力してください。");
					return "certificate-vocational-training.jsp";
				}
				// 入力された値がnullでなければ記号を格納する。未選択の場合は空文字列を格納する。	
				calendar.put(i, marker);
			}
		}

		// ラジオボタンの入力値チェック
		// 就労有無が「した」「しない」以外の場合はエラーを返す
		if (!(problems.equals("した") || problems.equals("しない"))) {
			request.setAttribute("problemsError", "就労有無は「した」「しない」から選択してください");
		}
		// 収入有無が「得た」「得ない」以外の場合はエラーを返す
		if (!(income.equals("得た") || income.equals("得ない"))) {
			request.setAttribute("incomeError", "収入有無は「得た」「得ない」から選択してください");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "certificate-vocational-training.jsp";
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
			// 支給番号のデータベースからの取り出し
			String reEncryptedSupplyNumber = dao.getSupplyNumber(id);
			String supplyNumber = decrypt.getDecryptedDate(result, reEncryptedSupplyNumber);
			// 雇用保険のデータベースからの取り出し
			String reEncryptedEmploymentInsurance = dao.getEmploymentInsurance(id);
			String employmentInsurance = decrypt.getDecryptedDate(result, reEncryptedEmploymentInsurance);

			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(lastName, firstName, studentType, className, namePESO, supplyNumber,
					employmentInsurance)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// もし学生種類が職業訓練生でなければエラーを返す
			if (!studentType.equals("職業訓練生")) {
				request.setAttribute("innerError", "当該書類は職業訓練生のみが発行可能です。");
				return "certificate-vocational-training.jsp";
			}

			// もし雇用保険が無ければエラーを返す
			if (employmentInsurance.equals("無")) {
				request.setAttribute("innerError", "当該書類は雇用保険が「有」の場合のみ発行可能です。");
				return "certificate-vocational-training.jsp";
			}

			// 姓名を結合する
			String name = lastName + " " + firstName;

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/vocationalTraineePDF/公共職業訓練等受講証明書.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));
			// PDFへの書き込み
			// 名前・申請年月・クラス名
			editor.writeText(font, name, 135f, 493f, 200f, "left", 12);
			editor.writeText(font, subjectYear, 485f, 493f, 30f, "left", 12);
			editor.writeText(font, subjectMonth, 518f, 493f, 30f, "left", 12);
			editor.writeText(font, className, 135f, 470f, 200f, "left", 12);
			// 日毎の書き込み
			editor.writeSymbolsOnCalendar(font, calendar, 407f, 446f, 21f, 21.2f, 22);
			// 就労有無
			if (problems.equals("した")) {
				editor.writeText(font, "〇", 445f, 255.5f, 50f, "left", 16);
			} else {
				editor.writeText(font, "〇", 483.5f, 255.5f, 50f, "left", 16);
			}
			// 収入有無
			if (income.equals("得た")) {
				editor.writeText(font, "〇", 445f, 233.5f, 50f, "left", 16);
			} else {
				editor.writeText(font, "〇", 483.5f, 233.5f, 50f, "left", 16);
			}
			// 公共職業安定所名・名前・クラス名
			editor.writeText(font, namePESO, 30f, 150f, 40f, "left", 16);
			editor.writeText(font, name, 310f, 165f, 200f, "left", 12);
			editor.writeText(font, supplyNumber, 310f, 147f, 200f, "left", 12);

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Certificate Vocational Training");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "公共職業訓練等受講証明書");
			// Close and save
			editor.close("Certificate_Vocational_Training.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "certificate-vocational-training.jsp";
		}
	}
}
