package dms.controller;

import dms.application.DMSApplication;
import dms.model.Cashier;
import dms.model.DMS;
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
		
		//Find the inventory according to first letter
		char firstLetter = name.charAt(0);
		int index = 0;
		for(Inventory inv : dms.getInventories()) {
			if(firstLetter == inv.getFirstLetter()) {
				break;
			}
			index++;
		}
		
		//We add the drug to the inventory we found and we save the file
		try {
			dms.getInventory(index).addDrug(name, price, concentration, unit, inHandQuantity, minQuantity);
			DMSPersistence.save(dms);
		}
		catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}

}
