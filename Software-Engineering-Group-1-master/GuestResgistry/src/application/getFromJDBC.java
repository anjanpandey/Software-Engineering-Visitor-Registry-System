package application;

import java.util.ArrayList;

/**
 * A helper class used by the Controller.java class. Essentially takes in the latitude and longitude pairs from the database and returns them in an arraylist.
 * @author Aaron Cole
 *
 */
public class getFromJDBC {

	/**
	 * Takes in the latitude and longitude pairs from the database and returns them in an arraylist.
	 * @return latLongs An arraylist (of strings) containing the latitude and longitude pairs from the database.
	 */
	public static ArrayList<String> getLatLongs(){
		
		ArrayList<String> latLongs = new ArrayList<String>();
		
		/*This will be changed later, but for now it is just a static arraylist that I made up*/
		latLongs.add("34.16181816123038");
		latLongs.add("-98.4375");
		latLongs.add("36.4566360115962");
		latLongs.add("-102.83203125");
		latLongs.add("34.88593094075317");
		latLongs.add("-96.15234375");
		latLongs.add("33.284619968887675");
		latLongs.add("-104.23828125");
		latLongs.add("29.22889003019423");
		latLongs.add("-107.05078125");
		latLongs.add("24.5271348225978");
		latLongs.add("-100.546875");
		latLongs.add("38.272688535980976");
		latLongs.add("-110.56640625");
		latLongs.add("44.59046718130883");
		latLongs.add("-107.9296875");
		latLongs.add("43.32517767999296");
		latLongs.add("-108.10546875");
		latLongs.add("43.19716728250127");
		latLongs.add("-106.171875");
		latLongs.add("45.33670190996811");
		latLongs.add("-96.328125");
		latLongs.add("46.800059446787316");
		latLongs.add("-91.23046875");		
		latLongs.add("44.96479793033101");
		latLongs.add("-90.703125");
		latLongs.add("45.706179285330855");
		latLongs.add("-88.9453125");
		latLongs.add("44.33956524809714");
		latLongs.add("-88.41796875");
		latLongs.add("43.58039085560783");
		latLongs.add("-85.4296875");
		
		return latLongs;
		
	}
	
}