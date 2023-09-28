package forgotPassword;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.CustomLogger;
import tool.PasswordUtil;

public class SecretCheckAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(SecretCheckAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// セッションからトークンを取得
		String sessionToken = (String) session.getAttribute("csrfToken");
		// リクエストパラメータからトークンを取得
		String requestToken = request.getParameter("csrfToken");
		// セッションから秘密の質問を取り出す
		String secretQuestion = (String) session.getAttribute("secretQuestion");
		// セッションから暗号化されたIDを取り出す
		String encryptedId = (String) session.getAttribute("encryptedId");
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// 秘密の質問やIDがNULL、トークンが一致しない、またはどちらかがnullの場合はエラー
		if (encryptedId == null || secretQuestion == null || sessionToken == null || requestToken == null
				|| !sessionToken.equals(requestToken)) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "セッションエラーが発生しました。最初からやり直してください。");
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// 入力された値の変数への格納
		String secretAnswer = request.getParameter("secretAnswer");
		String birthYear = request.getParameter("birthYear");
		String birthMonth = request.getParameter("birthMonth");
		String birthDay = request.getParameter("birthDay");

		// もしも入力値が無し、もしくは空の場合はエラーを返す
		if (secretAnswer == null || birthYear == null || birthMonth == null || birthDay == null
				|| secretAnswer.isEmpty() || birthYear.isEmpty() || birthMonth.isEmpty()
				|| birthDay.isEmpty()) {
			request.setAttribute("secretError", "未入力項目があります。");
			return "secret-check.jsp";
		}

		// 文字数が32文字より多い場合はエラーを返す。生年月日はセレクトボックスの有効範囲画外の場合にエラーを返す。		
		if (secretAnswer.length() > 32 || birthYear.length() > 4 || birthMonth.length() > 2 || birthDay.length() > 2) {
			request.setAttribute("secretError", "32文字以下で入力してください。");
			return "secret-check.jsp";
		}

		// 生年月日が数字か確認する
		try {
			int year = Integer.parseInt(birthYear);
			int month = Integer.parseInt(birthMonth);
			int day = Integer.parseInt(birthDay);

			// 正しい日付かどうか確認する
			LocalDate date = LocalDate.of(year, month, day);
		} catch (NumberFormatException e) {
			request.setAttribute("secretError", "生年月日は数字で入力してください。");
			return "secret-check.jsp";
		} catch (DateTimeException e) {
			request.setAttribute("secretError", "有効な日付を入力してください。");
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
			if (secretAnswer.length() <= 32 && PasswordUtil.isPasswordMatch(secretAnswer, CheckSecretAnswer)) {
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
