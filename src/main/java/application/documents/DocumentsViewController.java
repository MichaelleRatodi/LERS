package application.documents;

import java.time.LocalDate;

import application.MainViewController;
import application.login.LoginManager;
import entity.AccesBDD;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	
	private Questionnaire questionnaire;
	
	public void initialize(LoginManager loginManager, RH user, String sessionId) {
		super.initialize(loginManager, user, sessionId);
		questionnaire = new Questionnaire();
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
		
	}
	
	private void showAlertMessage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Form Saved");
		alert.setContentText("Your form have been saved!");
		
		alert.showAndWait();
	}
	
	private void saveQuestionnaire(Questionnaire questionnaire) {
		AccesBDD abdd = AccesBDD.getInstance();
		abdd.initConnection();
		abdd.insererObjet(questionnaire);
	}
	
}