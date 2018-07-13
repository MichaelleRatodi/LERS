package model;

public class Personnel {
	
	private String metier;
	private int personne_id;
	private String nom;
	private String prenom;
	private String email;
	
	public static String[] getNameFields() {
		return new String[] { "personne_id", "nom", "prenom", "email", "metier"};
	}
	
	public int getPersonne_id() {
		return personne_id;
	}

	
	public String getMetier() {
		return metier;
	}
	
	public void setMetier(String metier) {
		this.metier = metier;
	}
	
}
