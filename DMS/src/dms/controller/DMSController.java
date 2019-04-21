package dms.controller;

import dms.application.DMSApplication;
import dms.model.Administrator;
import dms.model.Cashier;
import dms.model.DMS;
import dms.model.Drug;
import dms.model.Inventory;
import dms.model.Pharmacist;
import dms.model.User;
import dms.model.UserRole;
import dms.persistence.DMSPersistence;

public class DMSController {
	
	//Modifier Methods

	public static void addDrug(String name, double price, double concentration, String unit, int inHandQuantity, int minQuantity) throws InvalidInputException{
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		DMS dms = DMSApplication.getDMS();
		
		if(currentUserRole instanceof Cashier) {
			throw new InvalidInputException("Vous n'avez pas les droits n�cessaires pour cette op�ration.");
		}

		//Change the "structure" of the name
		name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
		
		Inventory inventory = dms.findInventory(name.charAt(0));
		if(inventory == null) {
			throw new InvalidInputException("L'inventaire n'existe pas.");
		}
		
		//We add the drug to the inventory we found and we save the file
		try {
			inventory.addDrug(name, price, concentration, unit, inHandQuantity, minQuantity);
			DMSPersistence.save(dms);
		}
		catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}

	public static void updateDrug(String name, int id, int newInHandQuantity, int newMinQuantity, double newPrice) throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		DMS dms = DMSApplication.getDMS();
		
		if(currentUserRole instanceof Cashier) {
			throw new InvalidInputException("Vous n'avez pas les droits n�cessaires pour cette op�ration.");
		}
		
		Inventory inventory = dms.findInventory(name.charAt(0));
		if(inventory == null) {
			throw new InvalidInputException("L'inventaire n'existe pas.");
		}
		
		Drug drug = inventory.findDrug(id);
		if(drug == null) {
			throw new InvalidInputException("Le m�dicament n'existe pas.");
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

	public static void deleteDrug(String name, int id) throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		DMS dms = DMSApplication.getDMS();
		
		if(currentUserRole instanceof Cashier) {
			throw new InvalidInputException("Vous n'avez pas les droits n�cessaires pour cette op�ration.");
		}
		
		Inventory inventory = dms.findInventory(name.charAt(0));
		if(inventory == null) {
			throw new InvalidInputException("L'inventaire n'existe pas.");
		}
		
		Drug drug = inventory.findDrug(id);
		if(drug == null) {
			throw new InvalidInputException("Le m�dicament n'existe pas.");
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
		
		if(currentUserRole instanceof Cashier || currentUserRole instanceof Pharmacist) {
			throw new InvalidInputException("Vous n'avez pas les droits n�cessaires pour cette op�ration.");
		}
		if(password == null || password.equals("")) {
			throw new InvalidInputException("Le mot de passe ne peut �tre vide.");
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
				throw new InvalidInputException("Un compte est d�j� associ� � ce nom d'utilisateur.");
			}
			userRole.delete();
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void login(String username, String password) throws InvalidInputException {
		if(username == null || username.equals("")) {
			throw new InvalidInputException("Le nom d'utilisateur ne peut �tre vide");
		}
		if(password == null || password.equals("")) {
			throw new InvalidInputException("Le mot de passe ne peut �tre vide.");
		}
		if (DMSApplication.getCurrentUserRole() != null) {
			throw new InvalidInputException("Un utilisateur est d�j� connect�.");
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
		DMS dms = DMSApplication.getDMS();
		
		if(currentUserRole instanceof Cashier || currentUserRole instanceof Pharmacist) {
			throw new InvalidInputException("Vous n'avez pas les droits n�cessaires pour cette op�ration.");
		}

		User user = User.getWithUsername(username);
		if(user == null) {
			throw new InvalidInputException("L'utilisateur n'existe pas.");
		}
		
		try {
			user.delete();
			DMSPersistence.save(dms);
		}
		catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	public static void changePassword(String username, String newPassword) throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		DMS dms = DMSApplication.getDMS();
		
		if(currentUserRole instanceof Cashier || currentUserRole instanceof Pharmacist) {
			throw new InvalidInputException("Vous n'avez pas les droits n�cessaires pour cette op�ration.");
		}
		
		User user = User.getWithUsername(username);
		if(user == null) {
			throw new InvalidInputException("L'utilisateur n'existe pas.");
		}
		
		try {
			user.getUserRole().setPassword(newPassword);
			DMSPersistence.save(dms);
		}
		catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	public static void addInventory(char firstLetter) throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		DMS dms = DMSApplication.getDMS();
		
		if(currentUserRole instanceof Cashier || currentUserRole instanceof Pharmacist) {
			throw new InvalidInputException("Vous n'avez pas les droits n�cessaires pour cette op�ration.");
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
		
		if(currentUserRole instanceof Cashier || currentUserRole instanceof Pharmacist) {
			throw new InvalidInputException("Vous n'avez pas les droits n�cessaires pour cette op�ration.");
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

	
	//Query Methods
	public static TOInventory getInventoryWithFirstLetter(char firstLetter) throws InvalidInputException {
		DMS dms = DMSApplication.getDMS();
		
		if(firstLetter < 'A' || firstLetter > 'Z') {
			throw new InvalidInputException("L'inventaire n'existe pas.");
		}
		
		Inventory inventory = dms.findInventory(firstLetter);
		TOInventory toInventory = new TOInventory();
		
		for(Drug drug : inventory.getDrugs()) {
			new TODrug(drug.getName(),
					drug.getPrice(),
					drug.getConcentration(),
					drug.getUnit(),
					drug.getInHandQuantity(),
					drug.getOrderedQuantity(),
					drug.getMinQuantity(),
					drug.getId(),
					toInventory);
		}
		
		return toInventory;
	}

	public static TODrug getSelectedDrug(TOInventory toInventory, int id) throws InvalidInputException {		
		for(TODrug toDrug : toInventory.getTODrugs()) {
			if(toDrug.getId() == id) {
				return toDrug;
			}
		}
		//Should never reach this return statement
		return null;
	}
}
