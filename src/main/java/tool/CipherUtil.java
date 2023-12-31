package tool;

import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtil {
	private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

	// TomcatでJavaのシステムプロパティを設定する場合のコード
	private static String myKey = System.getProperty("MY_ENCRYPTION_KEY");
	private static String myIv = System.getProperty("MY_IV");

	// 環境変数が設定されていない場合にエラーを返す
	static {
		if (myKey == null) {
			throw new RuntimeException("環境変数MY_ENCRYPTION_KEYが設定されていません");
		}
		if (myIv == null) {
			throw new RuntimeException("環境変数MY_IVが設定されていません");
		}
	}

	// マスターキーを用いた暗号化
	public static String encrypt(String key, String iv, String value) {
		try {
			byte[] keyBytes = Arrays.copyOf(key.getBytes("UTF-8"), 16);

			SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));

			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

			byte[] encrypted = cipher.doFinal(value.getBytes());

			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			CustomLogger.getLogger(CipherUtil.class).log(Level.SEVERE, "Encryption failed.", e);
			throw new RuntimeException("An error occurred during encryption.");
		}
	}

	// マスターキーを用いた復号
	public static String decrypt(String key, String iv, String encryptedValue) {
		try {
			byte[] keyBytes = Arrays.copyOf(key.getBytes("UTF-8"), 16);

			SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));

			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

			byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));

			return new String(decrypted, "UTF-8");
		} catch (Exception e) {
			CustomLogger.getLogger(CipherUtil.class).log(Level.SEVERE, "Decryption failed.", e);
			throw new RuntimeException("An error occurred during decryption.");
		}
	}

	// 環境変数を用いた暗号化	
	public static String commonEncrypt(String value) {
		try {
			byte[] keyBytes = Arrays.copyOf(myKey.getBytes("UTF-8"), 16);

			SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.getDecoder().decode(myIv));

			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

			byte[] encrypted = cipher.doFinal(value.getBytes());

			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			CustomLogger.getLogger(CipherUtil.class).log(Level.SEVERE, "Common encryption failed.", e);
			throw new RuntimeException("An error occurred during common encryption.");
		}
	}

	// 環境変数を用いた復号	
	public static String commonDecrypt(String encryptedValue) {
		try {
			byte[] keyBytes = Arrays.copyOf(myKey.getBytes("UTF-8"), 16);

			SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.getDecoder().decode(myIv));

			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

			byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));

			return new String(decrypted, "UTF-8");
		} catch (Exception e) {
			CustomLogger.getLogger(CipherUtil.class).log(Level.SEVERE, "Common decryption failed.", e);
			throw new RuntimeException("An error occurred during common decryption.");
		}
	}
}
