package singleton;

import model.User;

public class CurrentUser {
	 private static User currentUser;

	    // Menyimpan user yang sedang login
	    public static void setCurrentUser(User user) {
	        currentUser = user;
	    }

	    // Mendapatkan user yang sedang login
	    public static User getCurrentUser() {
	        return currentUser;
	    }
}
