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
	
	public Kostenvoranschlag(int idKV, String beschreibung, Date schadendatum, double summe, String status) {
		super();
		this.idKV = idKV;
		this.beschreibung = beschreibung;
		this.schadendatum = schadendatum;
		this.summe = summe;
		this.status = status;
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
	
	
	

	
	
	

}
