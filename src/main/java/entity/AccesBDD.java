package entity;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Properties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
//import org.postgresql.Driver;

public class AccesBDD {
	
	/** Constructeur privé */
    private AccesBDD()
    {initConnection();}
 
    /** Instance unique pré-initialisée */
    private static AccesBDD INSTANCE = new AccesBDD();
     
    /** Point d'accès pour l'instance unique du singleton */
    public static AccesBDD getInstance()
    {   return INSTANCE;
    }
    
	private Connection connection;
	
	public  Connection getConnection() {
		
		return connection;
	}
	
	public boolean initConnection() {
		
		// Connexion database postgreSQL
		String url = "jdbc:postgresql://localhost/projet";
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "postgres");
		
		// Create the connection to our database.
		try {
			connection = DriverManager.getConnection(url, props);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Problème avec la base de données : " + e.getMessage());
			System.exit(-2);
			return false;
		}
		return true;
	}
	

}
