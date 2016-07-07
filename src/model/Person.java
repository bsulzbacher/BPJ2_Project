package model;

public class Person {
	private String vorname;
	private String nachname;
	private int personID;
	
	
	public Person(String vorname, String nachname) {
		this.vorname = vorname;
		this.nachname = nachname;
	}

	public Person(String vorname, String nachname, int personID) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.personID = personID;
	}
	
	public String getVorname() {
		return vorname;
	}


	public String getNachname() {
		return nachname;
	}
	
	@Override
	public String toString() {
        return this.nachname + ", " + vorname; 
    }
	
	public int getPersonID() {
		return personID;
	}
	
}
