package application;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class APIClient {
	public static String[] geocodingRequest(String zip) {
		Client client = ClientBuilder.newClient();
		String url = "https://maps.googleapis.com/maps/api/geocode/xml?address=" + zip
				+ "&key=AIzaSyBuT9VzPqnYSaB5Os0U4mkI8C7CiuFzwao";
		System.out.println(url);
		WebTarget target = client.target(url);
		String output = (target.request(MediaType.APPLICATION_JSON_TYPE).get(String.class));
		String[] latlng = new String[2];
		System.out.println(output);
		int latStart = output.indexOf("<lat>") + 5;
		int latEnd = output.indexOf("</lat>");
		System.out.print(output.substring(latStart, latEnd));
		latlng[0] = output.substring(latStart, latEnd);
		int lngStart = output.indexOf("<lng>") + 5;
		int lngEnd = output.indexOf("</lng>");
		System.out.print(output.substring(lngStart, lngEnd));
		latlng[1] = output.substring(lngStart, lngEnd);
		return latlng;
	}

}
