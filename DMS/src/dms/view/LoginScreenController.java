package dms.view;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginScreenController {

	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button connectButton;

	@FXML
	void doConnect(ActionEvent event) {
		try {
			DMSController.login(usernameField.getText(), passwordField.getText());
			DMSPage.toMainScene();
		}
		catch(Exception e) {
			//TODO
		} 
	}

	@FXML
	void doConnectWithEnter(KeyEvent event) {
		if(event.getCode().equals(KeyCode.ENTER)) {
			connectButton.fire();
		}
	}
}