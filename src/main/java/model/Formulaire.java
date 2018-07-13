package model;

import java.time.LocalDate;

public class Formulaire {
	
	private int formulaire_id;
	private String titre;
	private LocalDate dateCreation;
	private LocalDate dateLimite;
	private int intervalleRelance;
	
	public static String[] getNameFields() {
		return new String[] { "formulaire_id", "Titre", "DateCreation", "DateLimite", "IntervalleRelance" };
	}
	
	public static String getSchema() {
		return "formulaire_id";
	}
	
	public Formulaire(int formulaire_id, String titre, LocalDate dateCreation, LocalDate dateLimite,
			int intervalleRelance) {
		this.formulaire_id = formulaire_id;
		this.titre = titre;
		this.dateCreation = dateCreation;
		this.dateLimite = dateLimite;
		this.intervalleRelance = intervalleRelance;
	}
	
	public Formulaire(int formulaire_id) {
		this.formulaire_id = formulaire_id;
	}
	
	public int getFormulaire_id() {
		return formulaire_id;
	}
	
	public void setFormulaire_id(int formulaire_id) {
		this.formulaire_id = formulaire_id;
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
