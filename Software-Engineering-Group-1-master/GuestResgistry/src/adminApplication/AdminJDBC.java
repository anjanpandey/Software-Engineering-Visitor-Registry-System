package adminApplication;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import application.APIClient;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AdminJDBC {
	/*
	 * Reads from the database
	 */
	private static Set<Integer> ids;

	public static List<VisitorDetails> getVisitorDetailsFromQuery(String query) {
		Connection con = null;
		Statement stmt;

		List<VisitorDetails> resultSet = new ArrayList<VisitorDetails>();

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

				ResultSet res = stmt.executeQuery(query);

				while (res.next()) {
					resultSet.add(new VisitorDetails(res.getInt("VisitorID"), res.getString("Email"),
							res.getString("Latitude"), res.getString("Longitude"), res.getString("City"),
							res.getString("Metro"), res.getString("State"), res.getString("Country"), res.getInt("zip"),
							res.getInt("Party"), res.getString("Heard"), res.getString("Hotel"),
							res.getString("Destination"), (res.getInt("RepeatVisit") == 1 ? true : false),
							res.getString("TravelingFor"), (Date) res.getDate("Visiting_Day")));
				}
			}

			return resultSet;
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
		return null;
	}

	public static List<VisitorDetails> getVisitorsFromDateRange(LocalDate start, LocalDate end) {
		Connection con = null;
		Statement stmt;

		List<VisitorDetails> resultSet = new ArrayList<VisitorDetails>();

		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, user, password);

			if (!con.isClosed()) {
				System.out.println("Successfully connected to " + "MySQL server using TCP/IP...");
				stmt = con.createStatement();

				stmt.execute("CREATE DATABASE IF NOT EXISTS visitordb");
				stmt.execute("USE visitordb");

				Date startDate = java.sql.Date.valueOf(start);
				Date endDate = java.sql.Date.valueOf(end);

				ResultSet res = stmt.executeQuery(
						"SELECT * FROM visitors LEFT JOIN visits ON visitors.VisitorID = visits.VisitorID LEFT JOIN visitorlocations on visitors.VisitorID = visitorlocations.VisitorID WHERE Visiting_Day BETWEEN "
								+ startDate + " AND " + endDate + " ORDER BY Visiting_Day");

				while (res.next()) {
					resultSet.add(new VisitorDetails(res.getInt("VisitorID"), res.getString("Email"),
							res.getString("Latitude"), res.getString("Longitude"), res.getString("City"),
							res.getString("Metro"), res.getString("State"), res.getString("Country"),
							res.getInt("Party"), res.getString("Heard"), res.getString("Hotel"),
							res.getString("Destination"), (res.getString("RepeatVisit") == "true" ? true : false),
							res.getString("TravelingFor"), (Date) res.getDate("Visiting_Day")));
				}

			}

			return resultSet;
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
		return null;
	}

	public static List<String> getCitiesandMetros() {
		List<String> cities = new LinkedList<String>();

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
				 * Query entries with the Zip '71467'
				 */
				Set<String> fields = new HashSet<String>();
				ResultSet res = stmt.executeQuery("SELECT DISTINCT City, State, Metro FROM visitorlocations");
				try {
					while (res.next()) {
						String city = res.getString("City");
						String state = res.getString("State");
						String metro = res.getString("Metro");
						if (metro == null || metro.equals("") || metro.equals("null") || metro.isEmpty()) {
							if (city != null && !city.equals("null") && !city.isEmpty() && state != null && !state.isEmpty()) {
								fields.add(city + ", " + state);
							}
							else if (city != null && !city.equals("null") && !city.isEmpty()){
								fields.add(city);
							}
						} else {
							if (state != null && !state.isEmpty()) {
								fields.add(metro + ", " + state);
							}
						}
					}
					res.close();
					for (String city : fields) {
						cities.add(city);
					}
					cities.sort(Comparator.naturalOrder());
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (

		SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return cities;
	}

	public static List<String> getCountries() {
		return getFieldOptions("Country", "visitorlocations");
	}

	public static List<String> getDestinations() {
		return getFieldOptions("Destination", "visits");
	}

	public static List<String> getHotels() {
		return getFieldOptions("Hotel", "visits");
	}

	public static List<String> getReferences() {
		return getFieldOptions("Heard", "visits");
	}

	public static List<String> getStates() {
		return getFieldOptions("State", "visitorlocations");
	}

	public static List<String> getReasons() {
		return getFieldOptions("TravelingFor", "visits");
	}

	private static List<String> getFieldOptions(String field, String tableName) {
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
				 * Query entries with the Zip '71467'
				 */
				List<String> fields = new LinkedList<String>();
				System.out.println(field + " from " + tableName);
				ResultSet res = stmt.executeQuery("SELECT DISTINCT " + field + " FROM " + tableName);
				while (res.next()) {
					fields.add(res.getString(field));
				}
				fields.sort(Comparator.naturalOrder());
				return fields;
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
		return null;
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
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void deleteVisitor(VisitorDetails vd) {
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

				stmt.execute("CREATE DATABASE IF NOT EXISTS VisitorsDB");
				stmt.execute("USE visitordb");

				int visitorID = vd.getId();
				stmt.executeUpdate("DELETE FROM visitors WHERE VisitorID=" + visitorID);
				stmt.executeUpdate("DELETE FROM visits WHERE VisitorID=" + visitorID);
				stmt.executeUpdate("DELETE FROM visitorlocations WHERE VisitorID=" + visitorID);
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateVisitorDetails(VisitorDetails vd) {
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

				stmt.execute("CREATE DATABASE IF NOT EXISTS visitordb");
				stmt.execute("USE visitordb");

				int visitorID = vd.getId();
				Integer repeatVisit = vd.getRepeatVisit() ? 1 : 0;
				System.out.println(vd.getVisitingDay().toString());
				Timestamp visitingDay = new java.sql.Timestamp(vd.getVisitingDay().getTime());
				String visitsQuery = "UPDATE visits SET Party=" + vd.getParty() + ", Heard='" + vd.getHeard()
						+ "', Hotel='" + vd.getHotel() + "', Destination='" + vd.getDestination() + "', RepeatVisit="
						+ repeatVisit + ", TravelingFor='" + vd.getTravelingFor() + "', Visiting_Day='"
						+ visitingDay.toString() + "' WHERE VisitorID=" + visitorID + "";
				System.out.println(visitsQuery);
				stmt.executeUpdate(visitsQuery);
				String visitorLocationsQuery = "UPDATE visitorlocations SET Latitude='" + vd.getLatitude()
						+ "', Longitude='" + vd.getLongitude() + "', City = '" + vd.getCity() + "', Metro='"
						+ vd.getMetro() + "', State='" + vd.getState() + "', Country='" + vd.getCountry() + "', Zip="
						+ vd.getZip() + " WHERE VisitorID=" + visitorID + "";
				System.out.println(visitorLocationsQuery);
				stmt.executeUpdate(visitorLocationsQuery);
				String visitorsQuery = "UPDATE visitors SET Email='" + vd.getEmail() + "' WHERE VisitorID=" + visitorID
						+ "";
				System.out.println(visitorsQuery);
				stmt.executeUpdate(visitorsQuery);
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addVisitors(List<VisitorDetails> newData) {
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

				for (VisitorDetails vd : newData) {

					int visitorID = vd.getId();
					Date newDate = vd.getVisitingDay();

					Timestamp visitingDay = (newDate == null ? Timestamp.valueOf(LocalDateTime.now())
							: new Timestamp(newDate.getTime()));
					Integer zip = vd.getZip();
					if (zip != null) {
						String[] latlng = APIClient.geocodingRequest(zip.toString());
						vd.setLatitude(latlng[0]);
						System.out.println("lat" + latlng[0]);
						vd.setLongitude(latlng[1]);
						System.out.println("lng" + latlng[0]);
					}
					String visitorLocationsQuery = "INSERT INTO visitorlocations (VisitorID, Latitude, Longitude, City, Metro, State, Country, Zip) VALUES ";
					String visitorsQuery = "INSERT INTO visitors (VisitorID, Email) VALUES ";
					String visitsQuery = "INSERT INTO visits (VisitorID, Party, Heard, Hotel, Destination, RepeatVisit, TravelingFor, Visiting_Day) VALUES ";

					visitorLocationsQuery += "(" + visitorID + ", '" + vd.getLatitude() + "', '" + vd.getLongitude()
							+ "', '" + vd.getCity() + "', '" + vd.getMetro() + "', '" + vd.getState() + "', '"
							+ vd.getCountry() + "', " + vd.getZip() + ")";
					visitorsQuery += "(" + visitorID + ", '" + vd.getEmail() + "')";
					visitsQuery += "(" + visitorID + ", " + vd.getParty() + ", \"" + vd.getHeard() + "\", \""
							+ vd.getHotel() + "\", '" + vd.getDestination() + "', " + vd.getParty() + ", '"
							+ vd.getTravelingFor() + "', '" + visitingDay + "')";
					System.out.println(visitorLocationsQuery);
					System.out.println(visitorsQuery);
					System.out.println(visitsQuery);
					stmt.executeUpdate(visitorLocationsQuery);
					stmt.executeUpdate(visitorsQuery);
					stmt.executeUpdate(visitsQuery);
				}
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteVisitors(java.sql.Date ageTimestamp) {
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

				stmt.execute("CREATE DATABASE IF NOT EXISTS visitordb");
				stmt.execute("USE visitordb");

				stmt.executeUpdate("DELETE FROM visitors WHERE Timestamp < " + ageTimestamp);
				stmt.executeUpdate("DELETE FROM visitorlocations WHERE Timestamp < " + ageTimestamp);
				stmt.executeUpdate("DELETE FROM visits WHERE Timestamp < " + ageTimestamp);
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}