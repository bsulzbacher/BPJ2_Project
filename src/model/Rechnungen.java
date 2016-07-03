package model;

public class Rechnungen {

	private int rngNr;
	private String empf;
	private String bez;
	private int schadNr;	 
	
	
	public Rechnungen(int rngNr, String empf, String bez, int schadNr) {
		super();
		this.rngNr = rngNr;
		this.empf = empf;
		this.bez = bez;
		this.schadNr = schadNr;
	}
	

	

	public int getRngNr() {
		return rngNr;
	}




	public void setRngNr(int rngNr) {
		this.rngNr = rngNr;
	}




	public String getEmpf() {
		return empf;
	}




	public void setEmpf(String empf) {
		this.empf = empf;
	}




	public String getBez() {
		return bez;
	}




	public void setBez(String bez) {
		this.bez = bez;
	}




	public int getSchadNr() {
		return schadNr;
	}




	public void setSchadNr(int schadNr) {
		this.schadNr = schadNr;
	}




	@Override
	public String toString() {
		return rngNr+"-"+empf ;
	}

}
