package model;

public class Personne {
	
	private int personne_id;
	private String nom;
	private String prenom;
	private String email;
	
	public static String[] getNameFields() {
		return new String[] { "personne_id", "nom", "prenom", "email" };
	}
	
	public int getPersonne_id() {
		return personne_id;
	}
	
	public void setPersonne_id(int personne_id) {
		this.personne_id = personne_id;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
