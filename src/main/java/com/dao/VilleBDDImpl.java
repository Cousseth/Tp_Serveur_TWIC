package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.modele.Commune;

public class VilleBDDImpl {

	public static String getVilles(String codePostals) {
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		String villesDescription = "";
		String requete = null;
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
					String nom_commune = resultat.getString("Nom_commune");
					villesDescription += "<p>Nom commune : " + nom_commune + "</p>";
				}
			} else {
				String[] listCodePostal = codePostals.split(",");
				for (String codePostal : listCodePostal) {
					requete = "SELECT * FROM ville_france WHERE Code_postal = '" + codePostal + "';";
					statement = connexion.createStatement();
					resultat = statement.executeQuery(requete);
					System.out.println("Requête envoyé");

					while (resultat.next()) {
						String nom_commune = resultat.getString("Nom_commune");
						villesDescription += "<p>Nom commune : " + nom_commune + "</p>";
					}
				}
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return villesDescription;
	}

	public static String ajouterVilles(String[] listParams) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		Commune commune = new Commune();

		affecteParamToCommune(commune, listParams);

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

		} catch (SQLException e) {
			return e.getMessage();
		}

		return "Insertion passé en BDD";
	}

	
	
	private static void affecteParamToCommune(Commune commune, String[] listParams) {
		for (String param : listParams) {
			String key = param.split("=")[0];
			String value = param.split("=")[1];
			switch (key) {
				case "Nom_commune":
					commune.setNom_commune(value);
					break;
	
				case "Code_commune_INSEE":
					commune.setCode_commune_INSEE(value);
					break;
	
				case "Code_postal":
					commune.setCode_postal(value);
					break;
	
				case "Libelle_acheminement":
					commune.setLibelle_acheminement(value);
					break;
	
				case "Ligne_5":
					commune.setLigne_5(value);
					break;
	
				case "Latitude":
					commune.setLatitude(value);
					break;
	
				case "Longitude":
					commune.setLongitude(value);
					break;
			}
		}
	}
}
