package tool;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.mindrot.jbcrypt.BCrypt;

import bean.User;

public class PasswordUtil {
	// 使用する暗号化アルゴリズムを設定します。ここではAES/CBC/PKCS5Paddingを使用します。
	private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

	// AESのキーサイズを設定します。ここでは128bitを使用します。
	private static final int MASTER_KEY_SIZE = 16;

	// 新しいソルト（ランダムなバイト列）を生成します。このソルトはBase64でエンコードされた文字列として返されます。
	public static String getNewSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[MASTER_KEY_SIZE];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

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

	// AESで暗号化を行います。鍵としては、生成した暗号化キーとIVを使用します。
	private static String encryptWithAES(String key, String iv, String value) {
		try {
			byte[] keyBytes = Arrays.copyOf(key.getBytes("UTF-8"), 16);

			SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("UTF-8"));

			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

			byte[] encrypted = cipher.doFinal(value.getBytes());

			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// AESのブロックサイズ（128ビット、16バイト）に対応するランダムな初期化ベクトル（IV）を生成します。
	private static String generateIV() {
		SecureRandom random = new SecureRandom();
		byte[] iv = new byte[16];
		random.nextBytes(iv);
		return Base64.getEncoder().encodeToString(iv);
	}

	// ユーザーの登録を行います。パスワードはソルトとペッパーを加えてハッシュ化し、秘密の答えは暗号化します。
	public static User register(String account, String password, String secretQuestion, String secretAnswer) {
		// パスワードのハッシュ化
		String hashedPassword = getHashedPassword(password);

		// 暗号化キーとIVの生成と暗号化。ユーザーIDとパスワードを結合した文字列を鍵として使用します。
		String encryptionKey = generateEncryptionKey();
		String iv = generateIV();
		String encryptedKey = encryptWithAES(account + password, iv, encryptionKey);

		// 秘密の答えの暗号化。暗号化キーとIVを鍵として使用します。
		String encryptedSecretAnswer = encryptWithAES(encryptionKey, iv, secretAnswer);

		// ユーザー情報の作成
		User user = new User();
		user.setAccount(account);
		user.setPassword(hashedPassword);
		user.setSecretQuestion(secretQuestion);
		user.setSecretAnswer(encryptedSecretAnswer);
		user.setEncryptedKey(encryptedKey);
		user.setIv(iv);
		// データベースにユーザー情報を保存する処理は省略します。

		return user;
	}

	// Bcryptを使用して、保存されたパスワードハッシュとユーザーが入力したパスワードを比較します。
	public static boolean isPasswordMatch(String candidatePassword, String hashedPassword) {
		return BCrypt.checkpw(candidatePassword, hashedPassword);
	}
}
