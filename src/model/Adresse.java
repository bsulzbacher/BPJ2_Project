package model;

public class Adresse {
	private int idAdresse;
	private String Adresse;
	private int HNr;
	private int PLZ;
	private String Ort;
	
	
	public Adresse(int idAdresse, String adresse, int hNr, int pLZ, String ort) {
		super();
		this.idAdresse = idAdresse;
		Adresse = adresse;
		HNr = hNr;
		PLZ = pLZ;
		Ort = ort;
	}
	
	@Override
	public String toString() {
		return Adresse + " " + HNr + ", " + PLZ + " " + Ort ;
	}
	
	public int getIdAdresse() {
		return idAdresse;
	}
	
}
