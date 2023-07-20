package bean;

public class User implements java.io.Serializable {

	private int id;
	private String account;
	private String password;
	private String secretQuestion;
	private String secretAnswer;
	private String encryptedKey;
	private String secondEncryptedKey;
	private String iv;
	private String studenttype;
	private String lastName;
	private String firstName;
	private String className;
	private String studentNumber;
	private String birthYear;
	private String birthMonth;
	private String birthDay;

	public int getId() {
		return id;
	}

	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public String getSecondEncryptedKey() {
		return secondEncryptedKey;
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

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getClassName() {
		return className;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public String getBirthMonth() {
		return birthMonth;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setSecondEncryptedKey(String secondEncryptedKey) {
		this.secondEncryptedKey = secondEncryptedKey;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public void setStudentType(String studenttype) {
		this.studenttype = studenttype;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
}