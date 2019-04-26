package dms.view;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainPane extends BorderPane {
	private static InventoryPane inventoryPane;
	private static ReceiptPane receiptPane;

	public MainPane() {
		//Initialization of every attribute
		inventoryPane = new InventoryPane();
		receiptPane = new ReceiptPane();
		
		//Setting BorderPane
		this.setRight(inventoryPane);
		this.setLeft(receiptPane);
		
		//Setting Style
		this.getStylesheets().add(DMSPage.getResource("dms/resources/stylesheet.css"));
	}
}
