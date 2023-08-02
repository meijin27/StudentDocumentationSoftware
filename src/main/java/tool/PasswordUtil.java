package tool;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;

import org.mindrot.jbcrypt.BCrypt;

import bean.User;

public class PasswordUtil {
	private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
	private static final int MASTER_KEY_SIZE = 16;

	public static String getHashedPassword(String password) {
		try {
			return BCrypt.hashpw(password, BCrypt.gensalt());
		} catch (Exception e) {
			CustomLogger.getLogger(PasswordUtil.class).log(Level.SEVERE, "Password hashing failed.", e);
			throw new RuntimeException("An error occurred during password hashing.");
		}
	}

	private static String generateEncryptionKey() {
		try {
			SecureRandom random = new SecureRandom();
			byte[] key = new byte[MASTER_KEY_SIZE];
			random.nextBytes(key);
			return Base64.getEncoder().encodeToString(key);
		} catch (Exception e) {
			CustomLogger.getLogger(PasswordUtil.class).log(Level.SEVERE, "Encryption key generation failed.", e);
			throw new RuntimeException("An error occurred during encryption key generation.");
		}
	}

	private static String generateIV() {
		try {
			SecureRandom random = new SecureRandom();
			byte[] iv = new byte[16];
			random.nextBytes(iv);
			return Base64.getEncoder().encodeToString(iv);
		} catch (Exception e) {
			CustomLogger.getLogger(PasswordUtil.class).log(Level.SEVERE,
					"Initialization Vector (IV) generation failed.", e);
			throw new RuntimeException("An error occurred during Initialization Vector (IV) generation.");
		}
	}

	public static User register(String account, String password) {
		try {
			String hashedPassword = getHashedPassword(password);

			String masterKey = generateEncryptionKey();
			String iv = generateIV();
			String encryptedKey = encryptWithAES(password + account, iv, masterKey);
			String reEncryptedKey = CipherUtil.commonEncrypt(encryptedKey);

			User user = new User();
			user.setAccount(CipherUtil.commonEncrypt(account));
			user.setPassword(hashedPassword);
			user.setMasterKey(reEncryptedKey);
			user.setIv(iv);

			return user;
		} catch (Exception e) {
			CustomLogger.getLogger(PasswordUtil.class).log(Level.SEVERE, "User registration failed.", e);
			throw new RuntimeException("An error occurred during user registration.");
		}
	}

	public static boolean isPasswordMatch(String candidatePassword, String hashedPassword) {
		try {
			return BCrypt.checkpw(candidatePassword, hashedPassword);
		} catch (Exception e) {
			CustomLogger.getLogger(PasswordUtil.class).log(Level.SEVERE, "Password matching failed.", e);
			throw new RuntimeException("An error occurred during password matching.");
		}
	}

	private static String encryptWithAES(String key, String iv, String value) {
		try {
			return CipherUtil.encrypt(key, iv, value);
		} catch (Exception e) {
			CustomLogger.getLogger(PasswordUtil.class).log(Level.SEVERE, "AES encryption failed.", e);
			throw new RuntimeException("An error occurred during AES encryption.");
		}
	}

}
