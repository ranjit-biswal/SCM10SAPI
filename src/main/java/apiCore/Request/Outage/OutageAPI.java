package apiCore.Request.Outage;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

import com.sus.api.scm.Filepaths;

public class OutageAPI {
	
	String sCreateOutageJsonFP = Filepaths.sCreateOutageJsonFP;
	String sOutPayLoadFileName = sCreateOutageJsonFP + "outagePayLoad.json";
	
	/**
	 * Write outage payload in JSON to create new outage.
	 * 
	 * @param sOutageCause
	 * @param sStartTime
	 * @param sEndTime
	 * @param sOutageMessage
	 * @param sOutageReportInfo
	 * @param sIsResolved
	 */
	public void writeOutagePayloadJson(String sOutageCause, String sStartTime, String sEndTime,
			String sOutageMessage, String sOutageReportInfo, String sIsResolved) {
		// Creating a JSONObject object
		JSONObject jsonObject = new JSONObject();
		// Put type parameter
		jsonObject.put("Type", "0");
		// Put cause of outage.
		jsonObject.put("Cause", "Power Failure");
		// Put Start Time
		jsonObject.put("StartTime", sStartTime);
		// Put circuit
		jsonObject.put("Circuit", "");
		// Put end time
		jsonObject.put("EndTime", sEndTime);
		// Put outage longitude
		jsonObject.put("OutageLongitude", "33.744681");
		// Put outage latitude
		jsonObject.put("OutageLatitude", "-117.755101");
		// Put outage zipcode
		jsonObject.put("Zipcode", "92602");
		// Put outage message
		jsonObject.put("OutageMessage", sOutageMessage);
		// Put outage report info
		jsonObject.put("OutageReportInfo", sOutageReportInfo);
		// Put outage xml data
		jsonObject.put("xmldata",
				"<root><Outage><Area><y>-117.89088483772818</y><x>33.740256417070491</x><zipcode>92602</zipcode></Area><Area><y>-117.70961042366594</y><x>33.773082043018007</x><zipcode>92602</zipcode></Area><Area><y>-117.65261884651756</y><x>33.725980485938763</x><zipcode>92602</zipcode></Area><Area><y>-117.76711698494516</y><x>33.726123257006968</x><zipcode>92602</zipcode></Area><Area><y>-117.76763196907604</y><x>33.726837108785531</x><zipcode>92602</zipcode></Area><Area><y>-117.89088483772818</y><x>33.740256417070491</x><zipcode>92602</zipcode></Area></Outage><Outage><points><y>-117.75510068855846</y><x>33.744681473408249</x><zipcode>92602</zipcode></points></Outage></root>");
		// Put offset
		jsonObject.put("offset", "-330");
		// Put outage id as blank
		jsonObject.put("OutageId", "");
		// Put outage latitude
		jsonObject.put("IsResolved", sIsResolved);
		// Put outage city
		jsonObject.put("City", "1");
		// Put service type ID
		jsonObject.put("ServiceTypeID", 1);
		try {
			FileWriter file = new FileWriter(sOutPayLoadFileName);
			file.write(jsonObject.toJSONString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write outage payload in JSON to update the existing outage.
	 * 
	 * @param sOutageId
	 * @param sOutageCause
	 * @param sStartTime
	 * @param sEndTime
	 * @param sOutageMessage
	 * @param sOutageReportInfo
	 * @param sIsResolved
	 */
	public void writeOutagePayloadJson(String sOutageId, String sOutageCause, String sStartTime, String sEndTime,
			String sOutageMessage, String sOutageReportInfo, String sIsResolved) {
		// Creating a JSONObject object
		JSONObject jsonObject = new JSONObject();
		// Put type parameter
		jsonObject.put("Type", "0");
		// Put cause of outage.
		jsonObject.put("Cause", "Power Failure");
		// Put Start Time
		jsonObject.put("StartTime", sStartTime);
		// Put circuit
		jsonObject.put("Circuit", "");
		// Put end time
		jsonObject.put("EndTime", sEndTime);
		// Put outage longitude
		jsonObject.put("OutageLongitude", "33.744681");
		// Put outage latitude
		jsonObject.put("OutageLatitude", "-117.755101");
		// Put outage zipcode
		jsonObject.put("Zipcode", "92602");
		// Put outage message
		jsonObject.put("OutageMessage", sOutageMessage);
		// Put outage report info
		jsonObject.put("OutageReportInfo", sOutageReportInfo);
		// Put outage xml data
		jsonObject.put("xmldata",
				"<root><Outage><Area><y>-117.89088483772818</y><x>33.740256417070491</x><zipcode>92602</zipcode></Area><Area><y>-117.70961042366594</y><x>33.773082043018007</x><zipcode>92602</zipcode></Area><Area><y>-117.65261884651756</y><x>33.725980485938763</x><zipcode>92602</zipcode></Area><Area><y>-117.76711698494516</y><x>33.726123257006968</x><zipcode>92602</zipcode></Area><Area><y>-117.76763196907604</y><x>33.726837108785531</x><zipcode>92602</zipcode></Area><Area><y>-117.89088483772818</y><x>33.740256417070491</x><zipcode>92602</zipcode></Area></Outage><Outage><points><y>-117.75510068855846</y><x>33.744681473408249</x><zipcode>92602</zipcode></points></Outage></root>");
		// Put offset
		jsonObject.put("offset", "-330");
		// Put outage id as blank
		jsonObject.put("OutageId", sOutageId);
		// Put outage latitude
		jsonObject.put("IsResolved", sIsResolved);
		// Put outage city
		jsonObject.put("City", "1");
		// Put service type ID
		jsonObject.put("ServiceTypeID", 1);
		try {
			FileWriter file = new FileWriter(sOutPayLoadFileName);
			file.write(jsonObject.toJSONString());
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
