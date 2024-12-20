package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Registration extends GridPane{
	Stage stage;
	TextField emailField, usernameField, passwordField;
	Button registerButton, loginButton;
	ComboBox<String> roleComboBox;
	Label emailLabel, usernameLabel, passwordLabel, roleLabel, statusLabel;
	
	public void initialized() {
		emailField = new TextField();
		emailField.setPromptText("Insert email");
		usernameField = new TextField();
		usernameField.setPromptText("Insert name");
		passwordField = new PasswordField();
		passwordField.setPromptText("Insert password");
		roleComboBox = new ComboBox<>();		
		registerButton = new Button("Sign Up");
		loginButton = new Button("Already has account?");
		emailLabel = new Label("Email:");
		usernameLabel = new Label("Username:");
		passwordLabel = new Label("Password:");
		roleLabel = new Label("Role:");
		statusLabel = new Label();
		roleComboBox.getItems().addAll("Event Organizer", "Vendor", "Guest");
		roleComboBox.setPromptText("Select a Role");
	}
	
	public void setLayout() {
		this.setPadding(new Insets(15));
        this.setHgap(10);
        this.setVgap(10);
		
		this.add(emailLabel, 0, 0);
        this.add(emailField, 1, 0);
        this.add(usernameLabel, 0, 1);
        this.add(usernameField, 1, 1);
        this.add(passwordLabel, 0, 2);
        this.add(passwordField, 1, 2);
        this.add(roleLabel, 0, 3);
        this.add(roleComboBox, 1, 3);
        this.add(statusLabel, 1, 4);
        this.add(registerButton, 1, 5);
        this.add(loginButton, 1, 6);
	}
	
	
	public void setButton() {
		registerButton.setOnAction(e -> {
        	String email = emailField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleComboBox.getValue();
            
            String result = UserController.register(email, username, password,role);
            
            if (result.equalsIgnoreCase("success")){
//                statusLabel.setText("Registration successful!");
            	System.out.println("Registration successful!");
                new Login(stage);
            } else {
            	statusLabel.setStyle("-fx-text-fill: red;");
            	statusLabel.setText(result);
            }
        });
        
        loginButton.setOnAction(e -> {
        	new Login(stage);
        });
	}
	
	public Registration(Stage stage) {
		this.stage = stage;
		initialized();
		setLayout();
		setButton();
		Scene scene = new Scene(this, 320,300);
		stage.setScene(scene);
		stage.setTitle("Register");
		stage.show();
	}
	
}
