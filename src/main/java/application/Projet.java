package application;

import java.time.LocalDate;
import java.util.List;

import entity.AccesBDD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Questionnaire;

public class Projet extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Parent root = FXMLLoader.load(getClass().getResource("questionnaire/Questionnaire.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			AccesBDD abdd = AccesBDD.getInstance();
//			abdd.createBDD();
//			abdd.remplirBDD();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

//	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
////		System.out.println("Classe d'exécution du projet");
////
////		AccesBDD abdd = AccesBDD.getInstance();
////		
////		Questionnaire quest = new Questionnaire(1, "Essai1", LocalDate.now(), LocalDate.now().plusDays(2), 2);
////		abdd.insererObjet(quest);
////		quest = new Questionnaire(2, "Essai2", LocalDate.now(), LocalDate.now().plusDays(2), 2);
////		abdd.insererObjet(quest);
////		quest = new Questionnaire(3, "Essai3", LocalDate.now(), LocalDate.now().plusDays(2), 2);
////		abdd.insererObjet(quest);
////		abdd.supprimerObjet(quest);
////		
////		
////		quest = abdd.recupererObjetParId(new Questionnaire(), 1);
////		System.out.println(quest.getQuestionnaire_id());
////		System.out.println(quest.getDateCreation());
////		
////		List<Questionnaire> quests = abdd.recupererTousObjets(new Questionnaire());
////		System.out.println(quests);
////		System.out.println(quests.size());
////		System.out.println(quests.isEmpty());
////		System.out.println(quests.get(0).getDateCreation());
////		System.out.println(quests.get(1).getDateCreation());
//	}

}
