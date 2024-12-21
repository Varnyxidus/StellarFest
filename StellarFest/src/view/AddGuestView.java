package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.InvitationModel;
import model.User;

public class AddGuestView extends VBox{
	private Stage stage;
	private TableView<User> guestTableView;
	String eventId;

// semua tentang add guest	
	public AddGuestView(Stage stage, String eventId) {
		this.stage = stage;
		this.eventId = eventId;
		
		guestTableView = new TableView<>();
		guestTableView.setEditable(false);
		
		TableColumn<User, Integer> guestIdColumn = new TableColumn<>("Guest Id");
		guestIdColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
		
		TableColumn<User, Integer> guestNameColumn = new TableColumn<>("Guest Id");
		guestNameColumn.setCellValueFactory(new PropertyValueFactory<>("user_name"));
		
		TableColumn<User, Integer> guestEmailColumn = new TableColumn<>("Guest Id");
		guestEmailColumn.setCellValueFactory(new PropertyValueFactory<>("user_email"));

		TableColumn<User, String> sendInviteColumn = new TableColumn<>("Send Invite");
	    sendInviteColumn.setCellFactory(param -> new TableCell<User, String>() {
	        private final Button sendButton = new Button("Send Invite");

	        {
	            sendButton.setOnAction(e -> {
	                User vendor = getTableView().getItems().get(getIndex());
	                sendInvitation(vendor);
	            });
	        }

	        @Override
	        protected void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty) {
	                setGraphic(null);
	            } else {
	                setGraphic(sendButton);
	            }
	        }
	    });

		
		guestTableView.getColumns().add(guestIdColumn);
		guestTableView.getColumns().add(guestNameColumn);
		guestTableView.getColumns().add(guestEmailColumn);
		guestTableView.getColumns().add(sendInviteColumn);
		
		loadGuestList();
		
		this.getChildren().addAll(guestTableView);
		
		Scene scene = new Scene(this, 600, 400);
		stage.setScene(scene);
		stage.setTitle("Add Guest to Event");
		stage.show();
	}
	
	public void sendInvitation(User guest) {
	    Connect db = Connect.getInstance();
	    String query = "INSERT INTO invitations (event_id, user_id, invitation_status, invitation_role) VALUES (?, ?, ?, ?)";
	    PreparedStatement ps = db.preparedStatement(query);
	    
	    if (InvitationModel.isInvitationAlreadySent(eventId, guest.getUser_id())) {
	    	showError("Invitation already sent to Vendor ID: " + guest.getUser_id());
	        return;  // Return early if the invitation has already been sent
	    }

	    try {
	        ps.setString(1, eventId);  // Gunakan eventId yang diteruskan
	        ps.setInt(2, guest.getUser_id());  // Mengambil user_id dari objek vendor
	        ps.setString(3, "Pending");  // Status undangan
	        ps.setString(4, "Guest");  // Peran undangan untuk guest
	        ps.executeUpdate();
	        System.out.println("Invitation sent to Guest ID: " + guest.getUser_id());
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Error while sending invitation.");
	    }
	}
	
	private void loadGuestList() {
		ObservableList<User> guestList = FXCollections.observableArrayList();
		
		// Mengambil data user dengan role guest
		guestList.addAll(getGuest());
		
		// Mengambil daftar guest ke table view
		guestTableView.setItems(guestList);
	}
	
	public static ObservableList<User> getGuest(){
		ObservableList<User> guest = FXCollections.observableArrayList();
		Connect db = Connect.getInstance();
		String query = "SELECT user_id, user_name, user_email, user_password FROM user WHERE user_role = 'Guest'";
		ResultSet rs = null;
		PreparedStatement ps = db.preparedStatement(query);
		
		try {
			rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("user_id");
				String userName = rs.getString("user_name");
				String email = rs.getString("user_email");
				String pass = rs.getString("user_password");
				guest.add(new User(id, userName, "Guest", email, pass));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return guest;
	}
	
	// Method to display error messages
	private static void showError(String message) {
	    // Create an error alert
	    Alert alert = new Alert(Alert.AlertType.ERROR); 
	    alert.setTitle("Error");  // Set the title of the alert
	    alert.setHeaderText(null);  // No header text
	    alert.setContentText(message);  // Set the message content

	    // Show the alert dialog
	    alert.showAndWait();  // Wait for the user to close the alert
	}
}
