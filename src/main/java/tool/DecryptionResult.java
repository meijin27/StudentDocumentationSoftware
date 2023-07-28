package tool;

public class DecryptionResult {
	private String id;
	private String account;
	private String masterKey;
	private String iv;

	public DecryptionResult(String id, String account, String masterKey, String iv) {
		this.id = id;
		this.account = account;
		this.masterKey = masterKey;
		this.iv = iv;
	}

	public String getId() {
		return id;
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
