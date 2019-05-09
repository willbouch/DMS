package dms.view;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import dms.controller.TODrug;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class DrugDeletionPane extends GridPane {
	private static Label messageLabel;
	private static Button yesButton;
	private static Button noButton;
	private static HBox buttonBox;

	public DrugDeletionPane(TODrug toDrug) {
		//Initialization of every attribute
		messageLabel = new Label("Voulez-vous bien supprimer ("+toDrug.getName()+") de la base de données ?");
		yesButton = new Button("Oui");
		noButton = new Button("Non");
		buttonBox = new HBox(DMSPage.HBOX_SPACING);

		//Setting the containers
		buttonBox.getChildren().addAll(yesButton, noButton);
		buttonBox.setAlignment(Pos.CENTER);
		
		//Setting the GridPane
		this.addRow(0, messageLabel);
		this.addRow(1, buttonBox);
		this.setVgap(DMSPage.VBOX_SPACING);
		this.setHgap(DMSPage.HBOX_SPACING);
		this.setAlignment(Pos.CENTER);

		//Setting the Style
		this.getStylesheets().add(DMSPage.getResource("dms/resources/stylesheet.css"));
		messageLabel.setId("redLabel");
		yesButton.setId("greenButton");
		noButton.setId("redButton");

		//Setting the Listeners
		setListeners(toDrug);
	}

	private static void setListeners(TODrug toDrug) {
		yesButton.setOnAction(e -> {
			InventoryPane.closeDeleteStage();
			try {
				DMSController.deleteDrug(toDrug.getName(), toDrug.getId());
				InventoryPane.refreshInventoryTable();
			}
			catch(InvalidInputException iie) {
				//We do nothing
			}
		});

		noButton.setOnAction(e -> {
			InventoryPane.closeDeleteStage();
		});
	}
}
