package application.login;


import java.sql.SQLException;
import java.util.List;

import entity.AccesBDD;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.RH;

/** Controls the login screen */
public class LoginController {
	@FXML
	private TextField user;
	@FXML
	private TextField password;
	@FXML
	private Button loginButton;
	@FXML
	private Hyperlink hyperlink = new Hyperlink();
	
	private RH userConnected = null;
	
	public void initialize() {
	}
	
	public void initManager(final LoginManager loginManager) {
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				String sessionID = authorize();
				if (sessionID != null)
					loginManager.authenticated(sessionID, userConnected);
				else
					loginManager.showLoginScreen();
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
	 * If accepted, return a sessionID for the authorized session otherwise, return
	 * null.
	 * 
	 * @throws SQLException
	 */
	private String authorize() {
		
		AccesBDD abdd = AccesBDD.getInstance();
		System.out.println("user: " + this.user.getText() + " password: " + this.password.getText());
		try {
			List<RH> listRH = abdd.recupererTousObjets(new RH());
			for (RH rh : listRH) {
				if (rh.getPseudo().equals(this.user.getText()) && rh.getMotDePasse().equals(this.password.getText())) {
					this.userConnected = rh;
					return generateSessionID();
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static int sessionID = 0;
	
	private String generateSessionID() {
		sessionID++;
		return " ID " + sessionID;
	}
	
}
