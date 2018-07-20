package application.sending;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import entity.AccesBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import model.Personnel;
import model.Question;
import model.Questionnaire;
import java.lang.String;

public class SendingViewController implements Initializable {
	@FXML
	private Pane pane = new Pane();
	
	@FXML
	ObservableList<String> comboCollection ;
	
	@FXML
	private ComboBox<Questionnaire> choixQuestionnaire = new ComboBox<Questionnaire>();

	@FXML
	private CheckBox hierarchicalDirection = new CheckBox();

	@FXML
	private CheckBox humanResources = new CheckBox();

	@FXML
	private CheckBox allEmployees = new CheckBox();
	
	@FXML
	private Label recipientList = new Label();

	@FXML
	private Button send = new Button();

	public SendingViewController() {

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
		
//		comboCollection = FXCollections.observableArrayList(
//				"test","test1"
//							);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//pour récupérer les questionnaires de la base de données
			AccesBDD abdd = AccesBDD.getInstance();
			
		//ObservableList pour afficher le choix dans le ComboBox
			ObservableList<Questionnaire> questionnaires = FXCollections.observableArrayList(

						);
			try {
				questionnaires = FXCollections.observableArrayList(abdd.recupererTousObjets(new Questionnaire()));
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}

//		questionnaires.add(new Questionnaire(1, "Q1", LocalDate.now(), LocalDate.now(), 100));
//		questionnaires.add(new Questionnaire(2, "Q2", LocalDate.now(), LocalDate.now(), 100));
//		questionnaires.add(new Questionnaire(3, "Q3", LocalDate.now(), LocalDate.now(), 100));
		
		choixQuestionnaire.getItems().clear();
		
		choixQuestionnaire.setItems(questionnaires);
        
		choixQuestionnaire.valueProperty()
				.addListener((obs, oldVal, newVal) -> System.out.println("Questionnaire : " + newVal.getTitre()));

		choixQuestionnaire.setConverter(new StringConverter<Questionnaire>() {
			@Override
			public String toString(Questionnaire object) {
				return object.getTitre();
			}

			@Override
			public Questionnaire fromString(String string) {
				return null;
			}
		});
		
	
		//pour récupérer la liste du personnel de la base de données
		
		List<Personnel> employeeList = new ArrayList<>();
			
		try {
			employeeList= (List<Personnel>) abdd.recupererTousObjets(new Personnel());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		//pour afficher dans le checkbox
		
		allEmployees.setItems(employeeList);	
	}

}
