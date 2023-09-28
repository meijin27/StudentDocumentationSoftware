package mainMenu.vocationalTraineeDocument;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
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

public class CertificateOfEmploymentAction extends Action {

	private static final Logger logger = CustomLogger.getLogger(CertificateOfEmploymentAction.class);

	// 月ごとの末日を判定するためのメソッド
	private int getLastDayOfMonth(int month) {
		switch (month) {
		case 2:
			return 29; // 2月を29日とする（年を指定できないため）
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		default:
			return 31;
		}
	}

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// セッションからトークンを取得
		String sessionToken = (String) session.getAttribute("csrfToken");
		// リクエストパラメータからトークンを取得
		String requestToken = request.getParameter("csrfToken");
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// IDやマスターキーのセッションがない、トークンが一致しない、またはセッションの有効期限切れの場合はエラーとして処理
		if (session.getAttribute("master_key") == null || session.getAttribute("id") == null || sessionToken == null
				|| requestToken == null || !sessionToken.equals(requestToken)) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "セッションエラーが発生しました。ログインしてください。");
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// 入力された値を変数に格納
		String firstMonth = request.getParameter("firstMonth");
		String secondMonth = request.getParameter("secondMonth");

		// 入力された値をリクエストに格納	
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			request.setAttribute(paramName, paramValue);
		}

		// 未入力項目があればエラーを返す
		if (firstMonth == null || firstMonth.isEmpty()) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "certificate-of-employment.jsp";
		}

		// 一月目は半角数字2桁以下でなければエラーを返す
		if (!firstMonth.matches("^\\d{1,2}$")) {
			request.setAttribute("numberError", "月は半角数字2桁以下で入力してください。");
			return "certificate-of-employment.jsp";
		}

		int month = Integer.parseInt(firstMonth);
		int lastDay = getLastDayOfMonth(month);

		// 日付毎に入力された記号をMAPに格納する()
		Map<Integer, String> firstMonthCalendar = new HashMap<>();
		for (int i = 1; i <= 31; i++) {
			String day = "firstMonthDay" + i;
			String marker = request.getParameter(day);
			// 就労日が空文字か「○」以外の入力があった場合にエラーを返す
			if (marker != null && !marker.isEmpty() && !marker.equals("○")) {
				request.setAttribute("innerError", "就労日は「○」以外入力しないでください。");
				return "certificate-of-employment.jsp";
			}
			// 指定された月の最後の日以降の日付であれば強制的に空文字にする
			if (i > lastDay) {
				firstMonthCalendar.put(i, "");
			} else if (marker != null) {
				firstMonthCalendar.put(i, marker);
			}
		}

		Map<Integer, String> secondMonthCalendar = new HashMap<>();
		// 二月目が未入力であれば何もしない
		if (secondMonth == null || secondMonth.isEmpty()) {

		} else {
			// 二月目は半角数字2桁以下でなければエラーを返す
			if (!secondMonth.matches("^\\d{1,2}$")) {
				request.setAttribute("numberError", "月は半角数字2桁以下で入力してください。");
				return "certificate-of-employment.jsp";
			}

			// 二月目と一月目が同一ならばエラーを返す
			if (secondMonth.equals(firstMonth)) {
				request.setAttribute("logicalError", "一月目と二月目は異なる月にしてください。");
				return "certificate-of-employment.jsp";
			}

			// 二月目が入力されていればカレンダーに入力する
			month = Integer.parseInt(secondMonth);
			lastDay = getLastDayOfMonth(month);

			// 日付毎に入力された記号をMAPに格納する
			for (int i = 1; i <= 31; i++) {
				String day = "secondMonthDay" + i;
				String marker = request.getParameter(day);
				// 就労日が空文字か「○」以外の入力があった場合にエラーを返す
				if (marker != null && !marker.isEmpty() && !marker.equals("○")) {
					request.setAttribute("innerError", "就労日は「○」以外入力しないでください。");
					return "certificate-of-employment.jsp";
				}
				// 指定された月の最後の日以降の日付であれば強制的に空文字にする
				if (i > lastDay) {
					secondMonthCalendar.put(i, "");
				} else if (marker != null) {
					secondMonthCalendar.put(i, marker);
				}
			}
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
				return "certificate-of-employment.jsp";
			}

			// 公共職業安定所名のデータベースからの取り出し
			String reEncryptedNamePESO = dao.getNamePESO(id);
			// 最初にデータベースから取り出した職業訓練生のデータがnullの場合、初期設定をしていないためログインページにリダイレクト
			if (reEncryptedNamePESO == null) {
				session.setAttribute("otherError", "初期設定が完了していません。ログインしてください。");
				response.sendRedirect(contextPath + "/login/login.jsp");
				return null;
			}
			String encryptedNamePESO = CipherUtil.commonDecrypt(reEncryptedNamePESO);
			String namePESO = CipherUtil.decrypt(masterKey, iv, encryptedNamePESO);

			// PDFとフォントのパス作成
			String pdfPath = "/pdf/vocationalTraineePDF/就労証明書.pdf";
			String fontPath = "/font/MS-Mincho-01.ttf";
			// EditPDFのオブジェクト作成
			EditPDF editor = new EditPDF(pdfPath);
			// フォントの作成
			PDFont font = PDType0Font.load(editor.getDocument(), this.getClass().getResourceAsStream(fontPath));
			// PDFへの書き込み
			// 学校名
			editor.writeText(font, "横浜システム工学院専門学校", 415f, 735f, 147f, "left", 11);
			// 生年月日
			editor.writeText(font, birthYear, 415f, 644f, 30f, "left", 12);
			editor.writeText(font, birthMonth, 470f, 644f, 30f, "left", 12);
			editor.writeText(font, birthDay, 513f, 644f, 30f, "left", 12);
			// 名前
			editor.writeText(font, name, 415f, 663f, 147f, "left", 12);
			// 就労した月
			editor.writeText(font, firstMonth, 110f, 608f, 30f, "left", 12);
			editor.writeText(font, secondMonth, 400f, 608f, 30f, "left", 12);
			// クラス名
			editor.writeText(font, className, 415f, 698f, 147f, "left", 12);
			// 就労した日
			editor.writeSymbolsOnCalendar(font, firstMonthCalendar, 62f, 581.5f, 31.2f, 23.3f, 20);
			if (secondMonth != null && !secondMonth.isEmpty()) {
				editor.writeSymbolsOnCalendar(font, secondMonthCalendar, 352.5f, 581.5f, 30.6f, 23.3f, 20);
			}
			// 公共職業安定所名
			editor.writeText(font, namePESO, 90f, 428f, 55f, "center", 12);

			// 出力内容のデータベースへの登録
			dao.addOperationLog(id, "Printing Certificate Of Employment");

			// トークンの削除
			request.getSession().removeAttribute("csrfToken");
			// セッションに作成した書類名を持たせる				
			session.setAttribute("document", "就労証明書");
			// Close and save
			editor.close("Certificate_Of_Employment.pdf", request, response);

			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "certificate-of-employment.jsp";
		}
	}
}