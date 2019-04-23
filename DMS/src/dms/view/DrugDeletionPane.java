package dms.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DrugDeletionPane extends BorderPane {
	private static Label messageLabel;
	private static Button yesButton;
	private static Button noButton;
	private static HBox buttonBox;
	private static VBox motherContainer;

	public DrugDeletionPane() {
		//Initialization of every attribute
		messageLabel = new Label("Voulez-vous bien supprimer le produit de la base de données ?");
		yesButton = new Button("Oui");
		noButton = new Button("Non");
		buttonBox = new HBox(DMSPage.HBOX_SPACING);
		motherContainer = new VBox(DMSPage.VBOX_SPACING);

		//Setting the containers
		buttonBox.getChildren().addAll(yesButton, noButton);
		buttonBox.setAlignment(Pos.CENTER);
		motherContainer.getChildren().addAll(messageLabel, buttonBox);
		motherContainer.setAlignment(Pos.CENTER);

		//Setting the BorderPane
		this.setCenter(motherContainer);
	}
}
