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

public class Profile extends GridPane {
		Stage stage;
		TextField email, name, oldPass, newPass;
		Label nameLabel, emailLabel, passwordLabel;
		Button edit, update, backTo;
		
		private User user;
		
		public void initialized() {
			nameLabel = new Label("Name:  " + user.getUser_name());
			name = new TextField();
			name.setPromptText("Change Name");
			emailLabel = new Label("Email:  " + user.getUser_email());
			email = new TextField();
			email.setPromptText("Change Email");
			passwordLabel = new Label("Password:  *****");
			oldPass = new PasswordField();
			newPass = new PasswordField();
			edit = new Button("Edit");
			update = new Button("Save Changes");
		}
		
		public void setLayout() {
			this.setPadding(new Insets(15));
	        this.setHgap(10);
	        this.setVgap(10);
			
	        this.add(nameLabel, 0, 0);
			this.add(emailLabel, 0, 2);
			this.add(passwordLabel, 0, 4);
			this.add(edit, 1, 7);
//			this.add(goToRegister, 1, 3);
		}
		
		public void setButton() {
			update.setOnAction(e ->{
//				String mail = email.getText();
//				String password = pass.getText();
//				User user = UserController.Login(mail, password);
//				if (user == null) {
//					System.out.println("FAILED");
//				}
//				else {
//					System.out.println("WELCOME");
//				}
			});
			edit.setOnAction(e ->{
				this.add(name, 1, 1);
				this.add(email, 1, 3);
				this.add(oldPass, 1, 5);
				this.add(newPass, 1, 6);
				this.add(update, 1, 8);
				
			});
//				navigate to another page;
		}
		
		public Profile(Stage stage, User user) {
			this.stage = stage;
			this.user = user;
			initialized();
			setLayout();
			setButton();
			Scene scene = new Scene(this, 500,400);
			stage.setScene(scene);
			stage.setTitle("Login");
			stage.show();
		}
	}


