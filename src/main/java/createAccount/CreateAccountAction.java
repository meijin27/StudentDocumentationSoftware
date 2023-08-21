package createAccount;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.CustomLogger;

public class CreateAccountAction extends Action {

	private static final Logger logger = CustomLogger.getLogger(CreateAccountAction.class);

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 入力されたアカウント名を変数に格納
		String account = request.getParameter("account");

		// アカウント名が入力されている場合の処理
		if (account != null && !account.isEmpty()) {
			// 文字数が32文字より多い場合はエラーを返す
			if (account.length() > 32) {
				request.setAttribute("accountError", "32文字以下で入力してください。");
				return "create-account.jsp";
			}

			// データベース操作用クラス
			UserDAO dao = new UserDAO();
			// ユーザークラスの作成
			User user = null;
			// 暗号化されたアカウント名
			String encryptedAccount = null;
			try {
				// 入力されたアカウント名を共通暗号キーで暗号化
				encryptedAccount = CipherUtil.commonEncrypt(account);
				// データベースに暗号化されたアカウント名が登録されているか確認するため
				// 登録されているアカウント名と一致した場合は一致したユーザーデータを格納する
				user = dao.createSearch(encryptedAccount);
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				request.setAttribute("accountError", "内部エラーが発生しました。");
				return "create-account.jsp";
			}
			// エラーがあれば元のページに戻る
			if (request.getAttribute("accountError") != null) {
				return "create-account.jsp";
			}

			// ユーザーデータがnullである　＝　未登録アカウントの場合の処理
			if (user == null) {
				// リクエストに共通暗号キーで暗号化されたアカウント名を格納
				request.setAttribute("encryptedAccount", encryptedAccount);
				// パスワード登録画面に移動
				return "create-password.jsp";
				// もしアカウントがデータベースに登録されていればエラーメッセージを表示			
			} else {
				request.setAttribute("accountError", "このアカウントは使用できません。");
				return "create-account.jsp";
			}
		}
		// アカウント名未入力の場合は元のページに戻す
		request.setAttribute("accountError", "アカウント名の入力は必須です。");
		return "create-account.jsp";
	}
}
