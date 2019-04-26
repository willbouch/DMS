package dms.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReceiptPane extends HBox {
	private static ListView<String> receiptListView;;
	private static ObservableList<String> receiptList;
	private static Button printButton;
	private static Button deleteItemButton;
	private static Button deleteReceiptButton;
	private static VBox buttonBox;

	public ReceiptPane() {
		//Initialization of every attribute
		receiptListView = new ListView<>();
		receiptList = FXCollections.observableArrayList();
		printButton = new Button("Imprimer\nReçu");
		deleteItemButton = new Button("Annuler\nProduit");
		deleteReceiptButton = new Button("Supprimer\ntransaction");
		buttonBox = new VBox(DMSPage.VBOX_SPACING);

		//Setting the ListView
		receiptListView.setItems(receiptList);
		receiptListView.setMaxSize(DMSPage.RECEIPT_WIDTH, DMSPage.RECEIPT_HEIGHT);
		for(int i = 0; i < 30; i++) {
			receiptListView.getItems().add("exemple");
		}
		
		//Setting the Buttons
		Platform.runLater(()->
		{
			printButton.setMinWidth(deleteReceiptButton.getWidth());
			deleteItemButton.setMinWidth(deleteReceiptButton.getWidth());
		});
		
		//Setting containers
		buttonBox.getChildren().addAll(printButton, deleteItemButton, deleteReceiptButton);
		buttonBox.setAlignment(Pos.CENTER);
		
		//Setting the MotherContainer
		this.getChildren().addAll(buttonBox, receiptListView);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(DMSPage.HBOX_SPACING);
		
		//Setting Style
		this.getStylesheets().add(DMSPage.getResource("dms/resources/stylesheet.css"));
		printButton.setId("greenButton");
		deleteItemButton.setId("redButton");
		deleteReceiptButton.setId("redButton");
	}
}
