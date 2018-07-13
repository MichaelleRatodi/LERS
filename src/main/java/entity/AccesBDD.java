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
	
	private Connection connection;
	
	public Connection getConnection() {
		return connection;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public boolean initConnection() {
		
		// Connexion database postgreSQL
		String url = "jdbc:postgresql://localhost/projet";
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "postgres");
		
		// Create the connection to our database.
		try {
			this.connection = DriverManager.getConnection(url, props);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Problème avec la base de données : " + e.getMessage());
			System.exit(-2);
			return false;
		}
		return true;
	}
	
	public static <T> boolean insererObjet(Connection connection, T objet) {
		
		boolean retour = false;
		
		StringBuilder requete = new StringBuilder("insert into " + objet.getClass().getSimpleName() + " (");
		Method method;
		try {
			method = objet.getClass().getDeclaredMethod("getNameFields");
			String[] arguments = (String[]) method.invoke(objet);
			for (String argument : arguments) {
				requete.append("\"" + argument.toLowerCase() + "\",");
			}
			requete.deleteCharAt(requete.length() - 1);
			requete.append(") values (");
			
			for (String argument : arguments) {
				
				method = objet.getClass()
						.getDeclaredMethod("get" + Character.toUpperCase(argument.charAt(0)) + argument.substring(1));
				
				if (method.invoke(objet) instanceof Integer)
					requete.append(method.invoke(objet) + ",");
				else
					requete.append("'" + method.invoke(objet) + "',");
			}
			
			requete.deleteCharAt(requete.length() - 1);
			requete.append(")");
			System.out.println("Requete : " + requete.toString());
			Statement st;
			st = connection.createStatement();
			retour = st.execute(requete.toString());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | SQLException e) {
			e.printStackTrace();
		}
		return retour;
	}
	
	public static <T> T recupererObjetParId(Connection connection, T objet, int idObjet) {
		
		T retour = objet;
		
		StringBuilder requete = new StringBuilder("select * from " + objet.getClass().getSimpleName().toLowerCase()
				+ " where " + objet.getClass().getSimpleName().toLowerCase() + "_id = " + idObjet);
		
		try {
			Method method;
			method = objet.getClass().getDeclaredMethod("getNameFields");
			String[] arguments = (String[]) method.invoke(objet);
			System.out.println("Requete : " + requete.toString());
			Statement st;
			st = connection.createStatement();
			ResultSet rs = st.executeQuery(requete.toString());
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
					String columnType = rsmd.getColumnTypeName(i);
					System.out.println(columnType);
					method = objet.getClass().getDeclaredMethod(
							"set" + Character.toUpperCase(arguments[i].charAt(0)) + arguments[i].substring(1));
					switch (columnType) {
					case "serial":
					case "int4":
						method.invoke(objet, rs.getInt(i));
						break;
					case "varchar":
						method.invoke(objet, rs.getString(i));
						break;
					case "timestamp":
						String date = rs.getString(i);
						method.invoke(objet, LocalDate.of(Integer.parseInt(date.substring(0, 4)),
								Integer.parseInt(date.substring(6, 8)), Integer.parseInt(date.substring(10, 12))));
						break;
					}
				}
			}
		} catch (IllegalAccessException | InvocationTargetException | SecurityException | NoSuchMethodException
				| IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}
		
		return retour;
	}
	
}
