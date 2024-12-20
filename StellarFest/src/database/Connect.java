package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "stellarfest";
	private final String HOST = "localhost:3306";
	private final String URL = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	private Connection con;
	private Statement st;
	
	private static Connect instance;
	public static Connect getInstance() {
		if (instance == null) return new Connect();
		return instance;
	}
	
	private Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			st = con.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Database connection failure!");
		}
	}
	
	public ResultSet selectUserData(String query) {
		try {
			return st.executeQuery(query);
		} catch (Exception e) {
			return null;
		}
	}
	
	public PreparedStatement preparedStatement(String query) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(query);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ps;
	}
	
//	public void insertData(String query) {
//		try {
//			st.executeUpdate(query);
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("insert data failed!");
//		}
//	}

}
