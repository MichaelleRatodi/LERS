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
	private RadioButton freeChoice = new RadioButton();
	@FXML
	private List<Button> addChoices = new ArrayList<Button>();
	@FXML
	private TextField addLabelChoice = new TextField();
	@FXML
	private TextField questionLabel = new TextField();
	@FXML
	private VBox listeQuestions = new VBox();
	@FXML
	private AnchorPane questionPane = new AnchorPane();
	@FXML
	private TextField freeAnswerWidth = new TextField();
	@FXML
	private TextField freeAnswerHeigth = new TextField();
	
	private List<TextField> addLabelChoices = new ArrayList<TextField>();
	
	private List<TextField> listeLabels = new ArrayList<TextField>();
	
	private List<ArrayList<Label>> listeMultipleChoices = new ArrayList<ArrayList<Label>>();
	
	private List<RadioButton> listeMultipleChoice = new ArrayList<RadioButton>();
	
	private List<RadioButton> listeFreeChoice = new ArrayList<RadioButton>();
	
	private List<TextField> listeFreeAnswerWidth = new ArrayList<TextField>();
	
	private List<TextField> listeFreeAnswerHeight = new ArrayList<TextField>();
	
	private Questionnaire questionnaire;
	
	private int nbQuestions;
	
	public void initialize(LoginManager loginManager, RH user) {
		super.initialize(loginManager, user);
		questionnaire = new Questionnaire();
		
		home.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				loginManager.showMainView(user);
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
				
				showAlertMessage(loginManager, user);
			}
		});
		
		nbQuestions = 1;
		
		listeLabels.add(questionLabel);
		listeMultipleChoices.add(new ArrayList<Label>());
		listeFreeChoice.add(freeChoice);
		addLabelChoices.add(addLabelChoice);
		listeFreeAnswerWidth.add(freeAnswerWidth);
		listeFreeAnswerHeight.add(freeAnswerHeigth);
		
		addChoices.add(addChoice);
		addChoices.get(0).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				Label newLabel = new Label();
				newLabel.setText(addLabelChoice.getText());
				newLabel.setLayoutY(92);
				
				int index = 1;
				for (Label choice : listeMultipleChoices.get(0))
					index++;
				newLabel.setId("" + index);
				newLabel.setLayoutX(200 + 100 * index);
				questionPane.getChildren().add(newLabel);
				listeMultipleChoices.get(0).add(newLabel);
			}
		});
		
		addQuestion.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				nbQuestions++;
				
				listeMultipleChoices.add(new ArrayList<Label>());
				
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
				newLabelChoice.setPromptText("Choice answer example");
				newLabelChoice.setLayoutX(41);
				newLabelChoice.setLayoutY(88);
				newLabelChoice.setPrefHeight(26);
				newLabelChoice.setPrefWidth(200);
				addLabelChoices.add(newLabelChoice);
				Button addChoice = new Button();
				addChoice.setText("Add Choice");
				addChoice.setLayoutX(41);
				addChoice.setLayoutY(125);
				
				addChoices.add(addChoice);
				addChoices.get(nbQuestions - 1).addEventHandler(MouseEvent.MOUSE_CLICKED,
						new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent event) {
								
								Label newLabel = new Label();
								newLabel.setText(addLabelChoices.get(nbQuestions - 1).getText());
								newLabel.setLayoutY(92);
								
								int index = 1;
								for (Label choice : listeMultipleChoices.get(nbQuestions - 1))
									index++;
								newLabel.setId("" + index);
								newLabel.setLayoutX(200 + 100 * index);
								newAP.getChildren().add(newLabel);
								listeMultipleChoices.get(nbQuestions - 1).add(newLabel);
							}
						});
				RadioButton newRadioFreeChoice = new RadioButton();
				newRadioFreeChoice.setText("Free answer Question");
				newRadioFreeChoice.setLayoutX(23);
				newRadioFreeChoice.setLayoutY(162);
				newRadioFreeChoice.setToggleGroup(tg);
				
				TextField newFreeAnswerWidth = new TextField();
				newFreeAnswerWidth.setLayoutX(103);
				newFreeAnswerWidth.setLayoutY(188);
				newFreeAnswerWidth.setPrefHeight(26);
				newFreeAnswerWidth.setPrefWidth(59);
				TextField newFreeAnswerHeight = new TextField();
				newFreeAnswerHeight.setLayoutX(103);
				newFreeAnswerHeight.setLayoutY(218);
				newFreeAnswerHeight.setPrefHeight(26);
				newFreeAnswerHeight.setPrefWidth(59);
				
				newAP.getChildren().add(newLabel);
				newAP.getChildren().add(newRadioMultipleChoice);
				newAP.getChildren().add(newLabelChoice);
				newAP.getChildren().add(addChoice);
				newAP.getChildren().add(newRadioFreeChoice);
				newAP.getChildren().add(newFreeAnswerWidth);
				newAP.getChildren().add(newFreeAnswerHeight);
				newQuestion.setContent(newAP);
				
				listeQuestions.getChildren().add(newQuestion);
				
				listeLabels.add(newLabel);
				listeMultipleChoice.add(newRadioMultipleChoice);
				listeFreeChoice.add(newRadioFreeChoice);
				listeFreeAnswerWidth.add(newFreeAnswerWidth);
				listeFreeAnswerHeight.add(newFreeAnswerHeight);
			}
		});
		
	}
	
	private void showAlertMessage(LoginManager loginManager, RH user) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Form Saved");
		alert.setContentText("Your form have been saved!");
		
		alert.showAndWait();
		loginManager.showMainView(user);
	}
	
	private int saveQuestionnaire(Questionnaire questionnaire) {
		AccesBDD abdd = AccesBDD.getInstance();
		int retour = abdd.insererObjet(questionnaire);
		saveQuestions(retour);
		return retour;
	}
	
	private void saveQuestions(int idForm) {
		AccesBDD abdd = AccesBDD.getInstance();
		
		for (int i = 0; i < listeLabels.size(); i++) {
			
			if (listeFreeChoice.get(i).isSelected()) {
				QuestionTexte newQuestT = new QuestionTexte();
				newQuestT.setLibelle(listeLabels.get(i).getText());
				newQuestT.setQuestionnaire_id(idForm);
				newQuestT.setNbColonnesZoneTexte(Integer.parseInt(listeFreeAnswerWidth.get(i).getText()));
				newQuestT.setNbLignesZoneTexte(Integer.parseInt(listeFreeAnswerHeight.get(i).getText()));
				abdd.insererObjet(newQuestT);
			} else {
				QuestionChoix newQuestC = new QuestionChoix();
				newQuestC.setLibelle(listeLabels.get(i).getText());
				newQuestC.setQuestionnaire_id(idForm);
				if (listeMultipleChoices.get(i).size() > 1)
					newQuestC.setPlusieursChoix(true);
				else
					newQuestC.setPlusieursChoix(false);
				
				int newQuestId = abdd.insererObjet(newQuestC);
				
				for (Label strChoix : listeMultipleChoices.get(i)) {
					Choix newChoix = new Choix();
					newChoix.setChoix_id(newQuestId);
					newChoix.setLibelle(strChoix.getText());
					abdd.insererObjet(newChoix);
				}
			}
		}
	}
	
}