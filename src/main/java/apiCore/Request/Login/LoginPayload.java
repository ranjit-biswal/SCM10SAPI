package apiCore.Request.Login;

public class LoginPayload {
	private String UserId;
	private String Password;
	private String UtilityAccountNumber;
	private String LanguageCode;
	private String ExternalLoginId;
	private int LoginMode;
	private int IsCSRUser;
	private String PushToken;
	private String Deviceid;
	private String UpdatedDate;
	private String LUpdHideShow;
	private int TimeOffSet;
	private String deviceType;
	private String OperatingSystem;
	private String Browser;
	private String Country;
	private String IPAddress;

	/*
	 * private String Key; private String IsEmailasUserName;
	 */

	public LoginPayload(String UserId, String Password, String UtilityAccountNumber,  String LanguageCode,String ExternalLoginId,
			int LoginMode,
			int IsCSRUser,  String PushToken,String Deviceid,  String UpdatedDate,
			String LUpdHideShow, int TimeOffSet, String deviceType, String OperatingSystem, String Browser,
			String Country, String IPAddress) {
		
		this.UserId = UserId;
		this.Password = Password;
		this.UtilityAccountNumber = UtilityAccountNumber;
		this.LanguageCode = LanguageCode;
		this.ExternalLoginId = ExternalLoginId;
		this.LoginMode = LoginMode;
		this.IsCSRUser = IsCSRUser;
		this.PushToken = PushToken;
		this.Deviceid = Deviceid;
		this.UpdatedDate = UpdatedDate;	
		this.LUpdHideShow = LUpdHideShow;
		this.TimeOffSet = TimeOffSet;
		this.deviceType= deviceType;
		this.OperatingSystem= OperatingSystem;
		this.Browser =Browser;
		this.Country = Country;
		this.IPAddress = IPAddress;		

	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getUtilityAccountNumber() {
		return UtilityAccountNumber;
	}

	public void setUtilityAccountNumber(String utilityAccountNumber) {
		UtilityAccountNumber = utilityAccountNumber;
	}

	public String getLanguageCode() {
		return LanguageCode;
	}

	public void setLanguageCode(String languageCode) {
		LanguageCode = languageCode;
	}

	public String getExternalLoginId() {
		return ExternalLoginId;
	}

	public void setExternalLoginId(String externalLoginId) {
		ExternalLoginId = externalLoginId;
	}

	public int getLoginMode() {
		return LoginMode;
	}

	public void setLoginMode(int loginMode) {
		LoginMode = loginMode;
	}

	public int getIsCSRUser() {
		return IsCSRUser;
	}

	public void setIsCSRUser(int isCSRUser) {
		IsCSRUser = isCSRUser;
	}

	public String getPushToken() {
		return PushToken;
	}

	public void setPushToken(String pushToken) {
		PushToken = pushToken;
	}

	public String getDeviceid() {
		return Deviceid;
	}

	public void setDeviceid(String deviceid) {
		Deviceid = deviceid;
	}

	public String getUpdatedDate() {
		return UpdatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		UpdatedDate = updatedDate;
	}

	public String getLUpdHideShow() {
		return LUpdHideShow;
	}

	public void setLUpdHideShow(String lUpdHideShow) {
		LUpdHideShow = lUpdHideShow;
	}

	public int getTimeOffSet() {
		return TimeOffSet;
	}

	public void setTimeOffSet(int timeOffSet) {
		TimeOffSet = timeOffSet;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getOperatingSystem() {
		return OperatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		OperatingSystem = operatingSystem;
	}

	public String getBrowser() {
		return Browser;
	}

	public void setBrowser(String browser) {
		Browser = browser;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getIPAddress() {
		return IPAddress;
	}

	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}

	}
