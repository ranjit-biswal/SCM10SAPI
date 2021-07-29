package resources;
//enum is special class in java which has collection of constants or  methods
public enum Routes {
	//10s 
	AddOneTimePaymentCC("/SCM_7_5_2-BaseChasePayment/api/Payment/Card"),
	AddOneTimePaymentBank("/SCM_7_5_2-BaseChasePayment/api/Payment/Bank"),
	LoginApi("/SCM_7_5_2-API/UserLogin/ValidateUserLogin"),
	RegistrationApi("/SCM_7_5_2-API/Registration/SetCustomerRegistration"),
	PaymentProfile("/SCM_7_5_2-API/Billing/SetInsertBillPayMode"),
	AddTokenizedPayment("/SCM_7_5_2-BaseChasePayment/api/Payment/TokenizedPayment"),
	CreateUpadateOutage("/SCM_7_5_2-API/Outagecsp/InsertOutage"),
	GetLoginID("/SCM_10.0_S-API/UserLogin/GetId"),
	ValidateUserLogin("/SCM_10.0_S-API/UserLogin/ValidateUserLogin"),
	SetConnectMeRequest("/SCM_10.0_S-API/ContactUs/SetConnectMeRequest");
	
	
	private String resource;
	
	Routes(String resource)
	{
		this.resource=resource;
	}
	
	public String getResource()
	{
		return resource;
	}	
}
