package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import database.Connect;

public class EventModel {
	private String eventId;
	private String eventName;
	private String eventDate;
	private String eventLocation;
	private String eventDescription;
	private int organizerId;
	
// Insert Event
	public static boolean createEvent(String eventName, String eventDate, String eventLocation, String eventDescription, int organizerId) {
		Connect db = Connect.getInstance();
		String eventId = generateEventId();
		String query = "INSERT INTO events_ (event_id ,event_name, event_date, event_location, event_description, organizer_id) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = db.preparedStatement(query);
		
		try {
			ps.setString(1, eventId);
			ps.setString(2, eventName);
			ps.setString(3, eventDate);
			ps.setString(4, eventLocation);
			ps.setString(5, eventDescription);
			ps.setInt(6, organizerId);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
// Increment Id for event_id
	public static String generateEventId() {
		Connect db = Connect.getInstance();
	    String query = "SELECT event_id FROM events_ ORDER BY event_id DESC LIMIT 1";
	    PreparedStatement ps = db.preparedStatement(query);
	    
	    try {
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            String lastEventId = rs.getString("event_id");
	            int lastNumber = Integer.parseInt(lastEventId.substring(2));
	            lastNumber++; 
	            return "EV" + String.format("%03d", lastNumber);
	        } else {
	            return "EV001";
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
// Get All Events
	public static ObservableList<EventModel> getAllEventsByOrganizer(int organizerId){
		ObservableList<EventModel> events = FXCollections.observableArrayList();
		Connect db = Connect.getInstance();
		String query = "SELECT * FROM events_ WHERE organizer_id = ?";
		ResultSet rs = null;
		PreparedStatement ps = db.preparedStatement(query);
		
		try {
			ps.setInt(1, organizerId);
			rs = ps.executeQuery();
			while(rs.next()) {
				String id = rs.getString("event_id");
				String name = rs.getString("event_name");
				String date = rs.getString("event_date");
				String location = rs.getString("event_location");
				String description = rs.getString("event_description");
				events.add(new EventModel(id, name, date, location, description));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return events;
	}
	
//	Edit Event
	public static boolean updateEvent(EventModel event) {
		Connect db = Connect.getInstance();
		String query = "UPDATE events_ SET event_name = ?, event_date = ?, event_location = ?, event_description = ? WHERE event_id = ?";
		PreparedStatement ps = db.preparedStatement(query);
		
		try {
			ps.setString(1, event.eventName);
			ps.setString(2, event.eventDate);
			ps.setString(3, event.eventLocation);
			ps.setString(4, event.eventDescription);
			ps.setString(5, event.getEventId());
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

//	Constructor
	public EventModel(String eventId, String eventName, String eventDate, String eventLocation, String eventDescription) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventLocation = eventLocation;
		this.eventDescription = eventDescription;
	}
	
//	Getter and Setter
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public int getOrganizerId() {
		return organizerId;
	}
}
