package dms.view;

import dms.controller.DMSController;
import dms.controller.InvalidInputException;
import dms.controller.TODrug;
import dms.controller.TOInventory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InventoryPane extends VBox {
	private static TableView<TODrug> inventoryTable;
	private static TableColumn<TODrug, String> nameColumn;
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
	private static ImageView previousArrow;
	private static ImageView nextArrow;
	private static Stage informationStage;
	private static Stage manageStage;
	private static Stage deleteStage;
	private static Stage addDrugStage;

	public InventoryPane() {
		//Initialization of every attribute
		inventoryTable = new TableView<>();
		nameColumn = new TableColumn<>("Nom");
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
		previousArrow = new ImageView(DMSPage.getResource("dms/resources/nextPreviousArrow.png"));
		nextArrow = new ImageView(DMSPage.getResource("dms/resources/nextPreviousArrow.png"));

		//Setting the arrows
		previousArrow.setRotate(180);
		previousArrow.setFitHeight(DMSPage.ARROW_SIZE);
		previousArrow.setFitWidth(DMSPage.ARROW_SIZE);
		nextArrow.setFitHeight(DMSPage.ARROW_SIZE);
		nextArrow.setFitWidth(DMSPage.ARROW_SIZE);

		//Setting the letter TextField
		letterField.setAlignment(Pos.CENTER);
		letterField.setPrefWidth(DMSPage.NUMERICAL_TEXTFIELD_WIDTH);

		//Setting the TableView
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("inHandQuantity"));
		inventoryTable.setItems(inventoryList);
		inventoryTable.getColumns().addAll(nameColumn, quantityColumn);
		nameColumn.prefWidthProperty().bind(inventoryTable.widthProperty().divide(inventoryTable.getColumns().size()));
		quantityColumn.prefWidthProperty().bind(inventoryTable.widthProperty().divide(inventoryTable.getColumns().size()));
		refreshInventoryTable();
		Platform.runLater(()->
		{
			inventoryTable.setMaxWidth(addDrugButton.getWidth()+informationButton.getWidth()+manageButton.getWidth()+deleteButton.getWidth()+3*DMSPage.HBOX_SPACING);
			inventoryTable.prefHeightProperty().bind(DMSPage.getPrimaryStage().heightProperty());
		});

		//Setting the containers
		buttonHorBox.getChildren().addAll(addDrugButton, informationButton, manageButton, deleteButton);
		buttonHorBox.setAlignment(Pos.CENTER);
		buttonVerBox.getChildren().addAll(buttonHorBox, orderButton);
		buttonVerBox.setAlignment(Pos.CENTER);
		nextPreviousBox.getChildren().addAll(previousArrow, letterField, nextArrow);
		nextPreviousBox.setAlignment(Pos.CENTER);

		//Setting the MotherContainer
		this.getChildren().addAll(inventoryTable, nextPreviousBox, buttonVerBox);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(DMSPage.VBOX_SPACING);

		//Setting the Listeners
		setListeners();	

		//Setting the Style
		this.getStylesheets().add(DMSPage.getResource("dms/resources/stylesheet.css"));
		addDrugButton.setId("greenButton");
		informationButton.setId("yellowButton");
		manageButton.setId("yellowButton");
		deleteButton.setId("redButton");

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
			TODrug toDrug = inventoryTable.getSelectionModel().getSelectedItem();
			if(toDrug != null) {
				informationStage = new Stage();
				informationStage.setAlwaysOnTop(true);
				informationStage.initOwner(DMSPage.getPrimaryStage());
				informationStage.setScene(new Scene(new DrugInformationPane(toDrug)));
				informationStage.setResizable(false);
				informationStage.show();
				informationStage.setTitle("Information");
				informationStage.setHeight(DMSPage.INFORMATION_WINDOW_HEIGHT);
				informationStage.setWidth(DMSPage.INFORMATION_WINDOW_WIDTH);

				disableButtons();
				informationStage.setOnCloseRequest(a -> {
					enableButtons();
				});
			}
		});

		manageButton.setOnAction(e -> {
			TODrug toDrug = inventoryTable.getSelectionModel().getSelectedItem();
			if(toDrug != null) {
				manageStage = new Stage();
				manageStage.setAlwaysOnTop(true);
				manageStage.initOwner(DMSPage.getPrimaryStage());
				manageStage.setScene(new Scene(new DrugManagementPane(toDrug)));
				manageStage.setResizable(false);
				manageStage.show();
				manageStage.setTitle("Gérer");
				manageStage.setHeight(DMSPage.MANAGEMENT_WINDOW_HEIGHT);
				manageStage.setWidth(DMSPage.MANAGEMENT_WINDOW_WIDTH);

				disableButtons();
				manageStage.setOnCloseRequest(a -> {
					enableButtons();
				});
			}
		});

		deleteButton.setOnAction(e -> {
			TODrug toDrug = inventoryTable.getSelectionModel().getSelectedItem();
			if(toDrug != null) {
				deleteStage = new Stage();
				deleteStage.setAlwaysOnTop(true);
				deleteStage.initOwner(DMSPage.getPrimaryStage());
				deleteStage.setScene(new Scene(new DrugDeletionPane(toDrug)));
				deleteStage.setResizable(false);
				deleteStage.show();
				deleteStage.setTitle("Supprimer");
				deleteStage.setHeight(DMSPage.DELETION_WINDOW_HEIGHT);
				deleteStage.setWidth(DMSPage.DELETION_WINDOW_WIDTH);

				disableButtons();
				deleteStage.setOnCloseRequest(a -> {
					enableButtons();
				});
			}
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
			
		}
	}
}
