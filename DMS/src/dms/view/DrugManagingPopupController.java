package dms.view;

import java.net.URL;
import java.util.ResourceBundle;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import dms.controller.TODrug;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DrugManagingPopupController implements Initializable {

	@FXML
	private Label nameLabel;

	@FXML
	private Label concentrationLabel;

	@FXML
	private TextField priceField;

	@FXML
	private TextField inHandQuantityField;

	@FXML
	private TextField minQuantityField;

	@FXML
	private TextField orderedQuantityField;

	@FXML
	private Button updateButton;

	@FXML
	void doUpdateDrug(ActionEvent event) {
		TODrug toDrug = MainScreenController.getTODrug();
		try {
			DMSController.updateDrug(toDrug.getName(), toDrug.getCode(), Integer.parseInt(inHandQuantityField.getText()),
					Integer.parseInt(minQuantityField.getText()), Double.parseDouble(priceField.getText()));
		}
		catch(InvalidInputException iie) {
		}
		Stage stage = ((Stage) ((Node)event.getSource()).getScene().getWindow());
		stage.close();
		stage.fireEvent(new WindowEvent(
				stage,
				WindowEvent.WINDOW_CLOSE_REQUEST
				));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TODrug toDrug = MainScreenController.getTODrug();
		nameLabel.setText(toDrug.getName());
		concentrationLabel.setText(toDrug.getConcentration()+" "+toDrug.getUnit());
		priceField.setText(""+toDrug.getPrice());
		inHandQuantityField.setText(""+toDrug.getInHandQuantity());
		minQuantityField.setText(""+toDrug.getMinQuantity());
		orderedQuantityField.setText(""+toDrug.getOrderedQuantity());
	}

}
