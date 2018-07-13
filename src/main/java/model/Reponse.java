package model;

public class Reponse {
	
	private int question_id;
	private int personne_id;
	
	public static String[] getNameFields() {
		return new String[] { "question_id", "personne_id" };
	}
	
	public int getQuestion_id() {
		return question_id;
	}
	
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	
	public int getPersonne_id() {
		return personne_id;
	}
	
	public void setPersonne_id(int personne_id) {
		this.personne_id = personne_id;
	}
	
	public String getContenuReponse() {
		return contenuReponse;
	}
	
	public void setContenuReponse(String contenuReponse) {
		this.contenuReponse = contenuReponse;
	}
	
	private String contenuReponse;
}
