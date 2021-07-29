package apiCore.Request.Payments;

public class OneTimePaymentCreditCard {

	private String ccAccountNum;
	private String ccExp;
	private String cardCVV;
	private String paymentMethodType;
	private String amount;
	private String serviceAccountNumber;
	private String CustomerName;
	private String FirstName;
	private String LastName;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zip;
	private String countryCode;
	private String email;
	private String phone;
	private String AccounType;
	private String AccountUtilityID;
	private String ChannelType;
	private String UserId;

	/**
	 * Constructor for One Time payment CC
	 * 
	 * @param ccAccountNum
	 * @param ccExp
	 * @param cardCVV
	 * @param paymentMethodType
	 * @param amount
	 * @param serviceAccountNumber
	 * @param CustomerName
	 * @param FirstName
	 * @param LastName
	 * @param addressLine1
	 * @param addressLine2
	 * @param city
	 * @param state
	 * @param zip
	 * @param countryCode
	 * @param email
	 * @param phone
	 * @param AccounType
	 * @param AccountUtilityID
	 * @param ChannelType
	 * @param UserId
	 */
	public OneTimePaymentCreditCard(String ccAccountNum, String ccExp, String cardCVV, String paymentMethodType,
			String amount, String serviceAccountNumber, String CustomerName, String FirstName, String LastName,
			String addressLine1, String addressLine2, String city, String state, String zip, String countryCode,
			String email, String phone, String AccounType, String AccountUtilityID, String ChannelType, String UserId) {
		this.ccAccountNum = ccAccountNum;
		this.ccExp = ccExp;
		this.cardCVV = cardCVV;
		this.paymentMethodType = paymentMethodType;
		this.amount = amount;
		this.serviceAccountNumber = serviceAccountNumber;
		this.CustomerName = CustomerName;
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.countryCode = countryCode;
		this.email = email;
		this.phone = phone;
		this.AccounType = AccounType;
		this.AccountUtilityID = AccountUtilityID;
		this.ChannelType = ChannelType;
		this.UserId = UserId;
	}

	public String getCcAccountNum() {
		return ccAccountNum;
	}

	public void setCcAccountNum(String ccAccountNum) {
		this.ccAccountNum = ccAccountNum;
	}

	public String getCcExp() {
		return ccExp;
	}

	public void setCcExp(String ccExp) {
		this.ccExp = ccExp;
	}

	public String getCardCVV() {
		return cardCVV;
	}

	public void setCardCVV(String cardCVV) {
		this.cardCVV = cardCVV;
	}

	public String getPaymentMethodType() {
		return paymentMethodType;
	}

	public void setPaymentMethodType(String paymentMethodType) {
		this.paymentMethodType = paymentMethodType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getServiceAccountNumber() {
		return serviceAccountNumber;
	}

	public void setServiceAccountNumber(String serviceAccountNumber) {
		this.serviceAccountNumber = serviceAccountNumber;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAccounType() {
		return AccounType;
	}

	public void setAccounType(String accounType) {
		AccounType = accounType;
	}

	public String getAccountUtilityID() {
		return AccountUtilityID;
	}

	public void setAccountUtilityID(String accountUtilityID) {
		AccountUtilityID = accountUtilityID;
	}

	public String getChannelType() {
		return ChannelType;
	}

	public void setChannelType(String channelType) {
		ChannelType = channelType;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

}
