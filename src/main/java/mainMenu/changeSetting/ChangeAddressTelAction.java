package mainMenu.changeSetting;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;
import tool.Action;
import tool.CipherUtil;
import tool.Decrypt;
import tool.DecryptionResult;

public class ChangeAddressTelAction extends Action {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションの作成
		HttpSession session = request.getSession();

		// セッションの有効期限切れの場合はエラーとして処理
		if (session.getAttribute("id") == null || session.getAttribute("master_key") == null) {
			// ログインページにリダイレクト
			session.setAttribute("otherError", "エラーが発生しました。やり直してください。");
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/login/login.jsp");
			return null;
		}

		// 入力された値を変数に格納
		String tel = request.getParameter("tel");
		String postCode = request.getParameter("postCode");
		String address = request.getParameter("address");

		// 入力された値をセッションに格納	
		session.setAttribute("tel", tel);
		session.setAttribute("postCode", postCode);
		session.setAttribute("address", address);

		// 未入力項目があればエラーを返す
		if (tel == null || postCode == null || address == null) {
			request.setAttribute("nullError", "未入力項目があります。");
		}

		// 電話番号が半角10~11桁でなければエラーを返す
		if (!tel.matches("^\\d{10,11}$")) {
			request.setAttribute("telError", "電話番号は半角数字10桁～11桁で入力してください。");
		}

		// 郵便番号が半角7桁でなければエラーを返す
		if (!postCode.matches("^\\d{7}$")) {
			request.setAttribute("postCodeError", "郵便番号は半角数字7桁で入力してください。");
		}

		// 文字数が64文字より多い場合はエラーを返す
		if (address.length() > 64) {
			request.setAttribute("valueLongError", "64文字以下で入力してください。");
		}

		// エラーが発生している場合は元のページに戻す
		if (request.getAttribute("nullError") != null || request.getAttribute("valueLongError") != null
				|| request.getAttribute("telError") != null || request.getAttribute("postCodeError") != null) {
			return "change-address-tel.jsp";
		}

		// セッションのデータ削除
		session.removeAttribute("tel");
		session.removeAttribute("postCode");
		session.removeAttribute("address");

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
		// 名前と生年月日変更成功画面に遷移
		request.setAttribute("changes", "住所と電話番号を変更しました。");
		return "change-success.jsp";

	}
}