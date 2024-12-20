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
		Label titleLabel, nameLabel, emailLabel, passwordLabel;
		Button edit, update, backTo, navigate;
		
		private User user;
		
		public void initialized() {
			titleLabel = new Label("Profile");
			titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
			nameLabel = new Label("Name:  " + user.getUser_name());
			name = new TextField();
			name.setPromptText("Change Name");
			emailLabel = new Label("Email:  " + user.getUser_email());
			email = new TextField();
			email.setPromptText("Change Email");
			passwordLabel = new Label("Password:  *****");
			oldPass = new PasswordField();
			oldPass.setPromptText("Old password");
			newPass = new PasswordField();
			newPass.setPromptText("New password");
			edit = new Button("Edit");
			update = new Button("Save Changes");
			backTo = new Button("Back");
			navigate = new Button("Another page..");
		}
		
		public void setLayout() {
			this.setPadding(new Insets(15));
	        this.setHgap(10);
	        this.setVgap(10);
			
	        this.add(titleLabel, 0, 0);
	        this.add(nameLabel, 0, 1);
			this.add(emailLabel, 0, 3);
			this.add(passwordLabel, 0, 5);
			this.add(edit, 0, 8);
			this.add(navigate, 0, 9);
		}
		
		public void setButton() {
			update.setOnAction(e ->{
				String userName = name.getText();
				String mail = email.getText();
				String oldPassword = oldPass.getText();
				String newPassword = newPass.getText();
				User result = UserController.changeProfile(user.getUser_id(),userName, mail, oldPassword, newPassword);
				if (result != null) {
					System.out.println("FAILED");
				}
				else {
					System.out.println("Update successful!");
					new Profile(stage, result);
				}
			});
			edit.setOnAction(e ->{
				this.add(name, 0, 2);
				this.add(email, 0, 4);
				this.add(oldPass, 0, 6);
				this.add(newPass, 0, 7);
				this.add(update, 0, 8);
				this.getChildren().remove(navigate);
				this.getChildren().remove(edit);
				this.add(backTo, 0, 9);
				
			});
			backTo.setOnAction(e ->{
				this.getChildren().remove(name);
				this.getChildren().remove(email);
				this.getChildren().remove(oldPass);
				this.getChildren().remove(newPass);
				this.getChildren().remove(update);
				this.getChildren().remove(backTo);
				this.add(edit, 0, 8);
				this.add(navigate, 0, 9);
			});
			navigate.setOnAction(e ->{
//				navigate to another page;				
			});
		}
		
		public Profile(Stage stage, User user) {
			this.stage = stage;
			this.user = user;
			initialized();
			setLayout();
			setButton();
			Scene scene = new Scene(this, 300,400);
			stage.setScene(scene);
			stage.setTitle("User Profile");
			stage.show();
		}
	}


