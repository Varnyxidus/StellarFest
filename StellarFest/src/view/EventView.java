package view;

import controller.EventController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;
import singleton.CurrentUser;

public class EventView extends GridPane {
	Stage stage;
	
	Label eventNameLabel, eventDateLabel, eventLocationLabel,
	eventDescriptionLabel;
	TextField eventName, eventDate, eventLocation;
	TextArea eventDescription;
	Button createEvent;

	public void initialize() {
		eventName = new TextField();
		eventName.setPromptText("Enter Event Name");
		
		eventDate = new TextField();
		eventDate.setPromptText("Enter Event Date [ex: 2024-12-25]");
		
		eventLocation = new TextField();
		eventLocation.setPromptText("Enter Event Location");
		
		eventDescription = new TextArea();
		eventDescription.setPromptText("Enter Event Description");
		eventDescription.setWrapText(true);
		
		eventNameLabel = new Label("Event Name:");
		eventDateLabel = new Label("Event Date:");
		eventLocationLabel = new Label("Event Location:");
		eventDescriptionLabel = new Label("Event Description:");

		createEvent = new Button("Create Event");
	}
	
	public void setLayout() {
		this.setPadding(new Insets(15));
        this.setHgap(10);
        this.setVgap(10);
		
		this.add(eventNameLabel, 0, 0);
		this.add(eventName, 1, 0);
		this.add(eventDateLabel, 0, 1);
		this.add(eventDate, 1, 1);
		this.add(eventLocationLabel, 0, 2);
		this.add(eventLocation, 1, 2);
		this.add(eventDescriptionLabel, 0, 3);
		this.add(eventDescription, 1, 3);
		this.add(createEvent, 0, 4);
	}
	
	public void setButton() {
		
		createEvent.setOnAction(e->{
			String name = eventName.getText();
			String date = eventDate.getText();
			String location = eventLocation.getText();
			String description = eventDescription.getText();
			User loggedInUser = CurrentUser.getCurrentUser();
	        if (loggedInUser == null) {
	            System.out.println("User not logged in");
	            return;  // Jika user belum login
	        }

	        int organizerId = loggedInUser.getUser_id(); 
//			EventController eventController = new EventController(loggedInUser);
			boolean result = EventController.createEvent(name, date, location, description, organizerId);
			
			if(!result) {
				System.out.println("Error creating event");
			}else {
				System.out.println("Event created successfully");
				new AllEventView(organizerId ,stage);
			}
		});
	}
	
	public EventView(Stage stage) {
		this.stage = stage;
		initialize();
		setLayout();
		setButton();
		Scene scene = new Scene(this, 650, 500);
		stage.setScene(scene);
		stage.setTitle("Submit Event");
		stage.show();
	}
}
