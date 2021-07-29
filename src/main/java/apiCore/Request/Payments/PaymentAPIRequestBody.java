package apiCore.Request.Payments;

public class PaymentAPIRequestBody {

	public static String getTokenizePayment(String serviceAcc, String refNo, String amount, int paymentMethod,
			String userID) {
		String body = "{\r\n  \"serviceAccountNumber\": \"" + serviceAcc + "\",\r\n  \"customerRefNum\": \"" + refNo
				+ "\",\r\n  \"amount\": \"" + amount
				+ "\",\r\n  \"channeltype\": \"Android\",\r\n  \"paymentMethodType\": " + paymentMethod
				+ ",\r\n  \"customerName\": \"Marsh & Parsons\",\r\n  \"FirstName\": \"Johns@#$&*{}[]\",\r\n  \"LastName\": \"LeeStone@#$%&*(){]\",\r\n  \"addressLine1\": \"Apartment 2@#$!&*{]>\",\r\n  \"addressLine2\": \"1 Northeastern Blvd@#$&*(){}]\",\r\n  \"city\": \"San JosÃ©\",\r\n  \"state\": \"CA\",\r\n  \"zip\": \"90001\",\r\n  \"countryCode\": \"US\",\r\n  \"ChannelType\":\"Android\",\r\n  \"AccounType\":\"Residential\",\r\n  \"AccountUtilityID\":\"1\",\r\n  \"UserId\":\""
				+ userID + "\"\r\n}";
		return body;
	}

	/**
	 * This method is used to delete the payment profile card
	 * 
	 * @param userID
	 * @param accountNumber
	 * @param cardNumber
	 * @param userProfileID
	 * @param utilityAccountNumber
	 * @param customerRefNumber
	 * @return
	 */
	public static String getDeletePaymentProfileCard(String userID, String accountNumber, String cardNumber,
			String userProfileID, String utilityAccountNumber, String customerRefNumber) {
		String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);
		String body = "{\r\n    \"UserID\": " + userID + ",\r\n    \"AccountNumber\": " + accountNumber
				+ ",\r\n    \"PayTypeId\": 0,\r\n    \"IsBankAccount\": 0,\r\n    \"Mode\": 2,\r\n    \"CardName\": null,\r\n    \"CardType\": null,\r\n    \"CardNumber\": \""
				+ "************" + lastFourDigits
				+ "\",\r\n    \"ExpiryMonth\": 0,\r\n    \"ExpiryYear\": 0,\r\n    \"SecurityCode\": null,\r\n    \"BankName\": null,\r\n    \"BankAccount\": null,\r\n    \"AchType\": null,\r\n    \"BankRouting\": null,\r\n    \"AccountHolderName\": null,\r\n    \"MakePrimary\": 0,\r\n    \"UserProfileId\": \""
				+ userProfileID
				+ "\",\r\n    \"PaymentMode\": 2,\r\n    \"LanguageCode\": \"EN\",\r\n    \"UtilityAccountNumber\": \""
				+ utilityAccountNumber + "\",\r\n    \"PaymentToken\": \"\",\r\n    \"CustomerRefNum\": \""
				+ customerRefNumber + "\",\r\n    \"ChannelType\": 0,\r\n    \"IP\": null\r\n}";
		return body;

	}

}
