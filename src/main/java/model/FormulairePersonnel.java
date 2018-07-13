package model;

public class FormulairePersonnel {
	
	private int formulaire_id;
	private int personne_id;

	public static String[] getNameFields() {
		return new String[] { "formulaire_id", "personne_id"};
	}
	
	public int getFormulaire_id() {
		return formulaire_id;
	}
	
	public void setFormulaire_id(int formulaire_id) {
		this.formulaire_id = formulaire_id;
	}
	
	public int getPersonne_id() {
		return personne_id;
	}
	
	public void setPersonne_id(int personne_id) {
		this.personne_id = personne_id;
	}
}
