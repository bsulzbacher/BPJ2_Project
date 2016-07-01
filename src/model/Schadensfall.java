package model;

public class Schadensfall {
	private int idSchadensfall;
	private String geschaedigter;
	private String adresse;
	
	
	
	public Schadensfall(int idSchadensfall, String geschaedigter, String adresse) {
		super();
		this.idSchadensfall = idSchadensfall;
		this.geschaedigter = geschaedigter;
		this.adresse = adresse;
	}
	public int getIdSchadensfall() {
		return idSchadensfall;
	}
	public String getGeschaedigter() {
		return geschaedigter;
	}

	public String getAdresse() {
		return adresse;
	}
	@Override
	public String toString() {
		return idSchadensfall+"-"+geschaedigter+"-"+adresse;
	}

	
	
}
