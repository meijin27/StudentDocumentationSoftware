package tool;

import javax.servlet.http.HttpSession;

import dao.UserDAO;

public class Decrypt {

	private UserDAO dao;

	public Decrypt(UserDAO dao) {
		this.dao = dao;
	}

	public DecryptionResult getDecryptedMasterKey(HttpSession session) throws Exception {
		// セッションからIDの取り出し
		int id = (int) session.getAttribute("id");
		// データベースから暗号化されたアカウント名の取り出し
		String encryptedAccount = dao.getAccount(id);
		// 暗号化されたアカウント名の復号
		String account = CipherUtil.commonDecrypt(encryptedAccount);
		// データベースからivの取り出し
		String iv = dao.getIv(id);
		// セッションから暗号化したマスターキーの取り出し
		String reEncryptedMasterkey = (String) session.getAttribute("master_key");
		// セッションから共通暗号キーで再暗号化したマスターキーの復号	
		String encryptedMasterkey = CipherUtil.commonDecrypt(reEncryptedMasterkey);
		// セッションから暗号化したマスターキーの復号	
		String masterKey = CipherUtil.decrypt(account + id, iv, encryptedMasterkey);

		return new DecryptionResult(account, masterKey, iv);
	}
}
