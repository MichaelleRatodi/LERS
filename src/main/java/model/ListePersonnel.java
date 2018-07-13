package model;

public class ListePersonnel {
	
	private int liste_id;
	private int personne_id;

	public static String[] getNameFields() {
		return new String[] { "liste_id", "personne_id"};
	}
	
	public int getListe_id() {
		return liste_id;
	}
	
	public void setListe_id(int liste_id) {
		this.liste_id = liste_id;
	}
	
	public int getPersonne_id() {
		return personne_id;
	}
	
	public void setPersonne_id(int personne_id) {
		this.personne_id = personne_id;
	}
}
