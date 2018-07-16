package application;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

import application.documents.DocumentsViewController;
import application.employee.EmployeeViewController;
import application.login.LoginManager;
import application.users.UserInfoController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.RH;

/** Controls the main application screen */
public class MainViewController extends UserInfoController{
  @FXML private Label  usernameLabel;
  @FXML private ImageView  documents = new ImageView();
  @FXML private ImageView  diaries = new ImageView();
  @FXML private ImageView  employees = new ImageView();
  @FXML private ImageView  statistics = new ImageView();
  @FXML private ImageView  settings = new ImageView();
  
  
  public void initSessionID(LoginManager loginManager, String sessionID, RH user) {
	  super.initialize(loginManager, user, sessionID);
	    usernameLabel.setText(user.getPseudo());
	    documents.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
        public void handle(MouseEvent event) {
        	
			showDocumentsView(loginManager, sessionID, user);
        }
	    });	
	    employees.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
	        public void handle(MouseEvent event) {
	        	
				showEmployeeView(loginManager, sessionID, user);
	        }	
		});
    
  }
  
  private void showDocumentsView(LoginManager loginManager, String sessionID, RH user) {
	    try {
	      FXMLLoader loader = new FXMLLoader(
	        getClass().getResource("./documents/docsview.fxml")
	      );
	      loginManager.getScene().setRoot((Parent) loader.load());
	      DocumentsViewController controller = 
	        loader.<DocumentsViewController>getController();
	      controller.initialize(loginManager, user, sessionID);
	    } catch (IOException ex) {
	      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
	  }
  
  private void showEmployeeView(LoginManager loginManager, String sessionID, RH user) {
	    try {
	      FXMLLoader loader = new FXMLLoader(
	        getClass().getResource("./employee/employeeview.fxml")
	      );
	      loginManager.getScene().setRoot((Parent) loader.load());
	      EmployeeViewController controller = 
	        loader.<EmployeeViewController>getController();
	      controller.initialize(loginManager, user, sessionID);
	    } catch (IOException ex) {
	      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
	  }
  
}