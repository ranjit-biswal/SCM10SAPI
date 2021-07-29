package stepDefinations;

import java.sql.ResultSet;
import com.sus.api.scm.Filepaths;

import cucumber.api.java.Before;
import stepDefinations.Billing.StepDefinationCreateCardProfile;
import utils.JsonUtil;

public class Hooks {
	static JsonUtil jsUtil_paymentProfile_CC = new JsonUtil(Filepaths.sTestDataScpJsonFP, "createProfileCC.json");
	ResultSet rs;

	@Before("@DeleteCreditCard")
	public static void beforeScenario_DeleteCard() throws Exception {
		StepDefinationCreateCardProfile ccp = new StepDefinationCreateCardProfile();
		ccp.deleteCardProfile(jsUtil_paymentProfile_CC.getStringJsonValue("UserID"));
	}

}
