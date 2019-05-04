package dms.view;

import dms.controller.TODrug;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class DrugInformationPane extends GridPane {
	private static Label nameLabel;
	private static Label concentrationLabel;
	private static Label priceLabel;
	private static Label inHandQuantityLabel;
	private static Label minQuantityLabel;
	private static Label orderedQuantityLabel;

	public DrugInformationPane(TODrug toDrug) {
		//Initialization of every attribute
		nameLabel = new Label("Nom :");
		concentrationLabel = new Label("Concentration :");
		priceLabel = new Label("Prix :");
		inHandQuantityLabel = new Label("Quantité en main :");
		minQuantityLabel = new Label("Quantité minimale :");
		orderedQuantityLabel = new Label("Quantité en commande :");

		//Setting the GridPane
		this.addRow(0, nameLabel, new Label(toDrug.getName()));
		this.addRow(1, concentrationLabel, new Label(toDrug.getConcentration()+" "+toDrug.getUnit()));
		this.addRow(2, priceLabel, new Label(toDrug.getPrice()+" $"));
		this.addRow(3, inHandQuantityLabel, new Label(""+toDrug.getInHandQuantity()));
		this.addRow(4, minQuantityLabel, new Label(""+toDrug.getMinQuantity()));
		this.addRow(5, orderedQuantityLabel, new Label(""+toDrug.getOrderedQuantity()));
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
	}
}
