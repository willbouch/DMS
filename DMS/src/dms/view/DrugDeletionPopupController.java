package dms.view;

import java.net.URL;
import java.util.ResourceBundle;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DrugDeletionPopupController implements Initializable {

	@FXML
	private Label messageLabel;

	@FXML
	private Button yesButton;

	@FXML
	private Button noButton;

	@FXML
	void doNo(ActionEvent event) {
		((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
	}

	@FXML
	void doYes(ActionEvent event) {
		Stage stage = ((Stage) ((Node)event.getSource()).getScene().getWindow());
		stage.close();
		try {
			DMSController.deleteDrug(MainScreenController.getTODrug().getName(), MainScreenController.getTODrug().getCode());	
		}
		catch(InvalidInputException iie) {
			//We do nothing
		}
		stage.fireEvent(new WindowEvent(
				stage,
				WindowEvent.WINDOW_CLOSE_REQUEST
				));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		messageLabel.setText("Voulez-vous bien supprimer ("+MainScreenController.getTODrug().getName()+")\nde la base de données ?");
		messageLabel.setAlignment(Pos.CENTER);
	}

}
