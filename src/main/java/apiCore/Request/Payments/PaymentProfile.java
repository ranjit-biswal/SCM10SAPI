package apiCore.Request.Payments;

public class PaymentProfile {
	private int UserID;
	private int AccountNumber;
	private int PayTypeId;
	private int IsBankAccount;
	private int Mode;
	private String CardName;
	private String CardType;
	private String CardNumber;
	private int ExpiryMonth;
	private int ExpiryYear;
	private String SecurityCode;
	private int PaymentMode;
	private String LanguageCode;
	private String UtilityAccountNumber;
	private String PaymentToken;
	private String CustomerRefNum;
	private int ChannelType;
	private String IP;

	public PaymentProfile(int UserID, int AccountNumber, int PayTypeId, int IsBankAccount, int Mode, String CardName,
			String CardType, String CardNumber, int ExpiryMonth, int ExpiryYear, String SecurityCode, int PaymentMode,
			String LanguageCode, String UtilityAccountNumber, String PaymentToken, String CustomerRefNum,
			int ChannelType, String IP) {
		this.UserID = UserID;
		this.AccountNumber = AccountNumber;
		this.PayTypeId = PayTypeId;
		this.IsBankAccount = IsBankAccount;
		this.Mode = Mode;
		this.CardName = CardName;
		this.CardType = CardType;
		this.CardNumber = CardNumber;
		this.ExpiryMonth = ExpiryMonth;
		this.ExpiryYear = ExpiryYear;
		this.SecurityCode = SecurityCode;
		this.PaymentMode = PaymentMode;
		this.LanguageCode = LanguageCode;
		this.UtilityAccountNumber = UtilityAccountNumber;
		this.PaymentToken = PaymentToken;
		this.CustomerRefNum = CustomerRefNum;
		this.ChannelType = ChannelType;
		this.IP = IP;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int UserID) {
		this.UserID = UserID;
	}

	public int getAccountNumber() {
		return AccountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		AccountNumber = accountNumber;
	}

	public int getPayTypeId() {
		return PayTypeId;
	}

	public void setPayTypeId(int payTypeId) {
		PayTypeId = payTypeId;
	}

	public int getIsBankAccount() {
		return IsBankAccount;
	}

	public void setIsBankAccount(int isBankAccount) {
		IsBankAccount = isBankAccount;
	}

	public int getMode() {
		return Mode;
	}

	public void setMode(int mode) {
		Mode = mode;
	}

	public String getCardName() {
		return CardName;
	}

	public void setCardName(String cardName) {
		CardName = cardName;
	}

	public String getCardType() {
		return CardType;
	}

	public void setCardType(String cardType) {
		CardType = cardType;
	}

	public String getCardNumber() {
		return CardNumber;
	}

	public void setCardNumber(String cardNumber) {
		CardNumber = cardNumber;
	}

	public int getExpiryMonth() {
		return ExpiryMonth;
	}

	public void setExpiryMonth(int expiryMonth) {
		ExpiryMonth = expiryMonth;
	}

	public int getExpiryYear() {
		return ExpiryYear;
	}

	public void setExpiryYear(int expiryYear) {
		ExpiryYear = expiryYear;
	}

	public String getSecurityCode() {
		return SecurityCode;
	}

	public void setSecurityCode(String securityCode) {
		SecurityCode = securityCode;
	}

	public int getPaymentMode() {
		return PaymentMode;
	}

	public void setPaymentMode(int paymentMode) {
		PaymentMode = paymentMode;
	}

	public String getLanguageCode() {
		return LanguageCode;
	}

	public void setLanguageCode(String languageCode) {
		LanguageCode = languageCode;
	}

	public String getUtilityAccountNumber() {
		return UtilityAccountNumber;
	}

	public void setUtilityAccountNumber(String utilityAccountNumber) {
		UtilityAccountNumber = utilityAccountNumber;
	}

	public String getPaymentToken() {
		return PaymentToken;
	}

	public void setPaymentToken(String paymentToken) {
		PaymentToken = paymentToken;
	}

	public String getCustomerRefNum() {
		return CustomerRefNum;
	}

	public void setCustomerRefNum(String customerRefNum) {
		CustomerRefNum = customerRefNum;
	}

	public int getChannelType() {
		return ChannelType;
	}

	public void setChannelType(int channelType) {
		ChannelType = channelType;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}
}
