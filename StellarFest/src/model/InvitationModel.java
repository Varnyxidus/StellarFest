package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InvitationModel {
	private String invitationId;
	private String eventId;
	private String userId;
	private String invitationStatus;
	private String invitationRole;
	
	// Add Invitations
	  // Method to check if the invitation already exists
    public static boolean isInvitationAlreadySent(String eventId, int userId) {
        Connect db = Connect.getInstance();
        String query = "SELECT COUNT(*) FROM invitations WHERE event_id = ? AND user_id = ?";
        PreparedStatement ps = db.preparedStatement(query);

        try {
            ps.setString(1, eventId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true;  // Invitation already exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while checking existing invitation.");
        }
        return false;  // Invitation does not exist
    }
  
	public InvitationModel(String invitationId, String eventId, String userId, String invitationStatus,
			String invitationRole) {
		super();
		this.invitationId = invitationId;
		this.eventId = eventId;
		this.userId = userId;
		this.invitationStatus = invitationStatus;
		this.invitationRole = invitationRole;
	}

	public String getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(String invitationId) {
		this.invitationId = invitationId;
	}

	public String getInvitationStatus() {
		return invitationStatus;
	}

	public void setInvitationStatus(String invitationStatus) {
		this.invitationStatus = invitationStatus;
	}

	public String getInvitationRole() {
		return invitationRole;
	}

	public void setInvitationRole(String invitationRole) {
		this.invitationRole = invitationRole;
	}

	public String getEventId() {
		return eventId;
	}

	public String getUserId() {
		return userId;
	}
	
	
}
