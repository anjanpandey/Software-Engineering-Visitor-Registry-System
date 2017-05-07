package adminApplication;

import java.util.Date;

public class VisitorDetails {
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
	private String repeatVisitString;
	private String travelingFor;
	private Date visitingDay;

	public VisitorDetails(int id, String email, String latitude, String longitude, String city, String metro,
			String state, String country, Integer party, String heard, String hotel, String destination,
			boolean repeatVisit, String travelingFor, Date visitingDay) {
		this.setId(id);
		this.setEmail(email);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setCity(city);
		this.setMetro(metro);
		this.setState(state);
		this.setCountry(country);
		this.setParty(party);
		this.setHeard(heard);
		this.setHotel(hotel);
		this.setDestination(destination);
		this.setRepeatVisit(repeatVisit);
		this.setTravelingFor(travelingFor);
		this.setVisitingDay(visitingDay);
	}

	public VisitorDetails(String email, String city, String metro, String state, String country, Integer party,
			String heard, String hotel, String destination, boolean repeatVisit, String travelingFor,
			Date visitingDay) {
		this.setId(AdminJDBC.generateID());
		this.setEmail(email);
		this.setLatitude("");
		this.setLongitude("");
		this.setCity(city);
		this.setMetro(metro);
		this.setState(state);
		this.setCountry(country);
		this.setParty(party);
		this.setHeard(heard);
		this.setHotel(hotel);
		this.setDestination(destination);
		this.setRepeatVisit(repeatVisit);
		this.setTravelingFor(travelingFor);
		this.setVisitingDay(visitingDay);
	}

	public VisitorDetails(String email, String city, String metro, String state, String country, Integer zip,
			Integer party, String heard, String hotel, String destination, boolean repeatVisit, String travelingFor,
			Date visitingDay) {
		this.setId(AdminJDBC.generateID());
		this.setEmail(email);
		this.setCity(city);
		this.setMetro(metro);
		this.setState(state);
		this.setCountry(country);
		this.setZip(zip);
		if (zip != null) {
			String[] latlong = APIClient.geocodingRequest(zip.toString());
			if (latlong.length > 1) {
				String lat = latlong[0];
				String lng = latlong[1];
				if (!lat.isEmpty()) {
					this.setLatitude(lat);
				}
				if (!lng.isEmpty()) {
					this.setLongitude(lng);
				}
			}
		}
		this.setParty(party);
		this.setHeard(heard);
		this.setHotel(hotel);
		this.setDestination(destination);
		this.setRepeatVisit(repeatVisit);
		this.setTravelingFor(travelingFor);
		this.setVisitingDay(visitingDay);
	}

	public VisitorDetails(String email, String latitude, String longitude, Integer party, String heard, String hotel,
			String destination, boolean repeatVisit, String travelingFor, Date visitingDay) {
		this.setId(AdminJDBC.generateID());
		this.setEmail(email);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setCity("");
		this.setMetro("");
		this.setState("");
		this.setCountry("");
		this.setParty(party);
		this.setHeard(heard);
		this.setHotel(hotel);
		this.setDestination(destination);
		this.setRepeatVisit(repeatVisit);
		this.setTravelingFor(travelingFor);
		this.setVisitingDay(visitingDay);
	}

	public VisitorDetails(int id, String email, String latitude, String longitude, String city, String metro,
			String state, String country, int zip, int party, String heard, String hotel, String destination,
			boolean repeatVisit, String travelingFor, Date visitingDay) {
		this.setId(id);
		this.setEmail(email);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setCity(city);
		this.setMetro(metro);
		this.setState(state);
		this.setCountry(country);
		this.setZip(zip);
		this.setParty(party);
		this.setHeard(heard);
		this.setHotel(hotel);
		this.setDestination(destination);
		this.setRepeatVisit(repeatVisit);
		this.setTravelingFor(travelingFor);
		this.setVisitingDay(visitingDay);
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
		if (!email.equals("null"))
			this.email = email;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		if (!latitude.equals("null"))
			this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		if (!longitude.equals("null"))
			this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if (city != null && !city.equals("null"))
			this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if (state != null && !state.equals("null"))
			this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		if (country != null && !country.equals("null"))
			this.country = country;
	}

	public Integer getParty() {
		return party;
	}

	public void setParty(Integer party) {
		if (party != null && !party.equals("null"))
			this.party = party;
	}

	public String getHeard() {
		return heard;
	}

	public void setHeard(String heard) {
		if (heard != null && !heard.equals("null"))
			this.heard = heard;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		if (hotel != null & !hotel.equals("null"))
			this.hotel = hotel;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		if (destination != null && !destination.equals("null"))
			this.destination = destination;
	}

	public Boolean getRepeatVisit() {
		return repeatVisit;
	}

	public void setRepeatVisit(Boolean repeatVisit) {
		this.repeatVisitString = (repeatVisit ? "true" : "false");
		this.repeatVisit = repeatVisit;
	}

	public String getTravelingFor() {
		return travelingFor;
	}

	public void setTravelingFor(String travelingFor) {
		if (travelingFor != null && !travelingFor.equals("null"))
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
		if (metro != null && !metro.equals("null"))
			this.metro = metro;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		if (zip != null && zip > 0)
			this.zip = zip;
	}

	public String getRepeatVisitString() {
		return repeatVisitString;
	}

	public void setRepeatVisitString(String repeatVisitString) {
		this.repeatVisitString = repeatVisitString;
		this.repeatVisit = (repeatVisitString.equals("true") ? true : false);
	}

}
