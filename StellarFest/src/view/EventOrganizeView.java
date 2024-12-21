package view;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;

public class EventOrganizeView extends GridPane {
	Stage stage;
	
	Button eventButton, createEventButton, 
	addGuestButton, addVendorButton;
	
	
	public void initialize() {
		eventButton = new Button("Event");
		createEventButton = new Button("Create Event");
		addGuestButton = new Button("Add Guest");
		addVendorButton = new Button("Add Vendor");
	}
	
	
	public void setLayout() {
		this.setPadding(new Insets(15));
        this.setHgap(10);
        this.setVgap(10);
		
		this.add(eventButton, 0, 0);
		this.add(createEventButton, 0, 1);
		this.add(addGuestButton, 0, 2);
		this.add(addVendorButton, 0, 3);
	}
	
	public void setButton(int organizerId) {
		eventButton.setOnAction(e-> {
			new AllEventView(organizerId ,stage);
		});
		
		createEventButton.setOnAction(e-> {
			new EventView(stage);
		});
	}
	
	public EventOrganizeView(int organizerId,Stage stage) {
		this.stage = stage;
		initialize();
		setLayout();
		setButton(organizerId);
		Scene scene = new Scene(this, 500, 500);
		stage.setScene(scene);
		stage.setTitle("Event Organizer");
		stage.show();
	}
}
