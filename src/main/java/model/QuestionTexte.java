package model;

public class QuestionTexte extends Question {
	
	private int nbColonnesZoneTexte;
	private int nbLignesZoneTexte;
	
	public static String getSchema() {
		return "create table QuestionTexte (\r\n" + "NbColonnesZoneTexte int not null,\r\n"
				+ "NbLignesZoneTexte int not null) inherits(Question)";
	}

	public static String getConstraints() {
		return "";
	}
	
	public static String[] getFields() {
		return new String[] { "question_id", "formulaire_id", "nbColonnesZoneTexte", "nbLignesZoneTexte" };
	}
	
	public int getNbColonnesZoneTexte() {
		return nbColonnesZoneTexte;
	}
	
	public void setNbColonnesZoneTexte(int nbColonnesZoneTexte) {
		this.nbColonnesZoneTexte = nbColonnesZoneTexte;
	}
	
	public int getNbLignesZoneTexte() {
		return nbLignesZoneTexte;
	}
	
	public void setNbLignesZoneTexte(int nbLignesZoneTexte) {
		this.nbLignesZoneTexte = nbLignesZoneTexte;
	}
}
