package model;

public class QuestionChoix extends Question {

	private int choix_id;
	
	private boolean plusieursChoix;
	
	public static String getSchema() {
		return "create table QuestionChoix (choix_id int unique not null, PlusieursChoix boolean not null) inherits(Question)";
	}
	
	public static String getConstraints() {
		return "";
	}
	
	public static String[] getFields() {
		return new String[] { "question_id", "questionnaire_id", "libelle", "choix_id", "plusieursChoix" };
	}

	public int getChoix_id() {
		return choix_id;
	}
	
	public void setChoix_id(int choix_id) {
		this.choix_id = choix_id;
	}
	
	public int getQuestion_id() {
		return super.getQuestion_id();
	}
	
	public void setQuestion_id(int question_id) {
		super.setQuestion_id(question_id);
	}
	
	public int getQuestionnaire_id() {
		return super.getQuestionnaire_id();
	}
	
	public void setQuestionnaire_id(int questionnaire_id) {
		super.setQuestionnaire_id(questionnaire_id);
	}
	
	public String getLibelle() {
		return super.getLibelle();
	}
	
	public void setLibelle(String libelle) {
		super.setLibelle(libelle);
	}
	
	public boolean getPlusieursChoix() {
		return plusieursChoix;
	}
	
	public void setPlusieursChoix(boolean plusieursChoix) {
		this.plusieursChoix = plusieursChoix;
	}
}
