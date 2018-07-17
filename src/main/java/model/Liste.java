package model;

public class Liste {
	
	private int liste_id;
	private String titre;
	
	public static String getSchema() {
		return "create table Liste (\r\n" + "liste_id serial primary key,\r\n" + "Titre varchar(255) not null)";
	}
	
	public static String getConstraints() {
		return "\"liste_pkey\" PRIMARY KEY, btree (liste_id)"
				+ "TABLE \"listepersonnel\" CONSTRAINT \"listepersonnel_liste_id_fkey\" FOREIGN KEY (liste_id) "
				+ "REFERENCES liste(liste_id)";
	}
	
	public static String[] getFields() {
		return new String[] { "liste_id", "titre" };
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
