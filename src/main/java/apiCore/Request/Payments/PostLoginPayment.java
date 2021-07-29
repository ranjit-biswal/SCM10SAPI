package apiCore.Request.Payments;

public class PostLoginPayment
{
	public String makeTokenizePayment(String serviceAcc, String refNo, String amount, String paymentMethod,
			String userID)  {
		String body = null;
		switch (paymentMethod) {
		case "Card":
			body=PaymentAPIRequestBody.getTokenizePayment(serviceAcc, refNo, amount, 1, userID);
			break;
		case "Bank":
			body=PaymentAPIRequestBody.getTokenizePayment(serviceAcc, refNo, amount, 2, userID);
			break;
		}
		return body;
}
}
