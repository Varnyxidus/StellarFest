package view;

import controller.EventController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.EventModel;


public class AllEventView extends VBox {
	
	private Stage stage;
    private TableView<EventModel> eventTableView;
    
// Memperlihatkan semua event yang telah dimasukkan
    public AllEventView(int organizerId, Stage stage) {
        this.stage = stage;

        eventTableView = new TableView<>();
        eventTableView.setEditable(false);

// Memperlihatkan list event(id, name, date, dan location ) yanga ada 
        TableColumn<EventModel, String> eventIdColumn = new TableColumn<>("Event Id");
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        
        TableColumn<EventModel, String> eventNameColumn = new TableColumn<>("Event Name");
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        
        TableColumn<EventModel, String> eventDateColumn = new TableColumn<>("Event Date");
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        
        TableColumn<EventModel, String> eventLocationColumn = new TableColumn<>("Event Location");
        eventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));

// Column yang memperlihatkan button untuk details Event
        TableColumn<EventModel, String> viewDetailColumn = new TableColumn<>("View Detail");
        viewDetailColumn.setCellFactory(column -> {
            Button viewButton = new Button("View Details");
            viewButton.setOnAction(event -> {
                EventModel selectedEvent = eventTableView.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    showEventDetails(selectedEvent); // Show details of selected event
                } else {
                    // Show an alert if no event is selected
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please select an event to view its details.");
                    alert.show();
                } 
            });
         
            TableCell<EventModel, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(viewButton);
                    }
                }
            };
            return cell;
        });
              
        eventTableView.getColumns().add(eventIdColumn);
        eventTableView.getColumns().add(eventNameColumn);
        eventTableView.getColumns().add(eventDateColumn);
        eventTableView.getColumns().add(eventLocationColumn);
        eventTableView.getColumns().add(viewDetailColumn);


        loadEventList(organizerId);
        this.getChildren().add(eventTableView);

        Scene scene = new Scene(this, 500, 500);
        stage.setScene(scene);
        stage.setTitle("All Events");
        stage.show();
    }  

// memperlihatkan semua data event yang ada di database
    private void loadEventList(int organizerId) {
        ObservableList<EventModel> events = EventModel.getAllEventsByOrganizer(organizerId);
        eventTableView.setItems(events);
    }

// memperlihatkan data details event
    private void showEventDetails(EventModel selectedEvent) {
        Stage detailStage = new Stage();
        GridPane grid = new GridPane();
        grid.setVgap(10); // vertical gap
        grid.setHgap(10); // horizontal gap
        
        grid.add(new Text(" Event ID: " + selectedEvent.getEventId()), 0, 0);
        grid.add(new Text(" Event Name: " + selectedEvent.getEventName()), 0, 1);
        grid.add(new Text(" Event Date: " + selectedEvent.getEventDate()), 0, 2);
        grid.add(new Text(" Event Location: " + selectedEvent.getEventLocation()), 0, 3);
        grid.add(new Text(" Event Description: " + selectedEvent.getEventDescription()), 0, 4);

        Button editButton = new Button("Edit Details");
        editButton.setOnAction(e -> {
        	detailStage.close();
        	AllEventView allEventView = this;
        	new EditEventDetailsView(stage, selectedEvent.getOrganizerId(), selectedEvent, allEventView);
        });
        grid.add(editButton, 0, 5);
        
        Button addGuestButton = new Button("Add Guest");
        addGuestButton.setOnAction(e -> {
        	detailStage.close();
        	new AddGuestView(stage, selectedEvent.getEventId());
        });
        grid.add(addGuestButton, 1, 5);
        
        Button addVendorButton = new Button("Add Vendor");
        addVendorButton.setOnAction(e -> {
        	detailStage.close();
        	new AddVendorView(stage, selectedEvent.getEventId());
        });
        grid.add(addVendorButton, 2, 5);
        
        Scene detailScene = new Scene(grid, 400, 300);
        detailStage.setScene(detailScene);
        detailStage.setTitle("Event Details");
        detailStage.show();
    }
    
// memperlihatkan data event yang telah terupdate
    public void reloadEventList(int organizerId) {
    	loadEventList(organizerId);
    }
}
