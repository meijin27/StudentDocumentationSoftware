package bean;

public class User implements java.io.Serializable {

	private String id;// ID
	private String account;// アカウント名
	private String password;// パスワード
	private String masterKey;// マスターキー
	private String secondMasterKey;// パスワードを忘れたとき用のマスターキー
	private String iv;// 初期化ベクトル（Initialization Vector：IV）
	private String secretQuestion;// 秘密の質問
	private String secretAnswer;// 秘密の質問の答え
	private String lastName;// 姓
	private String firstName;// 名
	private String tel;// 電話番号
	private String postCode;// 郵便番号
	private String address;// 住所
	private String birthYear;// 生年
	private String birthMonth;// 生月
	private String birthDay;// 生日
	private String studentType; // 学生種類
	private String className; // クラス名
	private String studentNumber; // 学籍番号
	private String schoolYear; // 学年
	private String classNumber; // 組
	private String namePESO; // 公共職業安定所名。Public Employment Security Officeの略
	private String attendanceNumber; // 職業訓練生に付与される出席番号
	private String employmentInsurance;// 雇用保険有無
	private String supplyNumber;// 支給番号

	// 以下ゲッター
	public String getId() {
		return id;
	}

	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public String getMasterKey() {
		return masterKey;
	}

	public String getSecondMasterKey() {
		return secondMasterKey;
	}

	public String getIv() {
		return iv;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getTel() {
		return tel;
	}

	public String getPostCode() {
		return postCode;
	}

	public String getAddress() {
		return address;
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

	public String getStudentType() {
		return studentType;
	}

	public String getClassName() {
		return className;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public String getClassNumber() {
		return classNumber;
	}

	public String getNamePESO() {
		return namePESO;
	}

	public String getAttendanceNumber() {
		return attendanceNumber;
	}

	public String getEmploymentInsurance() {
		return employmentInsurance;
	}

	public String getSupplyNumber() {
		return supplyNumber;
	}

	// 以下セッター
	public void setId(String id) {
		this.id = id;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public void setStudentType(String studentType) {
		this.studentType = studentType;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	public void setNamePESO(String namePESO) {
		this.namePESO = namePESO;
	}

	public void setAttendanceNumber(String attendanceNumber) {
		this.attendanceNumber = attendanceNumber;
	}

	public void setEmploymentInsurance(String employmentInsurance) {
		this.employmentInsurance = employmentInsurance;
	}

	public void setSupplyNumber(String supplyNumber) {
		this.supplyNumber = supplyNumber;
	}

}