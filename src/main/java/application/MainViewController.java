package application;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.documents.DocumentsViewController;
import application.employee.EmployeeViewController;
import application.login.LoginManager;
import application.questionnaire.QuestionnaireViewController;
import application.sending.SendingViewController;
import application.statistics.StatisticsViewController;
import application.users.UserInfoController;
import entity.AccesBDD;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Questionnaire;
import model.RH;

/** Controls the main application screen */
public class MainViewController extends UserInfoController {
	@FXML
	private Label usernameLabel;
	@FXML
	private ImageView documents = new ImageView();
	@FXML
	private ImageView questionnaire = new ImageView();
	@FXML
	private ImageView Squestionnaire = new ImageView();
	@FXML
	private ImageView employees = new ImageView();
	@FXML
	private ImageView statistics = new ImageView();
	@FXML
	private ImageView settings = new ImageView();
	@FXML
	private ComboBox<String> selectionQuestionnaire = new ComboBox<String>();
	@FXML
	private Button envoyer = new Button();
	
	public void initSessionID(LoginManager loginManager, RH user) {
		super.initialize(loginManager, user);
		usernameLabel.setText(user.getPseudo());
		documents.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				showDocumentsView(loginManager, user);
			}
		});
		employees.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				showEmployeeView(loginManager, user);
			}
		});
		
		questionnaire.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				showQuestionnaireSelectionView(loginManager, user);
			}
		});
		
		Squestionnaire.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				showSendingPageView(loginManager, user);
			}
		});
		
		statistics.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				showStatisticsView(loginManager, user);
			}
		});
		
		settings.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				showSettingsView(loginManager, user);
			}
		});
		
	}
	
	public void initializeSQ(LoginManager loginManager, RH user, Stage create) {
		super.initialize(loginManager, user);
		AccesBDD abdd = AccesBDD.getInstance();
		try {
			List<Questionnaire> questionnaires = abdd.recupererTousObjets(new Questionnaire());
			List<String> libelleQuest = new ArrayList<String>();
			for (Questionnaire quest : questionnaires)
				libelleQuest.add(quest.getTitre());
			selectionQuestionnaire.setItems(FXCollections.observableArrayList(libelleQuest));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		envoyer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				create.close();
				showQuestionnaireView(loginManager, user, selectionQuestionnaire.getValue());
			}
		});
		
	}
	
	private void showDocumentsView(LoginManager loginManager, RH user) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("./documents/docsview.fxml"));
			loginManager.getScene().setRoot((Parent) loader.load());
			DocumentsViewController controller = loader.<DocumentsViewController>getController();
			controller.initialize(loginManager, user);
		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private void showEmployeeView(LoginManager loginManager, RH user) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("./employee/employeeview.fxml"));
			loginManager.getScene().setRoot((Parent) loader.load());
			EmployeeViewController controller = loader.<EmployeeViewController>getController();
			controller.initialize(loginManager, user);
			((Stage) loginManager.getScene().getWindow()).setMaximized(true);
		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private void showStatisticsView(LoginManager loginManager, RH user) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("./statistics/statisticsview.fxml"));
			loginManager.getScene().setRoot((Parent) loader.load());
			StatisticsViewController controller = loader.<StatisticsViewController>getController();
			controller.initialize(loginManager, user);
			((Stage) loginManager.getScene().getWindow()).setMaximized(true);
		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private void showQuestionnaireView(LoginManager loginManager, RH user, String titreQuest) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("./questionnaire/Questionnaire.fxml"));
			loginManager.getScene().setRoot((Parent) loader.load());
			QuestionnaireViewController controller = loader.<QuestionnaireViewController>getController();
			controller.initializeQuestionnaire(loginManager, user, titreQuest);
			((Stage) loginManager.getScene().getWindow()).setMaximized(true);
		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private void showSendingPageView(LoginManager loginManager, RH user) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("./sending/SendingPage.fxml"));
			loginManager.getScene().setRoot((Parent) loader.load());
			SendingViewController controller = loader.<SendingViewController>getController();
			controller.initialize(loginManager, user);
			((Stage) loginManager.getScene().getWindow()).setMaximized(true);
		} catch (IOException ex) {
			Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void showQuestionnaireSelectionView(LoginManager loginManager, RH user) {
		Stage create = new Stage();
		create.initModality(Modality.APPLICATION_MODAL);
		create.initOwner((Stage) loginManager.getScene().getWindow());
		VBox dialogVbox = new VBox(20);
		dialogVbox.getChildren().add(new Text("S�lection Questionnaire"));
		Scene scene = new Scene(dialogVbox, 400, 600);
		create.setScene(scene);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("questionnaire/selectionquestionnaire.fxml"));
		try {
			scene.setRoot((Parent) loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		MainViewController controller = loader.<MainViewController>getController();
		controller.initializeSQ(loginManager, user, create);
		create.centerOnScreen();
		create.show();
	}
	
	private void showSettingsView(LoginManager loginManager, RH user) {
		Stage dialog = new Stage();
		dialog.initOwner(loginManager.getScene().getWindow());
		dialog.initModality(Modality.APPLICATION_MODAL);
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		dialog.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 200);
		dialog.setY(0);
		dialog.setWidth(200);
		dialog.setHeight(400);
		VBox dialogVbox = new VBox(20);
		dialogVbox.getChildren().add(new Text("Not yet implemented !"));
		Button back = new Button("Back");
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event2) {
				dialog.close();
			}
		});
		dialogVbox.getChildren().add(back);
		Scene dialogScene = new Scene(dialogVbox, 200, 400);
		dialog.setScene(dialogScene);
		dialog.showAndWait();
	}
}