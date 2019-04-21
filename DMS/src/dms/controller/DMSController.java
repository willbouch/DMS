package dms.controller;

import dms.application.DMSApplication;
import dms.model.Cashier;
import dms.model.DMS;
import dms.model.Drug;
import dms.model.Inventory;
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
}
