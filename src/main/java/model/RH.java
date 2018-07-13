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
