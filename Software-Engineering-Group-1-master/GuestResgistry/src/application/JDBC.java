package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
/********************************************************************************
JDBC takes the values from the visitor object and inserts them into the database.   	
********************************************************************************/
import java.sql.*;

public class JDBC {
	private static Set<Integer> ids;

	/*
	 * This method allows timestamp to be inserted properly into the database
	 */

	private static java.sql.Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}

	public static int generateID() {
		updateIDs();
		Random generator = new Random();
		int id = generator.nextInt(Integer.MAX_VALUE);
		while (ids.contains(id)) {
			id = generator.nextInt(Integer.MAX_VALUE);
		}
		return id;
	}

	public static void updateIDs() {
		ids = new HashSet<Integer>();
		Connection con = null;
		Statement stmt;

		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, user, password);

			if (!con.isClosed()) {
				System.out.println("Successfully connected to " + "MySQL server using TCP/IP...");
				stmt = con.createStatement();

				// create and select db

				stmt.execute("CREATE DATABASE IF NOT EXISTS VisitorsDB");
				stmt.execute("USE visitordb");

				/**
				 * Query entries with the Zip '71467'
				 */

				ResultSet res = stmt.executeQuery("SELECT VisitorID FROM visitors");

				/**
				 * Iterate over the result set from the above query
				 */

				while (res.next()) {
					ids.add(res.getInt("VisitorID"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<String> getLatLongs() {

		ArrayList<String> latLongs = new ArrayList<String>();

		Connection con = null;
		Statement stmt;

		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, user, password);

			if (!con.isClosed()) {
				System.out.println("Successfully connected to " + "MySQL server using TCP/IP...");
				stmt = con.createStatement();

				// create and select db

				stmt.execute("CREATE DATABASE IF NOT EXISTS visitordb");
				stmt.execute("USE visitordb");

				/**
				 * Query entries with latlongs that are not null
				 */

				ResultSet res = stmt.executeQuery(
						"SELECT Latitude, Longitude FROM visitorlocations WHERE (Latitude IS NOT NULL AND Latitude != '' AND Latitude != 'null' AND Longitude IS NOT NULL AND Longitude != '' AND Longitude != 'null')");

				/**
				 * Iterate over the result set from the above query
				 */
				Scanner scan;
				while (res.next()) {
					latLongs.add(res.getString("Latitude"));
					latLongs.add(res.getString("Longitude"));
				}

			}
		}

		catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return latLongs;
	}

	public static int getNumberofVisitors(String city, String metro) {
		int count = 1;

		Connection con = null;
		Statement stmt;

		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, user, password);

			if (!con.isClosed()) {
				System.out.println("Successfully connected to " + "MySQL server using TCP/IP...");
				stmt = con.createStatement();

				// create and select db

				stmt.execute("CREATE DATABASE IF NOT EXISTS visitordb");
				stmt.execute("USE visitordb");

				/**
				 * Query entries with latlongs that are not null
				 */
				if (city.isEmpty() || city == null) {
					city = "tHiSiSnOtAvAlIdCiTy";
				}
				if (metro.isEmpty() || metro == null) {
					metro = "tHiSiSnOtAvAlIdMeTrO";
				}
				String countQuery = "SELECT COUNT(VisitorID) AS VisitorsFromArea FROM visitorlocations WHERE City='"
						+ city + "' OR Metro='" + metro + "'";

				System.out.println(countQuery);

				ResultSet res = stmt.executeQuery(countQuery);

				/**
				 * Iterate over the result set from the above query
				 */

				while (res.next()) {
					int fromHere = res.getInt("VisitorsFromArea");
					System.out.println("" + fromHere);
					count += fromHere;
				}
			}
		}

		catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return count;
	}

	public static void addVisitor(Visitor vd) {
		Connection con = null;
		Statement stmt;

		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, user, password);

			if (!con.isClosed()) {
				System.out.println("Successfully connected to " + "MySQL server using TCP/IP...");
				stmt = con.createStatement();

				// create and select db

				stmt.execute("CREATE DATABASE IF NOT EXISTS visitordb");
				stmt.execute("USE visitordb");

				int visitorID = vd.getId();
				Integer zip = vd.getZip();
				String lat = vd.getLatitude();
				String lng = vd.getLongitude();
				if ((lat == null || lat.isEmpty() || lat.equals("null"))
						&& (lng == null || lng.isEmpty() || lng.equals("null"))) {
					if (zip != null) {
						String[] latlng = APIClient.geocodingRequest(zip.toString());
						vd.setLatitude(latlng[0]);
						System.out.println("lat" + latlng[0]);
						vd.setLongitude(latlng[1]);
						System.out.println("lng" + latlng[1]);
					}
				}
				Timestamp newDate = getCurrentTimeStamp();// vd.getVisitingDay();

				System.out.println(newDate.toString());
				Timestamp visitingDay = new Timestamp(newDate.getTime());
				String visitorLocationsQuery = "INSERT INTO visitorlocations (VisitorID, Latitude, Longitude, City, Metro, State, Country, Zip) VALUES ";
				String visitorsQuery = "INSERT INTO visitors (VisitorID, Email) VALUES ";
				String visitsQuery = "INSERT INTO visits (VisitorID, Party, Heard, Hotel, Destination, RepeatVisit, TravelingFor, Visiting_Day) VALUES ";

				visitorLocationsQuery += "(" + visitorID + ", '" + vd.getLatitude() + "', '" + vd.getLongitude()
						+ "', '" + vd.getCity() + "', '" + vd.getMetro() + "', '" + vd.getState() + "', '"
						+ vd.getCountry() + "', " + vd.getZip() + ")";
				visitorsQuery += "(" + visitorID + ", '" + vd.getEmail() + "')";
				visitsQuery += "(" + visitorID + ", " + vd.getParty() + ", \""
						+ (vd.getHeard() == null ? "No Response" : vd.getHeard()) + "\", \""
						+ (vd.getHotel() == null ? "No Response" : vd.getHotel()) + "\", '" + vd.getDestination()
						+ "', " + (vd.getRepeatVisit() == null ? 0 : (vd.getRepeatVisit() ? 1 : 0)) + ", '"
						+ (vd.getTravelingFor() == null ? "No Response" : vd.getTravelingFor()) + "', '" + visitingDay
						+ "')";
				System.out.println(visitorLocationsQuery);
				System.out.println(visitorsQuery);
				System.out.println(visitsQuery);
				stmt.executeUpdate(visitorLocationsQuery);
				stmt.executeUpdate(visitorsQuery);
				stmt.executeUpdate(visitsQuery);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}