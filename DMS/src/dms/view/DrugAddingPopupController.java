package dms.view;

import java.net.URL;
import java.util.ResourceBundle;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DrugAddingPopupController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField concentrationField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField minQuantityField;

    @FXML
    private TextField inHandQuantityField;

    @FXML
    private TextField codeField;

    @FXML
    private ComboBox<String> unitComboBox;

    @FXML
    private Button addDrugButton;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	unitComboBox.getItems().addAll("mcg","mg","mL","mg/mL","U/mL");
    	
    	concentrationField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (!newValue.matches("[\\d\\.]*")) {
					concentrationField.setText(newValue.replaceAll("[^0-9\\.]", ""));
				}
			}
		});	
		
		priceField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (!newValue.matches("[\\d\\.]*")) {
					priceField.setText(newValue.replaceAll("[^0-9\\.]", ""));
				}
			}
		});
		
		inHandQuantityField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (!newValue.matches("[0-9]*")) {
					inHandQuantityField.setText(newValue.replaceAll("\\D", ""));
				}
			}
		});
		
		minQuantityField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (!newValue.matches("[0-9]*")) {
					minQuantityField.setText(newValue.replaceAll("\\D", ""));
				}
			}
		});
		
		codeField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (!newValue.matches("[0-9]*")) {
					codeField.setText(newValue.replaceAll("\\D", ""));
				}
			}
		});
	}

    @FXML
    void doAddDrug(ActionEvent event) {
    	try {
			DMSController.addDrug(nameField.getText(),
					Double.parseDouble(priceField.getText()),
					Double.parseDouble(priceField.getText()),
					unitComboBox.getSelectionModel().getSelectedItem(),
					Integer.parseInt(inHandQuantityField.getText()), 
					Integer.parseInt(minQuantityField.getText()),
					codeField.getText());
			
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
}
