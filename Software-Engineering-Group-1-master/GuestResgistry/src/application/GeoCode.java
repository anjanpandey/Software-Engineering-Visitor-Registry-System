package application;

import java.io.*;
import java.util.*;
import java.net.URL;

/**
 * @author Crunchify.com and Aaron Cole reverseGroCode is adapted from a
 *         tutorial there. Made to take a latitude and longitude and return a
 *         JSON object with further details to be stored.
 */

public class GeoCode {

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
			URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lon
					+ "&key=AIzaSyC5V3Fi15XSebqH_NsXrv0_UUZjb46vu94");
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
		temp = temp.substring(1, temp.length() - 1);
		Scanner scan = new Scanner(temp);
		scan.useDelimiter(", ");

		latlong[0] = scan.next();
		latlong[1] = scan.next();

		System.out.println("This is from GeoCode: " + latlong[0] + " " + latlong[1]);
		scan.close();

		return latlong;
	}

}
