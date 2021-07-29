package com.sus.api.scm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.ExcelUtil;
import utils.ReusableMethods;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import data.Resources;



public class LoginTest extends BaseSetUp {

	private static Logger Log = (Logger) LogManager.getLogger(LoginTest.class.getName());

/*	String EN_InvalidLoginMessage = proptext.getProperty("EN_InvalidLoginMessage");
	String FR_InvalidLoginMessage = proptext.getProperty("FR_InvalidLoginMessage");*/

	@Test
	public void verifyUserLogin() throws IOException {

		/*
		 * Response res = given().header("Content-Type",
		 * "application/json").body(getLoginData("verifyUserLogin", "Login"))
		 * .log().all().when().post(Resource.getLoginResource()).then().
		 * assertThat().statusCode(200).and()
		 * .contentType(ContentType.JSON).log().all().and().extract().response()
		 * ;
		 * 
		 * Log.info("Response: " + res.asString());
		 */

		getLoggedIn("verifyUserLogin", 200);

	}

	@Test
	public void verifyUserLoginWrongPassword() throws IOException {

		Response res = given().header("Content-Type", "application/json")
				.body(getLoginData("verifyUserLoginWrongPassword", "Login")).when().post(Resources.getLoginResource())
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON).log().all().and().extract()
				.response();

		Log.info("Response: " + res.asString());

	}

	@Test
	public void verifyUserLoginWrongDetails() throws IOException {

		Response res = getLoggedIn("verifyUserLoginWrongDetails", 200);
		JsonPath jsPath = ReusableMethods.rawToJson(res);
		String sMessage = jsPath.get("result.Message");
		assertTrue(sMessage.equalsIgnoreCase(proptext.getProperty("EN_InvalidLoginMessage")));

	}

	@Test
	public void verifyUserLoginBlankUserId() throws IOException {

		Response res = getLoggedIn("verifyUserLoginBlankUserId", 200);
		JsonPath jsPath = ReusableMethods.rawToJson(res);
		String sMessage = jsPath.get("result.Message");
		assertTrue(sMessage.equalsIgnoreCase(proptext.getProperty("EN_InvalidLoginMessage")));

	}

	@Test
	public void verifyUserLoginBlankPassword() throws IOException {

		Response res = getLoggedIn("verifyUserLoginBlankPassword", 200);
		JsonPath jsPath = ReusableMethods.rawToJson(res);
		String sMessage = jsPath.get("result.Message");
		assertTrue(sMessage.equalsIgnoreCase(proptext.getProperty("EN_InvalidLoginMessage")));

	}

	@Test
	public void verifyUserLoginBlankCredentials() throws IOException {

		Response res = getLoggedIn("verifyUserLoginBlankCredentials", 200);
		JsonPath jsPath = ReusableMethods.rawToJson(res);
		String sMessage = jsPath.get("result.Message");
		assertTrue(sMessage.equalsIgnoreCase(proptext.getProperty("EN_InvalidLoginMessage")));

	}

	public Response getLoggedIn(String testcase, int statuscode) throws IOException {

		Response res = given().header("Content-Type", "application/json").body(getLoginData(testcase, "Login")).log()
				.all().when().post(Resources.getLoginResource()).then().assertThat().statusCode(statuscode).and()
				.contentType(ContentType.JSON).log().all().and().extract().response();

		return res;

	}

	public HashMap<String, Object> getLoginData(String testCase, String Module) throws IOException {

		ExcelUtil excelReader = new ExcelUtil();
		ArrayList<String> data = excelReader.getData(testCase, Module);

		HashMap<String, Object> jsonAsMap = new HashMap<>();
		jsonAsMap.put("UserId", data.get(1));
		jsonAsMap.put("password", data.get(2));
		jsonAsMap.put("IPAddress", data.get(3));
		jsonAsMap.put("LanguageCode", data.get(4));
		jsonAsMap.put("ExternalLoginId", data.get(5));
		jsonAsMap.put("LoginMode", data.get(6));
		jsonAsMap.put("IsCSRUser", data.get(7));
		jsonAsMap.put("PushToken", data.get(8));
		jsonAsMap.put("Deviceid", data.get(9));
		jsonAsMap.put("UtilityAccountNumber", data.get(10));
		jsonAsMap.put("UpdatedDate", data.get(11));
		jsonAsMap.put("LUpdHideShow", data.get(12));
		jsonAsMap.put("TimeOffSet", data.get(13));
		return jsonAsMap;

	}

}
