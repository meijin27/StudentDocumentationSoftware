package mainMenu.generalStudent;

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

public class RecommendedDeliveryAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(RecommendedDeliveryAction.class);

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
		String propose = request.getParameter("propose");
		String subject = request.getParameter("subject");
		String reason = request.getParameter("reason");
		String deadlineYear = request.getParameter("deadlineYear");
		String deadlineMonth = request.getParameter("deadlineMonth");
		String deadlineDay = request.getParameter("deadlineDay");
		String nominationForm = request.getParameter("nominationForm");

		// 入力された値をリクエストに格納	
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			request.setAttribute(paramName, paramValue);
		}

		// 未入力項目があればエラーを返す
		if (requestYear == null || requestMonth == null || requestDay == null
				|| propose == null || subject == null
				|| deadlineYear == null || deadlineMonth == null || deadlineDay == null || nominationForm == null
				|| requestYear.isEmpty() || requestMonth.isEmpty()
				|| requestDay.isEmpty()
				|| propose.isEmpty() || subject.isEmpty() || deadlineYear.isEmpty() || deadlineMonth.isEmpty()
				|| deadlineDay.isEmpty() || nominationForm.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "recommended-delivery.jsp";
		}

		// 年月日が存在しない日付の場合はエラーにする
		try {
			int checkYear = Integer.parseInt(requestYear) + 2018;
			int checkMonth = Integer.parseInt(requestMonth);
			int checkDay = Integer.parseInt(requestDay);

			// 申請日の日付の妥当性チェック
			LocalDate requestDate = LocalDate.of(checkYear, checkMonth, checkDay);

			checkYear = Integer.parseInt(deadlineYear) + 2018;
			checkMonth = Integer.parseInt(deadlineMonth);
			checkDay = Integer.parseInt(deadlineDay);
			// 提出期限日の日付の妥当性チェック
			LocalDate deadlineDate = LocalDate.of(checkYear, checkMonth, checkDay);

			// 申請日と提出期限日の比較
			if (deadlineDate.isBefore(requestDate)) {
				request.setAttribute("dayError", "提出期限は申請日より後の日付でなければなりません。");
			}
		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
		}

		// 事由が「その他」の場合で理由が未記載の場合はエラーを返す
		if (subject.equals("その他") && (reason == null || reason.isEmpty())) {
			request.setAttribute("nullError", "事由が「その他」の場合は理由を入力してください。");
			// 文字数が18文字より多い場合はエラーを返す
		} else if (reason.length() > 18) {
			request.setAttribute("valueLongError", "理由は18文字以下で入力してください。");
		}

		// 文字数が32文字より多い場合はエラーを返す
		if (propose.length() > 32) {
			request.setAttribute("valueLongError", "提出先は32文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("nullError") != null || request.getAttribute("dayError") != null
				|| request.getAttribute("valueLongError") != null) {
			return "recommended-delivery.jsp";
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
			// 郵便番号をに分割する
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
			// Close and save
			editor.close("Recommended_Delivery.pdf", response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "recommended-delivery.jsp";
		}
	}
}
