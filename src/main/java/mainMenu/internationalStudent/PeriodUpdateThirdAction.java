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

		// 請人との関係 （2枚目で在外経費支弁者負担又は在日経費支弁者負担を選択した場合に記入）のエラー処理
		// 申請人との関係が未入力なら問題なし
		if (ValidationUtil.areAllNullOrEmpty(relationship)) {
		} 
		// 入力されている場合
		else {
			// 申請人との関係のエラー処理
			// 文字数が16文字より多い場合はエラーを返す
			if (ValidationUtil.areValidLengths(16, relationship)) {
				request.setAttribute("relationshipError", "16文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(relationship)) {
				request.setAttribute("relationshipError", "使用できない特殊文字が含まれています");
			} 
			
			// その他を選択した場合は詳細記入のエラー処理
			// 申請人との関係がその他の場合で詳細記入がなければエラーを返す
			if (relationship.equals("その他")) {
				if (ValidationUtil.isNullOrEmpty(relationshipOtherContents)) {
					request.setAttribute("relationshipOtherContentsError", "申請人との関係でその他を選択した場合は詳細も入力してください。");
				}
				// 文字数が16文字より多い場合はエラーを返す
				else if (ValidationUtil.areValidLengths(16, relationshipOtherContents)) {
					request.setAttribute("relationshipOtherContentsError", "その他の詳細は16文字以下で入力してください。");
				} 
				// 入力値に特殊文字が入っていないか確認する
				else if (ValidationUtil.containsForbiddenChars(relationshipOtherContents)) {
					request.setAttribute("relationshipOtherContentsError", "使用できない特殊文字が含まれています");
				}
			}	
		}

		// 外国政府（Foreign government）のエラー処理
		// 未入力なら問題なし
		if (ValidationUtil.isNullOrEmpty(foreignGovernment)) {
		}
		// 入力されている場合
		else {
			// 入力値に特殊文字が入っていないか確認する
			if (ValidationUtil.containsForbiddenChars(foreignGovernment)) {
				request.setAttribute("foreignGovernmentError", "使用できない特殊文字が含まれています");
			}
			// 文字数が多い場合はエラーを返す。
			else if (ValidationUtil.areValidLengths(18, foreignGovernment)) {
				request.setAttribute("foreignGovernmentError", "奨学金支給機関名は18文字以下で入力してください。");
			}
		}
		
		// 日本国政府（Japanese government）のエラー処理
		// 未入力なら問題なし
		if (ValidationUtil.isNullOrEmpty(japaneseGovernment)) {
		}
		// 入力されている場合
		else {
			// 入力値に特殊文字が入っていないか確認する
			if (ValidationUtil.containsForbiddenChars(japaneseGovernment)) {
				request.setAttribute("japaneseGovernmentError", "使用できない特殊文字が含まれています");
			}
			// 文字数が多い場合はエラーを返す。
			else if (ValidationUtil.areValidLengths(18, japaneseGovernment)) {
				request.setAttribute("japaneseGovernmentError", "奨学金支給機関名は18文字以下で入力してください。");
			}
		}
		
		// 地方公共団体（Local government）のエラー処理
		// 未入力なら問題なし
		if (ValidationUtil.isNullOrEmpty(localGovernment)) {
		}
		// 入力されている場合
		else {
			// 入力値に特殊文字が入っていないか確認する
			if (ValidationUtil.containsForbiddenChars(localGovernment)) {
				request.setAttribute("localGovernmentError", "使用できない特殊文字が含まれています");
			}
			// 文字数が多い場合はエラーを返す。
			else if (ValidationUtil.areValidLengths(18, localGovernment)) {
				request.setAttribute("localGovernmentError", "奨学金支給機関名は18文字以下で入力してください。");
			}
		}				
		
		// その他（Others）のエラー処理
		// 未入力なら問題なし
		if (ValidationUtil.isNullOrEmpty(otherOrganization)) {
		}
		// 入力されている場合
		else {
			// 入力値に特殊文字が入っていないか確認する
			if (ValidationUtil.containsForbiddenChars(otherOrganization)) {
				request.setAttribute("otherOrganizationError", "使用できない特殊文字が含まれています");
			}
			// 文字数が多い場合はエラーを返す。
			else if (ValidationUtil.areValidLengths(18, otherOrganization)) {
				request.setAttribute("otherOrganizationError", "奨学金支給機関名は18文字以下で入力してください。");
			}

			// 奨学金支給機関がその他の場合で詳細記入がなければエラーを返す
			if (ValidationUtil.isNullOrEmpty(organizationOtherContents)) {
				request.setAttribute("organizationOtherContentsError", "奨学金支給機関でその他を選択した場合は詳細も入力してください。");
			} 
			// 文字数が16文字より多い場合はエラーを返す
			else if (ValidationUtil.areValidLengths(16, organizationOtherContents)) {
				request.setAttribute("organizationOtherContentsError", "その他の詳細は16文字以下で入力してください。");
			} 
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(organizationOtherContents)) {
				request.setAttribute("organizationOtherContentsError", "使用できない特殊文字が含まれています");
			}
		
		}						
		
		// 公益社団法人又は公益財団法人（Public interest incorporated association /Public interest incorporated foundation）のエラー処理
		// 未入力なら問題なし
		if (ValidationUtil.isNullOrEmpty(publicInterest)) {
		}
		// 入力されている場合
		else {
			// 入力値に特殊文字が入っていないか確認する
			if (ValidationUtil.containsForbiddenChars(publicInterest)) {
				request.setAttribute("publicInterestError", "使用できない特殊文字が含まれています");
			}
			// 文字数が多い場合はエラーを返す。
			else if (ValidationUtil.areValidLengths(18, publicInterest)) {
				request.setAttribute("publicInterestError", "奨学金支給機関名は18文字以下で入力してください。");
			}

			// 奨学金支給機関が公益社団法人又は公益財団法人の場合で詳細記入がなければエラーを返す
			if (ValidationUtil.isNullOrEmpty(organizationpublicInterestContents)) {
				request.setAttribute("organizationpublicInterestContentsError", "公益社団法人又は公益財団法人を選択した場合は詳細も入力してください。");
			} else if (ValidationUtil.areValidLengths(16, organizationpublicInterestContents)) {
				// 文字数が16文字より多い場合はエラーを返す
				request.setAttribute("organizationpublicInterestContentsError", "公益社団法人又は公益財団法人の詳細は16文字以下で入力してください。");
			} else if (ValidationUtil.containsForbiddenChars(organizationpublicInterestContents)) {
				// 入力値に特殊文字が入っていないか確認する
				request.setAttribute("organizationpublicInterestContentsError", "使用できない特殊文字が含まれています");
			}
		}							
		
		// 奨学金支給機関名にエラーがある場合
		if (!ValidationUtil.areAllNullOrEmpty((String) request.getAttribute("foreignGovernmentError"),
				(String) request.getAttribute("japaneseGovernmentError"),(String) request.getAttribute("localGovernmentError"),(String) request.getAttribute("otherOrganizationError"),(String) request.getAttribute("publicInterestError"))) {
			request.setAttribute("publicError", " 奨学金支給機関名は18文字以下で特殊文字を含めずに入力してください。");
		}

		// 更新項目確認用変数
		boolean checkOtherActivity = false;

		// 資格外活動の有無（Are you engaging in activities other than those permitted under the status of residence previously granted?）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(otherActivity)) {
			request.setAttribute("otherActivityError", "入力必須項目です。");
		}
		// 資格外活動の有無が「有」「無」以外の場合はエラーを返す
		else if (!(otherActivity.equals("有") || otherActivity.equals("無"))) {
			request.setAttribute("otherActivityError", "資格外活動は「有（Yes）」「無（No）」から選択してください");
		}
		else if (otherActivity.equals("有")) {
			// 資格外活動の有無が有の場合で内容等が全て未入力なら問題なし(※任意様式の別紙可のため)
			if (ValidationUtil.areAllNullOrEmpty(work, employment, workPhone, workTimePerWeek,
					salary, monthlyOrDaily)) {
			} 
			// 入力事項がある場合のエラー処理
			else {
				// 内容（Type of work）のエラー処理
				if (ValidationUtil.isNullOrEmpty(work)) {
					request.setAttribute("workError", "資格外活動の内容を入力する場合は全項目を入力してください。");
				} 
				// 文字数が32文字より多い場合はエラーを返す
				else if (ValidationUtil.areValidLengths(32, work)) {
					request.setAttribute("workError", "32文字以下で入力してください。");
				} 
				// 入力値に特殊文字が入っていないか確認する
				else if (ValidationUtil.containsForbiddenChars(work)) {
					request.setAttribute("workError", "使用できない特殊文字が含まれています");
				} 		
				
				// 勤務先名称（Place of employment）のエラー処理
				if (ValidationUtil.isNullOrEmpty(employment)) {
					request.setAttribute("employmentError", "資格外活動の内容を入力する場合は全項目を入力してください。");
				} 
				// 文字数が32文字より多い場合はエラーを返す
				else if (ValidationUtil.areValidLengths(32, employment)) {
					request.setAttribute("employmentError", "32文字以下で入力してください。");
				} 
				// 入力値に特殊文字が入っていないか確認する
				else if (ValidationUtil.containsForbiddenChars(employment)) {
					request.setAttribute("employmentError", "使用できない特殊文字が含まれています");
				} 						
				
				// 勤務先電話番号（Telephone No.）のエラー処理
				if (ValidationUtil.isNullOrEmpty(workPhone)) {
					request.setAttribute("workPhoneError", "資格外活動の内容を入力する場合は全項目を入力してください。");
				} 
				// 電話番号が半角10~11桁でなければエラーを返す
				else if (ValidationUtil.isTenOrElevenDigit(workPhone)) {
					request.setAttribute("workPhoneError", "電話番号は半角数字10桁～11桁で入力してください。");
				}
				// 電話番号に「-」をつける
				else if (workPhone.length() == 11) {
					workPhone = workPhone.substring(0, 3) + "-" + workPhone.substring(3, 7) + "-"
							+ workPhone.substring(7, 11);
				} else {
					workPhone = workPhone.substring(0, 3) + "-" + workPhone.substring(3, 6) + "-"
							+ workPhone.substring(6, 10);
				}
				
				// 週間稼働時間（Work time per week）のエラー処理
				if (ValidationUtil.isNullOrEmpty(workTimePerWeek)) {
					request.setAttribute("workTimePerWeekError", "資格外活動の内容を入力する場合は全項目を入力してください。");
				} 				
				// 稼働時間及び入力金額に数字以外が含まれている、もしくは数字が１０００万円を超える場合はエラーを返す
				else if (!workTimePerWeek.matches("\\d+") || workTimePerWeek.length() > 3) {
					request.setAttribute("workTimePerWeekError", "稼働時間は数字のみ3桁以下で入力してください。");				
				}
			
				// 報酬（Salary）のエラー処理
				if (ValidationUtil.isNullOrEmpty(salary)) {
					request.setAttribute("salaryError", "資格外活動の内容を入力する場合は全項目を入力してください。");
				} 				
				// 稼働時間及び入力金額に数字以外が含まれている、もしくは数字が１０００万円を超える場合はエラーを返す
				else if (!salary.matches("\\d+") || salary.length() > 7) {
					request.setAttribute("salaryError", "報酬は数字のみ7桁以下で入力してください。");				
				}				
				// 文字列の数値をカンマ付きに変更する
				else {
					salary = numberFormat.format(Integer.parseInt(salary));				
				}
				
				// 月額・日額（Monthly or Daily）のエラー処理
				if (ValidationUtil.isNullOrEmpty(monthlyOrDaily)) {
					request.setAttribute("monthlyOrDailyError", "資格外活動の内容を入力する場合は全項目を入力してください。");
				} 							
				else if (!(monthlyOrDaily.equals("月額") || monthlyOrDaily.equals("日額"))) {
					// セレクトボックスの選択肢外の場合はエラーを返す。
					request.setAttribute("monthlyOrDailyError", "セレクトボックスの選択肢から選んでください");
				}
				checkOtherActivity = true;
			}	
		}

		// 卒業後の予定（Plan after graduation） のエラー処理
		if (ValidationUtil.isNullOrEmpty(afterGraduation)) {
			request.setAttribute("afterGraduationError", "入力必須項目です。");
		} 				
		// 文字数が6文字より多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(6, afterGraduation)) {
			request.setAttribute("afterGraduationError", "卒業後の予定は6文字以下で入力してください。");
		} 
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(afterGraduation)) {
			request.setAttribute("afterGraduationError", "使用できない特殊文字が含まれています");
		}
		// 卒業後の予定がその他の場合で詳細記入がなければエラーを返す
		else if (afterGraduation.equals("その他")) {
			if (ValidationUtil.isNullOrEmpty(afterGraduationOtherContents)) {
				request.setAttribute("afterGraduationOtherContentsError", "卒業後の予定でその他を選択した場合は詳細も入力してください。");
			} else if (ValidationUtil.areValidLengths(32, afterGraduationOtherContents)) {
				// 文字数が21文字より多い場合はエラーを返す。セレクトボックスの有効範囲画外の場合もエラーを返す。
				request.setAttribute("afterGraduationOtherContentsError", "卒業後の予定のその他の詳細は32文字以下で入力してください。");
			} else if (ValidationUtil.containsForbiddenChars(afterGraduationOtherContents)) {
				// 入力値に特殊文字が入っていないか確認する
				request.setAttribute("afterGraduationOtherContentsError", "使用できない特殊文字が含まれています");
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
