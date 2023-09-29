package forgotPassword;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.CustomLogger;
import tool.ErrorCheckUtil;
import tool.PasswordUtil;
import tool.RequestAndSessionUtil;
import tool.ValidationUtil;

public class SecretCheckAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(SecretCheckAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークン及びログイン状態の確認
		if (!RequestAndSessionUtil.validateSession(request, response, "encryptedId", "secretQuestion")) {
			// ログイン状態が不正ならば処理を終了
			return null;
		}

		// セッションから秘密の質問を取り出す
		String secretQuestion = (String) session.getAttribute("secretQuestion");
		// セッションから暗号化されたIDを取り出す
		String encryptedId = (String) session.getAttribute("encryptedId");
		// 入力された値の変数への格納
		String secretAnswer = request.getParameter("secretAnswer");
		String birthYear = request.getParameter("birthYear");
		String birthMonth = request.getParameter("birthMonth");
		String birthDay = request.getParameter("birthDay");

		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(secretAnswer, birthYear, birthMonth, birthDay)) {
			request.setAttribute("nullError", "未入力項目があります。");
			return "secret-check.jsp";
		}

		// 文字数が32文字より多い場合はエラーを返す。		
		if (!ValidationUtil.areValidLengths(32, secretAnswer)) {
			request.setAttribute("valueLongError", "32文字以下で入力してください。");
		}

		// 生年月日が存在しない日付の場合はエラーにする
		// 年月日が年４桁、月日２桁になっていることを検証し、違う場合はエラーを返す
		if (!ValidationUtil.isFourDigit(birthYear) ||
				!ValidationUtil.isOneOrTwoDigit(birthMonth, birthDay)) {
			request.setAttribute("dayError", "年月日は正規の桁数で入力してください。");
		} else {
			if (!ValidationUtil.validateDate(birthYear, birthMonth, birthDay)) {
				request.setAttribute("dayError", "存在しない日付です。");
			}
		}

		// エラーが発生している場合は元のページに戻す
		if (ErrorCheckUtil.hasErrorAttributes(request)) {
			return "secret-check.jsp";
		}

		try {
			// IDの復号
			String id = CipherUtil.commonDecrypt(encryptedId);
			// データベース操作用クラス
			UserDAO dao = new UserDAO();
			// データベースからivの取り出し
			String iv = dao.getIv(id);
			// データベースから照合用データの取り出し
			String CheckSecretAnswer = dao.getSecretAnswer(id);
			String CheckBirthYear = dao.getBirthYear(id);
			String CheckBirthMonth = dao.getBirthMonth(id);
			String CheckBirthDay = dao.getBirthDay(id);
			// 秘密の質問の答えの一致確認		
			if (PasswordUtil.isPasswordMatch(secretAnswer, CheckSecretAnswer)) {
				// データベースからセカンドマスターキーの取り出し
				String reEncryptedSecondMasterKey = dao.getSecondMasterKey(id);
				// セカンドマスターキーの共通暗号からの復号
				String encryptedSecondMasterKey = CipherUtil.commonDecrypt(reEncryptedSecondMasterKey);
				// セカンドマスターキーの復号
				String secondMasterKey = CipherUtil.decrypt(secretAnswer + secretQuestion, iv,
						encryptedSecondMasterKey);
				// 入力された生年月日の暗号化			
				String encryptedBirthYear = CipherUtil.encrypt(secondMasterKey, iv, birthYear);
				String encryptedBirthMonth = CipherUtil.encrypt(secondMasterKey, iv, birthMonth);
				String encryptedBirthDay = CipherUtil.encrypt(secondMasterKey, iv, birthDay);
				// 入力された生年月日の共通暗号キーでの再暗号化			
				String reEncryptedBirthYear = CipherUtil.commonEncrypt(encryptedBirthYear);
				String reEncryptedBirthMonth = CipherUtil.commonEncrypt(encryptedBirthMonth);
				String reEncryptedBirthDay = CipherUtil.commonEncrypt(encryptedBirthDay);

				// 生年月日の一致確認(暗号化されたデータ同士の照合)
				if (reEncryptedBirthYear.equals(CheckBirthYear) && reEncryptedBirthMonth.equals(CheckBirthMonth)
						&& reEncryptedBirthDay.equals(CheckBirthDay)) {
					// データベースからアカウントの取り出し
					String encryptedAcconunt = dao.getAccount(id);
					// アカウントの共通暗号からの復号
					String account = CipherUtil.commonDecrypt(encryptedAcconunt);
					// 復号したマスターキーをアカウントとIDを暗号キーにして暗号化する（なるべく文字列をランダム化するためにアカウントが先でIDが後）
					String encryptedKey = CipherUtil.encrypt(account + id, iv, secondMasterKey);
					// 暗号化したマスターキーをさらに共通暗号キーで暗号化する
					String reEncryptedKey = CipherUtil.commonEncrypt(encryptedKey);
					// セッションに再暗号化したマスターキーを格納
					session.setAttribute("master_key", reEncryptedKey);
					// セッションから秘密の質問を削除
					request.getSession().removeAttribute("secretQuestion");
					// トークンの削除
					request.getSession().removeAttribute("csrfToken");
					// 秘密の質問と答え確認画面にリダイレクト
					response.sendRedirect(contextPath + "/forgotPassword/recreate-password.jsp");
					return null;
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("secretError", "内部エラーが発生しました。");
			return "secret-check.jsp";
		}
		// 入力された値が間違っていた場合は元のページに戻す
		request.setAttribute("secretError", "登録されている情報と一致しません");
		return "secret-check.jsp";
	}

}
