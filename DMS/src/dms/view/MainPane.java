package dms.view;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainPane extends BorderPane {
	private static ReceiptPane receiptPane;
	private static EmployeeManagementPane employeeManagementPane;
	private static VBox leftBox;

	public MainPane() {
		//Initialization of every attribute
		receiptPane = new ReceiptPane();
		employeeManagementPane = new EmployeeManagementPane();
		leftBox = new VBox(DMSPage.VBOX_SPACING);
		
		//Setting containers
		leftBox.getChildren().addAll(receiptPane, employeeManagementPane);
		leftBox.setAlignment(Pos.CENTER);
		
		//Setting BorderPane
		this.setLeft(leftBox);

		//Setting Style
		this.getStylesheets().add(DMSPage.getResource("dms/resources/stylesheet.css"));
	}
}
