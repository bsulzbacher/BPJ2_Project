package model;

import java.util.Date;

public class Kostenvoranschlag {
	private int idKV;
	//private String schadenart;
	//private double vkPreis;
	//private int verranschlagt;
	private String beschreibung;
	private Date schadendatum;
	private double summe;
	private String status;
	
	public Kostenvoranschlag(int idKV, String beschreibung, Date schadendatum, double summe) {
		super();
		this.idKV = idKV;
		this.beschreibung = beschreibung;
		this.schadendatum = schadendatum;
		this.summe = summe;

	}

	public int getIdKV() {
		return idKV;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public Date getSchadendatum() {
		return schadendatum;
	}

	public double getSumme() {
		return summe;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return idKV + " - " + beschreibung + " - EUR " + summe ;
	}
	
	
	

	
	
	

}
