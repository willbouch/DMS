package dms.view;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import dms.controller.TODrug;
import dms.controller.TOInventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
	private static Button addDrugButton;
	private static HBox buttonHorBox;
	private static VBox buttonVerBox;
	private static VBox motherContainer;
	private static ImageView previousArrow;
	private static ImageView nextArrow;
	private static Stage informationStage;
	private static Stage manageStage;
	private static Stage deleteStage;
	private static Stage addDrugStage;

	public InventoryPane() {
		//Initialization of every attribute
		errorMessage = new Label(); 
		inventoryTable = new TableView<>();
		nameColumn = new TableColumn<>("Nom");
		idColumn = new TableColumn<>("Id");
		quantityColumn = new TableColumn<>("Quantité en main");
		inventoryList = FXCollections.observableArrayList();
		nextPreviousBox = new HBox(DMSPage.HBOX_SPACING);
		letterField = new TextField("A");
		informationButton = new Button("Information");
		manageButton = new Button("Gestion");
		deleteButton = new Button("Supprimer");
		addDrugButton = new Button("Ajouter");
		orderButton = new Button("Approvisionnement");
		buttonHorBox = new HBox(DMSPage.HBOX_SPACING);
		buttonVerBox = new VBox(DMSPage.VBOX_SPACING);
		motherContainer = new VBox(DMSPage.VBOX_SPACING);
		previousArrow = new ImageView(DMSPage.getResource("dms/resources/nextPreviousArrow.png"));
		nextArrow = new ImageView(DMSPage.getResource("dms/resources/nextPreviousArrow.png"));

		//Setting the arrows
		previousArrow.setRotate(180);
		previousArrow.setFitHeight(DMSPage.ARROW_SIZE);
		previousArrow.setFitWidth(DMSPage.ARROW_SIZE);
		nextArrow.setFitHeight(DMSPage.ARROW_SIZE);
		nextArrow.setFitWidth(DMSPage.ARROW_SIZE);
		previousArrow.setEffect(new DropShadow(2, Color.rgb(0, 0, 0, 1)));
		nextArrow.setEffect(new DropShadow(2, Color.rgb(0, 0, 0, 1)));

		//Setting the letter TextField
		letterField.setAlignment(Pos.CENTER);
		letterField.setPrefWidth(DMSPage.NUMERICAL_TEXTFIELD_WIDTH);

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
		buttonHorBox.getChildren().addAll(informationButton, manageButton, deleteButton, addDrugButton);
		buttonHorBox.setAlignment(Pos.CENTER);
		buttonVerBox.getChildren().addAll(buttonHorBox, orderButton);
		buttonVerBox.setAlignment(Pos.CENTER);
		nextPreviousBox.getChildren().addAll(previousArrow, letterField, nextArrow);
		nextPreviousBox.setAlignment(Pos.CENTER);
		motherContainer.getChildren().addAll(inventoryTable, nextPreviousBox, buttonVerBox, errorMessage);
		motherContainer.setAlignment(Pos.CENTER);

		//Setting the BorderPane
		this.setCenter(motherContainer);	

		//Setting the Listeners
		setListeners();
	}

	private static void setListeners() {
		letterField.setOnKeyPressed(e -> {
			if(!e.getCode().equals(KeyCode.ENTER)) {
				char c = e.getCharacter().charAt(0);
				letterField.setText(c+"");
			}
		});

		letterField.setOnAction(e -> {
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
		
		informationButton.setOnAction(e -> {
			informationStage = new Stage();
			informationStage.setAlwaysOnTop(true);
			informationStage.initOwner(DMSPage.getPrimaryStage());
			informationStage.setScene(new Scene(new DrugInformationPane(inventoryTable.getSelectionModel().getSelectedItem())));
			informationStage.setResizable(false);
			informationStage.show();
			informationStage.setTitle("Information");
			informationStage.setHeight(DMSPage.INFORMATION_WINDOW_HEIGHT);
			informationStage.setWidth(DMSPage.INFORMATION_WINDOW_WIDTH);
			
			disableButtons();
			informationStage.setOnCloseRequest(a -> {
				enableButtons();
			});
		});
		
		manageButton.setOnAction(e -> {
			manageStage = new Stage();
			manageStage.setAlwaysOnTop(true);
			manageStage.initOwner(DMSPage.getPrimaryStage());
			manageStage.setScene(new Scene(new DrugManagementPane(inventoryTable.getSelectionModel().getSelectedItem())));
			manageStage.setResizable(false);
			manageStage.show();
			manageStage.setTitle("Gérer");
			manageStage.setHeight(DMSPage.MANAGEMENT_WINDOW_HEIGHT);
			manageStage.setWidth(DMSPage.MANAGEMENT_WINDOW_WIDTH);
			
			disableButtons();
			manageStage.setOnCloseRequest(a -> {
				enableButtons();
			});
		});
		
		deleteButton.setOnAction(e -> {
			deleteStage = new Stage();
			deleteStage.setAlwaysOnTop(true);
			deleteStage.initOwner(DMSPage.getPrimaryStage());
			deleteStage.setScene(new Scene(new DrugDeletionPane(inventoryTable.getSelectionModel().getSelectedItem())));
			deleteStage.setResizable(false);
			deleteStage.show();
			deleteStage.setTitle("Supprimer");
			deleteStage.setHeight(DMSPage.DELETION_WINDOW_HEIGHT);
			deleteStage.setWidth(DMSPage.DELETION_WINDOW_WIDTH);
			
			disableButtons();
			deleteStage.setOnCloseRequest(a -> {
				enableButtons();
			});
		});
		
		addDrugButton.setOnAction(e -> {
			addDrugStage = new Stage();
			addDrugStage.setAlwaysOnTop(true);
			addDrugStage.initOwner(DMSPage.getPrimaryStage());
			addDrugStage.setScene(new Scene(new DrugAddingPane()));
			addDrugStage.setResizable(false);
			addDrugStage.show();
			addDrugStage.setTitle("Ajouter");
			addDrugStage.setHeight(DMSPage.ADDING_WINDOW_HEIGHT);
			addDrugStage.setWidth(DMSPage.ADDING_WINDOW_WIDTH);
			
			disableButtons();
			addDrugStage.setOnCloseRequest(a -> {
				enableButtons();
			});
		});
	}
	
	public static void closeDeleteStage() {
		deleteStage.close();
		enableButtons();
	}
	
	public static void closeManageStage() {
		manageStage.close();
		enableButtons();
	}
	
	public static void closeAddDrugStage() {
		addDrugStage.close();
		enableButtons();
	}
	
	private static void disableButtons() {
		buttonVerBox.setDisable(true);
	}
	
	private static void enableButtons() {
		buttonVerBox.setDisable(false);
	}

	public static void refreshInventoryTable() {
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
