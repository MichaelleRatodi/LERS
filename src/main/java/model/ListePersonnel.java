package model;

public class ListePersonnel {
	
	private int liste_id;
	private int personnel_id;
	private int rh_id;
	
	public static String getSchema() {
		return "create table ListePersonnel (\r\n" + "liste_id int not null references Liste(liste_id),\r\n"
				+ "personnel_id int references Personnel(personnel_id), rh_id int references RH(rh_id))";
	}
	
	public static String getConstraints() {
		return "\"listepersonnel_liste_id_fkey\" FOREIGN KEY (liste_id) REFERENCES liste(liste_id)\r\n\"\r\n"
				+ "    \"listerh_rh_id_fkey\" FOREIGN KEY (rh_id) REFERENCES rh(rh_id)"
				+ "    \"listepersonnel_personne_id_fkey\" FOREIGN KEY (personne_id) REFERENCES personne(personne_id)";
	}
	
	public static String[] getFields() {
		return new String[] { "liste_id", "personnel_id", "rh_id" };
	}
	
	public int getListe_id() {
		return liste_id;
	}
	
	public void setListe_id(int liste_id) {
		this.liste_id = liste_id;
	}
	
	public int getPersonnel_id() {
		return personnel_id;
	}
	
	public void setPersonnel_id(int personnel_id) {
		this.personnel_id = personnel_id;
	}
	
	public int getRh_id() {
		return rh_id;
	}
	
	public void setRh_id(int rh_id) {
		this.rh_id = rh_id;
	}
	
}
