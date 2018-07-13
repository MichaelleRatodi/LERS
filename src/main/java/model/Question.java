package model;

public class Question {
	
	private int question_id;
	private int formulaire_id;
	private String libelle;

	public static String[] getNameFields() {
		return new String[] { "question_id", "formulaire_id", "libelle" };
	}
	
	public int getQuestion_id() {
		return question_id;
	}
	
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	
	public int getFormulaire_id() {
		return formulaire_id;
	}
	
	public void setFormulaire_id(int formulaire_id) {
		this.formulaire_id = formulaire_id;
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
