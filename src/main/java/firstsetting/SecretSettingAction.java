package firstsetting;

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
			// 秘密の質問は共通暗号化キーで暗号化
			String encryptionSecretQuestion = CipherUtil.commonEncrypt(secretQuestion);
			// 秘密の質問の答えはパスワードと同じくハッシュ化
			String hashedSecretAnswer = PasswordUtil.getHashedPassword(secretAnswer);
			// セッションから暗号化したマスターキーの取り出し
			String master_key = (String) session.getAttribute("master_key");
			// セッションから暗号化したマスターキーの復号	
			String masterKey = CipherUtil.commonDecrypt(master_key);
			// データベースからivの取り出し
			String iv = dao.getIv(id);
			System.out.println(id + ":" + iv + ":" + secretQuestion + ":" + secretAnswer + ":" + masterKey);
			// マスターキーを秘密の質問と答えで暗号化
			String secondMasterKey = CipherUtil.encrypt(secretQuestion + secretAnswer, iv, masterKey);

			// ユーザー情報の作成
			User user = new User();
			user.setId(id);
			user.setSecretQuestion(encryptionSecretQuestion);
			user.setSecretAnswer(hashedSecretAnswer);
			user.setSecondMasterKey(secondMasterKey);
			// 秘密の質問と答えのデータベースへの登録
			dao.updateSecret(user);
			// アップデート内容のデータベースへの登録
			dao.addOperationLog(id, "Create SecretQuestion & Answer");

			return "first-setting.jsp";

		}
		request.setAttribute("secretError", "秘密の質問の選択と答えの入力は必須です");
		return "secret-setting.jsp";

	}

}
