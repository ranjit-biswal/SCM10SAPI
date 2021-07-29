package stepDefinations.Billing;

import static io.restassured.RestAssured.given;

import com.sus.api.scm.Filepaths;

import apiCore.Request.Payments.OneTimePaymentAPI;
import apiCore.Request.Payments.PostLoginPayment;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

import java.util.List;

import org.testng.Assert;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.masterthought.cucumber.json.Hook;
import resources.Routes;
import apiCore.Util.TestRequestBuilder;
import stepDefinations.Hooks;
import utils.JsonUtil;
import utils.Utils;

public class StepDefinationOTPCC extends Utils {
	// RequestSpecification res;
	// ResponseSpecification resspec;
	// Response response;
	PostLoginPayment postLoginPayment =new PostLoginPayment();
	TestRequestBuilder data = new TestRequestBuilder();
	String transID = null;
	JsonUtil jsUtil_OTP_CC = new JsonUtil(Filepaths.sTestDataScpJsonFP, "oneTimePaymentCC.json");

	/**
	 * This method is used to build request for API
	 * 
	 * @param CardType
	 * @param ccAccountNum
	 * @param ccExp
	 * @param cardCVV
	 * @param amount
	 * @param serviceAccountNumber
	 * @throws Throwable
	 */
	@Given("^Add OneTimePayment Payload with \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
	public void add_OneTimePayment_Payload_with_and_and_and_and_and(String CardType, String ccAccountNum, String ccExp,
			String cardCVV, String amount, String serviceAccountNumber) throws Throwable {
		res = given().spec(requestSpecification())
				.body(data.oneTimePaymentCCPayload(ccAccountNum, ccExp, cardCVV, amount, serviceAccountNumber));
	}

	/**
	 * This Method is used to post the request
	 * 
	 * @param resource
	 * @param method
	 * @throws Throwable
	 */
	@When("^user calls \"([^\"]*)\" with \"([^\"]*)\" http request$")
	public void user_calls_with_http_request(String resource, String method) throws Throwable {
		Routes resourceAPI = Routes.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if (method.equalsIgnoreCase("POST"))
			response = res.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPI.getResource());
	}

	/**
	 * This method is to validate the status of the HTTP response
	 * 
	 * @param arg1
	 * @throws Throwable
	 */
	@Then("^the API call got success with status code \"([^\"]*)\"$")
	public void the_API_call_got_success_with_status_code(String arg1) throws Throwable {
		assertEquals(Integer.toString(response.getStatusCode()), arg1);
	}

	/**
	 * This method used to validate the response body with the expected value
	 * 
	 * @param keyValue
	 * @param Expectedvalue
	 * @throws Throwable
	 */
	@Then("^\"([^\"]*)\" in response body \"([^\"]*)\"$")
	public void in_response_body_is(String keyValue, String Expectedvalue) throws Throwable {
		Assert.assertEquals(Expectedvalue, getJsonPath(response, keyValue));
	}

	/**
	 * This method is used to validate the Response message of the request
	 * 
	 * @param keyValue
	 * @param Expectedvalue
	 * @throws Throwable
	 */
	@Then("^User validate the \"([^\"]*)\" from the Response Message \"([^\"]*)\"$")
	public void user_validate_the_from_the_Response_Message(String keyValue, String Expectedvalue) throws Throwable {
		Assert.assertEquals(Expectedvalue, getJsonPath(response, keyValue));
	}

	/**
	 * This method is used to validate value in DB
	 * 
	 * @param keyValue
	 * @param statusCode
	 * @param amt
	 * @throws Throwable
	 */
	@Then("^User validate \"([^\"]*)\" for \"([^\"]*)\" with DB details \"([^\"]*)\"$")
	public void user_validate_for_with_DB_details(String keyValue, String statusCode, String amt) throws Throwable {
		if (statusCode.equals("200")) {
			transID = getJsonPath(response, keyValue);
			Assert.assertEquals(amt, OneTimePaymentAPI.getPaymentFrom_ChaseDB(transID).get("Amount"));
		} else if (statusCode.equals("400")) {
			transID = getJsonPath(response, keyValue);
			Assert.assertEquals(transID, null);
		}
	}

	/**
	 * This Method is used to verify the card type from the database
	 * 
	 * @param cardType
	 * @throws Throwable
	 */
	@Then("^User validate the \"([^\"]*)\" in DataBase paymentRecons Table$")
	public void user_validate_the_in_DataBase_paymentRecons_Table(String cardType) throws Throwable {
		switch (cardType) {
		case "Credit Card - VISA":
			Assert.assertEquals(OneTimePaymentAPI.getPaymentFrom_ChaseDB(transID).get("PaymentMethodSubType"), "4");
			break;
		case "Credit Card - MASTER CARD":
			Assert.assertEquals(OneTimePaymentAPI.getPaymentFrom_ChaseDB(transID).get("PaymentMethodSubType"), "3");
			break;
		case "Credit Card - Discover":
			Assert.assertEquals(OneTimePaymentAPI.getPaymentFrom_ChaseDB(transID).get("PaymentMethodSubType"), "2");
			break;
		}
	}

	@Then("^User validate the \"([^\"]*)\" with the DataBase paymentRecons Table with column \"([^\"]*)\"$")
	public void user_validate_the_with_the_DataBase_paymentRecons_Table_with_column(String value,
			String dataBaseColName) throws Throwable {
		Assert.assertEquals(OneTimePaymentAPI.getPaymentFrom_ChaseDB(transID).get(dataBaseColName), value);
	}

	@Then("^User validate the customerName with the DataBase paymentRecons Table with column \"([^\"]*)\"$")
	public void user_validate_the_customerName_with_the_DataBase_paymentRecons_Table_with_column(String dataBaseColName)
			throws Throwable {
		Assert.assertEquals(OneTimePaymentAPI.getPaymentFrom_ChaseDB(transID).get(dataBaseColName),
				jsUtil_OTP_CC.getStringJsonValue("CustomerName"));
	}

	@Then("^User validate the \"([^\"]*)\" from Response with the DataBase paymentRecons Table with column \"([^\"]*)\"$")
	public void user_validate_the_from_Response_with_the_DataBase_paymentRecons_Table_with_column(String keyValue,
			String dataBaseNameCol) throws Throwable {
		Assert.assertEquals(OneTimePaymentAPI.getPaymentFrom_ChaseDB(transID).get(dataBaseNameCol),
				getJsonPath(response, keyValue));
	}

	@Given("^User add Credit Card for CardType$")
	public void user_add_Credit_Card_for_CardType(DataTable table) throws Throwable {
		List<List<String>> data = table.raw();
		Hooks.beforeScenario_DeleteCard();
		StepDefinationCreateCardProfile ccp = new StepDefinationCreateCardProfile();
		ccp.user_add_credit_card_with_details_to_profile_and_and_and(data.get(4).get(1), data.get(3).get(1),
				data.get(1).get(1), data.get(2).get(1));

	}

	@Given("^User Make payment with card type : \"([^\"]*)\" with amount : \"([^\"]*)\"$")
	public void user_Make_payment_with_card_type_with_amount(String cardType, String amt) throws Throwable {
		String custRefNo = OneTimePaymentAPI.getPaymentProfile_ChaseDB(data.getPaymentProfile().getCardName(),
				String.valueOf(data.getPaymentProfile().getUserID()), data.getPaymentProfile().getUtilityAccountNumber()).get("customerRefNum");
		String bodyForTokenizePayment = postLoginPayment.makeTokenizePayment(data.getPaymentProfile().getUtilityAccountNumber(),
				custRefNo,amt, "Card", String.valueOf(data.getPaymentProfile().getUserID()));
		res = given().spec(requestSpecification())
				.body(bodyForTokenizePayment);
	}

}
