package dms.view;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
		motherContainer.getChildren().addAll(usernameBox, passwordBox, loginButton, errorMessage);
		motherContainer.setAlignment(Pos.CENTER);

		//Setting the BorderPane
		this.setCenter(motherContainer);

		//Setting the TextFields
		passwordLabel.setMinWidth(usernameLabel.getWidth());
		Platform.runLater(()->
		{
			//the width of usernameLabel is known only once it is shown
			passwordLabel.setMinWidth(usernameLabel.getWidth());
		});

		//Setting the Listeners
		setListeners();

		//Setting the Style
		this.getStylesheets().add(DMSPage.getResource("dms/resources/stylesheet.css"));
		loginButton.setId("greenButton");
		errorMessage.setId("error");

	}

	private static void setListeners() {
		loginButton.setOnAction(e -> {
			try {
				DMSController.login(usernameField.getText(), passwordField.getText());
				DMSPage.toMainScene();
			}
			catch(InvalidInputException iie) {
				errorMessage.setText(iie.getMessage());
			}
		});

		passwordField.setOnKeyPressed(e -> {
			if(e.getCode().equals(KeyCode.ENTER)) {
				loginButton.fire();
			}
		});
	}
}
