package application.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import application.MainViewController;
import application.login.LoginManager;
import entity.AccesBDD;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Personnel;
import model.Question;
import model.Questionnaire;
import model.QuestionnairePersonnel;
import model.RH;
import model.Reponse;

public class StatisticsViewController extends MainViewController {
	
	@FXML
	private ScrollPane SscrollPane = new ScrollPane();
	@FXML
	private Pane Spane = new Pane();
	@FXML
	private ImageView home = new ImageView();
	@FXML
	private ComboBox<String> SselectionQuests = new ComboBox<String>();
	@FXML
	private ComboBox<String> SselectionEmploye = new ComboBox<String>();
	@FXML
	private Button Senvoyer = new Button();
	@FXML
	private Button Sretour = new Button();
	
	private Questionnaire SquestConsulte;
	
	public StatisticsViewController() {
		
	}
	
	@Override
	public void initialize(LoginManager loginManager, RH user) {
		
		AccesBDD abdd = AccesBDD.getInstance();
		List<Questionnaire> SlisteQuestionnaires = new ArrayList<Questionnaire>();
		
		try {
			SlisteQuestionnaires = (List<Questionnaire>) abdd.recupererTousObjets(new Questionnaire());
			List<String> listeTitresQuest = new ArrayList<String>();
			for (Questionnaire quest : SlisteQuestionnaires)
				listeTitresQuest.add(quest.getTitre());
			
			SselectionQuests.setItems(FXCollections.observableArrayList(listeTitresQuest));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		home.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				loginManager.showMainView(user);
			}
		});
		
		SselectionQuests.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				
				List<Questionnaire> SlisteQuestionnaires = new ArrayList<Questionnaire>();
				try {
					SlisteQuestionnaires = (List<Questionnaire>) abdd.recupererTousObjets(new Questionnaire());
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				
				String titreQuest = t1;
				
				for (Questionnaire quest : SlisteQuestionnaires)
					if (quest.getTitre().equals(titreQuest))
						SquestConsulte = quest;
					
				SlisteQuestionnaires.clear();
				
				List<QuestionnairePersonnel> questPersonnels = new ArrayList<QuestionnairePersonnel>();
				
				try {
					questPersonnels = (List<QuestionnairePersonnel>) abdd
							.recupererTousObjets(new QuestionnairePersonnel());
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				
				for (int i = questPersonnels.size() - 1; i >= 0; i--)
					if (questPersonnels.get(i).getQuestionnaire_id() != SquestConsulte.getQuestionnaire_id())
						questPersonnels.remove(i);
					
				List<String> listeNoms = new ArrayList<String>();
				Personnel pers = new Personnel();
				for (QuestionnairePersonnel qp : questPersonnels) {
					int idPersonnel = qp.getPersonnel_id();
					try {
						pers = abdd.recupererObjetParId(new Personnel(), idPersonnel);
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
					listeNoms.add(pers.getNom() + " " + pers.getPrenom());
				}
				
				SselectionEmploye.setItems(FXCollections.observableArrayList(listeNoms));
			}
		});
		
		SselectionEmploye.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				
				if (Spane.getChildren().size() > 3)
					Spane.getChildren().remove(3, Spane.getChildren().size());
				
				if (t1 != null) {
					
					String nomPersonnel = t1;
					Personnel pers = new Personnel();
					
					List<Personnel> listePersonnels = new ArrayList<Personnel>();
					List<Question> listeQuestions = new ArrayList<Question>();
					List<Reponse> listeReponsesOK = new ArrayList<Reponse>();
					
					try {
						listePersonnels = (List<Personnel>) abdd.recupererTousObjets(new Personnel());
						
						for (int i = listePersonnels.size() - 1; i >= 0; i--) {
							if (nomPersonnel.equals(listePersonnels.get(i).getNom()+" "+listePersonnels.get(i).getPrenom()))
								pers = listePersonnels.get(i);
						}
						
						listePersonnels.clear();
						
						listeQuestions = (List<Question>) abdd.recupererTousObjets(new Question());
						
						for (int i = listeQuestions.size() - 1; i >= 0; i--) {
							if (listeQuestions.get(i).getQuestionnaire_id() != SquestConsulte.getQuestionnaire_id())
								listeQuestions.remove(i);
						}
						
						Collections.sort(listeQuestions, new Comparator<Question>() {
							@Override
							public int compare(Question question1, Question question2) {
								
								if (question1.getQuestion_id() < question2.getQuestion_id())
									return -1;
								else if (question1.getQuestion_id() > question2.getQuestion_id())
									return 1;
								return 0;
							}
						});
						
						List<Reponse> listeReponses = (List<Reponse>) abdd.recupererTousObjets(new Reponse());
						listeReponsesOK = new ArrayList<Reponse>();
						
						for (int i = listeReponses.size() - 1; i >= 0; i--) {
							for (int ii = listeQuestions.size() - 1; ii >= 0; ii--) {
								if (listeReponses.get(i).getQuestion_id() == listeQuestions.get(ii).getQuestion_id()
										&& listeReponses.get(i).getPersonnel_id() == pers.getPersonnel_id())
									listeReponsesOK.add(listeReponses.get(i));
							}
						}
						
						Collections.sort(listeReponsesOK, new Comparator<Reponse>() {
							@Override
							public int compare(Reponse reponse1, Reponse reponse2) {
								
								if (reponse1.getQuestion_id() < reponse2.getQuestion_id())
									return -1;
								else if (reponse1.getQuestion_id() > reponse2.getQuestion_id())
									return 1;
								return 0;
							}
						});
						
						listeReponses.clear();
						
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
					
					int parcours = 200;
					for (int i = 0; i < listeQuestions.size(); i++) {
						
						Label question = new Label();
						question.setText(listeQuestions.get(i).getLibelle());
						question.setLayoutX(50);
						question.setLayoutY(parcours);
						question.setId("" + listeQuestions.get(i).getQuestion_id());
						question.setStyle("-fx-font-weight: bold");
						
						parcours += 20;
						
						Spane.getChildren().add(question);
						
						Label labelR = new Label();
						String contenuReponse = listeReponsesOK.get(i).getContenuReponse();
						if (contenuReponse.contains((CharSequence) "|"))
							contenuReponse = contenuReponse.replace((CharSequence) "|", (CharSequence) " & ");
						labelR.setText(contenuReponse);
						labelR.setLayoutX(70);
						labelR.setLayoutY(parcours);
						labelR.setId("" + listeReponsesOK.get(i).getQuestion_id());
						
						parcours += 20;
						
						Spane.getChildren().add(labelR);
					}
				}
				
			}
			
		});
		
	}
	
}
