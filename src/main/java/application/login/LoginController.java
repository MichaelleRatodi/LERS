package application.login;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.AccesBDD;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.RH;

/** Controls the login screen */
public class LoginController {
  @FXML private TextField user;
  @FXML private TextField password;
  @FXML private Button loginButton;
  @FXML private Hyperlink hyperlink = new Hyperlink();
  
  private RH userConnected = null;
  
  public void initialize() {}
  
  public void initManager(final LoginManager loginManager) {
    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        String sessionID = authorize();
        if (sessionID != null) {
          loginManager.authenticated(sessionID, userConnected);
        }
      }
    });
    hyperlink.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            System.out.println("Forgot your password link is clicked");
            loginManager.passwordForgotten();
        }
    });
  }

  /**
   * Check authorization credentials.
   * 
   * If accepted, return a sessionID for the authorized session
   * otherwise, return null.
 * @throws SQLException 
   */   
  private String authorize() {
	 
	  AccesBDD abdd = new AccesBDD();
	 abdd.initConnection();
	 StringBuilder requete = new StringBuilder("select * from RH where pseudo = '" + user.getText() + "' and MotDePasse = '" + password.getText() + "'");
	 System.out.println("Requete : " + requete.toString());
	 Statement st;
	try {
		st = abdd.getConnection().createStatement();
		ResultSet rs = st.executeQuery(requete.toString());
		if  (rs.next()) {
			userConnected = new RH();
			userConnected.setPseudo(rs.getString("pseudo"));
			userConnected.setMotDePasse(rs.getString("MotDePasse"));
			userConnected.setNom(rs.getString("Nom"));
			userConnected.setPrenom(rs.getString("Prenom"));
			userConnected.setEmail(rs.getString("email"));
			userConnected.setPersonne_id(rs.getInt("personne_id"));
			return generateSessionID();
		} else return null;
	} catch (SQLException e) {
		System.out.println(e);
		return null;
	}
	   
  }
  
  private static int sessionID = 0;

  private String generateSessionID() {
    sessionID++;
    return " ID " + sessionID;
  }
}
