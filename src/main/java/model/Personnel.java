package model;

public class Personnel extends Personne {
	
	private String metier;

	public static String[] getNameFields() {
		return new String[] { "metier" };
	}
	
	public String getMetier() {
		return metier;
	}
	
	public void setMetier(String metier) {
		this.metier = metier;
	}
	
}
