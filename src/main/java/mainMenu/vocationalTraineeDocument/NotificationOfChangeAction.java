package mainMenu.vocationalTraineeDocument;

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
		String changeYear = request.getParameter("changeYear");
		String changeMonth = request.getParameter("changeMonth");
		String changetDay = request.getParameter("changeDay");
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String postCode = request.getParameter("postCode");
		String address = request.getParameter("address");
		String tel = request.getParameter("tel");
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

		// 更新項目確認用変数
		boolean changeName = false;
		boolean changeAddress = false;
		boolean changeTel = false;

		// 未入力項目があればエラーを返す
		if (changeYear == null || changeMonth == null || changetDay == null || requestYear == null
				|| requestMonth == null || requestDay == null || changeYear.isEmpty() || changeMonth.isEmpty()
				|| changetDay.isEmpty() || requestYear.isEmpty() || requestMonth.isEmpty()
				|| requestDay.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "notification-of-change.jsp";
		}

		// 年月日が存在しない日付の場合はエラーにする
		try {
			int checkYear = Integer.parseInt(changeYear);
			int checkMonth = Integer.parseInt(changeMonth);
			int checkDay = Integer.parseInt(changetDay);

			// 日付の妥当性チェック
			LocalDate Date = LocalDate.of(checkYear, checkMonth, checkDay);

			checkYear = Integer.parseInt(requestYear);
			checkMonth = Integer.parseInt(requestMonth);
			checkDay = Integer.parseInt(requestDay);

			Date = LocalDate.of(checkYear, checkMonth, checkDay);

		} catch (DateTimeException e) {
			request.setAttribute("dayError", "存在しない日付です。");
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

		// 少なくとも1つの項目が入力されている必要がある
		if ((lastName == null || lastName.isEmpty()) &&
				(firstName == null || firstName.isEmpty()) &&
				(postCode == null || postCode.isEmpty()) &&
				(address == null || address.isEmpty()) &&
				(tel == null || tel.isEmpty())) {
			request.setAttribute("inputError", "変更する項目を入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("nameError") != null || request.getAttribute("inputError") != null
				|| request.getAttribute("addressError") != null || request.getAttribute("dayError") != null
				|| request.getAttribute("postCodeError") != null || request.getAttribute("telError") != null) {
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
			if (changeName) {
				name = lastName + " " + firstName;
			}

			// 電話番号のデータベースからの取り出し
			String reEncryptedTel = dao.getTel(id);
			String encryptedTel = CipherUtil.commonDecrypt(reEncryptedTel);
			String oldTel = CipherUtil.decrypt(masterKey, iv, encryptedTel);

			// 電話番号を3分割してハイフンをつけてくっつける
			String firstTel = null;
			String secondTel = null;
			String lastTel = null;
			if (oldTel.length() == 11) {
				firstTel = oldTel.substring(0, 3);
				secondTel = oldTel.substring(3, 7);
				lastTel = oldTel.substring(7, 11);
			} else {
				firstTel = oldTel.substring(0, 3);
				secondTel = oldTel.substring(3, 6);
				lastTel = oldTel.substring(6, 10);
			}
			oldTel = firstTel + "-" + secondTel + "-" + lastTel;

			if (changeTel) {
				if (tel.length() == 11) {
					firstTel = tel.substring(0, 3);
					secondTel = tel.substring(3, 7);
					lastTel = tel.substring(7, 11);
				} else {
					firstTel = tel.substring(0, 3);
					secondTel = tel.substring(3, 6);
					lastTel = tel.substring(6, 10);
				}
				tel = firstTel + "-" + secondTel + "-" + lastTel;
			}

			// 郵便番号のデータベースからの取り出し
			String reEncryptedPostCode = dao.getPostCode(id);
			String encryptedPostCode = CipherUtil.commonDecrypt(reEncryptedPostCode);
			String oldPostCode = CipherUtil.decrypt(masterKey, iv, encryptedPostCode);
			// 郵便番号をに分割する
			String oldFirstPostCode = oldPostCode.substring(0, 3);
			String oldLastPostCode = oldPostCode.substring(3, 7);
			oldPostCode = oldFirstPostCode + "-" + oldLastPostCode;
			if (changeAddress) {
				String firstPostCode = postCode.substring(0, 3);
				String lastPostCode = postCode.substring(3, 7);
				postCode = firstPostCode + "-" + lastPostCode;
			}
			// 住所のデータベースからの取り出し
			String reEncryptedAddress = dao.getAddress(id);
			String encryptedAddress = CipherUtil.commonDecrypt(reEncryptedAddress);
			String oldAddress = CipherUtil.decrypt(masterKey, iv, encryptedAddress);

			// クラス名のデータベースからの取り出し
			String reEncryptedClassName = dao.getClassName(id);
			String encryptedClassName = CipherUtil.commonDecrypt(reEncryptedClassName);
			String className = CipherUtil.decrypt(masterKey, iv, encryptedClassName);

			// 学生種類のデータベースからの取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			String encryptedStudentType = CipherUtil.commonDecrypt(reEncryptedStudentType);
			String studentType = CipherUtil.decrypt(masterKey, iv, encryptedStudentType);
			// もし学生種類が職業訓練生でなければエラーを返す
			if (!studentType.equals("職業訓練生")) {
				request.setAttribute("innerError", "当該書類は職業訓練生のみが発行可能です。");
				return "notification-of-change.jsp";
			}

			// 公共職業安定所名のデータベースからの取り出し
			String reEncryptedNamePESO = dao.getNamePESO(id);
			// 最初にデータベースから取り出した職業訓練生のデータがnullの場合、初期設定をしていないためログインページにリダイレクト
			if (reEncryptedNamePESO == null) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/vocationalTraineePDF/氏名・住所等変更届.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));

			// PDFへの記載
			// 変更年月日
			editor.writeText(font, changeYear, 305f, 667f, 70f, "left", 12);
			editor.writeText(font, changeMonth, 348f, 667f, 70f, "left", 12);
			editor.writeText(font, changetDay, 390f, 667f, 70f, "left", 12);
			// 変更前後の名前
			if (changeName) {
				editor.writeText(font, oldLastName, 185f, 540f, 77f, "center", 12);
				editor.writeText(font, oldFirstName, 268f, 540f, 77f, "center", 12);
				editor.writeText(font, lastName, 350f, 540f, 77f, "center", 12);
				editor.writeText(font, firstName, 435f, 540f, 77f, "center", 12);
			}
			// 変更前後の住所	
			if (changeAddress) {
				editor.writeText(font, oldPostCode, 200f, 512f, 115f, "left", 12);
				editor.writeText(font, postCode, 366f, 512f, 117f, "left", 12);
				// 住所の長さによって記載個所を変更
				if (oldAddress.length() > 13) {
					editor.writeText(font, oldAddress.substring(0, 13), 185f, 495f, 163f, "left", 12);
					editor.writeText(font, oldAddress.substring(13, oldAddress.length()), 185f, 480f, 163f, "left", 12);
				} else {
					editor.writeText(font, oldAddress, 185f, 488f, 163f, "left", 12);
				}
				// 住所の長さによって記載個所を変更
				if (address.length() > 13) {
					editor.writeText(font, address.substring(0, 13), 350f, 495f, 163f, "left", 12);
					editor.writeText(font, address.substring(13, address.length()), 350f, 480f, 163f, "left", 12);
				} else {
					editor.writeText(font, address, 350f, 488f, 163f, "left", 12);
				}
			}
			// 変更前後の電話番号
			if (changeTel) {
				editor.writeText(font, oldTel, 185f, 435f, 163f, "center", 12);
				editor.writeText(font, tel, 350f, 435f, 160f, "center", 12);
			}
			// 申請年月日・クラス名・名前
			editor.writeText(font, requestYear, 125f, 331f, 40f, "left", 12);
			editor.writeText(font, requestMonth, 168f, 331f, 40f, "left", 12);
			editor.writeText(font, requestDay, 210f, 331f, 40f, "left", 12);
			editor.writeText(font, className, 310f, 264f, 200f, "center", 12);
			editor.writeText(font, name, 310f, 230f, 200f, "center", 12);

			// Close and save
			editor.close("氏名・住所等変更届.pdf");
			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Vocational Trainee Notification Of Change");
			// PDF作成成功画面に遷移
			request.setAttribute("createPDF", "「氏名・住所等変更届」を作成しました。");
			return "create-pdf-success.jsp";
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "notification-of-change.jsp";
		}
	}
}
