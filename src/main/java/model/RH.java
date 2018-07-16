package model;

public class RH extends Personnel {
	
	private String pseudo;
	private String motDePasse;
	
	public static String getSchema() {
		return "create table RH (\r\n" + "Pseudo varchar(20) not null unique,\r\n"
				+ "MotDePasse varchar(20) not null unique) inherits(Personnel)";
	}
	
	public static String getConstraints() {
		return "\"rh_motdepasse_key\" UNIQUE CONSTRAINT, btree (motdepasse)\r\n"
				+ "    \"rh_pseudo_key\" UNIQUE CONSTRAINT, btree (pseudo)";
	}
	
	public static String[] getFields() {
		return new String[] { "personnel_id", "nom", "prenom", "email", "metier", "pseudo", "motDePasse" };
	}

	public int getPersonnel_id() {
		return super.getPersonnel_id();
	}
	
	public void setPersonnel_id(int personnel_id) {
		super.setPersonnel_id(personnel_id);
	}
	
	public String getNom() {
		return super.getNom();
	}
	
	public void setNom(String nom) {
		super.setNom(nom);
	}
	
	public String getPrenom() {
		return super.getPrenom();
	}
	
	public void setPrenom(String prenom) {
		super.setPrenom(prenom);
	}
	
	public String getEmail() {
		return super.getEmail();
	}
	
	public void setEmail(String email) {
		super.setEmail(email);
	}
	
	public String getMetier() {
		return super.getMetier();
	}
	
	public void setMetier(String metier) {
		super.setMetier(metier);
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public String getMotDePasse() {
		return motDePasse;
	}
	
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
}
