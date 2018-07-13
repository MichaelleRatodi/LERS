package model;

public class RH {
	
	private String pseudo;
	private String motDePasse;
	
	public static String[] getNameFields() {
		return new String[] { "pseudo", "motDePasse" };
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
