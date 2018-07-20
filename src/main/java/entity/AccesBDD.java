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

import model.Choix;
import model.Liste;
import model.ListePersonnel;
import model.Personnel;
import model.Question;
import model.QuestionChoix;
import model.QuestionTexte;
import model.Questionnaire;
import model.RH;
import model.Reponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;

public class AccesBDD {
	
	/** Constructeur privé */
	private AccesBDD() {
		initDatabase();
	}
	
	/** Instance unique pré-initialisée */
	private static AccesBDD INSTANCE = new AccesBDD();
	
	/** Point d'accès pour l'instance unique du singleton */
	public static AccesBDD getInstance() {
		return INSTANCE;
	}
	
	private Connection connection;
	
	public Connection getConnection() {
		return connection;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		
		connection.close();
	}
	
	private void initDatabase() {
		this.createBDD();
		this.initConnection();
		this.createTables();
		this.remplirBDD();
	}
	
	private boolean initConnection() {
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
	
	public <T> int insererObjet(T objet) {
		
		int newIndex = 0;
		
		try {
			StringBuilder requete = new StringBuilder();
			Statement st;
			
			if (!(objet.getClass().getSimpleName().equals("Choix")
					|| objet.getClass().getSimpleName().equals("ListePersonnel")
					|| objet.getClass().getSimpleName().equals("QuestionnairePersonnel")
					|| objet.getClass().getSimpleName().equals("Reponse"))) {
				
				if (objet.getClass().getSimpleName().equals("QuestionTexte")
						|| objet.getClass().getSimpleName().equals("QuestionChoix"))
					requete.append("select max(question_id) from Question");
				else if (objet.getClass().getSimpleName().equals("RH"))
					requete.append("select max(personnel_id) from Personnel");
				else
					requete.append("select max(" + objet.getClass().getSimpleName().toLowerCase() + "_id) from "
							+ objet.getClass().getSimpleName().toLowerCase());
				
				st = connection.createStatement();
				ResultSet rs = st.executeQuery(requete.toString());
				rs.next();
				newIndex = rs.getInt(1) + 1;
			}
			requete = new StringBuilder("insert into " + objet.getClass().getSimpleName() + " (");
			Method method;
			method = objet.getClass().getDeclaredMethod("getFields");
			String[] arguments = (String[]) method.invoke(objet);
			for (String argument : arguments) {
				requete.append("\"" + argument.toLowerCase() + "\",");
			}
			requete.deleteCharAt(requete.length() - 1);
			requete.append(") values (");
			
			int indexInsert = newIndex;
			int choix_id = 0;
			for (String argument : arguments) {
				
				method = objet.getClass()
						.getDeclaredMethod("get" + Character.toUpperCase(argument.charAt(0)) + argument.substring(1));
				
				if (indexInsert > 0) {
					requete.append(indexInsert + ",");
					choix_id = indexInsert;
					indexInsert = 0;
				} else if (objet.getClass().getSimpleName().equals("QuestionChoix") && argument.equals("choix_id"))
					requete.append("'" + choix_id + "',");
				else
					requete.append("'" + method.invoke(objet) + "',");
			}
			
			requete.deleteCharAt(requete.length() - 1);
			requete.append(")");
			st = connection.createStatement();
			st.execute(requete.toString());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | SQLException e) {
			e.printStackTrace();
		}
		
		return newIndex;
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
	
	public <T> void majObjet(T objet) {
		
		try {
			Method method;
			method = objet.getClass().getDeclaredMethod("get" + objet.getClass().getSimpleName() + "_id");
			int idToMAJ = (int) method.invoke(objet);
			StringBuilder requete = new StringBuilder("update " + objet.getClass().getSimpleName() + " set ");
			
			method = objet.getClass().getDeclaredMethod("getFields");
			
			String[] arguments = (String[]) method.invoke(objet);
			
			for (String argument : arguments) {
				if (!(argument.substring(0, argument.length() - 3))
						.equals(objet.getClass().getSimpleName().toLowerCase())) {
					
					requete.append("" + argument.toLowerCase() + " = ");
					
					method = objet.getClass().getDeclaredMethod(
							"get" + Character.toUpperCase(argument.charAt(0)) + argument.substring(1));
					
					requete.append("'" + method.invoke(objet) + "',");
				}
			}
			
			requete.deleteCharAt(requete.length() - 1);
			
			requete.append(" where " + objet.getClass().getSimpleName().toLowerCase() + "_id = " + idToMAJ);
			
			Statement st = connection.createStatement();
			st.execute(requete.toString());
			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | SQLException e) {
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
				@SuppressWarnings("unchecked")
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
	
	public void createBDD() {
		// Connexion database postgreSQL
		String url = "jdbc:postgresql://localhost/";
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "postgres");
		
		try (
				// Create the connection to our database.
				Connection connection = DriverManager.getConnection(url, props);
				// Create statement.
				Statement st = connection.createStatement()) {
			st.execute("drop database if exists projet");
			
			st.execute("create database projet");
			
			url = "jdbc:postgresql://localhost/projet";
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createTables() {
		try (Statement st = connection.createStatement()) {
			st.execute(Questionnaire.getSchema());
			
			st.execute(Liste.getSchema());
			
			st.execute(Personnel.getSchema());
			
			st.execute(ListePersonnel.getSchema());
			
			st.execute(Question.getSchema());
			
			st.execute(QuestionChoix.getSchema());
			
			st.execute(QuestionTexte.getSchema());
			
			st.execute(Choix.getSchema());
			
			st.execute(Reponse.getSchema());
			
			st.execute(RH.getSchema());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void remplirBDD() {
		try (Statement st = connection.createStatement()) {
			ResultSet result = st.executeQuery("select count(*) AS nb from RH");
			result.next();
			if (result.getInt("nb") == 0) {
				st.executeUpdate("insert into RH values\r\n"
						+ "(1, 'Bréhu', 'Soraya', 'soraya.brehu@gmail.com', 'RH', 'linda', '1234'),\r\n"
						+ "(2, 'Ratodiarivony', 'Michaëlle', 'michaelle.ratodi@gmail.com', 'RH', 'michaelle', 'michaelle'),\r\n"
						+ "(3, 'Themelin', 'Mathieu', 'mat.themelin@hotmail.fr', 'RH', 'mathieu', 'mathieu')");

				st.executeUpdate("insert into Personnel values \r\n" 
					+ "(4, 'Hugo' ,'LLORIS','hugo@gmail.com', 'RH'),\r\n"
					+ "(5, 'Benjamin','PAVARD', 'berjamin@gmail.com', 'IT manager'),\r\n"
					+ "(6, 'Lucas','HERNANDEZ', 'lucas.tata@gmail.com', 'employee1'),\r\n"
					+ "(7, 'Steve' ,'MANDANDA', 'steve.tata@gmail.com', 'employee2'),\r\n"
					+ "(8, 'Benjamin','MENDY', 'b2@gmail.com', 'employee3'),\r\n"
					+ "(9, 'Samuel','UMTITI', 's1.tata@gmail.com', 'employee4'),\r\n"
					+ "(10, 'Adil','RAMI', 'a.r@gmail.com', 'employee5'),\r\n"
					+ "(11, 'Olivier','GIROUD', 'o.g.tata@gmail.com', 'employee6'),\r\n"
					+ "(12, 'Nabil','FEKIR', 'n.f@gmail.com', 'employee7'),\r\n"
					+ "(13, 'Steven','NZONZI', 's.n@gmail.com', 'employee8')");
			
				st.executeUpdate("insert into Questionnaire values\r\n"
					+"(1,'Employees Opinions','2018-07-18', '2018-07-30', 7),\r\n"
					+"(2,'Employees Survey','2018-07-18', '2018-07-30', 7),\r\n"
					+"(3,'Work Optimization','2018-07-18', '2018-07-30', 7)");
			
				st.executeUpdate("insert into QuestionChoix values\r\n"
					+"(1,1,'What do you think about the management of the enterprise ?',1,false),\r\n"
					+"(3,1,'What do you think about the operation of the enterprise ?',3,false),\r\n"
					+"(5,1,'What do you think about the collaboration with colleagues in the enterprise ?',5,false),\r\n"
					+"(7,1,'What do you think about your workspace ?',7,false),\r\n"
					+"(9,1,'Are you satisfied with the available tools ?',9,false)");
					
				st.executeUpdate("insert into QuestionTexte values\r\n"
						+ "(2,1,'Explain your answer about the management',80,4),\r\n"
						+ "(4,1,'Explain your answer about the operation',80,4),\r\n"
						+ "(6,1,'Explain your answer about the collaboration',80,4),\r\n"
						+ "(8,1,'Explain your answer about the workspace',80,4),\r\n"
						+ "(10,1,'If not, explain why',80,4)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try (Statement st = connection.createStatement()) {

			st.executeUpdate("insert into Choix values\r\n"
				+"(1,'Very satisfied'),\r\n"
				+"(1,'Moderately satisfied'),\r\n"
				+"(1,'Unsatisfied'),\r\n"
				+"(3,'Very satisfied'),\r\n"
				+"(3,'Moderately satisfied'),\r\n"
				+"(3,'Unsatisfied'),\r\n"
				+"(5,'Very satisfied'),\r\n"
				+"(5,'Moderately satisfied'),\r\n"
				+"(5,'Unsatisfied'),\r\n"
				+"(7,'Very satisfied'),\r\n"
				+"(7,'Moderately satisfied'),\r\n"
				+"(7,'Unsatisfied'),\r\n"
				+"(9,'Yes'),\r\n"
				+"(9,'No')");

			st.executeUpdate("insert into Liste values\r\n"
				+"(1,' All Employees '),\r\n"
				+"(2,' Human Resources'),\r\n"
				+"(3,' Hierarchical Direction ')");

			st.executeUpdate("insert into ListePersonnel values\r\n"
				+"(1,4),\r\n"
				+"(1,5),\r\n"
				+"(1,6),\r\n"
				+"(1,7),\r\n"
				+"(1,8),\r\n"
				+"(1,9),\r\n"
				+"(1,10),\r\n"
				+"(1,11),\r\n"
				+"(1,12),\r\n"
				+"(1,13),\r\n"
				+"(2,1),\r\n"
				+"(2,2),\r\n"
				+"(2,3)");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
