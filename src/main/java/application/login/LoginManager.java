package application.login;


import java.io.IOException;
import java.util.logging.*;

import application.MainViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import model.RH;

/** Manages control flow for logins */
public class LoginManager {
  private Scene scene;

  public Scene getScene() {
	  return this.scene;
  }
  
  public LoginManager(Scene scene) {
    this.scene = scene;
  }

  /**
   * Callback method invoked to notify that a user has been authenticated.
   * Will show the main application screen.
   */ 
  public void authenticated(String sessionID, RH user) {
    showMainView(sessionID, user);
  }

  /**
   * Callback method invoked to notify that a user has forgotten his password.
   * Will show the forgot password page.
   */ 
  public void passwordForgotten() {
    showPasswordForgotten();
  }
  
  /**
   * Callback method invoked to notify that a user has logged out of the main application.
   * Will show the login application screen.
   */ 
  public void logout() {
    showLoginScreen();
  }
  
  public void showPasswordForgotten() {
	  try {
	    	
	      FXMLLoader loader = new FXMLLoader(
	        getClass().getResource("passwordForgotten.fxml")
	      );
	      scene.setRoot((Parent) loader.load());
	      LoginController controller = 
	        loader.<LoginController>getController();
	      controller.initManager(this);
	    } catch (IOException ex) {
	      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
  }
  
  public void showLoginScreen() {
    try {
    	
      FXMLLoader loader = new FXMLLoader(
        getClass().getResource("login.fxml")
      );
      scene.setRoot((Parent) loader.load());
      LoginController controller = 
        loader.<LoginController>getController();
      controller.initManager(this);
    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void showMainView(String sessionID, RH user) {
    try {
      FXMLLoader loader = new FXMLLoader(
        getClass().getResource("../mainview.fxml")
      );
      scene.setRoot((Parent) loader.load());
      Stage stage = (Stage) scene.getWindow();
      stage.centerOnScreen();
      stage.setMaximized(true);
      MainViewController controller = 
        loader.<MainViewController>getController();
      controller.initSessionID(this, sessionID, user);
    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void showUserInfo(String sessionID, RH user) {
	  try {
    	  FXMLLoader loader = new FXMLLoader(
	        getClass().getResource("../users/userInfo.fxml")
	      );
	      scene.setRoot((Parent) loader.load());
	      MainViewController controller = 
	    	        loader.<MainViewController>getController();
	      controller.initialize(this, user, sessionID);
	    } catch (IOException ex) {
	      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
  }
}
