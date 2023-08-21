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

public class NotificationOfChangeAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(NotificationOfChangeAction.class);

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
		String ChangeSubject = request.getParameter("ChangeSubject");
		String tel = request.getParameter("tel");
		String postCode = request.getParameter("postCode");
		String address = request.getParameter("address");
		String residentCard = request.getParameter("residentCard");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String endDay = request.getParameter("endDay");
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");

		// 入力された値をリクエストに格納	
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			request.setAttribute(paramName, paramValue);
		}

		// 更新項目確認用変数
		boolean changeAddress = false;
		boolean changeTel = false;
		boolean changeResidentCard = false;
		boolean changeName = false;

		// 未入力項目があればエラーを返す
		if (requestYear == null || requestMonth == null || requestDay == null
				|| ChangeSubject == null
				|| requestYear.isEmpty() || requestMonth.isEmpty()
				|| requestDay.isEmpty()
				|| ChangeSubject.isEmpty()

		) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "notification-of-change.jsp";
		}

		// 姓と名のチェック
		if ((lastName == null || lastName.isEmpty()) && (firstName == null || firstName.isEmpty())) {
			// すべてが空の場合は問題なし
		} else if (lastName == null || lastName.isEmpty() || firstName == null || firstName.isEmpty()) {
			// 何か一つだけ入力されている場合
			request.setAttribute("nameError", "姓と名を全て入力してください。");
		} else if (lastName.length() > 32 || firstName.length() > 32) {
			request.setAttribute("nameError", "名前は32文字以下で入力してください。");
		} else {
			changeName = true;
		}

		// 郵便番号と住所のチェック
		if ((postCode == null || postCode.isEmpty()) && (address == null || address.isEmpty())) {
			// どちらも空の場合は問題なし
		} else if (postCode == null || postCode.isEmpty() || address == null || address.isEmpty()) {
			// どちらかだけ入力されている場合
			request.setAttribute("addressError", "郵便番号と住所は両方とも入力してください。");
		} else if (address.length() > 64) {
			// 文字数が64文字より多い場合はエラーを返す
			request.setAttribute("addressError", "住所は64文字以下で入力してください。");
		} else if (!postCode.matches("^\\d{7}$")) {
			// 郵便番号が半角7桁でなければエラーを返す
			request.setAttribute("postCodeError", "郵便番号は半角数字7桁で入力してください。");
		} else {
			changeAddress = true;
		}

		// 電話番号が半角10~11桁でなければエラーを返す
		if (tel != null && !tel.isEmpty() && !tel.matches("^\\d{10,11}$")) {
			request.setAttribute("telError", "電話番号は半角数字10桁～11桁で入力してください。");
		} else if (tel != null && !tel.isEmpty()) {
			changeTel = true;
		}

		// 在留カードの記号番号と期間満了年月日のチェック
		if ((residentCard == null || residentCard.isEmpty()) && (endYear == null || endYear.isEmpty())
				&& (endMonth == null || endMonth.isEmpty()) && (endDay == null || endDay.isEmpty())) {
			// どちらも空の場合は問題なし
		} else if (residentCard == null || residentCard.isEmpty() || endYear == null || endYear.isEmpty()
				|| endMonth == null || endMonth.isEmpty() || endDay == null || endDay.isEmpty()) {
			// どちらかだけ入力されている場合
			request.setAttribute("residentCardError", "記号・番号と期間満了年月日は全て入力してください。");
		} else if (residentCard.length() != 12) {
			// 文字数が12文字でない場合はエラーを返す
			request.setAttribute("residentCardError", "記号・番号は12文字で入力してください。");
		} else if (!residentCard.matches("^[A-Z]{2}\\d{8}[A-Z]{2}$")) {
			// 記号番号の最初と最後の２桁が大文字のアルファベット、間が半角数字8桁でなければエラーを返す
			request.setAttribute("residentCardError", "記号番号の最初と最後の２桁は大文字のアルファベット、間は半角数字8桁で入力してください。");
		} else {
			changeResidentCard = true;
		}

		// 年月日が存在しない日付の場合はエラーにする
		try {
			int checkYear = Integer.parseInt(requestYear);
			int checkMonth = Integer.parseInt(requestMonth);
			int checkDay = Integer.parseInt(requestDay);

			// 届出年月日の日付の妥当性チェック
			LocalDate requestDate = LocalDate.of(checkYear, checkMonth, checkDay);

			if (changeResidentCard) {
				checkYear = Integer.parseInt(endYear);
				checkMonth = Integer.parseInt(endMonth);
				checkDay = Integer.parseInt(endDay);
				// 在留カード期間満了年月日の日付の妥当性チェック
				LocalDate endDate = LocalDate.of(checkYear, checkMonth, checkDay);

				// 届出年月日と在留カード期間満了年月日の比較
				if (endDate.isBefore(requestDate)) {
					request.setAttribute("dayError", "期間満了年月日は届出年月日より後の日付でなければなりません。");
				}
			}
		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
		}

		// 少なくとも1つの項目が入力されている必要がある
		if (changeAddress || changeTel || changeResidentCard || changeName) {
			// 何かしらの変更がある場合は問題なし
		} else {
			request.setAttribute("inputError", "変更する項目を入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("nameError") != null || request.getAttribute("inputError") != null
				|| request.getAttribute("addressError") != null || request.getAttribute("dayError") != null
				|| request.getAttribute("postCodeError") != null || request.getAttribute("telError") != null
				|| request.getAttribute("residentCardError") != null) {
			return "notification-of-change.jsp";
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
			String oldLastName = CipherUtil.decrypt(masterKey, iv, encryptedLastName);
			// 名のデータベースからの取り出し
			String reEncryptedFirstName = dao.getFirstName(id);
			String encryptedFirstName = CipherUtil.commonDecrypt(reEncryptedFirstName);
			String oldFirstName = CipherUtil.decrypt(masterKey, iv, encryptedFirstName);

			String name = oldLastName + " " + oldFirstName;

			// クラス名のデータベースからの取り出し
			String reEncryptedClassName = dao.getClassName(id);
			String encryptedClassName = CipherUtil.commonDecrypt(reEncryptedClassName);
			String className = CipherUtil.decrypt(masterKey, iv, encryptedClassName);
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

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/generalStudentPDF/変更届.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));
			// PDFへの書き込み
			// 申請年月日
			editor.writeText(font, requestYear, 420f, 645f, 70f, "left", 12);
			editor.writeText(font, requestMonth, 480f, 645f, 70f, "left", 12);
			editor.writeText(font, requestDay, 523f, 645f, 70f, "left", 12);
			// 学籍番号・名前・クラス名			
			editor.writeText(font, studentNumber, 187f, 645f, 132f, "center", 12);
			editor.writeText(font, name, 187f, 609f, 363f, "center", 12);
			if (className.equals("ＩＴ・ゲームソフト科")) {
				if (schoolYear.equals("1")) {
					if (classNumber.equals("1")) {
						editor.writeText(font, "✓", 193f, 564f, 112f, "left", 12);
					} else if (classNumber.equals("2")) {
						editor.writeText(font, "✓", 259f, 564f, 125f, "left", 12);
					} else {
						editor.writeText(font, "✓", 319f, 564f, 125f, "left", 12);
					}
				} else {
					if (classNumber.equals("1")) {
						editor.writeText(font, "✓", 193f, 539f, 112f, "left", 12);
					} else {
						editor.writeText(font, "✓", 259f, 539f, 112f, "left", 12);
					}
				}
			} else if (className.equals("ＡＩ・データサイエンス科") && schoolYear.equals("1") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 443f, 564f, 125f, "left", 12);
			} else if (className.equals("グローバルＩＴシステム科") && schoolYear.equals("1") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 379f, 564f, 125f, "left", 12);
			} else if (className.equals("ＩＴライセンス科（通信制）")) {
				editor.writeText(font, "✓", 502f, 564f, 125f, "left", 12);
			} else if (className.equals("ロボット・ＩＯＴソフト科") && schoolYear.equals("2") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 318f, 539f, 125f, "left", 12);
			} else if (className.equals("グローバルＩＴビジネス科") && schoolYear.equals("1") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 378f, 539f, 125f, "left", 12);
			} else if (className.equals("グローバルＩＴビジネス科") && schoolYear.equals("2") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 443f, 539f, 125f, "left", 12);
			}

			// 申請者の種類
			if (ChangeSubject.equals("本人")) {
				editor.writeText(font, "✓", 157f, 500f, 50f, "left", 12);
			} else if (ChangeSubject.equals("保護者")) {
				editor.writeText(font, "✓", 228f, 500f, 50f, "left", 12);
			} else {
				editor.writeText(font, "✓", 299f, 500f, 50f, "left", 12);
			}

			// 変更後の住所
			if (changeAddress) {
				editor.writeText(font, "✓", 63f, 446f, 50f, "left", 12);
				float num = 0;
				for (int i = 0; i < 3; i++) {
					// 郵便番号を２つに分割する
					String FirstPostCode = postCode.substring(i, i + 1);
					String LastPostCode = postCode.substring(3 + i, i + 4);
					editor.writeText(font, FirstPostCode, 171f + num, 473f, 180f, "left", 14);
					editor.writeText(font, LastPostCode, 226f + num, 473f, 180f, "left", 14);
					num += 14;
				}
				editor.writeText(font, postCode.substring(6, 7), 268f, 473f, 180f, "left", 14);

				// 住所の文字数によって表示する位置を変える
				if (address.length() < 34) {
					editor.writeText(font, address, 152f, 440f, 398f, "left", 12);
				} else {
					editor.writeText(font, address.substring(0, 33), 152f, 450f, 398f, "left", 12);
					editor.writeText(font, address.substring(33, address.length()), 152f, 430f, 398f, "left", 12);
				}
			}

			// 変更後の電話番号
			if (changeTel) {
				editor.writeText(font, "✓", 63f, 383f, 50f, "left", 12);
				// 電話番号を3分割する
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
				editor.writeText(font, firstTel, 185f, 383f, 40f, "left", 16);
				editor.writeText(font, secondTel, 275f, 383f, 40f, "left", 16);
				editor.writeText(font, lastTel, 360f, 383f, 40f, "left", 16);
			}

			// 変更後の在留カード
			if (changeResidentCard) {
				editor.writeText(font, "✓", 63f, 322f, 50f, "left", 12);

				float num = 0;
				for (int i = 0; i < 12; i++) {
					String str = residentCard.substring(i, i + 1);
					editor.writeText(font, str, 235f + num, 326f, 180f, "left", 16);
					num += 22.8;
				}
				editor.writeText(font, endYear, 265f, 303f, 180f, "left", 12);
				editor.writeText(font, endMonth, 340f, 303f, 180f, "left", 12);
				editor.writeText(font, endDay, 395f, 303f, 180f, "left", 12);
			}

			// 変更後の名前
			if (changeName) {
				editor.writeText(font, "✓", 63f, 265f, 50f, "left", 12);
				String newName = lastName + " " + firstName;
				editor.writeText(font, newName, 152f, 265f, 330f, "center", 12);
			}

			// Close and save
			editor.close("変更届.pdf");
			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Notification Of Change");
			// PDF作成成功画面に遷移
			request.setAttribute("createPDF", "「変更届」を作成しました。");
			return "create-pdf-success.jsp";
		} catch (

		Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "notification-of-change.jsp";
		}
	}
}
