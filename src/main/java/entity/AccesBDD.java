package entity;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
//import org.postgresql.Driver;

public class AccesBDD {
	
	private static Connection connection;
	
	public Connection getConnection() {
		
		if (connection == null)
			initConnection();
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
	
	public <T> void insererObjet(T objet) {
		
		StringBuilder requete = new StringBuilder("insert into " + objet.getClass().getSimpleName() + " (");
		Method method;
		try {
			method = objet.getClass().getDeclaredMethod("getFields");
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
			Statement st;
			st = connection.createStatement();
			st.execute(requete.toString());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public <T> void supprimerObjet(T objet) {
		
		StringBuilder requete = new StringBuilder("delete from " + objet.getClass().getSimpleName() + " where "
				+ Character.toUpperCase(objet.getClass().getSimpleName().charAt(0))
				+ objet.getClass().getSimpleName().substring(1) + "_id = ");
		
		Method method;
		int id = 0;
		try {
			method = objet.getClass()
					.getDeclaredMethod("get" + Character.toUpperCase(objet.getClass().getSimpleName().charAt(0))
							+ objet.getClass().getSimpleName().substring(1) + "_id");
			id = (int) method.invoke(objet);
			requete.append("" + id);
			Statement st;
			st = connection.createStatement();
			st.execute(requete.toString());
		} catch (SQLException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
	public <T> T recupererObjetParId(T objet, int idObjet) throws InstantiationException, IllegalAccessException {
		
		try {
			StringBuilder requete = new StringBuilder("select * from " + objet.getClass().getSimpleName().toLowerCase()
					+ " where " + objet.getClass().getSimpleName().toLowerCase() + "_id = " + idObjet);
			
			Method method;
			method = objet.getClass().getDeclaredMethod("getFields");
			String[] arguments = (String[]) method.invoke(objet);
			Statement st;
			st = connection.createStatement();
			ResultSet rs = st.executeQuery(requete.toString());
			
			rs.next();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			for (int i = 0; i < arguments.length; i++) {
				String columnType = rsmd.getColumnTypeName(i + 1);
				switch (columnType) {
				case "serial":
				case "int4":
					method = objet.getClass().getDeclaredMethod(
							"set" + Character.toUpperCase(arguments[i].charAt(0)) + arguments[i].substring(1),
							int.class);
					method.invoke(objet, rs.getInt(i + 1));
					break;
				case "varchar":
					method = objet.getClass().getDeclaredMethod(
							"set" + Character.toUpperCase(arguments[i].charAt(0)) + arguments[i].substring(1),
							String.class);
					method.invoke(objet, rs.getString(i + 1));
					break;
				case "timestamp":
					method = objet.getClass().getDeclaredMethod(
							"set" + Character.toUpperCase(arguments[i].charAt(0)) + arguments[i].substring(1),
							LocalDate.class);
					String date = rs.getString(i + 1);
					method.invoke(objet, LocalDate.of(Integer.parseInt(date.substring(0, 4)),
							Integer.parseInt(date.substring(5, 7)), Integer.parseInt(date.substring(8, 10))));
					break;
				}
			}
		} catch (IllegalAccessException | InvocationTargetException | SecurityException | NoSuchMethodException
				| IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}
		return objet;
	}
	
	public <T> List<T> recupererTousObjets(T objet) throws InstantiationException, IllegalAccessException {
		
		List<T> retour = new ArrayList<T>();
		try {
			StringBuilder requete = new StringBuilder(
					"select * from " + objet.getClass().getSimpleName().toLowerCase());
			
			Method method;
			method = objet.getClass().getDeclaredMethod("getFields");
			String[] arguments = (String[]) method.invoke(objet);
			Statement st;
			st = connection.createStatement();
			ResultSet rs = st.executeQuery(requete.toString());
			ResultSetMetaData rsmd = rs.getMetaData();
			
			while (rs.next()) {
				T item = (T) objet.getClass().newInstance();
				for (int i = 0; i < arguments.length; i++) {
					String columnType = rsmd.getColumnTypeName(i + 1);
					switch (columnType) {
					case "serial":
					case "int4":
						method = item.getClass().getDeclaredMethod(
								"set" + Character.toUpperCase(arguments[i].charAt(0)) + arguments[i].substring(1),
								int.class);
						method.invoke(item, rs.getInt(i + 1));
						break;
					case "varchar":
						method = item.getClass().getDeclaredMethod(
								"set" + Character.toUpperCase(arguments[i].charAt(0)) + arguments[i].substring(1),
								String.class);
						method.invoke(item, rs.getString(i + 1));
						break;
					case "timestamp":
						method = item.getClass().getDeclaredMethod(
								"set" + Character.toUpperCase(arguments[i].charAt(0)) + arguments[i].substring(1),
								LocalDate.class);
						String date = rs.getString(i + 1);
						System.out.println(date);
						method.invoke(item, LocalDate.of(Integer.parseInt(date.substring(0, 4)),
								Integer.parseInt(date.substring(5, 7)), Integer.parseInt(date.substring(8, 10))));
						break;
					}
				}
				retour.add(item);
			}
		} catch (IllegalAccessException | InvocationTargetException | SecurityException | NoSuchMethodException
				| IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}
		return retour;
	}
	
}
