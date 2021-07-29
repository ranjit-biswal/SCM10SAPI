package apiCore.Request.Payments;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import utils.DataBaseUtil;
import utils.SqlQuery;

public class OneTimePaymentAPI {

	public static ResultSet rs;

	public static HashMap<String, String> getPaymentFrom_ChaseDB(String transId) throws Exception {
		HashMap<String, String> paymentMap_DB = new HashMap<String, String>();
		try {
			rs = DataBaseUtil.getResultSetPaymentDatabaseChase(SqlQuery.getPaymentDetails_Chase_TransID(transId));
			rs.next();
			paymentMap_DB.put("StatusMessage", rs.getString("StatusMessage"));// 1
			paymentMap_DB.put("Amount", rs.getString("Amount"));// 2
			paymentMap_DB.put("IsPreFlag", rs.getString("IsPreFlag"));// 3
			paymentMap_DB.put("OrderID", rs.getString("OrderID"));// 4
			paymentMap_DB.put("ServiceAccountNumber", rs.getString("ServiceAccountNumber"));// 5
			paymentMap_DB.put("ChannelType", rs.getString("ChannelType"));// 6
			paymentMap_DB.put("PaymentMethodType", rs.getString("PaymentMethodType"));// 7
			paymentMap_DB.put("PaymentMethodSubType", rs.getString("PaymentMethodSubType"));// 8
			paymentMap_DB.put("ServiceFeeAmount", rs.getString("ServiceFeeAmount"));// 9
			paymentMap_DB.put("AccountNumber", rs.getString("AccountNumber"));// 10
			paymentMap_DB.put("TotalAmount", rs.getString("TotalAmount"));// 11
			paymentMap_DB.put("CustomerName", rs.getString("CustomerName"));// 12
			paymentMap_DB.put("BankName", rs.getString("BankName"));// 13
			paymentMap_DB.put("FirstName", rs.getString("FirstName"));// 14
			paymentMap_DB.put("LastName", rs.getString("LastName"));// 15

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return paymentMap_DB;
	}

	public static HashMap<String, String> getPaymentProfile_ChaseDB(String custName, String user, String acc)
			throws Exception {
		HashMap<String, String> paymentMap_DB = new HashMap<String, String>();
		try {
			rs = DataBaseUtil.getResultSetPaymentDatabaseChase(SqlQuery.getCustomerRefNum(custName, user, acc));
			rs.next();
			paymentMap_DB.put("customerRefNum", rs.getString("customerRefNum"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return paymentMap_DB;
	}

}
