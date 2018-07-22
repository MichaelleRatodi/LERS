package application.sending;

import java.net.URL;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.MainViewController;
import application.login.LoginManager;
import entity.AccesBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import model.Liste;
import model.ListePersonnel;
import model.Personnel;
import model.Questionnaire;
import model.RH;

import java.lang.String;

public class SendingViewController extends MainViewController {
	@FXML
	private Pane pane = new Pane();
	
	@FXML
	private ImageView user = new ImageView();
	
	@FXML
	ObservableList<String> comboCollection;
	
	@FXML
	private ComboBox<Questionnaire> choixQuestionnaire = new ComboBox<Questionnaire>();
	
	@FXML
	private CheckBox humanResources = new CheckBox();
	
	@FXML
	private CheckBox allEmployees = new CheckBox();
	
	@FXML
	private CheckBox hierarchicalDirection = new CheckBox();
	
	@FXML
	private Label recipient = new Label();
	
	@FXML
	private Button send = new Button();
	
	@FXML
	private ImageView home = new ImageView();
	
	public SendingViewController() {
		
	}
	
	@Override
	public void initialize(LoginManager loginManager, RH user) {
		// pour accéder à la base données
		AccesBDD abdd = AccesBDD.getInstance();
		// pour récupérer les questionnaires de la base de données
		// ObservableList pour afficher le choix dans le ComboBox
		ObservableList<Questionnaire> questionnaires = FXCollections.observableArrayList();
		try {
			questionnaires = FXCollections.observableArrayList(abdd.recupererTousObjets(new Questionnaire()));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		// choixQuestionnaire.getItems().clear();
		
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
		
		// pour récupérer la liste de destinataires de la base de données
		List<Liste> recipientList = new ArrayList<Liste>();
		
		try {
			recipientList = (List<Liste>) abdd.recupererTousObjets(new Liste());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		// pour afficher dans le checkbox
		allEmployees.setText(recipientList.get(0).getTitre());
		allEmployees.setId("" + (recipientList.get(0).getListe_id()));
		humanResources.setText(recipientList.get(1).getTitre());
		humanResources.setId("" + (recipientList.get(1).getListe_id()));
		hierarchicalDirection.setText(recipientList.get(2).getTitre());
		hierarchicalDirection.setId("" + (recipientList.get(2).getListe_id()));
		
		send.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				boolean retour = false;
				
				List<Integer> listeIds = new ArrayList<Integer>();
				List<String> emails = new ArrayList<String>();
				
				if (allEmployees.isSelected()) {
					listeIds.add(Integer.parseInt(allEmployees.getId()));
					retour = true;
				}
				if (humanResources.isSelected()) {
					listeIds.add(Integer.parseInt(humanResources.getId()));
					retour = true;
				}
				if (hierarchicalDirection.isSelected()) {
					listeIds.add(Integer.parseInt(hierarchicalDirection.getId()));
					retour = true;
				}
				
				AccesBDD abdd = AccesBDD.getInstance();
				
				List<ListePersonnel> listeListePersonnel = new ArrayList<ListePersonnel>();
				List<ListePersonnel> listeListePersonnelOK = new ArrayList<ListePersonnel>();
				
				try {
					listeListePersonnel = abdd.recupererTousObjets(new ListePersonnel());
					
					for (ListePersonnel lPersonnel : listeListePersonnel) {
						for (Integer id : listeIds) {
							if (lPersonnel.getListe_id() == id.intValue()) {
								ListePersonnel lp = new ListePersonnel();
								lp.setListe_id(lPersonnel.getListe_id());
								lp.setPersonnel_id(lPersonnel.getPersonnel_id());
								lp.setRh_id(lPersonnel.getRh_id());
								listeListePersonnelOK.add(lp);
							}
						}
					}
					
					listeListePersonnel.clear();
					
					List<Personnel> listePersonnel = new ArrayList<Personnel>();
					List<RH> listeRH = new ArrayList<RH>();
					
					listePersonnel = abdd.recupererTousObjets(new Personnel());
					listeRH = abdd.recupererTousObjets(new RH());
					
					for (ListePersonnel lListePersonnel : listeListePersonnelOK) {
						for (Personnel personnel : listePersonnel) {
							if (personnel.getPersonnel_id() == lListePersonnel.getPersonnel_id())
								emails.add(personnel.getEmail());
						}
						for (RH rh : listeRH) {
							if (rh.getRh_id() == lListePersonnel.getRh_id())
								emails.add(rh.getEmail());
						}
					}
					System.out.println("Mails:");
					for (String mail : emails) {
						System.out.println("mail " + mail);
						
						// Get system properties
						Properties props = System.getProperties();
						
						// Setup mail server
						props.put("mail.smtp.auth", "true");
						props.put("mail.smtp.host", "smtp.gmail.com");
						props.put("mail.from", "mat.themelin@hotmail.fr");
						props.put("mail.smtp.port", "465");
						props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
						props.put("mail.smtp.ssl", "true");
						
						// Get the default Session object
						Session session = Session.getInstance(props, new Authenticator() {
							@Override
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication("mathieu.themelin@gmail.com", "34goriL!az2005");
							}
						});
						
						try {
							// Create a default MimeMessage object.
							MimeMessage message = new MimeMessage(session);
							
							// Set From: header field of the header.
							message.setFrom(new InternetAddress("mat.themelin@hotmail.fr"));
							
							// Set To: header field of the header.
							message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
							
							// Set Subject: header field
							message.setSubject("Test LERS");
							
							// Now set the actual message
							message.setText("Test LERS");
							
							// Send message
							Transport.send(message);
							System.out.println("Sent message successfully....");
						} catch (MessagingException mex) {
							mex.printStackTrace();
						}
					}
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				
				if (retour)
					loginManager.showMainView(user);
			}
			
		});
		
	}
	
}
