package model;

public class Choix {
	
	private int choix_id;
	private String libelle;
	
	public static String getSchema() {
		return "create table Choix (\r\n" + "choix_id int not null references QuestionChoix(choix_id),\r\n"
				+ "Libelle varchar(255) not null)";
	}
	
	public static String getConstraints() {
		return "\"choix_choix_id_fkey\" FOREIGN KEY (choix_id) REFERENCES questionchoix(choix_id)";
	}
	
	public static String[] getFields() {
		return new String[] { "choix_id", "libelle" };
	}
	
	public int getChoix_id() {
		return choix_id;
	}
	
	public void setChoix_id(int choix_id) {
		this.choix_id = choix_id;
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
