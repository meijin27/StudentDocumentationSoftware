package mainMenu.internationalStudent;

import java.text.NumberFormat;
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

public class PeriodUpdateThirdAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(PeriodUpdateThirdAction.class);

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
		String relationship = request.getParameter("relationship");
		String relationshipOtherContents = request.getParameter("relationshipOtherContents");
		String foreignGovernment = request.getParameter("foreignGovernment");
		String japaneseGovernment = request.getParameter("japaneseGovernment");
		String localGovernment = request.getParameter("localGovernment");
		String otherOrganization = request.getParameter("otherOrganization");
		String publicInterest = request.getParameter("publicInterest");
		String organizationpublicInterestContents = request.getParameter("organizationpublicInterestContents");
		String organizationOtherContents = request.getParameter("organizationOtherContents");
		String otherActivity = request.getParameter("otherActivity");
		String work = request.getParameter("work");
		String employment = request.getParameter("employment");
		String workPhone = request.getParameter("workPhone");
		String workTimePerWeek = request.getParameter("workTimePerWeek");
		String salary = request.getParameter("salary");
		String monthlyOrDaily = request.getParameter("monthlyOrDaily");
		String afterGraduation = request.getParameter("afterGraduation");
		String afterGraduationOtherContents = request.getParameter("afterGraduationOtherContents");

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 入力された金額をカンマ付きに変更するメソッド
		NumberFormat numberFormat = NumberFormat.getNumberInstance();

		// 申請人との関係がその他の場合で詳細記入がなければエラーを返す
		if (relationship.equals("その他")) {
			if (ValidationUtil.isNullOrEmpty(relationshipOtherContents)) {
				request.setAttribute("otherError", "申請人との関係でその他を選択した場合は詳細も入力してください。");
			} else if (ValidationUtil.areValidLengths(16, relationshipOtherContents, relationship)) {
				// 文字数が16文字より多い場合はエラーを返す。セレクトボックスの有効範囲画外の場合もエラーを返す。
				request.setAttribute("otherError", "その他の詳細は16文字以下で入力してください。");
			} else if (ValidationUtil.containsForbiddenChars(relationshipOtherContents, relationship)) {
				// 入力値に特殊文字が入っていないか確認する
				request.setAttribute("validationError", "使用できない特殊文字が含まれています");
			}
		}

		// 奨学金支給機関名は18文字以下
		if (ValidationUtil.areValidLengths(18, foreignGovernment)
				|| ValidationUtil.areValidLengths(18, japaneseGovernment)
				|| ValidationUtil.areValidLengths(18, localGovernment)
				|| ValidationUtil.areValidLengths(18, otherOrganization)
				|| ValidationUtil.areValidLengths(18, publicInterest)) {
			request.setAttribute("publicInterestError", " 奨学金支給機関名は18文字以下で入力してください。");
		}

		// 奨学金支給機関が公益社団法人又は公益財団法人の場合で詳細記入がなければエラーを返す
		if (!ValidationUtil.isNullOrEmpty(publicInterest)) {
			if (ValidationUtil.isNullOrEmpty(organizationpublicInterestContents)) {
				request.setAttribute("publicInterestError", "公益社団法人又は公益財団法人を選択した場合は詳細も入力してください。");
			} else if (ValidationUtil.areValidLengths(16, organizationpublicInterestContents)) {
				// 文字数が16文字より多い場合はエラーを返す
				request.setAttribute("publicInterestError", "公益社団法人又は公益財団法人の詳細は16文字以下で入力してください。");
			} else if (ValidationUtil.containsForbiddenChars(organizationpublicInterestContents)) {
				// 入力値に特殊文字が入っていないか確認する
				request.setAttribute("validationError", "使用できない特殊文字が含まれています");
			}
		}

		// 奨学金支給機関がその他の場合で詳細記入がなければエラーを返す
		if (!ValidationUtil.isNullOrEmpty(otherOrganization)) {
			if (ValidationUtil.isNullOrEmpty(organizationOtherContents)) {
				request.setAttribute("otherOrganizationError", "奨学金支給機関でその他を選択した場合は詳細も入力してください。");
			} else if (ValidationUtil.areValidLengths(16, organizationOtherContents)) {
				// 文字数が16文字より多い場合はエラーを返す
				request.setAttribute("otherOrganizationError", "その他の詳細は16文字以下で入力してください。");
			} else if (ValidationUtil.containsForbiddenChars(organizationOtherContents)) {
				// 入力値に特殊文字が入っていないか確認する
				request.setAttribute("validationError", "使用できない特殊文字が含まれています");
			}
		}

		// 更新項目確認用変数
		boolean checkOtherActivity = false;

		// ラジオボタンの入力値チェック
		// 資格外活動の有無が「有」「無」以外の場合はエラーを返す
		if (!(otherActivity.equals("有") || otherActivity.equals("無"))) {
			request.setAttribute("otherActivityError", "資格外活動は「有（Yes）」「無（No）」から選択してください");
		}

		// 資格外活動の有無が有の場合で内容等が全て未入力なら問題なし
		if (otherActivity.equals("有")) {
			if (ValidationUtil.areAllNullOrEmpty(work, employment, workPhone, workTimePerWeek,
					salary, monthlyOrDaily)) {
			} else if (ValidationUtil.isNullOrEmpty(work, employment, workPhone, workTimePerWeek,
					salary, monthlyOrDaily)) {
				// どれかだけ入力されている場合
				request.setAttribute("otherActivityError", "資格外活動の内容を入力する場合は(1)～(4)の全項目を入力してください。");
			} else if (ValidationUtil.areValidLengths(32, work, employment)) {
				// 文字数が32文字より多い場合はエラーを返す。セレクトボックスの有効範囲画外の場合もエラーを返す。
				request.setAttribute("otherActivityError", "32文字以下で入力してください。");
			} else if (!(monthlyOrDaily.equals("月額") || monthlyOrDaily.equals("日額"))) {
				// セレクトボックスの選択肢外の場合はエラーを返す。
				request.setAttribute("otherActivityError", "セレクトボックスの選択肢から選んでください");
			} else if (ValidationUtil.containsForbiddenChars(work, employment)) {
				// 入力値に特殊文字が入っていないか確認する
				request.setAttribute("validationError", "使用できない特殊文字が含まれています");
			} else if (ValidationUtil.isTenOrElevenDigit(workPhone)) {
				// 電話番号が半角10~11桁でなければエラーを返す
				request.setAttribute("otherActivityError", "電話番号は半角数字10桁～11桁で入力してください。");
			} else if (!workTimePerWeek.matches("\\d+") || workTimePerWeek.length() > 3 || !salary.matches("\\d+")
					|| salary.length() > 7) {
				// 稼働時間及び入力金額に数字以外が含まれている、もしくは数字が１０００万円を超える場合はエラーを返す
				request.setAttribute("otherActivityError", "稼働時間は数字のみ3桁以下、報酬は数字のみ7桁以下で入力してください。");
			} else {
				// 電話番号に「-」をつける
				if (workPhone.length() == 11) {
					workPhone = workPhone.substring(0, 3) + "-" + workPhone.substring(3, 7) + "-"
							+ workPhone.substring(7, 11);
				} else {
					workPhone = workPhone.substring(0, 3) + "-" + workPhone.substring(3, 6) + "-"
							+ workPhone.substring(6, 10);
				}
				// 文字列の数値をカンマ付きに変更する
				salary = numberFormat.format(Integer.parseInt(salary));
				checkOtherActivity = true;
			}
		}

		if (ValidationUtil.areValidLengths(6, afterGraduation)) {
			// 文字数が6文字より多い場合はエラーを返す。。
			request.setAttribute("afterGraduationError", "卒業後の予定は6文字以下で入力してください。");
		} else if (ValidationUtil.containsForbiddenChars(afterGraduation)) {
			// 入力値に特殊文字が入っていないか確認する
			request.setAttribute("validationError", "使用できない特殊文字が含まれています");
		}

		// 卒業後の予定がその他の場合で詳細記入がなければエラーを返す
		if (afterGraduation.equals("その他")) {
			if (ValidationUtil.isNullOrEmpty(afterGraduationOtherContents)) {
				request.setAttribute("afterGraduationError", "卒業後の予定でその他を選択した場合は詳細も入力してください。");
			} else if (ValidationUtil.areValidLengths(32, afterGraduationOtherContents)) {
				// 文字数が21文字より多い場合はエラーを返す。セレクトボックスの有効範囲画外の場合もエラーを返す。
				request.setAttribute("afterGraduationError", "卒業後の予定のその他の詳細は32文字以下で入力してください。");
			} else if (ValidationUtil.containsForbiddenChars(afterGraduationOtherContents)) {
				// 入力値に特殊文字が入っていないか確認する
				request.setAttribute("validationError", "使用できない特殊文字が含まれています");
			}
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "period-update-third.jsp";
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
				return "period-update-third.jsp";
			}

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/internationalStudentPDF/在留期間更新許可申請書3枚目.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));
			// PDFへの書き込み

			// 申請人との関係	
			switch (relationship) {
			case "夫":
				editor.writeText(font, "✓", 106f, 772f, 40f, "left", 8);
				break;
			case "妻":
				editor.writeText(font, "✓", 145f, 772f, 40f, "left", 8);
				break;
			case "父":
				editor.writeText(font, "✓", 184f, 772f, 40f, "left", 8);
				break;
			case "母":
				editor.writeText(font, "✓", 223f, 772f, 40f, "left", 8);
				break;
			case "祖父":
				editor.writeText(font, "✓", 262f, 772f, 40f, "left", 8);
				break;
			case "祖母":
				editor.writeText(font, "✓", 314f, 772f, 40f, "left", 8);
				break;
			case "養父":
				editor.writeText(font, "✓", 365f, 772f, 40f, "left", 8);
				break;
			case "養母":
				editor.writeText(font, "✓", 417f, 772f, 40f, "left", 8);
				break;
			case "兄弟姉妹":
				editor.writeText(font, "✓", 106f, 748f, 40f, "left", 8);
				break;
			case "叔父 （伯父）・叔母（伯母）":
				editor.writeText(font, "✓", 184f, 748f, 40f, "left", 8);
				break;
			case "受入教育機関":
				editor.writeText(font, "✓", 314f, 748f, 40f, "left", 8);
				break;
			case "友人・知人":
				editor.writeText(font, "✓", 417f, 748f, 40f, "left", 8);
				break;
			case "友人・知人の親族":
				editor.writeText(font, "✓", 106f, 725f, 40f, "left", 8);
				break;
			case "取引関係者・現地企業等職員":
				editor.writeText(font, "✓", 210f, 725f, 40f, "left", 8);
				break;
			case "取引関係者・現地企業等職員の親族":
				editor.writeText(font, "✓", 106f, 701f, 40f, "left", 8);
				break;
			case "その他":
				editor.writeText(font, "✓", 314f, 701f, 40f, "left", 8);
				editor.writeText(font, relationshipOtherContents, 360f, 701f, 82f, "center", 8);
				break;
			default:
				break;
			}

			// 奨学金支給期間
			// 外国政府
			if (foreignGovernment != null && !foreignGovernment.isEmpty()) {
				editor.writeText(font, "✓", 106f, 652f, 40f, "left", 8);
			}

			// 日本政府
			if (japaneseGovernment != null && !japaneseGovernment.isEmpty()) {
				editor.writeText(font, "✓", 184f, 652f, 40f, "left", 8);
			}

			// 地方公共団体
			if (localGovernment != null && !localGovernment.isEmpty()) {
				editor.writeText(font, "✓", 275f, 652f, 40f, "left", 8);
			}
			// 公益社団法人又は公益財団法人
			if (publicInterest != null && !publicInterest.isEmpty()) {
				editor.writeText(font, "✓", 106f, 628f, 40f, "left", 8);
				editor.writeText(font, organizationpublicInterestContents, 255f, 628f, 95f, "center", 8);
			}

			// その他
			if (otherOrganization != null && !otherOrganization.isEmpty()) {
				editor.writeText(font, "✓", 366f, 628f, 40f, "left", 8);
				editor.writeText(font, organizationOtherContents, 412f, 628f, 80f, "center", 8);
			}

			// 資格外活動の有無
			if (otherActivity.equals("有")) {
				editor.writeText(font, "〇", 440f, 592f, 40f, "left", 18);
				if (checkOtherActivity) {
					editor.writeText(font, work, 168f, 532f, 335f, "center", 12);
					editor.writeText(font, employment, 168f, 508f, 155f, "center", 12);
					editor.writeText(font, workPhone, 390f, 508f, 114f, "center", 12);
					editor.writeText(font, workTimePerWeek, 168f, 485f, 50f, "center", 12);
					editor.writeText(font, salary, 300f, 485f, 75f, "center", 12);
					if (monthlyOrDaily.equals("月額")) {
						editor.writeText(font, "✓", 404f, 495f, 40f, "left", 8);
					} else {
						editor.writeText(font, "✓", 443f, 495f, 40f, "left", 8);
					}
				}
			} else {
				editor.writeText(font, "〇", 461f, 592f, 40f, "left", 18);
			}

			// 申請人との関係	
			switch (afterGraduation) {
			case "帰国":
				editor.writeText(font, "✓", 93f, 458f, 40f, "left", 8);
				break;
			case "日本での進学":
				editor.writeText(font, "✓", 236f, 458f, 40f, "left", 8);
				break;
			case "日本での就職":
				editor.writeText(font, "✓", 93f, 436f, 40f, "left", 8);
				break;
			case "その他":
				editor.writeText(font, "✓", 236f, 436f, 40f, "left", 8);
				editor.writeText(font, afterGraduationOtherContents, 283f, 436f, 210f, "center", 8);
				break;
			default:
				break;
			}

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Period Update Third.jsp");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "在留期間更新許可申請書3枚目");
			// Close and save
			editor.close("Period_Update_Third.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "period-update-third.jsp";
		}
	}
}
