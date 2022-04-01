package com.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modele.Commune;

public class VilleBDDImpl {

	public static String getVilles(String codePostals) {
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		String villesDescription = "";
		String requete = null;
		Map<String, Object> map = new HashMap();
		
		try {
			DaoFactory daoFactory = DaoFactory.getInstance();
			connexion = daoFactory.getConnection();

			// Trouver les identifiants des équipes

			if (codePostals == null || codePostals.equals("")) {
				requete = "SELECT * FROM ville_france;";
				statement = connexion.createStatement();
				resultat = statement.executeQuery(requete);
				System.out.println("Requête envoyé");

				while (resultat.next()) {
					String codePostal = resultat.getString("Code_postal");
					String nom_commune = resultat.getString("Nom_commune");
					map.put(nom_commune, codePostal);
				}
			} else {
				String[] listCodePostal = codePostals.split(",");
				for (String codePostal : listCodePostal) {
					requete = "SELECT * FROM ville_france WHERE Code_postal = '" + codePostal + "';";
					statement = connexion.createStatement();
					resultat = statement.executeQuery(requete);
					
					while (resultat.next()) {
						String nom_commune = resultat.getString("Nom_commune");
						map.put(nom_commune, codePostal);
					}
				}
			}
			
			connexion.close();
		} catch (SQLException e) {
			return e.getMessage();
		}
		
		
		try {
			villesDescription =  new ObjectMapper().writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
		
		
		return villesDescription;
	}

	
	
	
	
	public static String ajouterVilles(Map<String, String> listModif) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Commune commune = new Commune();

		affecteParamToCommune(commune, listModif);

		try {
			DaoFactory daoFactory = DaoFactory.getInstance();
			connexion = daoFactory.getConnection();

			preparedStatement = connexion.prepareStatement(""
					+ "INSERT INTO ville_france(Code_commune_INSEE,Nom_commune,Code_postal,Libelle_acheminement,Ligne_5,Latitude,Longitude) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?);");
			preparedStatement.setString(1, commune.getCode_commune_INSEE());
			preparedStatement.setString(2, commune.getNom_commune());
			preparedStatement.setString(3, commune.getCode_postal());
			preparedStatement.setString(4, commune.getLibelle_acheminement());
			preparedStatement.setString(5, commune.getLigne_5());
			preparedStatement.setString(6, commune.getLatitude());
			preparedStatement.setString(7, commune.getLongitude());
			preparedStatement.executeUpdate();
			
			connexion.close();
		} catch (SQLException e) {
			return e.getMessage();
		} 

		return "Insertion passé en BDD";
	}

	
	
	
	
	
	private static void affecteParamToCommune(Commune commune, Map<String, String> listModif) {
		Object[] listKey = listModif.keySet().toArray();
		for (Object key : listKey) {
			switch ((String) key) {
				case "Nom_commune":
					commune.setNom_commune(listModif.get(key));
					break;
	
				case "Code_commune_INSEE":
					commune.setCode_commune_INSEE(listModif.get(key));
					break;
	
				case "Code_postal":
					commune.setCode_postal(listModif.get(key));
					break;
	
				case "Libelle_acheminement":
					commune.setLibelle_acheminement(listModif.get(key));
					break;
	
				case "Ligne_5":
					commune.setLigne_5(listModif.get(key));
					break;
	
				case "Latitude":
					commune.setLatitude(listModif.get(key));
					break;
	
				case "Longitude":
					commune.setLongitude(listModif.get(key));
					break;
			}
		}
	}
	
	
	public static Map<String, String> jsonToMap(String requete) throws JsonParseException, JsonMappingException, IOException { 
		Map<String, String> dictionnary = new ObjectMapper().readValue(requete, HashMap.class);
//		System.out.println(dictionnary.get("Code_commune_INSEE"));
//		System.out.println(dictionnary.keySet());
//		System.out.println(dictionnary.keySet().toArray()[0]);
		return dictionnary;
	}
	
	
	public static String modifVilleBDD(Map<String, String> listModif) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			DaoFactory daoFactory = DaoFactory.getInstance();
			connexion = daoFactory.getConnection();
			
			String preparedString = "UPDATE ville_france SET";
			Object[] listKey = listModif.keySet().toArray();
			for (Object key : listKey) {
				if(!key.equals("Code_commune_INSEE")) {
					preparedString += ", "+key + "='"+listModif.get(key)+"'";
				}
			}
			preparedString = preparedString.replaceFirst(",", "");
			preparedString += " WHERE Code_commune_INSEE='"+listModif.get("Code_commune_INSEE")+"';";

			preparedStatement = connexion.prepareStatement(preparedString);
			preparedStatement.executeUpdate();
			
			connexion.close();
		} catch (SQLException e) {
			return e.getMessage();
		}
		
		return "La commune a bien été modifié";
	}
	
	
	
	
	public static String supprVilleBDD(Map<String, String> listModif) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		
		try {
			DaoFactory daoFactory = DaoFactory.getInstance();
			connexion = daoFactory.getConnection();
			
			String preparedString = "DELETE FROM ville_france WHERE ";
			Object[] listKey = listModif.keySet().toArray();
			for (Object key : listKey) {
				if(key.equals("Code_commune_INSEE")) {
					preparedString += ""+ key + "='"+listModif.get(key)+"';";
					
				}
			}

			if(!preparedString.equals("DELETE FROM ville_france WHERE ")) {
				preparedStatement = connexion.prepareStatement(preparedString);
				preparedStatement.executeUpdate();
			}
			else {
				return "La requête n'est pas correct, merci d'utiliser la clé primaire";
			}
			
			
			connexion.close();
		} catch (SQLException e) {
			return e.getMessage();
		}
		
		return "La commune a bien été supprimé";
	}
	
	
	
}
