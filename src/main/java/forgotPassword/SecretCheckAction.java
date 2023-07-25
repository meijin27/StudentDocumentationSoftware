package forgotPassword;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.PasswordUtil;

public class SecretCheckAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションの作成
		HttpSession session = request.getSession();

		// セッションの有効期限切れの場合はエラーとして処理
		if (session.getAttribute("id") == null || session.getAttribute("secretQuestion") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "エラーが発生しました。やり直してください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// 入力された値の変数への格納
		String secretAnswer = request.getParameter("secretAnswer");
		String birthYear = request.getParameter("birthYear");
		String birthMonth = request.getParameter("birthMonth");
		String birthDay = request.getParameter("birthDay");
		// データベース操作用クラス
		UserDAO dao = new UserDAO();
		// セッションから暗号化されたIDの取り出し
		String strId = (String) session.getAttribute("id");
		// IDの復号
		int id = Integer.parseInt(CipherUtil.commonDecrypt(strId));
		// セッションから秘密の質問の取り出し
		String secretQuestion = (String) session.getAttribute("secretQuestion");
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
			String secondMasterKey = CipherUtil.decrypt(secretAnswer + secretQuestion, iv, encryptedSecondMasterKey);
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
				// セッションの秘密の質問データ削除
				session.removeAttribute("secretQuestion");
				// データベースからアカウントの取り出し
				String encryptedAcconunt = dao.getAccount(id);
				// アカウントの共通暗号からの復号
				String account = CipherUtil.commonDecrypt(encryptedAcconunt);
				// 復号したマスターキーをアカウントとIDを暗号キーにして暗号化する（なるべく文字列をランダム化するためにアカウントが先でIDが後）
				String encryptedKey = CipherUtil.encrypt(account + id, iv, secondMasterKey);
				// 暗号化したマスターキーをさらに共通暗号キーで暗号化する
				String reEncryptedKey = CipherUtil.commonEncrypt(encryptedKey);
				// セッションに再暗号化したマスターキーを持たせる
				session.setAttribute("master_key", reEncryptedKey);
				return "recreate-password.jsp";
			}
		}

		// 入力された値が間違っていた場合は元のページに戻す
		request.setAttribute("secretError", "登録されている情報と一致しません");
		return "secret-check.jsp";
	}

}
