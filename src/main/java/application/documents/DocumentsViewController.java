package application.documents;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import model.Formulaire;
import model.RH;

/** Controls the main application screen */
public class DocumentsViewController extends MainViewController {
  
	  @FXML
	  private ImageView  home = new ImageView();
	  @FXML
	  private ImageView  saveFile = new ImageView();
	  @FXML
	  private ImageView  newFile = new ImageView();
	  @FXML
	  private TextField formTitle = new TextField();
	  
	  private Formulaire formulaire;
	  
	  public void initialize(LoginManager loginManager, RH user, String sessionId) {
		  super.initialize(loginManager, user, sessionId);
		  formulaire = new Formulaire(getNewIdFormulaire());
		  home.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
		      public void handle(MouseEvent event) {
		      	
					loginManager.showMainView(sessionId, user);
		      }
		  });
		  
		  saveFile.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				  @Override
			      public void handle(MouseEvent event) {
			      	if (formulaire != null) {
			      		formulaire.setTitre(formTitle.getText());
			      		formulaire.setDateCreation(LocalDate.now());
			      		formulaire.setDateLimite(LocalDate.now().plusMonths(2));
			      		formulaire.setIntervalleRelance(7);
			      	}
			      	saveFormulaire(formulaire);
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
	  
	  private void saveFormulaire(Formulaire formulaire) {
		  int index = 0;
		  AccesBDD abdd = new AccesBDD();
			 abdd.initConnection();
			 StringBuilder requete = new StringBuilder("select * from formulaire where formulaire_id = " + formulaire.getFormulaire_id());
			 System.out.println("Requete : " + requete.toString());
			 Statement st;
			try {
				st = abdd.getConnection().createStatement();
				ResultSet rs = st.executeQuery(requete.toString());
				if  (rs.next()) {
				 
				 StringBuilder requetedel = new StringBuilder("delete from formulaire where formulaire_id = " + formulaire.getFormulaire_id());
				 System.out.println("Requete : " + requetedel.toString());
						Statement st2 = abdd.getConnection().createStatement();
						st.executeUpdate(requetedel.toString());
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
			 		 
			 abdd.insererObjet(formulaire);
	  }
	  
	  private Integer getNewIdFormulaire() {
		  int index = 0;
		  AccesBDD abdd = new AccesBDD();
			 abdd.initConnection();
			 StringBuilder requete = new StringBuilder("select count(*) from formulaire ");
			 System.out.println("Requete : " + requete.toString());
			 Statement st;
			try {
				st = abdd.getConnection().createStatement();
				ResultSet rs = st.executeQuery(requete.toString());
				if  (rs.next()) {
					index=  rs.getInt(1);
				} 
			} catch (SQLException e) {
				System.out.println(e);
			}
		  return index +1;
	  }
  
}