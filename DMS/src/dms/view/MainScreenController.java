package dms.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import dms.controller.TODrug;
import dms.controller.TOReceipt;
import dms.controller.TOScannedItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainScreenController implements Initializable {
	private static ObservableList<TODrug> inventoryList;
	private static ObservableList<String> drugsToOrderList;
	private static ObservableList<TOScannedItem> receiptList;

	private static TODrug toDrug;
	private static TOScannedItem toScannedItem;

	private static Stage informationStage;
	private static Stage manageStage;
	private static Stage deleteStage;
	private static Stage addDrugStage;

	@FXML
	private TableView<TODrug> inventoryTable;

	@FXML
	private TableColumn<TODrug, String> inventoryNameColumn;

	@FXML
	private TableColumn<TODrug, String> inventoryQuantityColumn;

	@FXML
	private Button addButton;

	@FXML
	private Button deleteButton;

	@FXML
	private Button orderButton;

	@FXML
	private Button manageButton;

	@FXML
	private TextField letterField;

	@FXML
	private ImageView rightArrow;

	@FXML
	private ImageView leftArrow;

	@FXML
	private TableView<TOScannedItem> receiptTable;

	@FXML
	private TableColumn<TOScannedItem, String> receiptCodeColumn;

	@FXML
	private TableColumn<TOScannedItem, String> receiptNameColumn;

	@FXML
	private TableColumn<TOScannedItem, String> receiptPriceColumn;

	@FXML
	private TextField codeField;

	@FXML
	private TextField quantityField;

	@FXML
	private Button printButton;

	@FXML
	private Button cancelItemButton;

	@FXML
	private Button deleteReceiptButton;
	
	@FXML
	private Button adjustQuantityButton;

	@FXML
	private Label totalLabel;
	
	@FXML
    private ListView<String> drugsToOrderListView;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rightArrow.setImage(new Image(DMSPage.getResource("dms/resources/nextPreviousArrow.png")));
		leftArrow.setImage(new Image(DMSPage.getResource("dms/resources/nextPreviousArrow.png")));
		leftArrow.setRotate(180);

		inventoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		inventoryQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("inHandQuantity"));
		inventoryList = FXCollections.observableArrayList();
		inventoryTable.setItems(inventoryList);
		inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		receiptNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		receiptPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		receiptCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
		receiptList = FXCollections.observableArrayList();
		receiptTable.setItems(receiptList);
		receiptTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		drugsToOrderList = FXCollections.observableArrayList();
		drugsToOrderListView.setItems(drugsToOrderList);

		refreshInventoryTable();
		refreshDrugsToOrderListView();
	}

	@FXML
	void doAddToReceipt(ActionEvent event) {

		try {
			DMSController.addToReceipt(codeField.getText());
			codeField.setText("");
			refreshReceiptTable();
		}
		catch(InvalidInputException iie) {

		}

	}

	@FXML
	void doChangeLeft(MouseEvent event) {
		if(!letterField.getText().equals("A")) {
			char letter = letterField.getText().charAt(0);
			letter--;
			letterField.setText(letter+"");
		}
		else {
			letterField.setText("Z");
		}
		refreshInventoryTable();
	}

	@FXML
	void doChangeRight(MouseEvent event) {
		if(!letterField.getText().equals("Z")) {
			char letter = letterField.getText().charAt(0);
			letter++;
			letterField.setText(letter+"");
		}
		else {
			letterField.setText("A");
		}
		refreshInventoryTable();
	}

	@FXML
	void doAdd(ActionEvent event) throws Exception {
		addDrugStage = new Stage();
		
		addDrugStage.setAlwaysOnTop(true);
		addDrugStage.initOwner(DMSPage.getPrimaryStage());

		GridPane pane = (GridPane) FXMLLoader.load(DMSPage.class.getResource("drugAddingPopup.fxml"));

		addDrugStage.setScene(new Scene(pane));
		addDrugStage.setResizable(false);
		addDrugStage.show();
		addDrugStage.setTitle("Ajouter");

		addDrugStage.setOnCloseRequest(e -> {
			refreshInventoryTable();
		});
	}

	@FXML
	void doDelete(ActionEvent event) throws Exception {
		toDrug = inventoryTable.getSelectionModel().getSelectedItem();
		if(toDrug != null) {
			deleteStage = new Stage();
			deleteStage.setAlwaysOnTop(true);
			deleteStage.initOwner(DMSPage.getPrimaryStage());

			GridPane pane = (GridPane) FXMLLoader.load(DMSPage.class.getResource("drugDeletionPopup.fxml"));

			deleteStage.setScene(new Scene(pane));
			deleteStage.setResizable(false);
			deleteStage.show();
			deleteStage.setTitle("Supprimer");

			deleteStage.setOnCloseRequest(e -> {
				refreshInventoryTable();
			});
		}
	}

	@FXML
	void doChangeTable(ActionEvent event) {
		char letter = letterField.getText().charAt(0);
		if(letter < 'A' || letter > 'Z') {
			if(letter > 'a' && letter < 'z') {
				letter -= (int)('a' - 'A');
				letterField.setText(letter+"");
			}
			else {
				letterField.setText("A");
			}
		}

		refreshInventoryTable();
	}

	@FXML
	void doChangeLetter(KeyEvent event) {
		if(!event.getCode().equals(KeyCode.ENTER)) {
			char c = event.getCharacter().charAt(0);
			letterField.setText(c+"");
		}
	}

	@FXML
	void doManage(ActionEvent event) throws Exception {
		toDrug = inventoryTable.getSelectionModel().getSelectedItem();
		if(toDrug != null) {
			manageStage = new Stage();
			manageStage.setAlwaysOnTop(true);
			manageStage.initOwner(DMSPage.getPrimaryStage());

			GridPane pane = (GridPane) FXMLLoader.load(DMSPage.class.getResource("drugManagingPopup.fxml"));

			manageStage.setScene(new Scene(pane));
			manageStage.setResizable(false);
			manageStage.show();
			manageStage.setTitle("Gérer");

			manageStage.setOnCloseRequest(e -> {
				refreshInventoryTable();
			});
		}
	}

	@FXML
	void doOrder(ActionEvent event) {
		//TODO
	}

	@FXML
	void doInformation(MouseEvent event) throws Exception {
		if(event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
			toDrug = inventoryTable.getSelectionModel().getSelectedItem();
			if(toDrug != null) {
				informationStage = new Stage();
				informationStage.setAlwaysOnTop(true);
				informationStage.initOwner(DMSPage.getPrimaryStage());

				GridPane pane = (GridPane) FXMLLoader.load(DMSPage.class.getResource("drugInformationPopup.fxml"));

				informationStage.setScene(new Scene(pane));
				informationStage.setResizable(false);
				informationStage.show();
				informationStage.setTitle("Information");
			}
		}
	}

	@FXML
	void doAdjustQuantity(ActionEvent event) throws Exception {
		quantityField.setVisible(true);
	}
	
	@FXML
	void doDeleteReceipt(ActionEvent event) throws Exception {
		try{
			DMSController.deleteReceipt();
		}
		catch(InvalidInputException iie) {
			
		}
		refreshReceiptTable();
	}
	
	@FXML
	void doCancelItem(ActionEvent event) throws Exception {
		toScannedItem = receiptTable.getSelectionModel().getSelectedItem();

		try{
			DMSController.deleteFromReceipt(toScannedItem.getCode());
		}
		catch(InvalidInputException iie) {
			
		}
		refreshReceiptTable();
	}

	@FXML
	void doPrintReceipt(ActionEvent event) throws Exception {
		try{
			for(TOScannedItem toScannedItem : receiptTable.getItems()) {
				DMSController.reduceDrugQuantityByOne(toScannedItem);
			}
			DMSController.printReceipt();
			DMSController.deleteReceipt();
		}
		catch(InvalidInputException iie) {
			
		}
		
		refreshInventoryTable();
		refreshReceiptTable();
	}
	
	@FXML
	void adjustQuantity(ActionEvent event) throws Exception {
		toScannedItem = receiptTable.getSelectionModel().getSelectedItem();
		
		try{
			DMSController.deleteFromReceipt(toScannedItem.getCode());
			for(int i = 0; i < Integer.parseInt(quantityField.getText()); i++){
				DMSController.addToReceipt(toScannedItem.getCode());
			}
		}
		catch(InvalidInputException iie) {
			
		}
		
		refreshReceiptTable();
		quantityField.setVisible(false);
		quantityField.setText("");
	}
	
	private void refreshInventoryTable() {
		inventoryTable.getItems().clear();
		try {
			List<TODrug> toDrugs = DMSController.getInventoryWithFirstLetter(letterField.getText().charAt(0));
			for(TODrug toDrug : toDrugs) {
				inventoryList.add(toDrug);
			}
		}
		catch(InvalidInputException iie) {

		}
	}
	
	private void refreshDrugsToOrderListView() {
		drugsToOrderListView.getItems().clear();
		try {
			List<TODrug> toDrugs = DMSController.getDrugsToOrder();
			for(TODrug toDrug : toDrugs) {
				drugsToOrderList.add(String.format("%1$-" + 25 + "s", toDrug.getName())+toDrug.getCode());
			}
		}
		catch(InvalidInputException iie) {

		}
	}

	private void refreshReceiptTable() {
		receiptTable.getItems().clear();
		try {
			TOReceipt toReceipt = DMSController.getCurrentTOReceipt();
			
			if(toReceipt != null) {
				for(TOScannedItem toScannedItem : toReceipt.getTOScannedItems()) {
					receiptList.add(toScannedItem);
				}
			}
			totalLabel.setText("Total : "+String.format("%.2f", toReceipt.getTotalPrice()));
		}
		catch(InvalidInputException iie) {
			
		}
	}
	
	public static TODrug getTODrug() {
		return toDrug;
	}
}