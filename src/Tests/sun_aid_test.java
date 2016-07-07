package Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.categories.TestFX;

import controller.Controller;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Adresse;
import model.Kostenvoranschlag;
import model.KvItem;
import model.Material;
import model.Mitarbeiter;
import model.Person;
import model.Rechnung;
import model.Rechnungsexport;
import model.Schadensfall;
import utils.DBConnection;
import view.MainView;

public class sun_aid_test {

	// Test Model
	
	@Test
	public void test_checkAdresse() throws ClassNotFoundException{
		Adresse a = new Adresse(1,"Strasse",1,8010,"Graz");
		assertEquals("Strasse 1, 8010 Graz",a.toString());
	}
	
	@Test
	public void test_checkKostenvoranschlag() throws ClassNotFoundException{
		
		Kostenvoranschlag kv = new Kostenvoranschlag(1,"TestkostenVS",new Date(2016,01,01),2000);
		assertEquals("1 - TestkostenVS - EUR 2000.0",kv.toString());
	}
	
	@Test
	public void test_checkMaterial() throws ClassNotFoundException{
		
		Material m = new Material(1,"testmat",100.0);
		assertEquals("testmat EUR 100.0",m.toString());
	}
	
	@Test
	public void test_checkMitarbeiterAndPerson() throws ClassNotFoundException{
		
		Mitarbeiter m = new Mitarbeiter("karl", "Test", 1, "karl@firma.at");
		assertEquals("1 Test",m.toString());
		assertEquals("karl",m.getVorname());
	}
	
	@Test
	public void test_checkRechnung() throws ClassNotFoundException{
		
		Rechnung r = new Rechnung(1,2,3);
		assertEquals("1-2",r.toString());
	}
	
	@Test
	public void test_checkRechnungsexport() throws ClassNotFoundException{
		
		Rechnungsexport r = new Rechnungsexport(1,"nein","testexport",3);
		r.setBezahlt("ja");
		assertEquals("ja",r.getBezahlt());
		assertEquals(1,r.getRgnNr());
	}
	
	@Test
	public void test_checkSchadensfall() throws ClassNotFoundException{
		
		Schadensfall s1 = new Schadensfall(1,"2:koarl","adresse1");
		Person p = new Person("koarl","testerl");
		assertEquals("1-koarl-adresse1",s1.toString());
		Schadensfall s2 = new Schadensfall(1,4,"Regenschaden","123A",1,"20160201","20160401","testschaden");
		assertEquals(1, s2.getIdMitarbeiter());
		assertNotSame(s1, s2);
	}
	
	// Test GUI/Login
	
	@Test
	public void test_checkLogin() throws ClassNotFoundException, IOException, SQLException {
		Controller controller = new Controller();
		
		//checkLogin(String benutzername, String passwort)
		//Bei korrekten �bergabeparametern darf Mitarbeiter nicht null sein
		Mitarbeiter ma = controller.checkLogin("1234", "1234");
		assertNotNull(ma);
		
		//
		//
	}
	
	// Test DB Connections and Model
	
	@Test
	public void test_checkDBconnect() throws ClassNotFoundException, SQLException, IOException {
		Connection c = DBConnection.getConnection();
		//pruefen der Datenbankverbindung
		assertNotNull(c);
		
		//
		//
	}
	
	@Test
	public void test_DBconnectionSchaeden() throws ClassNotFoundException, IOException, SQLException {
		Controller controller = new Controller();
		
		//Teste DB-connection:
		//Lese Schaeden aus Datenbank,
		// ok wenn >0 Schaeden retourniert werden 
		ArrayList<Schadensfall> schaeden = controller.getConnectionHelper().getAllSchaeden();
		assertNotNull(schaeden);
		assertTrue(schaeden.size() > 0 );
	}
	
	@Test
	public void test_DBconnectionMatListe() throws ClassNotFoundException, IOException, SQLException {
		Controller controller = new Controller();
		
		//Teste DB-connection:
		//Lese Schaeden aus Datenbank,
		// ok wenn >0 Schaeden retourniert werden 
		ArrayList<Schadensfall> schaeden = controller.getConnectionHelper().getAllSchaeden();
		assertNotNull(schaeden);
		assertTrue(schaeden.size() > 0 );
	}

	
	// Logic Tests
	
	// Test if KVItem-Class instances and calculates correctly
	@Test
	public void test_checkKVItem() throws ClassNotFoundException{
		
		KvItem kvi = new KvItem(1,1,"testmat",100,20);
		assertEquals(2000.0,kvi.getSumme(),0.0);
	}

	    @Test
	    public void testA() throws InterruptedException {
	        Thread thread = new Thread(new Runnable() {

	            @Override
	            public void run() {
	                new JFXPanel(); // Initializes the JavaFx Platform
	                Platform.runLater(new Runnable() {

	                    @Override
	                    public void run() {
	                    		Stage g = new Stage();
	                    		final MainView main = new MainView();
								main.showMainView(g);
								
								try {
									Controller c = new Controller();
									c.erfasseSchadensfall(main);
								} catch (ClassNotFoundException | IOException | SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								main.zeichneSchadenfallerfassung();
								
								main.getMitarbeiterComboBox().getSelectionModel().select(1);
								main.getGeschaedigterComboBox().getSelectionModel().select(1);
								main.getSchadensadresseComboBox().getSelectionModel().select(1);
								
								main.getSchadensartText().setText("Rohrbruch");
								//main.getSchErfOkAndSave().fire();
								

	                    }
	                });
	            }
	        });
	        thread.start();// Initialize the thread
	        Thread.sleep(5000); // Time to use the app, with out this, the thread
	                                // will be killed before you can tell.
	    }
	
}
