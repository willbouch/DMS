package dms.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptTemplate {
	public static final String RECEIPT_TOP_TEMPLATE = "==============================================\n"
													+ "                Hôpital OFATMA                \n"
													+ "==============================================\n"
													+ "             "+getDateAndTime()+"             \n"
													+ "              Liste des Produits              \n"
													+ "                                              \n";

	public static final String RECEIPT_BOT_TEMPLATE = "                                              \n"
													+ "                     Merci                    \n"
													+ "==============================================";

	private static String getDateAndTime() {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
		String formattedDate = myDateObj.format(myFormatObj);
		
		return formattedDate;
	}
}
