package model;

public class Personnel {
	
	private int personnel_id;
	private String nom;
	private String prenom;
	private String email;
	private String metier;
	
	public static String[] getNameFields() {
		return new String[] { "personnel_id", "nom", "prenom", "email", "metier" };
	}
	
	public int getPersonnel_id() {
		return personnel_id;
	}
	
	public void setPersonnel_id(int personnel_id) {
		this.personnel_id = personnel_id;
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
	
	public String getMetier() {
		return metier;
	}
	
	public void setMetier(String metier) {
		this.metier = metier;
	}
	
}
