package tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class PasswordUtil {
	// 使用する暗号化アルゴリズムを設定します。ここではSHA-256を使用します。
	private static final String ALGORITHM = "SHA-256";

	// 使用する暗号化アルゴリズムを設定します。ここではAESを使用します。
	private static final String CIPHER_ALGORITHM = "AES";

	// ストレッチングを行う回数を設定します。
	private static final int STRETCH_COUNT = 10001;

	// ソルトを生成する際のバイト数を設定します。
	private static final int SALT_BYTE_SIZE = 32;

	// AESのキーサイズを設定します。ここでは128bitを使用します。
	private static final int MASTER_KEY_SIZE = 16;

	// ペッパー（暗号化の追加要素）を環境変数から取得します。設定されていない場合はデフォルト値を使用します。
	private static final String PEPPER = System.getenv("PEPPER") == null ? "default_pepper" : System.getenv("PEPPER");

	// ランダムな暗号化キーを生成します。このキーはBase64でエンコードされた文字列として返されます。
	private static String generateEncryptionKey() {
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[SALT_BYTE_SIZE];
		random.nextBytes(key);
		return Base64.getEncoder().encodeToString(key);
	}

	// AESで暗号化を行います。鍵としては、ユーザーIDとパスワードを結合した文字列を使用します。
	private static String encryptWithAES(String key, String value) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(getSHA256(key).getBytes(), CIPHER_ALGORITHM);
			IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]); // 初期化ベクトルを0で初期化した16バイトの配列を使用します。
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
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

	// AESで暗号化を行います。鍵としては、生成した暗号化キーとIVを使用します。
	private static String encryptWithAES(String key, String iv, String value) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(getSHA256(key).getBytes(), CIPHER_ALGORITHM);
			IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	// ユーザーの登録を行います。パスワードはソルトとペッパーを加えてハッシュ化し、秘密の答えは暗号化します。
	public static User register(String userId, String password, String secretQuestion, String secretAnswer) {
	    // パスワードのハッシュ化
	    String salt = getNewSalt();
	    String hashedPassword = getSaltedHash(password, salt);
	
	    // 暗号化キーとIVの生成と暗号化。ユーザーIDとパスワードを結合した文字列を鍵として使用します。
	    String encryptionKey = generateEncryptionKey();
	    String iv = generateIV();
	    String encryptedKey = encryptWithAES(userId + password, iv, encryptionKey);
	
	    // 秘密の答えの暗号化。暗号化キーとIVを鍵として使用します。
	    String encryptedSecretAnswer = encryptWithAES(encryptionKey, iv, secretAnswer);
	
	    // ユーザー情報の作成
	    User user = new User(userId, hashedPassword, salt, secretQuestion, encryptedSecretAnswer, encryptedKey, iv);
	    // データベースにユーザー情報を保存する処理は省略します。
	
	    return user;
	}
	*/
	// ソルトとペッパーを加えたパスワードをハッシュ化します。ハッシュ化は設定した回数だけ繰り返されます（ストレッチング）。
	public static String getSaltedHash(String password, String salt) {
		String saltPepperPass = salt + password + PEPPER;
		return getHashStretched(saltPepperPass, STRETCH_COUNT);
	}

	// 新しいソルト（ランダムなバイト列）を生成します。このソルトはBase64でエンコードされた文字列として返されます。
	public static String getNewSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	// ハッシュ値を設定した回数だけ計算し直します。これによりハッシュ関数の計算時間が増え、ブルートフォース攻撃の効率を低下させます。
	private static String getHashStretched(String input, int count) {
		String hashed = input;
		for (int i = 0; i < count; i++) {
			hashed = getSHA256(hashed);
		}
		return hashed;
	}

	// 入力文字列のSHA-256ハッシュを計算します。
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

	// バイト配列を16進数の文字列に変換します。
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