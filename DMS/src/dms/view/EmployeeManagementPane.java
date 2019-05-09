package dms.view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class EmployeeManagementPane extends GridPane {
	private static ComboBox<String> employeesComboBox;
	private static Label getPasswordLabel;
	private static Button changePasswordButton;
	private static TextField changePasswordField;
	private static TextField usernameField;
	private static RadioButton cashiereRadioButton;
	private static RadioButton pharmacistRadioButton;
	private static RadioButton adminRadioButton;
	private static VBox radioButtonBox;
	private static TextField passwordField;
	private static TextField confirmationPasswordField;
	private static Button registerButton;

	public EmployeeManagementPane() {
		//Initialization of every attribute
		employeesComboBox = new ComboBox<>();
		getPasswordLabel = new Label();
		changePasswordButton = new Button("Modifier le mot de passe");
		changePasswordField = new TextField();
		usernameField = new TextField();
		passwordField = new TextField();
		confirmationPasswordField = new TextField();
		cashiereRadioButton = new RadioButton("Caissier/ère");
		pharmacistRadioButton = new RadioButton("Pharmacien/ne");
		adminRadioButton = new RadioButton("Administrateur/rice");
		radioButtonBox = new VBox(DMSPage.VBOX_SPACING);
		registerButton = new Button("Créer");

		//Setting the containers
		radioButtonBox.getChildren().addAll(cashiereRadioButton, pharmacistRadioButton, adminRadioButton);
		
		//Setting the GridPane
		this.addRow(0, employeesComboBox, getPasswordLabel);
		this.addRow(1, changePasswordField, changePasswordButton);
		this.addRow(2, usernameField, radioButtonBox, registerButton);
		this.addRow(3, passwordField, confirmationPasswordField);
		this.setVgap(DMSPage.VBOX_SPACING);
		this.setHgap(DMSPage.HBOX_SPACING);
		
		//Setting Style
		this.getStylesheets().add(DMSPage.getResource("dms/resources/stylesheet.css"));
	}
}
