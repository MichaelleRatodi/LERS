package entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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

public class ManagerBDD {
	public static <T> boolean insererObjet(T objet) {
		
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
			st = AccesBDD.getInstance().getConnection().createStatement();
			retour = st.execute(requete.toString());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | SQLException e) {
			e.printStackTrace();
		}
		return retour;
	}
	
	public static <T> T recupererObjetParId(T objet, int idObjet) {
		
		T retour = objet;
		
		StringBuilder requete = new StringBuilder("select * from " + objet.getClass().getSimpleName().toLowerCase()
				+ " where " + objet.getClass().getSimpleName().toLowerCase() + "_id = " + idObjet);
		
		try {
			Method method;
			method = objet.getClass().getDeclaredMethod("getNameFields");
			String[] arguments = (String[]) method.invoke(objet);
			System.out.println("Requete : " + requete.toString());
			Statement st;
			st = AccesBDD.getInstance().getConnection().createStatement();
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
	
	public void createBDD() {
		try {
			Questionnaire.getSchema();
			Statement st;
			st = AccesBDD.getInstance().getConnection().createStatement();
			st.execute(Questionnaire.getSchema());
			
			Choix.getSchema();
			st = AccesBDD.getInstance().getConnection().createStatement();
			st.execute(Choix.getSchema());
			
			Liste.getSchema();
			st = AccesBDD.getInstance().getConnection().createStatement();
			st.execute(Liste.getSchema());
			
			ListePersonnel.getSchema();
			st = AccesBDD.getInstance().getConnection().createStatement();
			st.execute(ListePersonnel.getSchema());
			
			Personnel.getSchema();
			st = AccesBDD.getInstance().getConnection().createStatement();
			st.execute(Personnel.getSchema());
			
			Question.getSchema();
			st = AccesBDD.getInstance().getConnection().createStatement();
			st.execute(Question.getSchema());
			
			QuestionChoix.getSchema();
			st = AccesBDD.getInstance().getConnection().createStatement();
			st.execute(QuestionChoix.getSchema());
			
			QuestionTexte.getSchema();
			st = AccesBDD.getInstance().getConnection().createStatement();
			st.execute(QuestionTexte.getSchema());
			
			Reponse.getSchema();
			st = AccesBDD.getInstance().getConnection().createStatement();
			st.execute(Reponse.getSchema());
			
			RH.getSchema();
			st = AccesBDD.getInstance().getConnection().createStatement();
			st.execute(RH.getSchema());
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
