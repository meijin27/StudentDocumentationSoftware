package forgotPassword;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.CustomLogger;
import tool.RequestAndSessionUtil;
import tool.ValidationUtil;

public class SearchAccountAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(SearchAccountAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションの作成
		HttpSession session = request.getSession();
		// リダイレクト用コンテキストパス
		String contextPath = request.getContextPath();

		// トークン及びログイン状態の確認
		if (RequestAndSessionUtil.validateSession(request, response)) {
			// ログイン状態が不正ならば処理を終了
			return null;
		}

		// 入力されたアカウント名を変数に格納
		String account = request.getParameter("account");

		// アカウント名が入力されている場合の処理		
		if (ValidationUtil.isNullOrEmpty(account)) {
			// アカウント名未入力の場合は元のページに戻す
			request.setAttribute("accountError", "アカウント名の入力は必須です");
			return "search-account.jsp";
		}

		// 文字数が32文字より多い場合はエラーを返す
		if (ValidationUtil.areValidLengths(32, account)) {
			request.setAttribute("accountError", "32文字以下で入力してください。");
			return "search-account.jsp";
		}

		try {
			// データベース操作用クラス
			UserDAO dao = new UserDAO();
			// 入力されたアカウント名を共通暗号キーで暗号化
			String encryptedAccount = CipherUtil.commonEncrypt(account);
			// データベースに暗号化されたアカウント名が登録されているか確認するため
			// 登録されているアカウント名と一致した場合は一致したユーザーデータを格納する
			User user = dao.loginSearch(encryptedAccount);
			// もしアカウントがデータベースに登録されていなければエラーメッセージを表示		
			if (user == null) {
				request.setAttribute("accountError", "このアカウントはパスワードの再設定ができません");
				return "search-account.jsp";
			}
			// 現在のセッションを無効化
			session.invalidate();
			// 新しいセッションを作成
			session = request.getSession(true);
			// 秘密の質問を取り出す
			String reEncryptedSecretQuestion = user.getSecretQuestion();
			// 秘密の質問と生年の登録を確認し、登録済みならパスワード再設定にすすむ
			if (reEncryptedSecretQuestion != null && user.getStudentType() != null) {
				// ユーザーIDを変数に格納
				String id = user.getId();
				// データベースからivの取り出し
				String iv = dao.getIv(id);
				// IDを共通暗号キーで暗号化する
				String encryptedId = CipherUtil.commonEncrypt(String.valueOf(id));
				// セッションに共通暗号キーで暗号化されたユーザー識別用のIDを格納
				session.setAttribute("encryptedId", encryptedId);
				// 暗号化した秘密の質問を共通暗号キーで復号する
				String encryptedSecretQuestion = CipherUtil.commonDecrypt(reEncryptedSecretQuestion);
				// 秘密の質問はアカウント名とIDをキーにして復号
				String secretQuestion = CipherUtil.decrypt(account + id, iv, encryptedSecretQuestion);
				// セッションに秘密の質問を格納
				session.setAttribute("secretQuestion", secretQuestion);
				// トークンの削除
				request.getSession().removeAttribute("csrfToken");
				// 秘密の質問と答え確認画面にリダイレクト
				response.sendRedirect(contextPath + "/forgotPassword/secret-check.jsp");
				return null;
			} else {
				request.setAttribute("accountError", "このアカウントはパスワードの再設定ができません");
				return "search-account.jsp";
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "search-account.jsp";
		}
	}

}
