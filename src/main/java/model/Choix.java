package model;

public class Choix {
	
	private int question_id;
	private String libelle;
	
	public static String getSchema() {
		return "create table Choix (\r\n" + "question_id int not null references Question(question_id),\r\n"
				+ "Libelle varchar(255) not null)";
	}
	
	public static String getConstraints() {
		return "\"choix_question_id_fkey\" FOREIGN KEY (question_id) REFERENCES question(question_id)";
	}
	
	public static String[] getFields() {
		return new String[] { "question_id", "libelle" };
	}
	
	public int getQuestion_id() {
		return question_id;
	}
	
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
