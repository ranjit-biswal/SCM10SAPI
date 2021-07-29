package com.sus.api.scm;

import java.io.File;

public class Filepaths {
	/*******************************************************
	 * >>>>>>>>>>>> GOBAL FILE PATHS <<<<<<<<<<<*
	 *******************************************************/
	public static String sGlobalProp = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
			+ File.separator + "resource" + File.separator + "resources" + File.separator + "global.properties";
	
	public static String sEnvProp = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
			+ File.separator + "resource" + File.separator + "resources" + File.separator + "env.properties";

	/********************************************************
	 * >>>>>>>>>> EXTENT RELATED FILE PATHS <<<<<<<<<<<<<<<<<*
	 *******************************************************/
	public static String sExtentFP = System.getProperty("user.dir") + File.separator + "Report" + File.separator
			+ "ExtReport" + File.separator + "WebRegressionTestExecutionReport.html";
	public static String sScreenshotFP = System.getProperty("user.dir") + File.separator + "Report" + File.separator
			+ "Screenshot";

	/*********************************************************
	 * >>>>>>>>>>>>>>> JSON RELATED FILE PATHS <<<<<<<<<<<<<<<*
	 *********************************************************/
	// Scp test data filepath
	public static String sTestDataJsonFP = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "java" + File.separator + "data" + File.separator + "testscp" + File.separator;
	// Scp test data new file path
	public static String sTestDataScpJsonFP = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "java" + File.separator + "data" + File.separator + "testscp" + File.separator;
	// Scp text properties file path
	public static String sTextPropScpJsonFP = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "java" + File.separator + "resources" + File.separator + "textscp"
			+ File.separator;
	// Csp text properties file path
	public static String sTextPropCspJsonFP = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "java" + File.separator + "resources" + File.separator + "textcsp"
			+ File.separator;
	// Csp test properties file path
	public static String sTestDataCspJsonFP = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "java" + File.separator + "data" + File.separator + "testcsp" + File.separator;
	// Json API related file paths.
	public static String sTestDataSCPJsonFP = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "resources" + File.separator + "testDataJson" + File.separator;
	public static String sInsertOutagePayloadFP = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "resources" + File.separator + "data" + File.separator + "api"
			+ File.separator + "outage" + File.separator;
	public static String sConnectMeProgramJson = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "resources" + File.separator + "testDataJson" + File.separator;
	public static String sCreateOutageJsonFP = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "resources" + File.separator + "testDataJson"+ File.separator;

	/************************************************************
	 * >>>>>>>>>>>>>>>>>>>> OTHER FILE PATHS <<<<<<<<<<<<<<<<<<<<*
	 ************************************************************/
	// Download folder path
	public static String sDownloadFP = System.getProperty("user.dir") + File.separator + "Downloads" + File.separator;
	// Upload folder path
	public static String sUploadFP = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
			+ File.separator + "java" + File.separator + "data" + File.separator + "fileupload" + File.separator;
	// Mail content text file path
	public static String sMailContentTxtFilepath = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "java" + File.separator + "data" + File.separator
			+ "mailcontent" + File.separator + "out.txt";
	// Config output properties file path
	public static String outputConfigFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "java" + File.separator + "resources" + File.separator
			+ "registeredUser.properties";
	public static String apiConfigFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "java" + File.separator + "resources" + File.separator
			+ "apiConfigFilePath.properties";
	public static String apiPaymentsFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "java" + File.separator + "resources" + File.separator + "Payments.xlsx";
	public static String notificationFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "java" + File.separator + "resources" + File.separator + "Notification.xlsx";
}
