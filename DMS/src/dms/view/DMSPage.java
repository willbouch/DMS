package dms.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DMSPage extends Application {
	private static Stage primaryStage;
	
	private static Scene loginScene;
	private static Scene mainScene;
	
	public final static double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
	public final static double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
	public final static double WINDOW_WIDTH = SCREEN_WIDTH / 1.5;
	public final static double WINDOW_HEIGHT = SCREEN_HEIGHT / 1.5;
	public final static double HBOX_SPACING = WINDOW_WIDTH / 100;
	public final static double VBOX_SPACING = WINDOW_HEIGHT / 50;
	public final static double INFORMATION_WINDOW_WIDTH = 220;
	public final static double INFORMATION_WINDOW_HEIGHT = 270;
	public final static double MANAGEMENT_WINDOW_WIDTH = 350;
	public final static double MANAGEMENT_WINDOW_HEIGHT = 370;
	public final static double DELETION_WINDOW_WIDTH = 370;
	public final static double DELETION_WINDOW_HEIGHT = 135;
	public final static double ADDING_WINDOW_WIDTH = 420;
	public final static double ADDING_WINDOW_HEIGHT = 350;
	public final static double RECEIPT_HEIGHT = WINDOW_HEIGHT / 2;
	public final static double RECEIPT_WIDTH = WINDOW_WIDTH / 3;
	public final static double NUMERICAL_TEXTFIELD_WIDTH = 50;
	public final static double ARROW_SIZE = 40;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		DMSPage.primaryStage = primaryStage;
		primaryStage.show();
		primaryStage.setTitle("DMS");
		//primaryStage.setResizable(false);
		primaryStage.setHeight(WINDOW_HEIGHT);
		primaryStage.setWidth(WINDOW_WIDTH);

		toMainScene();
		//primaryStage.getIcons().add(new Image("ca/mcgill/ecse223/block/view/resources/logo.jpg"));
	}
	
	public static void toLoginScene() {
		LoginPane loginPane = new LoginPane();
		loginScene = new Scene(loginPane);
		primaryStage.setScene(loginScene);
	}
	
	public static void toMainScene() {
		MainPane mainPane = new MainPane();
		mainScene = new Scene(mainPane);
		primaryStage.setScene(mainScene);
	}

	public static String getResource(String res) {
		return ClassLoader.getSystemResource(res).toString();
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}
}
