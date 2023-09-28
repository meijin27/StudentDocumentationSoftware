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

public class CertificateIssuanceAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(CertificateIssuanceAction.class);

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
		String requestYear = request.getParameter("requestYear");
		String requestMonth = request.getParameter("requestMonth");
		String requestDay = request.getParameter("requestDay");
		String use = request.getParameter("use");
		String propose = request.getParameter("propose");
		String immigrationBureau = request.getParameter("immigrationBureau");

		String proofOfStudent = request.getParameter("proofOfStudent");
		String attendanceRate = request.getParameter("attendanceRate");
		String results = request.getParameter("results");
		String expectedGraduation = request.getParameter("expectedGraduation");
		String diploma = request.getParameter("diploma");
		String certificateCompletion = request.getParameter("certificateCompletion");
		String enrollmentCertificate = request.getParameter("enrollmentCertificate");
		String healthCertificate = request.getParameter("healthCertificate");
		String closedPeriod = request.getParameter("closedPeriod");

		String englishLastName = request.getParameter("englishLastName");
		String englishFirstName = request.getParameter("englishFirstName");
		String englishProofOfStudent = request.getParameter("englishProofOfStudent");
		String englishResults = request.getParameter("englishResults");
		String englishDiploma = request.getParameter("englishDiploma");
		String reissueBrokenStudentID = request.getParameter("reissueBrokenStudentID");
		String reissueLostStudentID = request.getParameter("reissueLostStudentID");
		String reissueTemporaryIdentification = request.getParameter("reissueTemporaryIdentification");
		String internationalRemittanceRequest = request.getParameter("internationalRemittanceRequest");
		String applicationForm = request.getParameter("applicationForm");
		String overseasRemittanceCalculator = request.getParameter("overseasRemittanceCalculator");

		// 入力された値をリクエストに格納	
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			request.setAttribute(paramName, paramValue);
		}

		// 印刷項目確認用変数
		boolean checkCredentials = false;
		boolean checkEnglish = false;
		boolean checkReissue = false;
		boolean checkOther = false;

		// 必須項目に未入力項目があればエラーを返す
		if (requestYear == null || requestMonth == null || requestDay == null
				|| use == null || propose == null || immigrationBureau == null
				|| requestYear.isEmpty() || requestMonth.isEmpty()
				|| requestDay.isEmpty()
				|| use.isEmpty() || propose.isEmpty()
				|| immigrationBureau.isEmpty()

		) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "certificate-issuance.jsp";
		}

		// 証明書の入力のチェック
		// すべてが空の場合はスルー
		if ((proofOfStudent == null || proofOfStudent.isEmpty())
				&& (attendanceRate == null || attendanceRate.isEmpty())
				&& (results == null || results.isEmpty())
				&& (expectedGraduation == null || expectedGraduation.isEmpty())
				&& (diploma == null || diploma.isEmpty())
				&& (certificateCompletion == null || certificateCompletion.isEmpty())
				&& (enrollmentCertificate == null || enrollmentCertificate.isEmpty())
				&& (healthCertificate == null || healthCertificate.isEmpty())
				&& (closedPeriod == null || closedPeriod.isEmpty())) {
		} else {
			// 何か入力されている場合	
			// 入力チェックをtrueにする
			checkCredentials = true;
			// 入力されている証明書のバリデーションチェック
			if ((!proofOfStudent.isEmpty() && !proofOfStudent.matches("^\\d{1}$"))
					&& (!attendanceRate.isEmpty() && !attendanceRate.matches("^\\d{1}$"))
					&& (!results.isEmpty() && !results.matches("^\\d{1}$"))
					&& (!expectedGraduation.isEmpty() && !expectedGraduation.matches("^\\d{1}$"))
					&& (!diploma.isEmpty() && !diploma.matches("^\\d{1}$"))
					&& (!certificateCompletion.isEmpty() && !certificateCompletion.matches("^\\d{1}$"))
					&& (!enrollmentCertificate.isEmpty() && !enrollmentCertificate.matches("^\\d{1}$"))
					&& (!healthCertificate.isEmpty() && !healthCertificate.matches("^\\d{1}$"))
					&& (!closedPeriod.isEmpty() && !closedPeriod.matches("^\\d{1}$"))) {
				request.setAttribute("numberError", "必要枚数は半角数字１桁で入力してください。");
			}
		}

		// 英文の証明書の入力のチェック
		// すべてが空の場合はスルー
		if ((englishProofOfStudent == null || englishProofOfStudent.isEmpty())
				&& (englishResults == null || englishResults.isEmpty())
				&& (englishDiploma == null || englishDiploma.isEmpty())) {
		} else {
			// 何か入力されている場合
			// 英語の姓と名のチェック
			if ((englishLastName == null || englishLastName.isEmpty())
					&& (englishFirstName == null || englishFirstName.isEmpty())) {
				// すべてが空の場合はエラーを返す
				request.setAttribute("nameError", "英文証明書は英語の姓と名を入力してください。");
			} else if (englishLastName == null || englishLastName.isEmpty() || englishFirstName == null
					|| englishFirstName.isEmpty()) {
				// 何か一つだけ入力されている場合
				request.setAttribute("nameError", "英語の姓と名を全て入力してください。");
			} else if (englishLastName.length() > 32 || englishFirstName.length() > 32) {
				request.setAttribute("nameError", "氏名は32文字以下で入力してください。");
			} else if (!englishLastName.matches("^[a-zA-Z\\s]*$") || !englishFirstName.matches("^[a-zA-Z\\s]*$")) {
				request.setAttribute("nameError", "英文証明書を発行する場合の英語氏名欄は英語で入力してください。");
			}
			// 入力チェックをtrueにする
			checkEnglish = true;
			// 入力されている証明書のバリデーションチェック
			if ((!englishProofOfStudent.isEmpty() && !englishProofOfStudent.matches("^\\d{1}$"))
					&& (!englishResults.isEmpty() && !englishResults.matches("^\\d{1}$"))
					&& (!englishDiploma.isEmpty() && !englishDiploma.matches("^\\d{1}$"))) {
				request.setAttribute("numberError", "必要枚数は半角数字１桁で入力してください。");
			}
		}

		// 再発行の証明書の入力のチェック
		// すべてが空の場合はスルー
		if ((reissueBrokenStudentID == null || reissueBrokenStudentID.isEmpty())
				&& (reissueLostStudentID == null || reissueLostStudentID.isEmpty())
				&& (reissueTemporaryIdentification == null || reissueTemporaryIdentification.isEmpty())) {
		} else {
			// 何か入力されている場合	
			// 入力チェックをtrueにする
			checkReissue = true;
			// 入力されている証明書のバリデーションチェック
			if ((!reissueBrokenStudentID.isEmpty() && !reissueBrokenStudentID.matches("^\\d{1}$"))
					&& (!reissueLostStudentID.isEmpty() && !reissueLostStudentID.matches("^\\d{1}$"))
					&& (!reissueTemporaryIdentification.isEmpty()
							&& !reissueTemporaryIdentification.matches("^\\d{1}$"))) {
				request.setAttribute("numberError", "必要枚数は半角数字１桁で入力してください。");
			}
		}

		// その他の書類の入力のチェック
		// すべてが空の場合はスルー
		if ((internationalRemittanceRequest == null || internationalRemittanceRequest.isEmpty())
				&& (applicationForm == null || applicationForm.isEmpty())
				&& (overseasRemittanceCalculator == null || overseasRemittanceCalculator.isEmpty())) {
		} else {
			// 何か入力されている場合	
			// 入力チェックをtrueにする
			checkOther = true;
			// 入力されている証明書のバリデーションチェック
			if ((!internationalRemittanceRequest.isEmpty() && !internationalRemittanceRequest.matches("^\\d{1}$"))
					&& (!applicationForm.isEmpty() && !applicationForm.matches("^\\d{1}$"))
					&& (!overseasRemittanceCalculator.isEmpty() && !overseasRemittanceCalculator.matches("^\\d{1}$"))) {
				request.setAttribute("numberError", "必要枚数は半角数字１桁で入力してください。");
			}
		}

		// 年月日が存在しない日付の場合はエラーにする
		try {
			int checkYear = Integer.parseInt(requestYear);
			int checkMonth = Integer.parseInt(requestMonth);
			int checkDay = Integer.parseInt(requestDay);

			// 届出年月日の日付の妥当性チェック
			LocalDate requestDate = LocalDate.of(checkYear, checkMonth, checkDay);
		} catch (NumberFormatException e) {
			request.setAttribute("dayError", "年月日は数字で入力してください。");
		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
		}

		// 文字数が18文字より多い場合はエラーを返す。セレクトボックス・ラジオボタンの有効範囲画外の場合もエラーを返す。
		if (propose.length() > 18 || immigrationBureau.length() > 3 || requestYear.length() > 4
				|| requestMonth.length() > 2 || requestDay.length() > 2) {
			request.setAttribute("valueLongError", "18文字以下で入力してください。");
		}

		// 提出先が「はい」「いいえ」以外の場合はエラーを返す
		if (!(immigrationBureau.equals("はい") || immigrationBureau.equals("いいえ"))) {
			request.setAttribute("innerError", "提出先は「はい」「いいえ」から選択してください");
		}

		// 少なくとも1つの項目が入力されている必要がある
		if (checkCredentials || checkEnglish || checkReissue || checkOther) {
			// 何かしらの変更がある場合は問題なし
		} else {
			request.setAttribute("inputError", "PDF出力する項目を入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("nameError") != null || request.getAttribute("inputError") != null
				|| request.getAttribute("valueLongError") != null
				|| request.getAttribute("dayError") != null || request.getAttribute("numberError") != null
				|| request.getAttribute("innerError") != null) {
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
			// 郵便番号を２つに分割する
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
			// 学年のデータベースからの取り出し
			String reEncryptedSchoolYear = dao.getSchoolYear(id);
			String encryptedSchoolYear = CipherUtil.commonDecrypt(reEncryptedSchoolYear);
			String schoolYear = CipherUtil.decrypt(masterKey, iv, encryptedSchoolYear);
			// クラス番号のデータベースからの取り出し
			String reEncryptedClassNumber = dao.getClassNumber(id);
			String encryptedClassNumber = CipherUtil.commonDecrypt(reEncryptedClassNumber);
			String classNumber = CipherUtil.decrypt(masterKey, iv, encryptedClassNumber);
			// 学籍番号のデータベースからの取り出し
			String reEncryptedStudentNumber = dao.getStudentNumber(id);
			String encryptedStudentNumber = CipherUtil.commonDecrypt(reEncryptedStudentNumber);
			String studentNumber = CipherUtil.decrypt(masterKey, iv, encryptedStudentNumber);
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

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/generalStudentPDF/証明書交付願.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));
			// PDFへの書き込み
			// 申請年月日
			editor.writeText(font, requestYear, 440f, 759f, 70f, "left", 12);
			editor.writeText(font, requestMonth, 478f, 759f, 70f, "left", 12);
			editor.writeText(font, requestDay, 518f, 759f, 70f, "left", 12);
			// 学籍番号・名前・生年月日
			editor.writeText(font, studentNumber, 80f, 730f, 132f, "center", 12);
			editor.writeText(font, name, 80f, 700f, 274f, "center", 12);
			editor.writeText(font, birthYear, 130f, 676f, 180f, "left", 10);
			editor.writeText(font, birthMonth, 185f, 676f, 180f, "left", 10);
			editor.writeText(font, birthDay, 225f, 676f, 180f, "left", 10);
			// 英文証明書の発行がある場合のみ英文氏名を書き込む
			if (checkEnglish) {
				editor.writeText(font, englishLastName + " " + englishFirstName, 80f, 645f, 274f, "center", 12);
			}
			// 郵便番号
			editor.writeText(font, FirstPostCode, 115f, 629f, 180f, "left", 10);
			editor.writeText(font, LastPostCode, 155f, 629f, 180f, "left", 10);
			// 電話番号
			editor.writeText(font, firstTel, 400f, 629f, 40f, "left", 10);
			editor.writeText(font, secondTel, 450f, 629f, 40f, "left", 10);
			editor.writeText(font, lastTel, 510f, 629f, 40f, "left", 10);

			// 住所の文字数によって表示する位置を変える
			if (address.length() < 39) {
				editor.writeText(font, address, 80f, 608f, 470f, "left", 12);
			} else {
				editor.writeText(font, address.substring(0, 39), 80f, 615f, 470f, "left", 12);
				editor.writeText(font, address.substring(39, address.length()), 80f, 600f, 470f, "left", 12);
			}
			// 用途
			if (use.equals("就職・進学活動")) {
				editor.writeText(font, "✓", 86f, 584f, 112f, "left", 10);
			} else if (use.equals("奨学金等申請")) {
				editor.writeText(font, "✓", 180f, 584f, 112f, "left", 10);
			} else if (use.equals("被扶養者控除申請")) {
				editor.writeText(font, "✓", 275f, 584f, 112f, "left", 10);
			} else if (use.equals("アルバイト")) {
				editor.writeText(font, "✓", 370f, 584f, 112f, "left", 10);
			} else if (use.equals("その他")) {
				editor.writeText(font, "✓", 466f, 584f, 112f, "left", 10);
			} else if (use.equals("留学ビザ更新申請")) {
				editor.writeText(font, "✓", 86f, 565f, 112f, "left", 10);
			} else if (use.equals("就労ビザ申請")) {
				editor.writeText(font, "✓", 180f, 565f, 112f, "left", 10);
			} else if (use.equals("特定活動ビザ申請")) {
				editor.writeText(font, "✓", 275f, 565f, 112f, "left", 10);
			} else if (use.equals("その他のビザ申請")) {
				editor.writeText(font, "✓", 370f, 565f, 112f, "left", 10);
			} else if (use.equals("国民年金申請")) {
				editor.writeText(font, "✓", 466f, 565f, 112f, "left", 10);
			}

			// クラス
			if (className.equals("ＩＴ・ゲームソフト科")) {
				if (schoolYear.equals("1")) {
					if (classNumber.equals("1")) {
						editor.writeText(font, "✓", 387f, 702f, 125f, "left", 8);
					} else if (classNumber.equals("2")) {
						editor.writeText(font, "✓", 432f, 702f, 125f, "left", 8);
					} else {
						editor.writeText(font, "✓", 477f, 702f, 125f, "left", 8);
					}
				} else {
					if (classNumber.equals("1")) {
						editor.writeText(font, "✓", 431f, 686f, 125f, "left", 8);
					} else {
						editor.writeText(font, "✓", 476f, 686f, 125f, "left", 8);
					}
				}
			} else if (className.equals("ＡＩ・データサイエンス科") && schoolYear.equals("1") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 387f, 686f, 125f, "left", 8);
			} else if (className.equals("グローバルＩＴシステム科") && schoolYear.equals("1") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 514f, 702f, 125f, "left", 8);
			} else if (className.equals("ＩＴライセンス科（通信制）")) {
				editor.writeText(font, "✓", 477f, 670f, 125f, "left", 8);
			} else if (className.equals("ロボット・ＩＯＴソフト科") && schoolYear.equals("2") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 513f, 686f, 125f, "left", 8);
			} else if (className.equals("グローバルＩＴビジネス科") && schoolYear.equals("1") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 387f, 670f, 125f, "left", 8);
			} else if (className.equals("グローバルＩＴビジネス科") && schoolYear.equals("2") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 432f, 670f, 125f, "left", 8);
			}

			editor.writeText(font, propose, 80f, 538f, 238f, "center", 12);

			// 入管を選択した場合
			if (immigrationBureau.equals("はい")) {
				editor.writeText(font, "✓", 319f, 538f, 50f, "left", 12);
			}

			// 在学証明書にデータがある場合
			if (proofOfStudent != null && !proofOfStudent.isEmpty()) {
				editor.writeText(font, "✓", 48f, 485f, 112f, "left", 12);
				editor.writeText(font, proofOfStudent, 270f, 485f, 112f, "left", 12);
			}

			// 出席率証明書にデータがある場合
			if (attendanceRate != null && !attendanceRate.isEmpty()) {
				editor.writeText(font, "✓", 48f, 465f, 112f, "left", 12);
				editor.writeText(font, attendanceRate, 270f, 465f, 112f, "left", 12);
			}

			// 成績証明書にデータがある場合
			if (results != null && !results.isEmpty()) {
				editor.writeText(font, "✓", 48f, 445f, 112f, "left", 12);
				editor.writeText(font, results, 270f, 445f, 112f, "left", 12);
			}

			// 卒業見込証明書にデータがある場合
			if (expectedGraduation != null && !expectedGraduation.isEmpty()) {
				editor.writeText(font, "✓", 48f, 426f, 112f, "left", 12);
				editor.writeText(font, expectedGraduation, 270f, 426f, 112f, "left", 12);
			}

			// 卒業証明書にデータがある場合
			if (diploma != null && !diploma.isEmpty()) {
				editor.writeText(font, "✓", 48f, 406f, 112f, "left", 12);
				editor.writeText(font, diploma, 270f, 406f, 112f, "left", 12);
			}

			// 履修証明書にデータがある場合
			if (certificateCompletion != null && !certificateCompletion.isEmpty()) {
				editor.writeText(font, "✓", 48f, 386f, 112f, "left", 12);
				editor.writeText(font, certificateCompletion, 270f, 386f, 112f, "left", 12);
			}

			// 在籍期間証明書にデータがある場合
			if (enrollmentCertificate != null && !enrollmentCertificate.isEmpty()) {
				editor.writeText(font, "✓", 48f, 366f, 112f, "left", 12);
				editor.writeText(font, enrollmentCertificate, 270f, 366f, 112f, "left", 12);
			}

			// 健康診断書にデータがある場合
			if (healthCertificate != null && !healthCertificate.isEmpty()) {
				editor.writeText(font, "✓", 48f, 346f, 112f, "left", 12);
				editor.writeText(font, healthCertificate, 270f, 346f, 112f, "left", 12);
			}

			// 休業期間証明書にデータがある場合
			if (closedPeriod != null && !closedPeriod.isEmpty()) {
				editor.writeText(font, "✓", 48f, 326f, 112f, "left", 12);
				editor.writeText(font, closedPeriod, 270f, 326f, 112f, "left", 12);
			}

			// 在学証明書（英語）にデータがある場合
			if (englishProofOfStudent != null && !englishProofOfStudent.isEmpty()) {
				editor.writeText(font, "✓", 48f, 306f, 112f, "left", 12);
				editor.writeText(font, englishProofOfStudent, 270f, 306f, 112f, "left", 12);
			}

			// 成績証明書（英語）にデータがある場合
			if (englishResults != null && !englishResults.isEmpty()) {
				editor.writeText(font, "✓", 48f, 287f, 112f, "left", 12);
				editor.writeText(font, englishResults, 270f, 287f, 112f, "left", 12);
			}

			// 卒業証明書（英語）にデータがある場合
			if (englishDiploma != null && !englishDiploma.isEmpty()) {
				editor.writeText(font, "✓", 48f, 267f, 112f, "left", 12);
				editor.writeText(font, englishDiploma, 270f, 267f, 112f, "left", 12);
			}

			// 学生証再発行（破損）にデータがある場合
			if (reissueBrokenStudentID != null && !reissueBrokenStudentID.isEmpty()) {
				editor.writeText(font, "✓", 48f, 246f, 112f, "left", 12);
				editor.writeText(font, reissueBrokenStudentID, 270f, 246f, 112f, "left", 12);
			}

			// 学生証再発行（紛失）にデータがある場合
			if (reissueLostStudentID != null && !reissueLostStudentID.isEmpty()) {
				editor.writeText(font, "✓", 48f, 227f, 112f, "left", 12);
				editor.writeText(font, reissueLostStudentID, 270f, 227f, 112f, "left", 12);
			}

			// 仮身分証明書再発行にデータがある場合
			if (reissueTemporaryIdentification != null && !reissueTemporaryIdentification.isEmpty()) {
				editor.writeText(font, "✓", 48f, 207f, 112f, "left", 12);
				editor.writeText(font, reissueTemporaryIdentification, 270f, 207f, 112f, "left", 12);
			}

			// 海外送金依頼書にデータがある場合
			if (internationalRemittanceRequest != null && !internationalRemittanceRequest.isEmpty()) {
				editor.writeText(font, "✓", 48f, 187f, 112f, "left", 12);
			}

			// 所属機関等作成用申請書にデータがある場合
			if (applicationForm != null && !applicationForm.isEmpty()) {
				editor.writeText(font, "✓", 48f, 167f, 112f, "left", 12);
			}

			// 海外送金計算書にデータがある場合
			if (overseasRemittanceCalculator != null && !overseasRemittanceCalculator.isEmpty()) {
				editor.writeText(font, "✓", 49f, 142f, 112f, "left", 12);
			}

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Certificate Issuance");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "証明書交付願");
			// Close and save
			editor.close("Certificate_Issuance.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "certificate-issuance.jsp";
		}
	}
}
