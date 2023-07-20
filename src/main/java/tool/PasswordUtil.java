package tool;

import java.security.SecureRandom;
import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;

import bean.User;

public class PasswordUtil {
	// 使用する暗号化アルゴリズムを設定します。ここではAES/CBC/PKCS5Paddingを使用します。
	private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

	// AESのキーサイズを設定します。ここでは128bitを使用します。
	private static final int MASTER_KEY_SIZE = 16;

	// Bcryptを使用してパスワードをハッシュ化します。
	public static String getHashedPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	// ランダムな暗号化キーを生成します。このキーはBase64でエンコードされた文字列として返されます。
	private static String generateEncryptionKey() {
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[MASTER_KEY_SIZE];
		random.nextBytes(key);
		return Base64.getEncoder().encodeToString(key);
	}

	// AESのブロックサイズ（128ビット、16バイト）に対応するランダムな初期化ベクトル（IV）を生成します。
	private static String generateIV() {
		SecureRandom random = new SecureRandom();
		byte[] iv = new byte[16];
		random.nextBytes(iv);
		return Base64.getEncoder().encodeToString(iv);
	}

	// ユーザーの登録を行います。パスワードはソルトとペッパーを加えてハッシュ化し、秘密の答えは暗号化します。
	public static User register(String account, String password) {
		// パスワードのハッシュ化
		String hashedPassword = getHashedPassword(password);

		// マスターキーの作成（暗号化前）
		String encryptionKey = generateEncryptionKey();
		// IVの生成
		String iv = generateIV();
		// マスターキーの暗号化。ユーザーIDとパスワードを結合した文字列を鍵として使用します。
		String masterKey = encryptWithAES(account + password, iv, encryptionKey);

		// ユーザー情報の作成
		User user = new User();
		user.setAccount(CipherUtil.commonEncrypt(account));
		user.setPassword(hashedPassword);
		user.setMasterKey(masterKey);
		user.setIv(iv);

		return user;
	}

	// Bcryptを使用して、保存されたパスワードハッシュとユーザーが入力したパスワードを比較します。
	public static boolean isPasswordMatch(String candidatePassword, String hashedPassword) {
		return BCrypt.checkpw(candidatePassword, hashedPassword);
	}

	// AESで暗号化を行います。鍵としては、生成した暗号化キーとIVを使用します。
	private static String encryptWithAES(String key, String iv, String value) {
		return CipherUtil.encrypt(key, iv, value);
	}

	// 暗号化されたキーを復号化します。
	public static String getDecryptedKey(String account, String password, String iv, String encryptedKey) {
		return decryptWithAES(account + password, iv, encryptedKey);
	}

	// AESで復号化を行います。鍵としては、生成した暗号化キーとIVを使用します。
	private static String decryptWithAES(String key, String iv, String encryptedValue) {
		return CipherUtil.decrypt(key, iv, encryptedValue);
	}

}
