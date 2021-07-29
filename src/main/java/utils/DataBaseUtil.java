package utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataBaseUtil {

	private static String connectionUrl = Utils.getPropValue("databaseURL");
	private static String dbUsername = Utils.getPropValue("dbusername");
	private static String dbPassword = Utils.getPropValue("dbpassword");
	private static String dbURLCredentials = Utils.getPropValue("dbURLCredentials");
	private static String dbURLCredentialsPaymentDatabaseChase = Utils
			.getPropValue("dbURLCredentialsPaymentDatabaseChase");
	private static String dbURLCredentialsPaymentDatabase = Utils.getPropValue("dbURLCredentialsPaymentDatabase");
	static String dbClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	public static String executeSQLQuery(String sqlQuery) throws Exception {
		Connection connection = null;
		String resultValue = "";
		ResultSet rs;
		try {
			/*
			 * Runner.test.log(Status.INFO, "Connecting to the database");
			 */
			Class.forName(dbClass).newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			if (connection != null) {
				/*
				 * Runner.test.log(Status.PASS, "Connected to the database...");
				 */
			} else {
				/*
				 * Runner.test.log(Status.FAIL,
				 * "Database connection failed to  Environment");
				 */
			}
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			try {
				while (rs.next()) {
					resultValue = rs.getString(1).toString();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException err) {
				/*
				 * Runner.test.log(Status.FAIL,
				 * "No Records obtained for this specific query");
				 */
				err.printStackTrace();
			}
		} catch (SQLException sqlEx) {
			/*
			 * Runner.test.log(Status.FAIL, "SQL Exception:" +
			 * sqlEx.getStackTrace());
			 */
		} finally {
			connection.close();
		}
		/* Runner.test.log(Status.PASS, "SQL Results: " + resultValue); */
		return resultValue;
	}

	public static void executeUpdateDeleteSQLQuery(String sqlQuery) throws Exception {
		Connection connection = null;
		ResultSet rs;
		try {

			Class.forName(dbClass).newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			if (connection != null) {

			} else {

			}
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlQuery);
		} catch (SQLException sqlEx) {

		}
		connection.close();
	}

	public static int callStoredProcedureToActivateUser(int iUserID) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		int FinalResult = 0;
		CallableStatement stmt = null;
		try {

			Class.forName(dbClass).newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			if (connection != null) {

			} else {

			}
			//
			String insertUserID = "{call AutoActivation(?)}";
			stmt = connection.prepareCall(insertUserID);
			stmt.setInt(1, iUserID);
			stmt.execute();
			rs = stmt.getResultSet();
			try {
				rs.next();
				FinalResult = rs.getInt(1);
				System.out.println(FinalResult);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException err) {

				err.printStackTrace();
			}
		} catch (NullPointerException err) {

			err.printStackTrace();
		}
		connection.close();
		return FinalResult;
	}

	/**
	 * Delete user by given user id by calling this method.
	 *
	 * @param userId
	 * @return
	 *
	 */
	public static int callProcedureToDeleteUser(int userId) {
		Connection connection = null;
		ResultSet rs = null;
		int FinalResult = 0;
		CallableStatement stmt = null;
		try {

			Class.forName(dbClass).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			if (connection != null) {

			} else {

			}
			String insertUserID = "{call SetUnRegisterUser(?)}";
			stmt = connection.prepareCall(insertUserID);
			stmt.setInt(1, userId);
			stmt.execute();
			rs = stmt.getResultSet();
			rs.next();
			String message = rs.getString(1);
			System.out.println(message);
			connection.close();
		} catch (Exception err) {

			err.printStackTrace();
		}
		return FinalResult;
	}

	public static void callStoredProcedureUnblockAccountIp() {
		Connection connection = null;
		ResultSet rs = null;
		int FinalResult = 0;
		CallableStatement stmt = null;
		try {

			Class.forName(dbClass).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			if (connection != null) {

			} else {

			}
			//
			String SQL = "{call AutoUnLockAll}";
			// String SQL = "call AutoUnLockAll";
			stmt = connection.prepareCall(SQL);
			stmt.execute();
			connection.close();
		} catch (Exception err) {

			err.printStackTrace();
		}
	}

	/**
	 * This method is used to get the result set by executing the given query.
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public static ResultSet getResultSet(String query) throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		Connection con = DriverManager.getConnection(dbURLCredentials);
		Statement statement = con.createStatement();
		Thread.sleep(500);
		ResultSet resultSet = statement.executeQuery(query);
		return resultSet;
	}

	/**
	 * This Method is used to get the result from multiple result set
	 * 
	 * @author Souradeep.Ghosh
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public static ResultSet getResultSetMulti(String query) throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		Connection con = DriverManager.getConnection(dbURLCredentials);
		Statement statement = con.createStatement();
		Thread.sleep(500);
		ResultSet resultSet2 = null;
		ResultSet resultSet1 = statement.executeQuery(query);
		resultSet1.close();
		boolean rs2 = statement.getMoreResults();
		if (rs2) {
			resultSet2 = statement.getResultSet();
		}
		return resultSet2;
	}

	/**
	 * This method is used to get the result set from the Payment Database by
	 * executing the given query.
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 *
	 */
	public static ResultSet getResultSetPaymentDatabase(String query) throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		Connection con = DriverManager.getConnection(dbURLCredentialsPaymentDatabase);
		Statement statement = con.createStatement();
		Thread.sleep(500);
		ResultSet resultSet = statement.executeQuery(query);
		return resultSet;
	}

	/**
	 * This method is used to get the result set from the Payment Database by
	 * executing the given query from Chase Payment API DB
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 *
	 */
	public static ResultSet getResultSetPaymentDatabaseChase(String query) throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		Connection con = DriverManager.getConnection(dbURLCredentialsPaymentDatabaseChase);
		Statement statement = con.createStatement();
		Thread.sleep(500);
		ResultSet resultSet = statement.executeQuery(query);
		return resultSet;
	}

	/**
	 * This method is used to activate a new account.
	 *
	 * @param utilityAccountNumber
	 * @return
	 * @throws Exception
	 *
	 */
	public static int activateNewUserStoredProcedure(String utilityAccountNumber) throws Exception {
		String sUserIDQuery = SqlQuery.sUserIDQuery + utilityAccountNumber + "'";
		String sUserID = DataBaseUtil.executeSQLQuery(sUserIDQuery);
		int iUserID = Integer.parseInt(sUserID);
		Connection connection = null;
		ResultSet rs = null;
		int FinalResult = 0;
		CallableStatement stmt = null;
		try {

			Class.forName(dbClass).newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			if (connection != null) {

			} else {

			}
			//
			String insertUserID = "{call AutoActivation(?)}";
			stmt = connection.prepareCall(insertUserID);
			stmt.setInt(1, iUserID);
			stmt.execute();
			rs = stmt.getResultSet();
			try {
				rs.next();
				FinalResult = rs.getInt(1);
				System.out.println(FinalResult);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException err) {

				err.printStackTrace();
			}

		} catch (NullPointerException err) {
			err.printStackTrace();
		}
		connection.close();
		return FinalResult;
	}

	public static Map<String, String> tableResultInMapForm(ResultSet resultSet) throws SQLException {
		ResultSet rs = resultSet;
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		List<String> columnNames = new LinkedList<>();
		Map<String, String> columnNameToValuesMap = new HashMap<String, String>();
		for (int i = 1; i <= columnCount; i++) {
			String columnName = rsmd.getColumnName(i);
			columnNames.add(columnName);
			// Load the Map initially with keys(columnnames) and empty list
			columnNameToValuesMap.put(columnName, new String());
		}
		try {
			while (rs.next()) { // Iterate the result set for each row
				for (String columnName : columnNames) {
					// Get the list mapped to column name
					// String columnDataList =
					// columnNameToValuesMap.get(columnName);
					// Add the current row's column data to list
					String value1 = new String();
					value1 = rs.getString(columnName);
					System.out.println("Column Name =" + columnName);
					System.out.println("Value =" + value1);
					// add the updated list of column data to the map now
					columnNameToValuesMap.put(columnName, value1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columnNameToValuesMap;
	}

	/**
	 * This method is used to get the min max length of all the registration
	 * fields.
	 *
	 */
	public static HashMap<String, String[]> getMinMaxOfRegistrationFieldsFromDB() {
		String query = SqlQuery.getRegistrationTemplateConfig();
		HashMap<String, String[]> minMaxValueRegFields = new HashMap<>();
		try {
			ResultSet rs = DataBaseUtil.getResultSet(query);
			while (rs.next()) {
				String[] minMaxLimit = new String[2];
				minMaxLimit[0] = rs.getString("Min Length");
				minMaxLimit[1] = rs.getString("Max Length");
				minMaxValueRegFields.put(rs.getString("ParentHead"), minMaxLimit);
			}
			return minMaxValueRegFields;
		} catch (Exception e) {
			e.printStackTrace();
			return minMaxValueRegFields;
		}
	}

	// /**
	// * This method is used to get Notification module related data
	// *
	// */
	// public static HashMap<String, Integer> getNotificationDataFromDB(){
	// String query = SqlQuery.getNotificationQuery(TestDataHelper.userId);
	// int rOutage=0, uOutage=0, rBilling=0, uBilling=0, rService=0, uService=0,
	// rConnectMe=0, uConnectMe=0, rDR=0, uDR=0,rLeakAlert=0, uLeakAlert=0,
	// rOthers=0, uOthers=0;
	// int savedCount=0, sentCount=0, trashed=0, receivedCount=0,
	// repliedCount=0, allMailsCount=0;
	// HashMap <String, Integer> notificationMatrix = new HashMap<>();
	// HashMap <String, String> msgIdMatrix = new HashMap<>();
	// try {
	// ResultSet rs = DataBaseUtil.getResultSet(query);
	// while(rs.next()) {
	// String msgId = rs.getString("MessageId");
	// if(msgIdMatrix.containsKey(msgId)) {
	// String value = msgIdMatrix.get(msgId);
	// if(value.equals("0") && rs.getString("IsReply").equals("1")) {
	// msgIdMatrix.put(msgId, rs.getString("IsReply"));
	// if(rs.getString("IsTrashed").equals("0")) {
	// sentCount++;
	// repliedCount++;
	// }
	// }
	// if(value.equals("1") && rs.getString("IsReply").equals("1")) {
	// if(rs.getString("IsTrashed").equals("0")) {
	//// System.out.println(rs.getString("Subject"));
	// repliedCount++;
	// }
	// }
	// }else {
	// msgIdMatrix.put(msgId, rs.getString("IsReply"));
	// if(rs.getString("IsReply").equals("1")) {
	// if(rs.getString("IsTrashed").equals("0"))
	// sentCount++;
	// }
	// if(rs.getString("IsReply").equals("0")) {
	// if(rs.getString("IsTrashed").equals("0"))
	// receivedCount++;
	// }
	// if(rs.getString("IsTrashed").equals("0") &&
	// rs.getString("IsSaved").equals("1")) {
	// savedCount++;
	// }
	// if(rs.getString("IsTrashed").equals("1")) {
	// trashed++;
	// }else {
	// allMailsCount++;
	// }
	// }
	//
	// String category = rs.getString("PlaceHolderName");
	//
	// switch (category)
	// {
	// case "Outages":
	// System.out.println(category);
	// if(rs.getString("IsReply").equals("0")) {
	// if(rs.getString("IsTrashed").equals("0")) {
	// if(rs.getString("IsRead").equals("1")) {
	// rOutage++;
	// }else {
	// uOutage++;
	// }
	// }
	// }
	// break;
	//
	// case "Billing":
	// System.out.println(category);
	// if(rs.getString("IsReply").equals("0")) {
	// if(rs.getString("IsTrashed").equals("0")) {
	// if(rs.getString("IsRead").equals("1")) {
	// rBilling++;
	// }else {
	// uBilling++;
	// }
	// }
	// }
	// break;
	//
	// case "Service":
	// System.out.println(category);
	// if(rs.getString("IsReply").equalsIgnoreCase("0")) {
	// if(rs.getString("IsTrashed").equalsIgnoreCase("0")) {
	// if(rs.getString("IsRead").equalsIgnoreCase("1")) {
	// rService++;
	// }else {
	// uService++;
	// }
	// }
	// }
	// break;
	//
	// case "Connect Me":
	// System.out.println(category);
	// if(rs.getString("IsReply").equals("0")) {
	// if(rs.getString("IsTrashed").equals("0")) {
	// if(rs.getString("IsRead").equals("1")) {
	// rConnectMe++;
	// }else {
	// uConnectMe++;
	// }
	// }
	// }
	// break;
	//
	// case "Demand Response":
	// System.out.println(category);
	// if(rs.getString("IsReply").equals("0")) {
	// if(rs.getString("IsTrashed").equals("0")) {
	// if(rs.getString("IsRead").equals("1")) {
	// rDR++;
	// }else {
	// uDR++;
	// }
	// }
	// }
	// break;
	//
	// case "Leak Alert":
	// System.out.println(category);
	// if(rs.getString("IsReply").equals("0")) {
	// if(rs.getString("IsTrashed").equals("0")) {
	// if(rs.getString("IsRead").equals("1")) {
	// rLeakAlert++;
	// }else {
	// uLeakAlert++;
	// }
	// }
	// }
	// break;
	//
	// default:
	// System.out.println(category);
	// if(rs.getString("IsReply").equals("0")) {
	// if(rs.getString("IsTrashed").equals("0")) {
	// if(rs.getString("IsRead").equals("1")) {
	// rOthers++;
	// }else {
	// uOthers++;
	// }
	// }
	// }
	// }
	// }
	//
	// notificationMatrix.put("SavedCount", savedCount);
	// notificationMatrix.put("SentCount", sentCount);
	// notificationMatrix.put("TrashCount", trashed);
	// notificationMatrix.put("ReceivedCount", receivedCount);
	// notificationMatrix.put("RepliedCount", repliedCount);
	// notificationMatrix.put("AllMailsCount", allMailsCount);
	// notificationMatrix.put("ReadOutage", rOutage);
	// notificationMatrix.put("UnReadOutage", uOutage);
	// notificationMatrix.put("ReadBilling", rBilling);
	// notificationMatrix.put("UnReadBilling", uBilling);
	// notificationMatrix.put("ReadService", rService);
	// notificationMatrix.put("UnReadService", uService);
	// notificationMatrix.put("ReadConnectMe", rConnectMe);
	// notificationMatrix.put("UnReadConnectMe", uConnectMe);
	// notificationMatrix.put("ReadDR", rDR);
	// notificationMatrix.put("UnReadDR", uDR);
	// notificationMatrix.put("ReadLeakAlert", rLeakAlert);
	// notificationMatrix.put("UnReadLeakAlert", uLeakAlert);
	// notificationMatrix.put("ReadOthers", rOthers);
	// notificationMatrix.put("UnReadOthers", uOthers);
	// System.out.println(notificationMatrix);
	// return notificationMatrix;
	// } catch (Exception e) {
	// e.printStackTrace();
	// return notificationMatrix;
	// }
	// }

	/**
	 * This method is used to get Bill Statement.
	 * 
	 * @param serviceAccountNumber
	 * @return billValue
	 */
	public static List<String> getBillStatementFromDB(String serviceAccountNumber) {
		String query = SqlQuery.getBillStatementQuery(serviceAccountNumber);
		List<String> billValue = new ArrayList<>();
		try {
			ResultSet rs = DataBaseUtil.getResultSet(query);
			while (rs.next()) {
				String value = rs.getString("value");
				billValue.add(value);
			}
			return billValue;
		} catch (Exception e) {
			e.printStackTrace();
			return billValue;
		}
	}

	/**
	 * This method is used to get Payments.
	 * 
	 * @param serviceAccountNumber
	 * @return billValue
	 */
	public static List<String> getPaymentsFromDB(String serviceAccountNumber) {
		String query = SqlQuery.getPaymentsQuery(serviceAccountNumber);
		List<String> paymentsValue = new ArrayList<>();
		try {
			ResultSet rs = DataBaseUtil.getResultSet(query);
			while (rs.next()) {
				String value = rs.getString("TransactionAmount");
				paymentsValue.add(value);
			}
			return paymentsValue;
		} catch (Exception e) {
			e.printStackTrace();
			return paymentsValue;
		}
	}

	public static HashMap<String, String> getRegisteredInactiveAccountInfo() {
		String query = SqlQuery.sRegisteredInactiveAccount;
		HashMap<String, String> registeredInactiveAccountInfo = new HashMap<>();
		try {
			ResultSet rs = DataBaseUtil.getResultSet(query);
			while (rs.next()) {
				registeredInactiveAccountInfo.put("UtilityAccountNumber", rs.getString("UtilityAccountNumber"));
				registeredInactiveAccountInfo.put("EmailID", rs.getString("EmailID"));
				break;
			}
			return registeredInactiveAccountInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return registeredInactiveAccountInfo;
		}
	}

	// public static String getForgetPasswordEmailLink() {
	// String url = null;
	// try {
	// String emailBodyQuery =
	// SqlQuery.getForgetPasswordEmailQuery(TestDataHelper.userId);
	// ResultSet rs = DataBaseUtil.getResultSet(emailBodyQuery);
	// rs.next();
	// String str1 = rs.getString("Message");
	// String s1 = "href='";
	// String s2 = "'>Click here";
	// url = str1.substring(str1.indexOf(s1)+s1.length(), str1.indexOf(s2));
	// System.out.println(url);
	// } catch(Exception e) {
	// e.printStackTrace();
	// }
	// return url;
	// }

	// public static String getFirstSecurityQueOfUser() throws Exception {
	// String securityQue1Query =
	// SqlQuery.getSecurityQue1Query(TestDataHelper.userId);
	// ResultSet rs = DataBaseUtil.getResultSet(securityQue1Query);
	// rs.next();
	// String que1 = rs.getString("ControlText");
	// return que1;
	// }

	public static String getResetPasswordHistoryDB() throws Exception {
		String allUtilitySettingsQuery = SqlQuery.sAllUtilitySettingsQuery;
		ResultSet rs = DataBaseUtil.getResultSet(allUtilitySettingsQuery);
		rs.next();
		String count = rs.getString("ResetPasswordHistory");
		return count;
	}

	public static int getUserIdDB(String sUserName) throws Exception {
		String getUserIdQuery = "SELECT UserId from [User] where UserName = '" + sUserName + "'";
		ResultSet rs = DataBaseUtil.getResultSet(getUserIdQuery);
		rs.next();
		String userId = rs.getString("UserId");
		return Integer.parseInt(userId);
	}

	public static String getPrimaryContactType(String sUsername) throws Exception {
		String query = "Select Name from CommonMaster where MasterType = 'PhoneNumberType' and MasterCode = (Select MobilePhoneType from [User] where UserName ='"
				+ sUsername + "')";
		ResultSet rs = DataBaseUtil.getResultSet(query);
		rs.next();
		String contactType = rs.getString("Name");
		return contactType;
	}

	public static String getAlternateContactType(String sUsername) throws Exception {
		String query = "Select Name from CommonMaster where MasterType = 'PhoneNumberType' and MasterCode = (Select HomePhoneType from [User] where UserName ='"
				+ sUsername + "')";
		ResultSet rs = DataBaseUtil.getResultSet(query);
		String contactType = "";
		if (rs.next())
			contactType = rs.getString("Name");
		return contactType;
	}

	public static List<String> getAllTextsInNotificationPref(String sUsername) throws Exception {
		String query = "select Distinct EmailORPhone from AccountNotificationDetail where AccountNotificationTypeID in (1, 5, 9, 13, 17, 21, 25, 29) AND UserID = (Select UserID from [User] where UserName ='"
				+ sUsername + "')";
		ResultSet rs = DataBaseUtil.getResultSet(query);
		List<String> lst = new ArrayList<>();
		while (rs.next()) {
			String value = rs.getString("EmailORPhone");
			lst.add(value);
		}
		return lst;
	}

	public static List<String> getAllEmailsInNotificationPref(String sUsername) throws Exception {
		String query = "select Distinct EmailORPhone from AccountNotificationDetail where AccountNotificationTypeID in (2, 6, 10, 14, 18, 22, 26, 30) AND UserID = (Select UserID from [User] where UserName ='"
				+ sUsername + "')";
		ResultSet rs = DataBaseUtil.getResultSet(query);
		List<String> lst = new ArrayList<>();
		while (rs.next()) {
			String value = rs.getString("EmailORPhone");
			lst.add(value);
		}
		return lst;
	}

	public static List<String> getAllIVRsInNotificationPref(String sUsername) throws Exception {
		String query = "select Distinct EmailORPhone from AccountNotificationDetail where AccountNotificationTypeID in (4, 8, 12, 16, 20, 24, 28, 32) AND UserID = (Select UserID from [User] where UserName ='"
				+ sUsername + "')";
		ResultSet rs = DataBaseUtil.getResultSet(query);
		List<String> lst = new ArrayList<>();
		while (rs.next()) {
			String value = rs.getString("EmailORPhone");
			lst.add(value);
		}
		return lst;
	}
}