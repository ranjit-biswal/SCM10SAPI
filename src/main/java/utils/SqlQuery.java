package utils;

import java.util.Map;

/**
 * This class contains all the SCM and SCP related SQL Queries.
 *
 * @author Ranjit.Biswal
 * @since 09-04-19
 *
 */
public class SqlQuery {
	/*******************************************************
	 * >>>>>>>>>>>>> SCP RELATED SQL QUERIES <<<<<<<<<<<<<<*
	 *******************************************************/

	/*******************************************************
	 * >>>>>>>>>>>>>>>> LOGIN PAGE QUERIES <<<<<<<<<<<<<<< *
	 *******************************************************/
	public static String getDeActiveAccount() {
		String deActiveAccount = "select Top 1 Username from [user] where status='2' order by 1 desc";
		return deActiveAccount;
	}

	public static String getPassword(String username) {
		String password = "select password from [user] where username='" + username + "'";
		return password;
	}

	public static String updatePassword(String username, String password) {
		String updatePassword = "update [user] set Password='" + password + "' where username='" + username + "'";
		return updatePassword;
	}

	public static String updateUsernameQuery(String sUserId, String sChangeUsername) {
		String sQuery = "UPDATE [User]\n" + "SET UserName = '" + sChangeUsername + "'\n" + "WHERE UserID = '" + sUserId
				+ "'";
		return sQuery;
	}

	/*********************************************************
	 * >>>>>>>>>>>>> LOGIN SUPPORT PAGE QUERIES <<<<<<<<<<<<<<*
	 *********************************************************/
	public static String sRegisteredInactiveAccount = "SELECT UtilityAccountNumber, EmailID "
			+ "FROM CustomerInfo(NOLOCK) " + "WHERE UtilityAccountNumber IN (SELECT UtilityAccountNumber "
			+ "FROM CustomerInfo(NOLOCK) " + "WHERE AccountStatusID=2 " + "AND AccountNumber NOT IN (-1,2))";

	public static String getForgetPasswordEmailQuery(String username) {
		String query = "SELECT TOP 1 Message FROM ContractAccountNotifyEmail "
				+ "WHERE EmailID = (SELECT EmailID FROM [User] " + "WHERE UserName = '" + username + "') "
				+ "AND Subject = 'SCM Password Reset Link' ORDER BY CreateDate DESC";
		return query;
	}

	public static String getSecurityQue1Query(String username) {
		String query = "SELECT ControlText FROM multilingualmaster WHERE LanguageCode = 'EN' "
				+ "AND ControlId=(SELECT ControlId FROM SecurityQuestion WHERE QuestionId = "
				+ "(SELECT SecurityQuestionId FROM [user] WHERE UserName ='" + username + "'))";
		return query;
	}

	public static String getSecurityQue2Query(String username) {
		String query = "SELECT ControlText FROM multilingualmaster WHERE LanguageCode = 'EN' "
				+ "AND ControlId=(Select ControlId from SecurityQuestion "
				+ "WHERE QuestionId = (SELECT SecurityQuestionId2 FROM [user] WHERE UserName ='" + username + "'))";
		return query;
	}

	public static String getRegisteredNotactivatedUserQuery() {
		String query = "Select  CI.UtilityAccountNumber , U.EmailID, U.UserName "
				+ "from [User] U Join useraccount UA on U.Userid=UA.Userid \r\n"
				+ "join CustomerInfo CI on UA.UtilityAccountNumber = CI.UtilityAccountNumber "
				+ "where U.status=0 and CI.AddressType in  ('Residential', 'Commercial') "
				+ "and convert(date,U.LinkSentDate)>=convert(date,getdate()-3)\r\n" + "";
		return query;
	}

	public static String sSecurityLeveValue = "Select [Value] from TemplateDetail"
			+ " where ControlName = 'Security Level'";

	public static String getPasswordResetLinkEmailMsg(String sEmailId) {
		String sQuery = "SELECT TOP 1 ID, AccountNumber, EmailID, Subject, Message\n"
				+ "FROM ContractAccountNotifyEmail\n" + "WHERE EmailID = '" + sEmailId + "'\n"
				+ "AND Subject = 'SCM Password Reset Link'\n" + "ORDER BY ID DESC";
		return sQuery;
	}

	public static String getUsernameAssistanceEmailMsg(String sSubjectLine, String sEmailAddress) {
		String sQuery = "SELECT TOP 1 Message, IsNotify\n" + "FROM ContractAccountNotifyEmail\n" + "WHERE EmailID = '"
				+ sEmailAddress + "' " + "AND Subject = '" + sSubjectLine + "'\n" + "ORDER BY ID DESC";
		return sQuery;
	}

	/*****************************************************
	 * >>>>>>> CUSTOMER REGISTRATION PAGE QUERIES <<<<<<< *
	 *****************************************************/
	public static String sResidentialInactiveUserIDQuery = "DECLARE @ExpirationDays SMALLINT, @ExpirationMinutes INT "
			+ "SELECT @ExpirationDays = REPLACE(CM.Name,' day(s)','') " + "FROM TemplateDetail T(NOLOCK) "
			+ "JOIN CommonMaster CM(NOLOCK) ON (CM.MasterCode = T.Value AND CM.MasterType = 'TempValidity') "
			+ "WHERE TempDetailID = 51 SELECT U.UserID FROM [User] U (NOLOCK) "
			+ "JOIN UserAccount UA(NOLOCK) ON U.UserID=UA.UserID "
			+ "JOIN Account IA(NOLOCK) ON IA.AccountNumber=UA.AccountNumber "
			+ "JOIN CustomerAddress CA(NOLOCK) ON CA.AddressId=IA.AddressId " + "WHERE U.Status=0  "
			+ "AND CA.AddressType=2 "
			+ "AND DATEDIFF(DAY,ISNULL(ReminderDate,U.CreatedDate),GETDATE())<=@ExpirationDays  ";

	public static String sInactiveDetailsQuery = "SELECT EmailID, UtilityAccountNumber, ZipCode, MeterNumber "
			+ "FROM VCustomer WHERE UserID= ";

	public static String sUserNameFromQuery = "Select Top 1 UserName From [User] where UserName is NOT NULL "
			+ "AND UserId NOT IN (1,-1) AND UserName <> ''";

	public static String sResidentialAccountResistered = "" + "SELECT Top 1 A.UtilityAccountNumber,A.AccountNumber "
			+ ",(CASE CA.AddressType WHEN 1 THEN 'Residential' ELSE 'Commercial' END) AS AddressType " + " "
			+ "FROM Account A(NOLOCK) " + "JOIN CustomerAddress CA(NOLOCK) ON A.AddressID=CA.AddressID "
			+ "WHERE A.Status=1 AND CA.AddressType=1";

	public static String sCommercialAccountResistered = "" + "SELECT Top 1 A.UtilityAccountNumber,A.AccountNumber "
			+ ",(CASE CA.AddressType WHEN 1 THEN 'Residential' ELSE 'Commercial' END) AS AddressType " + " "
			+ "FROM Account A(NOLOCK) " + "JOIN CustomerAddress CA(NOLOCK) ON A.AddressID=CA.AddressID "
			+ "WHERE A.Status=1 AND CA.AddressType=2";

	/**
	 * This getRegistrationTemplateConfig query fetches the Min,Max, Mandatory
	 * Status, Validation against CIS related information form the Database
	 * Primary Contact Number 10 10 true true
	 * 
	 * @return Field Name-Primary Contact Number, MinLegth-10, MaxLegth-10,
	 *         Mandatory -True, Validation Against CIS-true
	 */
	public static String getRegistrationTemplateConfig() {
		String sRegistrationTemplateConfig = "" + " SELECT * from( "
				+ "select a.ControlName AS ParentHead,b.ControlName,b.value, b.ScpStatus from TemplateDetail a "
				+ "left join TemplateDetail b on a.TempDetailID=b.Parentid "
				+ "where b.ControlName IN ('Min Length','Max Length','Mandatory','Validation Against CIS', 'Type') "
				+ " ) AS s " + " PIVOT " + "(max(value) "
				+ "	for ControlName IN ([Min Length],[Max Length],[Mandatory],[Validation Against CIS], [Type]) )as pvt";
		return sRegistrationTemplateConfig;
	}

	/**
	 * This getRegistrationData query fetches the information required to do a
	 * successful registration
	 * 
	 * @param accountType
	 *            - 1 for Residential User, 2 For Business User
	 * @return CustomerID MobilePhone DrivingLicence customerNo
	 *         UtilityAccountNumber ZipCode Address1 SSNNumber CustomerType
	 *         MeterNumber
	 */
	public static String getRegistrationData(String accountType) {
		String sRegistrationData = "SELECT Top 1 c.CustomerID, c.MobilePhone, c.DrivingLicence, C.customerNo, "
				+ "CA.UtilityAccountNumber,CA.ZipCode,CA.Address1,1234 AS SSNNumber, "
				+ "(CASE WHEN CA.AddressType=1 THEN 'Residential' ELSE 'Commercial' END) AS CustomerType, "
				+ "MAX(AMM.MeterNumber) AS MeterNumber " + "FROM Customer c(NOLOCK) "
				+ "JOIN CustomerAddress CA(NOLOCK) ON c.CustomerID=ca.CustomerID "
				+ "JOIN Account a(NOLOCK) ON ca.AddressID = a.AddressID "
				+ "JOIN AccountMeterMapping AMM(NOLOCK) ON A.AccountNumber=AMM.AccountNumber "
				+ "LEFT JOIN (SELECT DISTINCT IA.AccountNumber "
				+ "FROM Customer IC(NOLOCK) JOIN CustomerAddress ICA(NOLOCK) ON IC.CustomerID = ICA.CustomerID "
				+ "JOIN Account IA(NOLOCK) ON IA.AddressID = ICA.AddressID "
				+ "JOIN UserAccount IUA(NOLOCK) ON IUA.AccountNumber=IA.AccountNumber) R ON A.AccountNumber = "
				+ "R.AccountNumber " + "WHERE R.AccountNumber IS NULL "
				+ "AND c.CustomerID NOT IN (1,-1, 10417, 10615, 10931) " + "AND CA.AddressType=" + accountType + " "
				+ "AND a.Status='3' " + "AND CA.PortalAccessType = 0 "
				+ "GROUP BY c.CustomerID, c.MobilePhone, C.customerNo, "
				+ "(CASE WHEN CA.AddressType = 1 THEN 'Residential' ELSE 'Commercial' END), "
				+ "c.DrivingLicence, CA.ZipCode, CA.Address1,C.SSNNumber,CA.UtilityAccountNumber";
		return sRegistrationData;
	}

	public static String getRegistrationDataForInactiveAccount(String accountType) {
		String sRegistrationData = "SELECT Top 1 c.CustomerID, c.MobilePhone, c.DrivingLicence, C.customerNo, "
				+ "CA.UtilityAccountNumber,CA.ZipCode,CA.Address1,1234 AS SSNNumber, "
				+ "(CASE WHEN CA.AddressType=1 THEN 'Residential' ELSE 'Commercial' END) AS CustomerType, "
				+ "MAX(AMM.MeterNumber) AS MeterNumber " + "FROM Customer c(NOLOCK) "
				+ "JOIN CustomerAddress CA(NOLOCK) ON c.CustomerID=ca.CustomerID "
				+ "JOIN Account a(NOLOCK) ON ca.AddressID = a.AddressID "
				+ "JOIN AccountMeterMapping AMM(NOLOCK) ON A.AccountNumber=AMM.AccountNumber "
				+ "LEFT JOIN (SELECT DISTINCT IA.AccountNumber "
				+ "FROM Customer IC(NOLOCK) JOIN CustomerAddress ICA(NOLOCK) ON IC.CustomerID = ICA.CustomerID "
				+ "JOIN Account IA(NOLOCK) ON IA.AddressID = ICA.AddressID "
				+ "JOIN UserAccount IUA(NOLOCK) ON IUA.AccountNumber=IA.AccountNumber) R ON A.AccountNumber = "
				+ "R.AccountNumber " + "WHERE R.AccountNumber IS NULL "
				+ "AND c.CustomerID NOT IN (1,-1, 10417, 10615, 10931) " + "AND CA.AddressType=" + accountType + " "
				+ "AND a.Status='2' " + "AND CA.PortalAccessType = 0 "
				+ "GROUP BY c.CustomerID, c.MobilePhone, C.customerNo, "
				+ "(CASE WHEN CA.AddressType = 1 THEN 'Residential' ELSE 'Commercial' END), "
				+ "c.DrivingLicence, CA.ZipCode, CA.Address1,C.SSNNumber,CA.UtilityAccountNumber";
		return sRegistrationData;
	}

	/**
	 * This sUserIDQuery is to get the user id for an utilityAccountNumber
	 */
	public static String sUserIDQuery = "SELECT UserID FROM UserAccount where RoleID=3 AND UtilityAccountNumber = '";

	/**
	 * This getUserId is to get the user id for a username select
	 * PaymentAccount, CCExpMonth, CCExpYear, FirstName, BankName from
	 * PaymentProfiles where ExternalId= '431' and ProfileStatus=1 order by 1
	 * desc.
	 */
	public static String getUserId(String username) {
		String sUserIDQuery = "select UserId from [User] where UserName='" + username + "'";
		return sUserIDQuery;
	}

	public static String sCommercialInactiveUserIDQuery = "DECLARE @ExpirationDays SMALLINT, @ExpirationMinutes "
			+ "INT SELECT @ExpirationDays = REPLACE(CM.Name,' day(s)','') FROM TemplateDetail T(NOLOCK) "
			+ "JOIN CommonMaster CM(NOLOCK) ON(CM.MasterCode = T.Value AND CM.MasterType = 'TempValidity') "
			+ "WHERE TempDetailID = 51 SELECT U.UserID FROM [User] U (NOLOCK) "
			+ "JOIN UserAccount UA(NOLOCK) ON U.UserID = UA.UserID "
			+ "JOIN Account IA(NOLOCK) ON IA.AccountNumber = UA.AccountNumber "
			+ "JOIN CustomerAddress CA(NOLOCK) ON CA.AddressId = IA.AddressId "
			+ "WHERE U.Status = 0 AND CA.AddressType = 2 "
			+ "AND DATEDIFF(DAY, ISNULL(ReminderDate, U.CreatedDate), GETDATE())<=@ExpirationDays";

	public static String getContactTypeQuery = "select Name from commonmaster where mastertype='PhoneNumberType'";

	/**
	 * This method is to return the query for getting user id of the given user
	 * name.
	 * 
	 * @param userName
	 * @return
	 */
	public static String getUserIdOfGivenUsernameQuery(String userName) {
		String sQuery = "SELECT UserID FROM [User] WHERE UserName = '" + userName + "'";
		return sQuery;
	}

	/**
	 * This method is to return stored procedure for deleting user with user id.
	 * 
	 * @param userId
	 * @return
	 */
	public static String getStoredProcedureForDeletingUserWithUserId(String userId) {
		String sQuery = "EXEC SetUnRegisterUser @UserID=" + userId;
		return sQuery;
	}

	/**
	 * This method is to get the registered user data query.
	 * 
	 * @param userName
	 * @return
	 */
	public static String getRegisteredUserDataQuery(String userName) {
		String sQuery = "SELECT UserID, UserName, EmailID, [Status], SecurityQuestionId, SecurityQuestionId2, "
				+ "Zipcode, MobilePhone, FirstName, LastName FROM [User] WHERE UserName = '" + userName + "'";
		return sQuery;
	}

	/**
	 * This method returns query for fetching the account activation mail
	 * content.
	 *
	 * @param sUsername
	 * @return
	 */
	public static String getRegistrationEmailContent(String sUsername) {
		String sQuery = "SELECT CustomerName, EmailId, Subject, EmailMsg, IsEmailSent\r\n"
				+ "FROM ContractAccountNotify\r\n" + "WHERE CustomerName='" + sUsername + "'";
		return sQuery;
	}

	public static String getAccountStatus(String sUsername) {
		String sQuery = "SELECT Status, UserName \n" + "FROM [User] \n" + "WHERE UserName ='" + sUsername + "'";
		return sQuery;
	}

	public static String getCISDataFromCustomerInfo(String sUtilityAccountNumber) {
		String sQuery = "SELECT * FROM Customer\n" + "WHERE CustomerId = (SELECT CustomerId\n" + "FROM CustomerInfo\n"
				+ "WHERE UtilityAccountNumber = '" + sUtilityAccountNumber + "')";
		return sQuery;
	}

	public static String getCISDataFromAccountTable(String sUtilityAccountNumber) {
		String sQuery = "SELECT * FROM Account\n" + "WHERE UtilityAccountNumber = '" + sUtilityAccountNumber + "'";
		return sQuery;
	}

	/***********************************************************
	 * >>>>>>>>>>> ONE TIME PAYMENT PAGE QUERIES <<<<<<<<<<<<< *
	 ***********************************************************/
	// This query is for accounts with -ve balance
	public static String sResidentialAccountRegisteredBalanceAmountNegative = "";

	public static String sResidentialAccountResisteredBalanceAmountNegative = ";" + "DECLARE @AddressType TINYINT=1 "
			+ "IF OBJECT_ID('TempDB..#LatestBill','U') IS NOT NULL " + "DROP TABLE #LatestBill "
			+ "IF OBJECT_ID('TempDB..#LatestBill1','U') IS NOT NULL " + "DROP TABLE #LatestBill1 "
			+ "SELECT AccountNumber,MAX(BillingDate) AS BillingDate " + "INTO #LatestBill " + "FROM Billing(NOLOCK) "
			+ "GROUP BY AccountNumber " + "SELECT LB.AccountNumber,B.BillingID " + "INTO #LatestBill1 "
			+ "FROM #LatestBill LB "
			+ "JOIN Billing B(NOLOCK) ON LB.AccountNumber=B.AccountNumber AND LB.BillingDate=B.BillingDate "
			+ "SELECT TOP 1 CI.FullName,CI.Address1,CI.AddressType,CI.CityName,CI.StateName,CI.ZipCode, "
			+ "CI.MobilePhone,CI.EmailID,CI.UtilityAccountNumber,LB.AccountNumber,CONVERT(NUMERIC(18,2),Value) AS RemainingBalance "
			+ "FROM #LatestBill1 LB " + "JOIN BillingDetail BD(NOLOCK) ON LB.BillingID=BD.BillingID "
			+ "JOIN CustomerInfo CI(NOLOCK) ON LB.AccountNumber=CI.AccountNumber "
			+ "WHERE BD.HeadID=24 AND CONVERT(NUMERIC(18,2),Value)<=0 AND CI.AddressTypeID=@AddressType";

	public static String sUserBillNotGenerated = ""
			+ "SELECT TOP 1 CI.FullName,CI.Address1,CI.AddressType,CI.CityName,CI.StateName,CI.ZipCode,CI.MobilePhone,CI.EmailID,CI.UtilityAccountNumber,CI.AccountNumber"
			+ " FROM CustomerInfo CI(NOLOCK)" + " LEFT JOIN" + "( " + "SELECT DISTINCT AccountNumber "
			+ "FROM Billing(NOLOCK) " + ")B ON CI.AccountNumber=B.AccountNumber "
			+ "WHERE CI.AccountStatusID=1 AND B.AccountNumber IS NULL";

	/**
	 * This method returns payment received mail query.
	 * 
	 * @param sUserID
	 * @param sMailSub
	 * @return
	 */
	public static String getPaymentMailsQuery(String sUserID, String sMailSub) {
		String sQuery = "SELECT TOP 1 Message, IsNotify, CreateDate FROM ContractAccountNotifyEmail\n"
				+ "WHERE UserID = '" + sUserID + "'\n" + "AND Subject = '" + sMailSub + "'\n" + "ORDER BY ID DESC";
		return sQuery;
	}

	/***********************************************************
	 * >>>>>>>>>>>>>>>> SIGN OUT PAGE QUERIES <<<<<<<<<<<<<<<< *
	 ***********************************************************/

	/***********************************************************
	 * >>>>>>>>>>>>>>>> DASHBOARD PAGE QUERIES <<<<<<<<<<<<<<<< *
	 ***********************************************************/
	public static String getCompareDataForDashboardWidget(String sUtilityAccountNumber) {
		String sQuery = "SELECT UtilityAccountNumber, Usagedate, [Value], Consumed, FromDate, ToDate "
				+ "FROM CompareDataLanding\n" + "WHERE UtilityAccountNumber = '" + sUtilityAccountNumber + "'\n"
				+ "ORDER BY UsageDate DESC";
		return sQuery;
	}

	public static String getExistingEncryptedPasswordQuery(String sUserName) {
		String sQuery = "SELECT Password\n" + "FROM [User]\n" + "WHERE UserName = '" + sUserName + "'";
		return sQuery;
	}

	public static String updateExistingPassGivenPass(String sPassword, String sUserName) {
		String sQuery = "UPDATE [User]\n" + "SET Password = '" + sPassword + "'\n" + "WHERE UserName = '" + sUserName
				+ "'";
		return sQuery;
	}

	/***********************************************************
	 * >>>>>>>>>>> ACCOUNTS PROFILE PAGE QUERIES <<<<<<<<<<<<< *
	 ***********************************************************/
	/**
	 * Getting the profile information for a user
	 * 
	 * @param Username
	 * @return sProfileInfo query which brings Username, email id, Mobile number
	 *         etc.
	 */
	public static String getMyAccountProfileInfo(String Username) {
		String sProfileInfo = "Select * from [User] where UserName ='" + Username + "'";
		return sProfileInfo;
	}

	/**
	 * This query brings the default account type whether commercial(2) or
	 * residential(1).
	 */
	public static String sDefaultUserAccountType = ""
			+ "SELECT distinct AddressType FROM VCustomer WHERE IsDefaultAccount=1 and UserName='";

	/**
	 * This query brings the default account and address in the Adress list in
	 * the header in the below format. 3456, NorthDakota Ave (C002002003)
	 * 
	 * @param sUserName
	 * @return
	 */
	public static String getDefaultAccountAddressHeader(String sUserName) {
		String sDefaultAccountAddressHeaderQuery = "DECLARE @AccountNo INT=(SELECT UserId FROM [User] WHERE UserName='"
				+ sUserName + "')"
				+ " SELECT C.Address1+' ('+ UA.UtilityAccountNumber+')' as PropertyAddress FROM  [User] U (NOLOCK)"
				+ " JOIN  UserAccount UA (NOLOCK) ON UA.UserID=U.UserID"
				+ " JOIN  CustomerInfo C (NOLOCK) ON UA.Accountnumber = C.AccountNumber WHERE U.userid=@AccountNo"
				+ " AND NOT EXISTS (SELECT 1 from GuestAccessRequest ga WHERE AccessExpiryDate < getdate()"
				+ " AND ua.RequestID=ga.RequestID) and UA.ISDefaultAccount=1 ORDER BY UA.IsDefaultAccount DESC";
		return sDefaultAccountAddressHeaderQuery;
	}

	/**
	 * This query brings the default account and address in the Account Widget
	 * in Dashboard Page the header in the below format. 3456, NorthDakota
	 * Ave,Chino Hills,CA,-92602 (C002002003)
	 * 
	 * @param sUserName
	 * @return
	 */
	public static String getDefaultAddressAccountWidget(String sUserName) {
		String sDefaultAccountAddressHeaderQuery = "SELECT DISTINCT (Address1 +', '+ Address2 +', '+CityName+', '+(Select  StateCode from statemaster where StateName=(select Top 1 StateName from VCustomer WHERE IsDefaultAccount=1 and UserName='"
				+ sUserName
				+ "'))+' - '+ZipCode+' ('+ UtilityAccountNumber+')') AS AddressAccountNumber FROM VCustomer WHERE IsDefaultAccount=1 and UserName='"
				+ sUserName + "'";

		return sDefaultAccountAddressHeaderQuery;
	}

	/**
	 * This method is to get the query to get all the accounts having owner
	 * access.
	 * 
	 * @param userName
	 * @return
	 */
	public static String getAllAccountsHavingOwnerAccess(String userName) {
		String sAccountsHavingOwnerAccessQuery = "SELECT [UserAccount].UtilityAccountNumber FROM [UserAccount] "
				+ "Full OUTER JOIN [User] on [UserAccount].UserID=[User].UserID where [User].username='" + userName
				+ "' " + "and [UserAccount].RoleId='3'";
		return sAccountsHavingOwnerAccessQuery;
	}

	/**
	 * This query brings the all account and address in the Address list in the
	 * header in the below format. 3456, NorthDakota Ave (C002002003)
	 * 
	 * @param sUserName
	 * @return
	 */
	public static String getAllAccountAddressHeader(String sUserName) {
		String sAllAccountAddressHeader = "DECLARE @AccountNo INT=(SELECT UserId FROM [User] WHERE UserName='"
				+ sUserName + "') "
				+ "SELECT C.Address1+' ('+ UA.UtilityAccountNumber+')' as PropertyAddress FROM  [User] U (NOLOCK) "
				+ "JOIN  UserAccount UA (NOLOCK) ON UA.UserID=U.UserID "

				+ "JOIN  CustomerInfo C (NOLOCK) ON UA.Accountnumber = C.AccountNumber "
				+ "JOIN Account ac ON UA.Accountnumber = ac.AccountNumber "
				+ " WHERE ac.status <> 3 and U.userid=@AccountNo "
				+ "AND NOT EXISTS (SELECT 1 from GuestAccessRequest ga "

				+ "WHERE AccessExpiryDate < getdate() AND ua.RequestID=ga.RequestID)  ";
		return sAllAccountAddressHeader;
	}

	/**
	 * This query brings the default account in the header in the below format
	 * C002002003
	 * 
	 * @param sUserName
	 * @return
	 */
	public static String getDefaultAccount(String sUserName) {
		String sDefaultAccountQuery = "SELECT DISTINCT UtilityAccountNumber " + "FROM UserAccount "
				+ "WHERE UserID = (SELECT UserID FROM [User] WHERE UserName = '" + sUserName + "') "
				+ "AND IsDefaultAccount = '1'";
		return sDefaultAccountQuery;
	}

	public static String getAllAccountsLinkedToTheUserQuery(String sUserName) {
		String sQuery = "SELECT DISTINCT UtilityAccountNumber\r\n" + "FROM UserAccount\r\n"
				+ "WHERE UserID = (SELECT UserID FROM [User] WHERE UserName = '" + sUserName + "')";
		return sQuery;
	}

	public static String getAddressTypeQuery(String sUtilityAccountNumber) {
		String sQuery = "SELECT AddressType \n" + "FROM CustomerInfo\n" + "WHERE UtilityAccountNumber = '"
				+ sUtilityAccountNumber + "'";
		return sQuery;
	}

	/**
	 * This query brings the default
	 * 
	 * @param sUserName
	 * @return
	 */
	public static String getDefaultAddressAccountNumber(String sUserName) {
		String sDefaultAddressAccount = "SELECT DISTINCT (Address1 +' ('+ UtilityAccountNumber+')') AS AddressAccountNumber FROM VCustomer \n"
				+ "WHERE IsDefaultAccount=1 AND UserName='" + sUserName + "'";
		return sDefaultAddressAccount;
	}

	/**
	 * This query brings all the accounts associated with a user login.
	 *
	 * @param sUsername
	 * @return
	 */
	public static String getAllUtilityAccountByUsername(String sUsername) {
		String sAllUtilityAccountByUsername = "SELECT UA.UtilityAccountNumber FROM UserAccount UA JOIN [User] U ON UA.UserID=U.UserID WHERE U.UserName='"
				+ sUsername + "'";
		return sAllUtilityAccountByUsername;
	}

	/**
	 *
	 * @param sUsername
	 * @param sAccountNumber
	 * @return
	 */
	public static String setDefaultAccount(String sUsername, String sAccountNumber) {
		String sDefaultAccount = "DECLARE @UserName VARCHAR(100) " + " DECLARE @UtilityAccountNumber VARCHAR(100)"
				+ " SET @UserName='" + sUsername + "'" + " SET @UtilityAccountNumber='" + sAccountNumber + "'"
				+ " BEGIN TRANSACTION " + "UPDATE UA "
				+ " SET IsDefaultAccount=0 FROM UserAccount UA JOIN [User] U ON UA.UserID=U.UserID "
				+ " WHERE U.UserName=@UserName UPDATE UA 	SET IsDefaultAccount=1 "
				+ " FROM UserAccount UA JOIN [User] U ON UA.UserID=U.UserID "
				+ " WHERE U.UserName=@UserName AND UA.UtilityAccountNumber=@UtilityAccountNumber "
				+ " IF (SELECT COUNT(1) FROM UserAccount UA(NOLOCK) JOIN [User] U(NOLOCK) ON UA.UserID=U.UserID WHERE U.UserName=@UserName AND UA.IsDefaultAccount=1)=1 "
				+ " BEGIN 	COMMIT TRANSACTION " + "	SELECT 'Update was successful.' AS [Message] "
				+ " END ELSE BEGIN " + "	ROLLBACK TRANSACTION "
				+ "	SELECT 'Update was not successful due to some mismatch in data.' AS [Message] " + " END		";

		return sDefaultAccount;
	}

	/**
	 * Getting all plan associated with a utility account
	 *
	 * @param utilityAccountNumber
	 * @return sAllAssociatedPlan query which brings all associated plans value
	 *         with utility account
	 */
	public static String getAllAssociatedPlans(String utilityAccountNumber) {
		String sAllAssociatedPlan = "SELECT AddressPowerPlanId, WaterPlanId, GasPlanId, PVPowerPlanId FROM Account WHERE UtilityAccountNumber='"
				+ utilityAccountNumber + "'";
		return sAllAssociatedPlan;
	}

	/**
	 * This minMaxLengthSecurityAns will give the result of Min and Max length
	 * of Security Answers
	 */
	public static String minMaxLengthSecurityAns = "SELECT ParentHead, [Min Length],[Max Length] from( select a.ControlName AS ParentHead,b.ControlName,b.value from TemplateDetail a left join TemplateDetail b on a.TempDetailID=b.Parentid where b.ControlName IN ('Min Length','Max Length')  ) AS s  PIVOT (max(value)	for ControlName IN ([Min Length],[Max Length]) )as pvt where ParentHead in ('Security Question 1','Security Question 2')";

	/**
	 * This minMaxLenZipCode will give the result of Min and Max length of
	 * Security Answers
	 */
	public static String minMaxLenZipCode = "SELECT ParentHead, [Min Length],[Max Length] from( select a.ControlName AS ParentHead,b.ControlName,b.value from TemplateDetail a left join TemplateDetail b on a.TempDetailID=b.Parentid where b.ControlName IN ('Min Length','Max Length')  ) AS s  PIVOT (max(value)	for ControlName IN ([Min Length],[Max Length]) )as pvt where ParentHead = 'Zip Code'";

	/**
	 * This mailingAddress will give the query for mailing Address of a user
	 */
	public static String getMailingAddress(String userName) {
		String mailingAddrQuery = "Select convert(varchar(30),ExpiryDate,101) as ExpDate, * from UserCommunicationAddress where MailAddressType = '1' and UserID= (select UserId from [User] where UserName='"
				+ userName + "')";
		return mailingAddrQuery;
	}

	/**
	 * This temporaryMailingAddress will give the query for temporary mailing
	 * Address of a user
	 */
	public static String getTemporaryMailingAddress(String userName) {
		String tempMailingAddrQuery = "Select convert(varchar(30),ExpiryDate,101) as ExpDate, * from UserCommunicationAddress where MailAddressType = '2' and UserID= (select UserId from [User] where UserName='"
				+ userName + "')";
		return tempMailingAddrQuery;
	}

	/**
	 * This method is to get all Accounts(Owner/Guest) of a User, takes Username
	 * as input and give all Accounts of this user
	 */
	public static String getAllLinkedAccountAccounts(String username) {
		String sQuery = "SELECT UtilityAccountNumber, RoleID FROM UserAccount where UserID = (Select UserID FROM [User] where UserName='"
				+ username + "')";
		return sQuery;
	}

	// Accounts

	/*
	 * Get Security Question by Question ID
	 */
	public static String getSecurityQueByItsId(String questionID) {
		String sQuery = "select ControlText from multilingualmaster where LanguageCode = 'EN' and ControlId=(Select ControlId from SecurityQuestion WHERE QuestionId = '"
				+ questionID + "')";
		return sQuery;
	}

	/*****************************************************************************************
	 * ACCOUNTS SETTING PAGE QUERIES *
	 *****************************************************************************************/
	/**
	 * This query brings the my account setting info's saved by the account.
	 *
	 * @param UtilityAccountNumber
	 * @param Username
	 * @return
	 */
	public static String getMyAccountSettingConfig(String UtilityAccountNumber, String Username) {
		String sAccountSetting = "SELECT * FROM [UserAccount]  WHERE UtilityAccountNumber = '" + UtilityAccountNumber
				+ "'" + " AND UserID=(Select UserID from [User] where UserName ='" + Username + "'" + ")";
		return sAccountSetting;
	}

	/**
	 * This query brings the my account setting info's saved by the account.
	 *
	 * @param UtilityAccountNumber
	 * @param Username
	 * @return
	 */
	public static String getMyAccountSettingLanguageConfig(String Username) {
		String sUserLanguageSetting = "Select * from LanguageMaster where LanguageGuId = (Select LanguageGuId from UserLanguage WHERE UserID =(Select UserID from [User] where UserName ='"
				+ Username + "'" + ")" + ")";
		return sUserLanguageSetting;
	}

	/**
	 * This method is to get the query to update the default payment method.
	 * 
	 * @param sUsername
	 * @param sAccountNum
	 * @param sPaymentType
	 *            |=> For 'Pay as you Go' = '0' and 'Monthly' = '1'
	 * @return
	 */
	public static String getQueryToUpdatePaymentMethodForAcc(String sAccountNum, String sUsername,
			String sPaymentType) {
		String sQuery = "UPDATE [UserAccount] SET DefaultPaymentType = '" + sPaymentType
				+ "' WHERE  UtilityAccountNumber = '" + sAccountNum
				+ "' AND UserID=(Select UserID from [User] where UserName ='" + sUsername + "');";
		return sQuery;
	}

	/**
	 * This method is to get the query to update the default payment method.
	 * 
	 * @param sUsername
	 * @param sLanguageCode
	 *            + Englist = EN, French = FR, Spanish = ES
	 * @return
	 */
	public static String getQueryToUpdateLanguageForUser(String sLanguageCode, String sUsername) {
		String sQuery = "UPDATE UserLanguage set LanguageGuId = (Select LanguageGuId from LanguageMaster where LanguageCode = '"
				+ sLanguageCode + "') WHERE UserID =(Select UserID from [User] where UserName ='" + sUsername + "')";
		return sQuery;
	}

	/************************************************************
	 * >>>>>>>> NOTIFICATION PREFERENCE PAGE QUERIES <<<<<<<<<< *
	 ************************************************************/

	/************************************************************
	 * >>>>>>>>>>>>> PAYMENT INFO PAGE QUERIES <<<<<<<<<<<<<<<< *
	 ************************************************************/
	/**
	 * This query brings the my account setting info's saved by the account.
	 *
	 * @param UtilityAccountNumber
	 * @param Username
	 * @return
	 */
	/*
	 * public static String getDefaultPayId(String UserId, String
	 * PaymentMethodType) { String sPaymentInfo =
	 * "select Top 1 PaymentAccount, CCExpMonth, CCExpYear, BankRTE, FirstName, BankName from PaymentProfiles where ExternalId= '"
	 * + UserId + "' and ProfileStatus=1 and PaymentMethodType=" +
	 * PaymentMethodType + " order by CreatedAt desc";
	 * 
	 * return sPaymentInfo; }
	 */

	/**
	 * This query brings all the Payment info for a user id.
	 * 
	 * @param UserId
	 * @return It comes from the Payment Database
	 */
	public static String getAllPaymentInfo(String UserId) {
		String sPaymentInfo = "Select FirstName,BankRTE, BankName , PaymentAccount, CCExpMonth, CCExpYear "
				+ "from PaymentProfiles where ProfileStatus =1 and ( (CCExpMonth>=month(getdate()) and CCExpYear=RIGHT(year(getdate()),2))OR "
				+ "( CCExpMonth>=1 and CCExpYear>RIGHT(year(getdate()),2))OR ACHType = 1 AND ISNULL(FirstName,'')!='') AND ExternalId= '"
				+ UserId + "' order by CreatedAt desc";

		return sPaymentInfo;
	}

	/**
	 * This method returns the latest payment method added for the user
	 * 
	 * @param UserId
	 * @return
	 */
	public static String getLatestAddedPaymentInfo(String UserId) {
		String sPaymentInfo = "Select Top 1 FirstName,BankRTE, BankName , PaymentAccount, CCExpMonth, CCExpYear, ProfileStatus "
				+ "from PaymentProfiles where ProfileStatus =1 and ( (CCExpMonth>=month(getdate()) and CCExpYear=RIGHT(year(getdate()),2))OR "
				+ "( CCExpMonth>=1 and CCExpYear>RIGHT(year(getdate()),2))OR ACHType = 1 AND ISNULL(FirstName,'')!='') AND ExternalId= '"
				+ UserId + "' order by CreatedAt desc";
		return sPaymentInfo;
	}

	/**
	 * This method returns the latest payment method added for the user in Chase
	 * Database
	 * 
	 * @param sAccountNo
	 * @return
	 */
	public static String getLatestAddedPaymentInfoChase(String sAccountNo) {
		String sPaymentInfo = "SELECT TOP 1 FirstName, BankName, BankRouting, BankAccountNumber, "
				+ "CCAccountNumber, CcExp, Cvv, PaymentType, ProfileStatus, IsDeleted, ACHType \n"
				+ "FROM PaymentProfiles\n" + "WHERE ServiceAccountNumber = '" + sAccountNo + "'\n"
				+ "ORDER BY CreatedDate DESC";
		return sPaymentInfo;
	}

	/**
	 * This method returns the Defualt Payid or Token which is by the
	 * getDefaultPaymentInfo method for getting the Default Payment Info
	 * 
	 * @param Username
	 * @return
	 */
	public static String getDefaultPayToken(String Username) {
		String sPaymentToken = "SELECT DefaultPayId FROM defaultpayment acc "
				+ "WHERE UserId = (Select UserId from [USER] WHERE UserName='" + Username + "')";
		return sPaymentToken;
	}

	/**
	 * This method returns the external id which is by the getDefaultPaymentInfo
	 * method for getting the Default Payment Info
	 * 
	 * @param Username
	 * @return
	 */
	public static String getExternalId(String Username) {
		String sExternalId = "Select UserId from [USER] WHERE UserName='" + Username + "'";
		return sExternalId;
	}

	/**
	 * This method returns the Default Payment Info for a User
	 * 
	 * @param PaymentToken
	 * @return
	 */
	public static String getDefaultPaymentInfo(String PaymentToken, String ExternalId) {
		String sPaymentInfo = "select Top 1 FirstName,BankRTE, BankName , PaymentAccount, CCExpMonth, "
				+ "CCExpYear from PaymentProfiles where PaymentToken='" + PaymentToken + "'" + "  AND ExternalId='"
				+ ExternalId + "'" + "and FirstName IS NOT NULL order by CreatedAt desc";

		return sPaymentInfo;
	}

	/*****************************************************************************************
	 * ACCOUNT MARKETING PREFERENCE PAGE QUERIES *
	 *****************************************************************************************/
	public static String getMarketingPrefQuery(String username) {
		String marketingPrefQuery = "Select PreferenceId from UserMarketingPreferenceSetting where UserID = (Select UserID from [User] where UserName = '"
				+ username + "')";
		return marketingPrefQuery;
	}

	public static String getMarketingPrefEmailQuery(String email, String subject) {
		String marketingPrefEmailQuery = "SELECT TOP 1 * FROM dbo.ContractAccountNotifyEmail WHERE EmailId = '" + email
				+ "' and subject like '" + subject + "%' ORDER BY 1 desc";
		return marketingPrefEmailQuery;
	}

	/*****************************************************************************************
	 * GUEST USER PAGE QUERIES *
	 *****************************************************************************************/
	/**
	 * This method is to get the query to get the guest user invite mail content
	 * and sent status.
	 * 
	 * @param emailId
	 * @param subject
	 * @return
	 */
	public static String getGuestUserInviteMailContentQuery(String emailId, String subject) {
		String inviteMailContentQuery = "SELECT TOP 1 Message, IsNotify FROM ContractAccountNotifyEmail\r\n"
				+ "WHERE EmailId='" + emailId + "'\r\n" + "AND Subject LIKE '" + subject + "%'\r\n"
				+ "ORDER BY ID DESC";
		return inviteMailContentQuery;
	}

	/**
	 * This method is to get all the linked accounts to the user with there role
	 * id, address and address type.
	 * 
	 * @param sUsername
	 * @return
	 */
	public static String getAllLinkedAccountsWithRoleId(String sUsername) {
		String sQuery = "DECLARE @AccountNo INT=(SELECT UserId FROM [User] WHERE UserName='" + sUsername + "') "
				+ "SELECT UA.UtilityAccountNumber, UA.RoleID, C.Address1, C.AddressType  FROM  [User] U (NOLOCK) "
				+ "JOIN  UserAccount UA (NOLOCK) ON UA.UserID=U.UserID "
				+ "JOIN  CustomerInfo C (NOLOCK) ON UA.Accountnumber = C.AccountNumber WHERE U.userid=@AccountNo "
				+ "AND NOT EXISTS (SELECT 1 from GuestAccessRequest ga WHERE AccessExpiryDate < getdate() "
				+ "AND ua.RequestID=ga.RequestID) ORDER BY UA.IsDefaultAccount DESC";
		return sQuery;
	}

	/**
	 * This method is to get the user to whom email id is linked.
	 * 
	 * @param sEmail
	 * @param sUsername
	 * @return
	 */
	public static String getUserToWhomEmailIdIsLinked(String sEmail, String sUsername) {
		String sQuery = "SELECT TOP 1 UserName, Password FROM [User] WHERE EmailID = '" + sEmail + "' AND Status = '1'"
				+ " AND UserName!='" + sUsername + "'";
		return sQuery;
	}

	/**
	 * This method is to get the encrypted password.
	 * 
	 * @param sUsername
	 * @return
	 */
	public static String getEncryptedPasswordOfUser(String sUsername) {
		String sQuery = "SELECT UserName, Password FROM [User] WHERE UserName = '" + sUsername + "'";
		return sQuery;
	}

	/**
	 * This method is to get the query to update the password for user.
	 * 
	 * @param sUsername
	 * @param sPassword
	 * @return
	 */
	public static String getQueryToUpdatePasswordForUser(String sUsername, String sPassword) {
		String sQuery = "UPDATE [User]" + "SET Password = '" + sPassword + "' " + "WHERE UserName = '" + sUsername
				+ "'";
		return sQuery;
	}

	/**
	 * This method is to get the query to update the Primary Email Address for
	 * user.
	 * 
	 * @param sUsername
	 * @param sEmailID
	 * @return
	 */
	public static String getQueryToUpdatePrimaryEmailAddrForUser(String sUsername, String sEmailID) {
		String sQuery = "UPDATE [User]" + "SET EmailID = '" + sEmailID + "' " + "WHERE UserName = '" + sUsername + "'";
		return sQuery;
	}

	/**
	 * This method used to update the access expiry date of the guest user
	 * invite.
	 * 
	 * @param sAccountNumber
	 * @param sDate
	 * @return
	 */
	// public static String getQueryToUpdateAccessExpireDate(String
	// sAccountNumber, String sDate) {
	// String sDateFormat = DateUtil.changeStringToDateInFormat(sDate,
	// "yyyy-MM-dd", "yyyy-MM-d");
	// String sQuery = "UPDATE GuestAccessRequest " + "SET AccessExpiryDate = '"
	// + sDateFormat + " 00:00:00.000' "
	// + "WHERE UtilityAccountNumber = '" + sAccountNumber + "' AND "
	// + "RequestID = (SELECT Top 1 RequestID FROM GuestAccessRequest " + "WHERE
	// UtilityAccountNumber = '"
	// + sAccountNumber + "' ORDER BY LastUpdated DESC)";
	// return sQuery;
	// }

	/**
	 * This method is to get the csp guest user configuration.
	 * 
	 * @return
	 */
	public static String getCspGuestUserConfig() {
		String sQuery = "SELECT ConfigOption, ConfigValue FROM UtilityConfig WHERE "
				+ "ModuleName= 'GuestUserConfiguration'";
		return sQuery;
	}

	public static String getLoggedInUsersEmail(String sUsername) {
		String sQuery = "SELECT EmailID\n" + "FROM [User]\n" + "WHERE UserName='" + sUsername + "'";
		return sQuery;
	}

	/*****************************************************************************************
	 * ABOUT MY HOME PAGE QUERIES *
	 *****************************************************************************************/

	/**
	 * This method is to get the Residential Accounts of a User, takes Username
	 * as input and gives all Residential Owner Accounts of this user
	 */
	public static String getResidentialOwnerAccounts(String username) {
		String sQuery = "Select CustomerAddress.UtilityAccountNumber FROM CustomerAddress join (Select [User].UserID, [UserAccount].UtilityAccountNumber FROM [UserAccount] join [User] on [UserAccount].UserID = [User].UserID  WHERE RoleID = '3' and UserName='"
				+ username
				+ "') as A on CustomerAddress.UtilityAccountNumber = A.UtilityAccountNumber where AddressType= '1'";
		return sQuery;
	}

	/**
	 * This method is to get the Business/Commercial Accounts of a User, takes
	 * Username as input and gives all Business/Commercial Owner Accounts of
	 * this user
	 */
	public static String getCommercialOwnerAccounts(String username) {
		String sQuery = "Select CustomerAddress.UtilityAccountNumber FROM CustomerAddress join (Select [User].UserID, [UserAccount].UtilityAccountNumber FROM [UserAccount] join [User] on [UserAccount].UserID = [User].UserID  WHERE RoleID = '3' and UserName='"
				+ username
				+ "') as A on CustomerAddress.UtilityAccountNumber = A.UtilityAccountNumber where AddressType= '2'";
		return sQuery;
	}

	/*****************************************************************************************
	 * BILLING DASHBOARD PAGE QUERIES *
	 *****************************************************************************************/
	/**
	 *
	 * @param sUtilityAccountNumber
	 * @return
	 */
	public static String getOneTimePaymentDetails(String sUtilityAccountNumber) {
		String sGetOneTimePaymentDetails = ";" + "SELECT "
				+ " [Extent1].[UtilityAccountNumber] AS [UtilityAccountNumber], [Extent1].[MobilePhone] AS [MobilePhone], "
				+ " [Extent1].[FullName] AS [FullName], [Extent1].[EmailID] AS [EmailID], [Extent2].[FirstName] AS [FirstName], "
				+ " [Extent2].[LastName] AS [LastName], [Extent4].[Address1] AS [Address1], [Extent4].[ZipCode] AS [ZipCode], "
				+ " [Extent6].[CityName] AS [CityName], [Extent7].[StateName] AS [StateName], "
				+ " [Extent9].Value AS [RemainingBalance] " + "  FROM  [dbo].[CustomerInfo] AS [Extent1] "
				+ "INNER JOIN [dbo].[Customer] AS [Extent2] ON [Extent1].[CustomerId] = [Extent2].[CustomerId] "
				+ "  INNER JOIN [dbo].[Account] AS [Extent3] ON [Extent1].[AccountNumber] = [Extent3].[AccountNumber] "
				+ "  INNER JOIN [dbo].[CustomerAddress] AS [Extent4] ON [Extent3].[AddressId] = [Extent4].[AddressId] "
				+ "  INNER JOIN [dbo].[CityMaster] AS [Extent6] ON [Extent4].[CityId] = [Extent6].[CityID] "
				+ "  INNER JOIN [dbo].[StateMaster] AS [Extent7] ON [Extent6].[StateID] = [Extent7].[StateID] "
				+ " INNER JOIN (SELECT max(billingid) AS billingid,Accountnumber "
				+ "             FROM [dbo].[billing] "
				+ "            GROUP BY Accountnumber) AS [Extent8] ON [Extent3].Accountnumber=[Extent8].Accountnumber "
				+ "    INNER JOIN [dbo].[billingdetail] AS [Extent9] ON [Extent8].billingId=[Extent9].billingId "
				+ "   WHERE [Extent9].Headid=24  " + "       And  [Extent1].[UtilityAccountNumber] = '"
				+ sUtilityAccountNumber + "'";
		return sGetOneTimePaymentDetails;
	}

	/**
	 * This method used to get the query to get the details on utility bill
	 * page.
	 * 
	 * @param UtilityAccountNumber
	 * @param sLanguage
	 * @return
	 */
	public static String getDetailsUtilityBillPage(String UtilityAccountNumber, String sLanguage) {
		String sDetailsUtilityBillPage = "Declare @utilityaccountnumber VARCHAR(100) = '" + UtilityAccountNumber + "', "
				+ "@LanguageCode VARCHAR(10) = '" + sLanguage + "', \n"
				+ "@BillingDate DATETIME = NULL DECLARE @BillingId INT, @FromDate DATE, @ToDate DATE, \n"
				+ "@DefaultPaymentType BIT, @LastRechargeAmount NUMERIC(12, 2), @CustomerId INT, @AccountNumber INT \n"
				+ "SELECT TOP 1 @CustomerId = CA.CustomerId, @DefaultPaymentType = DefaultPaymentType                 \n"
				+ "FROM CustomerAddress CA WITH(NOLOCK) JOIN Account A WITH(NOLOCK) ON(A.AddressId = CA.AddressId)\n"
				+ "WHERE A.AccountNumber = @AccountNumber \n" + "SELECT @AccountNumber = AccountNumber \n"
				+ "FROM account \n" + "WHERE utilityaccountnumber = @utilityaccountnumber\n"
				+ "IF @BillingDate IS NULL \n"
				+ "SELECT TOP 1 @BillingId = BillingId, @FromDate = PeriodFrom, @ToDate = PeriodTo \n"
				+ "FROM Billing WITH (NOLOCK) \n" + "WHERE AccountNumber = @AccountNumber ORDER BY BillingDate DESC \n"
				+ "ELSE \n" + "SELECT TOP 1 @BillingId = BillingId, @FromDate = PeriodFrom, @ToDate = PeriodTo \n"
				+ "FROM Billing WITH (NOLOCK) \n"
				+ "WHERE AccountNumber = @AccountNumber AND BillingDate = @BillingDate \n"
				+ "BEGIN SELECT TOP 1 @LastRechargeAmount = TransactionAmount \n"
				+ "FROM BillingTransaction WITH(NOLOCK) \n"
				+ "WHERE AccountNumber = @AccountNumber AND IsPrepay = 1 ORDER BY TransactionDate DESC \n"
				+ "SELECT dbo.getMultilingualMessage(BHM.HeadControlId, @LanguageCode, 'N', DEFAULT) AS [HeaderName], \n"
				+ "CASE WHEN BHM.HeadId = 24 AND ISNUMERIC(BD.Value) = 1 AND CAST(BD.Value AS NUMERIC(13,2)) < 0 \n"
				+ "AND BHM.HeadId = 24 AND @DefaultPaymentType = 1 THEN CAST(ABS(CAST(BD.Value AS NUMERIC(13,2))) AS VARCHAR(12)) + ' CR' \n"
				+ "WHEN BHM.HeadId = 24 AND ISNUMERIC(BD.Value) = 1 AND CAST(BD.Value AS NUMERIC(13,2)) > 0 \n"
				+ "AND BHM.HeadId = 24 AND @DefaultPaymentType = 1 THEN CAST(ABS(CAST(BD.Value AS NUMERIC(13,2))) AS VARCHAR(12)) \n"
				+ "WHEN BHM.HeadId = 5 THEN CAST(ABS(CAST((CASE WHEN CHARINDEX('E',BD.Value) > 0 THEN CAST(BD.Value AS FLOAT) \n"
				+ "ELSE BD.Value END) AS NUMERIC(15,2))) AS VARCHAR(12)) + ' kWh' \n"
				+ "WHEN BHM.HeadId = 8 THEN cast(cast(BD.Value as numeric(10,2)) as varchar) + ' HCF' \n"
				+ "WHEN BHM.HeadId = 11 THEN CAST(ABS(CAST(BD.Value AS NUMERIC(15,2))) AS VARCHAR(12)) + ' CCF' \n"
				+ "WHEN BHM.HeadId in (9, 19, 20, 22, 23, 24, 6) THEN cast(cast(BD.Value as decimal(10,2)) as varchar) \n"
				+ "ELSE BD.Value END Value, BHM.ColorCode \n" + "FROM BillingHeadMaster BHM WITH (NOLOCK) \n"
				+ "LEFT OUTER JOIN BillingDetail BD WITH (NOLOCK) ON (BD.HeadId = BHM.HeadId AND BD.BillingId = @BillingId) \n"
				+ "WHERE BHM.IsActive = 1 AND (BD.Value IS NOT NULL OR BHM.HeaderType = 1) AND BHM.IsPDFHead = 0 \n"
				+ "AND (BHM.Section IN (SELECT FeatureName \n" + "FROM FeatureSettings FS WITH (NOLOCK) \n"
				+ "WHERE FS.[Status] = 1) or BHM.Section = 'BillDetails') ORDER BY BHM.SortOrder END\n";

		return sDetailsUtilityBillPage;
	}

	/**
	 * This method is to get the payments details query.
	 * 
	 * @param sAccountNo
	 * @return
	 */
	public static String getPaymentsDetailsQuery(String sAccountNo) {
		String sQuery = null;
		sQuery = "SELECT TOP 1 * FROM Payments\n" + "WHERE ServiceAccountNumber = '" + sAccountNo + "'\n"
				+ "ORDER BY 1 DESC";
		return sQuery;
	}

	/*****************************************************************************************
	 * BILLING PAYMENT PAGE QUERIES *
	 *****************************************************************************************/
	public static String getLatestPaymentProfile(String sAccountNumber) {
		String sQuery = "SELECT TOP 1 CustomerRefNum, PaymentType, CCAccountNumber, BankAccountNumber, "
				+ "CcExp, PaymentType, FirstName, ProfileStatus, BankRouting, BankName \n" + "FROM PaymentProfiles\n"
				+ "WHERE ServiceAccountNumber = '" + sAccountNumber + "'\n" + "ORDER BY PaymentProfileId DESC";
		return sQuery;
	}

	/*****************************************************************************************
	 * PAYMENT PAGE QUERIES *
	 *****************************************************************************************/

	/*****************************************************************************************
	 * RECURRING PAYMENT PAGE QUERIES *
	 *****************************************************************************************/
	/**
	 * This method is to get the query to get the non ami meters count linked to
	 * the given utility account number.
	 * 
	 * @param sUtilityAccountNumber
	 * @return
	 */
	public static String getQueryCheckAccEnrollForAutoPay(String sUtilityAccountNumber) {
		String sAccEnrollForAutoPay = "Select COUNT(*) AS AutoPayEnrolledCount FROM AccountRecurringPayment WHERE "
				+ "AccountNumber=(SELECT AccountNumber FROM Account WHERE UtilityAccountNumber='"
				+ sUtilityAccountNumber + "')";
		return sAccEnrollForAutoPay;
	}

	/**
	 * This method is to get the query to get the accounts enrolled for Auto
	 * Pay.
	 * 
	 * @param sUsername
	 * @return
	 */
	public static String getQueryAccountsEnrollForAutoPay(String sUsername) {
		String sAccountsEnrollForAutoPay = "SELECT UtilityAccountNumber FROM Account " + "WHERE AccountNumber "
				+ "IN (SELECT AccountNumber FROM AccountRecurringPayment " + "WHERE AccountNumber "
				+ "IN (SELECT AccountNumber FROM UserAccount "
				+ "WHERE UserID = (SELECT UserID FROM [User] WHERE UserName = '" + sUsername + "') "
				+ "AND RoleID in (3)))";
		return sAccountsEnrollForAutoPay;
	}

	public static String getQueryCountAccEnrolledForAutopay(String sUsername) {
		String sQuery = "SELECT COUNT (UtilityAccountNumber) AS UtilityAccountNumber FROM Account "
				+ "WHERE AccountNumber " + "IN (SELECT AccountNumber FROM AccountRecurringPayment "
				+ "WHERE AccountNumber " + "IN (SELECT AccountNumber FROM UserAccount "
				+ "WHERE UserID = (SELECT UserID FROM [User] " + "WHERE UserName = '" + sUsername + "') "
				+ "AND RoleID IN (3)))";
		return sQuery;
	}

	/**
	 * This method is to get the auto pay enrollment confirmation email.
	 * 
	 * @param sEmail
	 * @param sSubject
	 * @return
	 */
	public static String getAutoPayEnrollConfirmationEmail(String sEmail, String sSubject) {
		String sQuery = "SELECT TOP 1 CustomerName, EmailId, [subject], EmailMsg, CreatedDate\n"
				+ "FROM ContractAccountNotify\n" + "WHERE EmailId = '" + sEmail + "'\n" + "AND subject = '" + sSubject
				+ "'\n" + "ORDER BY ContractAccountId DESC";
		return sQuery;
	}

	/*****************************************************************************************
	 * >>>>>>>>>>>>>>>>>>>>>> BILLING HISTORY PAGE QUERIES
	 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< *
	 *****************************************************************************************/
	/**
	 * This method will return query to get Bill Statement of a given Utility
	 * Account
	 * 
	 * @param sUtilityAccountNumber
	 * @return sQueryBillingStatements
	 */
	public static String getBillStatementQuery(String sUtilityAccountNumber) {
		String sQueryBillingStatements = "SELECT Billing.BillingDate, BillingDetail.value from BillingDetail join Billing on BillingDetail.BillingID=Billing.BillingID where BillingDetail.HeadId='22' and Billing.AccountNumber=(select AccountNumber from account where utilityAccountNumber='"
				+ sUtilityAccountNumber + "')  order by 1 desc";
		return sQueryBillingStatements;
	}

	/**
	 * This method will return query to get Payments of a given Utility Account
	 * 
	 * @param sUtilityAccountNumber
	 * @return sQueryBillingStatements
	 */
	public static String getPaymentsQuery(String sUtilityAccountNumber) {
		String sQueryPayments = "SELECT TransactionDate, TransactionAmount FROM BillingTransaction where accountnumber=(select AccountNumber from account where utilityAccountNumber='"
				+ sUtilityAccountNumber + "' and TransactionStatus ='1')  order by 1 desc";
		return sQueryPayments;
	}

	/*****************************************************************************************
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>> BUDGET BILL PAGE QUERIES
	 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< *
	 *****************************************************************************************/
	/**
	 * This method is to get the query to get the non ami meters count linked to
	 * the given utility account number.
	 * 
	 * @param sUtilityAccountNumber
	 * @return
	 */
	public static String getNonAMIMetersCountLinkedToAccount(String sUtilityAccountNumber) {
		String sNonAMIMeterCounts = "SELECT COUNT(*) AS NonAMIMeterCount FROM AccountMeterMapping WHERE "
				+ "AccountNumber=(SELECT AccountNumber FROM Account WHERE UtilityAccountNumber='"
				+ sUtilityAccountNumber + "') AND ISAMI=0";
		return sNonAMIMeterCounts;
	}

	/**
	 * This method is to get the maximum monthly budget limit for residential
	 * and commercial accounts.
	 * 
	 * @return
	 */
	public static String getMaxMonthlyBudgetLimit() {
		String sQuery = "SELECT MonthlyBudgetMaxLimit, IMonthlyBudgetMaxLimit FROM UtilitySettings";
		return sQuery;
	}

	/*****************************************************************************************
	 * RATE ANALYSIS PAGE QUERIES *
	 *****************************************************************************************/

	/*****************************************************************************************
	 * LEVEL PAY PAGE QUERIES *
	 *****************************************************************************************/

	/*****************************************************************************************
	 * PAYMENT LOCATION QUERIES *
	 *****************************************************************************************/
	public static String getPaymentLocationListQuery() {
		String sQueryPaymentLocationList = "SELECT LocationName, Address1, CityName, PaymentLocWebsite, PaymentToDay, PayTimeFrom, PayTimeTo, ContactNo, Emailid FROM PaymentLocation LEFT JOIN CityMaster ON PaymentLocation.CityID = CityMaster.CityID where IsDeleted='0'and CityName != 'null'";
		return sQueryPaymentLocationList;
	}

	/*****************************************************************************************
	 * USAGE PAY PAGE QUERIES *
	 *****************************************************************************************/
	/**
	 * This query method is to get all meter counts for a utillity account
	 * sUtilityAccountNumber= C002002003
	 *
	 * @param sUtilityAccountNumber
	 * @return
	 */
	public static String getAllMeterTypesCount(String sUtilityAccountNumber, String Username) {
		String sAllMeterTypeCount = "SELECT Metertype, count(metertype) AS MeterCount " + "FROM accountmetermapping "
				+ "WHERE accountnumber = (SELECT accountnumber " + "FROM UserAccount " + "WHERE UtilityAccountNumber ='"
				+ sUtilityAccountNumber + "' " + "AND UserID = (SELECT UserID FROM [User] WHERE UserName='" + Username
				+ "')) and Status !=0 " + "GROUP BY metertype";
		return sAllMeterTypeCount;
	}

	/**
	 * This query method is to get specific meter counts for a utillity account
	 * sUtilityAccountNumber= C002002003 sMeterType = P, W, G, PV(Power, Water,
	 * Gas, Solar) iMeterStatus = 0,1(Non AMI-0, AMI-1)
	 *
	 * @param sUtilityAccountNumber
	 * @param sMeterType
	 * @param iMeterStatus
	 * @return
	 */
	public static String getCountSpecificMeterForAccountQuery(String sUtilityAccountNumber, String sMeterType,
			int iMeterStatus) {
		String sQuerySpecificMeters = ";"
				+ "SELECT Count (DISTINCT MeterNumber) as MeterNumber FROM AccountMeterMapping WHERE AccountNumber=(Select AccountNumber from Account where UtilityAccountNumber='"
				+ sUtilityAccountNumber + "') and MeterType='" + sMeterType + "' and IsAMI=" + iMeterStatus
				+ " and Status !=0";

		return sQuerySpecificMeters;
	}

	/**
	 * This query method is to get all the meter counts for a utillity account
	 * sUtilityAccountNumber= C002002003 sMeterType = P, W, G, PV(Power, Water,
	 * Gas, Solar) iMeterStatus = 0,1(Non AMI-0, AMI-1)
	 *
	 * @param sUtilityAccountNumber
	 * @param sMeterType
	 * @return
	 */
	public static String getCountAllMetersForAccountQuery(String sUtilityAccountNumber, String sMeterType) {
		String sQuerySpecificMeters = ";"
				+ "SELECT Count (DISTINCT MeterNumber) as MeterNumber FROM AccountMeterMapping WHERE AccountNumber=(Select AccountNumber from Account where UtilityAccountNumber='"
				+ sUtilityAccountNumber + "'" + ") and MeterType='" + sMeterType + "'" + " and Status !=0";
		return sQuerySpecificMeters;
	}

	/**
	 * This query method is to get all the meter names for a utillity account
	 * sUtilityAccountNumber= C002002003 sMeterType = P, W, G, PV(Power, Water,
	 * Gas, Solar) iMeterStatus = 0,1(Non AMI-0, AMI-1)
	 *
	 * @param sUtilityAccountNumber
	 * @param sMeterType
	 * @return
	 */
	public static String getUtilityAllMeterNameQuery(String sUtilityAccountNumber, String sMeterType) {
		String sQuerySpecificMeters = ";"
				+ "SELECT MeterNumber FROM AccountMeterMapping WHERE AccountNumber=(Select AccountNumber from Account where UtilityAccountNumber='"
				+ sUtilityAccountNumber + "') and MeterType='" + sMeterType + "'" + " and Status='1'";

		return sQuerySpecificMeters;
	}

	/**
	 * This query method is to get all the meter names for a utillity account
	 * sUtilityAccountNumber= C002002003 sMeterType = P, W, G, PV(Power, Water,
	 * Gas, Solar) iMeterStatus = 0,1(Non AMI-0, AMI-1).
	 *
	 * @param sUtilityAccountNumber
	 * @param sMeterType
	 * @param iMeterStatus
	 * @return
	 */
	public static String getUtilitySpecificMeterNameQuery(String sUtilityAccountNumber, String sMeterType,
			int iMeterStatus) {

		String sQuerySpecificMeters = ";"
				+ "SELECT MeterNumber FROM AccountMeterMapping WHERE AccountNumber=(Select AccountNumber from Account where UtilityAccountNumber='"
				+ sUtilityAccountNumber + "') and MeterType='" + sMeterType + "' and IsAMI=" + iMeterStatus
				+ " and Status !=0";

		return sQuerySpecificMeters;
	}

	/**
	 * This query method is to get the 1st Solar meter name for a utillity
	 * account sUtilityAccountNumber= C002002003.
	 *
	 * @param sUtilityAccountNumber
	 */
	public static String getUtilitySolarMeterNameQuery(String sUtilityAccountNumber) {
		String sQuerySpecificMeters = ";"
				+ "SELECT Top 1 MeterNumber FROM AccountMeterMapping WHERE AccountNumber=(Select AccountNumber from Account where UtilityAccountNumber='"
				+ sUtilityAccountNumber + "') and MeterType='PV'" + " and Status !=0";

		return sQuerySpecificMeters;
	}

	/**
	 *
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @param sTableName
	 * @param iCspConfiguredMonths
	 * @return
	 */
	public static String getQueryCountDataAvailableUsageMonths(String sUtilityAccountNumber, String sMeterNumer,
			String sTableName, int iCspConfiguredMonths) {

		String sCountDataAvailableUsageMonths = ";" + "declare @utilityaccountnumber varchar(100) ='"
				+ sUtilityAccountNumber + "' " + ",@MeterNumber varchar(100)='" + sMeterNumer + "' "
				+ ",@AccountNumber bigint " + ",@Meterid bigint "

				+ "select @AccountNumber=AccountNumber from account where utilityaccountnumber=@utilityaccountnumber "
				+ "select @Meterid=Meterid from AccountMeterMapping "
				+ "where AccountNumber = (select AccountNumber from account where utilityaccountnumber=@utilityaccountnumber) "
				+ "and MeterNumber=@MeterNumber "

				+ "select count(1) from " + sTableName
				+ " where AccountNumber=@AccountNumber and Meterid=@Meterid and usagedate>=dateadd(mm,-"
				+ iCspConfiguredMonths + ",getdate()) and rateplandetailid=1";

		return sCountDataAvailableUsageMonths;
	}

	/**
	 * This method is to get the monthly usage data
	 * 
	 * @param sTableName
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @param iMonthConfigured
	 * @return
	 */
	/*
	 * public static String getMonthlyUsageForUtillityMeters(String sTableName,
	 * String sUtilityAccountNumber, String sMeterNumer, int iMonthConfigured) {
	 * String sMeter = null; if (sMeterNumer == null) { sMeter = null; } else {
	 * sMeter = "'" + sMeterNumer + "'"; } String sMonthlyUsageQuery = ";" +
	 * "DECLARE @UtilityAccountNumber VARCHAR(100) = '" + sUtilityAccountNumber
	 * + "'" + ",@MeterNumber VARCHAR(100) = " + sMeter +
	 * " DECLARE @FromDate DATE " + ",@ToDate DATE " + ",@IsAMI TINYINT " +
	 * "IF @MeterNumber IS NULL " + "BEGIN " +
	 * "SET @ToDate = DATEADD(MM, - 1, DATEADD(month, DATEDIFF(month, 0, getdate()), 0)) "
	 * + "SET @FromDate = DATEADD(MM, -" + iMonthConfigured + "+1, @ToDate) " +
	 * "END " + "IF ISNULL(@MeterNumber, '') <> '' " + "BEGIN " +
	 * "SELECT @IsAMI = ISAMI " + "FROM account a " +
	 * "JOIN AccountMeterMapping amm ON a.AccountNumber = amm.AccountNumber " +
	 * "WHERE a.UtilityAccountNumber = @UtilityAccountNumber " +
	 * "	AND amm.meternumber = @MeterNumber " + "IF @IsAMI = 0 " + "BEGIN " +
	 * "SET @ToDate = DATEADD(MM, - 1, DATEADD(month, DATEDIFF(month, 0, getdate()), 0)) "
	 * + "SET @FromDate = DATEADD(MM, -" + iMonthConfigured + "+1, @ToDate) " +
	 * "END " + "IF @IsAMI = 1 " + "BEGIN " +
	 * "SET @ToDate = DATEADD(month, DATEDIFF(month, 0, getdate()), 0) " +
	 * "SET @FromDate = DATEADD(MM, -" + iMonthConfigured + "+1, @ToDate) " +
	 * "END " + "END " +
	 * "SELECT DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [UsageMonth] "
	 * + ", sum(P.Value) AS kWhValue " + "	,SUM(p.Consumed) AS DollarValue " +
	 * "FROM account a " +
	 * "JOIN AccountMeterMapping amm ON a.AccountNumber = amm.AccountNumber " +
	 * "JOIN " + sTableName + " p ON a.AccountNumber = p.AccountNumber" +
	 * "	AND amm.meterid = p.meterid " +
	 * "JOIN RatePlanDetail RPD ON P.RatePlanDetailID=RPD.RatePlanDetailID " +
	 * "WHERE a.UtilityAccountNumber = @UtilityAccountNumber " +
	 * "AND RPD.UsageType='Usage' " +
	 * "AND amm.meternumber = isnull(@MeterNumber, amm.meternumber) " +
	 * "AND amm.STATUS = 1 " + "AND ( " + "		p.UsageDate >= @FromDate " +
	 * "AND p.UsageDate <= @ToDate ) " + "GROUP BY UtilityAccountNumber " +
	 * "	,p.UsageDate ORDER BY 1;"; return sMonthlyUsageQuery; }
	 */

	public static String getMonthlyUsageForUtillityMeters(String sTableName, String sMeterType,
			String sUtilityAccountNumber, String sMeterNumer, int iMonthConfigured) {
		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}
		String sMonthlyUsageQuery = ";" +

				"; DECLARE @UtilityAccountNumber VARCHAR(100) = '" + sUtilityAccountNumber
				+ "', @MeterNumber VARCHAR(100) = " + sMeter
				+ " DECLARE @FromDate DATE ,@ToDate DATE, @IsAMI TINYINT, @month TinyInt"
				+ " IF @MeterNumber IS NULL BEGIN"
				+ " if Exists (Select 1 from Account A Inner join AccountMeterMapping AMM on A.AccountNumber=AMM.AccountNumber where"
				+ " A.UtilityAccountNumber=@UtilityAccountNumber and isami=0 and MeterType='" + sMeterType + "')"
				+ " Begin set @month=1 End Else  Set @month=0"
				+ " SET @ToDate = DATEADD(MM,  -@month, DATEADD(month, DATEDIFF(month, 0, getdate()), 0))"
				+ " SET @FromDate = DATEADD(MM, -" + iMonthConfigured + "+ 1, @ToDate) END"
				+ " IF ISNULL(@MeterNumber, '') <> ''" + " BEGIN SELECT @IsAMI = ISAMI FROM account a "
				+ " JOIN AccountMeterMapping amm ON a.AccountNumber = amm.AccountNumber"
				+ " WHERE a.UtilityAccountNumber = @UtilityAccountNumber AND amm.meternumber = @MeterNumber"
				+ " IF @IsAMI = 0  BEGIN"
				+ " SET @ToDate = DATEADD(MM, - 1, DATEADD(month, DATEDIFF(month, 0, getdate()), 0))"
				+ " SET @FromDate = DATEADD(MM, - " + iMonthConfigured + "+ 1, @ToDate) END" + " IF @IsAMI = 1  BEGIN"
				+ " SET @ToDate = DATEADD(month, DATEDIFF(month, 0, getdate()), 0)" + " SET @FromDate = DATEADD(MM, -"
				+ iMonthConfigured + " + 1, @ToDate) END END"
				+ " SELECT DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [UsageMonth]"
				+ " ,sum(P.Value) AS kWhValue ,SUM(p.Consumed) AS DollarValue "
				+ " FROM account a JOIN AccountMeterMapping amm ON a.AccountNumber = amm.AccountNumber" + " JOIN "
				+ sTableName + " p ON a.AccountNumber = p.AccountNumber"
				+ " AND amm.meterid = p.meterid JOIN RatePlanDetail RPD ON P.RatePlanDetailID = RPD.RatePlanDetailID"
				+ " WHERE a.UtilityAccountNumber = @UtilityAccountNumber "
				+ " AND RPD.UsageType = 'Usage' AND amm.meternumber = isnull(@MeterNumber, amm.meternumber) "
				+ " AND amm.STATUS = 1  AND (p.UsageDate >= @FromDate AND p.UsageDate <= @ToDate)"
				+ " GROUP BY UtilityAccountNumber, p.UsageDate ORDER BY 1;";
		return sMonthlyUsageQuery;
	}

	/**
	 *
	 * @param sTableName
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @param iMonthConfigured
	 * @return
	 */
	/*
	 * public static String getSeasonalUsageForUtillityMeters(String sTableName,
	 * String sUtilityAccountNumber, String sMeterNumer, int iMonthConfigured) {
	 * String sMeter = null; if (sMeterNumer == null) { sMeter = null; } else {
	 * sMeter = "'" + sMeterNumer + "'"; } String sSeasonUsageQuery = ";" +
	 * "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber +
	 * "'" + " ,@MeterNumber varchar(100)= " + sMeter + " select SeasonName" +
	 * ", sum(P.Value) kWh, sum(p.Consumed) DollarValue " + "from account a " +
	 * "join AccountMeterMapping amm on a.AccountNumber=amm.AccountNumber " +
	 * "join " + sTableName +
	 * " p on a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid " +
	 * "join SeasonDuration sd on sd.seasonmonth=datepart(MM,p.Usagedate) " +
	 * "JOIN RatePlanDetail RPD ON P.RatePlanDetailID=RPD.RatePlanDetailID " +
	 * "join SeasonMaster sm on sm.seasonid=sd.seasonid " +
	 * "where a.UtilityAccountNumber=@UtilityAccountNumber " +
	 * "AND RPD.UsageType='Usage' " +
	 * "and amm.meternumber=isnull(@MeterNumber,amm.meternumber) " +
	 * "and amm.Status=1 " + "and p.UsageDate>=dateadd(mm,-(" + iMonthConfigured
	 * + "-1),getdate()) " + "group by UtilityAccountNumber, SeasonName " +
	 * "order by 1";
	 * 
	 * return sSeasonUsageQuery; }
	 */

	public static String getSeasonalUsageForUtillityMeters(String sTableName, String sUtilityAccountNumber,
			String sMeterNumer, int iMonthConfigured) {
		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}
		String sSeasonUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber + "'"
				+ ",@MeterNumber varchar(100)= " + sMeter + " SELECT SeasonName, sum(P.Value) kWh, "
				+ "sum(p.Consumed) DollarValue from account a join AccountMeterMapping amm on a.AccountNumber=amm.AccountNumber join "
				+ sTableName
				+ " p on a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid join SeasonDuration sd on "
				+ "sd.seasonmonth=datepart(MM,p.Usagedate) JOIN RatePlanDetail RPD ON P.RatePlanDetailID=RPD.RatePlanDetailID join "
				+ "SeasonMaster sm on sm.seasonid=sd.seasonid where a.UtilityAccountNumber=@UtilityAccountNumber AND  "
				+ "RPD.UsageType='Usage' and amm.meternumber=isnull(@MeterNumber,amm.meternumber) and amm.Status=1 and "
				+ "p.UsageDate>=dateadd(mm,-(" + iMonthConfigured
				+ "-2),CAST(DATEADD(DAY, 1, EOMONTH(GETDATE(), - 1)) AS DATETIME)) group by UtilityAccountNumber, SeasonName order by 1";
		return sSeasonUsageQuery;
	}

	/**
	 * This query is for getting the daily usage for any meter/Utility/Account.
	 *
	 * @param sTableName
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @return
	 */
	public static String getDailyUsageForUtillityMeters(String sTableName, String sUtilityAccountNumber,
			String sMeterNumer) {
		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}
		String sDailyUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber + "'"
				+ "  ,@MeterNumber varchar(100)=" + sMeter + " select UtilityAccountNumber"
				+ ", p.UsageDate AS UsageDate" + ", sum(P.Value) kWh, sum(p.Consumed) DollarValue " + "from account a "
				+ "join AccountMeterMapping amm on a.AccountNumber=amm.AccountNumber " + "join " + sTableName
				+ " p JOIN RatePlanDetail RPD ON P.RatePlanDetailID=RPD.RatePlanDetailID "
				+ " on a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid"
				+ " where a.UtilityAccountNumber=@UtilityAccountNumber " + "AND RPD.UsageType='Usage' "
				+ " and amm.meternumber=isnull(@MeterNumber,amm.meternumber)" + " and amm.Status=1"
				+ " and p.UsageDate>=dateadd(day,-31,getdate())"
				+ " group by UtilityAccountNumber, p.UsageDate order by 1,2";

		return sDailyUsageQuery;
	}

	/**
	 * This query is for getting the Hourly usage for any meter/Utility/Account
	 *
	 * @param sTableName
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @return
	 */
	public static String getHourlyUsageForUtillityMeters(String sTableName, String sUtilityAccountNumber,
			String sMeterNumer) {
		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}
		String sHourlyUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber + "'"
				+ ",@MeterNumber varchar(100)=" + sMeter + " select UtilityAccountNumber" + ", p.UsageDate AS UsageDate"
				+ ", sum(P.Value) kWh, sum(p.Consumed) DollarValue " + "from account a "
				+ "join AccountMeterMapping amm on a.AccountNumber=amm.AccountNumber " + "join " + sTableName
				+ " p JOIN RatePlanDetail RPD ON P.RatePlanDetailID=RPD.RatePlanDetailID "
				+ " on a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid "
				+ "where a.UtilityAccountNumber=@UtilityAccountNumber " + "AND RPD.UsageType='Usage' "
				+ "and amm.meternumber=isnull(@MeterNumber,amm.meternumber) " + "and amm.Status=1 "
				+ "and CONVERT(DATE,p.UsageDate)=CONVERT(DATE,GETDATE()-1) "
				+ "group by UtilityAccountNumber, p.UsageDate order by 1,2 ";

		return sHourlyUsageQuery;
	}

	/**
	 * This query is for getting the 15 Min usage for any meter/Utility/Account.
	 *
	 * @param sTableName
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @return
	 */
	public static String getQuarterlyUsageForUtillityMeters(String sTableName, String sUtilityAccountNumber,
			String sMeterNumer) {
		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}
		String sQuarterlyUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber + "'"
				+ ",@MeterNumber varchar(100)=" + sMeter + " select UtilityAccountNumber" + ", p.UsageDate AS UsageDate"
				+ ", sum(P.Value) kWh, sum(p.Consumed) DollarValue " + "from account a "
				+ "join AccountMeterMapping amm on a.AccountNumber=amm.AccountNumber " + "join " + sTableName
				+ " p  JOIN RatePlanDetail RPD ON P.RatePlanDetailID=RPD.RatePlanDetailID "
				+ "on a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid "
				+ "where a.UtilityAccountNumber=@UtilityAccountNumber " + "AND RPD.UsageType='Usage' "
				+ "and amm.meternumber=isnull(@MeterNumber,amm.meternumber) " + "and amm.Status=1 "
				+ "and CONVERT(DATE,p.UsageDate)=CONVERT(DATE,GETDATE()-1) "
				+ "group by UtilityAccountNumber, p.UsageDate order by 1,2 ";

		return sQuarterlyUsageQuery;
	}

	/**
	 * This query is to fetch the monthly solar generation for the account
	 * 
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @return
	 */
	public static String getMonthlySolarGeneration(String sUtilityAccountNumber, String sMeterNumer,
			int iConfigMonths) {
		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}
		String sSolarMonthlyUsageQuery = ";" + "  declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber
				+ "'" + ",@MeterNumber varchar(100)=" + sMeter + " select UtilityAccountNumber "
				+ ", DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [GenerationDate]"
				+ ", sum(P.Value) kWh, sum(p.Consumed) DollarValue " + "from account a "
				+ "join AccountMeterMapping amm on a.AccountNumber=amm.AccountNumber "
				+ "join SolarMonthlyUsage p on a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid "
				+ "where a.UtilityAccountNumber=@UtilityAccountNumber "
				+ "and amm.meternumber=isnull(@MeterNumber,amm.meternumber) " + "and amm.Status=1 "
				+ "and p.UsageDate>=dateadd(mm,-" + iConfigMonths + ",getdate()) "
				+ "group by UtilityAccountNumber, p.UsageDate order by 1 ";

		return sSolarMonthlyUsageQuery;
	}

	/**
	 * This method gives all the daily solar generation result
	 * 
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @param iDays
	 * @return
	 */
	public static String getDailySolarGeneration(String sUtilityAccountNumber, String sMeterNumer, int iDays) {

		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}
		String sSolarDailyUsageQuery = ";" + "  declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber
				+ "'" + " ,@MeterNumber varchar(100)=" + sMeter

				+ " select UtilityAccountNumber" + " , p.UsageDate AS GenerationDate"
				+ ", sum(P.Value) kWh, sum(p.Consumed) DollarValue " + "from account a "
				+ "join AccountMeterMapping amm on a.AccountNumber=amm.AccountNumber "
				+ "join SolarDailyUsage p on a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid and amm.IsAMI=1 "
				+ "where a.UtilityAccountNumber=@UtilityAccountNumber "
				+ "and amm.meternumber=isnull(@MeterNumber,amm.meternumber) " + "and amm.Status=1 "
				+ "and p.UsageDate>CONVERT(DATE,dateadd(day,-" + iDays + ",getdate())) "
				+ "and p.UsageDate<CONVERT(DATE,getdate()) "
				+ "group by UtilityAccountNumber, p.UsageDate order by 1  ";

		return sSolarDailyUsageQuery;
	}

	/**
	 * This method gives all the Hourly solar generation result
	 * 
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @return
	 */
	public static String getHourlySolarGeneration(String sUtilityAccountNumber, String sMeterNumer) {
		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}

		String sSolarHourlyUsageQuery = ";" + "  declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber
				+ "'" + "   ,@MeterNumber varchar(100)=" + sMeter + " select UtilityAccountNumber "
				+ ", p.UsageDate AS GenerationDate " + ", sum(P.Value) kWh, sum(p.Consumed) DollarValue "
				+ "from account a " + "join AccountMeterMapping amm on a.AccountNumber=amm.AccountNumber "
				+ "join SolarHourlyUsage p on a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid "
				+ "where a.UtilityAccountNumber=@UtilityAccountNumber "
				+ "and amm.meternumber=isnull(@MeterNumber,amm.meternumber) " + "and amm.Status=1 "
				+ "and CONVERT(DATE,p.UsageDate)=CONVERT(DATE,GETDATE()-1) "
				+ "group by UtilityAccountNumber, p.UsageDate order by 1,2  ";

		return sSolarHourlyUsageQuery;
	}

	/*****************************************************************************************
	 * COMPARE SPENDING PAGE QUERIES *
	 *****************************************************************************************/
	/**
	 * @param sUtilityAccountNumber
	 * @return
	 */
	public static String getCompareSofarAndProjectedUsage(String sUtilityAccountNumber) {
		String sSolarHourlyUsageQuery = "SELECT * FROM "
				+ "UsageGenerationSummary where AccountNumber=(select AccountNumber from account where UtilityAccountNumber ='"
				+ sUtilityAccountNumber + "')";
		return sSolarHourlyUsageQuery;
	}

	/**
	 *
	 * @param sUtilityAccountNumber
	 * @param sUsageTypeKwh
	 * @param sUsageTypeDollar
	 * @param iConfiguredCspMonth
	 * @return
	 */
	public static String getCompareMePreviousCurrentUsage(String sUtilityAccountNumber, String sUsageTypeKwh,
			String sUsageTypeDollar, int iConfiguredCspMonth) {
		String sCompareMePreviousCurrentUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='"
				+ sUtilityAccountNumber + "'"
				+ " select curr.UtilityAccountNumber,[CurrentYearMonth],[CurrentKwhValue],[CurrentDollarValue],[PreviousYearMonth],[PreviousKwhValue],[PreviousDollarValue] from "
				+ "(select UtilityAccountNumber , DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [CurrentYearMonth] , sum(P."
				+ sUsageTypeKwh + ") AS [CurrentKwhValue] , sum(p." + sUsageTypeDollar
				+ ") AS [CurrentDollarValue] from account a "
				+ "join AccountCompareSpending p on a.AccountNumber=p.AccountNumber "
				+ "where a.UtilityAccountNumber=@UtilityAccountNumber and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate()) " + " AND p.UsageDate <= dateadd(mm, - 1, getdate()) "
				+ "group by UtilityAccountNumber, p.UsageDate ) curr join "
				+ "(select UtilityAccountNumber , DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [PreviousYearMonth] "
				+ ", sum(P." + sUsageTypeKwh + ") AS [PreviousKwhValue], sum(p." + sUsageTypeDollar
				+ ") AS [PreviousDollarValue] from account a "
				+ "join AccountCompareSpending p on a.AccountNumber=p.AccountNumber where a.UtilityAccountNumber=@UtilityAccountNumber "
				+ "and p.UsageDate>=dateadd(mm,-13-" + iConfiguredCspMonth
				+ ",getdate()) and p.UsageDate<=dateadd(mm,-13,getdate()) group by UtilityAccountNumber, p.UsageDate "
				+ ") pre on curr.UtilityAccountNumber=pre.UtilityAccountNumber  and month(CurrentYearMonth) =month(PreviousYearMonth)";
		return sCompareMePreviousCurrentUsageQuery;
	}

	/**
	 * This query brings zipusage data for the account and all the users in the
	 * zip.
	 * 
	 * @param sUtilityAccountNumber
	 * @param sUsageTypeKwh
	 * @param sUsageTypeDollar
	 * @param iConfiguredCspMonth
	 * @return
	 */
	public static String getCompareZipUsage(String sUtilityAccountNumber, String sUsageTypeKwh, String sUsageTypeDollar,
			int iConfiguredCspMonth) {

		String sCompareZipUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber
				+ "'" + " DECLARE @ZipCode VARCHAR(20),@AddressType TINYINT"
				+ " SELECT @ZipCode=CA.ZipCode,@AddressType=CA.AddressType FROM CustomerAddress CA "
				+ "WHERE CA.UtilityAccountNumber=@UtilityAccountNumber "

				+ "select [CurrentYearMonth],[MyUsageUnit],[MyUsageValue] ,[ZipUsageUnit],[ZipUsageValue] " + "from  ( "
				+ "select    CA.ZipCode , DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [CurrentYearMonth] "
				+ ", sum(P." + sUsageTypeKwh + ") AS [MyUsageUnit], sum(p." + sUsageTypeDollar + ") AS [MyUsageValue] "
				+ "FROM CustomerAddress CA " + "JOIN Account A ON CA.AddressID=A.AddressID "
				+ "JOIN AccountCompareSpending p on a.AccountNumber=p.AccountNumber "
				+ "where a.UtilityAccountNumber=@UtilityAccountNumber " + "and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate()) " + " AND p.UsageDate <= dateadd(mm, - 1, getdate()) "
				+ "group by CA.ZipCode, DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0)) curr join "
				+ "(	select p.ZipCode, DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [PreviousYearMonth] "
				+ " , sum(P." + sUsageTypeKwh + ") AS [ZipUsageUnit] , sum(p." + sUsageTypeDollar
				+ ") AS [ZipUsageValue]  "
				+ "from ZipCompareSpending p where ZipCode=@ZipCode 	and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate()) "
				+ "AND P.AddressType=@AddressType group by p.ZipCode,DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) "
				+ ") pre on curr.ZipCode=pre.ZipCode and month(CurrentYearMonth) =month(PreviousYearMonth) ";

		return sCompareZipUsageQuery;
	}

	/**
	 * This query brings Utility Usage data for the account and all the users in
	 * the zip.
	 * 
	 * @param sUtilityAccountNumber
	 * @param sUsageTypeKwh
	 * @param sUsageTypeDollar
	 * @param iConfiguredCspMonth
	 * @return
	 */
	public static String getCompareUtilityUsage(String sUtilityAccountNumber, String sUsageTypeKwh,
			String sUsageTypeDollar, int iConfiguredCspMonth) {

		String sCompareUtilityUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber
				+ "' DECLARE @AddressType TINYINT "
				+ "SELECT @AddressType=CA.AddressType FROM CustomerAddress CA WHERE CA.UtilityAccountNumber=@UtilityAccountNumber "

				+ "select [CurrentYearMonth],[MyUsageUnit],[MyUsageValue] ,[UtilityUsageUnit],[UtilityUsageValue] "
				+ "from ( select     DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [CurrentYearMonth] "
				+ ", sum(P." + sUsageTypeKwh + ") AS [MyUsageUnit], sum(p." + sUsageTypeDollar + ") AS [MyUsageValue] "
				+ " FROM CustomerAddress CA JOIN Account A ON CA.AddressID=A.AddressID JOIN AccountCompareSpending p on a.AccountNumber=p.AccountNumber "
				+ " where a.UtilityAccountNumber=@UtilityAccountNumber  and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate())" + " AND p.UsageDate <= dateadd(mm, - 1, getdate()) "
				+ "      group by DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) " + ") curr join "
				+ "(select    DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [PreviousYearMonth]  " + ", sum(P."
				+ sUsageTypeKwh + ") AS [UtilityUsageUnit] , sum(p." + sUsageTypeDollar + ") AS [UtilityUsageValue]  "
				+ "     from UtilityCompareSpending  p   where p.UsageDate>=dateadd(mm,-13,getdate())  "
				+ "    AND P.AddressType=@AddressType   group by DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) "
				+ ") pre on  month(CurrentYearMonth) =month(PreviousYearMonth)";

		return sCompareUtilityUsageQuery;
	}

	/**
	 * This query brings All Usage data for the account and all the users in the
	 * zip.
	 * 
	 * @param sUtilityAccountNumber
	 * @param sUsageTypeKwh
	 * @param sUsageTypeDollar
	 * @param iConfiguredCspMonth
	 * @return
	 */
	public static String getCompareAllUsage(String sUtilityAccountNumber, String sUsageTypeKwh, String sUsageTypeDollar,
			int iConfiguredCspMonth) {

		String sCompareAllUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber
				+ "'"
				+ " DECLARE @ZipCode VARCHAR(20),@AddressType TINYINT SELECT @ZipCode=CA.ZipCode,@AddressType=CA.AddressType "
				+ " FROM CustomerAddress CA WHERE CA.UtilityAccountNumber=@UtilityAccountNumber "
				+ " select e.[CurrentYearMonth], e.kWh as MyUsageUnit, e.DollarValue as MyUsageValue , "
				+ " e.[PreviousYearMonth], e.prekWh as PreviousUsageUnit, e.preDollarValue as PreviousUsageValue, "
				+ " b.[ZipUsageUnit], b.[ZipUsageValue], c.[UtilityUsageUnit], c.[UtilityUsageValue] FROM (select [CurrentYearMonth], "
				+ " curr.kWh, curr.DollarValue, [PreviousYearMonth], pre.kWh as prekwh, pre.DollarValue AS preDollarValue from "
				+ " (select UtilityAccountNumber , DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [CurrentYearMonth] "
				+ " , sum(P." + sUsageTypeKwh + ") kWh, sum(p." + sUsageTypeDollar
				+ ") DollarValue from account a join AccountCompareSpending p on a.AccountNumber=p.AccountNumber "
				+ " where a.UtilityAccountNumber=@UtilityAccountNumber and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate()) group by UtilityAccountNumber, p.UsageDate "
				+ " ) curr join ( select UtilityAccountNumber , DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [PreviousYearMonth] "
				+ " , sum(P." + sUsageTypeKwh + ") kWh, sum(p." + sUsageTypeDollar
				+ ") DollarValue  from account a join AccountCompareSpending p on a.AccountNumber=p.AccountNumber "
				+ " where a.UtilityAccountNumber=@UtilityAccountNumber and p.UsageDate>=dateadd(mm,-13-"
				+ iConfiguredCspMonth + ",getdate()) and p.UsageDate<=dateadd(mm,-1-" + iConfiguredCspMonth
				+ ",getdate()) " + " group by UtilityAccountNumber, p.UsageDate "
				+ " ) pre on curr.UtilityAccountNumber=pre.UtilityAccountNumber and month(CurrentYearMonth) =month(PreviousYearMonth)) e "
				+ "INNER JOIN(select [CurrentYearMonth],[MyUsageUnit],[MyUsageValue] ,[PreviousYearMonth],[ZipUsageUnit],[ZipUsageValue] "
				+ " from (select    CA.ZipCode, DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [CurrentYearMonth] "
				+ " , sum(P." + sUsageTypeKwh + ") AS [MyUsageUnit], sum(p." + sUsageTypeDollar
				+ ") AS [MyUsageValue] FROM CustomerAddress CA "
				+ "  JOIN Account A ON CA.AddressID=A.AddressID JOIN AccountCompareSpending p on a.AccountNumber=p.AccountNumber  "
				+ "  where a.UtilityAccountNumber=@UtilityAccountNumber and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate()) "
				+ "  group by CA.ZipCode, DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) "
				+ " ) curr join ( select   p.ZipCode , DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [PreviousYearMonth] "
				+ "  , sum(P." + sUsageTypeKwh + ") AS [ZipUsageUnit] , sum(p." + sUsageTypeDollar
				+ ") AS [ZipUsageValue]  "
				+ " from ZipCompareSpending p where ZipCode=@ZipCode and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate())  "
				+ "  AND P.AddressType=@AddressType group by p.ZipCode,DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) "
				+ " ) pre on curr.ZipCode=pre.ZipCode and month(CurrentYearMonth) =month(PreviousYearMonth))b ON e.currentyearmonth = b.currentyearmonth "
				+ " INNER JOIN(select [CurrentYearMonth],[MyUsageUnit],[MyUsageValue],[UtilityUsageUnit],[UtilityUsageValue] "
				+ " from ( select     DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [CurrentYearMonth] "
				+ "  , sum(P." + sUsageTypeKwh + ") AS [MyUsageUnit], sum(p." + sUsageTypeDollar
				+ ") AS [MyUsageValue] FROM CustomerAddress CA "
				+ " JOIN Account A ON CA.AddressID=A.AddressID JOIN AccountCompareSpending p on a.AccountNumber=p.AccountNumber "
				+ "  where a.UtilityAccountNumber=@UtilityAccountNumber and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate()) " + " AND p.UsageDate <= dateadd(mm, - 1, getdate()) "
				+ "  group by DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) "
				+ " ) curr join (select    DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [PreviousYearMonth] "
				+ "  , sum(P." + sUsageTypeKwh + ") AS [UtilityUsageUnit] , sum(p." + sUsageTypeDollar
				+ ") AS [UtilityUsageValue]  " + " from UtilityCompareSpending  p  where p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate())  AND P.AddressType=@AddressType "
				+ " group by DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) "
				+ ") pre on  month(CurrentYearMonth) =month(PreviousYearMonth))c "
				+ " ON e.currentyearmonth = c.currentyearmonth;";

		return sCompareAllUsageQuery;
	}

	/*****************************************************************************************
	 * OUTAGES PAGE QUERIES (Osp) *
	 *****************************************************************************************/
	/**
	 * This method is to get the outage id for given outage message.
	 * 
	 * @param outageMsg
	 * @return
	 */
	public static String getOutageIdQuery(String outageMsg) {
		String sOutageIdQuery = ";" + "SELECT OutageId FROM outageNotification WHERE OutageMessage='" + outageMsg + "'"
				+ " and IsResolved='0'";
		return sOutageIdQuery;
	}

	/**
	 * This method used to get the customer effected in given outage.
	 * 
	 * @param outageId
	 * @return
	 */
	public static String getCustomerAffected(String outageId) {
		String sCustomerAffected = ";"
				+ "SELECT COUNT(AccountNumber) as AccountNumber from customerOutage where OutageID='" + outageId + "'";
		return sCustomerAffected;
	}

	/*****************************************************************************************
	 * CONNECT ME PAGE QUERIES (Cmp) *
	 *****************************************************************************************/
	/**
	 * This method is to get the Service Account Number in Random
	 * 
	 * @return String
	 */
	public static String getConnectMeFeatures() {
		String sServiceAccountNumber = ";"
				+ "SELECT FeatureName, Status FROM dbo.FeatureSettings WHERE FeatureName LIKE 'ConnectMe.%'";
		return sServiceAccountNumber;
	}

	/*****************************************************************************************
	 * SERVICE REQUEST PAGE QUERIES *
	 *****************************************************************************************/
	/**
	 * This method is to get the query for fetching service request reason name
	 * from the DB.
	 * 
	 * @return
	 */
	public static String getPreLoginReasonNameList() {
		String sServiceReasonName = "SELECT ReasonName FROM SRReasonMaster WHERE isActive=1 AND isPreLogin = 1";
		return sServiceReasonName;
	}

	/**
	 * This method is to get the query for fetching service request reason name
	 * from the DB.
	 * 
	 * @return
	 */
	public static String getPostLoginReasonNameList() {
		String sServiceReasonName = ";" + "SELECT ReasonName FROM SRReasonMaster WHERE isActive=1";
		return sServiceReasonName;
	}

	/*****************************************************************************************
	 * ELECTRIC VEHICLE PAGE QUERIES *
	 *****************************************************************************************/
	/**
	 * This method is to get the EV assigned to user on given address.
	 * 
	 * @param userAccount
	 * @param accountId
	 * @return
	 */
	public static String getEVAssignedToUserOnGivenAccount(String userAccount, String accountId) {
		String eVAssignedToUserOnGivenAddress = ";" + "SELECT evm.Name FROM  [dbo].[UserElectricVehicleDetail] AS UEVD"
				+ " INNER JOIN [dbo].[UserElectricVehicle] AS UEV ON UEVD.[UserEVId] = UEV.[UserEVId] AND UEV.status=1"
				+ " INNER JOIN [dbo].[ElectronicVehicleMaster] AS EVM ON UEV.[EVId] = EVM.[EVId]"
				+ " INNER JOIN [dbo].[CustomerInfo] AS CI ON UEVD.[AddressId] = CI.[AddressId]"
				+ " WHERE UEV.userid=(SELECT UserID FROM [User] WHERE UserName = '" + userAccount
				+ "') AND CI.UtilityAccountNumber='" + accountId + "' order by Name Asc";
		return eVAssignedToUserOnGivenAddress;
	}

	/**
	 * This method is to get the complete electric vehicle list.
	 * 
	 * @return completeEV
	 */
	public static String getCompleteEVList() {
		String completeEV = "SELECT Name FROM dbo.ElectronicVehicleMaster where IsDeleted = 0";
		return completeEV;
	}

	/**
	 * This method is to get the complete electric vehicle list.
	 * 
	 * @return completeEV
	 */
	public static String unlinkElectricVehicleForAccount(String AccountNumber, String UserName) {
		String unlinkQuery = "Update [dbo].[UserElectricVehicle] set Status=0 where AddressId=(select AddressId from [CustomerInfo] where UtilityAccountNumber='"
				+ AccountNumber + "') and UserId=(SELECT UserID FROM [User] WHERE UserName = '" + UserName + "') ";
		return unlinkQuery;
	}

	/*****************************************************************************************
	 * CHARGING STATION PAGE QUERIES *
	 *****************************************************************************************/
	/**
	 * This method is to get the query for fetching charging station info from
	 * the DB.
	 * 
	 * @return
	 */
	public static String getChargingStationsInfo() {
		String chargingStationInfo = "SELECT LocationID, LocationName, Address1, Zipcode FROM ChargingStation "
				+ "where IsActive = 1";
		return chargingStationInfo;
	}

	/**
	 * This method is to get the query for fetching charging station info from
	 * the DB.
	 * 
	 * @return
	 */
	public static String getChargingStationsNameAndRate() {
		String chargingStationInfo = "SELECT LocationName, Rate FROM ChargingStation WHERE IsActive = 1 "
				+ "ORDER BY Rate ASC";
		return chargingStationInfo;
	}

	/*****************************************************************************************
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>> NOTIFICATION PAGE QUERIES
	 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<< *
	 *****************************************************************************************/
	public static String getNotificationQuery(String userId) {
		String notificationQuery = "SELECT [Filter1].[MessageId1] AS [MessageId], [Filter1].[PlaceHolderName], [Filter1].[CreatedDate1] AS [CreatedDate], [Filter1].[MessageBody] AS [MessageBody], [Filter1].[Reason] AS [Reason], [Filter1].[Subject] AS [Subject], [Filter1].[EmailId] AS [EmailId], [Filter1].[ControlText] AS [ControlText], [Filter1].[IsRead] AS [IsRead], [Filter1].[IsSaved] AS [IsSaved], [Filter1].[IsTrashed] AS [IsTrashed], [Filter1].[IsReply] AS [IsReply], CASE WHEN ([Filter1].[AttachmentId] IS NULL) THEN cast(0 as bigint) ELSE [Filter1].[AttachmentId] END AS [C1], [Filter1].[ServiceID] AS [ServiceID], [Extent7].[UtilityAccountNumber] AS [UtilityAccountNumber] FROM "
				+ "(SELECT [Extent1].[AccountNumber] AS [AccountNumber], [Extent1].[IsRead] AS [IsRead], [Extent1].[IsSaved] AS [IsSaved], [Extent1].[IsTrashed] AS [IsTrashed], [Extent1].[IsReply] AS [IsReply], [Extent1].[EmailId] AS [EmailId], [Extent1].[UserID] AS [UserID1], [Extent2].[MessageDetailId] AS [MessageDetailId1], [Extent2].[MessageId] AS [MessageId2], [Extent2].[MessageBody] AS [MessageBody], [Extent2].[CreatedDate] AS [CreatedDate1], [Extent3].[MessageId] AS [MessageId1], [Extent3].[Reason] AS [Reason], [Extent3].[Subject] AS [Subject], [Extent3].[ServiceID] AS [ServiceID], [Extent4].[AttachmentId] AS [AttachmentId], [Extent5].[PlaceHolderId] "
				+ "AS [PlaceHolderId1], [Extent6].[LanguageCode] AS [LanguageCode], [Extent6].[ControlText] AS [ControlText], [Extent5].[PlaceHolderName] FROM [dbo].[UserMessages] AS [Extent1] INNER JOIN [dbo].[MessageDetail] AS [Extent2] ON [Extent1].[MessageDetailId] = [Extent2].[MessageDetailId] INNER JOIN [dbo].[MessageMaster] AS [Extent3] ON [Extent2].[MessageId] = [Extent3].[MessageId] LEFT OUTER JOIN [dbo].[MessageAttachments] AS [Extent4] ON [Extent2].[MessageDetailId] = [Extent4].[MessageDetailID] INNER JOIN [dbo].[MessagePlaceHolders] AS [Extent5] ON [Extent3].[PlaceHolderId] = [Extent5].[PlaceHolderId] INNER JOIN [dbo]. [MultiLingualMasterLookUp] AS "
				+ "[Extent6] ON [Extent5].[LookUpID] = [Extent6].[LookUpID]) AS [Filter1] INNER JOIN [dbo].[UserAccount] AS [Extent7] ON ([Filter1].[UserID1] = [Extent7].[UserID]) AND (([Filter1].[AccountNumber] = [Extent7].[AccountNumber]) OR (([Filter1].[AccountNumber] IS NULL) AND ([Extent7].[AccountNumber] IS NULL))) WHERE ([Extent7].[UserID] =  (select UserID from [User] where username = '"
				+ userId + "')) AND ([Filter1].[LanguageCode] = 'EN')\r\n" + "";
		return notificationQuery;
	}

	// public static String getNotificationPreferenceChangeDetails(String
	// sUtilityAccountNumber,
	// TestNotificationPreferencePage.NotificationType notificationType) {
	// String sQuery = "SELECT AccountNumber, EmailORPhone, UserID FROM
	// AccountNotificationDetail\n"
	// + "WHERE AccountNumber = (SELECT TOP 1 AccountNumber FROM UserAccount \n"
	// + "WHERE UtilityAccountNumber = '" + sUtilityAccountNumber + "')\n"
	// + "AND AccountNotificationTypeID = (SELECT AccountNotificationTypeID FROM
	// AccountNotificationType \n"
	// + "WHERE AccountNotificationType = '" + notificationType + "')\n";
	// return sQuery;
	// }

	// public static String getCountPreferenceChangeDetails(String
	// sUtilityAccountNumber,
	// TestNotificationPreferencePage.NotificationType notificationType) {
	// String sQuery = "SELECT Count(AccountNumber) As NoRow FROM
	// AccountNotificationDetail\n"
	// + "WHERE AccountNumber = (SELECT TOP 1 AccountNumber FROM UserAccount \n"
	// + "WHERE UtilityAccountNumber = '" + sUtilityAccountNumber + "')\n"
	// + "AND AccountNotificationTypeID = (SELECT AccountNotificationTypeID FROM
	// AccountNotificationType \n"
	// + "WHERE AccountNotificationType = '" + notificationType + "')\n";
	// return sQuery;
	// }

	/*****************************************************************************************
	 * EFFICIENCY PAGE QUERIES *
	 *****************************************************************************************/

	/*****************************************************************************************
	 * FOOTPRINT PAGE QUERIES *
	 *****************************************************************************************/

	/*****************************************************************************************
	 * CSP RELATED SQL QUERIES *
	 *****************************************************************************************/
	// This query brings the number of Usage Months configured in CSP
	public static String sUsageMonthSettingsCspQuery = ""
			+ "select configOption, ConfigValue from utilityconfig  where configid=21";

	public static String sAllUtilitySettingsQuery = ""
			+ "SELECT * FROM UtilitySettings WITH(NOLOCK) WHERE UtilityId = 0";

	// This query brings the number of solar days to be displayed and is
	// configured in CSP
	public static String sConfiguredSolarDays = ""
			+ "SELECT SolarDays FROM UtilitySettings WITH(NOLOCK) WHERE UtilityId = 0";

	// This query brings the number of Usage Months configured in CSP
	public static String sDecimalPlaceAdminSettingsCspQuery = "" + "SELECT UptoDecimalPlaces FROM UtilitySettings";

	// This query brings the Date format settings in the CSP
	public static String sDateFormatConfigSettingsCspQuery = ""
			+ "select FormatName from MetricSystemMaster WHERE MetricSystemMaster.RID in(Select DateFormats from MetricSystemSettings)";

	// This query brings the Currency settings in the CSP
	public static String sCurrencyConfigSettingsCspQuery = ""
			+ "select DisplayName from MetricSystemMaster WHERE MetricSystemMaster.RID in(Select CurrencySymbols from MetricSystemSettings)";

	public static String sDefaultLanguageQuery = ""
			+ "SELECT LanguageDescription FROM LanguageMaster where IsDefault=1";

	/**
	 * This method used to get query to fetch the date format as per csp config.
	 * 
	 * @return
	 */
	public static String getDateFormatAsCspConfig() {
		String sDateFormatConfigSettingsCspQuery = ""
				+ "SELECT FormatName FROM MetricSystemMaster WHERE MetricSystemMaster.RID IN(SELECT DateFormats FROM MetricSystemSettings)";
		return sDateFormatConfigSettingsCspQuery;
	}

	/**
	 * This method brings the sql query for Date Format, Currency, Temperature,
	 * Time format and Time Zone settings
	 * 
	 * @return
	 */
	public static String getDateFormatMetricSettings() {
		String sDateFormatMetricSettings = ""
				+ "select 'Time Zone' AS Name ,'('+UTCOffset+') '+TimeZoneName as DisplayName from [TimeZone] TZ "
				+ " JOIN MetricSystemSettings MSS ON TZ.TimeZoneID=MSS.TimeZoneID UNION "
				+ "select 'Date format' AS Name ,DisplayName from MetricSystemMaster MS "
				+ "JOIN MetricSystemSettings MSS ON MS.RID=MSS.dateformats UNION "
				+ "select 'Time format' AS Name ,DisplayName from MetricSystemMaster MS "
				+ "JOIN MetricSystemSettings MSS ON MS.RID=MSS.Timeformats UNION "
				+ "select 'Temprature' AS Name ,DisplayName from MetricSystemMaster MS "
				+ "JOIN MetricSystemSettings MSS ON MS.RID=MSS.Tempratureormats UNION "
				+ "select 'Currency' AS Name ,DisplayName from MetricSystemMaster MS "
				+ "JOIN MetricSystemSettings MSS ON MS.RID=MSS.CurrencySymbols "
				+ "UNION select 'Distance' AS Name ,DisplayName from MetricSystemMaster MS "
				+ "JOIN MetricSystemSettings MSS ON MS.RID=MSS.DistanceFormats ";
		return sDateFormatMetricSettings;
	}

	/**
	 * This query will fetch the configured value for an option
	 * 
	 * @param sModuleName
	 *            = Compare
	 * @param sConfigOption
	 *            = Current Usage Colour in CSP
	 * @return #00f2ef
	 */
	public static String getConfigValue(String sModuleName, String sConfigOption) {
		String sConfiguredOption = ";" + "select ConfigOption, ConfigValue from utilityconfig where ModuleName = '"
				+ sModuleName + "'" + " and ConfigOption ='" + sConfigOption + "'";
		return sConfiguredOption;
	}

	/**
	 * This query will fetch the configured value for an option
	 * 
	 * @param sModuleName
	 *            = Compare = Current Usage Colour in CSP
	 * @return #00f2ef
	 */
	public static String getConfigValue(String sModuleName) {
		String sConfiguredOption = ";" + "select ConfigOption, ConfigValue from utilityconfig where ModuleName = '"
				+ sModuleName + "'";
		return sConfiguredOption;
	}

	/**
	 * This query will validate the module configured or not in CSP
	 * 
	 * @return 1 = Available, 0- Not available
	 */
	public static String getFeatureConfigured(String sFeatureName) {
		String sFeatureStatus = ";" + "SELECT Status FROM dbo.FeatureSettings WHERE FeatureName ='" + sFeatureName
				+ "'";
		return sFeatureStatus;
	}

	/**
	 * This query will validate the SCP module configured or not in CSP
	 * 
	 * @return 1 = Available, 0 - Not available
	 */
	public static String getAllFeatureConfigured() {
		String sFeatureStatus = ";" + "SELECT FeatureName, Status FROM dbo.FeatureSettings";
		return sFeatureStatus;
	}

	/**
	 * This query will validate the CSP module configured or not in CSP
	 * 
	 * @return 1 = Available, 0 - Not available
	 */
	public static String getAllCspModules() {
		String sCspModules = ";" + "SELECT RightName, IsActive FROM [right] WHERE ParentID=0";
		return sCspModules;
	}

	public static String getUserCount(String sOperator, int iStatus) {
		String sGetUserCount = ";"
				+ "select  count(AccountNumber) as AccountNumber from Account WHERE AccountNumber NOT IN (-1,2) AND Status"
				+ sOperator + "'" + iStatus + "'";
		return sGetUserCount;
	}

	public static String getUtilityAccountNo(String sOperator, int iStatus) {
		String sUtilityNumber = ";"
				+ "Select utilityAccountnumber from Account where AccountNumber not in (-1,2) and status" + sOperator
				+ "'" + iStatus + "'";
		return sUtilityNumber;
	}

	public static String getUserDetails(String sOperator, int iStatus, String sUtilityAccountNumber) {
		String sUserDetails = ";"
				+ "SELECT * FROM Customer FULL OUTER JOIN CustomerAddress ON Customer.customerid = CustomerAddress.customerid where CustomerAddress.AddressID=(select AddressId from Account Where AccountNumber Not in (-1,2) and Status"
				+ sOperator + "'" + iStatus + "'" + "and utilityAccountNumber='" + sUtilityAccountNumber + "')";
		return sUserDetails;
	}

	public static String getUserMeterNumber(String sOperator, int iStatus, String sUtilityAccountNumber) {
		String sUserDetails = ";" + "SELECT meternumber FROM AccountMeterMapping WHERE AccountNumber='"
				+ sUtilityAccountNumber + "'";
		return sUserDetails;
	}

	public static String getUserStatus(String sUtilityAccountNumber) {
		String sUserDetails = ";" + "select status from Account Where AccountNumber Not in (-1,2)"
				+ "and utilityAccountNumber='" + sUtilityAccountNumber + "'";
		return sUserDetails;
	}

	public static String getUserIsLockStatus(String sUtilityAccountNumber) {
		String sUserIsLockStatus = ";"
				+ "SELECT * FROM [UserAccount] Full OUTER JOIN [User] on [UserAccount].UserID=[User].UserID where [UserAccount].UtilityAccountNumber='"
				+ sUtilityAccountNumber + "'";
		return sUserIsLockStatus;
	}

	public static String getUserMessage(String reason) {
		String sUserIsLockStatus = ";" + "Select * from MessageMaster where Reason='" + reason
				+ "'order by messageID desc";
		return sUserIsLockStatus;
	}

	public static String getUserDetailsFromDB() {
		String sUserDetails = ";"
				+ "SELECT Top 1 c.FirstName,c.LastName,c.CustomerID,c.MobilePhone,c.DrivingLicence,C.customerNo,CA.UtilityAccountNumber AS UtilityAccountNumber ,MAX(AMM.MeterNumber) AS MeterNumber,CA.ZipCode,CA.Address1,C.SSNNumber "
				+ "  ,(CASE WHEN CA.AddressType=1 THEN 'Residential' ELSE 'Commercial' END) AS CustomerType " + "  "
				+ "FROM Customer c(NOLOCK)   JOIN CustomerAddress CA(NOLOCK) ON c.CustomerID=ca.CustomerID "
				+ "JOIN Account a(NOLOCK) ON ca.AddressID=a.AddressID "
				+ "JOIN AccountMeterMapping AMM(NOLOCK) ON A.AccountNumber=AMM.AccountNumber " + "LEFT JOIN  " + "( "
				+ "SELECT DISTINCT IA.AccountNumber          FROM Customer IC(NOLOCK) "
				+ "JOIN CustomerAddress ICA(NOLOCK) ON IC.CustomerID=ICA.CustomerID "
				+ "JOIN Account IA(NOLOCK) ON IA.AddressID=ICA.AddressID "
				+ "JOIN UserAccount IUA(NOLOCK) ON IUA.AccountNumber=IA.AccountNumber "
				+ ")R ON A.AccountNumber=R.AccountNumber   WHERE R.AccountNumber IS NULL "
				+ "AND c.CustomerID NOT IN (1,-1)" + "AND CA.AddressType=1"
				+ "GROUP BY c.FirstName,c.LastName,c.CustomerID,c.MobilePhone,C.customerNo,(CASE WHEN CA.AddressType=1 THEN 'Residential' ELSE 'Commercial' END) "
				+ ",c.DrivingLicence,CA.ZipCode,CA.Address1,C.SSNNumber,CA.UtilityAccountNumber";
		return sUserDetails;
	}

	/**
	 * This method returns query which return user details for the given
	 * username.
	 * 
	 * @param sUserName
	 * @return
	 */
	public static String getUserDetailsQuery(String sUserName) {
		String sQuery = "SELECT * FROM [User] " + "WHERE UserName = '" + sUserName + "'";
		return sQuery;
	}

	public static String getUserIsArchiveStatus(String userIP) {
		String sUserIsLockStatus = ";"
				+ "SELECT top 1  IsArchive FROM UserActivityTrail WHERE ActivityStatus<>1 and IPAddress='" + userIP
				+ "' order by activitydatetime desc";
		return sUserIsLockStatus;
	}

	public static String getUserBillingAmount(String sBillingdate) {
		String sUserIsLockStatus = ";"
				+ "SELECT Value FROM BillingDetail WHERE BillingID=( SELECT BillingId from Billing WHERE AccountNumber=16 and billingDate= '"
				+ sBillingdate + "') and Headid='22'";
		return sUserIsLockStatus;
	}

	public static String getUserBillingAmount(String sBillingdate, String sUtilityNum) {
		String sUserIsLockStatus = ";"
				+ " SELECT Value FROM BillingDetail WHERE BillingID=( SELECT BillingId from Billing WHERE AccountNumber=(select Accountnumber from Account Full outer join CustomerAddress on Account.AddressId=customerAddress.AddressId where CustomerAddress.utilityAccountnumber='"
				+ sUtilityNum + "') and billingDate= '" + sBillingdate + "') and Headid='22'";
		return sUserIsLockStatus;
	}

	public static String getUserPaymentAmount(String sPaymentDate, String sUtilityNum) {
		String sUserIsLockStatus = ";"
				+ "  SELECT * FROM BillingTransaction  where AccountNumber=(select Accountnumber from Account Full outer join CustomerAddress on Account.AddressId=customerAddress.AddressId where CustomerAddress.utilityAccountnumber='"
				+ sUtilityNum + "') and TransactionDate='" + sPaymentDate + "'";
		return sUserIsLockStatus;
	}

	public static String getUserMeterDetails(String sUtilityNumber) {
		String sUserMeterDetails = ";"
				+ "SELECT * FROM AccountMeterMapping where AccountNumber=(select Accountnumber from Account Full outer join CustomerAddress on Account.AddressId=customerAddress.AddressId where CustomerAddress.utilityAccountnumber='"
				+ sUtilityNumber + "') and Status='1'";
		return sUserMeterDetails;
	}

	public static String getUserUtilityNumberByMeterType(String sMeterType, String sStatus, String sOperator) {
		String utilityNum = ";"
				+ "select UtilityAccountNumber from Account where AccountNumber=(SELECT Top 1 AccountNumber FROM AccountMeterMapping WHERE Status = '"
				+ sStatus + "' and MeterType = '" + sMeterType
				+ "' and accountnumber not in (-1,2) GROUP BY AccountNumber HAVING COUNT(MeterType)" + sOperator + "1)";
		return utilityNum;
	}

	public static String getMeterNumberWithStatus(String sMeterType, String sUtilityNumber, String sStatus) {
		String meterNumber = ";"
				+ "SELECT MeterNumber FROM AccountMeterMapping where AccountNumber=(select Accountnumber from Account Full outer join CustomerAddress on Account.AddressId=customerAddress.AddressId where CustomerAddress.utilityAccountnumber='"
				+ sUtilityNumber + "') and Status='" + sStatus + "' and MeterType='" + sMeterType + "'";
		return meterNumber;
	}

	public static String getAllMeterCount(String sUtilityNumber, String sStatus, String sMeterType) {
		String meterCount = ";" + "SELECT Count (DISTINCT MeterNumber) as MeterNumber FROM vcustomer WHERE status='"
				+ sStatus + "' and metertype='" + sMeterType + "' AND UtilityAccountNumber='" + sUtilityNumber + "'";
		return meterCount;
	}

	public static String getAllAMIMeterCount(String sUtilityNumber, String iSAMI, String sStatus, String sMeterType) {
		String meterCount = ";" + "SELECT Count (DISTINCT MeterNumber) as MeterNumber FROM vcustomer WHERE IsAMI='"
				+ iSAMI + "' and status='" + sStatus + "' AND metertype='" + sMeterType + "' AND UtilityAccountNumber='"
				+ sUtilityNumber + "'";
		return meterCount;
	}

	public static String getUtilityNumberWithOneAMIMeter(String iSAMI, String sStatus, String sMeterType) {
		String utilityAccountNo = ";"
				+ "select UtilityAccountNumber from Account where AccountNumber=(SELECT Top 1 AccountNumber FROM AccountMeterMapping WHERE IsAMI='"
				+ iSAMI + "' and status='" + sStatus + "' AND metertype='" + sMeterType
				+ "'and accountnumber not in (-1,2) GROUP BY AccountNumber HAVING COUNT(IsAMI)=1)";

		return utilityAccountNo;
	}

	// This query brings the disclaimer text for a specific language.
	/**
	 * @param ControlId-ML_Compare_CompareZip_NoData
	 * @param sLangauge-En
	 * @return sControlTextQuery
	 */
	public static String getControlText(String ControlId, String sLangauge) {
		String sControlTextQuery = ";" + "select ControlText from multilingualmaster where ControlId ='" + ControlId
				+ "' and LanguageCode ='" + sLangauge + "'";

		return sControlTextQuery;
	}

	/**
	 * This query is for getting the Payment Options Value in CSP(Like Maximum
	 * Payment Amount, Processing Fee etc.)
	 */
	public static String getCspPaymentOption() {
		String sCspPaymentOption = ";" + "select ConfigType, ConfigValue from [Config].[ConfigurationSetting]";
		return sCspPaymentOption;
	}

	/**
	 * This method is to get the Service Account Number in Random
	 * 
	 * @return String
	 */
	public static String getServiceAccountNumber(String op, int value) {
		String sServiceAccountNumber = ";"
				+ "select UtilityAccountNumber from Account WHERE AccountNumber NOT IN (-1,2) AND Status" + op + value;
		return sServiceAccountNumber;
	}

	public static String getAdvanceSearchCustomer(String serviceAccountNumber, String accountType, String status,
			String customerNumber, String zipCode, String city, String firstName, String lastName, String email) {
		String query = ";"
				+ "SELECT DISTINCT A.customerid,A.customerNo,A.FirstName,A.LastName,A.FullName,A.EmailID,A.MobilePhone"
				+ "," + "case when b.customerid IS NOT NULL THEN 'Active' ELSE 'Inactive' END AS 'CustomerStatus'"
				+ "FROM customerinfo (NOLOCK) A "
				+ "left join customerinfo (NOLOCK) b on A.customerid=b.customerid and b.AccountStatusID IN (0,1,3)"
				+ "WHERE A.CustomerID NOT IN (1,-1) and ";
		boolean flag = false;

		if (!serviceAccountNumber.equalsIgnoreCase("")) {
			query = query + "A." + "UtilityAccountNumber='" + serviceAccountNumber + "'";
			flag = true;
		}
		if (!accountType.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			query = query + "A." + "AddressType='" + accountType + "'";
			flag = true;
		}

		if (!status.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			// query = query + "A." + "AccountStatus='" + status + "'";
			if (status.equalsIgnoreCase("Active")) {
				query = query + "A." + "AccountStatusID IN (0,1,3) ";
			} else {
				query = query + "A." + "AccountStatusID NOT IN (0,1,3) ";
			}
			flag = true;

		}
		if (!customerNumber.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			query = query + " A." + "customerNo='" + customerNumber + "'";
			flag = true;
		}
		if (!zipCode.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			query = query + " A." + "ZipCode='" + zipCode + "'";
			flag = true;
		}
		if (!city.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			query = query + " A." + "CityName='" + city + "'";
			flag = true;
		}
		if (!firstName.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			query = query + " A." + "FirstName='" + firstName + "'";
			flag = true;
		}
		if (!lastName.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			query = query + "A." + "LastName='" + lastName + "'";
			flag = true;
		}
		if (!email.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			query = query + "A." + "EmailID='" + email + "'";
			flag = true;
		}

		query = query + " ORDER BY A.FullName ASC";
		return query;

	}

	public static String getAdvanceSearchServiceAccountNumber(String serviceAccountNumber, String accountType,
			String status, String customerNumber, String zipCode, String city) {
		String query = ";"
				+ "SELECT DISTINCT A.customerid,A.customerNo,A.FirstName,A.LastName,A.FullName,A.EmailID,A.MobilePhone"
				+ "," + "case when b.customerid IS NOT NULL THEN 'Active' ELSE 'Inactive' END AS 'CustomerStatus'"
				+ "FROM customerinfo (NOLOCK) A "
				+ "left join customerinfo (NOLOCK) b on A.customerid=b.customerid and b.AccountStatusID IN (0,1,3)"
				+ "WHERE A.CustomerID NOT IN (1,-1) and ";

		String que = "SELECT cust.UtilityAccountNumber AS 'ServiceAccountnumber'"
				+ " ,cust.customerNo AS 'CustomerNumber',"
				+ " cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail',cust.MobilePhone AS 'PrimaryPhone' "
				+ " ,cust.AddressType AS 'AccountType',cust.AccountStatus,LinkedUser"
				+ " ,replace(isnull(cust.address1,'')+','+isnull(cust.address2,'')+','+isnull(cust.cityname,'')+','+isnull(cust.StateCode,'')+','+isnull(cust.ZipCode,''),',,',',') as 'Premise'"
				+ " FROM customerinfo cust"
				+ " left JOIN (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUser"
				+ "   FROM useraccount (NOLOCK)" + "  GROUP BY AccountNumber"
				+ " )AS linkeduser ON cust.AccountNumber=linkeduser.AccountNumber" + " WHERE ";

		boolean flag = false;

		if (!serviceAccountNumber.equalsIgnoreCase("")) {
			que = que + "cust." + "UtilityAccountNumber='" + serviceAccountNumber + "'";
			flag = true;
		}
		if (!accountType.equalsIgnoreCase("")) {
			if (flag) {
				que = que + " and ";
			}
			que = que + "cust." + "AddressType='" + accountType + "'" + " and cust.portalAccessType='standard' ";
			flag = true;
		}

		if (!status.equalsIgnoreCase("")) {
			if (flag) {
				que = que + " and ";
			}

			que = que + "cust." + "AccountStatus='" + status + "'";
			flag = true;

		}
		if (!customerNumber.equalsIgnoreCase("")) {
			if (flag) {
				que = que + " and ";
			}
			que = que + " cust." + "customerNo='" + customerNumber + "'";
			flag = true;
		}
		if (!zipCode.equalsIgnoreCase("")) {
			if (flag) {
				que = que + " and ";
			}
			que = que + " cust." + "ZipCode='" + zipCode + "'";
			flag = true;
		}
		if (!city.equalsIgnoreCase("")) {
			if (flag) {
				que = que + " and ";
			}
			que = que + " cust." + "CityName='" + city + "'";
			flag = true;
		}

		System.out.println(que);

		que = que + " ORDER BY cust.UtilityAccountNumber ASC";
		return que;

	}

	public static String getLinkAccountByCustomerNumber(String utilityAccountNumber) {
		String query = ";"
				+ "SELECT DISTINCT A.customerid,A.customerNo,A.FirstName,A.LastName,A.FullName,A.EmailID,A.MobilePhone,b.UtilityAccountNumber"
				+ "," + "case when b.customerid IS NOT NULL THEN 'Active' ELSE 'Inactive' END AS 'CustomerStatus'"
				+ "FROM customerinfo (NOLOCK) A "
				+ "left join customerinfo (NOLOCK) b on A.customerid=b.customerid and b.AccountStatusID IN (0,1,3)"
				+ "WHERE A.CustomerID NOT IN (1,-1) and A.UtilityAccountNumber='" + utilityAccountNumber + "'";
		return query;
	}

	public static String getCustomerCountByStatus(String status) {
		String query = ";"
				+ "SELECT DISTINCT A.customerid,A.customerNo,A.FirstName,A.LastName,A.FullName,A.EmailID,A.MobilePhone"
				+ "," + "case when b.customerid IS NOT NULL THEN 'Active' ELSE 'Inactive' END AS 'CustomerStatus'"
				+ "FROM customerinfo (NOLOCK) A "
				+ "left join customerinfo (NOLOCK) b on A.customerid=b.customerid and b.AccountStatusID IN (0,1,3)"
				+ "WHERE A.CustomerID NOT IN (1,-1) and A.AccountStatus='" + status + "'";
		return query;
	}

	public static String getCustomerDetailsByName(String customerName) {
		String query = ";"
				+ "SELECT DISTINCT A.customerid,A.customerNo,A.FirstName,A.LastName,A.FullName,A.EmailID,A.MobilePhone"
				+ "," + "case when b.customerid IS NOT NULL THEN 'Active' ELSE 'Inactive' END AS 'CustomerStatus'"
				+ "FROM customerinfo (NOLOCK) A "
				+ "left join customerinfo (NOLOCK) b on A.customerid=b.customerid and b.AccountStatusID IN (0,1,3)"
				+ "WHERE A.CustomerID NOT IN (1,-1) and A.FullName='" + customerName + "'";
		System.out.println(query);
		return query;
	}

	/**
	 * This Method is to get the link account number of the service account
	 * number
	 *
	 * @param utilityNumber
	 * @param roleType
	 * @return
	 */
	public static String getLinkAccountCount(String utilityNumber, String roleType) {
		String query = null;
		if (roleType.equalsIgnoreCase("Property Managers")) {
			query = "SELECT * FROM [UserAccount] Full OUTER JOIN [User] on [UserAccount].UserID=[User].UserID where [UserAccount].utilityAccountNumber='"
					+ utilityNumber + "' and [UserAccount].RoleID='" + 2 + "'";
		} else if (roleType.equalsIgnoreCase("Guest Users")) {
			query = "SELECT * FROM [UserAccount] Full OUTER JOIN [User] on [UserAccount].UserID=[User].UserID where [UserAccount].utilityAccountNumber='"
					+ utilityNumber + "' and [UserAccount].RoleID='" + 1 + "'";
		} else if (roleType.equalsIgnoreCase("Total")) {
			query = "SELECT * FROM [UserAccount] Full OUTER JOIN [User] on [UserAccount].UserID=[User].UserID where [UserAccount].utilityAccountNumber='"
					+ utilityNumber + "'";
		}
		System.out.println(query);
		return query;
	}

	/**
	 * This method is to get info of Commercial/ Residential 'Active', 'Not
	 * Registered', 'Registered' and 'Inactive' for Link Account Popup
	 *
	 * @param accountType
	 *            = Residential | Commercial
	 * @param accountStatus
	 *            = 'Not Registered' | 'Registered' | 'Active' | 'Inactive'
	 *
	 * @return a query having value for UtilityAccountNumber, ZipCode,
	 *         DrivingLicence, MeterNumber, Address1, AddressType
	 */
	public static String getInfoForLinkingAccountFromDB(String accountType, String accountStatus) {
		String query = null;
		query = "select top 1 CustomerInfo.UtilityAccountNumber, CustomerInfo.ZipCode, CustomerInfo.DrivingLicence, CustomerInfo.Address1, CustomerInfo.AddressType, AccountMeterMapping.MeterNumber  from CustomerInfo Inner JOIN  AccountMeterMapping on CustomerInfo.AccountNumber= AccountMeterMapping.AccountNumber WHERE AccountStatus = '"
				+ accountStatus + "' AND CustomerInfo.AddressType = '" + accountType + "'";
		System.out.println(query);
		return query;
	}

	public static String getInfoForLinkingAccountFromDB(String accountType, String accountStatus, String userId) {
		String query = null;
		query = "select Top 1 CustomerInfo.UtilityAccountNumber, CustomerInfo.ZipCode, CustomerInfo.DrivingLicence, CustomerInfo.Address1, CustomerInfo.AddressType, AccountMeterMapping.MeterNumber  from CustomerInfo Inner JOIN  AccountMeterMapping on CustomerInfo.AccountNumber= AccountMeterMapping.AccountNumber WHERE AccountStatus = '"
				+ accountStatus + "' AND CustomerInfo.AddressType = '" + accountType
				+ "' and CustomerInfo.accountnumber not in (SELECT AccountNumber FROM [UserAccount]  WHERE UserID=(Select UserID from [User]  where UserName ='"
				+ userId + "'))";
		System.out.println(query);
		return query;
	}

	/**
	 * This method is to get info of Commercial/ Residential
	 * Registered-NotActivated and Activation Expired for Link Account Popup
	 *
	 * @param accountType
	 *            = Residential | Commercial
	 *
	 * @return a query having value for UtilityAccountNumber, ZipCode,
	 *         DrivingLicence, MeterNumber, Address1, AddressType
	 */
	public static String getInfoForLinkAccountRegisteredExpiredActivationFromDB(String accountType) {
		String query = null;
		query = "select top 1 CI.UtilityAccountNumber, CI.ZipCode, CI.DrivingLicence, CI.Address1, CI.AddressType, AMM.MeterNumber\r\n"
				+ "from [User] U\r\n" + "Join useraccount UA on U.Userid=UA.Userid\r\n"
				+ "join CustomerInfo CI on UA.UtilityAccountNumber = CI.UtilityAccountNumber\r\n"
				+ "join AccountMeterMapping AMM on CI.AccountNumber= AMM.AccountNumber\r\n"
				+ "Join Account A on UA.AccountNumber=A.AccountNumber\r\n"
				+ "where A.status=0 and U.status=0 and CI.AddressType= '" + accountType + "'\r\n"
				+ "and convert(date,U.LinkSentDate)<=convert(date,getdate()-3)";
		System.out.println(query);
		return query;
	}

	public static String getOptedEfficiencyByAccountNumber(String accountNumber, String categoty) {
		String q = "SELECT * FROM EEPromotion LEFT JOIN EEPromotionUsers ON EEPromotion.PromotionId = EEPromotionUsers.PromotionId where EEPromotionUsers.AccountNumber=(select AccountNumber from customerinfo where UtilityAccountNumber="
				+ "'" + accountNumber + "') and EEPromotion.CategoryId=" + "'" + categoty
				+ "' and EEPromotion.IsActive='1' and EEPromotion.IsDeleted='0' ORDER BY EEPromotion.CreatedDate DESC";
		System.out.println(q);
		return q;
	}

	public static String getEfficiencyDescription(String efficiencyName, String categoty) {
		String q = "Select Description from EEPromotion where Title='" + efficiencyName + "'"
				+ "and IsActive='1' and CategoryId='" + categoty + "' and AccountType in ('1','2')"
				+ " and  IsDeleted='0'";
		System.out.println(q);
		return q;
	}

	public static String getEfficiencyAdded(String efficiencyName, String categoty) {
		String q = "SELECT count(*) as Added FROM EEPromotionUsers where promotionId=(Select promotionId from EEPromotion where Title='"
				+ efficiencyName + "'and IsActive='1' and CategoryId='" + categoty
				+ "'and AccountType in ('1','2') and  IsDeleted='0')";
		return q;
	}

	public static String getEfficiencyViews(String efficiencyName, String categoty) {
		String q = "Select views from EEPromotion where Title='" + efficiencyName + "'and IsActive='1' and CategoryId='"
				+ categoty + "'and IsActive='1' and AccountType in ('1','2') and  IsDeleted='0'";
		return q;
	}

	public static String getEfficiencyLikes(String efficiencyName, String categoty) {
		String q = "SELECT count(*) as LikeCount FROM EEPromotionLike where promotionId=(Select promotionId from EEPromotion where Title='"
				+ efficiencyName + "' and IsActive='1' and CategoryId='" + categoty
				+ "' and AccountType in ('1','2')  and  IsDeleted='0')";
		return q;
	}

	public static String getAccountNotificationDetail(String accountNumber) {
		String q = "SELECT AccountNotificationType,EmailOrPhone FROM AccountNotificationType Join AccountNotificationDetail on AccountNotificationType.AccountNotificationTypeID=AccountNotificationDetail.AccountNotificationTypeID where"
				+ " AccountNumber=(Select top 1 AccountNumber from userAccount where utilityAccountNumber='"
				+ accountNumber
				+ "') and UserID=(SELECT [UserAccount].UserID FROM [UserAccount] Full OUTER JOIN [User] on [UserAccount].UserID=[User].UserID where [UserAccount].utilityAccountNumber='"
				+ accountNumber + "'and [UserAccount].RoleID=3)";
		return q;
	}

	public static String getMeterNumberForAccount(String accountNumber, String meterType) {
		String q = null;
		q = "SELECT MeterNumber  FROM AccountMeterMapping where AccountNumber=(select Accountnumber from Account Full outer join CustomerAddress on Account.AddressId=customerAddress.AddressId where CustomerAddress.utilityAccountnumber='"
				+ accountNumber + "') and MeterType='" + meterType + "' and Status='1'";
		return q;
	}

	public static String getMarketPreference(String userName) {
		String q = null;
		q = "SELECT MarketingPreferenceSetting.PreferenceName FROM UserMarketingPreferenceSetting join MarketingPreferenceSetting on  UserMarketingPreferenceSetting.PreferenceId=MarketingPreferenceSetting.PreferenceId    where UserMarketingPreferenceSetting.UserId=(select UserID from [User] where UserName='"
				+ userName + "')";
		return q;
	}

	public static String getCustomerPropertyDetails(String accountNumber) {
		String q = null;
		q = "select UtilityAccountNumber,AddressType,CityName,Zipcode from customerinfo where UtilityAccountNumber='"
				+ accountNumber + "'";
		return q;
	}

	public static String getInteractionCount(String userName) {
		String q = null;
		q = "SELECT count(*) as EventCount from SCPModuleEventLog where userId=(select UserID from [User] where UserName='"
				+ userName + "') order by 1 desc";
		return q;
	}

	public static String getGuestCount(String accountNumber) {
		String q = null;
		q = "SELECT count(*) as GuestCount FROM GuestAccessRequest where UtilityAccountNumber='" + accountNumber + "'";
		return q;
	}

	public static String getGuestDetails(String accountNumber) {
		String q = null;
		q = "SELECT GuestName,GuestEmailID FROM GuestAccessRequest where UtilityAccountNumber='" + accountNumber + "'";
		return q;
	}

	public static String getBillingDetails(String accountNumber) {
		String q = null;
		q = "SELECT Top 10 Billing.BillingDate,BillingDetail.value from BillingDetail join Billing on  BillingDetail.BillingID=Billing.BillingID where BillingDetail.HeadId='22' and Billing.AccountNumber=(select AccountNumber from account where utilityAccountNumber='"
				+ accountNumber + "')  order by 1 desc ";
		return q;
	}

	public static String getPaymentDetails(String accountNumber) {
		String q = null;
		q = "SELECT top 10 * FROM BillingTransaction where accountnumber=(select AccountNumber from account where utilityAccountNumber='"
				+ accountNumber + "') order by 1 desc";
		return q;
	}

	// New query for CSRs
	public static String getServiceAccountNumberCount() {
		String sGetServiceAccountNumberCount = ";"
				+ "select count(AccountNumber) as TotalServiceAccountNumberCount from Account WHERE AccountNumber NOT IN (-1,2)";
		return sGetServiceAccountNumberCount;
	}

	public static String getServiceAccountNumberCountActive() {
		// String sGetServiceAccountNumberCountActive = ";"
		// + "select count(AccountNumber) as ActiveServiceAccountNumber from
		// Account WHERE AccountNumber NOT IN (-1,2) AND Status in (0,1,3);";
		// return sGetServiceAccountNumberCountActive;
		String sGetServiceAccountNumberCountActive = ";"
				+ "SELECT COUNT(AccountNumber) ActiveServiceAccountNumber FROM customerinfo   WHERE AccountStatusID IN (0,1,3) AND CustomerID NOT IN (1,-1)";
		return sGetServiceAccountNumberCountActive;

	}

	public static String getCustomerCount() {
		String sGetCustomerCount = ";"
				+ "SELECT COUNT(DISTINCT customerid) as TotalCustomerNumber FROM CustomerInfo(NOLOCK) WHERE CustomerID NOT IN (1,-1)";
		return sGetCustomerCount;
	}

	public static String getCustomerCountActive() {
		String sGetCustomerCountActive = ";"
				+ "SELECT COUNT(DISTINCT customerid) as TotalCustomerNumberActive FROM CustomerInfo(NOLOCK) WHERE CustomerID NOT IN (1,-1) AND AccountStatusID IN (0,1,3)";
		return sGetCustomerCountActive;
	}

	/**
	 * This Method is to get the Utility Number by the meter type and customer
	 * type
	 * 
	 * @param meterType
	 *            E,W,G
	 * @param customerType
	 *            Enterprise,Mass-market
	 * @return
	 */
	public static String getUtilityNumberByMeterAndCustomerType(String meterType, String customerType) {
		String query = "Declare @MeterType Varchar(500)='" + meterType + "',@CustomerType Varchar(200)='" + customerType
				+ "'" + " If(@CustomerType='Enterprise')" + " BEGIN"
				+ " select  CI.AccountNumber,CI.UtilityAccountnUmber from AccountMeterMapping AMM"
				+ " Inner Join CustomerInfo CI On AMM.AccountNumber=CI.AccountNumber"
				+ " where CI.AddressTypeID=2 And CI.PortalAccessTypeID=1 and CI.AccountStatusID=0"
				+ " And AMM.MeterType in (Select Item from dbo.SplitString(@MeterType,','))" + " END" + " Else"
				+ " BEGIN" + " select  CI.AccountNumber,CI.UtilityAccountnUmber from AccountMeterMapping AMM"
				+ " Inner Join CustomerInfo CI On AMM.AccountNumber=CI.AccountNumber"
				+ " where CI.AddressTypeID=1 And CI.PortalAccessTypeID=0 and CI.AccountStatusID=0"
				+ " And AMM.MeterType in (Select Item from dbo.SplitString(@MeterType,','))" + " END";
		return query;
	}

	// sql query for connect Me Template
	public static String getConnectMetemplate() {
		String sConnectMeTemplate = ";" + "SELECT top 1 * FROM TemplateType where status=1 and moduleid=2";
		return sConnectMeTemplate;
	}

	// sql query for Service Template
	public static String getServicetemplate() {
		String sServiceTemplate = ";" + "SELECT top 1 * FROM TemplateType where status=1 and moduleid=1";
		return sServiceTemplate;
	}

	public static String getConnectMeTopics() {
		String sConnectMeTopic = ";" + "SELECT  * FROM ConnectTopicMaster where IsActive=1";
		return sConnectMeTopic;
	}

	public static String getServiceTopics() {
		String sServiveTopic = ";" + "SELECT * FROM SRReasonMaster where IsActive=1";
		return sServiveTopic;
	}

	public static String getBanners() {
		String sBanners = ";" + "select * from BannerMaster where IsActive=1";
		return sBanners;
	}

	public static String getRegistrationTemplates() {
		String stempReg = ";" + "select * from TemplateMaster";
		return stempReg;
	}

	public static String getRegistrationTemplatesActive() {
		String stempRegActive = ";" + "select * from TemplateMaster where isActive=1";
		return stempRegActive;
	}

	public static String getBannersDetails(String att) {
		String stempRegActive = ";" + "select * from BannerMaster where IsActive=1 and BannerName='" + att + "'";
		return stempRegActive;
	}

	public static String getAllEmailTemplates() {
		String sEmailTemplate = ";" + "select * from EMailTemplate";
		return sEmailTemplate;
	}

	public static String getEmailTemplateDetails(String tempName) {
		String stempEmail = ";" + "select * from EMailTemplate where TemplateName='" + tempName + "'";
		return stempEmail;
	}

	public static String getFieldValueUniversalSearch(String field) {
		String q = "select top 1 " + field + " from CustomerInfo ORDER BY UtilityAccountNumber DESC";
		return q;
	}

	public static String getFieldValueUniversalSearch() {
		String q = "select top 1 * from Customerinfo  ORDER BY FullName ASC";
		return q;
	}

	public static String getValueByFieldName(String customerName, String zipCode, 
			String accountNumber, String emailID,
			String mobileNumber, String address) {
		String q = "SELECT UtilityAccountNumber, FullName, EmailID, Address1, ZipCode "
				+ "FROM customerinfo "
				+ "WHERE ";
		boolean flag = false;
		if (!customerName.equalsIgnoreCase("")) {
			q = q + "FullName like '%" + customerName + "%'";
			flag = true;
		}
		if (!zipCode.equalsIgnoreCase("")) {
			if (flag) {
				q = q + " and ";
			}
			q = q + "zipCode like '%" + zipCode + "%'";
			flag = true;
		}
		if (!accountNumber.equalsIgnoreCase("")) {
			if (flag) {
				q = q + " and ";
			}
			q = q + "UtilityAccountNumber like '%" + accountNumber + "%'";
			flag = true;
		}
		if (!emailID.equalsIgnoreCase("")) {
			if (flag) {
				q = q + " and ";
			}
			q = q + "EmailID like '%" + emailID + "%'";
			flag = true;
		}
		if (!mobileNumber.equalsIgnoreCase("")) {
			if (flag) {
				q = q + " and ";
			}
			q = q + "MobilePhone like '%" + mobileNumber + "%'";
			flag = true;
		}
		if (!address.equalsIgnoreCase("")) {
			if (flag) {
				q = q + " and ";
			}
			q = q + "Address1 like '%" + address + "%'";
			flag = true;
		}
		q = q + " ORDER BY FullName ASC";
		return q;
	}

	/*
	 * Query for Fetching the reset password msg
	 */
	public static String getResetPasswordLinkAdmin(String emailID) {
		String q = "SELECT top 1 *  FROM ContractAccountNotifyEmail where emailId='" + emailID
				+ "' AND subject='SCM Password Reset Link' ORDER BY 1 DESC";
		return q;
	}

	/*
	 * Query to fetch the Emailid form admin user
	 */
	public static String getUsernameFromAdminUser() {
		String q = "select top 10 username from Adminuser order by username ASC";
		return q;

	}

	public static String getFootPrintData(int zipcode, int locationtype) {
		String q = "select Name from GreenFootPrintLocations where LocationTypeId =" + locationtype + "and ZipCode = "
				+ zipcode + "order by Name ASC";
		return q;

	}

	public static String getFootPrintName(String username, String zipcode) {
		String q = "select Name from GreenFootPrintLocations where username = " + username + " and ZipCode = "
				+ zipcode;
		return q;
	}

	public static String getPaymentLocationsData() {
		String q = "select LocationName from PaymentLocation where IsDeleted=0 and cityId = '12979' order by LocationName ASC";
		return q;

	}

	public static String getPaymentDetails_Chase_TransID(String transID) {
		String q = "select * from PaymentRecons where orderid='" + transID + "'";
		return q;
	}

	public static String getCustomerRefNum(String cardName, String userID, String utilityAccNum) {
		String q = "select top 1 * from PaymentProfiles where userid='" + userID + "' and CustomerName='" + cardName
				+ "' and serviceAccountNumber='" + utilityAccNum + "' and IsDeleted=0 order by createdDate desc";
		return q;
	}

	public static String getUserNameFromUserID(String userID) {
		String q = "select FullName from [User] where UserID='" + userID + "'";
		return q;
	}

	public static String getAutoPayCount(String utilityNum) {
		String q = "SELECT COUNT(*)  AS autopayCount from AccountRecurringPayment where utilityAccountNumber='"
				+ utilityNum + "' order by 1 desc ";
		return q;
	}

	public static String getAutoPayInfo(String utilityNum) {
		String q = "SELECT * from AccountRecurringPayment where utilityAccountNumber='" + utilityNum
				+ "' order by 1 desc";
		return q;
	}

	public static String getTop10AdminRoleName() {
		String q = "select Top 10 RoleName from role order by rolename ASC";
		return q;
	}

	public static String getAllAdminRoleName() {
		String q = "select RoleName from role order by rolename ASC";
		return q;
	}

	public static String getorderIDFromPayments(int postingStatus, String statusMessage) {
		String q = "select top 1 * from PaymentRecons where CisPostingStatus ='" + postingStatus
				+ "' and StatusMessage = '" + statusMessage + "' order by 1 desc";
		return q;
	}

	public static String getorderIDFromPayments(String orderId) {
		String q = "select * from paymentRecons where orderid = '" + orderId + "'";
		return q;
	}

	public static String getEmailSubject(String emailid) {
		String q = "SELECT top 1 * FROM ContractAccountNotify where emailid ='" + emailid + "' order by 1 desc";
		return q;
	}

	public static String getEmailId_Notification_Pre(String UtilityAccountNumber) {
		String q = "SELECT * FROM AccountNotificationDetail where AccountNumber=(select AccountNumber from Account where utilityAccountNumber='"
				+ UtilityAccountNumber + "') and AccountNotificationTypeID=6";
		return q;
	}

	public static String getEmailTemplate(String templateName) {
		String q = "select * from EMailTemplate where TemplateName = '" + templateName + "'";
		return q;
	}

	public static String getPaymentOrderIDByCreateDate(String date, String paymentStatus) {
		String q = "select top 1 * from PaymentRecons where CreatedDate like '" + date + "%' and StatusMessage='"
				+ paymentStatus + "'" + "and IsRefunded=0 and IsReversed=0";
		return q;
	}

	public static String getPaymentTransactionID() {
		String q = "SELECT top 1 * FROM PaymentRecons where StatusMessage='Successful'and IsRefunded=0 and IsReversed=0 order by PaymentDate desc";
		return q;
	}

	public static String getEmailExcelReports(String emailId) {
		String q = "select top 1 * from contractaccountnotify where EmailId = '" + emailId + " order by 1 desc ";
		return q;
	}

	public static String getEmailIDFromAdminUser(String username) {
		String q = "select EmailID from Adminuser where username ='" + username + "'";
		return q;

	}

	// query for Notification Module
	public static String getNotificationByPlaceHolder(String placeHolderNumber) {
		String q = " exec GetInboxMessagesAdmin @accountnumber=N'2',@pagesize=N'50',@timeoffset=N'-480',@placeholderid=N'"
				+ placeHolderNumber + "', @pageindex=N'1',@userid=N'1',@ispageload=N'0'";
		return q;
	}

	// query for validate the notification in db
	public static String getSubjectNotification(String subject) {
		String q = "SELECT * FROM ContractAccountNotifyEmail where Subject = '" + subject + "' order by 1 desc";
		return q;
	}

	public static String getEmailIdByUtilityAccNumber(String utilityAccNum) {
		String q = "select EmailID from customerInfo where UtilityAccountNumber = '" + utilityAccNum + "'";
		return q;
	}

	/*
	 * This method is used to get the count of the Customer ref no present for
	 * an userID
	 */
	public static String getCustRefNoCount(String userID) {
		String q = "select  count(CustomerRefNum) as custRefNumber  from paymentprofiles where userId=" + userID
				+ " and isDeleted=0";
		return q;
	}

	public static String getCustRefnumber(String userID) {
		String q = "select CustomerRefNum from paymentprofiles where userId=" + userID + " and isDeleted=0";
		return q;
	}

	/*
	 * //query for validate the notification in db public static String
	 * getSubjectNotification(String subject) { String q =
	 * "SELECT * FROM ContractAccountNotifyEmail where Subject = '"
	 * +subject+"' order by 1 desc"; return q; }
	 */
	// query for Efficiency Module
	public static String getEfficiencyCount(String categoryId) {
		String q = "SELECT COUNT(categoryId) as Count from EEPromotion where categoryId='" + categoryId
				+ "' and IsDeleted=0";
		return q;
	}

	public static String getEfficiencyCount(String categoryId, String title) {
		String q = "SELECT COUNT(categoryId) as Count from EEPromotion where categoryId='" + categoryId
				+ "' and IsDeleted=1 and Title = '" + title + "'";
		return q;
	}

	public static String getEfficiencyDetails(String categoryID, String efficiencyName) {
		String q = "SELECT * from EEPromotion where categoryId='" + categoryID + "'and title = '" + efficiencyName
				+ "'";
		return q;
	}

	public static String getEfficiencyDetails(String categoryId) {
		String q = "SELECT * from EEPromotion where categoryId='" + categoryId + "' and IsDeleted=0";
		return q;
	}

	// query for Reset Password
	public static String getEmailContent(String emailID) {
		String q = "SELECT top 1 *  FROM ContractAccountNotifyEmail where emailId='" + emailID
				+ "' AND subject='SCM Temporary Password' ORDER BY 1 DESC";
		return q;
	}

	public static String setUserPassword(String password, String utilityAccountNumber) {
		String q = "UPDATE [user] SET Password ='" + password
				+ "' WHERE userID = (SELECT userID FROM [userAccount] "
				+ "WHERE utilityAccountNumber ='" + utilityAccountNumber + "')";
		return q;

	}

	public static String getPasswordUpdatedEmailContent(String emailID) {
		String q = "SELECT top 1 * FROM ContractAccountNotifyEmail "
				+ "WHERE emailId='" + emailID + "' ORDER BY 1 DESC";
		return q;
	}

	/**
	 * This Method is used to get the userName from userID
	 * 
	 * @param userName
	 * @return
	 */
	public static String getUserIDFromUserName(String userName) {
		String q = "SELECT UserId FROM [User] "
				+ "WHERE userName='" + userName + "'";
		return q;
	}

	public static String getAccountNumberFromUtilityAccountNumber(String utilityAccountNumber) {
		String q = "SELECT AccountNumber FROM Account "
				+ "WHERE utilityAccountNumber='" + utilityAccountNumber + "'";
		return q;
	}
	
	/*****************************************************
	 * >>>>>>>>>>>> OUTAGE RELATED QUERIES <<<<<<<<<<<<<<*
	 *****************************************************/
	public static String getCreatedOutageWithID(String sOutageId) {
		String sQuery = "SELECT * FROM OutageNotification "
				+ "WHERE OutageId = '"+sOutageId+"'";
		return sQuery;
	}
	
	public static String getOutageIdToUpdate() {
		String sQuery = "SELECT TOP 1 * FROM OutageNotification "
				+ "ORDER BY OutageId DESC";
		return sQuery;
	}
}
