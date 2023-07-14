package tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import bean.User;

public class PasswordUtil {
	private static final String ALGORITHM = "SHA-256";
	private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
	private static final int STRETCH_COUNT = 10001;
	private static final int SALT_BYTE_SIZE = 32;
	private static final int MASTER_KEY_SIZE = 16;
	private static final int AES_IV_SIZE = 16;
	private static final String PEPPER = System.getenv("PEPPER") == null ? "default_pepper" : System.getenv("PEPPER");

	public static String getNewSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	public static String getSaltedHash(String password, String salt) {
		String saltPepperPass = salt + password + PEPPER;
		return getHashStretched(saltPepperPass, STRETCH_COUNT);
	}

	private static String generateEncryptionKey() {
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[MASTER_KEY_SIZE];
		random.nextBytes(key);
		return Base64.getEncoder().encodeToString(key);
	}

	private static String encryptWithAES(String key, String iv, String value) {
		try {
			byte[] keyBytes = Base64.getDecoder().decode(key);
			byte[] ivBytes = Base64.getDecoder().decode(iv);

			SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

			byte[] encrypted = cipher.doFinal(value.getBytes());

			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String generateIV() {
		SecureRandom random = new SecureRandom();
		byte[] iv = new byte[AES_IV_SIZE];
		random.nextBytes(iv);
		return Base64.getEncoder().encodeToString(iv);
	}

	public static User register(String account, String password, String secretQuestion, String secretAnswer) {
		String salt = getNewSalt();
		String hashedPassword = getSaltedHash(password, salt);

		String encryptionKey = generateEncryptionKey();
		String iv = generateIV();
		String encryptedKey = encryptWithAES(encryptionKey, iv, account + password);

		String encryptedSecretAnswer = encryptWithAES(encryptionKey, iv, secretAnswer);

		User user = new User();
		user.setAccount(account);
		user.setPassword(hashedPassword);
		user.setSalt(salt);
		user.setSecretQuestion(secretQuestion);
		user.setSecretAnswer(encryptedSecretAnswer);
		user.setEncryptedKey(encryptedKey);
		user.setIv(iv);

		return user;
	}

	private static String getHashStretched(String input, int count) {
		String hashed = input;
		for (int i = 0; i < count; i++) {
			hashed = getSHA256(hashed);
		}
		return hashed;
	}

	private static String getSHA256(String input) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if (md != null) {
			md.update(input.getBytes());
			return byteToHex(md.digest());
		}
		return null;
	}

	private static String byteToHex(byte[] byteData) {
		StringBuilder sb = new StringBuilder();
		for (byte datum : byteData) {
			sb.append(Integer.toString((datum & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}

/*このコードの使い方は以下の通りです。

初回アカウント作成時に、新たなソルトを生成し、パスワードと合わせてソルト・ハッシュを行います。そして、生成したソルトとハッシュ値をデータベースなどに保存します。

String password = "user_password";
String salt = PasswordUtil.getNewSalt();
String hashedPassword = PasswordUtil.getSaltedHash(password, salt);
// save salt and hashedPassword in database
次に、認証時には、保存されているソルトとユーザーが入力したパスワードを用いてハッシュを計算し、保存されているハッシュと比較します。

String inputPassword = "user_input_password";
// get salt from database
String hashedInputPassword = PasswordUtil.getSaltedHash(inputPassword, salt);
// compare hashedInputPassword and stored hashedPassword
以上が一例です。適宜、ご自身の環境や要件に合わせてコードを調整してください。また、より高度なセキュリティを確保するためには、専用のライブラリ（例：Bcrypt、Scrypt、Argon2など）の利用を検討すると良いでしょう。 */