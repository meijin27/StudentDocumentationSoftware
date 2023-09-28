package mainMenu.internationalStudent;

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

public class PeriodUpdateFirstAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(PeriodUpdateFirstAction.class);

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
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			request.setAttribute(paramName, paramValue);
		}

		// 未入力項目があればエラーを返す
		if (nationalityRegion == null || nationalityRegion.isEmpty()
				|| homeTown == null || homeTown.isEmpty()
				|| sex == null || sex.isEmpty()
				|| maritalStatus == null || maritalStatus.isEmpty()
				|| passportNumber == null || passportNumber.isEmpty()
				|| effectiveYear == null || effectiveYear.isEmpty()
				|| effectiveMonth == null || effectiveMonth.isEmpty()
				|| effectiveDay == null || effectiveDay.isEmpty()
				|| statusOfResidence == null || statusOfResidence.isEmpty()
				|| periodOfStay == null || periodOfStay.isEmpty()
				|| periodYear == null || periodYear.isEmpty()
				|| periodMonth == null || periodMonth.isEmpty()
				|| periodDay == null || periodDay.isEmpty()
				|| residentCard == null || residentCard.isEmpty()
				|| desiredPeriodOfStay == null || desiredPeriodOfStay.isEmpty()
				|| reason == null || reason.isEmpty()
				|| criminalRecord == null || criminalRecord.isEmpty()
				|| familyInJapan == null || familyInJapan.isEmpty()) {

			request.setAttribute("nullError", "未入力項目があります。");
			return "period-update-first.jsp";
		}

		// 年月日が存在しない日付の場合はエラーにする
		try {
			int checkYear = Integer.parseInt(effectiveYear);
			int checkMonth = Integer.parseInt(effectiveMonth);
			int checkDay = Integer.parseInt(effectiveDay);
			// 日付の妥当性チェック
			LocalDate date = LocalDate.of(checkYear, checkMonth, checkDay);

			checkYear = Integer.parseInt(periodYear);
			checkMonth = Integer.parseInt(periodMonth);
			checkDay = Integer.parseInt(periodDay);
			// 日付の妥当性チェック
			date = LocalDate.of(checkYear, checkMonth, checkDay);
		} catch (NumberFormatException e) {
			request.setAttribute("dayError", "年月日は数字で入力してください。");
		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
		}

		// 文字数が32文字より多い場合はエラーを返す。セレクトボックス・ラジオボタンの有効範囲画外の場合もエラーを返す。
		if (nationalityRegion.length() > 32 || homeTown.length() > 32 || passportNumber.length() > 32
				|| statusOfResidence.length() > 32 || periodOfStay.length() > 32 || desiredPeriodOfStay.length() > 32
				|| reason.length() > 32 || effectiveYear.length() > 4
				|| effectiveMonth.length() > 2 || effectiveDay.length() > 2 || periodYear.length() > 4
				|| periodMonth.length() > 2 || periodDay.length() > 2 || sex.length() > 1
				|| maritalStatus.length() > 1 || criminalRecord.length() > 1) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// 在留カードの記号番号のチェック
		if (residentCard.length() != 12) {
			// 文字数が12文字でない場合はエラーを返す
			request.setAttribute("residentCardError", "在留カード番号は12文字で入力してください。");
		} else if (!residentCard.matches("^[A-Z]{2}\\d{8}[A-Z]{2}$")) {
			// 在留カード番号の最初と最後の２桁が大文字のアルファベット、間が半角数字8桁でなければエラーを返す
			request.setAttribute("residentCardError", "在留カード番号は記号番号の最初と最後の２桁は大文字のアルファベット、間は半角数字8桁で入力してください。");
		}

		// ラジオボタンの入力値チェック
		// 性別が「男」「女」以外の場合はエラーを返す
		if (!(sex.equals("男") || sex.equals("女"))) {
			request.setAttribute("innerError", "性別は「男（Male）」「女（Female）」から選択してください");
			// 配偶者が「有」「無」以外の場合はエラーを返す
		} else if (!(maritalStatus.equals("有") || maritalStatus.equals("無"))) {
			request.setAttribute("innerError", "配偶者は「有（Married）」「無（Single）」から選択してください");
		}

		// 犯罪歴が「有」「無」以外の場合はエラーを返す
		if (!(criminalRecord.equals("有") || criminalRecord.equals("無"))) {
			request.setAttribute("criminalError", "犯罪歴は「有」「無」から選択してください");
		}

		// 犯罪歴が有り、かつ理由が未記載の場合はエラーを返す
		if (criminalRecord.equals("有") && (reasonForTheCrime == null || reasonForTheCrime.isEmpty())) {
			request.setAttribute("criminalError", "犯罪の具体的な理由を入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("numberError") != null
				|| request.getAttribute("dayError") != null
				|| request.getAttribute("valueLongError") != null
				|| request.getAttribute("residentCardError") != null || request.getAttribute("criminalError") != null
				|| request.getAttribute("innerError") != null) {
			return "period-update-first.jsp";
		}
		// 作成する行数のカウント
		int count = 0;

		// 作成する行ごとにデータを取り出す
		for (int i = 1; i <= 6; i++) {

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

			// 在日親族及び同居者が「無」の場合はbreakする
			if (familyInJapan.equals("無")) {
				break;
				// 未入力項目があればbreakする			    
			} else if (relationship == null || relationship.isEmpty()
					|| relativeName == null || relativeName.isEmpty()
					|| relativeBirthYear == null || relativeBirthYear.isEmpty()
					|| relativeBirthMonth == null || relativeBirthMonth.isEmpty()
					|| relativeBirthDay == null || relativeBirthDay.isEmpty()
					|| relativeNationalityRegion == null || relativeNationalityRegion.isEmpty()
					|| livingTogether == null || livingTogether.isEmpty()
					|| placeOfEmployment == null || placeOfEmployment.isEmpty()
					|| cardNumber == null || cardNumber.isEmpty()) {
				// 在日親族及び同居者が「有」かつ１人目の入力がない場合はエラーを返す
				if (i == 1) {
					request.setAttribute("nullError", "在日親族及び同居者情報に未入力項目があります。");
					return "period-update-first.jsp";
				}
				// 入力がない場合はその行で終わる
				break;
			}

			// 年月日が存在しない日付の場合はエラーにする
			try {
				int checkYear = Integer.parseInt(relativeBirthYear);
				int checkMonth = Integer.parseInt(relativeBirthMonth);
				int checkDay = Integer.parseInt(relativeBirthDay);
				// 日付の妥当性チェック
				LocalDate date = LocalDate.of(checkYear, checkMonth, checkDay);
			} catch (NumberFormatException e) {
				request.setAttribute("dayError", "年月日は数字で入力してください。");
			} catch (DateTimeException e) {
				request.setAttribute("dayError", "存在しない日付です。");
			}

			// 文字数が32文字より多い場合はエラーを返す。セレクトボックス・ラジオボタンの有効範囲画外の場合もエラーを返す。
			if (relationship.length() > 32 || relativeName.length() > 32 || relativeNationalityRegion.length() > 32
					|| livingTogether.length() > 32 || placeOfEmployment.length() > 32 || cardNumber.length() > 32
					|| relativeBirthYear.length() > 4
					|| relativeBirthMonth.length() > 2 || relativeBirthDay.length() > 2
					|| livingTogether.length() > 1) {
				request.setAttribute("valueLongError", "32文字以下で入力してください。");
			}

			// 在留カードの記号番号のチェック
			if (cardNumber.length() != 12) {
				// 文字数が12文字でない場合はエラーを返す
				request.setAttribute("residentCardError", "在留カード番号は12文字で入力してください。");
			} else if (!cardNumber.matches("^[A-Z]{2}\\d{8}[A-Z]{2}$")) {
				// 在留カード番号の最初と最後の２桁が大文字のアルファベット、間が半角数字8桁でなければエラーを返す
				request.setAttribute("residentCardError", "在留カード番号は記号番号の最初と最後の２桁は大文字のアルファベット、間は半角数字8桁で入力してください。");
			}

			// ラジオボタンの入力値チェック
			// 同居の有無が「有」「無」以外の場合はエラーを返す
			if (!(livingTogether.equals("有") || livingTogether.equals("無"))) {
				request.setAttribute("innerError", "同居の有無は「有（Living together）」「無（Not living together）」から選択してください");
			}

			// エラーが発生している場合は元のページに戻す
			if (request.getAttribute("dayError") != null
					|| request.getAttribute("valueLongError") != null
					|| request.getAttribute("residentCardError") != null
					|| request.getAttribute("innerError") != null) {
				return "period-update-first.jsp";
			}

			// 作成する行数カウントの追加
			count++;
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

			// 学生種類のデータベースからの取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			String encryptedStudentType = CipherUtil.commonDecrypt(reEncryptedStudentType);
			String studentType = CipherUtil.decrypt(masterKey, iv, encryptedStudentType);
			// もし学生種類が留学生でなければエラーを返す
			if (!studentType.equals("留学生")) {
				request.setAttribute("exchangeStudentError", "当該書類は留学生のみが発行可能です。");
				return "period-update-first.jsp";
			}

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
