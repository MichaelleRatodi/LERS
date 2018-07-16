package defaultPackage;

import java.time.LocalDate;

import entity.AccesBDD;
import model.Questionnaire;

public class Projet {

	public static void main(String[] args) {
		System.out.println("Classe d'exécution du projet");

		AccesBDD abdd = new AccesBDD();
		abdd.initConnection();

		Questionnaire quest = new Questionnaire(2, "Essai", LocalDate.now(), LocalDate.now().plusDays(2), 2);
		System.out.println(AccesBDD.insererObjet(quest));

		System.out.println(abdd.recupererObjetParId(new Questionnaire(), 1));
	}

}
