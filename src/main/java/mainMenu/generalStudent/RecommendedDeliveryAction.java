package mainMenu.generalStudent;

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

public class RecommendedDeliveryAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(RecommendedDeliveryAction.class);

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
		String propose = request.getParameter("propose");
		String subject = request.getParameter("subject");
		String reason = request.getParameter("reason");
		String deadlineYear = request.getParameter("deadlineYear");
		String deadlineMonth = request.getParameter("deadlineMonth");
		String deadlineDay = request.getParameter("deadlineDay");
		String nominationForm = request.getParameter("nominationForm");

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 申請年月日のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "入力必須項目です。");
		}
		// 年月日が２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isOneOrTwoDigit(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "年月日は正規の桁数で入力してください。");
		}
		// 申請年月日が存在しない日付の場合はエラーにする
		else if (ValidationUtil.validateDate(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "存在しない日付です。");
		}

		// 提出期限のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(deadlineYear, deadlineMonth, deadlineDay)) {
			request.setAttribute("deadlineError", "入力必須項目です。");
		}
		// 年月日が２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isOneOrTwoDigit(deadlineYear, deadlineMonth, deadlineDay)) {
			request.setAttribute("deadlineError", "年月日は正規の桁数で入力してください。");
		}
		// 提出期限が存在しない日付の場合はエラーにする
		else if (ValidationUtil.validateDate(deadlineYear, deadlineMonth, deadlineDay)) {
			request.setAttribute("deadlineError", "存在しない日付です。");
		}

		// 事由のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(subject)) {
			request.setAttribute("subjectError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(subject)) {
			request.setAttribute("subjectError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(12, subject)) {
			request.setAttribute("subjectError", "事由は12文字以下で入力してください。");
		}
		// 事由が「その他」の場合で理由が未記載の場合はエラーを返す
		else if (subject.equals("その他")) {
			if (ValidationUtil.isNullOrEmpty(reason)) {
				request.setAttribute("reasonError", "事由が「その他」の場合は理由を入力してください。");
			} else if (ValidationUtil.areValidLengths(18, reason)) {
				// 文字数が18文字より多い場合はエラーを返す。セレクトボックスの入力値の確認も行う。
				request.setAttribute("reasonError", "理由は18文字以下で入力してください。");
			} else if (ValidationUtil.containsForbiddenChars(reason)) {
				// 入力値に特殊文字が入っていないか確認する
				request.setAttribute("reasonError", "使用できない特殊文字が含まれています");
			}
		}

		// 提出先のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(propose)) {
			request.setAttribute("proposeError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(propose)) {
			request.setAttribute("proposeError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(32, propose)) {
			request.setAttribute("proposeError", "提出先は32文字以下で入力してください。");
		}

		// 提出先のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(nominationForm)) {
			request.setAttribute("nominationFormError", "入力必須項目です。");
		}
		// 推薦様式が「本校書式」「いいえ」以外の場合はエラーを返す
		else if (!(nominationForm.equals("本校書式") || nominationForm.equals("提出先書式"))) {
			request.setAttribute("nominationFormError", "推薦様式は「本校書式」「提出先書式」から選択してください");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "recommended-delivery.jsp";
		}

		// 年月日入力にnullがないことを確認した後に日付の順序のエラーをチェックする
		// 申請日と提出期限の比較
		if (ValidationUtil.isBefore(requestYear, requestMonth, requestDay, deadlineYear, deadlineMonth,
				deadlineDay)) {
			request.setAttribute("deadlineError", "提出期限は申請年月日より後の日付でなければなりません。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "recommended-delivery.jsp";
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
			// 学籍番号のデータベースからの取り出し
			String reEncryptedStudentNumber = dao.getStudentNumber(id);
			String studentNumber = decrypt.getDecryptedDate(result, reEncryptedStudentNumber);
			// 誕生年のデータベースからの取り出し
			String reEncryptedBirthYear = dao.getBirthYear(id);
			String birthYear = decrypt.getDecryptedDate(result, reEncryptedBirthYear);
			// 誕生月のデータベースからの取り出し
			String reEncryptedBirthMonth = dao.getBirthMonth(id);
			String birthMonth = decrypt.getDecryptedDate(result, reEncryptedBirthMonth);
			// 誕生日のデータベースからの取り出し
			String reEncryptedBirthDay = dao.getBirthDay(id);
			String birthDay = decrypt.getDecryptedDate(result, reEncryptedBirthDay);
			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(lastName, firstName, tel, postCode, address, className,
					studentNumber, birthYear, birthMonth, birthDay)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
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
			// PDFとフォントのパス作成
			String pdfPath = "/pdf/generalStudentPDF/推薦書交付願.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));
			// PDFへの書き込み
			// 申請年月日
			editor.writeText(font, requestYear, 415f, 735f, 70f, "left", 12);
			editor.writeText(font, requestMonth, 455f, 735f, 70f, "left", 12);
			editor.writeText(font, requestDay, 495f, 735f, 70f, "left", 12);
			// クラス名・学籍番号・名前・生年月日
			editor.writeText(font, className, 270f, 673f, 127f, "center", 12);
			editor.writeText(font, studentNumber, 452f, 673f, 85f, "center", 12);
			editor.writeText(font, name, 270f, 637f, 265f, "center", 12);
			editor.writeText(font, birthYear, 323f, 606f, 230f, "left", 12);
			editor.writeText(font, birthMonth, 378f, 606f, 230f, "left", 12);
			editor.writeText(font, birthDay, 423f, 606f, 230f, "left", 12);
			// 郵便番号
			editor.writeText(font, FirstPostCode, 293f, 589f, 180f, "left", 10);
			editor.writeText(font, LastPostCode, 335f, 589f, 180f, "left", 10);
			// 電話番号
			editor.writeText(font, firstTel, 330f, 534f, 40f, "left", 10);
			editor.writeText(font, secondTel, 385f, 534f, 40f, "left", 10);
			editor.writeText(font, lastTel, 440f, 534f, 40f, "left", 10);

			// 住所の文字数によって表示する位置を変える
			if (address.length() < 23) {
				editor.writeText(font, address, 270f, 560f, 267f, "left", 12);
			} else {
				editor.writeText(font, address.substring(0, 22), 270f, 570f, 267f, "left", 12);
				editor.writeText(font, address.substring(22, address.length()), 270f, 550f, 267f, "left", 12);
			}
			// 提出期限
			editor.writeText(font, deadlineYear, 155f, 426f, 70f, "left", 12);
			editor.writeText(font, deadlineMonth, 195f, 426f, 70f, "left", 12);
			editor.writeText(font, deadlineDay, 240f, 426f, 70f, "left", 12);
			// 使用目的
			if (subject.equals("就職試験")) {
				editor.writeText(font, "✓", 119f, 478f, 50f, "left", 12);
			} else if (subject.equals("大学（編）入学試験")) {
				editor.writeText(font, "✓", 204f, 478f, 50f, "left", 12);
			} else {
				editor.writeText(font, "✓", 328f, 478f, 50f, "left", 12);
				editor.writeText(font, reason, 382f, 479f, 125f, "center", 12);
			}
			// 提出先
			editor.writeText(font, propose, 115f, 452f, 420f, "left", 12);
			// 書式
			if (nominationForm.equals("本校書式")) {
				editor.writeText(font, "✓", 142f, 398f, 50f, "left", 12);
			} else {
				editor.writeText(font, "✓", 227f, 398f, 50f, "left", 12);
			}

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Recommended Delivery");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "推薦書交付願");
			// Close and save
			editor.close("Recommended_Delivery.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "recommended-delivery.jsp";
		}
	}
}
