package model;

public class QuestionnairePersonnel {
	
	private int questionnaire_id;
	private int personnel_id;
	
	public static String getSchema() {
		return "create table QuestionnairePersonnel (\r\n"
				+ "questionnaire_id int not null references Questionnaire(questionnaire_id),\r\n"
				+ "personnel_id int not null references Personnel(personnel_id))";
	}
	
	public static String getConstraints() {
		return " \"questionnairepersonnel_personnel_id_fkey\" FOREIGN KEY (personnel_id) REFERENCES personnel(personnel_id)\r\n"
				+ "    \"questionnairepersonnel_questionnaire_id_fkey\" FOREIGN KEY (questionnaire_id) REFERENCES questionnaire\r\n"
				+ "(questionnaire_id)";
	}
	
	public static String[] getFields() {
		return new String[] { "questionnaire_id", "personnel_id" };
	}
	
	public int getQuestionnaire_id() {
		return questionnaire_id;
	}
	
	public void setQuestionnaire_id(int questionnaire_id) {
		this.questionnaire_id = questionnaire_id;
	}
	
	public int getPersonnel_id() {
		return personnel_id;
	}
	
	public void setPersonnel_id(int personnel_id) {
		this.personnel_id = personnel_id;
	}
}
