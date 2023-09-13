package tool;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import dao.UserDAO;

// セッションに格納したIDとマスターキーを復号するためのクラス
public class Decrypt {
	private static final Logger logger = CustomLogger.getLogger(Decrypt.class);

	private UserDAO dao;

	public Decrypt(UserDAO dao) {
		this.dao = dao;
	}

	public DecryptionResult getDecryptedMasterKey(HttpSession session) throws Exception {

		try {
			// セッションから暗号化されたIDの取り出し
			String encryptedId = (String) session.getAttribute("id");
			// IDの復号
			String id = CipherUtil.commonDecrypt(encryptedId);
			// データベースから暗号化されたアカウント名の取り出し
			String encryptedAccount = dao.getAccount(id);
			// 暗号化されたアカウント名の復号
			String account = CipherUtil.commonDecrypt(encryptedAccount);
			// データベースからivの取り出し
			String iv = dao.getIv(id);
			// セッションから暗号化したマスターキーの取り出し
			String reEncryptedMasterkey = (String) session.getAttribute("master_key");
			// 共通暗号キーで再暗号化したマスターキーの復号	
			String encryptedMasterkey = CipherUtil.commonDecrypt(reEncryptedMasterkey);
			// 暗号化したマスターキーの復号	
			String masterKey = CipherUtil.decrypt(account + id, iv, encryptedMasterkey);
			return new DecryptionResult(id, account, masterKey, iv);
		} catch (Exception e) {
			CustomLogger.getLogger(Decrypt.class).log(Level.SEVERE, "Decryption failed.", e);
			throw new RuntimeException("An error occurred during decryption.");
		}
	}
}
