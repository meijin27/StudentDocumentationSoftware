package bean;

public class User implements java.io.Serializable {

	private int id;
	private String account;
	private String password;
	private String secretQuestion;
	private String secretAnswer;
	private String masterKey;
	private String secondMasterKey;
	private String iv;
	private String studentType;
	private String lastName;
	private String firstName;
	private String className;
	private String schoolYear;
	private String classNumber;
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

	public String getSecondMasterKey() {
		return secondMasterKey;
	}

	public String getMasterKey() {
		return masterKey;
	}

	public String getIv() {
		return iv;
	}

	public String getStudentType() {
		return studentType;
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

	public String getSchoolYear() {
		return schoolYear;
	}

	public String getClassNumber() {
		return classNumber;
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

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}

	public void setSecondMasterKey(String secondMasterKey) {
		this.secondMasterKey = secondMasterKey;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public void setStudentType(String studentType) {
		this.studentType = studentType;
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

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
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