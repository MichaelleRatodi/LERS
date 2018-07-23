package model;

import java.time.LocalDate;

public class Questionnaire {
	
	private int questionnaire_id;
	private String titre;
	private LocalDate dateCreation;
	private LocalDate dateLimite;
	private int intervalleRelance;
	
	public static String getSchema() {
		return "create table Questionnaire (\r\n" + "questionnaire_id serial primary key,\r\n"
				+ "Titre varchar(255) not null,\r\n" + "DateCreation timestamp not null,\r\n"
				+ "DateLimite timestamp not null,\r\n" + "IntervalleRelance int not null)";
	}
	
	public static String getConstraints() {
		return "\"questionnaire_pkey\" PRIMARY KEY, btree (questionnaire_id)"
				+ " TABLE \"question\" CONSTRAINT \"question_questionnaire_id_fkey\" FOREIGN KEY (questionnaire_id) REFERENCES\r\n"
				+ " questionnaire(questionnaire_id)\r\n"
				+ "    TABLE \"questionnairepersonnel\" CONSTRAINT \"questionnairepersonnel_questionnaire_id_fkey\" FOREIGN KEY (\r\n"
				+ "questionnaire_id) REFERENCES questionnaire(questionnaire_id)";
	}
	
	public static String[] getFields() {
		return new String[] { "questionnaire_id", "Titre", "DateCreation", "DateLimite", "IntervalleRelance" };
	}
	
	public Questionnaire(int questionnaire_id, String titre, LocalDate dateCreation, LocalDate dateLimite,
			int intervalleRelance) {
		this.questionnaire_id = questionnaire_id;
		this.titre = titre;
		this.dateCreation = dateCreation;
		this.dateLimite = dateLimite;
		this.intervalleRelance = intervalleRelance;
	}

	public Questionnaire(int questionnaire_id) {
		this.questionnaire_id = questionnaire_id;
	}
	
	public Questionnaire() {
	}
	
	public int getQuestionnaire_id() {
		return questionnaire_id;
	}
	
	public void setQuestionnaire_id(int questionnaire_id) {
		this.questionnaire_id = questionnaire_id;
	}
	
	public String getTitre() {
		return titre;
	}
	
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	public LocalDate getDateCreation() {
		return dateCreation;
	}
	
	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	public LocalDate getDateLimite() {
		return dateLimite;
	}
	
	public void setDateLimite(LocalDate dateLimite) {
		this.dateLimite = dateLimite;
	}
	
	public int getIntervalleRelance() {
		return intervalleRelance;
	}
	
	public void setIntervalleRelance(int intervalleRelance) {
		this.intervalleRelance = intervalleRelance;
	}
	
}
