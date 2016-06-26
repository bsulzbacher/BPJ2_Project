package model;

public class Schadensfall {
	private int idSchadensfall;
	private String geschädigter;
	private String adresse;
	
	
	
	public Schadensfall(int idSchadensfall, String geschädigter, String adresse) {
		super();
		this.idSchadensfall = idSchadensfall;
		this.geschädigter = geschädigter;
		this.adresse = adresse;
	}
	public int getIdSchadensfall() {
		return idSchadensfall;
	}
	public String getGeschädigter() {
		return geschädigter;
	}

	public String getAdresse() {
		return adresse;
	}
	@Override
	public String toString() {
		return idSchadensfall+"-"+geschädigter+"-"+adresse;
	}

	
	
}
