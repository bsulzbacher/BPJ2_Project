package controller;

import java.io.IOException;
import java.sql.SQLException;



import java.util.ArrayList;



import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.stage.Stage;
import model.Adresse;
import model.KvItem;
import model.Mitarbeiter;
import model.Person;
import model.Schadensfall;
import model.Material;
import utils.ConnectionHelper;
import view.LoginView;
import view.MainView;

public class Controller {
	private int counterKvPos=0;
	private ConnectionHelper connection;
	private Mitarbeiter mitarbeiter;
	//private Kostenvoranschlag kostenvoranschlag;
	
	public Controller () throws ClassNotFoundException, IOException, SQLException {
		connection = new ConnectionHelper();
		mitarbeiter = null;
	}
	
	//
	// �berpr�fung der eingegebenen Logindaten 
	// Aufruf der Methode checkLoginDaten() aus der Klasse ConnectionHelper.java
	// R�ckgabewert: Objekt der Klasse Mitarbeiter
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
		
		// Hinzuf�gen eines Events bei Klick auf den Button "Anmelden
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
		main.showBenutzerdaten(mitarbeiter.getMitarbeiterBezeichnung());
		//Bei Klick auf den Testbutton wird die Funktion aus der Klasse
		//MainView.java aufgerufen, um eine Inhalt im mittleren Bereich des Fensters anzuzeigen
		// Hinzuf�gen eines Events bei Klick auf den Button "Anmelden
		main.getButtonErfassen().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				try {
					erfasseSchadensfall(main);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
       }); 
		main.getButtonKostenvoranschlag().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				try {
					erstelleKostenvoranschlag(main);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
       }); 
		main.getButtonAuftragserteilung().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				try {
					erteileAuftrag(main);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
       }); 
		main.getButtonRechnungserstellung().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				erstelleRechnung(main);
			}
       }); 
		
	
	}
	
	

	private void exportRechnung(MainView main) {
		// TODO Christian
		
	}

	protected void erstelleRechnung(MainView main) {
		// TODO Isabella
		
	}
	
	//HARY START
	private void erteileAuftrag(final MainView main) throws SQLException {
	
		main.getKostenvoranschlagList().clear();
		main.setKostenvoranschlagList(FXCollections.observableArrayList(connection.getAllKostenvoranschlaege()));
		// -> Br�uchte getAllKostenvoranschlaege in ConnectionHelper als Drop down
		//also eine setKvList(FX.Collections.observableArrayList(connection.getAllKostenvoranschlaege)
		// wie kann ich einstellen, dass ich die KVs nur im richtigen Status kriege?
	
		main.zeichneKostenvoranschlag(); // n�tig?
		final Alert alert = new Alert(AlertType.INFORMATION);

		
		main.getKvSubmit().setOnAction(new EventHandler<ActionEvent>() { //main.getErteileAuftrag
			public void handle(ActionEvent arg0) {
				Schadensfall sf=main.getSfBox().getSelectionModel().getSelectedItem();  //Auftrag statt Schadensfall // "sfBox?"
				
				if (sf!=null){
					if (!main.getKvList().isEmpty()){
						try {
							connection.writeKv(sf.getIdSchadensfall(), main.getKvList()); //writeAuftrag(sf.getKVID) ->
							alert.setContentText("Fertig");
							alert.setTitle("Auftrag erteilt"); //auftrag erteilt
							alert.setHeaderText("wurde in einen Auftrag umgewandelt!"); 
							alert.showAndWait();
							erteileAuftrag(main); // erstelleAuftrag(main)
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else{
						alert.setContentText("Probleme mit dem Auftrag!"); // Probleme mit dem Auftrag
						alert.setTitle("Fehler");
						alert.setHeaderText("Beim Admin melden");
						alert.showAndWait();
					}
					
				} else {
					alert.setContentText("Kostenvoranschlag ausw�hlen!");  //KV Ausw�hlen
			        alert.setTitle("Fehler");
			        alert.setHeaderText("EingabeFehler Kostenvoranschlag");
			        alert.showAndWait();
				}
			}
		}); 
	}		
//HARY ENDE

	private void erstelleKostenvoranschlag(final MainView main) throws SQLException {
		// TODO Max
		main.getKvList().clear();
		counterKvPos=0;
		main.setGesamtSumKv(0);
		main.setSfList(FXCollections.observableArrayList(connection.getAllSchaeden()));
		main.setMatList(FXCollections.observableArrayList(connection.getAllMaterial()));
		main.zeichneKostenvoranschlag();
		final Alert alert = new Alert(AlertType.INFORMATION);

		
		main.getButtonMatHinzu().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				
				Material neuMat=main.getMatBox().getSelectionModel().getSelectedItem();
				if (neuMat!=null){
					if(main.getAnzMat().getText().matches("^?\\d+$")){
						counterKvPos++;
						KvItem item=new KvItem(counterKvPos,neuMat.getIdMaterial(), neuMat.getMaterialName(), neuMat.getVkPreis(),Integer.parseInt(main.getAnzMat().getText()));
						main.getKvList().add(item);
						main.setGesamtSumKv(main.getGesamtSumKv()+item.getSumme());
						main.refreshKVsum();
			
					} else{
						alert.setContentText("Falscher Eingabewert der Materialanzahl!");
				        alert.setTitle("Fehler");
				        alert.setHeaderText("EingabeFehler Anzahl");
				        alert.showAndWait();
					}
					
				}else{
					   
				        alert.setContentText("Material ausw�hlen!");
				        alert.setTitle("Fehler");
				        alert.setHeaderText("EingabeFehler Material");
				        alert.showAndWait();
				}
			}
		}); 
		
		main.getKvSubmit().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				Schadensfall sf=main.getSfBox().getSelectionModel().getSelectedItem();
				
				if (sf!=null){
					if (!main.getKvList().isEmpty()){
						try {
							connection.writeKv(sf.getIdSchadensfall(), main.getKvList());
							alert.setContentText("Fertig");
							alert.setTitle("KV erstellt");
							alert.setHeaderText("Der Kostenvoranschlag wurde erstellt!");
							alert.showAndWait();
							erstelleKostenvoranschlag(main);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else{
						alert.setContentText("Keine KV-Positionen enthalten!");
						alert.setTitle("Fehler");
						alert.setHeaderText("EingabeFehler KV-Positionen");
						alert.showAndWait();
					}
					
				} else {
					alert.setContentText("Schadensfall ausw�hlen!");
			        alert.setTitle("Fehler");
			        alert.setHeaderText("EingabeFehler Schadensfall");
			        alert.showAndWait();
				}
			}
		}); 
	}

	private void erfasseSchadensfall(final MainView main) throws SQLException {
		// TODO Philipp
		
		main.getSchErfOkAndSave().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				final Alert alert = new Alert(AlertType.INFORMATION);
				Mitarbeiter bearbeitenderMA = main.getMitarbeiterComboBox().getSelectionModel().getSelectedItem();
				Adresse schAdresse = main.getSchadensadresseComboBox().getSelectionModel().getSelectedItem();
				Person geschaedigter  = main.getGeschaedigterComboBox().getSelectionModel().getSelectedItem();
				boolean fehlendeEingabedaten = false;
				
				if(!fehlendeEingabedaten && bearbeitenderMA == null){
					alert.setContentText("Kein Mitarbeiter angegeben!");
					fehlendeEingabedaten  = true;
				}
				
				if(!fehlendeEingabedaten && geschaedigter == null){
					alert.setContentText("Kein Geschaedigter angegeben!");
			        fehlendeEingabedaten = true;
				}
				
				if(!fehlendeEingabedaten && schAdresse == null){
					alert.setContentText("Keine Schadensfalladresse angegeben!");
					fehlendeEingabedaten  = true;
				}
				
				if(fehlendeEingabedaten){
				
			        alert.setTitle("Fehler");
			        alert.setHeaderText("Fehlende Eingabedaten!");
			        alert.showAndWait();
			        return;
				
		 	    }
			}
		});
		main.setMAList(FXCollections.observableArrayList(connection.getAllMitarbeiter()));
		main.setGSList(FXCollections.observableArrayList(connection.getAllGeschaedigte()));
		main.setAdresseList(FXCollections.observableArrayList(connection.getAllAdresses()));
		main.zeichneSchadenfallerfassung();
	}
}

