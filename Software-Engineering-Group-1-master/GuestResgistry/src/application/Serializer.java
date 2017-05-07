package application;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;


public class Serializer {

	public static String getAddress(String jsonObject) throws JSONException{
		
		JSONObject json = new JSONObject(jsonObject);
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		String returner = "";
		int tempest = 0;
		
		jsonMap = MakeMap.jsonToMap(json);
		
		tempest = jsonMap.get("results").toString().indexOf("geometry");
		System.out.println(tempest);
		returner = jsonMap.get("results").toString().substring(20, tempest-1); //"formatted address_ starts at index 2
		System.out.println(returner);
		
		return returner;
		
	}
	
}
