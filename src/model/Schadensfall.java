package model;

public class Schadensfall {
	private int idSchadensfall;
	private String geschaedigter;
	private String adresse;
	private int geschaedigterID;
	
	
	public Schadensfall(int idSchadensfall, String geschaedigter, String adresse) {
		super();
		this.idSchadensfall = idSchadensfall;
		String[] data = geschaedigter.split(":");
		this.geschaedigter = data[1];
		this.geschaedigterID = Integer.parseInt(data[0]);
		this.adresse = adresse;
	}
	public int getIdSchadensfall() {
		return idSchadensfall;
	}
	public String getGeschaedigter() {
		return geschaedigter;
	}

	public int getIDGeschaedigter() {
		return this.geschaedigterID;
	}
	
	public String getAdresse() {
		return adresse;
	}
	@Override
	public String toString() {
		return idSchadensfall+"-"+geschaedigter+"-"+adresse;
	}

	
	
}
