package mainMenu.generalStudent;

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

public class PetitionForDeferredPaymentAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(PetitionForDeferredPaymentAction.class);

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
		String reason = request.getParameter("reason");
		String howToRaiseFunds = request.getParameter("howToRaiseFunds");
		String amountPayable = request.getParameter("amountPayable");
		String generalDeliveryYear = request.getParameter("generalDeliveryYear");
		String generalDeliveryMonth = request.getParameter("generalDeliveryMonth");
		String generalDeliveryDay = request.getParameter("generalDeliveryDay");
		String tuitionFeePaid = request.getParameter("tuitionFeePaid");

		// 留学生専用項目
		String remittanceFromCountry = request.getParameter("remittanceFromCountry");
		String reasonNoRemittance = request.getParameter("reasonNoRemittance");
		String invoiceYear = request.getParameter("invoiceYear");
		String invoiceMonth = request.getParameter("invoiceMonth");
		String invoiceDay = request.getParameter("invoiceDay");

		// 支払金額確認用、この金額が総計で０にならなければならない
		int totalPayment = 0;
		// 入力された金額をカンマ付きに変更するメソッド
		NumberFormat numberFormat = NumberFormat.getNumberInstance();

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 願出年月日のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "入力必須項目です。");
		}
		// 年月日が２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isOneOrTwoDigit(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "年月日は正規の桁数で入力してください。");
		}
		// 願出年月日が存在しない日付の場合はエラーにする
		else if (ValidationUtil.validateDate(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "存在しない日付です。");
		}

		// 納付できない理由のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(reason)) {
			request.setAttribute("reasonError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(reason)) {
			request.setAttribute("reasonError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(64, reason)) {
			request.setAttribute("reasonError", "納付できない理由は64文字以下で入力してください。");
		}

		// 学費の捻出方法のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(howToRaiseFunds)) {
			request.setAttribute("howToRaiseFundsError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(howToRaiseFunds)) {
			request.setAttribute("howToRaiseFundsError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(64, howToRaiseFunds)) {
			request.setAttribute("howToRaiseFundsError", "学費の捻出方法は64文字以下で入力してください。");
		}

		// 納付すべき金額（学費、教材費、積立金等）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(amountPayable)) {
			request.setAttribute("amountPayableError", "入力必須項目です。");
		}
		// 入力金額に数字以外が含まれている、もしくは数字が１０００万円を超える場合はエラーを返す
		else if (!amountPayable.matches("\\d+$") || amountPayable.length() > 7) {
			request.setAttribute("amountPayableError", "金額は数字のみ7桁以下で入力してください。");
		}

		// 通常納期年月日日のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(generalDeliveryYear, generalDeliveryMonth, generalDeliveryDay)) {
			request.setAttribute("generalDeliveryError", "入力必須項目です。");
		}
		// 年月日が２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isOneOrTwoDigit(generalDeliveryYear, generalDeliveryMonth, generalDeliveryDay)) {
			request.setAttribute("generalDeliveryError", "年月日は正規の桁数で入力してください。");
		}
		// 通常納期年月日が存在しない日付の場合はエラーにする
		else if (ValidationUtil.validateDate(generalDeliveryYear, generalDeliveryMonth, generalDeliveryDay)) {
			request.setAttribute("generalDeliveryError", "存在しない日付です。");
		}

		// 通常納期内納付学費等（教材費・積立金等を含む）のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(tuitionFeePaid)) {
			request.setAttribute("tuitionFeePaidError", "入力必須項目です。");
		}
		// 入力金額に数字以外が含まれている、もしくは数字が１０００万円を超える場合はエラーを返す
		else if (!tuitionFeePaid.matches("\\d+$") || tuitionFeePaid.length() > 7) {
			request.setAttribute("tuitionFeePaidError", "金額は数字のみ7桁以下で入力してください。");
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

			// 学生種別が留学生の場合は記入必須項目の確認を行う。
			if (studentType.equals("留学生")) {
				// 母国からの送金が入力されていない場合はエラーを返す
				if (ValidationUtil.isNullOrEmpty(remittanceFromCountry)) {
					request.setAttribute("remittanceFromCountryError", "母国からの送金有無を選択してください");
					// 母国からの送金が入力されている場合で「有」「無」以外の入力値の場合はエラーを返す
				} else if (!(remittanceFromCountry.equals("有") || remittanceFromCountry.equals("無"))) {
					request.setAttribute("remittanceFromCountryError", "母国からの送金有無は「有」「無」から選択してください");
					// 母国からの送金が「無」で理由未記載の場合はエラーを返す。
				} else if (remittanceFromCountry.equals("無")
						&& ValidationUtil.isNullOrEmpty(reasonNoRemittance)) {
					request.setAttribute("reasonNoRemittanceError", "母国からの送金がない場合は理由を記載してください。");
					// 母国からの送金が「無」で文字数が64文字より多い場合はエラーを返す。
				} else if (remittanceFromCountry.equals("無")
						&& ValidationUtil.areValidLengths(64, reasonNoRemittance)) {
					request.setAttribute("reasonNoRemittanceError", "母国からの送金がない理由は64文字以下で入力してください。");
					// 入力値に特殊文字が入っていないか確認する
				} else if (remittanceFromCountry.equals("無")
						&& ValidationUtil.containsForbiddenChars(reasonNoRemittance)) {
					request.setAttribute("reasonNoRemittanceError", "使用できない特殊文字が含まれています");
					// 母国からの送金が「有」ならばinvoiceの日時を記載を確認する
				} else if (remittanceFromCountry.equals("有")
						&& ValidationUtil.isNullOrEmpty(invoiceYear, invoiceMonth, invoiceDay)) {
					request.setAttribute("invoiceError", "母国からの送金がある場合は海外送金依頼書INVOICE交付申請年月日を記載してください。");
				} else if (remittanceFromCountry.equals("有")) {
					// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
					if (ValidationUtil.isFourDigit(invoiceYear) ||
							ValidationUtil.isOneOrTwoDigit(invoiceMonth, invoiceDay)) {
						request.setAttribute("invoiceError", "年月日は正規の桁数で入力してください。");
					} else {
						if (ValidationUtil.validateDate(invoiceYear, invoiceMonth, invoiceDay)) {
							request.setAttribute("invoiceError", "存在しない日付です。");
						}
					}
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "petition-for-deferred-payment.jsp";
		}

		// 作成する行数のカウント
		int count = 0;

		// 作成する行ごとにデータを取り出す
		for (int i = 1; i <= 4; i++) {

			// 末尾に添付する番号のString
			String num = String.valueOf(i);

			// 入力された値を変数に格納
			String deliveryYear = request.getParameter("deliveryYear" + num);
			String deliveryMonth = request.getParameter("deliveryMonth" + num);
			String deliveryDay = request.getParameter("deliveryDay" + num);
			String deliveryError = "delivery" + num + "Error";
			String deferredPaymentAmount = request.getParameter("deferredPaymentAmount" + num);
			String deferredPaymentAmountError = "deferredPaymentAmount" + num + "Error";

			// 最初の行(1行目)以外に未入力箇所があればブレークする
			if (ValidationUtil.isNullOrEmpty(deliveryYear, deliveryMonth, deliveryDay, deferredPaymentAmount)
					&& i != 1) {
				break;
			}

			// 納付期限のエラー処理
			// 未入力項目があればエラーを返す
			if (ValidationUtil.isNullOrEmpty(deliveryYear, deliveryMonth, deliveryDay)) {
				request.setAttribute(deliveryError, "入力必須項目です。");
			}
			// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
			else if (ValidationUtil.isOneOrTwoDigit(deliveryYear, deliveryMonth, deliveryDay)) {
				request.setAttribute(deliveryError, "年月日は正規の桁数で入力してください。");
			}
			// 通常納期年月日が存在しない日付の場合はエラーにする
			else if (ValidationUtil.validateDate(deliveryYear, deliveryMonth, deliveryDay)) {
				request.setAttribute(deliveryError, "存在しない日付です。");
			}

			// 延納金額のエラー処理
			// 未入力項目があればエラーを返す
			if (ValidationUtil.isNullOrEmpty(deferredPaymentAmount)) {
				request.setAttribute(deferredPaymentAmountError, "入力必須項目です。");
			}
			// 入力金額に数字以外が含まれている、もしくは数字が１０００万円を超える場合はエラーを返す
			else if (!deferredPaymentAmount.matches("\\d+$") || deferredPaymentAmount.length() > 7) {
				request.setAttribute(deferredPaymentAmountError, "金額は数字のみ7桁以下で入力してください。");
			}

			// 日付エラーのチェック
			// エラーが発生していない場合はエラー処理を行う
			if (ValidationUtil.areAllNullOrEmpty((String) request.getAttribute("requestError"),
					(String) request.getAttribute(deliveryError),
					(String) request.getAttribute("generalDeliveryError"))) {
				// 申請日と申請期間の比較
				if (ValidationUtil.isBefore(requestYear, requestMonth, requestDay, deliveryYear, deliveryMonth,
						deliveryDay)) {
					request.setAttribute(deliveryError, "納付期限は願出年月日より後の日付でなければなりません。");
				} else if (ValidationUtil.isBefore(generalDeliveryYear, generalDeliveryMonth, generalDeliveryDay,
						deliveryYear, deliveryMonth, deliveryDay)) {
					request.setAttribute(deliveryError, "納付期限は通常納期年月日より後の日付でなければなりません。");
				}
			}

			// 支払い総額へ減算処理
			// エラーが発生していない場合は減算処理を行う
			if (ValidationUtil.areAllNullOrEmpty(
					(String) request.getAttribute(deferredPaymentAmountError))) {
				// 総支払金額より延納金額を減算する
				totalPayment -= Integer.parseInt(deferredPaymentAmount);
			}

			// 作成する行数カウントの追加
			count++;
		}

		// 支払い総額へ加減算処理
		// エラーが発生していない場合は加減算処理を行う
		if (ValidationUtil.areAllNullOrEmpty((String) request.getAttribute("amountPayableError"),
				(String) request.getAttribute("tuitionFeePaidError"))) {
			// 総支払金額に納付すべき金額を加算して、通常納期内納付学費等を減算する
			totalPayment += Integer.parseInt(amountPayable) - Integer.parseInt(tuitionFeePaid);
			// 文字列の数値をカンマ付きに変更する
			amountPayable = numberFormat.format(Integer.parseInt(amountPayable));
			tuitionFeePaid = numberFormat.format(Integer.parseInt(tuitionFeePaid));
		}

		if (totalPayment != 0) {
			request.setAttribute("totalPaymentError", "納付すべき金額と納付学費及び延納金額の合計が一致しません。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "petition-for-deferred-payment.jsp";
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

			// 学生種類のデータベースからの取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			String studentType = decrypt.getDecryptedDate(result, reEncryptedStudentType);

			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(lastName, firstName, className, schoolYear, classNumber, studentNumber)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// 姓名を結合する			
			String name = lastName + " " + firstName;

			// クラス名の末尾に「科」がついていた場合は削除する
			if (className.endsWith("科")) {
				className = className.substring(0, className.length() - 1);
			}

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/generalStudentPDF/学費延納願.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));
			// PDFへの書き込み
			// 申請年月日
			editor.writeText(font, requestYear, 415f, 733f, 70f, "left", 12);
			editor.writeText(font, requestMonth, 460f, 733f, 70f, "left", 12);
			editor.writeText(font, requestDay, 505f, 733f, 70f, "left", 12);
			// 名前・学籍番号・クラス・学年・組
			editor.writeText(font, name, 143f, 637f, 225f, "center", 12);
			editor.writeText(font, studentNumber, 425f, 637f, 112f, "center", 12);
			editor.writeText(font, className, 143f, 604f, 198f, "center", 12);
			editor.writeText(font, schoolYear, 455f, 604f, 132f, "left", 12);
			editor.writeText(font, classNumber, 500f, 604f, 132f, "left", 12);
			// 納付できない理由
			if (reason.length() < 29) {
				editor.writeText(font, reason, 240f, 520f, 296f, "left", 10);
			} else {
				editor.writeText(font, reason.substring(0, 29), 240f, 520f, 298f, "left", 10);
				editor.writeText(font, reason.substring(29, reason.length()), 166f, 496f, 370f, "left", 10);
			}
			// 学費の捻出方法
			if (howToRaiseFunds.length() < 30) {
				editor.writeText(font, howToRaiseFunds, 230f, 423f, 306f, "left", 10);
			} else {
				editor.writeText(font, howToRaiseFunds.substring(0, 30), 230f, 423f, 306f, "left", 10);
				editor.writeText(font, howToRaiseFunds.substring(30, howToRaiseFunds.length()), 166f, 400f, 370f,
						"left", 10);
			}
			// 納付すべき金額
			editor.writeText(font, amountPayable, 345f, 354f, 170f, "right", 12);
			// 通常納期内納付学費等
			editor.writeText(font, generalDeliveryYear, 213f, 327, 40f, "left", 12);
			editor.writeText(font, generalDeliveryMonth, 250f, 327f, 40f, "left", 12);
			editor.writeText(font, generalDeliveryDay, 295f, 327f, 40f, "left", 12);
			editor.writeText(font, tuitionFeePaid, 345f, 327f, 170f, "right", 12);

			if (studentType.equals("留学生")) {
				// 使用目的に応じて〇の位置を変える
				if (remittanceFromCountry.equals("有")) {
					editor.writeText(font, "〇", 259f, 468f, 40f, "left", 16);
					editor.writeText(font, invoiceYear, 410f, 377f, 70f, "left", 12);
					editor.writeText(font, invoiceMonth, 455f, 377f, 70f, "left", 12);
					editor.writeText(font, invoiceDay, 498f, 377f, 70f, "left", 12);
				} else {
					editor.writeText(font, "〇", 282.5f, 468f, 40f, "left", 16);
					if (reasonNoRemittance.length() < 16) {
						editor.writeText(font, reasonNoRemittance, 380f, 471f, 157f, "left", 10);
					} else {
						editor.writeText(font, reasonNoRemittance.substring(0, 15), 380f, 471f, 157f, "left", 10);
						editor.writeText(font, reasonNoRemittance.substring(15, reasonNoRemittance.length()), 166f,
								448f, 370f, "left", 10);
					}
				}
			}

			float row = 0;
			for (int i = 1; i <= count; i++) {
				// 末尾に添付する番号のString
				String num = String.valueOf(i);

				// 入力された値を変数に格納
				String deliveryYear = request.getParameter("deliveryYear" + num);
				String deliveryMonth = request.getParameter("deliveryMonth" + num);
				String deliveryDay = request.getParameter("deliveryDay" + num);
				String deferredPaymentAmount = request.getParameter("deferredPaymentAmount" + num);
				// 文字列の数値をカンマ付きに変更する
				deferredPaymentAmount = numberFormat.format(Integer.parseInt(deferredPaymentAmount));
				// PDFへの記入
				editor.writeText(font, deliveryYear, 213f, 300f - row, 40f, "left", 12);
				editor.writeText(font, deliveryMonth, 250f, 300f - row, 40f, "left", 12);
				editor.writeText(font, deliveryDay, 295f, 300f - row, 40f, "left", 12);
				editor.writeText(font, deferredPaymentAmount, 345f, 300f - row, 170f, "right", 12);

				row += 26;
			}

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Petition For Deferred-payment");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "学費延納願");
			// Close and save
			editor.close("Petition_For_Deferred-payment.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "petition-for-deferred-payment.jsp";
		}
	}
}
