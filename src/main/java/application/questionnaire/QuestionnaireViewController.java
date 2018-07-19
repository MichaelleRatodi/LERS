package application.questionnaire;

import java.net.URL;
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
	private RadioButton choix7a = new RadioButton();
	@FXML
	private RadioButton choix7b = new RadioButton();
	@FXML
	private TextArea question8 = new TextArea();
	@FXML
	private TextArea reponse8 = new TextArea();
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
		
		
		envoyer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				sendFunction(reponses);
			}

			
		});
	}
	
	private void sendFunction(Reponse reponses) {
		// pour insérer les réponses dans la table Reponse
		
			AccesBDD abdd = AccesBDD.getInstance();
			abdd.insererObjet(reponses);
		}
}

