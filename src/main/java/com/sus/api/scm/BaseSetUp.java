package com.sus.api.scm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import io.restassured.RestAssured;

public class BaseSetUp {
	public Properties propenv;
	public Properties proptext;
	public static String resourcePath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "java" + File.separator + "resources";
	public static String testDataPath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "java" + File.separator + "data";

	@BeforeTest
	public void getEnvironmentDetails() {
		propenv = new Properties();
		try {
			FileInputStream fis = new FileInputStream(resourcePath + "\\env.properties");
			propenv.load(fis);
			RestAssured.baseURI = propenv.getProperty("HOST");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void getTextProperty() {
		proptext = new Properties();
		try {
			FileInputStream fis = new FileInputStream(resourcePath + "\\text.properties");
			System.out.println(resourcePath + "\\text.properties");
			proptext.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
