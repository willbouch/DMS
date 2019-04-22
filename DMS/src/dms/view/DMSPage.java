package dms.view;

import java.awt.Toolkit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DMSPage extends Application {
	private static Stage primaryStage;
	
	private static Scene loginScene;
	
	public final static double SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public final static double SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public final static double HBOX_SPACING = SCREEN_WIDTH / 200;
	public final static double VBOX_SPACING = SCREEN_HEIGHT / 100;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		primaryStage.show();
		primaryStage.setTitle("DMS");
		primaryStage.setResizable(false);
		primaryStage.setHeight(SCREEN_HEIGHT/2);
		primaryStage.setWidth(SCREEN_WIDTH/2);
		
		toLoginScene();
		//primaryStage.getIcons().add(new Image("ca/mcgill/ecse223/block/view/resources/logo.jpg"));
	}
	
	public static void toLoginScene() {
		LoginPane loginPane = new LoginPane();
		loginScene = new Scene(loginPane);
		primaryStage.setScene(loginScene);
		
	}

}
