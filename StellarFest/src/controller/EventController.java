package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import database.Connect;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.EventModel;
import model.User;
import view.AddGuestView;
import view.AddVendorView;

public class EventController {

//	Insert Event Baru
	public static boolean createEvent(String name, String date, String location, String description, int organizerId) {
		if(name.isEmpty()) {
			showError("Event name cannot be empty");
			return false;
		}
		if(date.isEmpty() ||  isFutureDate(date)) {
			showError("Event date must be a future date");
			return false;
		}
		if(location.isEmpty() || location.length() < 5) {
			showError("Event location must be at least 5 characters");
			return false;
		}
		if(description.isEmpty() || description.length() > 200) {
			showError("Event description must not exceed 200 characters");
			return false;
		}
		
		return EventModel.createEvent(name, date, location, description, organizerId);
	}
	
	public static boolean isFutureDate(String date) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
	    try {
	        Date eventDate = sdf.parse(date);
	        return eventDate.after(new Date()); 
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
// Display error message
	private static void showError(String message) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setContentText(message);
	    alert.show();
	}
	
//	Melihat Event yang telah di Insert
	public static List<EventModel> getAllEvents(int organizerId){
		return EventModel.getAllEventsByOrganizer(organizerId);
	}
	
// Melihat Semua Vendor yang mau di Invite
	public static List<User> getAllVendor(){
		return AddVendorView.getVendors();
	}
	
	// Melihat Semua Vendor yang mau di Invite
	public static List<User> getAllGuest(){
		
		return AddGuestView.getGuest();
	}

}
