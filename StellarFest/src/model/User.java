package model;

//import database.Database;
import java.sql.*;
import java.sql.PreparedStatement;
import database.Connect;

public class User {
	private static int user_id;
	private static String user_email;
	private static String user_name;
	private static String user_password;
	private static String user_role;
	
	
	 public User(int user_id, String user_email, String user_name, String user_password, String user_role) {
		super();
		User.user_id = user_id;
		User.user_email = user_email;
		User.user_name = user_name;
		User.user_password = user_password;
		User.user_role = user_role;
	}
	
	// Proses registrasi
	public static boolean register(String email, String username, String password, String role) {
        Connect db = Connect.getInstance();
        // Menambah data baru ke table
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
	
	// Proses Login
	public static User Login(String email, String password) {
		Connect db = Connect.getInstance();
		// Memilih data yang sesuai dengan nama dan password
		String query = "SELECT * FROM user WHERE user_email = ? AND user_password = ?";
		ResultSet rs = null;
		PreparedStatement ps = db.preparedStatement(query);
		try {
			ps.setString(1, email);
			ps.setString(2, password);
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
	
	// Mengambil data dari user menggunakan email
	public static String getUserByEmail(String email) {
		Connect db = Connect.getInstance();
		String query = "SELECT * FROM user WHERE user_email = ?";
		
		PreparedStatement ps = db.preparedStatement(query);
		ResultSet rs = null;
		try {
			ps.setString(1, email);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
		try {
			while (rs.next()) {
				String foundUserMail = rs.getString("user_email");
				return foundUserMail;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	// Mengambil data dari user menggunakan nama
	public static String getUserByUsername(String name) {
		Connect db = Connect.getInstance();
		String query = "SELECT * FROM user WHERE user_name = ?";
		
		PreparedStatement ps = db.preparedStatement(query);
		ResultSet rs = null;
		try {
			ps.setString(1, name);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				String foundUserName = rs.getString("user_email");
				return foundUserName;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public static User changeProfile(int id, String name, String email, String oldPassword, String newPassword) {
		Connect db = Connect.getInstance();
		
		String currentEmail = getUser_email();
	    String currentName = getUser_name();

	    // Jika input nama dan email dikosongkan maka data sebelumnya akan dipertahankan
	    if (name.isEmpty()) {
	        name = currentName;
	    }
	    if (email.isEmpty()) {
	        email = currentEmail;
	    }
		
	    // Proses melakukan update data user pada table
		String updateQuery = "UPDATE user SET user_name = ?, user_email = ?, user_password = ? WHERE user_id = ?";
		PreparedStatement ps1 = db.preparedStatement(updateQuery);
		try {
			ps1.setString(1, name);
			ps1.setString(2, email);
			ps1.setString(3, newPassword);
			ps1.setInt(4, id);
			ps1.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		// Proses melakukan pengambilan data user pada table untuk ditunjukkan
		String showQuery = "SELECT * FROM user WHERE user_id = ?";
		PreparedStatement ps2 = db.preparedStatement(showQuery);
		ResultSet rs = null;
		try {
			ps2.setInt(1, id);
			rs = ps2.executeQuery();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				int ID = rs.getInt("user_id");
				String userName = rs.getString("user_name");
				String userRole = rs.getString("user_role");
				String mail = rs.getString("user_email");
				String pass = rs.getString("user_password");
				User updatedUser = new User(ID, mail, userName, pass, userRole);
				return updatedUser;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// Jika proses gagal maka return null
		return null;
	}

	public static int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		User.user_id = user_id;
	}

	public static String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		User.user_email = user_email;
	}

	public static String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		User.user_name = user_name;
	}

	public static String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		User.user_password = user_password;
	}

	public static String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		User.user_role = user_role;
	}
	 
	 
}
