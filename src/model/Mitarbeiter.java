package model;

public class Mitarbeiter extends Person{
	private int mitarbeiter_id;
	private String email;
	
	public Mitarbeiter(String vorname, String nachname, int id, String email) {
		super(vorname, nachname);
		this.mitarbeiter_id = id;
		this.email = email;
	}
	
	@Override
	public String toString() {
        return mitarbeiter_id + " " + super.getNachname();
    }
	
	public int getMitarbeiter_ID() {
        return mitarbeiter_id;
    }

	public String getEmail() {
		return email;
	}
	
}
