package dms.controller;

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
		
		//We add the drug to the inventory we found and we save the file
		try {
			inventory.addDrug(name, price, concentration, unit, inHandQuantity, minQuantity, code);
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
		
		if(currentReceipt == null) {
			dms.addReceipt(drug.getPrice());
		}
		else {
			currentReceipt.addDrug(drug);
			currentReceipt.setTotalPrice(currentReceipt.getTotalPrice() + drug.getPrice());
		}
	}
	
	public static void deleteReceipt() throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		Receipt currentReceipt = DMSApplication.getCurrentReceipt();
		
		if(currentUserRole == null) {
			throw new InvalidInputException("Aucun utilisateur n'est connecté.");
		}
		
		try {
			currentReceipt.delete();
		}
		catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
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

	
}
