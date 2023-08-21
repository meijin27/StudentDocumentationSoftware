package mainMenu.internationalStudent;

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

public class PeriodUpdateSecondAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(PeriodUpdateSecondAction.class);

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

		String testName = request.getParameter("testName");
		String attainedLevelOrScore = request.getParameter("attainedLevelOrScore");
		String organization = request.getParameter("organization");
		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String others = request.getParameter("others");
		String self = request.getParameter("self");
		String supporterLivingAbroad = request.getParameter("supporterLivingAbroad");
		String supporterInJapan = request.getParameter("supporterInJapan");
		String scholarship = request.getParameter("scholarship");
		String otherDisbursement = request.getParameter("otherDisbursement");
		String carryingAbroad = request.getParameter("carryingAbroad");
		String carryingName = request.getParameter("carryingName");
		String carryingTime = request.getParameter("carryingTime");
		String remittancesAbroad = request.getParameter("remittancesAbroad");
		String otherRemittances = request.getParameter("otherRemittances");
		String supporterName = request.getParameter("supporterName");
		String supporterAddress = request.getParameter("supporterAddress");
		String supporterTel = request.getParameter("supporterTel");
		String supporterIncome = request.getParameter("supporterIncome");
		String supporterEmployment = request.getParameter("supporterEmployment");
		String supporterWorkTel = request.getParameter("supporterWorkTel");

		// 入力された値をリクエストに格納	
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			request.setAttribute(paramName, paramValue);
		}

		// 更新項目確認用変数
		boolean checkTestName = false;
		boolean checkOrganization = false;
		boolean checkOthers = false;

		// 試験名と点数が全て未入力なら問題なし
		if ((testName == null || testName.isEmpty())
				&& (attainedLevelOrScore == null || attainedLevelOrScore.isEmpty())) {
		} else if (testName == null || testName.isEmpty() || attainedLevelOrScore == null
				|| attainedLevelOrScore.isEmpty()) {
			// どちらかだけ入力されている場合
			request.setAttribute("testNameError", "試験名と級又は点数は両方とも入力してください。");
		} else if (testName.length() > 32 || attainedLevelOrScore.length() > 32) {
			// 文字数が32文字より多い場合はエラーを返す
			request.setAttribute("testNameError", "32文字以下で入力してください。");
		} else {
			checkTestName = true;
		}

		// 日本語教育を受けた機関と期間が全て未入力なら問題なし
		if ((organization == null || organization.isEmpty())
				&& (startYear == null || startYear.isEmpty()) && (startMonth == null || startMonth.isEmpty())
				&& (endYear == null || endYear.isEmpty()) && (endMonth == null || endMonth.isEmpty())) {
		} else if (organization == null || organization.isEmpty()
				|| startYear == null || startYear.isEmpty() || startMonth == null || startMonth.isEmpty()
				|| endYear == null || endYear.isEmpty() || endMonth == null || endMonth.isEmpty()) {
			// どれかだけ入力されている場合
			request.setAttribute("organizationError", "日本語教育を受けた機関と期間は全て入力してください。");
		} else if (organization.length() > 32) {
			// 文字数が32文字より多い場合はエラーを返す
			request.setAttribute("organizationError", "32文字以下で入力してください。");
		} else if (Integer.parseInt(startYear) > Integer.parseInt(endYear)
				|| (startYear.equals(endYear) && (Integer.parseInt(startMonth) > Integer.parseInt(endMonth)))) {
			// 教育開始が終了より後の場合はエラーを返す
			request.setAttribute("organizationError", "教育開始が終了より後になっています。");
		} else {
			checkOrganization = true;
		}

		// その他が全て未入力なら問題なし
		if (others == null || others.isEmpty()) {
		} else if (others.length() > 64) {
			// 文字数が64文字より多い場合はエラーを返す
			request.setAttribute("othersError", "64文字以下で入力してください。");
		} else {
			checkOthers = true;
		}

		// 日本語能力の項目は少なくとも1つの項目が入力されている必要がある
		if (!checkTestName && !checkOrganization && !checkOthers) {
			request.setAttribute("inputError", "日本語能力項目を一つ以上入力してください。");
		}

		// 更新項目確認用変数
		boolean checkSelf = false;
		boolean checkSupporterLivingAbroad = false;
		boolean checkSupporterInJapan = false;
		boolean checkScholarship = false;
		boolean checkOtherDisbursement = false;

		// 本人負担が全て未入力なら問題なし
		if (self == null || self.isEmpty()) {
		} else if (self.length() > 32) {
			// 文字数が32文字より多い場合はエラーを返す
			request.setAttribute("payError", "32文字以下で入力してください。");
		} else {
			checkSelf = true;
		}

		// 在外経費支弁者負担が全て未入力なら問題なし
		if (supporterLivingAbroad == null || supporterLivingAbroad.isEmpty()) {
		} else if (supporterLivingAbroad.length() > 32) {
			// 文字数が32文字より多い場合はエラーを返す
			request.setAttribute("payError", "32文字以下で入力してください。");
		} else {
			checkSupporterLivingAbroad = true;
		}

		// 在日経費支弁者負担が全て未入力なら問題なし
		if (supporterInJapan == null || supporterInJapan.isEmpty()) {
		} else if (supporterInJapan.length() > 32) {
			// 文字数が32文字より多い場合はエラーを返す
			request.setAttribute("payError", "32文字以下で入力してください。");
		} else {
			checkSupporterInJapan = true;
		}

		// 奨学金が全て未入力なら問題なし
		if (scholarship == null || scholarship.isEmpty()) {
		} else if (scholarship.length() > 32) {
			// 文字数が32文字より多い場合はエラーを返す
			request.setAttribute("payError", "32文字以下で入力してください。");
		} else {
			checkScholarship = true;
		}

		// その他が全て未入力なら問題なし
		if (otherDisbursement == null || otherDisbursement.isEmpty()) {
		} else if (otherDisbursement.length() > 32) {
			// 文字数が32文字より多い場合はエラーを返す
			request.setAttribute("payError", "32文字以下で入力してください。");
		} else {
			checkOtherDisbursement = true;
		}

		// 滞在費の支弁方法は少なくとも1つの項目が入力されている必要がある
		if (!checkSelf && !checkSupporterLivingAbroad && !checkSupporterInJapan && !checkScholarship
				&& !checkOtherDisbursement) {
			request.setAttribute("inputError", "滞在費の支弁方法を一つ以上入力してください。");
		}

		// 更新項目確認用変数
		boolean checkCarryingAbroad = false;
		boolean checkRemittancesAbroad = false;
		boolean checkOtherRemittances = false;

		// 外国からの携行が全て未入力なら問題なし
		if ((carryingAbroad == null || carryingAbroad.isEmpty())
				&& (carryingName == null || carryingName.isEmpty())
				&& (carryingTime == null || carryingTime.isEmpty())) {
		} else if (carryingAbroad == null || carryingAbroad.isEmpty() || carryingName == null || carryingName.isEmpty()
				|| carryingTime == null || carryingTime.isEmpty()) {
			// どれかだけ入力されている場合
			request.setAttribute("carryingAbroadError", "外国からの携行は携行者と携行時期も入力してください。");
		} else if (carryingAbroad.length() > 32 || carryingName.length() > 32 || carryingTime.length() > 32) {
			// 文字数が32文字より多い場合はエラーを返す
			request.setAttribute("carryingAbroadError", "32文字以下で入力してください。");
		} else {
			checkCarryingAbroad = true;
		}

		// 外国からの送金が全て未入力なら問題なし
		if (remittancesAbroad == null || remittancesAbroad.isEmpty()) {
		} else if (remittancesAbroad.length() > 16) {
			// 文字数が32文字より多い場合はエラーを返す
			request.setAttribute("remittancesAbroadError", "32文字以下で入力してください。");
		} else {
			checkRemittancesAbroad = true;
		}

		// その他が全て未入力なら問題なし
		if (otherRemittances == null || otherRemittances.isEmpty()) {
		} else if (otherRemittances.length() > 16) {
			// 文字数が32文字より多い場合はエラーを返す
			request.setAttribute("otherRemittancesError", "32文字以下で入力してください。");
		} else {
			checkOtherRemittances = true;
		}

		// 送金・携行等の別は少なくとも1つの項目が入力されている必要がある
		if (!checkCarryingAbroad && !checkRemittancesAbroad && !checkOtherRemittances) {
			request.setAttribute("inputError", "送金・携行等の別は一つ以上入力してください。");
		}

		// 更新項目確認用変数
		boolean checkSupporter = false;

		// 経費支弁者が全て未入力なら問題なし
		if ((supporterName == null || supporterName.isEmpty())
				&& (supporterAddress == null || supporterAddress.isEmpty())
				&& (supporterTel == null || supporterTel.isEmpty())
				&& (supporterIncome == null || supporterIncome.isEmpty())
				&& (supporterEmployment == null || supporterEmployment.isEmpty())
				&& (supporterWorkTel == null || supporterWorkTel.isEmpty())) {
		} else if (supporterName == null || supporterName.isEmpty() ||
				supporterAddress == null || supporterAddress.isEmpty() ||
				supporterTel == null || supporterTel.isEmpty() ||
				supporterIncome == null || supporterIncome.isEmpty() ||
				supporterEmployment == null || supporterEmployment.isEmpty() ||
				supporterWorkTel == null || supporterWorkTel.isEmpty()) {
			// どれかだけ入力されている場合
			request.setAttribute("supporterError", "経費支弁者を入力する場合は経費支弁者の全項目を入力してください。");
		} else if (supporterName.length() > 32 || supporterAddress.length() > 32 || supporterTel.length() > 32
				|| supporterIncome.length() > 32 || supporterEmployment.length() > 32
				|| supporterWorkTel.length() > 32) {
			// 文字数が32文字より多い場合はエラーを返す
			request.setAttribute("supporterError", "32文字以下で入力してください。");
		} else {
			checkSupporter = true;
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("testNameError") != null || request.getAttribute("organizationError") != null
				|| request.getAttribute("othersError") != null || request.getAttribute("payError") != null
				|| request.getAttribute("carryingAbroadError") != null
				|| request.getAttribute("remittancesAbroadError") != null
				|| request.getAttribute("otherRemittancesError") != null
				|| request.getAttribute("supporterError") != null
				|| request.getAttribute("inputError") != null) {
			return "period-update-second.jsp";
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

			// 学生種類のデータベースからの取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			// 最初にデータベースから取り出したデータがnullの場合、初期設定をしていないためログインページにリダイレクト
			if (reEncryptedStudentType == null) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}
			String encryptedStudentType = CipherUtil.commonDecrypt(reEncryptedStudentType);
			String studentType = CipherUtil.decrypt(masterKey, iv, encryptedStudentType);
			// もし学生種類が留学生でなければエラーを返す
			if (!studentType.equals("留学生")) {
				request.setAttribute("exchangeStudentError", "当該書類は留学生のみが発行可能です。");
				return "period-update-second.jsp";
			}

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/internationalStudentPDF/在留期間更新許可申請書2枚目.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));
			// PDFへの書き込み

			// 学校名	
			editor.writeText(font, "横浜システム工学院専門学校", 145f, 771f, 375f, "center", 12);
			// 学校住所
			editor.writeText(font, "神奈川県横浜市旭区東希望が丘128-4", 128f, 746f, 180f, "center", 10);
			// 学校電話番号
			editor.writeText(font, "045-367-1881", 400f, 746f, 120f, "center", 12);

			// 日本語能力
			// 試験名と点数
			if (checkTestName) {
				editor.writeText(font, "✓", 77f, 557f, 40f, "left", 8);
				editor.writeText(font, testName, 115f, 523f, 195f, "center", 12);
				editor.writeText(font, attainedLevelOrScore, 355f, 523f, 165f, "center", 12);
			}

			// 日本語教育を受けた機関と期間
			if (checkOrganization) {
				editor.writeText(font, "✓", 77f, 512f, 40f, "left", 8);
				editor.writeText(font, organization, 145f, 488f, 193f, "center", 12);
				editor.writeText(font, startYear, 170f, 463f, 40f, "left", 12);
				editor.writeText(font, startMonth, 240f, 463f, 112f, "left", 12);
				editor.writeText(font, endYear, 340f, 463f, 70f, "left", 12);
				editor.writeText(font, endMonth, 405f, 463f, 70f, "left", 12);
			}

			// その他
			if (checkOthers) {
				editor.writeText(font, "✓", 77f, 452f, 40f, "left", 8);
				if (others.length() < 33) {
					editor.writeText(font, others, 130f, 440f, 390f, "left", 12);
				} else {
					editor.writeText(font, others.substring(0, 32), 130f, 440f, 390f, "left", 12);
					editor.writeText(font, others.substring(32, others.length()), 130f, 417f, 390f, "left", 12);
				}
			}

			// 支弁方法及び月平均支弁額
			// 本人負担
			if (checkSelf) {
				editor.writeText(font, "✓", 77f, 273f, 40f, "left", 8);
				editor.writeText(font, self, 142f, 261f, 112f, "center", 12);
			}

			// 在外経費支弁者負担
			if (checkSupporterLivingAbroad) {
				editor.writeText(font, "✓", 315f, 273f, 40f, "left", 8);
				editor.writeText(font, supporterLivingAbroad, 425f, 261f, 80f, "center", 12);
			}

			// 在日経費支弁者負担
			if (checkSupporterInJapan) {
				editor.writeText(font, "✓", 77f, 247f, 40f, "left", 8);
				editor.writeText(font, supporterInJapan, 185f, 235f, 110f, "center", 12);
			}

			// 奨学金
			if (checkScholarship) {
				editor.writeText(font, "✓", 357f, 247f, 40f, "left", 8);
				editor.writeText(font, scholarship, 425f, 235f, 81f, "center", 12);
			}

			// その他
			if (checkOtherDisbursement) {
				editor.writeText(font, "✓", 77f, 222f, 40f, "left", 8);
				editor.writeText(font, otherDisbursement, 130f, 210f, 110f, "center", 12);
			}

			// 送金・携行等の別
			// 外国からの携行
			if (checkCarryingAbroad) {
				editor.writeText(font, "✓", 77f, 185f, 40f, "left", 8);
				editor.writeText(font, carryingAbroad, 170f, 173f, 112f, "center", 12);
				editor.writeText(font, carryingName, 145f, 148f, 82f, "center", 12);
				editor.writeText(font, carryingTime, 298f, 148f, 55f, "center", 12);
			}

			// 外国からの送金
			if (checkRemittancesAbroad) {
				editor.writeText(font, "✓", 315f, 185f, 40f, "left", 8);
				editor.writeText(font, remittancesAbroad, 425f, 172f, 81f, "center", 12);
			}

			// その他
			if (checkOtherRemittances) {
				editor.writeText(font, "✓", 371f, 160f, 40f, "left", 8);
				editor.writeText(font, otherRemittances, 425f, 148f, 81f, "center", 12);
			}

			// 経費支弁者
			if (checkSupporter) {
				editor.writeText(font, supporterName, 130f, 92f, 237f, "center", 12);
				editor.writeText(font, supporterAddress, 130f, 67f, 237f, "center", 12);
				editor.writeText(font, supporterTel, 425f, 67f, 96f, "center", 12);
				editor.writeText(font, supporterEmployment, 215f, 42f, 137f, "center", 12);
				editor.writeText(font, supporterWorkTel, 425f, 42f, 96f, "center", 12);
				editor.writeText(font, supporterIncome, 157f, 18f, 68f, "center", 12);
			}

			// Close and save
			editor.close("在留期間更新許可申請書2枚目.pdf");
			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Period Update Second.jsp");
			// PDF作成成功画面に遷移
			request.setAttribute("createPDF",
					"「在留期間更新許可申請書２枚目」を作成しました。在留期間更新許可申請書は３枚組で、当該書類は２枚目です。(The application form for permission to extend the period of stay is in triplicate, and the said document is the second one.)");
			return "create-pdf-success-second.jsp";
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "period-update-second.jsp";
		}
	}
}
