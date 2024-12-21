package view;

import javafx.scene.Scene; 
import javafx.scene.control.Button; 
import javafx.scene.control.TextField; 
import javafx.scene.layout.GridPane; 
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.geometry.Insets; 
import model.EventModel; 

// Buat View Edit
public class EditEventDetailsView extends GridPane {
	Stage stage;
	TextField eventNameNew, eventDateNew, 
	eventLocationNew, eventDescriptionNew;
	Button saveButton, cancelButton;
	int organizerId;
	AllEventView allEventView;
	
	public void initialize() {
		eventNameNew = new TextField();
		eventNameNew.setPromptText("Enter New Event Name");
		
		eventDateNew = new TextField();
		eventDateNew.setPromptText("Enter New Date");
		
		eventLocationNew = new TextField();
		eventLocationNew.setPromptText("Enter New Location");
		
		eventDescriptionNew = new TextField();
		eventDescriptionNew.setPromptText("Enter New Description");
		
		saveButton = new Button("Save Change");
		cancelButton = new Button("Cancel Change");
	}
	
	public void setLayout() {
		this.setPadding(new Insets(15));
		this.setHgap(10);
		this.setVgap(10);
		
		this.add(eventNameNew, 0, 0);
		this.add(eventDateNew, 0, 1);
		this.add(eventLocationNew, 0, 2);
		this.add(eventDescriptionNew, 0, 3);
		this.add(saveButton, 0, 4);
		this.add(cancelButton, 1, 4);
	}
	
	public void setButton(EventModel event) {
		saveButton.setOnAction(e -> {
			event.setEventName(eventNameNew.getText());
			event.setEventDate(eventDateNew.getText());
			event.setEventLocation(eventLocationNew.getText());
			event.setEventDescription(eventDescriptionNew.getText());
			
			boolean updatedSuccess = EventModel.updateEvent(event);
			if(updatedSuccess) {
				System.out.println("Event details updated successfully");
				allEventView.reloadEventList(organizerId);
			} else {
				System.out.println("Failed to update event details");
			}
			new EventOrganizeView(organizerId, stage);
		});
		
		cancelButton.setOnAction(e -> {
			new EventOrganizeView(organizerId, stage);
		});
	}
	
	public EditEventDetailsView(Stage stage, int organizerId, EventModel event, AllEventView allEventView) { 
		this.stage = stage; 
		this.organizerId = organizerId;
		this.allEventView = allEventView;
		initialize(); 
		setLayout(); 
		setButton(event); 
		Scene scene = new Scene(this, 500, 500); stage.setScene(scene); stage.setTitle("Edit Event Details"); 
		stage.show(); 

		// ini isi field dengan detail event yang ada 
		eventNameNew.setText(event.getEventName()); 
		eventDateNew.setText(event.getEventDate()); 
		eventLocationNew.setText(event.getEventLocation()); 
		eventDescriptionNew.setText(event.getEventDescription()); 
	} 
	
}
