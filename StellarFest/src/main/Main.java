package main;

import controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.Registration;

public class Main extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		UserController userController = new UserController();
		new Registration(primaryStage);
	}

}
