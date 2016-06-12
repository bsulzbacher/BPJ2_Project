package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.Mitarbeiter;
import utils.ConnectionHelper;
import view.LoginView;
import view.MainView;

public class Controller {
	private ConnectionHelper connection;
	private Mitarbeiter mitarbeiter;
	
	public Controller () throws ClassNotFoundException, IOException, SQLException {
		connection = new ConnectionHelper();
		mitarbeiter = null;
	}
	
	//
	// Überprüfung der eingegebenen Logindaten 
	// Aufruf der Methode checkLoginDaten() aus der Klasse ConnectionHelper.java
	// Rückgabewert: Objekt der Klasse Mitarbeiter
	//
	public Mitarbeiter checkLogin(String benutzername, String passwort) throws SQLException {
		Mitarbeiter mitarbeiter = null;
		if(benutzername.length() == 0 && passwort.length() == 0)
			return mitarbeiter;
		else {
			mitarbeiter = connection.checkLoginDaten(benutzername, passwort);
			return mitarbeiter;
		}
	}
	
	//
	// ruft die Methode showLogin() von der Klass LoginView.java auf
	//
	public void showLogin(final Stage stage) throws Exception {
		final LoginView login = new LoginView();
		login.showLogin(stage);
		
		// Hinzufügen eines Events bei Klick auf den Button "Anmelden
		login.getLoginButton().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				//Holen der eingegebenen Werte mit Getter-Methoden der Klasse LoginView.java
				String value_benutzername = login.getBenutzername();
				String value_passwort = login.getPasswort();
				try {
					// Aufruf der Methode checkLogin() aus der Klasse Controller.java
					mitarbeiter = checkLogin(value_benutzername, value_passwort);
					
					// wenn die Anmeldung erfolgreich ist, wird die Methode showMainView() 
					// von der Klasse MainView.java aufgerufen, um die Hauptview aufzurufen.
					// wenn die Anmeldung nicht erfolgreich ist, wird die Methode showError()
					// aufgerufen, die Eine Fehlermeldung im Loginfenster anzeigt.
					if(mitarbeiter != null) 
						showMainView(stage);
					else {
						login.showError();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
       });
	}
	
	// Anzeige des Hauptfensters, bei erfolgreicher Anmeldung
	public void showMainView(Stage stage) throws ClassNotFoundException, IOException, SQLException {
		final MainView main = new MainView();
		main.showMainView(stage);
		//Bei Klick auf den Testbutton wird die Funktion showTest() aus der Klasse
		//MainView.java aufgerufen, um eine Inhalt im mittleren Bereich des Fensters anzuzeigen
		// Hinzufügen eines Events bei Klick auf den Button "Anmelden
		main.getTestButton().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				main.showTest();
			}
       }); 
	}
}

