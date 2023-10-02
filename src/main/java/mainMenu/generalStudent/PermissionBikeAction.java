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

public class PermissionBikeAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(PermissionBikeAction.class);

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
		String patron = request.getParameter("patron");
		String patronTel = request.getParameter("patronTel");
		String classification = request.getParameter("classification");
		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String startDay = request.getParameter("startDay");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String endDay = request.getParameter("endDay");
		String registrationNumber = request.getParameter("registrationNumber");
		String modelAndColor = request.getParameter("modelAndColor");

		// 必須項目に未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(requestYear, requestMonth, requestDay, patron, patronTel, startYear,
				startMonth, startDay, classification, endYear, endMonth, endDay, registrationNumber, modelAndColor)) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "permission-bike.jsp";
		}

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 年月日が１・２桁になっていることを検証し、違う場合はエラーを返す
		if (ValidationUtil.isOneOrTwoDigit(requestYear, requestMonth, requestDay, startYear, startMonth, startDay,
				endYear, endMonth, endDay)) {
			request.setAttribute("dayError", "年月日は正規の桁数で入力してください。");
		} else {
			if (ValidationUtil.validateDate(requestYear, requestMonth, requestDay)
					|| ValidationUtil.validateDate(startYear, startMonth, startDay)
					|| ValidationUtil.validateDate(endYear, endMonth, endDay)) {
				request.setAttribute("dayError", "存在しない日付です。");
				// 申請日と申請期間の比較
			} else if (ValidationUtil.isBefore(requestYear, requestMonth, requestDay, startYear, startMonth,
					startDay)) {
				request.setAttribute("dayError", "期間年月日（自）は願出年月日より後の日付でなければなりません。");
			} else if (ValidationUtil.isBefore(startYear, startMonth, startDay, endYear, endMonth, endDay)) {
				request.setAttribute("dayError", "期間年月日（自）は期間年月日（至）より前の日付でなければなりません。");
			}
		}

		// 電話番号が半角10~11桁でなければエラーを返す
		if (ValidationUtil.isTenOrElevenDigit(patronTel)) {
			request.setAttribute("telError", "電話番号は半角数字10桁～11桁で入力してください。");
		}

		// 種別が「自転車」「原動機付自転車」以外の場合はエラーを返す
		if (!(classification.equals("自転車") || classification.equals("原動機付自転車"))) {
			request.setAttribute("classificationError", "種別は「自転車」「原動機付自転車」から選択してください");
		}

		// 文字数が32文字より多い場合はエラーを返す。
		if (ValidationUtil.areValidLengths(32, patron, registrationNumber, modelAndColor)) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// 入力値に特殊文字が入っていないか確認する
		if (ValidationUtil.containsForbiddenChars(patron, registrationNumber, modelAndColor)) {
			request.setAttribute("validationError", "使用できない特殊文字が含まれています");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "permission-bike.jsp";
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

			// データベースから取り出したデータにnullがあれば初期設定をしていないためログインページにリダイレクト
			if (ValidationUtil.isNullOrEmpty(lastName, firstName, tel, postCode, address, className,
					studentNumber)) {
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
			String pdfPath = "/pdf/generalStudentPDF/自転車等通学許可願.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));
			// PDFへの書き込み
			// 申請年月日
			editor.writeText(font, requestYear, 430f, 733f, 70f, "left", 12);
			editor.writeText(font, requestMonth, 470f, 733f, 70f, "left", 12);
			editor.writeText(font, requestDay, 510f, 733f, 70f, "left", 12);
			// クラス名・学籍番号・名前
			editor.writeText(font, className, 242f, 676f, 125f, "center", 12);
			editor.writeText(font, studentNumber, 418f, 676f, 120f, "center", 12);
			editor.writeText(font, name, 242f, 648f, 270f, "center", 12);
			// 郵便番号
			editor.writeText(font, FirstPostCode, 262f, 622f, 180f, "left", 10);
			editor.writeText(font, LastPostCode, 305f, 622f, 180f, "left", 10);
			// 電話番号
			editor.writeText(font, firstTel, 402f, 622f, 40f, "left", 10);
			editor.writeText(font, secondTel, 447f, 622f, 40f, "left", 10);
			editor.writeText(font, lastTel, 497f, 622f, 40f, "left", 10);

			// 住所の文字数によって表示する位置を変える
			if (address.length() < 25) {
				editor.writeText(font, address, 242f, 603f, 295f, "left", 12);
			} else {
				editor.writeText(font, address.substring(0, 24), 242f, 610f, 295f, "left", 12);
				editor.writeText(font, address.substring(24, address.length()), 242f, 595f, 295f, "left", 12);
			}
			// 保護者
			editor.writeText(font, patron, 242f, 576f, 112f, "center", 12);
			// 保護者電話番号
			if (patronTel.length() == 11) {
				firstTel = patronTel.substring(0, 3);
				secondTel = patronTel.substring(3, 7);
				lastTel = patronTel.substring(7, 11);
				patronTel = firstTel + "-" + secondTel + "-" + lastTel;
			} else {
				firstTel = patronTel.substring(0, 3);
				secondTel = patronTel.substring(3, 6);
				lastTel = patronTel.substring(6, 10);
				patronTel = firstTel + "-" + secondTel + "-" + lastTel;
			}
			editor.writeText(font, patronTel, 418f, 576f, 120f, "center", 12);
			// 名前・学籍番号
			editor.writeText(font, name, 68f, 290f, 103f, "center", 12);
			editor.writeText(font, studentNumber, 240f, 290f, 68f, "center", 12);

			float num = 0;
			for (int i = 0; i < 2; i++) {
				// 自転車か原動機付自転車か選択
				if (classification.equals("自転車")) {
					editor.writeText(font, "✓", 117f, 519f - num, 50f, "left", 12);
				} else {
					editor.writeText(font, "✓", 192f, 519f - num, 50f, "left", 12);
				}
				// 期間（自）
				editor.writeText(font, startYear, 150f, 501f - num, 180f, "left", 12);
				editor.writeText(font, startMonth, 190f, 501f - num, 180f, "left", 12);
				editor.writeText(font, startDay, 235f, 501f - num, 180f, "left", 12);
				// 期間（至）
				editor.writeText(font, endYear, 330f, 501f - num, 180f, "left", 12);
				editor.writeText(font, endMonth, 370f, 501f - num, 180f, "left", 12);
				editor.writeText(font, endDay, 412f, 501f - num, 180f, "left", 12);
				// 原動機付自転車はナンバー、自転車は防犯登録番号
				editor.writeText(font, registrationNumber, 108f, 482f - num, 160f, "center", 12);
				// 車種・色
				editor.writeText(font, modelAndColor, 320f, 482f - num, 217f, "center", 12);

				num += 392f;
			}

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Permission Bike");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "自転車等通学許可願");
			// Close and save
			editor.close("Permission_Bike.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "permission-bike.jsp";
		}
	}
}
