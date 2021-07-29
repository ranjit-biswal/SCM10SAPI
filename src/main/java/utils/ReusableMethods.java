package utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReusableMethods {

	public static JsonPath rawToJson(Response res) {
		String responseData = res.asString();
		JsonPath js = new JsonPath(responseData);
		return js;

	}

}
