package model;

public class QuestionChoix extends Question {
	
	private boolean plusieursChoix;

	public static String[] getNameFields() {
		return new String[] { "plusieursChoix" };
	}
	
	public boolean getPlusieursChoix() {
		return plusieursChoix;
	}
	
	public void setPlusieursChoix(boolean plusieursChoix) {
		this.plusieursChoix = plusieursChoix;
	}
}
