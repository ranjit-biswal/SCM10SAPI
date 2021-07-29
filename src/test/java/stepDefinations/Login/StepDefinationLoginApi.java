package stepDefinations.Login;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import resources.Routes;
import apiCore.Util.LoginUtils;
import apiCore.Util.TestRequestBuilder;
import utils.Context;
import utils.TestContext;
import utils.Utils;

public class StepDefinationLoginApi extends Utils {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestRequestBuilder data = new TestRequestBuilder();
	TestContext testContext;

	public StepDefinationLoginApi(TestContext context) {
		testContext = context;
	}

/*	@Given("^User enters Valid LoginID \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" get the Login Token$")
	public void user_enters_Valid_LoginID_and_and_get_the_Login_Token(String UserName, String PassWord,
			String AccountNumber) throws Throwable {
		res = given().spec(requestSpecification()).body(data.getLoginPayLoad(UserName, PassWord, AccountNumber));
	}*/

	@Given("^User enters Valid LoginID \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and get the get the Login Token$")
	public void user_enters_Valid_LoginID_and_and_and_get_the_get_the_Login_Token(String UserName, String PassWord,
			String AccountNumber) throws Throwable {
//		res = given().spec(requestSpecification()).body(data.getLoginPayLoad(UserName, PassWord, AccountNumber));
		
		HashMap<String, String> userLoginKeyToken = LoginUtils.getUserLoginKeyToken();
		String loginToken = userLoginKeyToken.get("tokenId");
		String loginKey = userLoginKeyToken.get("key");
		String tokenUsedforLogin = "Basic" + " " + Utils.getConversionStringToBase64(loginKey + ":" + loginToken);
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", tokenUsedforLogin);
		headerMap.put("SourceType", "0");
		HashMap<String, String> loginValidateTokenAndUserID = LoginUtils
				.getUserIdSessionTokenValidateUserLogin(headerMap, UserName, PassWord, AccountNumber);
		String userID = loginValidateTokenAndUserID.get("UserID");
		String sessionToken = loginValidateTokenAndUserID.get("SessionToken");
		String finalToken = "Basic" + " " + getConversionStringToBase64(userID + ":" + sessionToken);
		System.out.println(finalToken);
		testContext.scenarioContext.setContext(Context.Final_Login_Token, finalToken);
	}

	@When("^user call \"([^\"]*)\" with \"([^\"]*)\"  http request$")
	public void user_call_with_http_request(String resource, String method) throws Throwable {
		Routes resourceAPI = Routes.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if (method.equalsIgnoreCase("POST"))
			response = res.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPI.getResource());
	}

	@When("^user validate API call got success with status code \"([^\"]*)\"$")
	public void user_validate_API_call_got_success_with_status_code(String int1) throws Throwable {
		assertEquals(Integer.toString(response.getStatusCode()), int1);
	}

	@Then("^user validate \"([^\"]*)\" the \"([^\"]*)\" from the response$")
	public void user_validate_the_from_the_response(String keyValue, String Expectedvalue) throws Throwable {
		Assert.assertEquals(Expectedvalue, getJsonPath(response, keyValue));
	}
/*
	@Given("^User enters Valid LoginID \"([^\"]*)\" and \"([^\"]*)\" and get the get the Login Token$")
	public void user_enters_Valid_LoginID_and_and_get_the_get_the_Login_Token(String userName, String passWord)
			throws Throwable {
		HashMap<String, String> userLoginKeyToken = LoginUtils.getUserLoginKeyToken();
		String loginToken = userLoginKeyToken.get("tokenId");
		String loginKey = userLoginKeyToken.get("key");
		String tokenUsedforLogin = "Basic" + " " + Utils.getConversionStringToBase64(loginKey + ":" + loginToken);
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", tokenUsedforLogin);
		headerMap.put("SourceType", "0");
		HashMap<String, String> loginValidateTokenAndUserID = LoginUtils
				.getUserIdSessionTokenValidateUserLogin(headerMap, userName, passWord);
		String userID = loginValidateTokenAndUserID.get("UserID");
		String sessionToken = loginValidateTokenAndUserID.get("SessionToken");
		String finalToken = "Basic" + " " + getConversionStringToBase64(userID + ":" + sessionToken);
		System.out.println(finalToken);
		testContext.scenarioContext.setContext(Context.Final_Login_Token, finalToken);
	}*/
}
