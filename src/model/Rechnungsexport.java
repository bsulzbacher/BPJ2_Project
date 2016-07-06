package model;

public class Rechnungsexport {
	
	int rgnNr;
	String bezahlt;
	String name;
	int schadNr;
	

	public Rechnungsexport(int rgnNr, String bezahlt, String name, int schadNr) {
		// TODO Auto-generated constructor stub
		
			super();
			this.rgnNr = rgnNr;
			this.bezahlt = bezahlt;
			this.name = name;
			this.schadNr = schadNr;
		
	}


	public int getRgnNr() {
		return rgnNr;
	}


	public void setRgnNr(int rgnNr) {
		this.rgnNr = rgnNr;
	}


	public String getBezahlt() {
		return bezahlt;
	}


	public void setBezahlt(String bezahlt) {
		this.bezahlt = bezahlt;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getSchadNr() {
		return schadNr;
	}


	public void setSchadNr(int schadNr) {
		this.schadNr = schadNr;
	}
	
	

}
