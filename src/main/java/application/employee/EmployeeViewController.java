package application.employee;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import application.LoginDemoApplication;
import application.MainViewController;
import application.login.LoginManager;
import entity.AccesBDD;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Personnel;
import model.RH;

/** Controls the main application screen */
public class EmployeeViewController extends MainViewController {
	
	private Scene scene;
	
	private Stage create;

	private List<Personnel> employeeList = new ArrayList<>();
	private List<Label> labelList = new ArrayList<>();
	@FXML
	private ImageView home = new ImageView();
	@FXML
	private Button add = new Button();
	@FXML
	private TextField nom = new TextField();
	@FXML
	private TextField prenom = new TextField();
	@FXML
	private TextField email = new TextField();
	@FXML
	private TextField metier = new TextField();
	@FXML
	private Button enregistrerBouton = new Button();
	@FXML
	private AnchorPane listEmployee = new AnchorPane();
	
	public void initialize(LoginManager loginManager, RH user, String sessionId) {
		super.initialize(loginManager, user, sessionId);
		home.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				loginManager.showMainView(sessionId, user);
			}
		});
		
		add.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				showUserCreateView(loginManager, sessionId, user);
			}
		});
		
		employeeList = getPersonnelListFromDB();
		
		employeeList.forEach(personnel -> labelList
				.add(new Label(personnel.getNom() + " " + personnel.getPrenom() + " " + personnel.getMetier())));
		
		HBox hbowTop = new HBox();
		hbowTop.setAlignment(Pos.CENTER_LEFT);
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		
		VBox vbox1 = new VBox();
		vbox1.setFillWidth(false);
		vbox1.setAlignment(Pos.CENTER_LEFT);
		vbox1.setPrefWidth(primaryScreenBounds.getWidth() / 2);
		VBox vbox2 = new VBox();
		vbox2.setFillWidth(false);
		vbox2.setAlignment(Pos.CENTER_LEFT);
		vbox2.setPrefWidth(primaryScreenBounds.getWidth() / 2);
		HBox.setHgrow(vbox1, Priority.ALWAYS);
		HBox.setHgrow(vbox2, Priority.ALWAYS);
		for (int i = 0; i < labelList.size(); i++) {
			
			ImageView imv = new ImageView();
			Image image2 = new Image(LoginDemoApplication.class.getResourceAsStream("./images/personnel.png"));
			imv.setImage(image2);
			
			HBox hbox = new HBox();
			hbox.getChildren().add(imv);
			labelList.get(i).setAlignment(Pos.CENTER_LEFT);
			hbox.getChildren().add(labelList.get(i));
			HBox.setMargin(labelList.get(i), new Insets(20));
			HBox.setMargin(imv, new Insets(20));
			if ((i % 2) == 0) {
				vbox1.getChildren().add(hbox);
			} else {
				vbox2.getChildren().add(hbox);
			}
			
		}
		hbowTop.getChildren().add(vbox1);
		hbowTop.getChildren().add(vbox2);
		listEmployee.getChildren().add(hbowTop);
		((Stage) loginManager.getScene().getWindow()).setMaximized(true);
		// listEmployee.getParent().prefWidth(primaryScreenBounds.getWidth() - 200);
		// listEmployee.getParent().prefHeight(primaryScreenBounds.getHeight() - 200);
	}
	
	public void initializeUser(LoginManager loginManager, RH user, String sessionId, Stage stage) throws NoSuchMethodException, SecurityException {
		super.initialize(loginManager, user, sessionId);
		
		create = stage;
		
		enregistrerBouton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ajouterUserDB();
				create.close();
				loginManager.showMainView(sessionId, user);
			}
		});
		
	}
	
	private List<Personnel> getPersonnelListFromDB() {
		
		AccesBDD abdd = AccesBDD.getInstance();
		try {
			return abdd.recupererTousObjets(new Personnel());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void showUserCreateView(LoginManager loginManager, String sessionId, RH user) {
		create = new Stage();
		create.initModality(Modality.APPLICATION_MODAL);
		create.initOwner((Stage) loginManager.getScene().getWindow());
		VBox dialogVbox = new VBox(20);
		dialogVbox.getChildren().add(new Text("Nouveau Personnel"));
		scene = new Scene(dialogVbox, 400, 600);
		create.setScene(scene);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("employeecreate.fxml"));
		try {
			scene.setRoot((Parent) loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		EmployeeViewController controller = loader.<EmployeeViewController>getController();
		try {
			controller.initializeUser(loginManager, user, sessionId, create);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		create.centerOnScreen();
		create.show();
	}
	
	private void ajouterUserDB() {
		System.out.println("ajouterUserDB");
		Personnel newUser = new Personnel();
		newUser.setNom(this.nom.getText());
		newUser.setPrenom(this.prenom.getText());
		newUser.setEmail(this.email.getText());
		newUser.setMetier(this.metier.getText());
		
		AccesBDD abdd = AccesBDD.getInstance();
		abdd.insererObjet(newUser);
	}
	
}