package dms.application;

import dms.model.DMS;
import dms.model.UserRole;
import dms.persistence.DMSPersistence;
import dms.view.DMSPage;

public class DMSApplication {
	
	private static DMS dms = null;
	private static UserRole currentUserRole = null;
	
	public static void main(String[] args) {
		new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(DMSPage.class);
            }
        }.start();
	}
	
	public static DMS getDMS() {
		if(dms == null) {
			//We load the persistence
			dms = DMSPersistence.load();
		}
		return dms;
	}
	
	public static UserRole getCurrentUserRole() {
		return currentUserRole;
	}
	
	public static void setCurrentUserRole(UserRole aRole) {
		currentUserRole = aRole;
	}
}
