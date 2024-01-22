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
		String requestYear = request.getParameter("requestYear");
		String requestMonth = request.getParameter("requestMonth");
		String requestDay = request.getParameter("requestDay");
		String changeSubject = request.getParameter("changeSubject");
		String tel = request.getParameter("tel");
		String postCode = request.getParameter("postCode");
		String address = request.getParameter("address");
		String residentCard = request.getParameter("residentCard");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String endDay = request.getParameter("endDay");
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");

		// 更新項目確認用変数
		boolean changeAddress = false;
		boolean changeTel = false;
		boolean changeResidentCard = false;
		boolean changeName = false;

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

		// 変更対象者のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(changeSubject)) {
			request.setAttribute("changeSubjectError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(changeSubject)) {
			request.setAttribute("changeSubjectError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(3, changeSubject)) {
			request.setAttribute("changeSubjectError", "変更対象者は３文字以下で入力してください。");
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
				request.setAttribute("lastNameError", "32文字以下で入力してください。");
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
				request.setAttribute("firstNameError", "32文字以下で入力してください。");
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

		// 在留カードの記号番号と期間満了年月日のチェック
		if (ValidationUtil.areAllNullOrEmpty(residentCard, endYear, endMonth, endDay)) {
			// どちらも空の場合は問題なし
		}
		// 入力されている場合
		else {
			// 在留カードのエラー処理
			// 未入力項目があればエラーを返す
			if (ValidationUtil.isNullOrEmpty(residentCard)) {
				request.setAttribute("residentCardError", "記号・番号と期間満了年月日は全て入力してください。");
			} else if (ValidationUtil.isResidentCard(residentCard)) {
				// 記号番号の最初と最後の２桁が大文字のアルファベット、間が半角数字8桁でなければエラーを返す
				request.setAttribute("residentCardError", "記号番号の最初と最後の２桁は大文字のアルファベット、間は半角数字８桁で入力してください。");
			}
			// 期間満了年月日のエラー処理
			// 未入力項目があればエラーを返す
			if (ValidationUtil.isNullOrEmpty(endYear, endMonth, endDay)) {
				request.setAttribute("endError", "記号・番号と期間満了年月日は全て入力してください。");
			}
			// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
			else if (ValidationUtil.isFourDigit(endYear) || ValidationUtil.isOneOrTwoDigit(endMonth, endDay)) {
				request.setAttribute("endError", "年月日は正規の桁数で入力してください。");
			}
			// 期間満了年月日年月日が存在しない日付の場合はエラーにする
			else if (ValidationUtil.validateDate(endYear, endMonth, endDay)) {
				request.setAttribute("endError", "存在しない日付です。");
			}
			changeResidentCard = true;
		}

		// 少なくとも1つの項目が入力されている必要がある
		if (changeAddress || changeTel || changeResidentCard || changeName) {
			// 何かしらの変更がある場合は問題なし
		} else {
			request.setAttribute("changeError", "変更する項目を入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "notification-of-change.jsp";
		}

		// 年月日入力にnullがないことを確認した後に日付の順序のエラーをチェックする
		// 在留カードの期限が入力されている場合は期間の順序をチェックする
		if (ValidationUtil.isBefore(requestYear, requestMonth, requestDay, endYear, endMonth, endDay)) {
			request.setAttribute("endError", "期間満了年月日は届出年月日より後の日付でなければなりません。");
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
			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(oldLastName, oldFirstName, className, schoolYear, classNumber,
					studentNumber)) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}

			// 姓名を結合する
			String name = oldLastName + " " + oldFirstName;

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
			} else if (className.equals("ロボット・ＩｏＴソフト科") && schoolYear.equals("2") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 318f, 539f, 125f, "left", 12);
			} else if (className.equals("グローバルＩＴビジネス科") && schoolYear.equals("1") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 378f, 539f, 125f, "left", 12);
			} else if (className.equals("グローバルＩＴビジネス科") && schoolYear.equals("2") && classNumber.equals("1")) {
				editor.writeText(font, "✓", 443f, 539f, 125f, "left", 12);
			}

			// 申請者の種類
			if (changeSubject.equals("本人")) {
				editor.writeText(font, "✓", 157f, 500f, 50f, "left", 12);
			} else if (changeSubject.equals("保護者")) {
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
					editor.writeText(font, str, 235f + num, 327f, 180f, "left", 16);
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

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Notification Of Change");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "変更届");
			// Close and save
			editor.close("Notification_Of_Change.pdf", request, response);

			return null;
		} catch (

		Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "notification-of-change.jsp";
		}
	}
}
