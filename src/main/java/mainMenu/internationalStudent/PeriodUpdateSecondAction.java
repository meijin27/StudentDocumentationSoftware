package mainMenu.internationalStudent;

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

public class PeriodUpdateSecondAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(PeriodUpdateSecondAction.class);

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
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 更新項目確認用変数
		boolean checkTestName = false;
		boolean checkOrganization = false;
		boolean checkOthers = false;

		// 試験による証明(Proof based on a Japanese Language Test)のエラー処理
		// 試験名と点数が全て未入力なら問題なし
		if (ValidationUtil.areAllNullOrEmpty(testName, attainedLevelOrScore)) {
		} 
		// 入力されている場合
		else {
			// 試験名（Name of the test）のエラー処理
			if (ValidationUtil.isNullOrEmpty(testName)) {
				request.setAttribute("testNameError", "試験名と級又は点数は両方とも入力してください。");
			} 
			// 文字数が32文字より多い場合はエラーを返す
			else if (ValidationUtil.areValidLengths(32, testName)) {
				request.setAttribute("testNameError", "32文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(testName)) {
				request.setAttribute("testNameError", "使用できない特殊文字が含まれています");
			} 
			// 級又は点数（Attained level or score）のエラー処理
			if (ValidationUtil.isNullOrEmpty(attainedLevelOrScore)) {
				request.setAttribute("attainedLevelOrScoreError", "試験名と級又は点数は両方とも入力してください。");
			} 
			// 文字数が32文字より多い場合はエラーを返す
			else if (ValidationUtil.areValidLengths(32, attainedLevelOrScore)) {
				request.setAttribute("attainedLevelOrScoreError", "32文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(attainedLevelOrScore)) {
				request.setAttribute("attainedLevelOrScoreError", "使用できない特殊文字が含まれています");
			} 
			checkTestName = true;
		}
		
		// 日本語教育を受けた教育機関及び期間(Organization and period to have received Japanese language education)のエラー処理
		// 日本語教育を受けた機関と期間が全て未入力なら問題なし
		if (ValidationUtil.areAllNullOrEmpty(organization, startYear, startMonth, endYear, endMonth)) {
		} 
		// 入力されている場合
		else {
			// 機関名（Organization）のエラー処理
			if (ValidationUtil.isNullOrEmpty(organization)) {
				request.setAttribute("organizationError", "日本語教育を受けた機関と期間は全て入力してください。");
			} 
			// 文字数が32文字より多い場合はエラーを返す。
			else if (ValidationUtil.areValidLengths(32, organization)) {
				request.setAttribute("organizationError", "32文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(organization)) {
				request.setAttribute("organizationError", "使用できない特殊文字が含まれています");
			} 
			// 期間（自）(Period from)のエラー処理
			// 機関名（Organization）のエラー処理
			if (ValidationUtil.isNullOrEmpty(startYear,startMonth)) {
				request.setAttribute("startError", "日本語教育を受けた機関と期間は全て入力してください。");
			} 		
			// 期間が年４桁、月２桁になっていることを検証し、違う場合はエラーを返す
			else if (ValidationUtil.isFourDigit(startYear) ||
					ValidationUtil.isOneOrTwoDigit(startMonth)) {
				request.setAttribute("startError", "年月は正規の桁数で入力してください。");
			} 
			
			// 期間（至）(Period to)のエラー処理
			if (ValidationUtil.isNullOrEmpty(endYear,endMonth)) {
				request.setAttribute("endError", "日本語教育を受けた機関と期間は全て入力してください。");
			} 			
			// 期間が年４桁、月２桁になっていることを検証し、違う場合はエラーを返す
			else if (ValidationUtil.isFourDigit(endYear) ||
					ValidationUtil.isOneOrTwoDigit(endMonth)) {
				request.setAttribute("endError", "年月は正規の桁数で入力してください。");
			} 
			
			// 年月日入力にエラーがないことを確認した後に日付の順序のエラーをチェックする
			if (ValidationUtil.areAllNullOrEmpty((String) request.getAttribute("startError"),
					(String) request.getAttribute("endError"))) {
				// 教育開始が終了より後の場合はエラーを返す
				if (Integer.parseInt(startYear) > Integer.parseInt(endYear)
					|| (startYear.equals(endYear) && (Integer.parseInt(startMonth) > Integer.parseInt(endMonth)))) {
					request.setAttribute("startError", "教育開始が終了より後になっています。");
				}
			} 
			checkOrganization = true;
		}
	
		// その他(Others)のエラー処理
		// その他が全て未入力なら問題なし
		if (ValidationUtil.areAllNullOrEmpty(others)) {
		} 
		// 入力されている場合
		else {
			// 文字数が64文字より多い場合はエラーを返す
			if (ValidationUtil.areValidLengths(64, others)) {
				request.setAttribute("othersError", "64文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(others)) {
				request.setAttribute("othersError", "使用できない特殊文字が含まれています");
			} 
			checkOthers = true;
		}
			
		// 日本語能力の項目は少なくとも1つの項目が入力されている必要がある
		if (!checkTestName && !checkOrganization && !checkOthers) {
			request.setAttribute("japaneseError", "日本語能力項目を一つ以上入力してください。");
		}

		// 更新項目確認用変数
		boolean checkSelf = false;
		boolean checkSupporterLivingAbroad = false;
		boolean checkSupporterInJapan = false;
		boolean checkScholarship = false;
		boolean checkOtherDisbursement = false;

		// 本人負担（Self）のエラー処理
		// 本人負担が全て未入力なら問題なし
		if (ValidationUtil.areAllNullOrEmpty(self)) {
		} 
		else {
			// 文字数が32文字より多い場合はエラーを返す
			if (ValidationUtil.areValidLengths(32, self)) {
				request.setAttribute("selfError", "32文字以下で入力してください。");
			// 入力値に特殊文字が入っていないか確認する
			} else if (ValidationUtil.containsForbiddenChars(self)) {
				request.setAttribute("selfError", "使用できない特殊文字が含まれています");
			} 
			checkSelf = true;
		}
		
		// 在外経費支弁者負担（Supporter living abroad）のエラー処理
		// 在外経費支弁者負担が全て未入力なら問題なし
		if (ValidationUtil.areAllNullOrEmpty(supporterLivingAbroad)) {
		} else { 
			// 文字数が32文字より多い場合はエラーを返す
			if (ValidationUtil.areValidLengths(32, supporterLivingAbroad)) {
			request.setAttribute("supporterLivingAbroadError", "32文字以下で入力してください。");
			}
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(supporterLivingAbroad)) {
			request.setAttribute("supporterLivingAbroadError", "使用できない特殊文字が含まれています");
			} 
			checkSupporterLivingAbroad = true;
		}

		// 在日経費支弁者負担（Supporter in Japan）のエラー処理
		// 在日経費支弁者負担が全て未入力なら問題なし
		if (ValidationUtil.areAllNullOrEmpty(supporterInJapan)) {
		} 
		else {
			// 文字数が32文字より多い場合はエラーを返す
			if (ValidationUtil.areValidLengths(32, supporterInJapan)) {
				request.setAttribute("supporterInJapanError", "32文字以下で入力してください。");
			// 入力値に特殊文字が入っていないか確認する
			} else if (ValidationUtil.containsForbiddenChars(supporterInJapan)) {
				request.setAttribute("supporterInJapanError", "使用できない特殊文字が含まれています");
			}
			checkSupporterInJapan = true;
		}
		
		// 奨学金（Scholarship）のエラー処理
		// 奨学金が全て未入力なら問題なし
		if (ValidationUtil.areAllNullOrEmpty(scholarship)) {
		} else { 
			// 文字数が32文字より多い場合はエラーを返す
			if (ValidationUtil.areValidLengths(32, scholarship)) {
				request.setAttribute("scholarshipError", "32文字以下で入力してください。");
			}
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(scholarship)) {
				request.setAttribute("scholarshipError", "使用できない特殊文字が含まれています");
			} 
			checkScholarship = true;
		}
	
		// その他(Others)のエラー処理
		// その他が全て未入力なら問題なし
		if (ValidationUtil.areAllNullOrEmpty(otherDisbursement)) {
		} else {
			// 文字数が32文字より多い場合はエラーを返す
			if (ValidationUtil.areValidLengths(32, otherDisbursement)) {
				request.setAttribute("otherDisbursementError", "32文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(otherDisbursement)) {
				request.setAttribute("otherDisbursementError", "使用できない特殊文字が含まれています");
			}
			checkOtherDisbursement = true;
		}

		// 滞在費の支弁方法は少なくとも1つの項目が入力されている必要がある
		if (!checkSelf && !checkSupporterLivingAbroad && !checkSupporterInJapan && !checkScholarship
				&& !checkOtherDisbursement) {
			request.setAttribute("supportError", "滞在費の支弁方法を一つ以上入力してください。");
		}

		// 更新項目確認用変数
		boolean checkCarryingAbroad = false;
		boolean checkRemittancesAbroad = false;
		boolean checkOtherRemittances = false;

		// 外国からの携行（Carrying from abroad）のエラー処理 
		// 外国からの携行が全て未入力なら問題なし
		if (ValidationUtil.areAllNullOrEmpty(carryingAbroad, carryingName, carryingTime)) {
		} else {
			// 外国からの携行（Carrying from abroad）のエラー処理
			if (ValidationUtil.isNullOrEmpty(carryingAbroad)) {
				request.setAttribute("carryingAbroadError", "外国からの携行を入力してください。");
			} 
			// 文字数が32文字より多い場合はエラーを返す
			else if (ValidationUtil.areValidLengths(32, carryingAbroad)) {
				request.setAttribute("carryingAbroadError", "32文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(carryingAbroad)) {
				request.setAttribute("carryingAbroadError", "使用できない特殊文字が含まれています");
			} 
			
			// 携行者（Name of the individual carrying cash）のエラー処理
			if (ValidationUtil.isNullOrEmpty(carryingName)) {
				request.setAttribute("carryingNameError", "外国からの携行は携行者と携行時期も入力してください。");
			} 
			// 文字数が32文字より多い場合はエラーを返す
			else if (ValidationUtil.areValidLengths(32, carryingName)) {
				request.setAttribute("carryingNameError", "32文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(carryingName)) {
				request.setAttribute("carryingNameError", "使用できない特殊文字が含まれています");
			} 			
			
			// 携行時期（Date and time of carrying cash）のエラー処理
			if (ValidationUtil.isNullOrEmpty(carryingTime)) {
				request.setAttribute("carryingTimeError", "外国からの携行は携行者と携行時期も入力してください。");
			} 
			// 文字数が32文字より多い場合はエラーを返す
			else if (ValidationUtil.areValidLengths(32, carryingTime)) {
				request.setAttribute("carryingTimeError", "32文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(carryingTime)) {
				request.setAttribute("carryingTimeError", "使用できない特殊文字が含まれています");
			} 			
			checkCarryingAbroad = true;
		}
			
		// 外国からの送金（Remittances from abroad）のエラー処理 
		// 外国からの送金が全て未入力なら問題なし
		if (ValidationUtil.areAllNullOrEmpty(remittancesAbroad)) {
		} else {
			// 文字数が32文字より多い場合はエラーを返す
			if (ValidationUtil.areValidLengths(32, remittancesAbroad)) {
				request.setAttribute("remittancesAbroadError", "32文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(remittancesAbroad)) {
				request.setAttribute("remittancesAbroadError", "使用できない特殊文字が含まれています");
			}
			checkRemittancesAbroad = true;
		}

		// その他（Others）のエラー処理 
		// その他が全て未入力なら問題なし
		if (ValidationUtil.areAllNullOrEmpty(otherRemittances)) {
		} else { 
			// 文字数が16文字より多い場合はエラーを返す
			if (ValidationUtil.areValidLengths(16, otherRemittances)) {
				request.setAttribute("otherRemittancesError", "16文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(otherRemittances)) {
				request.setAttribute("otherRemittancesError", "使用できない特殊文字が含まれています");
			} 
			checkOtherRemittances = true;
		}

		// 送金・携行等の別は少なくとも1つの項目が入力されている必要がある
		if (!checkCarryingAbroad && !checkRemittancesAbroad && !checkOtherRemittances) {
			request.setAttribute("remittancesError", "送金・携行等の別は一つ以上入力してください。");
		}

		// 更新項目確認用変数
		boolean checkSupporter = false;

		
		// 経費支弁者が全て未入力なら問題なし
		if (ValidationUtil.areAllNullOrEmpty(supporterName, supporterAddress, supporterTel, supporterIncome,
				supporterEmployment, supporterWorkTel)) {
		} else {
			// 経費支弁者氏名(Supporter Name)のエラー処理
			if (ValidationUtil.isNullOrEmpty(supporterName)) {
				request.setAttribute("supporterNameError", "経費支弁者を入力する場合は経費支弁者の全項目を入力してください。");
			} 
			// 文字数が32文字より多い場合はエラーを返す
			else if (ValidationUtil.areValidLengths(32, supporterName)) {
				request.setAttribute("supporterNameError", "32文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(supporterName)) {
				request.setAttribute("supporterNameError", "使用できない特殊文字が含まれています");
			} 			

			// 経費支弁者住所(Supporter Address)のエラー処理
			if (ValidationUtil.isNullOrEmpty(supporterAddress)) {
				request.setAttribute("supporterAddressError", "経費支弁者を入力する場合は経費支弁者の全項目を入力してください。");
			} 
			// 文字数が32文字より多い場合はエラーを返す
			else if (ValidationUtil.areValidLengths(32, supporterAddress)) {
				request.setAttribute("supporterAddressError", "32文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(supporterAddress)) {
				request.setAttribute("supporterAddressError", "使用できない特殊文字が含まれています");
			} 						

			// 経費支弁者電話番号(Supporter Tel)のエラー処理
			if (ValidationUtil.isNullOrEmpty(supporterTel)) {
				request.setAttribute("supporterTelError", "経費支弁者を入力する場合は経費支弁者の全項目を入力してください。");
			} 
			// 文字数が32文字より多い場合はエラーを返す
			else if (ValidationUtil.areValidLengths(32, supporterTel)) {
				request.setAttribute("supporterTelError", "32文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(supporterTel)) {
				request.setAttribute("supporterTelError", "使用できない特殊文字が含まれています");
			} 								

			// 経費支弁者年収(Supporter Annual income)のエラー処理
			if (ValidationUtil.isNullOrEmpty(supporterIncome)) {
				request.setAttribute("supporterIncomeError", "経費支弁者を入力する場合は経費支弁者の全項目を入力してください。");
			} 
			// 文字数が32文字より多い場合はエラーを返す
			else if (ValidationUtil.areValidLengths(32, supporterIncome)) {
				request.setAttribute("supporterIncomeError", "32文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(supporterIncome)) {
				request.setAttribute("supporterIncomeError", "使用できない特殊文字が含まれています");
			} 							
			
			// 経費支弁者勤務先(Supporter place of employment)のエラー処理
			if (ValidationUtil.isNullOrEmpty(supporterEmployment)) {
				request.setAttribute("supporterEmploymentError", "経費支弁者を入力する場合は経費支弁者の全項目を入力してください。");
			} 
			// 文字数が32文字より多い場合はエラーを返す
			else if (ValidationUtil.areValidLengths(32, supporterEmployment)) {
				request.setAttribute("supporterEmploymentError", "32文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(supporterEmployment)) {
				request.setAttribute("supporterEmploymentError", "使用できない特殊文字が含まれています");
			} 						
			
			// 経費支弁者勤務先電話番号(Supporter Work Phone Number)のエラー処理
			if (ValidationUtil.isNullOrEmpty(supporterWorkTel)) {
				request.setAttribute("supporterWorkTelError", "経費支弁者を入力する場合は経費支弁者の全項目を入力してください。");
			} 
			// 文字数が32文字より多い場合はエラーを返す
			else if (ValidationUtil.areValidLengths(32, supporterWorkTel)) {
				request.setAttribute("supporterWorkTelError", "32文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(supporterWorkTel)) {
				request.setAttribute("supporterWorkTelError", "使用できない特殊文字が含まれています");
			} 						
			checkSupporter = true;
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "period-update-second.jsp";
		}

		try {
			// データベース操作用クラス
			UserDAO dao = new UserDAO();
			// 復号とIDやIV等の取り出しクラスの設定
			Decrypt decrypt = new Decrypt(dao);
			DecryptionResult result = decrypt.getDecryptedMasterKey(session);
			// IDの取り出し
			String id = result.getId();

			// 学生種類のデータベースからの取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			String studentType = decrypt.getDecryptedDate(result, reEncryptedStudentType);

			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(studentType)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

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

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Period Update Second.jsp");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "在留期間更新許可申請書2枚目");
			// Close and save
			editor.close("Period_Update_Second.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "period-update-second.jsp";
		}
	}
}
