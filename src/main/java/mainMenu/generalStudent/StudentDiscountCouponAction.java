package mainMenu.generalStudent;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
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

public class StudentDiscountCouponAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(StudentDiscountCouponAction.class);

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

		// 入力された値をリクエストに格納	
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			request.setAttribute(paramName, paramValue);
		}

		// 未入力項目があればエラーを返す
		if (requestYear == null || requestMonth == null || requestDay == null
				|| requestYear.isEmpty()
				|| requestMonth.isEmpty() || requestDay.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "student-discount-coupon.jsp";
		}

		// 作成する行数のカウント
		int count = 0;

		// 作成する行ごとにデータを取り出す
		for (int i = 1; i <= 2; i++) {

			// 末尾に添付する番号のString
			String num = String.valueOf(i);

			// 入力された値を変数に格納
			String sheetsRequired = request.getParameter("sheetsRequired" + num);
			String startingStation = request.getParameter("startingStation" + num);
			String arrivalStation = request.getParameter("arrivalStation" + num);
			String intendedUse = request.getParameter("intendedUse" + num);
			String reason = request.getParameter("reason" + num);

			// 未入力項目があればエラーを返す
			if (sheetsRequired == null
					|| startingStation == null || arrivalStation == null || intendedUse == null
					|| sheetsRequired.isEmpty() || startingStation.isEmpty()
					|| arrivalStation.isEmpty()
					|| intendedUse.isEmpty()) {
				// 最初の行(1行目)が空ならばエラーを返す
				if (i == 1) {
					request.setAttribute("nullError", "未入力項目があります。");
					return "student-discount-coupon.jsp";
				} else {
					break;
				}
			}

			// 使用目的がその他で理由未記載の場合はエラーを返す。
			if (intendedUse.equals("その他") && (reason == null || reason.isEmpty())) {
				request.setAttribute("nullError", "使用目的がその他の場合は理由を記載してください。");
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

			// 文字数が18文字より多い場合はエラーを返す
			if (startingStation.length() > 18 || arrivalStation.length() > 18 || reason.length() > 18) {
				request.setAttribute("valueLongError", "18文字以下で入力してください。");
			}

			// エラーが発生している場合は元のページに戻す
			if (request.getAttribute("nullError") != null
					|| request.getAttribute("dayError") != null
					|| request.getAttribute("valueLongError") != null) {
				return "student-discount-coupon.jsp";
			}

			// 作成する行数カウントの追加
			count++;
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
			// クラス名の末尾に「科」がついていた場合は削除する
			if (className.endsWith("科")) {
				className = className.substring(0, className.length() - 1);
			}
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

			// 生年月日と申請日をLocalDate形式に変換
			int year = Integer.parseInt(requestYear);
			int month = Integer.parseInt(requestMonth);
			int day = Integer.parseInt(requestDay);
			LocalDate requestDate = LocalDate.of(year, month, day);
			year = Integer.parseInt(birthYear);
			month = Integer.parseInt(birthMonth);
			day = Integer.parseInt(birthDay);
			LocalDate birthDate = LocalDate.of(year, month, day);

			// 年齢の計算
			int age = Period.between(birthDate, requestDate).getYears();

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/generalStudentPDF/学割証発行願.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));

			// PDFへの記載
			float row = 0;
			for (int i = 1; i <= count; i++) {
				// 末尾に添付する番号のString
				String num = String.valueOf(i);

				// 入力された値を変数に格納
				String sheetsRequired = request.getParameter("sheetsRequired" + num);
				String startingStation = request.getParameter("startingStation" + num);
				String arrivalStation = request.getParameter("arrivalStation" + num);
				String intendedUse = request.getParameter("intendedUse" + num);
				String reason = request.getParameter("reason" + num);

				// PDFへの記入
				// 申請年月日
				editor.writeText(font, requestYear, 173f, 666f - row, 40f, "left", 12);
				editor.writeText(font, requestMonth, 230f, 666f - row, 40f, "left", 12);
				editor.writeText(font, requestDay, 275f, 666f - row, 40f, "left", 12);
				// 必要枚数
				editor.writeText(font, sheetsRequired, 350f, 666f - row, 40f, "left", 12);
				// 乗車線区間
				editor.writeText(font, startingStation, 138f, 632f - row, 97f, "center", 12);
				editor.writeText(font, arrivalStation, 262f, 632f - row, 84f, "center", 12);
				// クラス名・学年・組・学籍番号・名前・年齢
				editor.writeText(font, className, 138f, 597f - row, 135f, "center", 12);
				editor.writeText(font, schoolYear, 305f, 597f - row, 40f, "left", 12);
				editor.writeText(font, classNumber, 352f, 597f - row, 40f, "left", 12);
				editor.writeText(font, studentNumber, 138f, 562f - row, 242f, "center", 12);
				editor.writeText(font, name, 138f, 528f - row, 180f, "center", 12);
				editor.writeText(font, String.valueOf(age), 340f, 528f - row, 40f, "left", 12);
				// 郵便番号
				editor.writeText(font, FirstPostCode, 158f, 502f - row, 180f, "left", 10);
				editor.writeText(font, LastPostCode, 205f, 502f - row, 180f, "left", 10);
				// 電話番号
				editor.writeText(font, firstTel, 305f, 502f - row, 40f, "left", 10);
				editor.writeText(font, secondTel, 355f, 502f - row, 40f, "left", 10);
				editor.writeText(font, lastTel, 410f, 502f - row, 40f, "left", 10);

				// 住所の文字数によって表示する位置を変える
				if (address.length() < 28) {
					editor.writeText(font, address, 138f, 484f - row, 328f, "left", 12);
				} else {
					editor.writeText(font, address.substring(0, 27), 138f, 490f - row, 328f, "left", 12);
					editor.writeText(font, address.substring(27, address.length()), 138f, 478f - row, 328f, "left", 12);
				}

				// 使用目的に応じて〇の位置を変える
				if (intendedUse.equals("帰省")) {
					editor.writeText(font, "〇", 145f, 454f - row, 40f, "left", 16);
				} else if (intendedUse.equals("見学")) {
					editor.writeText(font, "〇", 237f, 454f - row, 40f, "left", 16);
				} else {
					editor.writeText(font, "〇", 320f, 454f - row, 40f, "left", 16);
					editor.writeText(font, reason, 379f, 455f - row, 73f, "center", 12);
				}

				row += 364;
			}

			// Close and save
			editor.close("学割証発行願.pdf");
			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Student Discount Coupon");
			// PDF作成成功画面に遷移
			request.setAttribute("createPDF", "「学割証発行願」を作成しました。");
			return "create-pdf-success.jsp";
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "student-discount-coupon.jsp";
		}
	}
}
