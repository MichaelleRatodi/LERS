package application.documents;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.MainViewController;
import application.login.LoginManager;
import entity.AccesBDD;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Choix;
import model.Question;
import model.QuestionChoix;
import model.QuestionTexte;
import model.Questionnaire;
import model.RH;

/** Controls the main application screen */
public class DocumentsViewController extends MainViewController {
	
	@FXML
	private ImageView home = new ImageView();
	@FXML
	private ImageView saveFile = new ImageView();
	@FXML
	private ImageView newFile = new ImageView();
	@FXML
	private TextField formTitle = new TextField();
	@FXML
	private Button addQuestion = new Button();
	@FXML
	private Button addChoice = new Button();
	@FXML
	private TextField addLabelChoice = new TextField();
	@FXML
	private TextField questionLabel = new TextField();
	@FXML
	private VBox listeQuestions = new VBox();
	@FXML
	private AnchorPane questionPane = new AnchorPane();
	
	private List<TextField> listeLabels = new ArrayList<TextField>();
	
	private List<RadioButton> listeMultipleChoice = new ArrayList<RadioButton>();
	
	private List<RadioButton> listeFreeChoice = new ArrayList<RadioButton>();
	
	private List<TextArea> listeFreeAnswers = new ArrayList<TextArea>();
	
	private Questionnaire questionnaire;
	
	private int nbQuestions;
	
	public void initialize(LoginManager loginManager, RH user, String sessionId) {
		super.initialize(loginManager, user, sessionId);
		questionnaire = new Questionnaire();
		// question = new Question();
		
		home.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				loginManager.showMainView(sessionId, user);
			}
		});
		
		saveFile.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (questionnaire != null) {
					questionnaire.setTitre(formTitle.getText());
					questionnaire.setDateCreation(LocalDate.now());
					questionnaire.setDateLimite(LocalDate.now().plusMonths(2));
					questionnaire.setIntervalleRelance(7);
				}
				saveQuestionnaire(questionnaire);
				
				showAlertMessage();
			}
		});
		
		nbQuestions = 1;
		
		listeLabels.add(questionLabel);
		
		addQuestion.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				nbQuestions++;
				
				TitledPane newQuestion = new TitledPane();
				newQuestion.setText("Question " + nbQuestions);
				newQuestion.setLayoutX(50);
				newQuestion.setLayoutY(250);
				AnchorPane newAP = new AnchorPane();
				newAP.setId("question" + nbQuestions + "Pane");
				
				TextField newLabel = new TextField();
				newLabel.setPromptText("Wording question");
				newLabel.setLayoutX(14);
				newLabel.setLayoutY(14);
				RadioButton newRadioMultipleChoice = new RadioButton();
				newRadioMultipleChoice.setText("Multiple choices Question");
				newRadioMultipleChoice.setLayoutX(23);
				newRadioMultipleChoice.setLayoutY(56);
				ToggleGroup tg = new ToggleGroup();
				newRadioMultipleChoice.setToggleGroup(tg);
				newRadioMultipleChoice.setSelected(true);
				TextField newLabelChoice = new TextField();
				newLabelChoice.setText("Choice answer example");
				newLabelChoice.setLayoutX(41);
				newLabelChoice.setLayoutY(88);
				Button addChoice = new Button();
				addChoice.setText("Add Choice");
				addChoice.setLayoutX(41);
				addChoice.setLayoutY(125);
				RadioButton newRadioFreeChoice = new RadioButton();
				newRadioFreeChoice.setText("Free answer Question");
				newRadioFreeChoice.setLayoutX(23);
				newRadioFreeChoice.setLayoutY(162);
				newRadioFreeChoice.setToggleGroup(tg);
				TextArea newTextAnswer = new TextArea();
				newTextAnswer.setPromptText("Text response example");
				newTextAnswer.setLayoutX(23);
				newTextAnswer.setLayoutY(198);
				newTextAnswer.setPrefHeight(100);
				newTextAnswer.setPrefWidth(500);
				
				newAP.getChildren().add(newLabel);
				newAP.getChildren().add(newRadioMultipleChoice);
				newAP.getChildren().add(newLabelChoice);
				newAP.getChildren().add(addChoice);
				newAP.getChildren().add(newRadioFreeChoice);
				newAP.getChildren().add(newTextAnswer);
				newQuestion.setContent(newAP);
				
				listeQuestions.getChildren().add(newQuestion);
				
				listeLabels.add(newLabel);
				listeMultipleChoice.add(newRadioMultipleChoice);
				listeFreeChoice.add(newRadioFreeChoice);
				listeFreeAnswers.add(newTextAnswer);
			}
		});
		
		addChoice.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				Label newLabel = new Label();
				newLabel.setText(addLabelChoice.getText());
				newLabel.setLayoutY(92);
				
				int index = 1;
				for (Node node : questionPane.getChildren()) {
					if (node.getClass().getSimpleName().equals("Label"))
						index++;
				}
				newLabel.setId("" + index);
				newLabel.setLayoutX(41 + 100 * index);
				questionPane.getChildren().add(newLabel);
			}
		});
		
	}
	
	private void showAlertMessage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Form Saved");
		alert.setContentText("Your form have been saved!");
		
		alert.showAndWait();
	}
	
	private int saveQuestionnaire(Questionnaire questionnaire) {
		AccesBDD abdd = AccesBDD.getInstance();
		int retour = abdd.insererObjet(questionnaire);
		saveQuestions(retour);
		return retour;
	}
	
	private void saveQuestions(int idForm) {
		AccesBDD abdd = AccesBDD.getInstance();
		
		for (TextField tf : listeLabels) {
			
			Question newQuest = new Question();
			newQuest.setLibelle(tf.getText());
			newQuest.setQuestionnaire_id(idForm);
			abdd.insererObjet(newQuest);
		}
		
		for (int i = 0; i < listeLabels.size(); i++) {
			// for(TextField tf : listeLabels) {
			/*
			 * Question newQuest = new Question();
			 * newQuest.setLibelle(listeLabels.get(i).getText());
			 * newQuest.setQuestionnaire_id(idForm);
			 */
			if (listeFreeChoice.get(i).isSelected()) {
				QuestionTexte newQuest = new QuestionTexte();
				newQuest.setLibelle(listeLabels.get(i).getText());
				newQuest.setQuestionnaire_id(idForm);
				newQuest.setNbColonnesZoneTexte(5);
				newQuest.setNbLignesZoneTexte(5);
				abdd.insererObjet(newQuest);
			} else {
				QuestionChoix newQuest = new QuestionChoix();
				newQuest.setLibelle(listeLabels.get(i).getText());
				newQuest.setQuestionnaire_id(idForm);
				newQuest.setPlusieursChoix(false);
				List<Choix> listeChoix = new ArrayList<Choix>();
				for (Node node : questionPane.getChildren()) {
					if (node.getClass().getSimpleName().equals("Label")) {
						Choix newChoix = new Choix();
						newChoix.setLibelle(libelle);
					}
					
				}
				// Choix newChoix = new Choix();
				// newChoix
			}
		}
	}
	
}