package controller;

import java.io.IOException;
import java.sql.SQLException;



import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.stage.Stage;
import model.Kostenvoranschlag;
import model.KvItem;
import model.Mitarbeiter;
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
		main.showBenutzerdaten(mitarbeiter.getMitarbeiterBezeichnung());
		//Bei Klick auf den Testbutton wird die Funktion aus der Klasse
		//MainView.java aufgerufen, um eine Inhalt im mittleren Bereich des Fensters anzuzeigen
		// Hinzufügen eines Events bei Klick auf den Button "Anmelden
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
			final Alert alert = new Alert(AlertType.INFORMATION);
			main.getKvList().clear();
			main.setGesamtSumKv(0);
			main.setKostenvoranschlagList(FXCollections.observableArrayList(connection.getAllKostenvoranschlaege()));
			main.auftragserteilung();

			main.getKvBox().getSelectionModel().selectedItemProperty().addListener(
					new ChangeListener<Kostenvoranschlag>(){

						public void changed(ObservableValue<? extends Kostenvoranschlag> observable,
								Kostenvoranschlag oldValue, Kostenvoranschlag newValue) {
							try {
								main.getKvList().setAll(connection.getAllKvItems(newValue.getIdKV()));
								main.setGesamtSumKv(connection.getKVsum(newValue.getIdKV()));
								main.refreshKVsum();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
			
			main.getAuftErt().setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					if (main.getKvBox().getSelectionModel().getSelectedItem()!=null){
						try {
							connection.updateKv(main.getKvBox().getSelectionModel().getSelectedItem().getIdKV(),
									"ja");
							alert.setContentText("Die Beauftragung ist erfogt!");
					        alert.setTitle("Bearbeitung durchgeführt");
					        alert.setHeaderText("");
					        alert.showAndWait();
					        erteileAuftrag(main);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else {
						alert.setContentText("Kein Schadensfall ausgewählt");
				        alert.setTitle("Fehler");
				        alert.setHeaderText("Fehler");
				        alert.showAndWait();
					}
						
					
				}	
			});
			
			main.getAuftStorn().setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
					if (main.getKvBox().getSelectionModel().getSelectedItem()!=null){
						try {
							connection.updateKv(main.getKvBox().getSelectionModel().getSelectedItem().getIdKV(),
									"abgelehnt");
							alert.setContentText("Der Auftrag wurde storniert!");
					        alert.setTitle("Bearbeitung durchgeführt");
					        alert.setHeaderText("");
					        alert.showAndWait();
					        erteileAuftrag(main);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else {
						alert.setContentText("Kein Schadensfall ausgewählt");
				        alert.setTitle("Fehler");
				        alert.setHeaderText("Fehler");
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
					   
				        alert.setContentText("Material auswählen!");
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
					alert.setContentText("Schadensfall auswählen!");
			        alert.setTitle("Fehler");
			        alert.setHeaderText("EingabeFehler Schadensfall");
			        alert.showAndWait();
				}
			}
		}); 
	}

	private void erfasseSchadensfall(MainView main) throws SQLException {
		// TODO Philipp
		main.setMAList(FXCollections.observableArrayList(connection.getAllMitarbeiter()));
		main.zeichneSchadenfallerfassung();
	}
}

