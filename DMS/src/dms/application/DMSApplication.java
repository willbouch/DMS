package dms.application;

import dms.model.DMS;
import dms.model.UserRole;

public class DMSApplication {
	
	private static DMS dms = null;
	private static UserRole currentUserRole = null;
	
	public static void main(String[] args) {
		
	}
	
	public static DMS getDMS() {
		if(dms == null) {
			//We load the persistence
			//TODO
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
