package application.questionnaire;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entity.AccesBDD;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Question;
import model.Questionnaire;
import model.Reponse;

public class QuestionnaireViewController implements Initializable {
	
	@FXML
	private Pane pane = new Pane();
	@FXML
	private TextArea nom = new TextArea();
	@FXML
	private TextArea prenom = new TextArea();
	@FXML
	private TextArea question1 = new TextArea();
	@FXML
	private CheckBox choix1a = new CheckBox();
	@FXML
	private CheckBox choix1b = new CheckBox();
	@FXML
	private CheckBox choix1c = new CheckBox();
	@FXML
	private TextArea question2 = new TextArea();
	@FXML
	private TextArea reponse2 = new TextArea();
	@FXML
	private TextArea question3 = new TextArea();
	@FXML
	private CheckBox choix3a = new CheckBox();
	@FXML
	private CheckBox choix3b = new CheckBox();
	@FXML
	private CheckBox choix3c = new CheckBox();
	@FXML
	private TextArea question4 = new TextArea();
	@FXML
	private TextArea reponse4 = new TextArea();
	@FXML
	private TextArea question5 = new TextArea();
	@FXML
	private CheckBox choix5a = new CheckBox();
	@FXML
	private CheckBox choix5b = new CheckBox();
	@FXML
	private CheckBox choix5c = new CheckBox();
	@FXML
	private TextArea question6 = new TextArea();
	@FXML
	private TextArea reponse6 = new TextArea();
	@FXML
	private TextArea question7 = new TextArea();
	@FXML
	private CheckBox choix7a = new CheckBox();
	@FXML
	private CheckBox choix7b = new CheckBox();
	@FXML
	private CheckBox choix7c = new CheckBox();
	@FXML
	private TextArea question8 = new TextArea();
	@FXML
	private TextArea reponse8 = new TextArea();
	@FXML
	private TextArea question9 = new TextArea();
	@FXML
	private RadioButton choix9a = new RadioButton();
	@FXML
	private RadioButton choix9b = new RadioButton();
	@FXML
	private TextArea question10 = new TextArea();
	@FXML
	private TextArea reponse10 = new TextArea();
	@FXML
	private Button envoyer = new Button();
	
	private Reponse reponses;

	public QuestionnaireViewController() {
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
//		AccesBDD abdd = AccesBDD.getInstance();
//		abdd.remplirBDD();
//		Statement st;
		
		//pour récupérer les questions de la base de données
		AccesBDD abdd = AccesBDD.getInstance();
		Questionnaire quest = new Questionnaire();
		List<Question> listeQuest = new ArrayList<Question>();
		try {
			quest = abdd.recupererObjetParId(new Questionnaire(), 1);
			listeQuest = (List<Question>) abdd.recupererTousObjets(new Question());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		//pour faire le tri des questions dont on a besoin
		for(int i=listeQuest.size()-1;i > 0;i--)
			if (listeQuest.get(i).getQuestionnaire_id() != quest.getQuestionnaire_id())
				listeQuest.remove(i);
		
		question1.setText(listeQuest.get(0).getLibelle());
		question2.setText(listeQuest.get(1).getLibelle());
		question3.setText(listeQuest.get(2).getLibelle());
		question4.setText(listeQuest.get(3).getLibelle());
		question5.setText(listeQuest.get(4).getLibelle());
		question6.setText(listeQuest.get(5).getLibelle());
		question7.setText(listeQuest.get(6).getLibelle());
		question8.setText(listeQuest.get(7).getLibelle());
		question9.setText(listeQuest.get(8).getLibelle());
		question10.setText(listeQuest.get(9).getLibelle());
		
		envoyer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				sendFunction(reponses);
			}

			
		});
	}
	
	// pour insérer les réponses dans la table Reponse
	private void sendFunction(Reponse reponses) {
				
			AccesBDD abdd = AccesBDD.getInstance();
			abdd.insererObjet(reponses);
		}
}

