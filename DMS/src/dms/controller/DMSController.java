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

	public static void addDrug(String name, double price, double concentration, String unit, int inHandQuantity, int minQuantity) throws InvalidInputException{
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		DMS dms = DMSApplication.getDMS();
		
		if(currentUserRole instanceof Cashier) {
			throw new InvalidInputException("Vous n'avez pas les droits nécessaires pour cette opération.");
		}

		//Change the "structure" of the name
		name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
		
		Inventory inventory = dms.findInventory(name);
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
			throw new InvalidInputException("Vous n'avez pas les droits nécessaires pour cette opération.");
		}
		
		Inventory inventory = dms.findInventory(name);
		if(inventory == null) {
			throw new InvalidInputException("L'inventaire n'existe pas.");
		}
		
		Drug drug = inventory.findDrug(id);
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

	public static void deleteDrug(String name, int id) throws InvalidInputException {
		UserRole currentUserRole = DMSApplication.getCurrentUserRole();
		DMS dms = DMSApplication.getDMS();
		
		if(currentUserRole instanceof Cashier) {
			throw new InvalidInputException("Vous n'avez pas les droits nécessaires pour cette opération.");
		}
		
		Inventory inventory = dms.findInventory(name);
		if(inventory == null) {
			throw new InvalidInputException("L'inventaire n'existe pas.");
		}
		
		Drug drug = inventory.findDrug(id);
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
			throw new InvalidInputException("Cannot login a user while a user is already logged in.");
		}

		User user = User.getWithUsername(username);

		if (user == null) {
			throw new InvalidInputException("The username and password do not match.");
		}

		if(user.getUsername().equals(username) && user.getUserRole().getPassword().equals(password)) {
			DMSApplication.setCurrentUserRole(user.getUserRole());
		}
		else {
			throw new InvalidInputException("The username and password do not match.");
		}
	}

	public static void logout() {
		DMSApplication.setCurrentUserRole(null);
	}
}
