package dms.view;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import dms.controller.TODrug;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DrugManagementPane extends BorderPane {
	private static Label errorMessage;
	private static Label nameLabel;
	private static Label concentrationLabel;
	private static Label priceLabel;
	private static TextField priceField;
	private static HBox priceBox;
	private static Label inHandQuantityLabel;
	private static TextField inHandQuantityField;
	private static HBox inHandQuantityBox;
	private static Label minQuantityLabel;
	private static TextField minQuantityField;
	private static HBox minQuantityBox;
	private static Label orderedQuantityLabel;
	private static TextField orderedQuantityField;
	private static HBox orderedQuantityBox;
	private static Button modifyButton;
	private static VBox motherContainer;
	
	public DrugManagementPane(TODrug toDrug) {
		//Initialization of every attribute
		errorMessage = new Label();
		nameLabel = new Label("Nom : "+toDrug.getName());
		concentrationLabel = new Label("Concentration : "+toDrug.getConcentration()+" "+toDrug.getUnit());
		priceLabel = new Label("Prix : ");
		priceField = new TextField(toDrug.getPrice()+"");
		priceBox = new HBox(DMSPage.HBOX_SPACING);
		inHandQuantityLabel = new Label("Quantité en main : ");
		inHandQuantityField = new TextField(toDrug.getInHandQuantity()+"");
		inHandQuantityBox = new HBox(DMSPage.HBOX_SPACING);
		minQuantityLabel = new Label("Quantité minimale : ");
		minQuantityField = new TextField(toDrug.getMinQuantity()+"");
		minQuantityBox = new HBox(DMSPage.HBOX_SPACING);
		orderedQuantityLabel = new Label("Quantité en commande : ");
		orderedQuantityField = new TextField(toDrug.getOrderedQuantity()+"");
		orderedQuantityBox = new HBox(DMSPage.HBOX_SPACING);
		modifyButton = new Button("Mettre à jour");
		motherContainer = new VBox(DMSPage.VBOX_SPACING);
		
		//Setting the TextFields
		priceField.setPrefWidth(DMSPage.NUMERICAL_TEXTFIELD_WIDTH);
		inHandQuantityField.setPrefWidth(DMSPage.NUMERICAL_TEXTFIELD_WIDTH);
		minQuantityField.setPrefWidth(DMSPage.NUMERICAL_TEXTFIELD_WIDTH);
		orderedQuantityField.setPrefWidth(DMSPage.NUMERICAL_TEXTFIELD_WIDTH);
		
		//Setting the containers
		priceBox.getChildren().addAll(priceLabel, priceField);
		inHandQuantityBox.getChildren().addAll(inHandQuantityLabel, inHandQuantityField);
		minQuantityBox.getChildren().addAll(minQuantityLabel, minQuantityField);
		orderedQuantityBox.getChildren().addAll(orderedQuantityLabel, orderedQuantityField);
		motherContainer.getChildren().addAll(nameLabel, concentrationLabel, priceBox,
											 inHandQuantityBox, minQuantityBox, orderedQuantityBox, modifyButton, errorMessage);
		
		//Setting the BorderPane
		this.setCenter(motherContainer);
		
		//Setting the Listeners
		setListeners(toDrug);
	}

	private static void setListeners(TODrug toDrug) {
		modifyButton.setOnAction(e -> {
			try {
				DMSController.updateDrug(toDrug.getName(), toDrug.getId(), Integer.parseInt(inHandQuantityField.getText()),
										 Integer.parseInt(minQuantityField.getText()), Double.parseDouble(priceField.getText()));
				InventoryPane.refreshInventoryTable();
				InventoryPane.closeManageStage();
			}
			catch(InvalidInputException iie) {
				errorMessage.setText(iie.getMessage());
			}
		});
	}
}
