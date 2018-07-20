package model;

public class Reponse {
	
	private int question_id;
	private int personnel_id;
	private String contenuReponse;
	
	public static String getSchema() {
		return "create table Reponse (\r\n" + "question_id int not null,\r\n"
				+ "personnel_id int not null,\r\n"
				+ "contenuReponse text not null)";
	}
	
	public static String getConstraints() {
		return "\"reponse_personnel_id_fkey\" FOREIGN KEY (personnel_id) REFERENCES personnel(personnel_id)\r\n"
				+ "    \"reponse_question_id_fkey\" FOREIGN KEY (question_id) REFERENCES question(question_id)";
	}
	
	public static String[] getFields() {
		return new String[] { "question_id", "personnel_id", "contenuReponse" };
	}
	
	public int getQuestion_id() {
		return question_id;
	}
	
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	
	public int getPersonnel_id() {
		return personnel_id;
	}
	
	public void setPersonnel_id(int personnel_id) {
		this.personnel_id = personnel_id;
	}
	
	public String getContenuReponse() {
		return contenuReponse;
	}
	
	public void setContenuReponse(String contenuReponse) {
		this.contenuReponse = contenuReponse;
	}
	
}
