package application.questionnaire;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import application.MainViewController;
import application.login.LoginManager;
import entity.AccesBDD;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Choix;
import model.Question;
import model.QuestionChoix;
import model.QuestionTexte;
import model.Questionnaire;
import model.RH;
import model.Reponse;

public class QuestionnaireViewController extends MainViewController {
	
	@FXML
	private ScrollPane scrollPane = new ScrollPane();
	@FXML
	private Pane pane = new Pane();
	@FXML
	private Label nom = new Label();
	@FXML
	private Button envoyer = new Button();
	@FXML
	private Button retour = new Button();
	
	private int questId = 0;
	
	public QuestionnaireViewController() {
		
	}
	
	public void initializeQuestionnaire(LoginManager loginManager, RH user, String questTitre) {
		
		try {
			AccesBDD abdd = AccesBDD.getInstance();
			for (Questionnaire quest : abdd.recupererTousObjets(new Questionnaire()))
				if (quest.getTitre().equals(questTitre))
					this.questId = quest.getQuestionnaire_id();
				
			initialize(loginManager, user);
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(LoginManager loginManager, RH user) {
		
		AccesBDD abdd = AccesBDD.getInstance();
		TreeMap<Integer, Object> mapQuestions = new TreeMap<Integer, Object>();
		List<Question> listeQuestions = new ArrayList<Question>();
		List<QuestionChoix> listeQuestionsC = new ArrayList<QuestionChoix>();
		List<QuestionTexte> listeQuestionsT = new ArrayList<QuestionTexte>();
		Questionnaire quest = new Questionnaire();
		try {
			if (questId == 0)
				quest = abdd.recupererObjetParId(new Questionnaire(), 1);
			else
				quest = abdd.recupererObjetParId(new Questionnaire(), questId);
			listeQuestions = (List<Question>) abdd.recupererTousObjets(new Question());
			listeQuestionsC = (List<QuestionChoix>) abdd.recupererTousObjets(new QuestionChoix());
			listeQuestionsT = (List<QuestionTexte>) abdd.recupererTousObjets(new QuestionTexte());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		nom.setText(quest.getTitre());
		
		// pour faire le tri des questions dont on a besoin
		for (int i = listeQuestions.size() - 1; i >= 0; i--)
			if (listeQuestions.get(i).getQuestionnaire_id() != quest.getQuestionnaire_id())
				listeQuestions.remove(i);
			
		for (int i = 0; i < listeQuestions.size(); i++)
			for (int ii = 0; ii < listeQuestionsC.size(); ii++)
				if (listeQuestions.get(i).getQuestion_id() == listeQuestionsC.get(ii).getQuestion_id())
					mapQuestions.put(listeQuestionsC.get(ii).getQuestion_id(), listeQuestionsC.get(ii));
		
		for (int i = 0; i < listeQuestions.size(); i++)
			for (int ii = 0; ii < listeQuestionsT.size(); ii++)
				if (listeQuestions.get(i).getQuestion_id() == listeQuestionsT.get(ii).getQuestion_id())
					mapQuestions.put(listeQuestionsT.get(ii).getQuestion_id(), listeQuestionsT.get(ii));
				
		listeQuestionsC.clear();
		listeQuestionsT.clear();
		
		int parcours = 100;
		for (Object obj : mapQuestions.values()) {
			
			Label newLabel = new Label();
			newLabel.setText(((Question) obj).getLibelle());
			newLabel.setLayoutX(50);
			newLabel.setLayoutY(parcours);
			newLabel.setId("" + ((Question) obj).getQuestion_id());
			
			parcours += 20;
			
			pane.getChildren().add(newLabel);
			
			if (obj.getClass().getSimpleName().equals("QuestionTexte")) {
				
				QuestionTexte qt = (QuestionTexte) obj;
				TextField newTF = new TextField();
				newTF.setPrefWidth(20 * qt.getNbColonnesZoneTexte());
				newTF.setPrefHeight(20 * qt.getNbLignesZoneTexte());
				newTF.setLayoutX(50);
				newTF.setLayoutY(parcours);
				pane.getChildren().add(newTF);
				parcours += newTF.getPrefHeight() + 20;
				
			} else {
				
				QuestionChoix qt = (QuestionChoix) obj;
				List<Choix> listeChoix = new ArrayList<Choix>();
				try {
					listeChoix = abdd.recupererTousObjets(new Choix());
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				
				for (int i = listeChoix.size() - 1; i >= 0; i--)
					if (listeChoix.get(i).getChoix_id() != qt.getChoix_id())
						listeChoix.remove(i);
					
				int parcoursH = 50;
				if (qt.getPlusieursChoix()) {
					for (int i = 0; i < listeChoix.size(); i++) {
						CheckBox cb = new CheckBox();
						cb.setText(listeChoix.get(i).getLibelle());
						cb.setLayoutX(parcoursH);
						cb.setLayoutY(parcours + 10);
						parcoursH += 7 * cb.getText().length() + 40;
						pane.getChildren().add(cb);
					}
				} else {
					ToggleGroup tg = new ToggleGroup();
					for (int i = 0; i < listeChoix.size(); i++) {
						RadioButton rb = new RadioButton();
						rb.setText(listeChoix.get(i).getLibelle());
						rb.setLayoutX(parcoursH);
						rb.setLayoutY(parcours + 10);
						rb.setToggleGroup(tg);
						parcoursH += 7 * rb.getText().length() + 40;
						pane.getChildren().add(rb);
					}
				}
				parcours += 40;
			}
		}
		
		envoyer.setLayoutY(parcours + 30);
		envoyer.setStyle("-fx-border-color:green;-fx-border-width:2px");
		
		pane.setPrefHeight(parcours + 100);
		
		envoyer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				sendFunction(user);
				loginManager.showMainView(user);
			}
			
		});
		
		retour.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				loginManager.showMainView(user);
			}
		});
	}
	
	private void sendFunction(RH user) {
		// pour insérer les réponses dans la table Reponse
		AccesBDD abdd = AccesBDD.getInstance();
		
		List<Node> listeNodes = pane.getChildren();
		for (int i = 3; i < listeNodes.size(); i++) {
			
			if (listeNodes.get(i).getClass().getSimpleName().equals("Label")) {
				int questionId = Integer.parseInt(((Label) listeNodes.get(i)).getId());
				Reponse reponse = new Reponse();
				reponse.setQuestion_id(questionId);
				reponse.setPersonnel_id(user.getPersonnel_id());
				i++;
				if (listeNodes.get(i).getClass().getSimpleName().equals("TextField")) {
					reponse.setContenuReponse(((TextField) listeNodes.get(i)).getText());
				} else if (listeNodes.get(i).getClass().getSimpleName().equals("RadioButton")) {
					do {
						RadioButton radio = (RadioButton) listeNodes.get(i);
						if (radio.isSelected())
							reponse.setContenuReponse(radio.getText());
					} while (++i < listeNodes.size()
							&& listeNodes.get(i).getClass().getSimpleName().equals("RadioButton"));
					i--;
				} else {
					do {
						CheckBox check = (CheckBox) listeNodes.get(i);
						if (check.isSelected()) {
							if (reponse.getContenuReponse() == null)
								reponse.setContenuReponse(check.getText());
							else
								reponse.setContenuReponse(reponse.getContenuReponse() + "|" + check.getText());
						}
					} while (++i < listeNodes.size()
							&& listeNodes.get(i).getClass().getSimpleName().equals("CheckBox"));
					i--;
				}
				abdd.insererObjet(reponse);
			}
		}
	}
}
