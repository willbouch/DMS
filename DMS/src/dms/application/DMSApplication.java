package dms.application;

import dms.model.Administrator;
import dms.model.DMS;
import dms.model.Receipt;
import dms.model.UserRole;
import dms.persistence.DMSPersistence;
import dms.view.DMSPage;

public class DMSApplication {

	private static DMS dms = null;
	private static UserRole currentUserRole = null;
	private static Receipt currentReceipt = null;

	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {
				javafx.application.Application.launch(DMSPage.class);
			}
		}.start();
		dms = getDMS();
		try {
			dms.addUser("wbouchard", new Administrator("11111111", dms));
		}catch(RuntimeException e) {}

		if(dms.getInventories().isEmpty()) {
			for(int i = 65; i < 91; ++i) {
				dms.addInventory((char)i);
			}
		}
		try {
			dms.getInventory(0).addDrug("A", 13.9, 13.7, "ml", 12, 14, "123124");
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

	}

	public static DMS getDMS() {
		if(dms == null) {
			//We load the persistence
			dms = DMSPersistence.load();
		}
		return dms;
	}

	public static DMS resetDMS() {
		if(dms != null) {
			dms.delete();
		}
		
		dms = DMSPersistence.load();
		return dms;
	}
	
	public static UserRole getCurrentUserRole() {
		return currentUserRole;
	}
	
	public static Receipt getCurrentReceipt() {
		return currentReceipt;
	}
	
	public static void setCurrentReceipt(Receipt receipt) {
		currentReceipt = receipt;
	}

	public static void setCurrentUserRole(UserRole aRole) {
		currentUserRole = aRole;
	}
}
