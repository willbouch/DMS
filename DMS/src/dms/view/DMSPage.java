package dms.view;

import java.awt.Toolkit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DMSPage extends Application {
	private static Stage primaryStage;
	
	private static Scene loginScene;
	private static Scene mainScene;
	
	public final static double SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public final static double SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public final static double HBOX_SPACING = SCREEN_WIDTH / 200;
	public final static double VBOX_SPACING = SCREEN_HEIGHT / 100;
	public final static double INFORMATION_WINDOW_WIDTH = SCREEN_WIDTH / 10;
	public final static double INFORMATION_WINDOW_HEIGHT = SCREEN_HEIGHT / 6;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		DMSPage.primaryStage = primaryStage;
		
		primaryStage.show();
		primaryStage.setTitle("DMS");
		primaryStage.setResizable(false);
		primaryStage.setHeight(SCREEN_HEIGHT/2);
		primaryStage.setWidth(SCREEN_WIDTH/2);
		
		toMainScene();
		//primaryStage.getIcons().add(new Image("ca/mcgill/ecse223/block/view/resources/logo.jpg"));
	}
	
	public static void toLoginScene() {
		LoginPane loginPane = new LoginPane();
		loginScene = new Scene(loginPane);
		primaryStage.setScene(loginScene);
	}
	
	public static void toMainScene() {
		InventoryPane p = new InventoryPane();
		mainScene = new Scene(p);
		primaryStage.setScene(mainScene);
	}

	public static String getResource(String res) {
		return ClassLoader.getSystemResource(res).toString();
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}
}
