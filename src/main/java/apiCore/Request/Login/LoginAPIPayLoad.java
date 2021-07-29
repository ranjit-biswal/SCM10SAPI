package apiCore.Request.Login;

import com.sus.api.scm.Filepaths;

import utils.Utils;

public class LoginAPIPayLoad {

	static String sSetLoginPayload = Filepaths.sTestDataSCPJsonFP + "Login.json";

	/**
	 * This Method is used to Create Connect Me program payload
	 * 
	 * @author Ranjit Biswal
	 * @return
	 */
	public static String createLoginPayload(String userName, String passWord) {
		String body = null;
		try {
			body = Utils.generateStringFromResource(sSetLoginPayload);
			System.out.println(body);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}
}
