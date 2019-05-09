package dms.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReceiptPane extends GridPane {
	private static ListView<String> receiptListView;;
	private static ObservableList<String> receiptList;
	private static Button printButton;
	private static Button deleteItemButton;
	private static Button deleteReceiptButton;
	private static VBox buttonBox;
	private static TextField upc;

	public ReceiptPane() {
		//Initialization of every attribute
		receiptListView = new ListView<>();
		receiptList = FXCollections.observableArrayList();
		printButton = new Button("Imprimer\nReçu");
		deleteItemButton = new Button("Annuler\nProduit");
		deleteReceiptButton = new Button("Supprimer\ntransaction");
		buttonBox = new VBox(DMSPage.VBOX_SPACING);
		upc = new TextField();

		//Setting the ListView
		receiptListView.setItems(receiptList);
		for(int i = 0; i < 30; i++) {
			receiptListView.getItems().add("exemple");
		}
		receiptListView.prefHeightProperty().bind(DMSPage.getPrimaryStage().heightProperty());

		//Setting the Buttons
		Platform.runLater(()->
		{
			printButton.setMinWidth(deleteReceiptButton.getWidth());
			deleteItemButton.setMinWidth(deleteReceiptButton.getWidth());
		});

		//Setting containers
		buttonBox.getChildren().addAll(printButton, deleteItemButton, deleteReceiptButton);
		buttonBox.setAlignment(Pos.CENTER);

		//Setting the GridPane
		this.addRow(0, buttonBox, receiptListView);
		this.addRow(1, upc);
		this.setVgap(DMSPage.VBOX_SPACING);
		this.setHgap(DMSPage.HBOX_SPACING);
		this.setAlignment(Pos.CENTER);

		//Setting Style
		this.getStylesheets().add(DMSPage.getResource("dms/resources/stylesheet.css"));
		printButton.setId("greenButton");
		deleteItemButton.setId("redButton");
		deleteReceiptButton.setId("redButton");
	}
}
