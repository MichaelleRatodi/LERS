package model;

public class QuestionChoix extends Question {
	
	private boolean plusieursChoix;
	
	public static String getSchema() {
		return "create table QuestionChoix (PlusieursChoix boolean not null) inherits(Question)";
	}
	
	public static String getConstraints() {
		return "";
	}
	
	public static String[] getFields() {
		return new String[] { "question_id", "formulaire_id", "plusieursChoix" };
	}
	
	public boolean getPlusieursChoix() {
		return plusieursChoix;
	}
	
	public void setPlusieursChoix(boolean plusieursChoix) {
		this.plusieursChoix = plusieursChoix;
	}
}
