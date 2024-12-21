package model;

//import database.Database;
import java.sql.*;


import database.Connect;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {
	private int user_id;
	private String user_email;
	private String user_name;
	private String user_password;
	private String user_role;
	
	 public User(int user_id, String user_email, String user_name, String user_password, String user_role) {
		super();
		this.user_id = user_id;
		this.user_email = user_email;
		this.user_name = user_name;
		this.user_password = user_password;
		this.user_role = user_role;
	}

	public static boolean register(String email, String username, String password, String role) {
        Connect db = Connect.getInstance();
        String query = "INSERT INTO user VALUES(0,?,?,?,?)";
		PreparedStatement ps = db.preparedStatement(query);
		try {
			ps.setString(1, email);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.setString(4, role);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}
	
	public static User Login(String email, String password) {
		Connect db = Connect.getInstance();
		String query = "SELECT * FROM user WHERE user_email = ? AND user_password = ?";
		
		ResultSet rs = null;
		PreparedStatement ps = db.preparedStatement(query);
		try {
			ps.setString(1, email);
			ps.setString(2, password);
//			ps.executeQuery();
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				int id = rs.getInt("user_id");
				String userName = rs.getString("user_name");
				String userRole = rs.getString("user_role");
				String mail = rs.getString("user_email");
				String pass = rs.getString("user_password");
				User founduser = new User(id, mail, userName, pass, userRole);
				return founduser;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean getUserByEmail(String email) {
		Connect db = Connect.getInstance();
		String query = "SELECT * FROM user WHERE user_email = ?";
		
		PreparedStatement ps = db.preparedStatement(query);
		ResultSet rs = null;
		try {
			ps.setString(1, email);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return false;
	        }
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		return true;
	}
	
	public static boolean getUserByUsername(String name) {
		Connect db = Connect.getInstance();
		String query = "SELECT * FROM user WHERE user_name = ?";
		
		PreparedStatement ps = db.preparedStatement(query);
		ResultSet rs = null;
		try {
			ps.setString(1, name);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			// Close resources
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public User changeProfile(String email, String name, String oldPassword, String newPassword) {
		Connect db = Connect.getInstance();
		String query = "UPDATE user SET user_name = ?, user_email = ?, user_password = ?";
		
		ResultSet rs = null;
		PreparedStatement ps = db.preparedStatement(query);
		try {
			ps.setString(1, email);
			ps.setString(2, password);
//			ps.executeQuery();
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				int id = rs.getInt("user_id");
				String userName = rs.getString("user_name");
				String userRole = rs.getString("user_role");
				String mail = rs.getString("user_email");
				String pass = rs.getString("user_password");
				User founduser = new User(id, mail, userName, pass, userRole);
				return founduser;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
	  
}
