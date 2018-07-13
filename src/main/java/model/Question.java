package model;

public class Question {
	
	private int question_id;
	private int formulaire_id;
	private String libelle;
	
	public static String getSchema() {
		return "create table Question (\r\n" + "question_id serial primary key,\r\n"
				+ "questionnaire_id int not null references Questionnaire(questionnaire_id),\r\n"
				+ "Libelle varchar(255) not null);";
	}
	
	public static String getConstraints() {
		return "\"question_pkey\" PRIMARY KEY, btree (question_id)"
				+ "\"question_questionnaire_id_fkey\" FOREIGN KEY (questionnaire_id)"
				+ "REFERENCES questionnaire(questionnaire_id)"
				+ "TABLE \"choix\" CONSTRAINT \"choix_question_id_fkey\" FOREIGN KEY (question_id)"
				+ "REFERENCES question(question_id)\r\n"
				+ "    TABLE \"reponse\" CONSTRAINT \"reponse_question_id_fkey\""
				+ "FOREIGN KEY (question_id) REFERENCES question(question_id)";
	}
	
	public static String[] getFields() {
		return new String[] { "question_id", "formulaire_id", "libelle" };
	}
	
	public int getQuestion_id() {
		return question_id;
	}
	
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	
	public int getFormulaire_id() {
		return formulaire_id;
	}
	
	public void setFormulaire_id(int formulaire_id) {
		this.formulaire_id = formulaire_id;
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
