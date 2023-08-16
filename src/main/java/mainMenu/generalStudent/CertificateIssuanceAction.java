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
		String englishFfirstName = request.getParameter("englishFfirstName");
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
					&& (englishFfirstName == null || englishFfirstName.isEmpty())) {
				// すべてが空の場合はエラーを返す
				request.setAttribute("nameError", "英文証明書は英語の姓と名を入力してください。");
			} else if (englishLastName == null || englishLastName.isEmpty() || englishFfirstName == null
					|| englishFfirstName.isEmpty()) {
				// 何か一つだけ入力されている場合
				request.setAttribute("nameError", "英語の姓と名を全て入力してください。");
			} else if (englishLastName.length() > 32 || englishFfirstName.length() > 32) {
				request.setAttribute("nameError", "名前は32文字以下で入力してください。");
			}
			// 入力チェックをtrueにする
			checkEnglish = true;
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
		}

		// 年月日が存在しない日付の場合はエラーにする
		try {
			int checkYear = Integer.parseInt(requestYear);
			int checkMonth = Integer.parseInt(requestMonth);
			int checkDay = Integer.parseInt(requestDay);

			// 届出年月日の日付の妥当性チェック
			LocalDate requestDate = LocalDate.of(checkYear, checkMonth, checkDay);

		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
		}

		// 文字数が18文字より多い場合はエラーを返す
		if (propose.length() > 18) {
			request.setAttribute("valueLongError", "18文字以下で入力してください。");
		}

		// 少なくとも1つの項目が入力されている必要がある
		if (checkCredentials || checkEnglish || checkReissue || checkOther) {
			// 何かしらの変更がある場合は問題なし
		} else {
			request.setAttribute("inputError", "変更する項目を入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("nameError") != null || request.getAttribute("inputError") != null
				|| request.getAttribute("valueLongError") != null
				|| request.getAttribute("dayError") != null) {
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
			String oldLastName = CipherUtil.decrypt(masterKey, iv, encryptedLastName);
			// 名のデータベースからの取り出し
			String reEncryptedFirstName = dao.getFirstName(id);
			String encryptedFirstName = CipherUtil.commonDecrypt(reEncryptedFirstName);
			String oldFirstName = CipherUtil.decrypt(masterKey, iv, encryptedFirstName);

			String name = oldLastName + " " + oldFirstName;

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

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/generalStudentPDF/証明書交付願.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));

			editor.writeText(font, requestYear, 420f, 645f, 70f, "left", 12);
			editor.writeText(font, requestMonth, 480f, 645f, 70f, "left", 12);
			editor.writeText(font, requestDay, 523f, 645f, 70f, "left", 12);
			editor.writeText(font, studentNumber, 187f, 645f, 132f, "center", 12);
			editor.writeText(font, name, 187f, 609f, 363f, "center", 12);
			if (className.equals("ＩＴ・ゲームソフト科")) {
				if (schoolYear.equals("1")) {
					if (classNumber.equals("1")) {
						editor.writeText(font, "✓", 193f, 564f, 112f, "left", 12);
					} else if (classNumber.equals("2")) {
						editor.writeText(font, "✓", 259f, 564f, 125f, "left", 12);
					} else {
						editor.writeText(font, "✓", 319f, 564f, 125f, "left", 12);
					}
				} else {
					if (classNumber.equals("1")) {
						editor.writeText(font, "✓", 193f, 539f, 112f, "left", 12);
					} else {
						editor.writeText(font, "✓", 259f, 539f, 112f, "left", 12);
					}
				}
			} else if (className.equals("ＡＩ・データサイエンス科") && schoolYear.equals("1") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 443f, 564f, 125f, "left", 12);
			} else if (className.equals("グローバルＩＴシステム科") && schoolYear.equals("1") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 379f, 564f, 125f, "left", 12);
			} else if (className.equals("ＩＴライセンス科（通信制）")) {
				editor.writeText(font, "✓", 502f, 564f, 125f, "left", 12);
			} else if (className.equals("ロボット・ＩＯＴソフト科") && schoolYear.equals("2") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 318f, 539f, 125f, "left", 12);
			} else if (className.equals("グローバルＩＴビジネス科") && schoolYear.equals("1") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 378f, 539f, 125f, "left", 12);
			} else if (className.equals("グローバルＩＴビジネス科") && schoolYear.equals("2") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 443f, 539f, 125f, "left", 12);
			}

			if (immigrationBureau.equals("はい")) {
				editor.writeText(font, "✓", 157f, 500f, 50f, "left", 12);
			} else {
				editor.writeText(font, "✓", 299f, 500f, 50f, "left", 12);
			}

			// Close and save
			editor.close("証明書交付願.pdf");
			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Certificate Issuance");
			// PDF作成成功画面に遷移
			request.setAttribute("createPDF", "「証明書交付願」を作成しました。");
			return "create-pdf-success.jsp";
		} catch (

		Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "certificate-issuance.jsp";
		}
	}
}
