package application.settings;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class SettingChangeViewController implements Initializable {
	@FXML
	private ImageView user = new ImageView();
	@FXML
	private AnchorPane changer = new AnchorPane();
	@FXML
	private Label changepass = new Label();
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
	private TextField newpass = new TextField ();
	@FXML
	private TextField confirmpass = new TextField ();
	@FXML
	private Label nom = new Label();
	@FXML
	private Label prenom = new Label();
	@FXML
	private Label mailaddress = new Label();
	@FXML
	private Label alias = new Label();
	@FXML
	private Label motdepasse = new Label();
	@FXML
	private Label newpass2 = new Label();
	@FXML
	private Label confirm2 = new Label();
	@FXML
	private Button confirme = new Button();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
