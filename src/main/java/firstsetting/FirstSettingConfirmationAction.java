package firstsetting;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;

public class FirstSettingConfirmationAction extends Action {

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

		// セッションからデータの取り出し
		String lastName = (String) session.getAttribute("lastName");
		String firstName = (String) session.getAttribute("firstName");
		String studentType = (String) session.getAttribute("studentType");
		String className = (String) session.getAttribute("className");
		String studentNumber = (String) session.getAttribute("studentNumber");
		String birthYear = (String) session.getAttribute("birthYear");
		String birthMonth = (String) session.getAttribute("birthMonth");
		String birthDay = (String) session.getAttribute("birthDay");
		
		// セッションのデータ削除
		session.removeAttribute("lastName");
		session.removeAttribute("firstName");
		session.removeAttribute("studentType");
		session.removeAttribute("className");
		session.removeAttribute("studentNumber");
		session.removeAttribute("birthYear");
		session.removeAttribute("birthMonth");
		session.removeAttribute("birthDay");

		// データベースとの接続用
		UserDAO dao = new UserDAO();
		// セッションからIDの取り出し
		int id = (int) session.getAttribute("id");
		// セッションから暗号化したマスターキーの取り出し
		String master_key = (String) session.getAttribute("master_key");
		// セッションから暗号化したマスターキーの復号	
		String encryptionKey = CipherUtil.commonDecrypt(master_key);
		// データベースからivの取り出し
		String iv = dao.;
		
		// 登録するデータの暗号化
		
		
		
		
		
		
		
		
		
		
		
		
		// ユーザー情報の作成
		User user = new User();
		user.setId(id);
		user.setSecretQuestion(encryptionSecretQuestion);
		user.setSecretAnswer(hashedSecretAnswer);

		// 秘密の質問と答えのデータベースへの登録
		dao.updateSecret(user);
		// アップデート内容のデータベースへの登録
		dao.addOperationLog(id, "Create SecretQuestion & Answer");

		return "first-setting-confirmation.jsp";

	}

}
