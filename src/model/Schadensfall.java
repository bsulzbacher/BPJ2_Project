package model;

public class Schadensfall {
	private int idSchadensfall;
	private String geschaedigter;
	private String adresse;
	private int idAdresse;
	private String schadenart;
	private String schadenNummerExtern;
	private int idMitarbeiter;
	private String anlageDatum;
	private String schadenDatum;
	private String beschreibung;
	
	public Schadensfall(int idSchadensfall, int idAdresse, String schadenart, String schadenNummerExtern,
			int idMitarbeiter, String anlageDatum, String schadenDatum, String beschreibung) {
		super();
		this.idSchadensfall = idSchadensfall;
		this.idAdresse = idAdresse;
		this.schadenart = schadenart;
		this.schadenNummerExtern = schadenNummerExtern;
		this.idMitarbeiter = idMitarbeiter;
		this.anlageDatum = anlageDatum;
		this.schadenDatum = schadenDatum;
		this.beschreibung = beschreibung;
	}

	public Schadensfall(int idSchadensfall, String geschaedigter, String adresse) {
		super();
		this.idSchadensfall = idSchadensfall;
		this.geschaedigter = geschaedigter;
		this.adresse = adresse;
	}
	public int getIdSchadensfall() {
		return idSchadensfall;
	}
	public String getGeschaedigter() {
		return geschaedigter;
	}

	public String getAdresse() {
		return adresse;
	}
	@Override
	public String toString() {
		return idSchadensfall+"-"+geschaedigter+"-"+adresse;
	}

	public int getIdAdresse() {
		return idAdresse;
	}

	public String getSchadenart() {
		return schadenart;
	}

	public String getSchadenNummerExtern() {
		return schadenNummerExtern;
	}

	public int getIdMitarbeiter() {
		return idMitarbeiter;
	}

	public String getAnlageDatum() {
		return anlageDatum;
	}

	public String getSchadenDatum() {
		return schadenDatum;
	}

	public String getBeschreibung() {
		return beschreibung;
	}
}
