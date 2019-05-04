package dms.view;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DrugAddingPane extends GridPane {
	private static Label errorMessage;
	private static Label nameLabel;
	private static TextField nameField;
	private static Label concentrationLabel;
	private static TextField concentrationField;
	private static ComboBox<String> unitComboBox;
	private static Label priceLabel;
	private static TextField priceField;
	private static Label inHandQuantityLabel;
	private static TextField inHandQuantityField;
	private static Label minQuantityLabel;
	private static TextField minQuantityField;
	private static Button addDrugButton;

	public DrugAddingPane() {
		//Initialization of every attribute
		errorMessage = new Label();
		nameLabel = new Label("Nom :");
		nameField = new TextField();
		concentrationLabel = new Label("Concentration :");
		concentrationField = new TextField();
		unitComboBox = new ComboBox<>();
		priceLabel = new Label("Prix :");
		priceField = new TextField();
		inHandQuantityLabel = new Label("Quantité en main :");
		inHandQuantityField = new TextField();
		minQuantityLabel = new Label("Quantité minimale :");
		minQuantityField = new TextField();
		addDrugButton = new Button("Ajouter");

		//Setting the ComboBox
		unitComboBox.getItems().addAll("mcg","mg","mL","mg/mL","U/mL");

		///Setting the GridPane
		this.addRow(0, nameLabel, nameField);
		this.addRow(1, concentrationLabel, concentrationField, unitComboBox);
		this.addRow(2, priceLabel, priceField);
		this.addRow(3, inHandQuantityLabel, inHandQuantityField);
		this.addRow(4, minQuantityLabel, minQuantityField);
		this.add(addDrugButton, 0, 5, 3, 1);
		this.setVgap(DMSPage.VBOX_SPACING);
		this.setHgap(DMSPage.HBOX_SPACING);
		this.setAlignment(Pos.CENTER);

		//Setting the Button
		Platform.runLater(() -> {
			addDrugButton.setPrefWidth(this.getWidth());
		});

		//Setting the Style
		this.getStylesheets().add(DMSPage.getResource("dms/resources/stylesheet.css"));
		nameLabel.setId("greenLabel");
		concentrationLabel.setId("greenLabel");
		priceLabel.setId("greenLabel");
		inHandQuantityLabel.setId("greenLabel");
		minQuantityLabel.setId("greenLabel");
		addDrugButton.setId("greenButton");

		//Setting the Listeners
		setListeners();
	}

	private static void setListeners() {
		addDrugButton.setOnAction(e -> {
			try {
				DMSController.addDrug(nameField.getText(),
						Double.parseDouble(priceField.getText()),
						Double.parseDouble(priceField.getText()),
						unitComboBox.getSelectionModel().getSelectedItem(),
						Integer.parseInt(inHandQuantityField.getText()), 
						Integer.parseInt(minQuantityField.getText()));
				InventoryPane.refreshInventoryTable();
				InventoryPane.closeAddDrugStage();
			}
			catch(InvalidInputException iie) {
				errorMessage.setText(iie.getMessage());
			}
		});

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
				if (!newValue.matches("[\\d]*")) {
					inHandQuantityField.setText(newValue.replaceAll("\\D", ""));
				}
			}
		});
		
		minQuantityField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				if (!newValue.matches("[\\d]*")) {
					minQuantityField.setText(newValue.replaceAll("\\D", ""));
				}
			}
		});
	}
}
