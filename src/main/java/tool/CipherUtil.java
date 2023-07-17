package tool;

import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtil {
	private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

	// 他のクラスからアクセス可能なencryptメソッド
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
			throw new RuntimeException(e);
		}
	}

	// 他のクラスからアクセス可能なdecryptメソッド
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
			throw new RuntimeException(e);
		}
	}
}
