package application.users;

import application.login.LoginManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.RH;

/** Controls the user application screen */
public class UserInfoController {
	@FXML
	protected ImageView img = new ImageView();
	
	private RH user;
	
	public RH getUser() {
		return user;
	}
	
	public void setUser(RH user) {
		this.user = user;
	}
	
	private LoginManager loginManager;
	
	public void initialize(LoginManager loginManager, RH user) {
		this.user = user;
		this.loginManager = loginManager;
		img.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				createModalUserInfo();
			}
		});
	}
	
	protected void createModalUserInfo() {
		Stage dialog = new Stage();
		dialog.initOwner(loginManager.getScene().getWindow());
		dialog.initModality(Modality.APPLICATION_MODAL);
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		dialog.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 200);
		dialog.setY(0);
		dialog.setWidth(200);
		dialog.setHeight(400);
		VBox dialogVbox = new VBox(20);
		dialogVbox.getChildren().add(new Text("Username: " + user.getPseudo()));
		dialogVbox.getChildren().add(new Text("FirstName: " + user.getPrenom()));
		dialogVbox.getChildren().add(new Text("Name: " + user.getNom()));
		dialogVbox.getChildren().add(new Text("email: " + user.getEmail()));
		Button logout = new Button("Logout");
		logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event2) {
				loginManager.logout();
				dialog.close();
				((Stage) loginManager.getScene().getWindow()).setMaximized(false);
			}
		});
		dialogVbox.getChildren().add(logout);
		Scene dialogScene = new Scene(dialogVbox, 200, 400);
		dialog.setScene(dialogScene);
		dialog.showAndWait();
	}
	
}