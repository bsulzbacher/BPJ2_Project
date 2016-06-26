package demo;



import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class keinlogin extends Application{
	private Controller sun_aid_controller;

	//
	// starten der Applikation durch Anzeige des Loginfensters
	//
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		sun_aid_controller =  new Controller();
		sun_aid_controller.showMainView(primaryStage);
	}
	
    public static void main(String[] args) {
        launch(args);
    }    
}
