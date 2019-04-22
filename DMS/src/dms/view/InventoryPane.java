package dms.view;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import dms.controller.TODrug;
import dms.controller.TOInventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class InventoryPane extends BorderPane {
	private static Label errorMessage;
	private static TableView<TODrug> inventoryTable;
	private static TableColumn<TODrug, String> nameColumn;
	private static TableColumn<TODrug, Integer> idColumn;	
	private static TableColumn<TODrug, Integer> quantityColumn;
	private static ObservableList<TODrug> inventoryList;
	private static HBox nextPreviousBox;
	private static TextField letterField;
	private static Button informationButton;
	private static Button manageButton;
	private static Button deleteButton;
	private static Button orderButton;
	private static HBox buttonBox;
	private static VBox motherContainer;
	private static ImageView previousArrow;
	private static ImageView nextArrow;

	public InventoryPane() {
		//Initialization of every attribute
		errorMessage = new Label(); 
		inventoryTable = new TableView<>();
		nameColumn = new TableColumn<>("Nom");
		idColumn = new TableColumn<>("Id");
		quantityColumn = new TableColumn<>("Quantit� en main");
		inventoryList = FXCollections.observableArrayList();
		nextPreviousBox = new HBox(DMSPage.HBOX_SPACING);
		letterField = new TextField("A");
		informationButton = new Button("Information");
		manageButton = new Button("Gestion");
		deleteButton = new Button("Supprimer");
		orderButton = new Button("Approvisionnement");
		buttonBox = new HBox(DMSPage.HBOX_SPACING);
		motherContainer = new VBox(DMSPage.VBOX_SPACING);
		previousArrow = new ImageView(DMSPage.getResource("dms/resources/nextPreviousArrow.png"));
		nextArrow = new ImageView(DMSPage.getResource("dms/resources/nextPreviousArrow.png"));

		//Setting the arrows
		previousArrow.setRotate(180);
		previousArrow.setFitHeight(50);
		previousArrow.setFitWidth(50);
		nextArrow.setFitHeight(50);
		nextArrow.setFitWidth(50);
	    previousArrow.setEffect(new DropShadow(2, Color.rgb(0, 0, 0, 1)));
	    nextArrow.setEffect(new DropShadow(2, Color.rgb(0, 0, 0, 1)));
	    
	    //Setting the letter TextField
	    letterField.setAlignment(Pos.CENTER);
	    letterField.setPrefWidth(50);
				
		//Setting the TableView
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("inHandQuantity"));
		inventoryTable.setItems(inventoryList);
		inventoryTable.getColumns().addAll(nameColumn, idColumn, quantityColumn);
		nameColumn.prefWidthProperty().bind(inventoryTable.widthProperty().divide(inventoryTable.getColumns().size()));
		idColumn.prefWidthProperty().bind(inventoryTable.widthProperty().divide(inventoryTable.getColumns().size()));
		quantityColumn.prefWidthProperty().bind(inventoryTable.widthProperty().divide(inventoryTable.getColumns().size()));	
		refreshInventoryTable();

		//Setting the containers
		buttonBox.getChildren().addAll(informationButton, manageButton, deleteButton);
		buttonBox.setAlignment(Pos.CENTER);
		nextPreviousBox.getChildren().addAll(previousArrow, letterField, nextArrow);
		nextPreviousBox.setAlignment(Pos.CENTER);
		motherContainer.getChildren().addAll(inventoryTable, nextPreviousBox, buttonBox, orderButton, errorMessage);
		motherContainer.setAlignment(Pos.CENTER);

		//Setting the BorderPane
		this.setCenter(motherContainer);	

		//Setting the Listeners
		setListeners();
	}
	
	private static void setListeners() {
		letterField.setOnAction(e -> {
			char letter = letterField.getText().charAt(0);
			if(letter < 'A' || letter > 'Z') {
				letterField.setText("A");
			}
			refreshInventoryTable();
		});
		
		previousArrow.setOnMouseClicked(e -> {
			if(!letterField.getText().equals("A")) {
				char letter = letterField.getText().charAt(0);
				letter--;
				letterField.setText(letter+"");
			}
			else {
				letterField.setText("Z");
			}
			refreshInventoryTable();
		});
		
		nextArrow.setOnMouseClicked(e -> {
			if(!letterField.getText().equals("Z")) {
				char letter = letterField.getText().charAt(0);
				letter++;
				letterField.setText(letter+"");
			}
			else {
				letterField.setText("A");
			}
			refreshInventoryTable();
		});
	}
	
	private static void refreshInventoryTable() {
		inventoryTable.getItems().clear();
		try {
			TOInventory toInventory = DMSController.getInventoryWithFirstLetter(letterField.getText().charAt(0));
			for(TODrug toDrug : toInventory.getTODrugs()) {
				inventoryList.add(toDrug);
			}
		}
		catch(InvalidInputException iie) {
			errorMessage.setText(iie.getMessage());
		}
	}
}