package dms.view;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DrugAddingPane extends BorderPane {
	private static Label errorMessage;
	private static Label nameLabel;
	private static TextField nameField;
	private static HBox nameBox;
	private static Label concentrationLabel;
	private static TextField concentrationField;
	private static ComboBox<String> unitComboBox;
	private static HBox concentrationBox;
	private static Label priceLabel;
	private static TextField priceField;
	private static HBox priceBox;
	private static Label inHandQuantityLabel;
	private static TextField inHandQuantityField;
	private static HBox inHandQuantityBox;
	private static Label minQuantityLabel;
	private static TextField minQuantityField;
	private static HBox minQuantityBox;
	private static Button addDrugButton;
	private static VBox motherContainer;

	public DrugAddingPane() {
		//Initialization of every attribute
		errorMessage = new Label();
		nameLabel = new Label("Nom : ");
		nameField = new TextField();
		nameBox = new HBox(DMSPage.HBOX_SPACING);
		concentrationLabel = new Label("Concentration : ");
		concentrationField = new TextField();
		concentrationBox = new HBox(DMSPage.HBOX_SPACING);
		unitComboBox = new ComboBox<>();
		priceLabel = new Label("Prix : ");
		priceField = new TextField();
		priceBox = new HBox(DMSPage.HBOX_SPACING);
		inHandQuantityLabel = new Label("Quantité en main : ");
		inHandQuantityField = new TextField();
		inHandQuantityBox = new HBox(DMSPage.HBOX_SPACING);
		minQuantityLabel = new Label("Quantité minimale : ");
		minQuantityField = new TextField();
		minQuantityBox = new HBox(DMSPage.HBOX_SPACING);
		addDrugButton = new Button("Ajouter");
		motherContainer = new VBox(DMSPage.VBOX_SPACING);

		//Setting the ComboBox
		unitComboBox.getItems().addAll("mcg","mg","mL","mg/mL","U/mL");

		//Setting the containers
		nameBox.getChildren().addAll(nameLabel, nameField);
		concentrationBox.getChildren().addAll(concentrationLabel, concentrationField, unitComboBox);
		priceBox.getChildren().addAll(priceLabel, priceField);
		inHandQuantityBox.getChildren().addAll(inHandQuantityLabel, inHandQuantityField);
		minQuantityBox.getChildren().addAll(minQuantityLabel, minQuantityField);
		motherContainer.getChildren().addAll(nameBox, concentrationBox, priceBox,
				inHandQuantityBox, minQuantityBox, addDrugButton, errorMessage);

		//Setting the BorderPane
		this.setCenter(motherContainer);

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
	}
}
