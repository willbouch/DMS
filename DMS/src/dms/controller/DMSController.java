package dms.controller;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import dms.application.DMSApplication;
import dms.model.Administrator;
import dms.model.Cashier;
import dms.model.DMS;
import dms.model.Drug;
import dms.model.Inventory;
import dms.model.Pharmacist;
import dms.model.Receipt;
import dms.model.User;
import dms.model.UserRole;
import dms.persistence.DMSPersistence;

public class DMSController {

	//Modifier Methods

	public static void addDrug(String name, double price, double concentration, String unit, int inHandQuantity, int minQuantity, String code) throws InvalidInputException{
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		DMS dms = DMSApplication.getDMS();

		if(currentUserRole == null) {
			throw new InvalidInputException("Aucun utilisateur n'est connecté.");
		}

		if(currentUserRole instanceof Cashier) {
			throw new InvalidInputException("Vous n'avez pas les droits nécessaires pour cette opération.");
		}

		//Change the "structure" of the name
		name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();

		Inventory inventory = dms.findInventory(name.charAt(0));
		if(inventory == null) {
			throw new InvalidInputException("L'inventaire n'existe pas.");
		}

		//We find the index where to add the drug
		int index = 0;
		for(Drug drug : inventory.getDrugs()) {
			if(name.compareToIgnoreCase(drug.getName()) < 0) {
				break;
			}
			index++;
		}

		//We add the drug to the inventory we found and we save the file
		try {
			Drug drug = new Drug(name, price, concentration, unit, inHandQuantity, minQuantity, code, inventory);
			inventory.addOrMoveDrugAt(drug, index);
			DMSPersistence.save(dms);
		}
		catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}

	public static void updateDrug(String name, String code, int newInHandQuantity, int newMinQuantity, double newPrice) throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		DMS dms = DMSApplication.getDMS();

		if(currentUserRole == null) {
			throw new InvalidInputException("Aucun utilisateur n'est connecté.");
		}

		if(currentUserRole instanceof Cashier) {
			throw new InvalidInputException("Vous n'avez pas les droits nécessaires pour cette opération.");
		}

		Inventory inventory = dms.findInventory(name.charAt(0));
		if(inventory == null) {
			throw new InvalidInputException("L'inventaire n'existe pas.");
		}

		Drug drug = inventory.findDrug(code);
		if(drug == null) {
			throw new InvalidInputException("Le médicament n'existe pas.");
		}

		try {
			drug.setInHandQuantity(newInHandQuantity);
			drug.setMinQuantity(newMinQuantity);
			drug.setPrice(newPrice);
			DMSPersistence.save(dms);
		}
		catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void deleteDrug(String name, String code) throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		DMS dms = DMSApplication.getDMS();

		if(currentUserRole == null) {
			throw new InvalidInputException("Aucun utilisateur n'est connecté.");
		}

		if(currentUserRole instanceof Cashier) {
			throw new InvalidInputException("Vous n'avez pas les droits nécessaires pour cette opération.");
		}

		Inventory inventory = dms.findInventory(name.charAt(0));
		if(inventory == null) {
			throw new InvalidInputException("L'inventaire n'existe pas.");
		}

		Drug drug = inventory.findDrug(code);
		if(drug == null) {
			throw new InvalidInputException("Le médicament n'existe pas.");
		}

		try {
			drug.delete();
			DMSPersistence.save(dms);
		}
		catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void register(String username, String password, String role) throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		DMS dms = DMSApplication.getDMS();

		if(currentUserRole == null) {
			throw new InvalidInputException("Aucun utilisateur n'est connecté.");
		}

		if(currentUserRole instanceof Cashier || currentUserRole instanceof Pharmacist) {
			throw new InvalidInputException("Vous n'avez pas les droits nécessaires pour cette opération.");
		}
		if(password == null || password.equals("")) {
			throw new InvalidInputException("Le mot de passe ne peut être vide.");
		}

		UserRole userRole;
		switch(role) {
		case "Pharmacist":
			userRole = new Pharmacist(password, dms);
			break;
		case "Cashier":
			userRole = new Cashier(password, dms);
			break;
		case "Administrator":
			userRole = new Administrator(password, dms);
			break;
		default:
			throw new InvalidInputException("Le statut n'est pas valide.");
		}

		try {
			dms.addUser(username, userRole);
			DMSPersistence.save(dms);
		} catch (RuntimeException e) {
			if (e.getMessage().equals("Cannot create due to duplicate username")) {
				throw new InvalidInputException("Un compte est déjà associé à ce nom d'utilisateur.");
			}
			userRole.delete();
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void login(String username, String password) throws InvalidInputException {
		if(username == null || username.equals("")) {
			throw new InvalidInputException("Le nom d'utilisateur ne peut être vide");
		}
		if(password == null || password.equals("")) {
			throw new InvalidInputException("Le mot de passe ne peut être vide.");
		}
		if (DMSApplication.getCurrentUserRole() != null) {
			throw new InvalidInputException("Un utilisateur est déjà connecté.");
		}

		User user = User.getWithUsername(username);

		if (user == null) {
			throw new InvalidInputException("Le nom d'utilisateur et le mot de passe ne correspondent pas.");
		}

		if(user.getUsername().equals(username) && user.getUserRole().getPassword().equals(password)) {
			DMSApplication.setCurrentUserRole(user.getUserRole());
		}
		else {
			throw new InvalidInputException("Le nom d'utilisateur et le mot de passe ne correspondent pas.");
		}
	}

	public static void logout() {
		DMSApplication.setCurrentUserRole(null);
	}

	public static void deleteUser(String username) throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();

		if(currentUserRole == null) {
			throw new InvalidInputException("Aucun utilisateur n'est connecté.");
		}

		if(currentUserRole instanceof Cashier || currentUserRole instanceof Pharmacist) {
			throw new InvalidInputException("Vous n'avez pas les droits nécessaires pour cette opération.");
		}

		User user = User.getWithUsername(username);
		if(user == null) {
			throw new InvalidInputException("L'utilisateur n'existe pas.");
		}

		try {
			user.delete();
			DMSPersistence.save(DMSApplication.getDMS());
		}
		catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void changePassword(String username, String newPassword) throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();

		if(currentUserRole == null) {
			throw new InvalidInputException("Aucun utilisateur n'est connecté.");
		}

		if(currentUserRole instanceof Cashier || currentUserRole instanceof Pharmacist) {
			throw new InvalidInputException("Vous n'avez pas les droits nécessaires pour cette opération.");
		}

		User user = User.getWithUsername(username);
		if(user == null) {
			throw new InvalidInputException("L'utilisateur n'existe pas.");
		}

		try {
			user.getUserRole().setPassword(newPassword);
			DMSPersistence.save(DMSApplication.getDMS());
		}
		catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void addInventory(char firstLetter) throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		DMS dms = DMSApplication.getDMS();

		if(currentUserRole == null) {
			throw new InvalidInputException("Aucun utilisateur n'est connecté.");
		}

		if(currentUserRole instanceof Cashier || currentUserRole instanceof Pharmacist) {
			throw new InvalidInputException("Vous n'avez pas les droits nécessaires pour cette opération.");
		}

		try {
			dms.addInventory(firstLetter);
			DMSPersistence.save(dms);
		}
		catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void deleteInventory(char firstLetter) throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		DMS dms = DMSApplication.getDMS();

		if(currentUserRole == null) {
			throw new InvalidInputException("Aucun utilisateur n'est connecté.");
		}

		if(currentUserRole instanceof Cashier || currentUserRole instanceof Pharmacist) {
			throw new InvalidInputException("Vous n'avez pas les droits nécessaires pour cette opération.");
		}

		Inventory inventory = dms.findInventory(firstLetter);
		if(inventory == null) {
			throw new InvalidInputException("L'inventaire n'existe pas.");
		}

		try {
			inventory.delete();
			DMSPersistence.save(dms);
		}
		catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void addToReceipt(String code) throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		DMS dms = DMSApplication.getDMS();
		Receipt currentReceipt = DMSApplication.getCurrentReceipt();
		if(currentUserRole == null) {
			throw new InvalidInputException("Aucun utilisateur n'est connecté.");
		}

		Drug drug = Drug.getWithCode(code);

		if(drug == null) {
			throw new InvalidInputException("Le médicament n'existe pas");
		}

		if(currentReceipt == null) {
			currentReceipt = new Receipt(drug.getPrice(), dms);
			DMSApplication.setCurrentReceipt(currentReceipt);
			currentReceipt.addDrug(drug);
		}
		else {
			currentReceipt.addDrugAllowDuplicates(drug);
			currentReceipt.setTotalPrice(currentReceipt.getTotalPrice() + drug.getPrice());
		}
	}

	public static void deleteFromReceipt(String code) throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		Receipt currentReceipt = DMSApplication.getCurrentReceipt();
		if(currentUserRole == null) {
			throw new InvalidInputException("Aucun utilisateur n'est connecté.");
		}

		Drug drug = Drug.getWithCode(code);

		if(drug == null) {
			throw new InvalidInputException("Le médicament n'existe pas");
		}

		currentReceipt.removeAllInstancesOfDrug(drug);
	}

	public static void printReceipt() throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		Receipt currentReceipt = DMSApplication.getCurrentReceipt();
		if(currentUserRole == null) {
			throw new InvalidInputException("Aucun utilisateur n'est connecté.");
		}

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Duplex\\Bureau\\files\\samplefile1.txt"));
			
			writer.write(ReceiptTemplate.RECEIPT_TOP_TEMPLATE);

			for(Drug drug : currentReceipt.getDrugs()) {
				writer.write("  "+String.format("%1$-" + 15 + "s", drug.getName())+String.format("%1$-" + 23 + "s", drug.getCode())+drug.getPrice()+"\n\n");
			}
			
			writer.write("  Total : "+String.format( "%.2f", currentReceipt.getTotalPrice())+"\n");
			
			writer.write(ReceiptTemplate.RECEIPT_BOT_TEMPLATE);
			
			writer.close();
		}
		catch(IOException i) {
			throw new InvalidInputException(i.getMessage());
		}

		//printOnPrinter();
	}

	public static void deleteReceipt() throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();

		if(currentUserRole == null) {
			throw new InvalidInputException("Aucun utilisateur n'est connecté.");
		}

		DMSApplication.setCurrentReceipt(null);
	}

	//Query Methods
	public static TOInventory getInventoryWithFirstLetter(char firstLetter) throws InvalidInputException {
		DMS dms = DMSApplication.getDMS();

		if(firstLetter < 'A' || firstLetter > 'Z') {
			throw new InvalidInputException("L'inventaire n'existe pas.");
		}

		Inventory inventory = dms.findInventory(firstLetter);
		if(inventory == null) {
			throw new InvalidInputException("L'inventaire n'existe pas.");
		}
		TOInventory toInventory = new TOInventory();

		for(Drug drug : inventory.getDrugs()) {
			new TODrug(drug.getName(),
					drug.getPrice(),
					drug.getConcentration(),
					drug.getUnit(),
					drug.getInHandQuantity(),
					drug.getOrderedQuantity(),
					drug.getMinQuantity(),
					drug.getCode(),
					toInventory);
		}

		return toInventory;
	}

	public static TOReceipt getCurrentTOReceipt() throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		Receipt currentReceipt = DMSApplication.getCurrentReceipt();

		if(currentUserRole == null) {
			throw new InvalidInputException("Aucun utilisateur n'est connecté.");
		}

		TOReceipt toReceipt = new TOReceipt();
		if(currentReceipt != null) {
			for(Drug drug : currentReceipt.getDrugs()) {
				toReceipt.addTOScannedItem(drug.getName(), drug.getCode(), drug.getPrice());
			}
		}

		return toReceipt;
	}

	//Helper Methods
	private static void printOnPrinter() {
		ArrayList<String> listFileContents = new ArrayList<String>();

		try {
			// Get the latest file in folder,  Open the file:
			FileInputStream fstream = new FileInputStream("D:\\Duplex\\Bureau\\files\\samplefile1.txt");

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				listFileContents.add(strLine);
			}
			//Close the input stream
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage(), "File I/O Error", JOptionPane.ERROR_MESSAGE);
		}
        
        // Make a String out of the Arraylist<String>:
        String printData = "";
        Iterator<String> it =  listFileContents.iterator();
        while (it.hasNext()) {
            printData = printData + it.next();
            printData = printData + "\r\n";
        }

        // Feed the data to be printed to the PrinterJob instance:
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new OutputPrinter(printData));
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException e) {
                // Print job did not complete.
            }
        }
		
	}
}
