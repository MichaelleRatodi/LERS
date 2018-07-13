package model;

public class Liste {
	
	private int liste_id;
	private String titre;

	public static String[] getNameFields() {
		return new String[] { "liste_id", "titre"};
	}
	
	public int getListe_id() {
		return liste_id;
	}
	
	public void setListe_id(int liste_id) {
		this.liste_id = liste_id;
	}
	
	public String getTitre() {
		return titre;
	}
	
	public void setTitre(String titre) {
		this.titre = titre;
	}
}
