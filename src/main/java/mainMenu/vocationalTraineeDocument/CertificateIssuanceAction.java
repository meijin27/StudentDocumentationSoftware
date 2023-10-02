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

public class CertificateIssuanceAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(CertificateIssuanceAction.class);

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
		String reason = request.getParameter("reason");
		String propose = request.getParameter("propose");
		String proofOfStudent = request.getParameter("proofOfStudent");
		String certificateOfCompletion = request.getParameter("certificateOfCompletion");
		String certificateOfExpectedCompletion = request.getParameter("certificateOfExpectedCompletion");
		String requestYear = request.getParameter("requestYear");
		String requestMonth = request.getParameter("requestMonth");
		String requestDay = request.getParameter("requestDay");

		// 必須項目に未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(requestYear, requestMonth, requestDay, reason, propose)) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "certificate-issuance.jsp";
		}

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 証明書の必要部数を全て選択していない場合、エラーを返す
		if (ValidationUtil.areAllNullOrEmpty(proofOfStudent, certificateOfCompletion,
				certificateOfExpectedCompletion)) {
			request.setAttribute("nullError", "証明書は1種類以上発行する必要があります。");
			return "certificate-issuance.jsp";
		}

		// 年月日が１・２桁になっていることを検証し、違う場合はエラーを返す
		if (ValidationUtil.isOneOrTwoDigit(requestYear, requestMonth, requestDay)) {
			request.setAttribute("dayError", "年月日は正規の桁数で入力してください。");
		} else {
			if (ValidationUtil.validateDate(requestYear, requestMonth, requestDay)) {
				request.setAttribute("dayError", "存在しない日付です。");
			}
		}

		// 必要枚数は半角1桁でなければエラーを返す
		if (ValidationUtil.isValidSingleDigitOrNullEmpty(proofOfStudent, certificateOfCompletion,
				certificateOfExpectedCompletion)) {
			request.setAttribute("numberError", "必要枚数は半角数字1桁で入力してください。");
		}

		// 文字数が64文字より多い場合はエラーを返す。
		if (ValidationUtil.areValidLengths(64, reason, propose)) {
			request.setAttribute("valueLongError", "64文字以下で入力してください。");
		}

		// 入力値に特殊文字が入っていないか確認する
		if (ValidationUtil.containsForbiddenChars(reason, propose)) {
			request.setAttribute("validationError", "使用できない特殊文字が含まれています");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "certificate-issuance.jsp";
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

			// 姓のふりがなの取り出し
			String reEncryptedLastNameRuby = dao.getLastNameRuby(id);
			String lastNameRuby = decrypt.getDecryptedDate(result, reEncryptedLastNameRuby);
			// 名のふりがなの取り出し
			String reEncryptedFirstNameRuby = dao.getFirstNameRuby(id);
			String firstNameRuby = decrypt.getDecryptedDate(result, reEncryptedFirstNameRuby);

			// 電話番号のデータベースからの取り出し
			String reEncryptedTel = dao.getTel(id);
			String tel = decrypt.getDecryptedDate(result, reEncryptedTel);

			// 郵便番号のデータベースからの取り出し
			String reEncryptedPostCode = dao.getPostCode(id);
			String postCode = decrypt.getDecryptedDate(result, reEncryptedPostCode);

			// 住所のデータベースからの取り出し
			String reEncryptedAddress = dao.getAddress(id);
			String address = decrypt.getDecryptedDate(result, reEncryptedAddress);

			// 誕生年のデータベースからの取り出し
			String reEncryptedBirthYear = dao.getBirthYear(id);
			String birthYear = decrypt.getDecryptedDate(result, reEncryptedBirthYear);
			// 誕生月のデータベースからの取り出し
			String reEncryptedBirthMonth = dao.getBirthMonth(id);
			String birthMonth = decrypt.getDecryptedDate(result, reEncryptedBirthMonth);
			// 誕生日のデータベースからの取り出し
			String reEncryptedBirthDay = dao.getBirthDay(id);
			String birthDay = decrypt.getDecryptedDate(result, reEncryptedBirthDay);

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
			String admissionYear = decrypt.getDecryptedDate(result, reEncryptedAdmissionYear);
			String JapaneseAdmissionYear = String.valueOf(Integer.parseInt(admissionYear) - 2018);
			// 入学月のデータベースからの取り出し
			String reEncryptedAdmissionMonth = dao.getAdmissionMonth(id);
			String admissionMonth = decrypt.getDecryptedDate(result, reEncryptedAdmissionMonth);
			// 入学日のデータベースからの取り出し
			String reEncryptedAdmissionDay = dao.getAdmissionDay(id);
			String admissionDay = decrypt.getDecryptedDate(result, reEncryptedAdmissionDay);
			// クラス名のデータベースからの取り出し
			String reEncryptedClassName = dao.getClassName(id);
			String className = decrypt.getDecryptedDate(result, reEncryptedClassName);

			// 学生種類のデータベースからの取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			String studentType = decrypt.getDecryptedDate(result, reEncryptedStudentType);

			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(lastName, firstName, lastNameRuby, firstNameRuby, tel, birthYear,
					birthMonth,
					birthDay, admissionYear, admissionMonth, admissionDay, studentType, className)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// もし学生種類が職業訓練生でなければエラーを返す
			if (!studentType.equals("職業訓練生")) {
				request.setAttribute("innerError", "当該書類は職業訓練生のみが発行可能です。");
				return "certificate-issuance.jsp";
			}

			// クラス名の末尾に「科」がついていた場合は削除する
			if (className.endsWith("科")) {
				className = className.substring(0, className.length() - 1);
			}

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

			// 郵便番号を２つに分割する
			String firstPostCode = postCode.substring(0, 3);
			String lastPostCode = postCode.substring(3, 7);

			// 姓名を結合する
			String name = lastName + " " + firstName;
			// ふりがなを結合する
			String nameRuby = lastNameRuby + "  " + firstNameRuby;

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
				editor.writeText(font, "レ", 193f, 372f, 40f, "left", 12);
				editor.writeText(font, proofOfStudent, 443f, 372f, 40f, "left", 12);
			}
			if (!certificateOfCompletion.isEmpty()) {
				editor.writeText(font, "レ", 193f, 351f, 40f, "left", 12);
				editor.writeText(font, certificateOfCompletion, 443f, 351f, 40f, "left", 12);
			}
			if (!certificateOfExpectedCompletion.isEmpty()) {
				editor.writeText(font, "レ", 193f, 330f, 40f, "left", 12);
				editor.writeText(font, certificateOfExpectedCompletion, 443f, 330f, 40f, "left", 12);
			}
			// 申請年月日
			editor.writeText(font, requestYear, 132f, 241f, 40f, "left", 12);
			editor.writeText(font, requestMonth, 163f, 241f, 40f, "left", 12);
			editor.writeText(font, requestDay, 195f, 241f, 40f, "left", 12);
			// 名前
			editor.writeText(font, name, 330f, 156f, 180f, "left", 12);

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Vocational Trainee Certificate Issuance");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "証明書交付願");
			// Close and save
			editor.close("Vocational_Trainee_Certificate_Issuance.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "certificate-issuance.jsp";
		}
	}
}
