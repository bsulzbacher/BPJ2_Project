package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.Calendar;
import java.util.Date;
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
import model.Kostenvoranschlag;
import model.Adresse;
import model.KvItem;
import model.Mitarbeiter;
import model.Rechnung;
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
	
	public ConnectionHelper getConnectionHelper(){
		return connection;
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
				try {
					erstelleRechnung(main);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
       }); 
		
		main.getButtonRechnungsexport().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				try {
					exportRechnung(main);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
       }); 
	
	}
	
	private void exportRechnung(MainView main) throws SQLException {
		// TODO Christian
		main.setRgnList(FXCollections.observableArrayList(connection.getAllRechnungen()));
		main.zeichneRechnungsExport();
		
		main.getExport().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				try {
					connection.updateExport();
					
					exportRechnung(main);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				final Alert alert = new Alert(AlertType.INFORMATION);

				alert.setContentText("Export ist erfogt!");
		        alert.setTitle("Export durchgef�hrt");
		        alert.setHeaderText("");
		        alert.showAndWait();
				
			}
		});
		
	}

	private void erstelleRechnung(MainView main) throws SQLException {
       main.getKvList().clear();
       // main.getSfList().clear();
       // main.setKostenvoranschlagList(FXCollections.observableArrayList());
        ArrayList<Schadensfall> schadensfaelle = connection.getAllSchaeden();
        main.zeichneAuftragsabschluss();
        main.setSfList2(FXCollections.observableArrayList(schadensfaelle));
        main.getSfBox2().setItems(main.getSfList2());
		main.getSfBox2().getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Schadensfall>(){
					@Override
					public void changed(ObservableValue<? extends Schadensfall> observable, Schadensfall oldValue,
							Schadensfall newValue) {
						// TODO Auto-generated method stub
						try {
							ArrayList<Kostenvoranschlag> kv  = connection.getKostenvoranschlaege(newValue.getIdSchadensfall());
							main.setKostenvoranschlagList(FXCollections.observableArrayList(kv));
							main.getKvBox().setItems(main.getKostenvoranschlagList());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		main.getButtonMatHinzu().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				Schadensfall sf = main.getSfBox2().getSelectionModel().getSelectedItem();
				String kundendaten = sf.getGeschaedigter();
				kundendaten = kundendaten + "\n" + sf.getAdresse();
				System.out.println(kundendaten);
				main.setKundenDaten(kundendaten);
				Kostenvoranschlag kv = main.getKvBox().getSelectionModel().getSelectedItem();
				try {
					main.setGesamtSumKv(connection.getKVsum(kv.getIdKV()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				main.refreshKVsum();
				try {
					main.getKvList().setAll(connection.getAllKvItems(kv.getIdKV()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Rechnung rechnung = new Rechnung(-1, sf.getIDGeschaedigter(), sf.getIdSchadensfall());
				main.showRechnung();
				try {
					connection.createRechnung(rechnung);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});         
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
					        alert.setTitle("Bearbeitung durchgef�hrt");
					        alert.setHeaderText("");
					        alert.showAndWait();
					        erteileAuftrag(main);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else {
						alert.setContentText("Kein Schadensfall ausgew�hlt");
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
					        alert.setTitle("Bearbeitung durchgef�hrt");
					        alert.setHeaderText("");
					        alert.showAndWait();
					        erteileAuftrag(main);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else {
						alert.setContentText("Kein Schadensfall ausgew�hlt");
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

	public void erfasseSchadensfall(final MainView main) throws SQLException {
		// TODO Philipp
		
		main.getSchErfOkAndSave().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				final Alert alert = new Alert(AlertType.INFORMATION);
				Mitarbeiter bearbeitenderMA = main.getMitarbeiterComboBox().getSelectionModel().getSelectedItem();
				Adresse schAdresse = main.getSchadensadresseComboBox().getSelectionModel().getSelectedItem();
				Person geschaedigter  = main.getGeschaedigterComboBox().getSelectionModel().getSelectedItem();
				boolean fehlendeEingabedaten = false;
				String schadendatum;
				String anlagedatum;
				
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
				
				
				// Überprüfen, ob Datum stimmen:
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyyMMdd");
				Date schadensDatum = null;
				Date anlageDatum = null;
				
				alert.setTitle("Fehler");
		        alert.setHeaderText("Falsche Eingabedaten!");
				
				try {
					schadensDatum = dateFormat.parse(main.getSchadensdatumText().getText());
				}
				catch (ParseException pe) {
					alert.setContentText("Kein gültiges Datum als Schadensdatum angegeben!");
			        alert.showAndWait();
			        return;
				}
				
				try {
					anlageDatum = dateFormat.parse(main.getAnlagedatumText().getText());
				}
				catch (ParseException pe) {
					alert.setContentText("Kein gültiges Datum als Anlagedatum angegeben!");
			        alert.showAndWait();
			        return;
				}
				
				Calendar c = Calendar.getInstance();
				c.setTime(schadensDatum);
				System.out.println(main.getSchadensdatumText().getText());
				System.out.println(sqlFormat.format(c.getTime()));
				schadendatum = sqlFormat.format(c.getTime());
				
				c.setTime(anlageDatum);
				System.out.println(main.getAnlagedatumText().getText());
				System.out.println(sqlFormat.format(c.getTime()));
				anlagedatum = sqlFormat.format(c.getTime()); 
				
				Schadensfall sf = new Schadensfall(0,schAdresse.getIdAdresse(),main.getSchadensartText().getText(),
						 main.getExtSchadensnummerText().getText(),bearbeitenderMA.getMitarbeiter_ID(),anlagedatum,schadendatum,
						 main.getBeschreibungText().getText());
				
				try {
					connection.writeSchadensfall(sf, geschaedigter.getPersonID());
					alert.setTitle("Erfolg");
			        alert.setHeaderText("Speichern erfolgt.");
					alert.setContentText("Daten wurden in die Datenbank geschrieben!");
			        alert.showAndWait();
					main.zeichneSchadenfallerfassung();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		main.setMAList(FXCollections.observableArrayList(connection.getAllMitarbeiter()));
		main.setGSList(FXCollections.observableArrayList(connection.getAllGeschaedigte()));
		main.setAdresseList(FXCollections.observableArrayList(connection.getAllAdresses()));
		main.zeichneSchadenfallerfassung();
	}
}

