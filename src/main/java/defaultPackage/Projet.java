package defaultPackage;

import java.time.LocalDate;

import entity.AccesBDD;
import model.Formulaire;

public class Projet {
	
	public static void main(String[] args) {
		System.out.println("Classe d'exécution du projet");
		
		AccesBDD abdd = new AccesBDD();
		abdd.initConnection();
		
		//Formulaire form = new Formulaire(2, "Essai", LocalDate.now(), LocalDate.now().plusDays(2), 2);
		// System.out.println(AccesBDD.insererObjet(abdd.getConnection(), form));
		
		System.out.println(AccesBDD.recupererObjetParId(abdd.getConnection(), new Formulaire(1), 1));
	}
	
}
