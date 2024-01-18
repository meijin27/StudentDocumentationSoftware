package mainMenu.changeSetting;

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
import tool.Decrypt;
import tool.DecryptionResult;
import tool.RequestAndSessionUtil;
import tool.ValidationUtil;

public class ChangeAddressTelAction extends Action {
	private static final Logger logger = CustomLogger.getLogger(ChangeAddressTelAction.class);

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
		String tel = request.getParameter("tel");
		String postCode = request.getParameter("postCode");
		String address = request.getParameter("address");

		// 入力された値をリクエストに格納	
		RequestAndSessionUtil.storeParametersInRequest(request);

		// 電話番号のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(tel)) {
			request.setAttribute("telError", "入力必須項目です。");
		}
		// 電話番号が半角10~11桁でなければエラーを返す
		else if (ValidationUtil.isTenOrElevenDigit(tel)) {
			request.setAttribute("telError", "電話番号は半角数字10桁～11桁で入力してください。");
		}

		// 郵便番号のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(postCode)) {
			request.setAttribute("postCodeError", "入力必須項目です。");
		}
		// 郵便番号が半角7桁でなければエラーを返す
		else if (ValidationUtil.isSevenDigit(postCode)) {
			request.setAttribute("postCodeError", "郵便番号は半角数字7桁で入力してください。");
		}

		// 住所のエラー処理
		// 未入力項目があればエラーを返す
		if (ValidationUtil.isNullOrEmpty(address)) {
			request.setAttribute("addressError", "入力必須項目です。");
		}
		// 入力値に特殊文字が入っていないか確認する
		else if (ValidationUtil.containsForbiddenChars(address)) {
			request.setAttribute("addressError", "使用できない特殊文字が含まれています");
		}
		// 文字数が多い場合はエラーを返す。
		else if (ValidationUtil.areValidLengths(64, address)) {
			request.setAttribute("addressError", "住所は64文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (RequestAndSessionUtil.hasErrorAttributes(request)) {
			return "change-address-tel.jsp";
		}

		try {
			// データベースとの接続用
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

			// 登録するデータの暗号化
			String encryptedTel = CipherUtil.encrypt(masterKey, iv, tel);
			String encryptedPostCode = CipherUtil.encrypt(masterKey, iv, postCode);
			String encryptedAddress = CipherUtil.encrypt(masterKey, iv, address);

			// 共通暗号キーによる暗号化
			String reEncryptedTel = CipherUtil.commonEncrypt(encryptedTel);
			String reEncryptedPostCode = CipherUtil.commonEncrypt(encryptedPostCode);
			String reEncryptedAddress = CipherUtil.commonEncrypt(encryptedAddress);

			// ユーザー情報の作成
			User user = new User();
			user.setId(id);
			user.setTel(reEncryptedTel);
			user.setPostCode(reEncryptedPostCode);
			user.setAddress(reEncryptedAddress);
			// データベースへの登録
			dao.updateTel(user);
			dao.updatePostCode(user);
			dao.updateAddress(user);
			// アップデート内容のデータベースへの登録
			dao.addOperationLog(id, "Change Address & Tel");

			// セッションに変更情報を持たせる				
			session.setAttribute("action", "住所と電話番号を変更しました。");
			// トークンの削除
			request.getSession().removeAttribute("csrfToken");

			// エラーがない場合は行為成功表示用JSPへリダイレクト
			response.sendRedirect(contextPath + "/mainMenu/action-success.jsp");
			return null;

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			request.setAttribute("innerError", "内部エラーが発生しました。");
			return "change-address-tel.jsp";
		}
	}
}