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
		return new String[] { "question_id", "formulaire_id", "libelle", "nbColonnesZoneTexte", "nbLignesZoneTexte" };
	}
	
	public int getQuestion_id() {
		return super.getQuestion_id();
	}
	
	public void setQuestion_id(int question_id) {
		super.setQuestion_id(question_id);
	}
	
	public int getQuestionnaire_id() {
		return super.getQuestionnaire_id();
	}
	
	public void setQuestionnaire_id(int questionnaire_id) {
		super.setQuestionnaire_id(questionnaire_id);
	}
	
	public String getLibelle() {
		return super.getLibelle();
	}
	
	public void setLibelle(String libelle) {
		super.setLibelle(libelle);
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
