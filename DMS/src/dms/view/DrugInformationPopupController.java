package dms.view;

import java.net.URL;
import java.util.ResourceBundle;

import dms.controller.TODrug;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DrugInformationPopupController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Label concentrationLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label inHandQuantityLabel;

    @FXML
    private Label minQuantityLabel;

    @FXML
    private Label orderedQuantityLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TODrug toDrug = MainScreenController.getTODrug();
		nameLabel.setText(toDrug.getName());
		concentrationLabel.setText(toDrug.getConcentration()+" "+toDrug.getUnit());
		priceLabel.setText(toDrug.getPrice()+" $");
		inHandQuantityLabel.setText(""+toDrug.getInHandQuantity());
		minQuantityLabel.setText(""+toDrug.getMinQuantity());
		orderedQuantityLabel.setText(""+toDrug.getOrderedQuantity());
	}

}
