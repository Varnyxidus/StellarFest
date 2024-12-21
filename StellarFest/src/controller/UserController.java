package controller;

import model.User;

public class UserController {
	
	public static String register(String email, String name, String password, String role) {
		// Pemanggilan method validasi. return "success" jika benar, return "error messages" jika salah.
		String checkInput = checkRegisterInput(email, name, password);
		if (checkInput.equals("success")) {
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
		String checkEmail = getUserByEmail(email);
		String checkUsername = getUserByUsername(name);
		
		// Validasi input saat registrasi
		// Input kosong
		if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
			return "All field must be filled!";
		}
		// Email sudah diambil
		else if (checkEmail != null) {
			return "Email already exist.";
		}
		// Nama sudah diambil
		else if (checkUsername != null) {
			return "Name already exist.";
		}
		// Panjang password minimal 5 huruf
		else if (password.length() < 5) {
			return "Password must contain 5 or more character.";
		}
		// Validasi sukses
		return "success";
	}
	
	public static String getUserByEmail(String email) {
		return User.getUserByEmail(email);
	}
	
	public static String getUserByUsername(String name) {
		return User.getUserByUsername(name);
	}
	
	public static User changeProfile(int id, String email, String name, String oldPassword, String newPassword) {
		// Pemanggilan method validasi. return "success" jika benar, return "error messages" jika salah.
		String checkProfileInput = checkChangeProfileInput(email, name, oldPassword, newPassword);
		if (checkProfileInput.equals("success")) {
			return User.changeProfile(id, email, name, oldPassword, newPassword);
		}
		return null;
	}
	
	public static String checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
		String checkEmail = getUserByEmail(email);
		String checkName = getUserByUsername(name);
		
		// Penampung data lama: nama, email, password
		String currentEmail = User.getUser_email();
		String currentName = User.getUser_name();
		String currentPass = User.getUser_password();
		
		// Validasi input email baru
		if (email.equalsIgnoreCase(currentEmail)) {
			return "Your new email can not match the old email.";
		}
		else if (checkEmail != null) {
			return "Email has taken.";
		}
		
		// Validasi input nama baru
		if (name.equalsIgnoreCase(currentName)) {
			return "Your new name can not match the old name.";
		}
		else if (checkName != null) {
			return "Name has taken.";
		}
		
		// Validasi input password baru
		if (!oldPassword.equals(currentPass)) {
			return "Invalid current password.";
		}
		else if (newPassword.length() < 5) {
			return "Password must contain 5 or more character.";
		}
		
		// Validasi sukses
		return "success";
	}
	
}
