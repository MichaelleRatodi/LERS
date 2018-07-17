package application.employee;

import java.util.ArrayList;
import java.util.List;

import application.LoginDemoApplication;
import application.MainViewController;
import application.login.LoginManager;
import entity.AccesBDD;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import model.Personnel;
import model.RH;

/** Controls the main application screen */
public class EmployeeViewController extends MainViewController {
	
	private List<Personnel> employeeList = new ArrayList<>();
	private List<Label> labelList = new ArrayList<>();
	@FXML
	private ImageView home = new ImageView();
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
		// listEmployee.getParent().prefWidth(primaryScreenBounds.getWidth() - 200);
		// listEmployee.getParent().prefHeight(primaryScreenBounds.getHeight() - 200);
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
	
}