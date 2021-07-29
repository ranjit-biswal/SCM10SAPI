package utils;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.*;

public class JsonUtil {

  //  private static final org.apache.log4j.Logger log = Logger.getLogger(Runner.class.getName());
    JSONObject jsonObject;
    JSONArray jsonArray;
    Object obj;
    private static String jsonFilePath = null;

    public JsonUtil(String jsonFilePath, String jsonFileName){
        initJsonTestData(jsonFilePath, jsonFileName);
    }

    /**
     * Initialize json test data file.
     */
    public void initJsonTestData(String jsonFilePath, String jsonFileName) {
        try {
            obj = new JSONParser().parse(new FileReader(jsonFilePath + jsonFileName));
            jsonObject = (JSONObject) obj;
            jsonArray = new JSONArray();
        } catch (Exception e) {
            e.printStackTrace();
         //   log.info("Json test data file :" + jsonFileName + " not found");
        }
    }

    /**
     * This method is used to get json data value as String.
     * @param key
     * @return
     */
    public String getStringJsonValue(String key) {
        String value = null;
        value = String.valueOf(jsonObject.get(key));
        return value;
    }

    /**
     * This method is used to get json data object as Map.
     * @param key
     */
    public Map getMapJsonObject(String key) {
        Map jsonObj = null;
        jsonObj = (Map) jsonObject.get(key);
        return jsonObj;
    }

    /**
     * This method is used to get json data value as JSON Array.
     *
     * @param key
     * @return
     *
     */
    public JSONArray getJSONArrayObject(String key) {
        JSONArray jsonArrayObj = null;
        jsonArrayObj = (JSONArray) jsonObject.get(key);
        return jsonArrayObj;
    }

    /**
     * This method is used to get the json data value as long.
     *
     * @param key
     * @return
     *
     */
    public long getLongJsonValue(String key) {
        long value = 0;
      //  value = (long) jsonObject.get(key);
        value = Long.valueOf((String) jsonObject.get(key));
        return value;
    }

    /**
     * This method is used to set the json test data key value.
     *
     * @param key
     * @param value
     *
     */
    public void setStringJsonValue(String key, String value) {
        jsonObject.put(key, value);
    }

    /**
     * This method is used to set the json test data key value.
     *
     * @param key
     * @param value
     */
    public void setLongJsonValue(String key, long value) {
        jsonObject.put(key, value);
    }

    /**
     * This method is used to set the json test data key map.
     *
     * @param key
     * @param value
     */
    public void setMapJsonObject(String key, Map value) {
        jsonObject.put(key, value);
    }

    /**
     * This method is used to set the json test data key map.
     *
     * @param key
     * @param value
     */
    /*public void setMapJsonArray(String key, List<Map> value) {
        for (Map m : value) {
            ((Object) jsonArray).put(value);
        }
        jsonObject.put(key, jsonArray);
    }*/


    public static void main(String args[]){
        JsonUtil jsonUtil = new JsonUtil(jsonFilePath, "testDataSCP.json");
        String pageHeader = jsonUtil.getStringJsonValue("pageHeaderCrp");
        System.out.println("Page Header : " + pageHeader);
        Map<String, String> residentialRegData = new HashMap<String, String>();
        List<String> key = new ArrayList<String>();
        key.add("");
        String[] registrationMandatoryFields = {"accountNo", "firstName"};
        String[] registrationMandatoryFields1 = {"accountNo", "firstName", "lastName", "email", "zipCode",
                "userName", "password", "confirmPassword","securityQuestion1","securityAnswer1","securityQuestion2",
                "securityAnswer2","meterNo","primaryContactNo","contactType","ssn","drivingLicenseNo","streetAddress"};
        for (String registrationMandatoryField: registrationMandatoryFields){
            residentialRegData.put(registrationMandatoryField, "value");
        }
        //jsonUtil.setMapJsonObject("residentialRegData1Crp", residentialRegData);
        residentialRegData = jsonUtil.getMapJsonObject("residentialRegData1Crp");
        for (Map.Entry<String, String> mapEntry: residentialRegData.entrySet()){
            System.out.println("Key :" + mapEntry.getKey() + " " +"Value :" + mapEntry.getValue());
        }
    }

}
