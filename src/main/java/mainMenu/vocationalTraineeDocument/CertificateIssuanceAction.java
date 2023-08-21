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

public class CertificateIssuanceAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(CertificateIssuanceAction.class);

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
		String reason = request.getParameter("reason");
		String propose = request.getParameter("propose");
		String proofOfStudent = request.getParameter("proofOfStudent");
		String certificateOfCompletion = request.getParameter("certificateOfCompletion");
		String certificateOfExpectedCompletion = request.getParameter("certificateOfExpectedCompletion");
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
		if (reason == null || propose == null || requestYear == null
				|| requestMonth == null || requestDay == null || reason.isEmpty() || propose.isEmpty()
				|| requestYear.isEmpty() || requestMonth.isEmpty()
				|| requestDay.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "certificate-issuance.jsp";
		}

		// 証明書の必要部数を全て選択していない場合、エラーを返す
		if (proofOfStudent.isEmpty() && certificateOfCompletion.isEmpty()
				&& certificateOfExpectedCompletion.isEmpty()) {
			request.setAttribute("nullError", "証明書は1種類以上発行する必要があります。");
			return "certificate-issuance.jsp";
		}

		// 年月日が存在しない日付の場合はエラーにする
		try {
			int checkYear = Integer.parseInt(requestYear);
			int checkMonth = Integer.parseInt(requestMonth);
			int checkDay = Integer.parseInt(requestDay);

			// 日付の妥当性チェック
			LocalDate date = LocalDate.of(checkYear, checkMonth, checkDay);

		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
		}

		// 文字数が64文字より多い場合はエラーを返す
		if (reason.length() > 64 || propose.length() > 64) {
			request.setAttribute("valueLongError", "64文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("dayError") != null || request.getAttribute("valueLongError") != null) {
			return "certificate-issuance.jsp";
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

			// 姓（ふりがな）のデータベースからの取り出し
			String reEncryptedLastNameRuby = dao.getLastNameRuby(id);
			String encryptedLastNameRuby = CipherUtil.commonDecrypt(reEncryptedLastNameRuby);
			String lastNameRuby = CipherUtil.decrypt(masterKey, iv, encryptedLastNameRuby);
			// 名（ふりがな）のデータベースからの取り出し
			String reEncryptedFirstNameRuby = dao.getFirstNameRuby(id);
			String encryptedFirstNameRuby = CipherUtil.commonDecrypt(reEncryptedFirstNameRuby);
			String firstNameRuby = CipherUtil.decrypt(masterKey, iv, encryptedFirstNameRuby);

			String nameRuby = lastNameRuby + "  " + firstNameRuby;

			// 電話番号のデータベースからの取り出し
			String reEncryptedTel = dao.getTel(id);
			String encryptedTel = CipherUtil.commonDecrypt(reEncryptedTel);
			String tel = CipherUtil.decrypt(masterKey, iv, encryptedTel);

			// 電話番号を3分割する
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
			String firstPostCode = postCode.substring(0, 3);
			String lastPostCode = postCode.substring(3, 7);
			// 住所のデータベースからの取り出し
			String reEncryptedAddress = dao.getAddress(id);
			String encryptedAddress = CipherUtil.commonDecrypt(reEncryptedAddress);
			String address = CipherUtil.decrypt(masterKey, iv, encryptedAddress);
			// 誕生年のデータベースからの取り出し
			String reEncryptedBirthYear = dao.getBirthYear(id);
			String encryptedBirthYear = CipherUtil.commonDecrypt(reEncryptedBirthYear);
			String birthYear = CipherUtil.decrypt(masterKey, iv, encryptedBirthYear);
			// 誕生月のデータベースからの取り出し
			String reEncryptedBirthMonth = dao.getBirthMonth(id);
			String encryptedBirthMonth = CipherUtil.commonDecrypt(reEncryptedBirthMonth);
			String birthMonth = CipherUtil.decrypt(masterKey, iv, encryptedBirthMonth);
			// 誕生日のデータベースからの取り出し
			String reEncryptedBirthDay = dao.getBirthDay(id);
			String encryptedBirthDay = CipherUtil.commonDecrypt(reEncryptedBirthDay);
			String birthDay = CipherUtil.decrypt(masterKey, iv, encryptedBirthDay);

			// 生年月日から和暦を算出する
			int year = Integer.parseInt(birthYear);
			int month = Integer.parseInt(birthMonth);
			int day = Integer.parseInt(birthDay);
			String JapaneseYear = null;
			String JapaneseBirthYear = null;

			if (year > 2019 || (year == 2019 && month > 4) || (year == 2019 && month == 4 && day >= 1)) {
				JapaneseYear = "令和";
				JapaneseBirthYear = String.valueOf(year - 2018);
			} else if (year > 1988 || (year == 1989 && month == 1 && day >= 8)) {
				JapaneseYear = "平成";
				JapaneseBirthYear = String.valueOf(year - 1988);
			} else if (year > 1925 || (year == 1926 && month == 12 && day >= 25)) {
				JapaneseYear = "昭和";
				JapaneseBirthYear = String.valueOf(year - 1925);
			}

			// 入学年のデータベースからの取り出し
			String reEncryptedAdmissionYear = dao.getAdmissionYear(id);
			String encryptedAdmissionYear = CipherUtil.commonDecrypt(reEncryptedAdmissionYear);
			String admissionYear = CipherUtil.decrypt(masterKey, iv, encryptedAdmissionYear);
			String JapaneseAdmissionYear = String.valueOf(Integer.parseInt(admissionYear) - 2018);
			// 入学月のデータベースからの取り出し
			String reEncryptedAdmissionMonth = dao.getAdmissionMonth(id);
			String encryptedAdmissionMonth = CipherUtil.commonDecrypt(reEncryptedAdmissionMonth);
			String admissionMonth = CipherUtil.decrypt(masterKey, iv, encryptedAdmissionMonth);
			// 入学日のデータベースからの取り出し
			String reEncryptedAdmissionDay = dao.getAdmissionDay(id);
			String encryptedAdmissionDay = CipherUtil.commonDecrypt(reEncryptedAdmissionDay);
			String admissionDay = CipherUtil.decrypt(masterKey, iv, encryptedAdmissionDay);
			// クラス名のデータベースからの取り出し
			String reEncryptedClassName = dao.getClassName(id);
			String encryptedClassName = CipherUtil.commonDecrypt(reEncryptedClassName);
			String className = CipherUtil.decrypt(masterKey, iv, encryptedClassName);
			// クラス名の末尾に「科」がついていた場合は削除する
			if (className.endsWith("科")) {
				className = className.substring(0, className.length() - 1);
			}
			// クラス名がnullになった場合はエラーを返す
			if (className.length() == 0) {
				request.setAttribute("innerError", "クラス名が不正です。クラス名を修正してください。");
				return "certificate-issuance.jsp";
			}

			// 学生種類のデータベースからの取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			String encryptedStudentType = CipherUtil.commonDecrypt(reEncryptedStudentType);
			String studentType = CipherUtil.decrypt(masterKey, iv, encryptedStudentType);
			// もし学生種類が職業訓練生でなければエラーを返す
			if (!studentType.equals("職業訓練生")) {
				request.setAttribute("innerError", "当該書類は職業訓練生のみが発行可能です。");
				return "certificate-issuance.jsp";
			}

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/vocationalTraineePDF/証明書交付願.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));

			// PDFへの記載
			// クラス名・入校日
			editor.writeText(font, className, 160f, 671f, 260f, "center", 12);
			editor.writeText(font, JapaneseAdmissionYear, 410f, 649f, 40f, "left", 12);
			editor.writeText(font, admissionMonth, 443f, 649f, 40f, "left", 12);
			editor.writeText(font, admissionDay, 475f, 649f, 40f, "left", 12);
			// 名前・ふりがな
			editor.writeText(font, nameRuby, 160f, 613f, 130f, "center", 8);
			editor.writeText(font, name, 160f, 600f, 130f, "center", 12);
			// 生年月日
			if (JapaneseYear.equals("昭和")) {
				editor.drawEllipse(364f, 611f, 40f, 20f);
			} else {
				editor.drawEllipse(363f, 590f, 40f, 20f);
			}
			editor.writeText(font, JapaneseBirthYear, 420f, 607f, 40f, "left", 12);
			editor.writeText(font, birthMonth, 453f, 607f, 40f, "left", 12);
			editor.writeText(font, birthDay, 485f, 607f, 40f, "left", 12);
			// 郵便番号
			editor.writeText(font, firstPostCode, 180f, 562f, 125f, "left", 12);
			editor.writeText(font, lastPostCode, 220f, 562f, 125f, "left", 12);
			// 住所の長さによって書き込み位置を変更する
			if (address.length() > 29) {
				editor.writeText(font, address.substring(0, 29), 160f, 540f, 350f, "left", 12);
				editor.writeText(font, address.substring(29, address.length()), 160f, 520f, 350f, "left", 12);
			} else {
				editor.writeText(font, address, 160f, 530f, 350f, "left", 12);
			}
			// 電話番号
			editor.writeText(font, firstTel, 375f, 499f, 117f, "left", 12);
			editor.writeText(font, secondTel, 423f, 499f, 117f, "left", 12);
			editor.writeText(font, lastTel, 470f, 499f, 117f, "left", 12);
			// 理由の長さによって書き込み位置を変更する
			if (reason.length() > 29) {
				editor.writeText(font, reason.substring(0, 29), 160f, 473f, 350f, "left", 12);
				editor.writeText(font, reason.substring(29, reason.length()), 160f, 453f, 350f, "left", 12);
			} else {
				editor.writeText(font, reason, 160f, 463f, 350f, "left", 12);
			}
			// 提出先
			editor.writeText(font, propose, 160f, 417f, 350f, "center", 12);
			// 交付区分
			if (!proofOfStudent.isEmpty()) {
				editor.writeText(font, "レ", 195f, 372f, 40f, "left", 12);
				editor.writeText(font, proofOfStudent, 443f, 372f, 40f, "left", 12);
			}
			if (!certificateOfCompletion.isEmpty()) {
				editor.writeText(font, "レ", 195f, 351f, 40f, "left", 12);
				editor.writeText(font, certificateOfCompletion, 443f, 351f, 40f, "left", 12);
			}
			if (!certificateOfExpectedCompletion.isEmpty()) {
				editor.writeText(font, "レ", 195f, 330f, 40f, "left", 12);
				editor.writeText(font, certificateOfExpectedCompletion, 443f, 330f, 40f, "left", 12);
			}
			// 申請年月日
			editor.writeText(font, requestYear, 132f, 241f, 40f, "left", 12);
			editor.writeText(font, requestMonth, 163f, 241f, 40f, "left", 12);
			editor.writeText(font, requestDay, 195f, 241f, 40f, "left", 12);
			// 名前
			editor.writeText(font, name, 330f, 156f, 180f, "left", 12);

			// Close and save
			editor.close("証明書交付願.pdf");
			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Vocational Trainee Certificate Issuance");
			// PDF作成成功画面に遷移
			request.setAttribute("createPDF", "「証明書交付願」を作成しました。");
			return "create-pdf-success.jsp";
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "certificate-issuance.jsp";
		}
	}
}
