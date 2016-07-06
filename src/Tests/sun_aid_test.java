package Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import controller.Controller;
import model.Mitarbeiter;

public class sun_aid_test {

	@Test
	public void test() throws ClassNotFoundException, IOException, SQLException {
		Controller controller = new Controller();
		
		//checkLogin(String benutzername, String passwort)
		//Bei korrekten Übergabeparametern darf Mitarbeiter nicht null sein
		Mitarbeiter ma = controller.checkLogin("1234", "1234");
		assertNotNull(ma);
		
		//
		//
	}

}
