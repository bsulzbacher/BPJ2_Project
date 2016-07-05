package model;

public class Rechnung {

	private int rgnNr;
	private int kundenID;
	private int schadNr;	 
	
	
	public Rechnung(int rngNr, int empf, int schadNr) {
		super();
		this.rgnNr = rngNr;
		this.kundenID = empf;
		this.schadNr = schadNr;
	}
	

	

	public int getRngNr() {
		return rgnNr;
	}




	public void setRngNr(int rngNr) {
		this.rgnNr = rngNr;
	}




	public int getEmpf() {
		return this.kundenID;
	}




	public void setEmpf(int empf) {
		this.kundenID = empf;
	}

	public int getSchadNr() {
		return schadNr;
	}




	public void setSchadNr(int schadNr) {
		this.schadNr = schadNr;
	}




	@Override
	public String toString() {
		return rgnNr+"-"+this.kundenID ;
	}

}
