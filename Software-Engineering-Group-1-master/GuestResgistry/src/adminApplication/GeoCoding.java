package adminApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

/**
 * @author Crunchify.com and Aaron Cole reverseGroCode is adapted from a
 *         tutorial there. Made to take a latitude and longitude and return a
 *         JSON object with further details to be stored.
 */

public class GeoCoding {
private static String lat;
private static String lon;
	/**
	 * With the use of a helper method converts a lat long into a detailed JSON
	 * object with attributes that include zip code, address, and country.
	 * 
	 * @param latlong
	 *            String object that is a raw lat long returned from Javascript.
	 * @return rgc A String in JSON format containing reverse geo coding information
	 */
	public static String reverseGeoCode(String latlong) {

		String[] latlongs = getLatLong(latlong);
		String rgc = "";
		String rgc1 = "";
		String lat = latlongs[0];
		String lon = latlongs[1];
		try {
			System.out.println("Initiatine reverse geo code");
			URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lon
					+ "&key=AIzaSyCQ0Bf0gGO7vOU90EAC8S0zpJMvYXCWJYU");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			while (null != (rgc = br.readLine())) {
				rgc1 += rgc;
			}

		} catch (Exception ex) {
			// cry, then
			ex.printStackTrace();
		}
		//System.out.println(rgc1);
		return rgc1;
	}

	/**
	 * Helper method used to manipulate a String sent in to make it suitable to be used as an integer.
	 * @param coordinates Lat long sent in from the JavaFX controller
	 * @return latlong A two part String array designed to be used to contact the Google Geocoding api.
	 */
	public static String[] getLatLong(String coordinates) {

		String[] latlong = new String[2];
		String temp = coordinates;
		System.out.println("This is temp: " + temp);
		temp = temp.substring(1, temp.length() - 1);
		Scanner scan = new Scanner(temp);
		scan.useDelimiter(", ");

		latlong[0] = scan.next();
		latlong[1] = scan.next();
		lat = latlong[0];
		lon = latlong[1];
		System.out.println("This is from GeoCode: " + latlong[0] + " " + latlong[1]);
		scan.close();
		return latlong;
	}
	public static String getLat()
	{
		return lat;
	}
	public static String getLon()
	{
		return lon;
	}
	
	
}

