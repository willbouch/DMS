package dms.view;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import dms.controller.TODrug;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DrugManagementPane extends GridPane {
	private static Label errorMessage;
	private static Label nameLabel;
	private static Label concentrationLabel;
	private static Label priceLabel;
	private static TextField priceField;
	private static Label inHandQuantityLabel;
	private static TextField inHandQuantityField;
	private static Label minQuantityLabel;
	private static TextField minQuantityField;
	private static Label orderedQuantityLabel;
	private static TextField orderedQuantityField;
	private static Button modifyButton;

	public DrugManagementPane(TODrug toDrug) {
		//Initialization of every attribute
		errorMessage = new Label();
		nameLabel = new Label("Nom :");
		concentrationLabel = new Label("Concentration :");
		priceLabel = new Label("Prix :");
		priceField = new TextField(toDrug.getPrice()+"");
		inHandQuantityLabel = new Label("Quantité en main :");
		inHandQuantityField = new TextField(toDrug.getInHandQuantity()+"");
		minQuantityLabel = new Label("Quantité minimale :");
		minQuantityField = new TextField(toDrug.getMinQuantity()+"");
		orderedQuantityLabel = new Label("Quantité en commande :");
		orderedQuantityField = new TextField(toDrug.getOrderedQuantity()+"");
		modifyButton = new Button("Mettre à jour");

		//Setting the Button
		Platform.runLater(() -> {
			modifyButton.setPrefWidth(this.getWidth());
		});

		//Setting the GridPane
		this.addRow(0, nameLabel, new Label(toDrug.getName()));
		this.addRow(1, concentrationLabel, new Label(toDrug.getConcentration()+" "+toDrug.getUnit()));
		this.addRow(2, priceLabel, priceField);
		this.addRow(3, inHandQuantityLabel, inHandQuantityField);
		this.addRow(4, minQuantityLabel, minQuantityField);
		this.addRow(5, orderedQuantityLabel, orderedQuantityField);
		this.add(modifyButton, 0, 6, 2, 1);
		this.setVgap(DMSPage.VBOX_SPACING);
		this.setHgap(DMSPage.HBOX_SPACING);
		this.setAlignment(Pos.CENTER);

		//Setting the Style
		this.getStylesheets().add(DMSPage.getResource("dms/resources/stylesheet.css"));
		nameLabel.setId("yellowLabel");
		concentrationLabel.setId("yellowLabel");
		priceLabel.setId("yellowLabel");
		inHandQuantityLabel.setId("yellowLabel");
		minQuantityLabel.setId("yellowLabel");
		orderedQuantityLabel.setId("yellowLabel");
		modifyButton.setId("yellowButton");

		//Setting the Listeners
		setListeners(toDrug);
	}

	private static void setListeners(TODrug toDrug) {
		modifyButton.setOnAction(e -> {
			try {
				DMSController.updateDrug(toDrug.getName(), toDrug.getCode(), Integer.parseInt(inHandQuantityField.getText()),
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
