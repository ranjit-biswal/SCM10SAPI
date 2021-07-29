package apiCore.Util;

import com.sus.api.scm.Filepaths;

import apiCore.Request.Login.LoginPayload;
import apiCore.Request.Payments.OneTimePaymentBank;
import apiCore.Request.Payments.OneTimePaymentCreditCard;
import apiCore.Request.Payments.PaymentProfile;
import apiCore.Request.Registration.Registration;
import utils.JsonUtil;

public class TestRequestBuilder {
	String fileBank = "OTPBank.json";
	String fileLogin = "Login.json";
	String fileOTP_CC = "oneTimePaymentCC.json";
	String filePaymentProfile_CC = "createProfileCC.json";
	String fileRegistration = "Registration.json";
	public PaymentProfile pp_cc;

	JsonUtil jsUtil_Bank = new JsonUtil(Filepaths.sTestDataSCPJsonFP, fileBank);
	JsonUtil jsUtil_login = new JsonUtil(Filepaths.sTestDataSCPJsonFP, fileLogin);
	JsonUtil jsUtil_OTP_CC = new JsonUtil(Filepaths.sTestDataSCPJsonFP, fileOTP_CC);
	JsonUtil jsUtil_paymentProfile_CC = new JsonUtil(Filepaths.sTestDataSCPJsonFP, filePaymentProfile_CC);
	JsonUtil jsUtil_Registration = new JsonUtil(Filepaths.sTestDataSCPJsonFP, fileRegistration);

	public OneTimePaymentCreditCard oneTimePaymentCCPayload(String ccAccountNum, String ccExp, String cardCVV,
			String amount, String serviceAccountNumber) {
		OneTimePaymentCreditCard otpaymCC = new OneTimePaymentCreditCard(ccAccountNum, ccExp, cardCVV, "1", amount,
				serviceAccountNumber, jsUtil_OTP_CC.getStringJsonValue("CustomerName"), "Dell", "Night",
				"4 @Northeastern", "Blvd Salem", "Salem", "NH", jsUtil_OTP_CC.getStringJsonValue("zip"),
				jsUtil_OTP_CC.getStringJsonValue("countryCode"), jsUtil_OTP_CC.getStringJsonValue("email"),
				jsUtil_OTP_CC.getStringJsonValue("phone"), "Residential", "1", "Mobile", "781");
		return otpaymCC;
	}

	public OneTimePaymentBank oneTimePaymentBankPayLoad(String amt) {
		OneTimePaymentBank otpayBank = new OneTimePaymentBank(jsUtil_Bank.getStringJsonValue("accountNumber"),
				jsUtil_Bank.getStringJsonValue("BankAccountNum"), jsUtil_Bank.getStringJsonValue("RoutingNumber"), amt,
				jsUtil_Bank.getStringJsonValue("paymentMethodType"),
				jsUtil_Bank.getStringJsonValue("serviceAccountNumber"), jsUtil_Bank.getStringJsonValue("customerName"),
				jsUtil_Bank.getStringJsonValue("FirstName"), jsUtil_Bank.getStringJsonValue("LastName"),
				jsUtil_Bank.getStringJsonValue("AddressLine1"), jsUtil_Bank.getStringJsonValue("AddressLine2"),
				jsUtil_Bank.getStringJsonValue("City"), jsUtil_Bank.getStringJsonValue("State"),
				jsUtil_Bank.getStringJsonValue("Zip"), jsUtil_Bank.getStringJsonValue("countryCode"),
				jsUtil_Bank.getStringJsonValue("BankAccountType"), jsUtil_Bank.getStringJsonValue("email"),
				jsUtil_Bank.getStringJsonValue("phone"), jsUtil_Bank.getStringJsonValue("AccounType"),
				jsUtil_Bank.getStringJsonValue("AccountUtilityID"), jsUtil_Bank.getStringJsonValue("ChannelType"),
				jsUtil_Bank.getStringJsonValue("UserId"), jsUtil_Bank.getStringJsonValue("BankName"));
		return otpayBank;
	}

	public LoginPayload getLoginPayLoad(String sUsername, String sPassword, String sAccountNumber) {
		LoginPayload login = new LoginPayload(sUsername, sPassword, sAccountNumber,
				jsUtil_login.getStringJsonValue("LanguageCode"), jsUtil_login.getStringJsonValue("ExternalLoginId"),
				Integer.parseInt(jsUtil_login.getStringJsonValue("LoginMode")),
				Integer.parseInt(jsUtil_login.getStringJsonValue("IsCSRUser")),
				jsUtil_login.getStringJsonValue("PushToken"), jsUtil_login.getStringJsonValue("Deviceid"),
				jsUtil_login.getStringJsonValue("UpdatedDate"), jsUtil_login.getStringJsonValue("LUpdHideShow"),
				Integer.parseInt(jsUtil_login.getStringJsonValue("TimeOffSet")),
				jsUtil_login.getStringJsonValue("deviceType"), jsUtil_login.getStringJsonValue("OperatingSystem"),
				jsUtil_login.getStringJsonValue("Browser"), jsUtil_login.getStringJsonValue("Country"),
				jsUtil_login.getStringJsonValue("IPAddress"));
		return login;
	}

	public PaymentProfile getPaymentProfile(String accNumber, String utilityNumber, String userID, String cardNumber,
			String cardType) {
		pp_cc = new PaymentProfile(Integer.parseInt(userID), Integer.parseInt(accNumber),
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("PayTypeId")),
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("IsBankAccount")),
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("Mode")),
				jsUtil_paymentProfile_CC.getStringJsonValue("CardName"), cardType, cardNumber,
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("ExpiryMonth")),
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("ExpiryYear")),
				jsUtil_paymentProfile_CC.getStringJsonValue("SecurityCode"),
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("PaymentMode")),
				jsUtil_paymentProfile_CC.getStringJsonValue("LanguageCode"), utilityNumber,
				jsUtil_paymentProfile_CC.getStringJsonValue("PaymentToken"),
				jsUtil_paymentProfile_CC.getStringJsonValue("CustomerRefNum"),
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("ChannelType")),
				jsUtil_paymentProfile_CC.getStringJsonValue("IP"));
		return pp_cc;
	}

	public PaymentProfile getPaymentProfile() {
		pp_cc = new PaymentProfile(Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("UserID")),
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("AccountNumber")),
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("PayTypeId")),
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("IsBankAccount")),
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("Mode")),
				jsUtil_paymentProfile_CC.getStringJsonValue("CardName"),
				jsUtil_paymentProfile_CC.getStringJsonValue("CardType"),
				jsUtil_paymentProfile_CC.getStringJsonValue("CardNumber"),
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("ExpiryMonth")),
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("ExpiryYear")),
				jsUtil_paymentProfile_CC.getStringJsonValue("SecurityCode"),
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("PaymentMode")),
				jsUtil_paymentProfile_CC.getStringJsonValue("LanguageCode"),
				jsUtil_paymentProfile_CC.getStringJsonValue("UtilityAccountNumber"),
				jsUtil_paymentProfile_CC.getStringJsonValue("PaymentToken"),
				jsUtil_paymentProfile_CC.getStringJsonValue("CustomerRefNum"),
				Integer.parseInt(jsUtil_paymentProfile_CC.getStringJsonValue("ChannelType")),
				jsUtil_paymentProfile_CC.getStringJsonValue("IP"));
		return pp_cc;
	}

	public Registration getRegistrationPayLoad(String firstName, String lastName, String emailId, String mobileNumber,
			String postalCode, String userName, String password, String confirmPassword, String meterNumber,
			String sSNNumber, String utilityAccountNumber, String streetNumber, String drivingLicence,
			String customerType, String contactType, String notificationPreference, String paperlessBill) {

		Registration registration = new Registration(jsUtil_Registration.getStringJsonValue("AccountNumber"), firstName,
				jsUtil_Registration.getStringJsonValue("MiddleName"), lastName, emailId,
				jsUtil_Registration.getStringJsonValue("DateOfBirth"), mobileNumber,
				jsUtil_Registration.getStringJsonValue("HomePhone"), jsUtil_Registration.getStringJsonValue("Address1"),
				jsUtil_Registration.getStringJsonValue("Address2"),
				jsUtil_Registration.getStringJsonValue("IsPasswordRequest"),
				jsUtil_Registration.getStringJsonValue("CityId"), postalCode, userName, password, confirmPassword,
				jsUtil_Registration.getStringJsonValue("SecurityQuestionId"),
				jsUtil_Registration.getStringJsonValue("HintAns"), meterNumber,
				jsUtil_Registration.getStringJsonValue("EmailNotify"),
				jsUtil_Registration.getStringJsonValue("BudgetNotify"),
				jsUtil_Registration.getStringJsonValue("BillingAddress"),
				jsUtil_Registration.getStringJsonValue("ServiceAccount"),
				jsUtil_Registration.getStringJsonValue("CustomerUtilityId"),
				jsUtil_Registration.getStringJsonValue("AddressPowerPlanID"),
				jsUtil_Registration.getStringJsonValue("EVPowerPlanID"),
				jsUtil_Registration.getStringJsonValue("DefaultUsageView"),
				jsUtil_Registration.getStringJsonValue("DefaultpaymentType"),
				jsUtil_Registration.getStringJsonValue("UtilityID"), jsUtil_Registration.getStringJsonValue("Latitude"),
				jsUtil_Registration.getStringJsonValue("Longitude"),
				jsUtil_Registration.getStringJsonValue("SecurityQuestionId2"),
				jsUtil_Registration.getStringJsonValue("HintsAns2"), sSNNumber, utilityAccountNumber,
				jsUtil_Registration.getStringJsonValue("IsVerfication"),
				jsUtil_Registration.getStringJsonValue("SessionCode"),
				jsUtil_Registration.getStringJsonValue("AlternateEmailID"),
				jsUtil_Registration.getStringJsonValue("BPNumber"), streetNumber, drivingLicence,
				jsUtil_Registration.getStringJsonValue("IPAddress"),
				jsUtil_Registration.getStringJsonValue("IsCSRUser"),
				jsUtil_Registration.getStringJsonValue("LanguageCode"),
				jsUtil_Registration.getStringJsonValue("CustomerNo"), customerType,
				jsUtil_Registration.getStringJsonValue("ExternalLoginId"),
				jsUtil_Registration.getStringJsonValue("LoginMode"), contactType, notificationPreference, paperlessBill,
				jsUtil_Registration.getStringJsonValue("OSType"));
		return registration;
	}

}
