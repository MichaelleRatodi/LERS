package defaultPackage;

import java.time.LocalDate;
import java.util.List;

import entity.AccesBDD;
import model.Questionnaire;

public class Projet {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		System.out.println("Classe d'exécution du projet");

		AccesBDD abdd = AccesBDD.getInstance();
		
		Questionnaire quest = new Questionnaire(1, "Essai1", LocalDate.now(), LocalDate.now().plusDays(2), 2);
		abdd.insererObjet(quest);
		quest = new Questionnaire(2, "Essai2", LocalDate.now(), LocalDate.now().plusDays(2), 2);
		abdd.insererObjet(quest);
		quest = new Questionnaire(3, "Essai3", LocalDate.now(), LocalDate.now().plusDays(2), 2);
		abdd.insererObjet(quest);
		abdd.supprimerObjet(quest);
		
		
		quest = abdd.recupererObjetParId(new Questionnaire(), 1);
		System.out.println(quest.getQuestionnaire_id());
		System.out.println(quest.getDateCreation());
		
		List<Questionnaire> quests = abdd.recupererTousObjets(new Questionnaire());
		System.out.println(quests);
		System.out.println(quests.size());
		System.out.println(quests.isEmpty());
		System.out.println(quests.get(0).getDateCreation());
		System.out.println(quests.get(1).getDateCreation());
	}

}
