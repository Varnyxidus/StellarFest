package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;
import singleton.CurrentUser;

public class Login extends GridPane{
	Stage stage;
	TextField email, pass;
	Label emailLabel, passwordLabel;
	Button login, goToRegister;
	
	public void initialized() {
		emailLabel = new Label("Email:");
		email = new TextField();
		email.setPromptText("Insert email");
		passwordLabel = new Label("Password:");
		pass = new PasswordField();
		pass.setPromptText("Insert password");
		login = new Button("Sign In");
		goToRegister = new Button("No account yet?");
	}
	
	public void setLayout() {
		this.setPadding(new Insets(15));
        this.setHgap(10);
        this.setVgap(10);
		
        this.add(emailLabel, 0, 0);
		this.add(email, 1, 0);
		this.add(passwordLabel, 0, 1);
		this.add(pass, 1, 1);
		this.add(login, 1, 2);
		this.add(goToRegister, 1, 3);
	}
	
	public void setButton() {
		login.setOnAction(e ->{
			String mail = email.getText();
			String password = pass.getText();
			User user = UserController.Login(mail, password);
			if (user == null) {
				System.out.println("FAILED");
			}
			else {
				System.out.println("Login successful!");
				CurrentUser.setCurrentUser(user);
				if("Event Organizer".equals(user.getUser_role())) {
					int organizerId = user.getUser_id(); 
					new EventOrganizeView(organizerId, stage);
				} else {
					new Profile(stage, user);
				}
			}
		});
		goToRegister.setOnAction(e ->{
			new Registration(stage);
			
		});
	}
	
	public Login(Stage stage) {
		this.stage = stage;
		initialized();
		setLayout();
		setButton();
		Scene scene = new Scene(this, 300,300);
		stage.setScene(scene);
		stage.setTitle("Login");
		stage.show();
	}
}
