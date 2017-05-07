package application;

import java.util.Date;

public class Visitor {
	private int id;
	private String email;
	private String latitude;
	private String longitude;
	private String city;
	private String metro;
	private String state;
	private String country;
	private Integer zip;
	private Integer party;
	private String heard;
	private String hotel;
	private String destination;
	private boolean repeatVisit;
	private String travelingFor;
	private Date visitingDay;

	/*
	 * Constructor for visitor object
	 */

	public Visitor(int id, String email, String latitude, String longitude, String city,
			String metro, String state, String country, int zip, int party, String heard, String hotel,
			String destination, boolean repeatVisit, String travelingFor, Date visitingDay) {
		this.id = id;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
		this.city = city;
		this.metro = metro;
		this.state = state;
		this.country = country;
		this.zip = zip;
		this.party = party;
		this.heard = heard;
		this.hotel = hotel;
		this.destination = destination;
		this.repeatVisit = repeatVisit;
		this.travelingFor = travelingFor;
		this.visitingDay = visitingDay;
	}

	/*
	 * A crap ton of getters and setters
	 */

	public Visitor() {
		this.id = JDBC.generateID();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getParty() {
		return party;
	}

	public void setParty(Integer party) {
		this.party = party;
	}

	public String getHeard() {
		return heard;
	}

	public void setHeard(String heard) {
		if (heard.equals("Interstate Sign")) {
			this.heard = "Interstate Sign";
		}
		else if (heard.equals("Billboard")) {
			this.heard = "Billboard";
		}
		else if (heard.equals("Other"))
		{
			this.heard = "Other";
		}
		else {
			this.heard = "No Response";
		}
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		if (hotel==null){
			this.hotel = "No Response";
		}
		else {
			this.hotel = hotel;
		}
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Boolean getRepeatVisit() {
		return repeatVisit;
	}

	public void setRepeatVisit(Boolean repeatVisit) {
		this.repeatVisit = repeatVisit;
	}

	public String getTravelingFor() {
		return travelingFor;
	}

	public void setTravelingFor(String travelingFor) {
		this.travelingFor = travelingFor;
	}

	public Date getVisitingDay() {
		return visitingDay;
	}

	public void setVisitingDay(Date visitingDay) {
		this.visitingDay = visitingDay;
	}

	public String getMetro() {
		return metro;
	}

	public void setMetro(String metro) {
		this.metro = metro;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}

	public void generateNewID() {
		this.id = JDBC.generateID();
	}

	public void clearData() {
		this.city = null;
		this.country = null;
		this.destination = null;
		this.email = null;
		this.heard = null;
		this.hotel = null;
		this.latitude = null;
		this.longitude = null;
		this.metro = null;
		this.party = null;
		this.repeatVisit = false;
		this.state = null;
		this.travelingFor = null;
		this.visitingDay = null;
		this.zip = null;
	}
}// end class
