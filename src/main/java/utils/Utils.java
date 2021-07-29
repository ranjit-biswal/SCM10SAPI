package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matchers;

import com.sus.api.scm.Filepaths;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils {

	public static RequestSpecification req;
	public static RequestSpecification res;
	public static ResponseSpecification resspec;
	public static Response response;

	public static RequestSpecification requestSpecification() throws IOException {

		if (req == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			return req;
		}
		return req;

	}

	public static RequestSpecification requestSpecification(Map<String, String> headerMap) throws IOException {

		if (req == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addHeaders(headerMap)
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			return req;
		}
		return req;

	}

	public static String getGlobalValue(String key) {
		String value = null;
		try {
			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(Filepaths.sGlobalProp);
			prop.load(fis);
			value = prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getPropValue(String key) {
		String value = null;
		try {
			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(Filepaths.sEnvProp);
			prop.load(fis);
			value = prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getJsonPath(Response response, String key) {
		String value = null;
		try {
			String resp = response.asString();
			JsonPath js = new JsonPath(resp);
			value = js.get(key).toString();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * This method is used to read data from json
	 * 
	 * @author Souradeep.Ghosh
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String generateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}

	public static JsonPath rawToJson(Response r) {
		String asString = r.asString();
		JsonPath j = new JsonPath(asString);
		return j;
	}

	/**
	 * This Method is used to get the response of the API
	 * 
	 * @param res
	 * @param methodType
	 * @param resources
	 * @return
	 */
	public static Response getResponse(RequestSpecification res, String methodType, String resources) {
		Response resp = null;
		switch (methodType) {
		case "post":
			resp = res.when().post(resources);
			ValidatableResponse valRes = resp.then();
			long responseTimeInSeconds = resp.getTimeIn(TimeUnit.SECONDS);
			valRes.time(Matchers.lessThan(23L), TimeUnit.SECONDS);
			break;
		}
		return resp;
	}

	/**
	 * This method is used for convert string to Base64 format
	 * 
	 * @param str
	 * @return
	 */
	public static String getConversionStringToBase64(String str) {
		Encoder encoder = Base64.getEncoder();
		String encodedString = encoder.encodeToString(str.getBytes());
		return encodedString;

	}
}
