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

public class AddVendorView extends VBox{
	private Stage stage;
	private TableView<User> vendorTableView;
	String eventId;
	
	public AddVendorView(Stage stage, String eventId) {
		this.stage = stage;
		this.eventId = eventId;
	
		vendorTableView = new TableView<>();
		vendorTableView.setEditable(false);
		
// Kolom Vendor ID
		TableColumn<User, Integer> vendorIdColumn = new TableColumn<>("Vendor Id");
		vendorIdColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
	
// Kolom Vendor Name
		TableColumn<User, String> vendorNameColumn = new TableColumn<>("Vendor Name");
		vendorNameColumn.setCellValueFactory(new PropertyValueFactory<>("user_name"));
		
// Kolo Vendor Email
		TableColumn<User, String> vendorEmailColumn = new TableColumn<>("Vendor Email");
		vendorEmailColumn.setCellValueFactory(new PropertyValueFactory<>("user_email"));
		
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
	    
		vendorTableView.getColumns().add(vendorIdColumn);
		vendorTableView.getColumns().add(vendorNameColumn);
		vendorTableView.getColumns().add(vendorEmailColumn);
		vendorTableView.getColumns().add(sendInviteColumn);
		
		loadVendorList();
		
		this.getChildren().add(vendorTableView);
		
		Scene scene = new Scene(this, 600, 400);
		stage.setScene(scene);
		stage.setTitle("All Vendors");
		stage.show();
	}
	
	public void sendInvitation(User vendor) {
	    Connect db = Connect.getInstance();
	    
	    if (InvitationModel.isInvitationAlreadySent(eventId, vendor.getUser_id())) {
	    	showError("Invitation already sent to Vendor ID: " + vendor.getUser_id());
	        return;  // Return early if the invitation has already been sent
	    }
	    
	    String query = "INSERT INTO invitations (event_id, user_id, invitation_status, invitation_role) VALUES (?, ?, ?, ?)";
	    PreparedStatement ps = db.preparedStatement(query);

	    try {
	        ps.setString(1, eventId);  // Gunakan eventId yang diteruskan
	        ps.setInt(2, vendor.getUser_id());  // Mengambil user_id dari objek vendor
	        ps.setString(3, "Pending");  // Status undangan
	        ps.setString(4, "Vendor");  // Peran undangan (Vendor)
	        ps.executeUpdate();
	        System.out.println("Invitation sent to Vendor ID: " + vendor.getUser_id());
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Error while sending invitation.");
	    }
	}
	
	private void loadVendorList() {
		ObservableList<User> vendorList = FXCollections.observableArrayList();
		
		// Mengambil data user dengan role vendor
		vendorList.addAll(getVendors());
		
		// Menetapkan daftar vendor ke table view
		vendorTableView.setItems(vendorList);
	}
	
	public static ObservableList<User> getVendors() {
	    ObservableList<User> vendors = FXCollections.observableArrayList();
	    Connect db = Connect.getInstance();
	    String query = "SELECT user_id, user_name, user_email, user_password FROM user WHERE user_role = 'Vendor'"; 
	    ResultSet rs = null;
	    PreparedStatement ps = db.preparedStatement(query);

	    try {
	        rs = ps.executeQuery();
	        while (rs.next()) {
	            int userId = rs.getInt("user_id");
	            String userName = rs.getString("user_name");
	            String userEmail = rs.getString("user_email");
	            String userPass = rs.getString("user_password");
	            vendors.add(new User(userId, userName, userEmail, "Vendor", userPass));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return vendors;
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
