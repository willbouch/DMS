package dms.view;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import dms.controller.TOReceipt;
import dms.controller.TOScannedItem;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReceiptPane extends GridPane {
	private static TableView<TOScannedItem> receiptTable;
	private static TableColumn<TOScannedItem, String> nameColumn;
	private static TableColumn<TOScannedItem, Integer> priceColumn;
	private static TableColumn<TOScannedItem, Integer> codeColumn;
	private static ObservableList<TOScannedItem> receiptList;
	private static Button printButton;
	private static Button deleteItemButton;
	private static Button deleteReceiptButton;
	private static VBox buttonBox;
	private static TextField upc;

	public ReceiptPane() {
		//Initialization of every attribute
		receiptTable = new TableView<>();
		codeColumn = new TableColumn<>("UPC");
		nameColumn = new TableColumn<>("Nom");
		priceColumn = new TableColumn<>("Prix");		
		receiptList = FXCollections.observableArrayList();
		printButton = new Button("Imprimer\nReçu");
		deleteItemButton = new Button("Annuler\nProduit");
		deleteReceiptButton = new Button("Supprimer\ntransaction");
		buttonBox = new VBox(DMSPage.VBOX_SPACING);
		upc = new TextField();

		//Setting the TableView
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		receiptTable.setItems(receiptList);
		receiptTable.getColumns().addAll(codeColumn, nameColumn, priceColumn);
		codeColumn.prefWidthProperty().bind(receiptTable.widthProperty().divide(receiptTable.getColumns().size()));
		nameColumn.prefWidthProperty().bind(receiptTable.widthProperty().divide(receiptTable.getColumns().size()));
		priceColumn.prefWidthProperty().bind(receiptTable.widthProperty().divide(receiptTable.getColumns().size()));
		Platform.runLater(()->
		{
			receiptTable.setMaxWidth(600);
			receiptTable.prefHeightProperty().bind(DMSPage.getPrimaryStage().heightProperty());
		});
		
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
		this.addRow(0, buttonBox, receiptTable);
		this.addRow(1, upc);
		this.setVgap(DMSPage.VBOX_SPACING);
		this.setHgap(DMSPage.HBOX_SPACING);
		this.setAlignment(Pos.CENTER);

		//Setting Style
		this.getStylesheets().add(DMSPage.getResource("dms/resources/stylesheet.css"));
		printButton.setId("greenButton");
		deleteItemButton.setId("redButton");
		deleteReceiptButton.setId("redButton");
		
		//Setting Listeners
		setListeners();
	}
	
	public static void setListeners() {
		upc.setOnAction(e -> {
			try {
				DMSController.addToReceipt(upc.getText());
				upc.setText("");
				refreshReceiptTable();
			}
			catch(InvalidInputException iie) {
				
			}
		});
	}
	
	public static void refreshReceiptTable() {
		receiptTable.getItems().clear();
		try {
			TOReceipt toReceipt = DMSController.getCurrentTOReceipt();
			for(TOScannedItem toScannedItem : toReceipt.getTOScannedItems()) {
				receiptList.add(toScannedItem);
			}
		}
		catch(InvalidInputException iie) {
			
		}
	}
}
