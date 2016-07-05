package utils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.util.Callback;
import model.Kostenvoranschlag;
import model.KvItem;
import model.Material;
import model.Mitarbeiter;
import model.Person;
import model.Rechnung;
import model.Schadensfall;

public class ConnectionHelper {
	//JDBC Datenbank Connection
	private Connection connection;
	
	public ConnectionHelper() throws ClassNotFoundException, IOException, SQLException{
		connection = DBConnection.getConnection();
	}
	
	//Auslesen der Stammdaten aus der Datenbank, f�r die Authentifizierung bei der Anmeldung
	public Mitarbeiter checkLoginDaten(String benutzername, String passwort) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT idPerson, vorname, name, email FROM Stammdaten where PersonalNr = ? and PW = ?");
		stmt.setString(1,benutzername);
		stmt.setString(2, passwort);
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
		if(mitarbeiter != null) {
		setLastLogin(mitarbeiter);
	}
	return mitarbeiter;
}

	public void setLastLogin(Mitarbeiter mitarbeiter) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("UPDATE Stammdaten set last_log = ? where idPerson = ?");
		
		Date CreatedDate= new Date(System.currentTimeMillis());
		java.util.Date d = new java.util.Date();
		long t = d.getTime();
		Timestamp time = new Timestamp(t);
		statement.setTimestamp(1, time);
		statement.setInt(2, mitarbeiter.getMitarbeiter_ID());
		//String query ="UPDATE Stammdaten set last_log = " + time + " where idPerson = " + mitarbeiter.getMitarbeiter_ID(); 
		//System.out.println(query);
		statement.executeUpdate();
	}
	
	public ArrayList<Mitarbeiter> getAllMitarbeiter() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT idPerson, vorname, name, email FROM Stammdaten WHERE Typ = 'Mitarbeiter' ");
		ResultSet rs= stmt.executeQuery();
		
		ArrayList<Mitarbeiter> allMAList = new ArrayList<Mitarbeiter>();
		
		while(rs.next()) {
			allMAList.add(new Mitarbeiter(rs.getString("vorname"),rs.getString("name"),rs.getInt("idPerson"),rs.getString("email")));
		}
		
		return allMAList;
	}
	
	public ArrayList<Schadensfall> getAllSchaeden() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT a.idSchadensfall,c.Vorname,c.Name, d.Strasse, d.Hnr, d.Ort, c.idPerson FROM Schadensfall a inner join  Geschaedigte b on a.idSchadensfall=b.SchadensfallID inner join Stammdaten c on b.PersonenId=c.idPerson inner join Adresse d on a.idAdresse=d.idAdresse");
		ResultSet rs= stmt.executeQuery();
		
		ArrayList<Schadensfall> allSchaedenList = new ArrayList<Schadensfall>();
		
		while(rs.next()) {
			int idSchaden=rs.getInt("idSchadensfall");
			String name=rs.getInt("idPerson") + ":" + rs.getString("Name")+" "+rs.getString("Vorname");
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
	
	//Chris Start
	
	/*public ArrayList<Rechnungen> getAllRechnungen() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT idRgn, bezahlt, sd.name, idSchadensfall FROM Rechnung rg join Stammdaten sd on rg.idEmpfaenger = sd.idPerson");
		ResultSet rs= stmt.executeQuery();
		
		ArrayList<Rechnungen> allRechnungList = new ArrayList<Rechnungen>();
		
		while(rs.next()) {
			int idRgn=rs.getInt("idRgn");
			String bezahlt=rs.getString("bezahlt");
			String name=rs.getString("name");
			int idSchadensfall=rs.getInt("idSchadensfall");
			allRechnungList.add(new Rechnungen(idRgn, bezahlt, name, idSchadensfall));
		}
		
		return allRechnungList;
	}*/
	
	//Chris Ende
	
	//HARY START
	public ArrayList<Kostenvoranschlag> getAllKostenvoranschlaege()throws SQLException {
		PreparedStatement stmt = connection.prepareStatement
				("Select kv.idKV as KVID,a.strasse,a.ort ,s.Name, sf.Beschreibung, sf.Schadendatum as Schadensdatum, sum(distinct m.VKPreis*v.verranschlagt) as Summe"
						+" from Schadensfall sf "
						+" join Geschaedigte g on sf.idSchadensfall = g.SchadensfallID "
						+" join Kostenvoranschlag kv on kv.idSchadensfall = sf.idSchadensfall" 
						+" join Stammdaten s on g.PersonenID = s.idPerson "
						+" join Verbrauch v on kv.idKV = v.idKV"
						+" join Material m on m.idMaterial = v.idMaterial "
						+" join Adresse a on sf.idAdresse=a.idAdresse"
						+" where kv.Freigegeben='nein'"
						+" group by  kv.idKV, sf.Beschreibung, sf.Schadendatum ;");
		ResultSet rs= stmt.executeQuery();
		
		ArrayList<Kostenvoranschlag> allKostenvoranschlaegeList = new ArrayList<Kostenvoranschlag>();
		
		while(rs.next()) {
			int idKV=rs.getInt("KVID");				//Korrekte Variablen!
			String beschreibung=rs.getString("name")+" - "+rs.getString("strasse")+" - "+rs.getString("ort");
			Date schadendatum=rs.getDate("Schadensdatum");
			double summe=rs.getDouble("Summe");
			//String status = rs.getString("Status");
			
			allKostenvoranschlaegeList.add(new Kostenvoranschlag(idKV, beschreibung, schadendatum, summe));
		}
		
		return allKostenvoranschlaegeList;
	}
	
	public ArrayList<KvItem> getAllKvItems(int KVid)throws SQLException {
		PreparedStatement stmt = connection.prepareStatement
				("SELECT mat.idMaterial, mat.Kategorie,  mat.materialName, mat.VKPreis, ver.verranschlagt FROM Material mat " 
						+ " inner join Verbrauch ver on mat.idMaterial=ver.idMaterial"
						+" where ver.idKV="+KVid+";");
		ResultSet rs= stmt.executeQuery();
		
		ArrayList<KvItem> allKvItems = new ArrayList<KvItem>();
		int counter=0;
		while(rs.next()) {
			counter++;
			int idMaterial=rs.getInt("idMaterial");				//Korrekte Variablen!
			String matName=rs.getString("Kategorie")+" "+rs.getString("materialName");
			double vkPreis=rs.getDouble("VKPReis");
			int anzahl=rs.getInt("verranschlagt");
			
			allKvItems.add(new KvItem(counter, idMaterial, matName, vkPreis, anzahl));
		}
		
		return allKvItems;
	}
	
	public double getKVsum(int KVid)throws SQLException {
		double kvSum=0;
		PreparedStatement stmt = connection.prepareStatement
				("Select sum(m.VKPreis*v.verranschlagt) as sum from Verbrauch v "
						+"inner join Material m on v.idMaterial=m.idMaterial where idKV="+KVid+";");
		ResultSet rs= stmt.executeQuery();
		while(rs.next()){
			kvSum=rs.getDouble("sum");
		}
		
		
		return kvSum;
	}

	//HARY ENDE
	
	
	public void writeKv(int idSchaden, ObservableList<KvItem> kvItems) throws SQLException{
		Statement wrKV = connection.createStatement();
		String query;
		query="insert into Kostenvoranschlag(idSchadensfall,verschickt,Freigegeben) values ("+idSchaden+",'nein','nein')"; 
		wrKV.executeUpdate(query);
		
		PreparedStatement read=connection.prepareStatement("SELECT distinct last_insert_id() FROM Sanierung.Kostenvoranschlag;");
		ResultSet rs=read.executeQuery();
		int kvId=0;
		while (rs.next()){
			kvId=rs.getInt("last_insert_id()");			
		}	
		
		for (KvItem i:kvItems){
			query="insert into Verbrauch (idSchadensfall,idMaterial,idKV,verranschlagt) values ("+idSchaden+","+i.getIdMaterial()+","+kvId+","+i.getAnzahl()+");";
			wrKV.executeUpdate(query);
		}
		}
		// HARY START
	public void updateKv(int idSchaden, String status) throws SQLException{
		Statement wrKV = connection.createStatement();			
		String query="update Kostenvoranschlag set Freigegeben='"+status.toString()+
				"' where idKV="+idSchaden+";"; 
		wrKV.executeUpdate(query);

	//HARY ENDE
		
	}

	public ArrayList<Person> getAllGeschaedigte() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT idPerson, vorname, name, email FROM Stammdaten WHERE Typ = 'Geschaedigte' ");
		ResultSet rs= stmt.executeQuery();
		
		ArrayList<Person> allGeschaedigteList = new ArrayList<Person>();
		
		while(rs.next()) {
			allGeschaedigteList.add(new Person(rs.getString("vorname"),rs.getString("name")));
		}
		
		return allGeschaedigteList;
	}
	
	public ArrayList<Material> getMaterialRechnung(int idSchadensfall) {
		ArrayList<Material> material = new ArrayList<Material>();
		//PreparedStatement stmt = connection.prepareStatement("SELECT idMaterial, materialName, VKPreis from Material where idMaterial in (select idMaterial from Verbrauch where idKV in (SELECT idKV from Kostenvoranschlag where idSchadensfall = 1 and freigegeben = 'ja'))");
		return material;
	}
	
	public ArrayList<Kostenvoranschlag> getKostenvoranschlaege(int idSchadensfall) throws SQLException {
		ArrayList<Kostenvoranschlag> kostenvoranschlaege = new ArrayList<Kostenvoranschlag>();
		PreparedStatement stmt = connection.prepareStatement
				("Select kv.idKV as KVID,a.strasse,a.ort ,s.Name, sf.Beschreibung, sf.Schadendatum as Schadensdatum, sum(distinct m.VKPreis*v.verranschlagt) as Summe"
						+" from Schadensfall sf "
						+" join Geschaedigte g on sf.idSchadensfall = g.SchadensfallID "
						+" join Kostenvoranschlag kv on kv.idSchadensfall = sf.idSchadensfall" 
						+" join Stammdaten s on g.PersonenID = s.idPerson "
						+" join Verbrauch v on kv.idKV = v.idKV"
						+" join Material m on m.idMaterial = v.idMaterial "
						+" join Adresse a on sf.idAdresse=a.idAdresse"
						+" where kv.Freigegeben='ja' and sf.idSchadensfall = ?"
						+" group by  kv.idKV, sf.Beschreibung, sf.Schadendatum ;");
		stmt.setInt(1, idSchadensfall);
		ResultSet rs= stmt.executeQuery();
		
		while(rs.next()) {
			int idKV=rs.getInt("KVID");
			String beschreibung=rs.getString("name")+" - "+rs.getString("strasse")+" - "+rs.getString("ort");
			Date schadendatum=rs.getDate("Schadensdatum");
			double summe=rs.getDouble("Summe");
			Kostenvoranschlag kv = new Kostenvoranschlag(idKV, beschreibung, schadendatum, summe);
			kostenvoranschlaege.add(kv);
		}
		return kostenvoranschlaege;
	}
	
	public void createRechnung(Rechnung rechnung) throws SQLException {
		Date CreatedDate= new Date(System.currentTimeMillis());
		java.util.Date d = new java.util.Date();
		long t = d.getTime();
		Timestamp time = new Timestamp(t);
		PreparedStatement statement = connection.prepareStatement("insert into Rechnung(idSchadensfall,verschickt,bezahlt, idEmpfaenger, export, bearbeitDat) values ("+rechnung.getSchadNr()+",'nein','nein',"+rechnung.getEmpf()+",'nein',?" + ")"); 
		statement.setTimestamp(1, time);
		statement.executeUpdate();
	}
}
