package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;
import model.User;

public class UserController {
	
	public static String register(String email, String name, String password, String role) {
		String checkInput = checkRegisterInput(email, name, password);
		if (checkInput.equalsIgnoreCase(checkInput)) {
			User.register(email, name, password, role);
			return "success";
		}
		return checkInput;
    }
	
	public static User Login(String username, String password) {
		if (username.isEmpty() || password.isEmpty()) {
			return null;
		}
		return User.Login(username, password);
	}
	
	public static String checkRegisterInput(String email, String name, String password){
		boolean checkEmail = getUserByEmail(email);
		boolean checkUsername = getUserByUsername(name);
		
		if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
			return "All field must be filled!";
		} else if (!checkEmail) {
			return "Email already exist!";
		} else if (!checkUsername) {
			return "Name already exist!";
		} else if (password.length() < 5) {
			return "Password must contain 5 or more character!";
		} 
		return "success";
	}
	
	public static boolean getUserByEmail(String email) {
		return User.getUserByEmail(email);
	}
	
	public static boolean getUserByUsername(String name) {
		return User.getUserByUsername(name);
	}
	
}
