package application;

/********************************************************************************
JDBC takes the values from the visitor object and inserts them into the database.   	
********************************************************************************/
 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.*;


public class JDBC
{
   static Integer id;
   public static void insertIntoDB(Visitor visitor) 
   {
      Connection con = null;
      Statement stmt;
      
      String url = "jdbc:mysql://localhost:3306/test";//will replace 'localhost' with proper ip address when time comes
      String user = "root";
      String password = "";
   
      try
      {
         Class.forName("com.mysql.jdbc.Driver").newInstance();
         con = DriverManager.getConnection(url, user, password);
      
         if(!con.isClosed())
         {  
            System.out.println("Successfully connected to " +
               "MySQL server using TCP/IP...");
            stmt = con.createStatement();
                        
            /***********************************************************************
            * The series of if statements below modifies the input values as needed
            * to prevent the database from exploding. 
            * *********************************************************************/
            
            
            /*
             * This if statement is basically just a placeholder for when
             * we add in the hasVisited functionality for the form. It
             * adds in a 'true' value by default at the moment.
             */
            
            int v = 0;
            if (visitor.getHasVisited() == true)
            {
            	v = 1;
            }
            
            /*
             * This if-else statement checks to see if the value from the 'heard' drop down menu is null.
             * It sets the value to "No Response" if it is, and leaves it alone otherwise.
             */
            
            String hear = "";
            if (!(visitor.getHeard() == "Billboard") && !(visitor.getHeard() == "Interstate Sign") && !(visitor.getHeard() == "Other"))
            {
            	hear = "No Response";
            }
            else
            {
            	hear = visitor.getHeard();
            }
            
            /*
             * This if-else statement is the same as above, but for the other drop down menu.
             */
            
            String reason = "";
            if (!(visitor.getReason() == "Business") && !(visitor.getReason() == "Convention") && !(visitor.getReason() == "Pleasure") && !(visitor.getReason() == "Other"))
            {
            	reason = "No Response";
            }
            else
            {
            	reason = visitor.getReason();
            }
            
            /*
             * This if-elseif-else block essentially does the same as the above two; it sets the proper value
             * for the radio buttons as needed.
             */
            
            String hotel = "";
            if (visitor.getHotel() == "Yes")
            {
            	hotel = "Yes";
            }
            else if (visitor.getHotel() == "No")
            {
            	hotel = "No";
            }
            else
            {
            	hotel = "No Response";
            }
            
            /*
             * This series of stmt.execute methods insert the data into the database. 
             * It's kind of ugly, but it works. 
             */
            
            stmt.execute("USE VisitorDB");
            
            stmt.execute("INSERT INTO visitors (VisitorID, Fname, Lname, Email) VALUES" + 
            		"(" + visitor.getId() + ", '" + visitor.getFirstName() + "', '" +  
            		visitor.getLastName() + "', '" + visitor.getEmail() + "')");
            
            stmt.execute("INSERT INTO visitorlocations (VisitorID, Latitude, Longitude, City, State, Country, Metro_Area) VALUES" +
            		"(" + visitor.getId() + ", '" + visitor.getLat() + "', '" + visitor.getLong() +
            		"', '" + "City" + "', '" + "State" + "', '" + "Country" + "', '" + "Metro_Area" + "')");
            
            stmt.execute("INSERT INTO visits (VisitorID, Party, Heard, Hotel, Destination, RepeatVisit, TravelingFor, Visiting_Day) VALUES" +
            		"(" + visitor.getId() + ", '" + visitor.getParty() + "' ," + "'" + hear + "'" + ", '" + hotel + "', " + "'" + visitor.getDestination() + "', " + 
            		"'" + v + "'" + ", '" + reason + "', " + "'" + getCurrentTimeStamp() + "'" + ")");            
         }//end if
      } //end try
      
      catch(Exception e) 
      {
         System.err.println("Exception: " + e.getMessage());
      } 
      finally
      {
         try
         {
            if(con != null)
               con.close();
         } 
         catch(SQLException e) {}
      }
   }//end insertIntoDB method   
   
   /*
     This method allows timestamp to be inserted properly into the database
   */
   
   private static java.sql.Timestamp getCurrentTimeStamp()
   {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
   }
   
   
   /**********************************************************************************************************************************
    * 
    */
   
   
   
   
   /*
    * 
    ***********************************************************************************************************************************/
   
   
   /*
    * Reads from the database
    */
   
   public static ArrayList<String> getLatLongs()
   {
		
	  ArrayList<String> latLongs = new ArrayList<String>();
		
      Connection con = null;
      Statement stmt;
      
      String url = "jdbc:mysql://localhost:3306/test";
      String user = "root";
      String password = "";
   
      try
      {
         Class.forName("com.mysql.jdbc.Driver").newInstance();
         con = DriverManager.getConnection(url, user, password);
      
         if(!con.isClosed())
         {  
            System.out.println("Successfully connected to " +
               "MySQL server using TCP/IP...");
            stmt = con.createStatement();
            
            //create and select db
            
            stmt.execute("CREATE DATABASE IF NOT EXISTS VisitorsDB");
            stmt.execute("USE VisitorDB");
            
            
            /**
             * Query entries with latlongs that are not null
             */
            
            ResultSet res = stmt.executeQuery("SELECT Latitude, Longitude FROM visitorlocations WHERE (Latitude <> 'null' AND Longitude <> 'null');");
            
            /**
             * Iterate over the result set from the above query
             */
    		
            while (res.next())
            {
                latLongs.add(res.getString("Latitude"));
                latLongs.add(res.getString("Longitude"));
            }
            
         }
      } 
      
      catch(Exception e) 
      {
         System.err.println("Exception: " + e.getMessage());
      } 
      finally
      {
         try
         {
            if(con != null)
               con.close();
         } 
         catch(SQLException e) {}
      }
	return latLongs;
   }   
   
   
}