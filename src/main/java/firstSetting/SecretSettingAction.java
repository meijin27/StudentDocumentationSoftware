package firstSetting;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.PasswordUtil;

public class SecretSettingAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		// セッションの有効期限切れや直接初期設定入力ページにアクセスした場合はエラーとして処理
		if (session.getAttribute("master_key") == null || session.getAttribute("id") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "エラーが発生しました。やり直してください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login-in.jsp");
			return null;
		}

		// 入力された値の変数への格納
		String secretQuestion = request.getParameter("secretQuestion");
		String secretAnswer = request.getParameter("secretAnswer");

		// もしも入力値が無し、もしくは空の場合はエラーを返す
		if (secretQuestion != null && !secretQuestion.isEmpty() && secretAnswer != null && !secretAnswer.isEmpty()) {
			UserDAO dao = new UserDAO();
			// セッションからIDの取り出し
			int id = (int) session.getAttribute("id");
			// データベースから暗号化されたアカウント名の取り出し
			String encryptedAccount = dao.getAccount(id);
			// 暗号化されたアカウント名の復号
			String account = CipherUtil.commonDecrypt(encryptedAccount);
			// データベースからivの取り出し
			String iv = dao.getIv(id);
			// 秘密の質問はアカウント名とIDをキーにして暗号化（なるべく文字列をランダム化するためにアカウントが先でIDが後）
			String encryptedSecretQuestion = CipherUtil.encrypt(account + id, iv, secretQuestion);
			// 秘密の質問の答えはパスワードと同じくハッシュ化
			String hashedSecretAnswer = PasswordUtil.getHashedPassword(secretAnswer);
			// セッションから暗号化したマスターキーの取り出し
			String reencryptedMasterkey = (String) session.getAttribute("master_key");
			// セッションから共通暗号キーで再暗号化したマスターキーの復号	
			String encryptedMasterkey = CipherUtil.commonDecrypt(reencryptedMasterkey);
			// セッションから暗号化したマスターキーの復号	
			String masterKey = CipherUtil.decrypt(account + id, iv, encryptedMasterkey);
			// マスターキーを秘密の答えと質問で暗号化（なるべく文字列をランダム化するために答えが先で質問が後）
			String secondMasterKey = CipherUtil.encrypt(secretAnswer + secretQuestion, iv, masterKey);

			// 秘密の質問関連のユーザー情報の作成
			User user = new User();
			user.setId(id);
			user.setSecretQuestion(encryptedSecretQuestion);
			user.setSecretAnswer(hashedSecretAnswer);
			user.setSecondMasterKey(secondMasterKey);
			// 秘密の質問と答えとパスワード忘れたとき用のマスターキーのデータベースへの登録
			dao.updateSecret(user);
			// アップデート内容のデータベースへの登録
			dao.addOperationLog(id, "Create SecretQuestion & Answer");

			return "first-setting.jsp";

		}
		request.setAttribute("secretError", "秘密の質問の選択と答えの入力は必須です");
		return "secret-setting.jsp";

	}

}
