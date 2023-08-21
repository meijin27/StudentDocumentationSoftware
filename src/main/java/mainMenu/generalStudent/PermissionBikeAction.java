package mainMenu.generalStudent;

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

public class PermissionBikeAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(PermissionBikeAction.class);

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
		String requestYear = request.getParameter("requestYear");
		String requestMonth = request.getParameter("requestMonth");
		String requestDay = request.getParameter("requestDay");
		String patron = request.getParameter("patron");
		String patronTel = request.getParameter("patronTel");
		String classification = request.getParameter("classification");
		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String startDay = request.getParameter("startDay");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String endDay = request.getParameter("endDay");
		String registrationNumber = request.getParameter("registrationNumber");
		String modelAndColor = request.getParameter("modelAndColor");

		// 入力された値をリクエストに格納	
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			request.setAttribute(paramName, paramValue);
		}

		// 未入力項目があればエラーを返す
		if (requestYear == null || requestMonth == null || requestDay == null
				|| patron == null || patronTel == null
				|| startYear == null || startMonth == null || startDay == null || classification == null
				|| endYear == null || endMonth == null || endDay == null || registrationNumber == null
				|| modelAndColor == null
				|| requestYear.isEmpty() || requestMonth.isEmpty()
				|| requestDay.isEmpty()
				|| patron.isEmpty() || patronTel.isEmpty() || startYear.isEmpty() || startMonth.isEmpty()
				|| startDay.isEmpty() || classification.isEmpty()
				|| endYear.isEmpty() || endMonth.isEmpty()
				|| endDay.isEmpty() || registrationNumber.isEmpty() || modelAndColor.isEmpty()

		) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "permission-bike.jsp";
		}

		// 年月日が存在しない日付の場合はエラーにする
		try {
			int checkYear = Integer.parseInt(requestYear) + 2018;
			int checkMonth = Integer.parseInt(requestMonth);
			int checkDay = Integer.parseInt(requestDay);

			// 願出年月日の日付の妥当性チェック
			LocalDate requestDate = LocalDate.of(checkYear, checkMonth, checkDay);

			checkYear = Integer.parseInt(startYear) + 2018;
			checkMonth = Integer.parseInt(startMonth);
			checkDay = Integer.parseInt(startDay);
			// 期間年月日（自）の日付の妥当性チェック
			LocalDate startDate = LocalDate.of(checkYear, checkMonth, checkDay);

			checkYear = Integer.parseInt(endYear) + 2018;
			checkMonth = Integer.parseInt(endMonth);
			checkDay = Integer.parseInt(endDay);
			// 期間年月日（至）の日付の妥当性チェック
			LocalDate endDate = LocalDate.of(checkYear, checkMonth, checkDay);

			// 申請日と申請期間の比較
			if (startDate.isBefore(requestDate)) {
				request.setAttribute("dayError", "期間年月日（自）は願出年月日より後の日付でなければなりません。");
			} else if (endDate.isBefore(startDate)) {
				request.setAttribute("dayError", "期間年月日（自）は期間年月日（至）より前の日付でなければなりません。");
			}
		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
		}

		// 電話番号が半角10~11桁でなければエラーを返す
		if (!patronTel.matches("^\\d{10,11}$")) {
			request.setAttribute("telError", "電話番号は半角数字10桁～11桁で入力してください。");
		}
		// 文字数が32文字より多い場合はエラーを返す
		if (patron.length() > 32 || registrationNumber.length() > 32 || modelAndColor.length() > 32) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("nullError") != null || request.getAttribute("telError") != null
				|| request.getAttribute("dayError") != null
				|| request.getAttribute("valueLongError") != null) {
			return "permission-bike.jsp";
		}

		// リクエストのデータ全削除
		Enumeration<String> attributeNames = request.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			request.removeAttribute(attributeName);
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

			// 電話番号のデータベースからの取り出し
			String reEncryptedTel = dao.getTel(id);
			String encryptedTel = CipherUtil.commonDecrypt(reEncryptedTel);
			String tel = CipherUtil.decrypt(masterKey, iv, encryptedTel);

			// 電話番号を3分割してハイフンをつけてくっつける
			String firstTel = null;
			String secondTel = null;
			String lastTel = null;
			if (tel.length() == 11) {
				firstTel = tel.substring(0, 3);
				secondTel = tel.substring(3, 7);
				lastTel = tel.substring(7, 11);
			} else {
				firstTel = tel.substring(0, 3);
				secondTel = tel.substring(3, 6);
				lastTel = tel.substring(6, 10);
			}

			// 郵便番号のデータベースからの取り出し
			String reEncryptedPostCode = dao.getPostCode(id);
			String encryptedPostCode = CipherUtil.commonDecrypt(reEncryptedPostCode);
			String postCode = CipherUtil.decrypt(masterKey, iv, encryptedPostCode);
			// 郵便番号をに分割する
			String FirstPostCode = postCode.substring(0, 3);
			String LastPostCode = postCode.substring(3, 7);

			// 住所のデータベースからの取り出し
			String reEncryptedAddress = dao.getAddress(id);
			String encryptedAddress = CipherUtil.commonDecrypt(reEncryptedAddress);
			String address = CipherUtil.decrypt(masterKey, iv, encryptedAddress);

			// クラス名のデータベースからの取り出し
			String reEncryptedClassName = dao.getClassName(id);
			String encryptedClassName = CipherUtil.commonDecrypt(reEncryptedClassName);
			String className = CipherUtil.decrypt(masterKey, iv, encryptedClassName);

			// 学籍番号のデータベースからの取り出し
			String reEncryptedStudentNumber = dao.getStudentNumber(id);
			String encryptedStudentNumber = CipherUtil.commonDecrypt(reEncryptedStudentNumber);
			String studentNumber = CipherUtil.decrypt(masterKey, iv, encryptedStudentNumber);

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/generalStudentPDF/自転車等通学許可願.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));
			// PDFへの書き込み
			// 申請年月日
			editor.writeText(font, requestYear, 430f, 733f, 70f, "left", 12);
			editor.writeText(font, requestMonth, 470f, 733f, 70f, "left", 12);
			editor.writeText(font, requestDay, 510f, 733f, 70f, "left", 12);
			// クラス名・学籍番号・名前
			editor.writeText(font, className, 242f, 676f, 125f, "center", 12);
			editor.writeText(font, studentNumber, 418f, 676f, 120f, "center", 12);
			editor.writeText(font, name, 242f, 648f, 270f, "center", 12);
			// 郵便番号
			editor.writeText(font, FirstPostCode, 262f, 622f, 180f, "left", 10);
			editor.writeText(font, LastPostCode, 305f, 622f, 180f, "left", 10);
			// 電話番号
			editor.writeText(font, firstTel, 402f, 622f, 40f, "left", 10);
			editor.writeText(font, secondTel, 447f, 622f, 40f, "left", 10);
			editor.writeText(font, lastTel, 497f, 622f, 40f, "left", 10);

			// 住所の文字数によって表示する位置を変える
			if (address.length() < 25) {
				editor.writeText(font, address, 242f, 603f, 295f, "left", 12);
			} else {
				editor.writeText(font, address.substring(0, 24), 242f, 610f, 295f, "left", 12);
				editor.writeText(font, address.substring(24, address.length()), 242f, 595f, 295f, "left", 12);
			}
			// 保護者
			editor.writeText(font, patron, 242f, 576f, 112f, "center", 12);
			// 保護者電話番号
			if (patronTel.length() == 11) {
				firstTel = patronTel.substring(0, 3);
				secondTel = patronTel.substring(3, 7);
				lastTel = patronTel.substring(7, 11);
				patronTel = firstTel + "-" + secondTel + "-" + lastTel;
			} else {
				firstTel = patronTel.substring(0, 3);
				secondTel = patronTel.substring(3, 6);
				lastTel = patronTel.substring(6, 10);
				patronTel = firstTel + "-" + secondTel + "-" + lastTel;
			}
			editor.writeText(font, patronTel, 418f, 576f, 120f, "center", 12);
			// 名前・学籍番号
			editor.writeText(font, name, 68f, 290f, 103f, "center", 12);
			editor.writeText(font, studentNumber, 240f, 290f, 68f, "center", 12);

			float num = 0;
			for (int i = 0; i < 2; i++) {
				// 自転車か原動機付自転車か選択
				if (classification.equals("自転車")) {
					editor.writeText(font, "✓", 117f, 519f - num, 50f, "left", 12);
				} else {
					editor.writeText(font, "✓", 192f, 519f - num, 50f, "left", 12);
				}
				// 期間（自）
				editor.writeText(font, startYear, 150f, 501f - num, 180f, "left", 12);
				editor.writeText(font, startMonth, 190f, 501f - num, 180f, "left", 12);
				editor.writeText(font, startDay, 235f, 501f - num, 180f, "left", 12);
				// 期間（至）
				editor.writeText(font, endYear, 330f, 501f - num, 180f, "left", 12);
				editor.writeText(font, endMonth, 370f, 501f - num, 180f, "left", 12);
				editor.writeText(font, endDay, 412f, 501f - num, 180f, "left", 12);
				// 原動機付自転車はナンバー、自転車は防犯登録番号
				editor.writeText(font, registrationNumber, 108f, 482f - num, 160f, "center", 12);
				// 車種・色
				editor.writeText(font, modelAndColor, 320f, 482f - num, 217f, "center", 12);

				num += 392f;
			}
			// Close and save
			editor.close("自転車等通学許可願.pdf");
			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Permission Bike");
			// PDF作成成功画面に遷移
			request.setAttribute("createPDF", "「自転車等通学許可願」を作成しました。当該書類は印刷後、氏名及び保護者欄に押印をお願いします。");
			return "create-pdf-success.jsp";
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "permission-bike.jsp";
		}
	}
}
