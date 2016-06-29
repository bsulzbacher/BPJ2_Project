package model;

public class KvItem {
	private int idMaterial;
	private int pos;
	private String matName;
	private double vkPreis;
	private int anzahl;
	private double summe;
	
	public KvItem(int pos, int idMaterial, String matName, double vkPreis, int anzahl) {
		super();
		this.pos = pos;
		this.idMaterial=idMaterial;
		this.matName = matName;
		this.vkPreis = vkPreis;
		this.anzahl = anzahl;
		summe = vkPreis*anzahl;
	}
	public int getPos() {
		return pos;
	}
	
	public int getIdMaterial(){
		return idMaterial;
	}
	
	public String getMatName() {
		return matName;
	}
	public double getVkPreis() {
		return vkPreis;
	}
	public int getAnzahl() {
		return anzahl;
	}
	public double getSumme() {
		return summe;
	}
	
	
	
}
