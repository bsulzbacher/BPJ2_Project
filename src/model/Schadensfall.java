package model;

public class Schadensfall {
	private int idSchadensfall;
	private String gesch�digter;
	private String adresse;
	
	
	
	public Schadensfall(int idSchadensfall, String gesch�digter, String adresse) {
		super();
		this.idSchadensfall = idSchadensfall;
		this.gesch�digter = gesch�digter;
		this.adresse = adresse;
	}
	public int getIdSchadensfall() {
		return idSchadensfall;
	}
	public String getGesch�digter() {
		return gesch�digter;
	}

	public String getAdresse() {
		return adresse;
	}
	@Override
	public String toString() {
		return idSchadensfall+"-"+gesch�digter+"-"+adresse;
	}

	
	
}
