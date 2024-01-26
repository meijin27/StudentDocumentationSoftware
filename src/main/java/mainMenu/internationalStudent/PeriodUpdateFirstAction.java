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

public class PeriodUpdateFirstAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(PeriodUpdateFirstAction.class);

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
		String nationalityRegion = request.getParameter("nationalityRegion");
		String homeTown = request.getParameter("homeTown");
		String sex = request.getParameter("sex");
		String maritalStatus = request.getParameter("maritalStatus");
		String passportNumber = request.getParameter("passportNumber");
		String effectiveYear = request.getParameter("effectiveYear");
		String effectiveMonth = request.getParameter("effectiveMonth");
		String effectiveDay = request.getParameter("effectiveDay");
		String statusOfResidence = request.getParameter("statusOfResidence");
		String periodOfStay = request.getParameter("periodOfStay");
		String periodYear = request.getParameter("periodYear");
		String periodMonth = request.getParameter("periodMonth");
		String periodDay = request.getParameter("periodDay");
		String residentCard = request.getParameter("residentCard");
		String desiredPeriodOfStay = request.getParameter("desiredPeriodOfStay");
		String reason = request.getParameter("reason");
		String criminalRecord = request.getParameter("criminalRecord");
		String reasonForTheCrime = request.getParameter("reasonForTheCrime");
		String familyInJapan = request.getParameter("familyInJapan");

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 国籍・地域（Nationality/Region）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(nationalityRegion)) {
			request.setAttribute("nationalityRegionError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(nationalityRegion)) {
			request.setAttribute("nationalityRegionError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, nationalityRegion)) {
			request.setAttribute("nationalityRegionError", "国籍・地域（Nationality/Region）は32文字以下で入力してください。");
		}

		// 本国における居住地（Home town/city）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(homeTown)) {
			request.setAttribute("homeTownError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(homeTown)) {
			request.setAttribute("homeTownError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, homeTown)) {
			request.setAttribute("homeTownError", "本国における居住地（Home town/city）は32文字以下で入力してください。");
		}

		// 性別（Sex）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(sex)) {
			request.setAttribute("sexError", "入力必須項目です。");
		}
		// 性別が「男」「女」以外の場合はエラーを返す
		else if (!(sex.equals("男") || sex.equals("女"))) {
			request.setAttribute("sexError", "性別は「男（Male）」「女（Female）」から選択してください");
		}

		// 配偶者の有無（Marital status）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(maritalStatus)) {
			request.setAttribute("maritalStatusError", "入力必須項目です。");
		}
		// 配偶者が「有」「無」以外の場合はエラーを返す
		else if (!(maritalStatus.equals("有") || maritalStatus.equals("無"))) {
			request.setAttribute("maritalStatusError", "配偶者は「有（Married）」「無（Single）」から選択してください");
		}

		// 旅券番号（Passport Number）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(passportNumber)) {
			request.setAttribute("passportNumberError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(passportNumber)) {
			request.setAttribute("passportNumberError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, passportNumber)) {
			request.setAttribute("passportNumberError", "旅券番号（Passport Number）は32文字以下で入力してください。");
		}

		// 旅券有効期限（Passport expiration date）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(effectiveYear, effectiveMonth, effectiveDay)) {
			request.setAttribute("effectiveError", "入力必須項目です。");
		}
		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isFourDigit(effectiveYear) ||
				ValidationUtil.isOneOrTwoDigit(effectiveMonth, effectiveDay)) {
			request.setAttribute("effectiveError", "年月日は正規の桁数で入力してください。");
		}
		// 旅券有効期限（Passport expiration date）が存在しない日付の場合はeffectiveYearにする
		else if (ValidationUtil.validateDate(effectiveYear, effectiveMonth, effectiveDay)) {
			request.setAttribute("effectiveError", "存在しない日付です。");
		}

		// 現に有する在留資格（Status of residence）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(statusOfResidence)) {
			request.setAttribute("statusOfResidenceError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(statusOfResidence)) {
			request.setAttribute("statusOfResidenceError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, statusOfResidence)) {
			request.setAttribute("statusOfResidenceError", "現に有する在留資格（Status of residence）は32文字以下で入力してください。");
		}

		// 在留期間（Period of stay）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(periodOfStay)) {
			request.setAttribute("periodOfStayError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(periodOfStay)) {
			request.setAttribute("periodOfStayError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, periodOfStay)) {
			request.setAttribute("periodOfStayError", "在留期間（Period of stay）は32文字以下で入力してください。");
		}

		// 在留期間の満了日（Expiration date of period of stay）の満了日のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(periodYear, periodMonth, periodDay)) {
			request.setAttribute("periodError", "入力必須項目です。");
		}
		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isFourDigit(periodYear) ||
				ValidationUtil.isOneOrTwoDigit(periodMonth, periodDay)) {
			request.setAttribute("periodError", "年月日は正規の桁数で入力してください。");
		}
		// 在留期間の満了日（Expiration date of period of stay）が存在しない日付の場合はeffectiveYearにする
		else if (ValidationUtil.validateDate(periodYear, periodMonth, periodDay)) {
			request.setAttribute("periodError", "存在しない日付です。");
		}

		// 在留カード番号（Residence card number）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(residentCard)) {
			request.setAttribute("residentCardError", "入力必須項目です。");
		}
		// 在留カードの記号番号のチェック
		else if (ValidationUtil.isResidentCard(residentCard)) {
			// 記号番号の最初と最後の２桁が大文字のアルファベット、間が半角数字8桁でなければエラーを返す
			request.setAttribute("residentCardError", "記号番号の最初と最後の２桁は大文字のアルファベット、間は半角数字８桁で入力してください。");
		}

		// 希望在留期間(Desired length of extension)のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(desiredPeriodOfStay)) {
			request.setAttribute("desiredPeriodOfStayError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(desiredPeriodOfStay)) {
			request.setAttribute("desiredPeriodOfStayError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, desiredPeriodOfStay)) {
			request.setAttribute("desiredPeriodOfStayError", "希望在留期間(Desired length of extension)は32文字以下で入力してください。");
		}

		// 更新の理由（Reason for extension）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(reason)) {
			request.setAttribute("reasonError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(reason)) {
			request.setAttribute("reasonError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, reason)) {
			request.setAttribute("reasonError", "更新の理由（Reason for extension）は32文字以下で入力してください。");
		}

		// 犯罪を理由とする処分を受けたことの有無 （日本国外におけるものを含む。）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(criminalRecord)) {
			request.setAttribute("criminalRecordError", "入力必須項目です。");
		}
		// 犯罪歴が「有」「無」以外の場合はエラーを返す
		else if (!(criminalRecord.equals("有") || criminalRecord.equals("無"))) {
			request.setAttribute("criminalRecordError", "犯罪歴は「有」「無」から選択してください");
		}
		// 犯罪歴が有り、かつ理由が未記載の場合はエラーを返す
		else if (criminalRecord.equals("有")) {
			// 未入力項目があればエラーを返す
			if (ValidationUtil.isNullOrEmpty(reasonForTheCrime)) {
				request.setAttribute("reasonForTheCrimeError", "犯罪の具体的な理由（Reason for the crime）を入力してください。");
			}
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(reasonForTheCrime)) {
				request.setAttribute("reasonForTheCrimeError", "使用できない特殊文字が含まれています");
			}
			// 文字数が多い場合はエラーを返す。
			else if (ValidationUtil.areValidLengths(32, reasonForTheCrime)) {
				request.setAttribute("reasonForTheCrimeError", "犯罪の具体的な理由（Reason for the crime）は32文字以下で入力してください。");
			}
		}

		// 作成する行数のカウント
		int count = 0;

		// 在日親族（父・母・配偶者・子・兄弟姉妹・祖父母・叔(伯)父・叔(伯)母など）及び同居者のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(familyInJapan)) {
			request.setAttribute("familyInJapanError", "入力必須項目です。");
		}
		// 在日親族（父・母・配偶者・子・兄弟姉妹・祖父母・叔(伯)父・叔(伯)母など）及び同居者が「有」「無」以外の場合はエラーを返す
		else if (!(familyInJapan.equals("有") || familyInJapan.equals("無"))) {
			request.setAttribute("familyInJapanError", "在日親族（父・母・配偶者・子・兄弟姉妹・祖父母・叔(伯)父・叔(伯)母など）及び同居者は「有」「無」から選択してください");
		}
		// 在日親族（父・母・配偶者・子・兄弟姉妹・祖父母・叔(伯)父・叔(伯)母など）及び同居者が「有」の場合は下記ループを回す
		else if (familyInJapan.equals("有")) {
			// 作成する行ごとにデータを取り出す
			for (int i = 1; i <= 6; i++) {

				// 末尾に添付する番号のString
				String num = String.valueOf(i);

				// 入力された値を変数に格納
				String relationship = request.getParameter("relationship" + num);
				String relationshipError = "relationship" + num + "Error";
				String relativeName = request.getParameter("relativeName" + num);
				String relativeNameError = "relativeName" + num + "Error";
				String relativeBirthYear = request.getParameter("relativeBirthYear" + num);
				String relativeBirthMonth = request.getParameter("relativeBirthMonth" + num);
				String relativeBirthDay = request.getParameter("relativeBirthDay" + num);
				String relativeBirthError = "relativeBirth" + num + "Error";
				String relativeNationalityRegion = request.getParameter("relativeNationalityRegion" + num);
				String relativeNationalityRegionError = "relativeNationalityRegion" + num + "Error";
				String livingTogether = request.getParameter("livingTogether" + num);
				String livingTogetherError = "livingTogether" + num + "Error";
				String placeOfEmployment = request.getParameter("placeOfEmployment" + num);
				String placeOfEmploymentError = "placeOfEmployment" + num + "Error";
				String cardNumber = request.getParameter("cardNumber" + num);
				String cardNumberError = "cardNumber" + num + "Error";

				if (ValidationUtil.isNullOrEmpty(relationship, relativeName, relativeBirthYear,
						relativeBirthMonth,
						relativeBirthDay, relativeNationalityRegion, livingTogether, placeOfEmployment,
						cardNumber) && i != 1) {
					// 初めの行以外で入力がない場合はその行で終わる
					break;
				}

				// 続柄（Relationship）のエラー処理
				// 未入力項目があればエラーを返す
				if (ValidationUtil.isNullOrEmpty(relationship)) {
					request.setAttribute(relationshipError, "入力必須項目です。");
				}
				// 入力値に特殊文字が入っていないか確認する
				else if (ValidationUtil.containsForbiddenChars(relationship)) {
					request.setAttribute(relationshipError, "使用できない特殊文字が含まれています");
				}
				// 文字数が多い場合はエラーを返す。
				else if (ValidationUtil.areValidLengths(32, relationship)) {
					request.setAttribute(relationshipError, "続柄（Relationship）は32文字以下で入力してください。");
				}

				// 氏名（Name）のエラー処理
				// 未入力項目があればエラーを返す
				if (ValidationUtil.isNullOrEmpty(relativeName)) {
					request.setAttribute(relativeNameError, "入力必須項目です。");
				}
				// 入力値に特殊文字が入っていないか確認する
				else if (ValidationUtil.containsForbiddenChars(relativeName)) {
					request.setAttribute(relativeNameError, "使用できない特殊文字が含まれています");
				}
				// 文字数が多い場合はエラーを返す。
				else if (ValidationUtil.areValidLengths(32, relativeName)) {
					request.setAttribute(relativeNameError, "氏名（Name）は32文字以下で入力してください。");
				}

				// 親族生年月日（Date of birth）の満了日のエラー処理
				// 未入力項目があればエラーを返す
				if (ValidationUtil.isNullOrEmpty(relativeBirthYear, relativeBirthMonth, relativeBirthDay)) {
					request.setAttribute(relativeBirthError, "入力必須項目です。");
				}
				// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
				else if (ValidationUtil.isFourDigit(relativeBirthYear) ||
						ValidationUtil.isOneOrTwoDigit(relativeBirthMonth, relativeBirthDay)) {
					request.setAttribute(relativeBirthError, "年月日は正規の桁数で入力してください。");
				}
				// 親族生年月日（Date of birth）が存在しない日付の場合はeffectiveYearにする
				else if (ValidationUtil.validateDate(relativeBirthYear, relativeBirthMonth, relativeBirthDay)) {
					request.setAttribute(relativeBirthError, "存在しない日付です。");
				}

				// 国籍・地域（Nationality/Region）のエラー処理
				// 未入力項目があればエラーを返す
				if (ValidationUtil.isNullOrEmpty(relativeNationalityRegion)) {
					request.setAttribute(relativeNationalityRegionError, "入力必須項目です。");
				}
				// 入力値に特殊文字が入っていないか確認する
				else if (ValidationUtil.containsForbiddenChars(relativeNationalityRegion)) {
					request.setAttribute(relativeNationalityRegionError, "使用できない特殊文字が含まれています");
				}
				// 文字数が多い場合はエラーを返す。
				else if (ValidationUtil.areValidLengths(32, relativeNationalityRegion)) {
					request.setAttribute(relativeNationalityRegionError, "国籍・地域（Nationality/Region）は32文字以下で入力してください。");
				}

				// 同居の有無（Residing with）のエラー処理
				// 未入力項目があればエラーを返す
				if (ValidationUtil.isNullOrEmpty(livingTogether)) {
					request.setAttribute(livingTogetherError, "入力必須項目です。");
				}
				// 同居の有無（Residing with）が「有」「無」以外の場合はエラーを返す
				if (!(livingTogether.equals("有") || livingTogether.equals("無"))) {
					request.setAttribute(livingTogetherError,
							"同居の有無は「有（Living together）」「無（Not living together）」から選択してください");
				}

				// 勤務先名称・通学先名称（Place of employment/Place of school）のエラー処理
				// 未入力項目があればエラーを返す
				if (ValidationUtil.isNullOrEmpty(placeOfEmployment)) {
					request.setAttribute(placeOfEmploymentError, "入力必須項目です。");
				}
				// 入力値に特殊文字が入っていないか確認する
				else if (ValidationUtil.containsForbiddenChars(placeOfEmployment)) {
					request.setAttribute(placeOfEmploymentError, "使用できない特殊文字が含まれています");
				}
				// 文字数が多い場合はエラーを返す。
				else if (ValidationUtil.areValidLengths(32, placeOfEmployment)) {
					request.setAttribute(placeOfEmploymentError,
							"勤務先名称・通学先名称（Place of employment/Place of school）は32文字以下で入力してください。");
				}

				// 在留カード番号・特別永住者証明書番号（Residence card number Special Permanent Resident Certificate number）のエラー処理
				// 未入力項目があればエラーを返す
				if (ValidationUtil.isNullOrEmpty(cardNumber)) {
					request.setAttribute(placeOfEmploymentError, "入力必須項目です。");
				}
				// 在留カード番号・特別永住者証明書番号（Residence card number Special Permanent Resident Certificate number）の記号番号のチェック
				if (ValidationUtil.isResidentCard(cardNumber)) {
					request.setAttribute(cardNumberError, "在留カード番号は記号番号の最初と最後の２桁は大文字のアルファベット、間は半角数字8桁で入力してください。");
				}

				// 作成する行数カウントの追加
				count++;
			}
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "period-update-first.jsp";
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

			// 電話番号のデータベースからの取り出し
			String reEncryptedTel = dao.getTel(id);
			String tel = decrypt.getDecryptedDate(result, reEncryptedTel);

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

			// 学生種類のデータベースからの取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			String studentType = decrypt.getDecryptedDate(result, reEncryptedStudentType);

			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(lastName, firstName, tel, address, birthYear, birthMonth, birthDay,
					studentType)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// もし学生種類が留学生でなければエラーを返す
			if (!studentType.equals("留学生")) {
				request.setAttribute("exchangeStudentError", "当該書類は留学生のみが発行可能です。");
				return "period-update-first.jsp";
			}

			// 姓名を結合する
			String name = lastName + " " + firstName;

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/internationalStudentPDF/在留期間更新許可申請書1枚目.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));
			// PDFへの書き込み
			// 国籍・地域
			editor.writeText(font, nationalityRegion, 130f, 653f, 125f, "center", 12);

			// 生年月日
			editor.writeText(font, birthYear, 355f, 653f, 70f, "left", 12);
			editor.writeText(font, birthMonth, 420f, 653f, 70f, "left", 12);
			editor.writeText(font, birthDay, 475f, 653f, 70f, "left", 12);
			// 名前
			editor.writeText(font, name, 130f, 630f, 390f, "center", 12);
			// 性別
			if (sex.equals("男")) {
				// 使用目的に応じて〇の位置を変える
				editor.writeText(font, "〇", 101f, 604f, 40f, "left", 18);
			} else {
				editor.writeText(font, "〇", 127f, 604f, 40f, "left", 18);
			}
			// 配偶者の有無
			if (maritalStatus.equals("有")) {
				// 使用目的に応じて〇の位置を変える
				editor.writeText(font, "〇", 361.5f, 605f, 40f, "left", 18);
			} else {
				editor.writeText(font, "〇", 387.5f, 604f, 40f, "left", 18);
			}
			// 職業	
			editor.writeText(font, "学生", 105f, 577f, 100f, "center", 12);
			// 本国における居住地
			editor.writeText(font, homeTown, 310f, 577f, 210f, "center", 12);
			// 住所
			editor.writeText(font, address, 130f, 557f, 390f, "left", 12);
			// 電話番号
			if (tel.length() == 11) {
				tel = tel.substring(0, 3) + "-" + tel.substring(3, 7) + "-" + tel.substring(7, 11);
				editor.writeText(font, tel, 360f, 532f, 160f, "center", 12);
			} else {
				tel = tel.substring(0, 3) + "-" + tel.substring(3, 6) + "-" + tel.substring(6, 10);
				editor.writeText(font, tel, 130f, 532f, 100f, "center", 12);
			}

			// パスポート・有効期限
			editor.writeText(font, passportNumber, 155f, 510f, 112f, "center", 12);
			editor.writeText(font, effectiveYear, 370f, 510f, 70f, "left", 12);
			editor.writeText(font, effectiveMonth, 430f, 510f, 70f, "left", 12);
			editor.writeText(font, effectiveDay, 485f, 510f, 70f, "left", 12);

			// 在留資格等
			editor.writeText(font, statusOfResidence, 155f, 485f, 138f, "center", 12);
			editor.writeText(font, periodOfStay, 371f, 485f, 150f, "center", 12);
			editor.writeText(font, periodYear, 180f, 462, 40f, "left", 12);
			editor.writeText(font, periodMonth, 240f, 462f, 40f, "left", 12);
			editor.writeText(font, periodDay, 290f, 462f, 40f, "left", 12);
			editor.writeText(font, residentCard, 155f, 439f, 175f, "center", 12);
			editor.writeText(font, desiredPeriodOfStay, 155f, 416f, 113f, "center", 12);
			editor.writeText(font, reason, 155f, 393f, 366f, "center", 12);

			// 犯罪歴の有無
			if (criminalRecord.equals("有")) {
				editor.writeText(font, "〇", 75f, 353.5f, 40f, "left", 18);
				editor.writeText(font, reasonForTheCrime, 155f, 353.5f, 313f, "center", 12);
			} else {
				editor.writeText(font, "〇", 491f, 353.5f, 40f, "left", 18);
			}

			// 在日親族及び同居者の有無
			if (familyInJapan.equals("有")) {
				editor.writeText(font, "〇", 75f, 308f, 40f, "left", 18);
			} else {
				editor.writeText(font, "〇", 398f, 308f, 40f, "left", 18);
			}

			float row = 0;
			for (int i = 1; i <= count; i++) {
				// 末尾に添付する番号のString
				String num = String.valueOf(i);

				// 入力された値を変数に格納
				String relationship = request.getParameter("relationship" + num);
				String relativeName = request.getParameter("relativeName" + num);
				String relativeBirthYear = request.getParameter("relativeBirthYear" + num);
				String relativeBirthMonth = request.getParameter("relativeBirthMonth" + num);
				String relativeBirthDay = request.getParameter("relativeBirthDay" + num);
				String relativeNationalityRegion = request.getParameter("relativeNationalityRegion" + num);
				String livingTogether = request.getParameter("livingTogether" + num);
				String placeOfEmployment = request.getParameter("placeOfEmployment" + num);
				String cardNumber = request.getParameter("cardNumber" + num);
				// PDFへの記入

				editor.writeText(font, relationship, 52f, 248f - row, 39f, "center", 12);
				editor.writeText(font, relativeName, 92f, 248f - row, 113f, "center", 12);
				editor.writeText(font, relativeBirthYear + "年" + relativeBirthMonth + "月" + relativeBirthDay + "日",
						206f, 248f - row, 53f, "left", 12);
				// 在日親族及び同居者の有無
				if (livingTogether.equals("有")) {
					editor.writeText(font, "〇", 308f, 253f - row, 40f, "left", 12);
				} else {
					editor.writeText(font, "〇", 320.5f, 253f - row, 40f, "left", 12);
				}
				editor.writeText(font, relativeNationalityRegion, 257f, 248f - row, 37f, "center", 12);
				editor.writeText(font, placeOfEmployment, 346f, 248f - row, 87f, "center", 12);
				editor.writeText(font, cardNumber, 435f, 248f - row, 100f, "center", 12);

				row += 20;
			}

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Period Update First.jsp");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "在留期間更新許可申請書1枚目");
			// Close and save
			editor.close("Period_Update_First.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "period-update-first.jsp";
		}
	}
}
