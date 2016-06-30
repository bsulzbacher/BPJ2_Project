package model;

public class Kostenvoranschlag {
	private int idKV;
	private String schadenart;
	private double vkPreis;
	private int verranschlagt;
	private double summe;
	private String beschreibung;
	private String status;
	
	public Kostenvoranschlag(int idKV, String schadenart, double vkPreis, int verranschlagt, double summe,
			String beschreibung, String status) {
		super();
		this.idKV = idKV;
		this.schadenart = schadenart;
		this.vkPreis = vkPreis;
		this.verranschlagt = verranschlagt;
		summe = vkPreis*verranschlagt;
		this.beschreibung = beschreibung;
		this.status = status;
		summe = vkPreis*verranschlagt;
	}

	public int getIdKV() {
		return idKV;
	}

	public String getSchadenart() {
		return schadenart;
	}

	public double getVkPreis() {
		return vkPreis;
	}

	public int getVerranschlagt() {
		return verranschlagt;
	}

	public double getSumme() {
		return summe;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public String getStatus() {
		return status;
	}
	
	
	
	
	

}
