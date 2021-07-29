package stepDefinations.Outage;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;

import com.sus.api.scm.Filepaths;
import apiCore.Request.Outage.OutageAPI;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.Routes;
import apiCore.Util.TestRequestBuilder;
import utils.DataBaseUtil;
import utils.SqlQuery;
import utils.Utils;

public class StepDefinitionOutage extends Utils {
    RequestSpecification reqSpec;
    ResponseSpecification resSpec;
    Response response;
    TestRequestBuilder data = new TestRequestBuilder();
    OutageAPI outageAPI = new OutageAPI();
    String sCreateOutageJsonFP = Filepaths.sCreateOutageJsonFP;
    String sOutPayLoadFileName = sCreateOutageJsonFP + "outagePayLoad.json";
    
    // Variable used to verify the updation of the outage
    String sOutageId = "";

    @Given("^User create outage with details \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
    public void user_create_outage_with_details_and_and_and_and_and(String outageInfo, 
    		String outageCause, String outageMsg, String startTime, String endTime, 
    		String isResolved) throws Throwable {
		// Write outage payload to create the outage
		outageAPI.writeOutagePayloadJson(outageCause, startTime, endTime, outageMsg, 
        		outageInfo, isResolved);
        String bodyOutage = null;
        try {
            bodyOutage = generateStringFromResource(sOutPayLoadFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqSpec = given().spec(requestSpecification())
                .body(bodyOutage);
    }

    @When("^User calls  \"([^\"]*)\" with \"([^\"]*)\" http request$")
    public void user_calls_with_http_request(String resource, String method) throws Throwable {
        Routes resourceAPI = Routes.valueOf(resource);
        System.out.println(resourceAPI.getResource());
        resSpec = new ResponseSpecBuilder().expectStatusCode(200)
        		.expectContentType(ContentType.JSON).build();
        if (method.equalsIgnoreCase("POST"))
            response = reqSpec.when().post(resourceAPI.getResource());
        else if (method.equalsIgnoreCase("GET"))
            response = reqSpec.when().get(resourceAPI.getResource());
    }
    
    @Then("^API call got success for creating outage with status code \"([^\"]*)\" and \"([^\"]*)\"$")
    public void api_call_got_success_for_creating_outage_with_status_code_and(String sStatusCode,
    		String sMessage) throws Throwable {
    	assertEquals(Integer.toString(response.getStatusCode()), sStatusCode);
    	// Fetching the JSON response
    	JsonPath rawToJson = rawToJson(response);
    	String sActMsg = rawToJson.getString("result.Message");
    	assertEquals(sActMsg, sMessage);
    }
    
    @Then("^Outage info get saved with details \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" in database$")
    public void outage_info_get_saved_with_details_and_and_and_and_in_database(String sExpOutageInfo,
    		String sExpOutageCause, String sExpOutageMsg, String sExpStartTime, String sExpEndTime) throws Throwable {
    	String sActOutageCause = "", sActOutageInfo = "", sActOutageMsg = "", 
    			sActStartTime = "", sActEndTime= "";
    	// Fetching the JSON response
    	JsonPath rawToJson = rawToJson(response);
    	String sOutageID = rawToJson.getString("result.Data[0].OutageID");
    	try {
    		String sQuery = SqlQuery.getCreatedOutageWithID(sOutageID);
    		ResultSet resultSet = DataBaseUtil.getResultSet(sQuery);
    		resultSet.next();
    		sActOutageCause = resultSet.getString("Cause");
    		sActOutageInfo = resultSet.getString("OutageReportInfo");
    		sActOutageMsg = resultSet.getString("OutageMessage");
    		sActStartTime = resultSet.getString("StartTime");
    		sActEndTime = resultSet.getString("EndTime");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	assertEquals(sExpOutageInfo, sActOutageInfo);
    	assertEquals(sExpOutageCause, sActOutageCause);
    	assertEquals(sExpOutageMsg, sActOutageMsg);
    }
    
    @Given("^User update outage with details \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
    public void user_update_outage_with_details_and_and_and_and_and(String sOutageInfo, 
    		String sOutageCause, String sOutageMsg, String sStartTime, String sEndTime, 
    		String sIsResolved) throws Throwable {
    	sOutageId =  "";
    	try {
    		String sQuery = SqlQuery.getOutageIdToUpdate();
    		ResultSet resultSet = DataBaseUtil.getResultSet(sQuery);
    		resultSet.next();
    		sOutageId = resultSet.getString("OutageId");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	// Write outage payload to update the existing outage
        outageAPI.writeOutagePayloadJson(sOutageId, sOutageCause, sStartTime, sEndTime, 
        		sOutageMsg, sOutageInfo, sIsResolved);
        String bodyOutage = null;
        try {
            bodyOutage = generateStringFromResource(sOutPayLoadFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reqSpec = given().spec(requestSpecification())
                .body(bodyOutage);
    }
    
    @When("^User calls \"([^\"]*)\" with the existing outage id and \"([^\"]*)\" http request$")
    public void user_calls_with_the_existing_outage_id_and_http_request(String resource,
    		String method) throws Throwable {
    	Routes resourceAPI = Routes.valueOf(resource);
        System.out.println(resourceAPI.getResource());
        resSpec = new ResponseSpecBuilder().expectStatusCode(200)
        		.expectContentType(ContentType.JSON).build();
        if (method.equalsIgnoreCase("POST"))
            response = reqSpec.when().post(resourceAPI.getResource());
        else if (method.equalsIgnoreCase("GET"))
            response = reqSpec.when().get(resourceAPI.getResource());
    }
    
    @Then("^API call got success for updating outage with status code \"([^\"]*)\" and \"([^\"]*)\"$")
    public void api_call_got_success_for_updating_outage_with_status_code_and(String sStatusCode,
    		String sMessage) throws Throwable {
    	assertEquals(Integer.toString(response.getStatusCode()), sStatusCode);
    	// Fetching the JSON response
    	JsonPath rawToJson = rawToJson(response);
    	String sActMsg = rawToJson.getString("result.Message");
    	assertEquals(sActMsg, sMessage);
    }

    @Then("^Outage info get updated with details \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" in database\\.$")
    public void outage_info_get_updated_with_details_and_and_and_and_in_database(String sExpOutageInfo,
    		String sExpOutageCause, String sExpOutageMsg, String sExpStartTime, String sExpEndTime) throws Throwable {
    	String sActOutageCause = "", sActOutageInfo = "", sActOutageMsg = "", 
    			sActStartTime = "", sActEndTime= "";
    	try {
    		String sQuery = SqlQuery.getCreatedOutageWithID(sOutageId);
    		ResultSet resultSet = DataBaseUtil.getResultSet(sQuery);
    		resultSet.next();
    		sActOutageCause = resultSet.getString("Cause");
    		sActOutageInfo = resultSet.getString("OutageReportInfo");
    		sActOutageMsg = resultSet.getString("OutageMessage");
    		sActStartTime = resultSet.getString("StartTime");
    		sActEndTime = resultSet.getString("EndTime");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	assertEquals(sExpOutageInfo, sActOutageInfo);
    	assertEquals(sExpOutageCause, sActOutageCause);
    	assertEquals(sExpOutageMsg, sActOutageMsg);
    }
}
