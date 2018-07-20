package application.settings;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entity.AccesBDD;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Liste;
import model.Personnel;
import model.RH;

public class SettingCreateViewController implements Initializable {
	@FXML
	private ImageView user = new ImageView();
	@FXML
	private AnchorPane creer = new AnchorPane();
	@FXML
	private Label nouveauHR = new Label();
	@FXML
	private TextField name = new TextField();
	@FXML
	private TextField firstname = new TextField();
	@FXML
	private TextField mail = new TextField();
	@FXML
	private TextField pseudo = new TextField();
	@FXML
	private TextField pass = new TextField();
	@FXML
	private TextField confirmpass = new TextField();
	@FXML
	private Label nom = new Label();
	@FXML
	private Label prenom = new Label();
	@FXML
	private Label mailaddress = new Label();
	@FXML
	private Label alias = new Label();
	@FXML
	private TextField motpasse = new TextField ();
	@FXML
	private TextField confirmer = new TextField ();
	@FXML
	private Button create = new Button();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		AccesBDD abdd = AccesBDD.getInstance();
		List<RH> userRH = new ArrayList<RH>();
	}	
		private void ajouterUserRH() {
			System.out.println("ajouterUserRH");
			RH newUserRH = new RH();
			newUserRH.setNom(this.name.getText());
			newUserRH.setPrenom(this.firstname.getText());
			newUserRH.setEmail(this.mail.getText());
			newUserRH.setMotDePasse(this.motpasse.getText());
			newUserRH.setMotDePasse(this.confirmer.getText());
			if ((this.motpasse.getText()).equals ((this.confirmer.getText()))) {
				AccesBDD abdd = AccesBDD.getInstance();
				abdd.insererObjet(newUserRH);
			}
			
		}	
}

	
