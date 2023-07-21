package tool;

public class DecryptionResult {
	private String account;
	private String masterKey;
	private String iv;

	public DecryptionResult(String account, String masterKey, String iv) {
		this.account = account;
		this.masterKey = masterKey;
		this.iv = iv;
	}

	public String getAccount() {
		return account;
	}

	public String getMasterKey() {
		return masterKey;
	}

	public String getIv() {
		return iv;
	}
}
