package dms.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginPane extends BorderPane {
	private static Label errorMessage;
	private static Button loginButton;
	private static TextField usernameField;
	private static PasswordField passwordField;
	private static Label usernameLabel;
	private static Label passwordLabel;
	private static HBox usernameBox;
	private static HBox passwordBox;
	private static VBox motherContainer;
	
	public LoginPane() {
		//Initialization of every attribute
		errorMessage = new Label(); 
		loginButton = new Button("Se connecter");
		usernameField = new TextField();
		passwordField = new PasswordField();
		usernameLabel = new Label("Nom d'utilisateur");
		passwordLabel = new Label("Mot de passe");
		usernameBox = new HBox(DMSPage.HBOX_SPACING);
		passwordBox = new HBox(DMSPage.HBOX_SPACING);
		motherContainer = new VBox(DMSPage.VBOX_SPACING);
		
		//Setting the containers
		usernameBox.getChildren().addAll(usernameLabel, usernameField);
		usernameBox.setAlignment(Pos.CENTER);
		passwordBox.getChildren().addAll(passwordLabel, passwordField);
		passwordBox.setAlignment(Pos.CENTER);
		motherContainer.getChildren().addAll(usernameBox, passwordBox, loginButton);
		motherContainer.setAlignment(Pos.CENTER);
				
		//Setting the borderpane
		this.setCenter(motherContainer);	
	}
}
