package utils;

import java.io.IOException;
import java.sql.*;

import model.Mitarbeiter;

public class ConnectionHelper {

	private Connection connection;
	
	public ConnectionHelper() throws ClassNotFoundException, IOException, SQLException{
		connection = DBConnection.getConnection();
	}
	
	//Auslesen der Stammdaten aus der Datenbank, f�r die Authentifizierung bei der Anmeldung
	public Mitarbeiter checkLoginDaten(String benutzername, String passwort) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT idPerson, vorname, name, email FROM Stammdaten where email = ?");
		stmt.setString(1,benutzername);
		ResultSet rs= stmt.executeQuery();
		
		Mitarbeiter mitarbeiter = null;
		int id = 0;
		String vorname = "";
		String nachname = "";
		String email = "";
		while(rs.next()) {
			id = rs.getInt("idPerson");
			vorname = rs.getString("vorname");
			nachname = rs.getString("name");
			email = rs.getString("email");
		}
		if(id != 0 && vorname.length() != 0 && nachname.length() != 0 && email.length() != 0)
			mitarbeiter = new Mitarbeiter(vorname, nachname, id, email);
		return mitarbeiter;
	}
	
	
}
