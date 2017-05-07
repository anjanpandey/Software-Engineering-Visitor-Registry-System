package adminApplication;

public class Visitor 
{
	private static int idCount = 1;
	private int id;
	private String fname;
	private String lname;
	private String email;
	private boolean hasVisited;
	private String reasonForVisit;
	private String heard;
	private String latitude;
	private String longitude;
	private String destination;
	private String hotel;
	private int party;
	
	public Visitor (String email)
	{
		id = idCount;
		idCount++;
		this.email = email;
	}
	
	public Visitor() {
		id = idCount;
		idCount++;
	}

	public int getId()
	{
		return id;
	}
	
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getFirstName()
	{
		return fname;
	}
	public void setFirstName(String fname)
	{
		this.fname = fname;
	}
	
	public String getLastName()
	{
		return lname;
	}
	public void setLastName(String lname)
	{
		this.lname = lname;
	}
	
	public boolean getHasVisited()
	{
		return hasVisited;
	}
	public void setHasVisited(boolean hasVisited)
	{
		this.hasVisited = hasVisited;
	}
	
	public String getReason()
	{
		return reasonForVisit;
	}
	public void setReason(String reasonForVisit)
	{
		this.reasonForVisit = reasonForVisit;
	}
	
	public String getHeard()
	{
		return heard;
	}
	public void setHeard(String heard)
	{
		this.heard = heard;
	}
	
	public String getLat()
	{
		return latitude;
	}
	public void setLat(String latitude)
	{
		this.latitude = latitude;
	}
	
	public String getLong()
	{
		return longitude;
	}
	public void setLong(String longitude)
	{
		this.longitude = longitude;
	}
	
	public String getDestination()
	{
		return destination;
	}
	public void setDestination(String destination)
	{
		this.destination = destination;
	}
	
	public String getHotel()
	{
		return hotel;
	}
	public void setHotel(String hotel)
	{
		this.hotel = hotel;
	}
	
	public int getParty()
	{
		return party;
	}
	public void setParty(int party)
	{
		this.party = party;
	}

	public void setID(int visitorID) {
		this.id = visitorID;	
	}
}