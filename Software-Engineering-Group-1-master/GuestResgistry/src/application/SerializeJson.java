package application;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;


public class SerializeJson {

	public static String[] getAddress(String jsonObject) throws JSONException{
		System.out.println(jsonObject);
		
		String[] returner = new String[2];
		String temp1 = "";
		String temp2 = "";
		String metro = "";
		int firstMetroIndex = 0;
		int beginIndex = 0;
		int endIndex = 0;
		
		firstMetroIndex = jsonObject.indexOf("Metropolitan");

		if (firstMetroIndex != -1) {
			String testString = jsonObject;
			int metroIndex = testString.indexOf("Metropolitan");
			String begin = testString.substring(0, metroIndex);
			int begindex = begin.lastIndexOf('"')+1;
			String end = testString.substring(metroIndex);
			int endex = testString.indexOf('"')+metroIndex-5;
			metro = testString.substring(begindex, endex);
			System.out.println(testString.substring(begindex, endex));
			returner[0] = metro;
		} else {
			returner[0] = "";
		}
		
		JSONObject json = new JSONObject(jsonObject);
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		int tempest = 0;
		
		jsonMap = JsonToMap.jsonToMap(json);
		
		tempest = jsonMap.get("results").toString().indexOf("geometry");
		System.out.println(tempest);
		
		
		returner[1] = jsonMap.get("results").toString().substring(20, tempest-1); //"formatted address_ starts at index 2		
		System.out.println("This is returner: " + returner);
		
		return returner;
		
	}
	
}
