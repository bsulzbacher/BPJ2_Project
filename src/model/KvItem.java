package model;

public class KvItem {
	private int pos;
	private String matName;
	private double vkPreis;
	private int anzahl;
	private double summe;
	
	public KvItem(int pos, String matName, double vkPreis, int anzahl) {
		super();
		this.pos = pos;
		this.matName = matName;
		this.vkPreis = vkPreis;
		this.anzahl = anzahl;
		summe = vkPreis*anzahl;
	}
	public int getPos() {
		return pos;
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
