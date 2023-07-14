package bean;

public class User implements java.io.Serializable {

	private int id;
	private String account;
	private String password;
	private String salt;
	private String secretQuestion;
	private String secretAnswer;
	private String encryptedKey;
	private String iv;
	private String studenttype;

	public int getId() {
		return id;
	}

	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public String getSalt() {
		return salt;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public String getEncryptedKey() {
		return encryptedKey;
	}

	public String getIv() {
		return iv;
	}

	public String getStudentType() {
		return studenttype;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public void setEncryptedKey(String encryptedKey) {
		this.encryptedKey = encryptedKey;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public void setStudentType(String studenttype) {
		this.studenttype = studenttype;
	}
}