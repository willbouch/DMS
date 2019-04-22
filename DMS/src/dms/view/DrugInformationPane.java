package dms.view;

import dms.controller.TODrug;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class DrugInformationPane extends BorderPane {
	private static Label nameLabel;
	private static Label concentrationLabel;
	private static Label priceLabel;
	private static Label inHandQuantityLabel;
	private static Label orderedQuantityLabel;
	private static VBox motherContainer;
	
	public DrugInformationPane(TODrug toDrug) {
		//Initialization of every attribute
		nameLabel = new Label("Nom : "+toDrug.getName());
		concentrationLabel = new Label("Concentration : "+toDrug.getConcentration()+" "+toDrug.getUnit());
		priceLabel = new Label("Prix : "+toDrug.getPrice()+" $");
		inHandQuantityLabel = new Label("Quantité en main : "+toDrug.getInHandQuantity());
		orderedQuantityLabel = new Label("Quantité en commande : "+toDrug.getOrderedQuantity());
		motherContainer = new VBox(DMSPage.VBOX_SPACING);
		
		//Setting the containers
		motherContainer.getChildren().addAll(nameLabel, concentrationLabel, priceLabel,
											 inHandQuantityLabel, orderedQuantityLabel);
		
		//Setting the BorderPane
		this.setCenter(motherContainer);
	}
}
