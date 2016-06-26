package utils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import model.Material;
import model.Mitarbeiter;
import model.Schadensfall;

public class ConnectionHelper {
	//JDBC Datenbank Connection
	private Connection connection;
	
	public ConnectionHelper() throws ClassNotFoundException, IOException, SQLException{
		connection = DBConnection.getConnection();
	}
	
	//Auslesen der Stammdaten aus der Datenbank, für die Authentifizierung bei der Anmeldung
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
		/*ArrayList<Mitarbeiter> list = new ArrayList<Mitarbeiter>();
		list.add(mitarbeiter);
		int i;
		for(i = 0; i < list.size(); i++) {
			Mitarbeiter ma = list.get(i);
		}*/
		if(id != 0 && vorname.length() != 0 && nachname.length() != 0 && email.length() != 0)
			mitarbeiter = new Mitarbeiter(vorname, nachname, id, email);
		return mitarbeiter;
	}
	
	public ArrayList<Mitarbeiter> getAllMitarbeiter() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT idPerson, vorname, name, email FROM Stammdaten");
		ResultSet rs= stmt.executeQuery();
		
		ArrayList<Mitarbeiter> allMAList = new ArrayList<Mitarbeiter>();
		
		while(rs.next()) {
			allMAList.add(new Mitarbeiter(rs.getString("vorname"),rs.getString("name"),rs.getInt("idPerson"),rs.getString("email")));
		}
		
		return allMAList;
	}
	
	public ArrayList<Schadensfall> getAllSchaeden() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT a.idSchadensfall,c.Vorname,c.Name, d.Strasse, d.Hnr, d.Ort FROM Schadensfall a inner join  Geschädigte b on a.idSchadensfall=b.SchadensfallID inner join Stammdaten c on b.PersonenId=c.idPerson inner join Adresse d on a.idAdresse=d.idAdresse");
		ResultSet rs= stmt.executeQuery();
		
		ArrayList<Schadensfall> allSchaedenList = new ArrayList<Schadensfall>();
		
		while(rs.next()) {
			int idSchaden=rs.getInt("idSchadensfall");
			String name=rs.getString("Name")+" "+rs.getString("Vorname");
			String adresse=rs.getString("Strasse")+" "+rs.getString("Hnr")+" "+rs.getString("Ort");
			
			allSchaedenList.add(new Schadensfall(idSchaden,name,adresse));
		}
		
		return allSchaedenList;
	}
	
	public ArrayList<Material> getAllMaterial() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT idMaterial,Kategorie,  materialName, VKPreis FROM Material;");
		ResultSet rs= stmt.executeQuery();
		
		ArrayList<Material> allMaterialList = new ArrayList<Material>();
		
		while(rs.next()) {
			int idMaterial=rs.getInt("idMaterial");
			String name=rs.getString("Kategorie")+" "+rs.getString("materialName");
			double vkPreis=rs.getDouble("VKPReis");
			
			allMaterialList.add(new Material(idMaterial,name,vkPreis));
		}
		
		return allMaterialList;
	}
	
	
}
