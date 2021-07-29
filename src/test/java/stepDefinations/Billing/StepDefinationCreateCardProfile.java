package stepDefinations.Billing;

import static io.restassured.RestAssured.given;
import com.sus.api.scm.Filepaths;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.Assert;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.Routes;
import utils.DataBaseUtil;
import utils.JsonUtil;
import utils.SqlQuery;
import utils.Utils;
import apiCore.Request.Payments.PaymentProfile;
import apiCore.Util.TestRequestBuilder;

public class StepDefinationCreateCardProfile extends Utils {
	//RequestSpecification res;
	//ResponseSpecification resspec;
	//Response response;
	TestRequestBuilder data = new TestRequestBuilder();
	public static ResultSet rs;
	JsonUtil jsUtil = new JsonUtil(Filepaths.sTestDataScpJsonFP, "createProfileCC.json");

	@Given("^User add credit card with details to profile \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
	public void user_add_credit_card_with_details_to_profile_and_and_and(String utilityAccNum, String userName,
			String cardType, String cardNumber) throws Throwable {
		rs = DataBaseUtil.getResultSet(SqlQuery.getUserIDFromUserName(userName));
		rs.next();
		String userID = rs.getString("UserId");
		rs = DataBaseUtil.getResultSet(SqlQuery.getAccountNumberFromUtilityAccountNumber(utilityAccNum));
		rs.next();
		String accNumber = rs.getString("AccountNumber");
		res = given().spec(requestSpecification())
				.body(data.getPaymentProfile(accNumber, utilityAccNum, userID, cardNumber, cardType));
	}

	public void deleteCardProfile(String userID) throws Exception {
		ResultSet rs1 = DataBaseUtil.getResultSetPaymentDatabaseChase(SqlQuery.getCustRefNoCount(userID));
		rs1.next();
		String countRefNum = rs1.getString("custRefNumber");
		if (!countRefNum.equals("0")) {
			try {
				rs = DataBaseUtil.getResultSetPaymentDatabaseChase(SqlQuery.getCustRefnumber(userID));
				while (rs.next()) {
					PaymentProfile pp = new PaymentProfile(Integer.parseInt(jsUtil.getStringJsonValue("UserID")),
							Integer.parseInt(jsUtil.getStringJsonValue("AccountNumber")),
							Integer.parseInt(jsUtil.getStringJsonValue("PayTypeId")),
							Integer.parseInt(jsUtil.getStringJsonValue("IsBankAccount")),
							Integer.parseInt(jsUtil.getStringJsonValue("Mode")), jsUtil.getStringJsonValue("CardName"),
							jsUtil.getStringJsonValue("CardType"), jsUtil.getStringJsonValue("CardNumber"),
							Integer.parseInt(jsUtil.getStringJsonValue("ExpiryMonth")),
							Integer.parseInt(jsUtil.getStringJsonValue("ExpiryYear")),
							jsUtil.getStringJsonValue("SecurityCode"),
							Integer.parseInt(jsUtil.getStringJsonValue("PaymentMode")),
							jsUtil.getStringJsonValue("LanguageCode"),
							jsUtil.getStringJsonValue("UtilityAccountNumber"),
							jsUtil.getStringJsonValue("PaymentToken"), jsUtil.getStringJsonValue("CustomerRefNum"),
							Integer.parseInt(jsUtil.getStringJsonValue("ChannelType")),
							jsUtil.getStringJsonValue("IP"));
					pp.setMode(2);
					pp.setCustomerRefNum(rs.getString("CustomerRefNum"));
					Routes resourceAPI = Routes.valueOf("PaymentProfile");

					res = given().spec(requestSpecification()).body(pp);
					response = res.when().post(resourceAPI.getResource());
					assertEquals(response.getStatusCode(), 200);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@When("^user calls \"([^\"]*)\"  and \"([^\"]*)\" http request$")
	public void user_calls_and_http_request(String resource, String method) throws Throwable {
		Routes resourceAPI = Routes.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if (method.equalsIgnoreCase("POST"))
			response = res.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPI.getResource());
	}

	@Then("^the API call get with status codes \"([^\"]*)\"$")
	public void the_API_call_get_with_status_codes(String arg1) throws Throwable {
		assertEquals(Integer.toString(response.getStatusCode()), arg1);
	}
	
	@Then("^User validate the Message after adding card to the payment profile of the user\"([^\"]*)\"$")
	public void user_validate_the_Message_after_adding_card_to_the_payment_profile_of_the_user(String expectedMsg) throws Throwable {
		Assert.assertEquals(expectedMsg, getJsonPath(response, "result.Message"));
	}
}
