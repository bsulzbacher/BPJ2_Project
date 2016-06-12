package view;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Mitarbeiter;

public class LoginView{
	private Button loginButton;
	private TextField txtBenutzername;
	private PasswordField txtPasswort;
	private Label errorMessage;
	
	
	//
	// Diese Methode erstellt das Loginfenster
	// Das Loginfenster besteht aus zwei Bereichen (Panels):
	// HBox Panel: Bereich für die Anzeige des Logos
	// GridPane Panel: Bereich für Benutzername und Passwort 
	//
	public void showLogin(Stage primaryStage) {
		primaryStage.setTitle("Anmelden");
		
		//erstellen BorderPane - Layout für das gesamte Fenster 
	    BorderPane bp = new BorderPane();
	    bp.setPadding(new Insets(10, 20, 10, 20));
	    bp.getStyleClass().add("login");
	    
        //erstellen HBox - Layout für den oberen Bereich des Loginfensters
	    // in diesem Panel (HBox) wird das Logo angezeigt
	    HBox hb = new HBox();
	    hb.setPadding(new Insets(2,2,2,3));
	    hb.setMinSize(400, 25);
	    	        
		//erstellen des Logos
        final ImageView selectedImage = new ImageView();   
        Image image1 = new Image(MainView.class.getResourceAsStream("../images/logo.jpg"));
        selectedImage.setImage(image1);
        selectedImage.setFitWidth(100);
        selectedImage.setFitHeight(25);
        //das Logo dem Panel (HBox) hinzufügen
        hb.getChildren().addAll(selectedImage);
        
        
	    // Layout für das Panel, in dem Benutzername und Passwort angezeigt werden
	    GridPane gridPane = new GridPane();
	    	        gridPane.setPadding(new Insets(20,20,20,20));
	    	        gridPane.setHgap(5);
	    	        gridPane.setVgap(5);
	    	        
	    // Erstellen der Eingabefelder und Labels
        errorMessage = new Label("");
        errorMessage.setId("error");
		Label benutzername = new Label("Benutzername");
		txtBenutzername = new TextField();
		txtBenutzername.setPrefWidth(200);
		Label passwort = new Label("Passwort");
		txtPasswort = new PasswordField();
		loginButton = new Button("Anmelden");
		
		// Hinzufügen der Eingabefelder und Labels in das Panel
		gridPane.add(errorMessage, 0, 0,2,1);
		gridPane.add(benutzername, 0, 1,1,1);
		gridPane.add(txtBenutzername, 1, 1,1,1);
		gridPane.add(passwort, 0, 2,1,1);
		gridPane.add(txtPasswort, 1, 2);
		gridPane.add(loginButton, 1, 3);
		
		//Panel für Logo (HBox) und Panel für Eingabefelder und Labels (GripPane) dem Loginfenster hinzufügen
		bp.setTop(hb);
		bp.setCenter(gridPane);
	    
		//Erstellen des Loginfensters für die Anzeige
		Scene scene = new Scene(bp, 400, 170);
        scene.getStylesheets().add
        //Einbinden des CSS
        (MainView.class.getResource("../style/style.css").toExternalForm());
	    primaryStage.setScene(scene);
	    //Loginfenster anzeigen
	    primaryStage.show();
	}
	
	//
	// Methode zum Anzeigen eines Fehlers, wenn die Eingabedaten falsch sind
	//
	public void showError() {
		errorMessage.setText("Benutzername oder Passwort falsch!");
		this.setBenutzername("");
		this.setPasswort("");
	}
	
	public Button getLoginButton(){
		return loginButton;
	}
	
	public String getBenutzername() {
		return txtBenutzername.getText();
	}
	
	public String getPasswort() {
		return txtPasswort.getText();
	}
	
	public void setBenutzername(String text) {
		txtBenutzername.setText(text);
	}
	
	public void setPasswort(String text) {
		txtPasswort.setText(text);
	}
}
