package model;

public class QuestionTexte extends Question {
	
	private int nbColonnesZoneTexte;
	private int nbLignesZoneTexte;

	public static String[] getNameFields() {
		return new String[] { "nbColonnesZoneTexte", "nbLignesZoneTexte" };
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
