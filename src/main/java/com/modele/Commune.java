package com.modele;

public class Commune {
	private String code_commune_INSEE;
	private String nom_commune;
	private String code_postal;
	private String libelle_acheminement;
	private String ligne_5;
	private String latitude;
	private String longitude;
	
	
	
	public String getCode_commune_INSEE() {
		return code_commune_INSEE;
	}
	public void setCode_commune_INSEE(String code_commune_INSEE) {
		this.code_commune_INSEE = code_commune_INSEE;
	}
	public String getNom_commune() {
		return nom_commune;
	}
	public void setNom_commune(String nom_commune) {
		this.nom_commune = nom_commune;
	}
	public String getCode_postal() {
		return code_postal;
	}
	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}
	public String getLibelle_acheminement() {
		return libelle_acheminement;
	}
	public void setLibelle_acheminement(String libelle_acheminement) {
		this.libelle_acheminement = libelle_acheminement;
	}
	public String getLigne_5() {
		return ligne_5;
	}
	public void setLigne_5(String ligne_5) {
		this.ligne_5 = ligne_5;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Commune() {
		this.code_commune_INSEE = null;
		this.code_postal = "";
		this.latitude = "";
		this.libelle_acheminement = "";
		this.ligne_5 = "";
		this.longitude = "";
		this.nom_commune = "";
	}
	
	
	
	
	
}
