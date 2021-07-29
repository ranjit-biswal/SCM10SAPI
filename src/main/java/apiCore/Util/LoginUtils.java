package apiCore.Util;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import apiCore.Request.Login.Login;
import apiCore.Request.Login.LoginAPIPayLoad;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.Routes;
import utils.DataBaseUtil;
import utils.SqlQuery;
import utils.Utils;
import apiCore.Request.Login.LoginPayload;

public class LoginUtils {
	public static RequestSpecification res;
	static Response response;


	public static HashMap<String, String> getUserLoginKeyToken() throws IOException {
		HashMap<String, String> userLoginResponseData = new HashMap<>();
		Routes resourceAPI = Routes.valueOf("GetLoginID");
		try {
			res = Login.generateRequestLoginID();
			response = Utils.getResponse(res, "post", resourceAPI.getResource());
			Assert.assertEquals(response.getStatusCode(), 200);
			userLoginResponseData.put("tokenId", Utils.getJsonPath(response, "result.Data.tokenId"));
			userLoginResponseData.put("key", Utils.getJsonPath(response, "result.Data.key"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userLoginResponseData;
	}

	public static HashMap<String, String> getUserIdSessionTokenValidateUserLogin(Map<String, String> headerMap,
			String userName, String passWord, String AccountNumber) throws IOException {
		HashMap<String, String> loginValidateResponseData = new HashMap<>();
		Routes resourceAPI = Routes.valueOf("ValidateUserLogin");
		TestRequestBuilder testDatabuild = new TestRequestBuilder();
		try {
			ResultSet resultSet = DataBaseUtil.getResultSet(SqlQuery.getDefaultAccount(userName));
			resultSet.next();
			String defaultAccount = resultSet.getString("UtilityAccountNumber");
			LoginPayload validateUserLogin = testDatabuild.getLoginPayLoad(userName, passWord, AccountNumber);
			res = Login.generateRequestValidateUserLoginID(headerMap, validateUserLogin);
			response = Utils.getResponse(res, "post", resourceAPI.getResource());
			Assert.assertEquals(response.getStatusCode(), 200);
			loginValidateResponseData.put("UserID", Utils.getJsonPath(response, "result.Data.Table[0].UserID"));
			loginValidateResponseData.put("SessionToken",
					Utils.getJsonPath(response, "result.Data.Table[0].SessionToken"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginValidateResponseData;

	}
}
