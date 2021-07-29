package apiCore.Request.Payments;

public class OneTimePaymentBank {

	private String BankAccountNum;
	private String accountNumber;
	private String RoutingNumber;
	private String amount;
	private String paymentMethodType;
	private String serviceAccountNumber;
	private String customerName;
	private String FirstName;
	private String LastName;
	private String AddressLine1;
	private String AddressLine2;
	private String City;
	private String State;
	private String Zip;
	private String countryCode;
	private String BankAccountType;
	private String email;
	private String phone;
	private String AccounType;
	private String AccountUtilityID;
	private String ChannelType;
	private String UserId;
	private String BankName;

	/**
	 * Constructor for one time payment bank
	 * 
	 */

	public OneTimePaymentBank(String accountNumber,String BankAccountNum, String RoutingNumber, String amount, String paymentMethodType,
			String serviceAccountNumber, String customerName, String FirstName, String LastName, String AddressLine1,
			String AddressLine2, String City, String State, String Zip, String countryCode, String BankAccountType,
			String email, String phone, String AccounType, String AccountUtilityID, String ChannelType, String UserId,
			String BankName) 
	{
		this.accountNumber=accountNumber;
		this.BankAccountNum = BankAccountNum;
		this.RoutingNumber = RoutingNumber;
		this.amount = amount;
		this.paymentMethodType = paymentMethodType;
		this.serviceAccountNumber = serviceAccountNumber;
		this.customerName = customerName;
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.AddressLine1 = AddressLine1;
		this.AddressLine2 = AddressLine2;
		this.City = City;
		this.State = State;
		this.Zip = Zip;
		this.countryCode = countryCode;
		this.BankAccountType = BankAccountType;
		this.email = email;
		this.phone = phone;
		this.AccounType = AccounType;
		this.AccountUtilityID = AccountUtilityID;
		this.ChannelType = ChannelType;
		this.UserId = UserId;
		this.BankName = BankName;

	}

	public String getBankAccountNum() {
		return BankAccountNum;
	}

	public void setBankAccountNum(String bankAccountNum) {
		BankAccountNum = bankAccountNum;
	}

	public String getRoutingNumber() {
		return RoutingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		RoutingNumber = routingNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPaymentMethodType() {
		return paymentMethodType;
	}

	public void setPaymentMethodType(String paymentMethodType) {
		this.paymentMethodType = paymentMethodType;
	}

	public String getServiceAccountNumber() {
		return serviceAccountNumber;
	}

	public void setServiceAccountNumber(String serviceAccountNumber) {
		this.serviceAccountNumber = serviceAccountNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
		return AddressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		AddressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return AddressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		AddressLine2 = addressLine2;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getZip() {
		return Zip;
	}

	public void setZip(String zip) {
		Zip = zip;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getBankAccountType() {
		return BankAccountType;
	}

	public void setBankAccountType(String bankAccountType) {
		BankAccountType = bankAccountType;
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

	public String getBankName() {
		return BankName;
	}

	public void setBankName(String bankName) {
		BankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
