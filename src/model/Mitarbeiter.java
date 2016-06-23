package model;

public class Mitarbeiter extends Person{
	private int mitarbeiter_id;
	private String email;
	
	public Mitarbeiter(String vorname, String nachname, int id, String email) {
		super(vorname, nachname);
		this.mitarbeiter_id = id;
		this.email = email;
	}
}
