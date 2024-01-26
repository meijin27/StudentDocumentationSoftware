package mainMenu.vocationalTraineeDocument;

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

public class NotificationOfChangeAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(NotificationOfChangeAction.class);

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
		String changeYear = request.getParameter("changeYear");
		String changeMonth = request.getParameter("changeMonth");
		String changeDay = request.getParameter("changeDay");
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String postCode = request.getParameter("postCode");
		String address = request.getParameter("address");
		String tel = request.getParameter("tel");
		String requestYear = request.getParameter("requestYear");
		String requestMonth = request.getParameter("requestMonth");
		String requestDay = request.getParameter("requestDay");

		// 更新項目確認用変数
		boolean changeName = false;
		boolean changeAddress = false;
		boolean changeTel = false;

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 変更年月日のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(changeYear, changeMonth, changeDay)) {
			request.setAttribute("changeDateError", "入力必須項目です。");
		}
		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isOneOrTwoDigit(changeYear, changeMonth, changeDay)) {
			request.setAttribute("changeDateError", "年月日は正規の桁数で入力してください。");
		}
		// 変更年月日が存在しない日付の場合はエラーにする
		else if (ValidationUtil.validateDate(changeYear, changeMonth, changeDay)) {
			request.setAttribute("changeDateError", "存在しない日付です。");
		}

		// 姓と名のチェック
		if (ValidationUtil.areAllNullOrEmpty(lastName, firstName)) {
			// すべてが空の場合は問題なし
		}
		// 入力されている場合
		else {
			// 姓のエラー処理
			if (ValidationUtil.isNullOrEmpty(lastName)) {
				request.setAttribute("lastNameError", "変更する場合は姓と名は両方入力してください。");
			}
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(lastName)) {
				request.setAttribute("lastNameError", "使用できない特殊文字が含まれています");
			}
			// 文字数が多い場合はエラーを返す。
			else if (ValidationUtil.areValidLengths(32, lastName)) {
				request.setAttribute("lastNameError", "姓は32文字以下で入力してください。");
			}
			// 名のエラー処理
			if (ValidationUtil.isNullOrEmpty(firstName)) {
				request.setAttribute("firstNameError", "変更する場合は姓と名は両方入力してください。");
			}
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(firstName)) {
				request.setAttribute("firstNameError", "使用できない特殊文字が含まれています");
			}
			// 文字数が多い場合はエラーを返す。
			else if (ValidationUtil.areValidLengths(32, firstName)) {
				request.setAttribute("firstNameError", "名は32文字以下で入力してください。");
			}
			changeName = true;
		}

		// 郵便番号と住所のチェック
		if (ValidationUtil.areAllNullOrEmpty(postCode, address)) {
			// どちらも空の場合は問題なし
		}
		// 入力されている場合
		else {
			// 郵便番号のエラー処理
			// 未入力項目があればエラーを返す
			if (ValidationUtil.isNullOrEmpty(postCode)) {
				request.setAttribute("postCodeError", "変更する場合は郵便番号と住所は両方とも入力してください。");
			}
			// 郵便番号が半角7桁でなければエラーを返す
			else if (ValidationUtil.isSevenDigit(postCode)) {
				request.setAttribute("postCodeError", "郵便番号は半角数字7桁で入力してください。");
			}
			// 住所のエラー処理
			// 未入力項目があればエラーを返す
			if (ValidationUtil.isNullOrEmpty(address)) {
				request.setAttribute("addressError", "変更する場合は郵便番号と住所は両方とも入力してください。");
			}
			// 入力値に特殊文字が入っていないか確認する
			else if (ValidationUtil.containsForbiddenChars(address)) {
				request.setAttribute("addressError", "使用できない特殊文字が含まれています");
			}
			// 文字数が多い場合はエラーを返す。
			else if (ValidationUtil.areValidLengths(64, address)) {
				request.setAttribute("addressError", "住所は64文字以下で入力してください。");
			}
			changeAddress = true;
		}

		// 電話番号のチェック
		if (ValidationUtil.areAllNullOrEmpty(tel)) {
			// 空の場合は問題なし
		} else if (ValidationUtil.isTenOrElevenDigit(tel)) {
			// 電話番号が半角10~11桁でなければエラーを返す
			request.setAttribute("telError", "電話番号は半角数字10桁～11桁で入力してください。");
		} else {
			changeTel = true;
		}

		// 申請年月日のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "入力必須項目です。");
		}
		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		else if (ValidationUtil.isOneOrTwoDigit(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "年月日は正規の桁数で入力してください。");
		}
		// 申請年月日が存在しない日付の場合はエラーにする
		else if (ValidationUtil.validateDate(requestYear, requestMonth, requestDay)) {
			request.setAttribute("requestError", "存在しない日付です。");
		}

		// 年月日入力にエラーがないことを確認した後に日付の順序のエラーをチェックする
		if (ValidationUtil.areAllNullOrEmpty((String) request.getAttribute("changeDateError"),
				(String) request.getAttribute("requestError"))) {
			// 在留カードの期限が入力されている場合は期間の順序をチェックする
			if (ValidationUtil.isBefore(changeYear, changeMonth, changeDay, requestYear, requestMonth, requestDay)) {
				request.setAttribute("requestError", "申請年月日は変更年月日より後の日付でなければなりません。");
			}
		}

		// 少なくとも1つの項目が入力されている必要がある
		if (changeName || changeAddress || changeTel) {
			// 入力されている場合は問題なし			
		} else {
			request.setAttribute("changeError", "変更する項目を入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "notification-of-change.jsp";
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
			String oldLastName = decrypt.getDecryptedDate(result, reEncryptedLastName);
			// 名のデータベースからの取り出し
			String reEncryptedFirstName = dao.getFirstName(id);
			String oldFirstName = decrypt.getDecryptedDate(result, reEncryptedFirstName);

			// 電話番号のデータベースからの取り出し
			String reEncryptedTel = dao.getTel(id);
			String oldTel = decrypt.getDecryptedDate(result, reEncryptedTel);

			// 郵便番号のデータベースからの取り出し
			String reEncryptedPostCode = dao.getPostCode(id);
			String oldPostCode = decrypt.getDecryptedDate(result, reEncryptedPostCode);

			// 住所のデータベースからの取り出し
			String reEncryptedAddress = dao.getAddress(id);
			String oldAddress = decrypt.getDecryptedDate(result, reEncryptedAddress);

			// クラス名のデータベースからの取り出し
			String reEncryptedClassName = dao.getClassName(id);
			String className = decrypt.getDecryptedDate(result, reEncryptedClassName);

			// 学生種類のデータベースからの取り出し
			String reEncryptedStudentType = dao.getStudentType(id);
			String studentType = decrypt.getDecryptedDate(result, reEncryptedStudentType);

			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(oldLastName, oldFirstName, oldTel, oldPostCode, oldAddress, studentType,
					className)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// もし学生種類が職業訓練生でなければエラーを返す
			if (!studentType.equals("職業訓練生")) {
				request.setAttribute("innerError", "当該書類は職業訓練生のみが発行可能です。");
				return "notification-of-change.jsp";
			}

			// 郵便番号を分割する
			String oldFirstPostCode = oldPostCode.substring(0, 3);
			String oldLastPostCode = oldPostCode.substring(3, 7);
			oldPostCode = oldFirstPostCode + "-" + oldLastPostCode;
			if (changeAddress) {
				String firstPostCode = postCode.substring(0, 3);
				String lastPostCode = postCode.substring(3, 7);
				postCode = firstPostCode + "-" + lastPostCode;
			}

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

			String name = oldLastName + " " + oldFirstName;
			if (changeName) {
				name = lastName + " " + firstName;
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
			editor.writeText(font, changeDay, 390f, 667f, 70f, "left", 12);
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

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Vocational Trainee Notification Of Change");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "氏名・住所等変更届");
			// Close and save
			editor.close("Vocational_Trainee_Notification_Of_Change.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "notification-of-change.jsp";
		}
	}
}
