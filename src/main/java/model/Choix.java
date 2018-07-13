package model;

public class Choix {
	
	private int question_id;
	private String libelle;

	public static String[] getNameFields() {
		return new String[] { "question_id", "libelle"};
	}
	
	public int getQuestion_id() {
		return question_id;
	}
	
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
