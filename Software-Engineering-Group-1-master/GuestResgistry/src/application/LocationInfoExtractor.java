package application;

import java.util.Scanner;

public class LocationInfoExtractor {
	
	/**
	 * This handles a JSON formatted address, it is comma delimited so it uses a scanner to take pieces such as state and city.
	 * @param rawInfo
	 * @return
	 */
	public static String[] extractLocationInfo(String[] rawInfo){
		
		int i = 0;
		String zipCode = "";
		String state = "";
		String metro = rawInfo[0];
		String[] reformatedInfo = new String[15]; //Intentionally larger than necessary
		
		Scanner scan = new Scanner(rawInfo[1]);
		scan.useDelimiter(", ");
		
		//This will put the street address in the first spot [0], city second, state/zip third, and the nation last
		//Only applicable to the US, so that must be found out as well
		if (rawInfo[1].contains("USA")) {
			while (scan.hasNext()) {
				reformatedInfo[i] = scan.next();
				i++;
			}
			
			//Separate out the zipcode and state, then make the state the fifth item in the array.
			state = reformatedInfo[2].substring(2);
			zipCode = reformatedInfo[2].substring(0, 2);
			
			reformatedInfo[2] = zipCode;
			reformatedInfo[5] = state;
			reformatedInfo[6] = metro;
		}
		
		scan.close();
		System.out.println(reformatedInfo[0]);
		return reformatedInfo;
	}

}
