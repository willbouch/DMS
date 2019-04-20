package dms.persistence;

import dms.model.DMS;

public class DMSPersistence {
	
	private static String filename = "data.DMS";
	
	public static void save(DMS dms) {
		PersistenceObjectStream.serialize(dms);
	}

	public static DMS load() {
		PersistenceObjectStream.setFilename(filename);
		DMS DMS = (DMS) PersistenceObjectStream.deserialize();
		
		if (DMS == null) {
			DMS = new DMS();
		}
		else {
			DMS.reinitialize();
		}
		return DMS;
	}
	
	public static void setFilename(String newFilename) {
		filename = newFilename;
	}

}