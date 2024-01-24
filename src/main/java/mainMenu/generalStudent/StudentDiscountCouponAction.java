package mainMenu.generalStudent;

import java.time.LocalDate;
import java.time.Period;
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
import tool.RequestAndSessionUtil;
import tool.ValidationUtil;

public class StudentDiscountCouponAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(StudentDiscountCouponAction.class);

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
		String requestYear = request.getParameter("requestYear");
		String requestMonth = request.getParameter("requestMonth");
		String requestDay = request.getParameter("requestDay");

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 申請年月日のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "入力必須項目です。");
		}
		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isFourDigit(requestYear) ||
				ValidationUtil.isOneOrTwoDigit(requestMonth, requestDay)) {
			request.setAttribute("requestError", "年月日は正規の桁数で入力してください。");
		}
		// 申請年月日が存在しない日付の場合はエラーにする
		else if (ValidationUtil.validateDate(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "存在しない日付です。");
		}

		// 作成する行数のカウント
		int count = 0;

		// 作成する行ごとにデータを取り出す
		for (int i = 1; i <= 2; i++) {

			// 末尾に添付する番号のString
			String num = String.valueOf(i);

			// 入力された値を変数に格納
			String sheetsRequired = request.getParameter("sheetsRequired" + num);
			String sheetsRequiredError = "sheetsRequired" + num + "Error";
			String startingStation = request.getParameter("startingStation" + num);
			String startingStationError = "startingStation" + num + "Error";
			String arrivalStation = request.getParameter("arrivalStation" + num);
			String arrivalStationError = "arrivalStation" + num + "Error";
			String intendedUse = request.getParameter("intendedUse" + num);
			String intendedUseError = "intendedUse" + num + "Error";
			String reason = request.getParameter("reason" + num);
			String reasonError = "reason" + num + "Error";

			// 未入力項目があればエラーを返す
			if (ValidationUtil.isNullOrEmpty(sheetsRequired, startingStation, arrivalStation, intendedUse) && i != 1) {
				break;
			}

			// 必要枚数のエラー処理
			// 未入力項目があればエラーを返す
			if (ValidationUtil.isNullOrEmpty(sheetsRequired)) {
				request.setAttribute(sheetsRequiredError, "入力必須項目です。");
			}
			// 必要枚数は1か2でなければエラーを返す
			else if (!(sheetsRequired.equals("1") || sheetsRequired.equals("2"))) {
				request.setAttribute(sheetsRequiredError, "必要枚数は1枚・2枚から選択してください。");
			}

			// 出発駅のエラー処理
			// 未入力項目があればエラーを返す
			if (ValidationUtil.isNullOrEmpty(startingStation)) {
				request.setAttribute(startingStationError, "入力必須項目です。");
			}
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(startingStation)) {
				request.setAttribute(startingStationError, "使用できない特殊文字が含まれています");
			}
			// 文字数が多い場合はエラーを返す。
			else if (ValidationUtil.areValidLengths(18, startingStation)) {
				request.setAttribute(startingStationError, "出発駅は18文字以下で入力してください。");
			}

			// 到着駅のエラー処理
			// 未入力項目があればエラーを返す
			if (ValidationUtil.isNullOrEmpty(arrivalStation)) {
				request.setAttribute(arrivalStationError, "入力必須項目です。");
			}
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(arrivalStation)) {
				request.setAttribute(arrivalStationError, "使用できない特殊文字が含まれています");
			}
			// 文字数が多い場合はエラーを返す。
			else if (ValidationUtil.areValidLengths(18, arrivalStation)) {
				request.setAttribute(arrivalStationError, "到着駅は18文字以下で入力してください。");
			}

			// 使用目的のエラー処理
			// 未入力項目があればエラーを返す
			if (ValidationUtil.isNullOrEmpty(intendedUse)) {
				request.setAttribute(intendedUseError, "入力必須項目です。");
			}
			// 文字数が3文字より多い場合はエラーを返す。
			else if (ValidationUtil.areValidLengths(3, intendedUse)) {
				request.setAttribute(intendedUseError, "3文字以下で入力してください。");
			}
			// 使用目的がその他で理由未記載の場合はエラーを返す。
			else if (intendedUse.equals("その他")) {
				if (ValidationUtil.isNullOrEmpty(reason)) {
					request.setAttribute(reasonError, "使用目的がその他の場合は理由を記載してください。");
				} else if (ValidationUtil.areValidLengths(18, reason)) {
					request.setAttribute(reasonError, "理由は18文字以下で入力してください。");
				} else if (ValidationUtil.containsForbiddenChars(reason)) {
					request.setAttribute(reasonError, "使用できない特殊文字が含まれています");
				}
			}

			// 作成する行数カウントの追加
			count++;
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "student-discount-coupon.jsp";
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
			// 郵便番号のデータベースからの取り出し
			String reEncryptedPostCode = dao.getPostCode(id);
			String postCode = decrypt.getDecryptedDate(result, reEncryptedPostCode);
			// 住所のデータベースからの取り出し
			String reEncryptedAddress = dao.getAddress(id);
			String address = decrypt.getDecryptedDate(result, reEncryptedAddress);
			// クラス名のデータベースからの取り出し
			String reEncryptedClassName = dao.getClassName(id);
			String className = decrypt.getDecryptedDate(result, reEncryptedClassName);
			// 学年のデータベースからの取り出し
			String reEncryptedSchoolYear = dao.getSchoolYear(id);
			String schoolYear = decrypt.getDecryptedDate(result, reEncryptedSchoolYear);
			// クラス番号のデータベースからの取り出し
			String reEncryptedClassNumber = dao.getClassNumber(id);
			String classNumber = decrypt.getDecryptedDate(result, reEncryptedClassNumber);
			// 学籍番号のデータベースからの取り出し
			String reEncryptedStudentNumber = dao.getStudentNumber(id);
			String studentNumber = decrypt.getDecryptedDate(result, reEncryptedStudentNumber);
			// 誕生年のデータベースからの取り出し
			String reEncryptedBirthYear = dao.getBirthYear(id);
			String encryptedBirthYear = CipherUtil.commonDecrypt(reEncryptedBirthYear);
			String birthYear = decrypt.getDecryptedDate(result, reEncryptedBirthYear);
			// 誕生月のデータベースからの取り出し
			String reEncryptedBirthMonth = dao.getBirthMonth(id);
			String encryptedBirthMonth = CipherUtil.commonDecrypt(reEncryptedBirthMonth);
			String birthMonth = decrypt.getDecryptedDate(result, reEncryptedBirthMonth);
			// 誕生日のデータベースからの取り出し
			String reEncryptedBirthDay = dao.getBirthDay(id);
			String encryptedBirthDay = CipherUtil.commonDecrypt(reEncryptedBirthDay);
			String birthDay = decrypt.getDecryptedDate(result, reEncryptedBirthDay);
			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(lastName, firstName, tel, postCode, address, className, schoolYear,
					classNumber,
					studentNumber, birthYear, birthMonth, birthDay)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
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
			String FirstPostCode = postCode.substring(0, 3);
			String LastPostCode = postCode.substring(3, 7);

			// 姓名を結合する
			String name = lastName + " " + firstName;
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

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Student Discount Coupon");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "学割証発行願");
			// Close and save
			editor.close("Student_Discount_Coupon.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "student-discount-coupon.jsp";
		}
	}
}
