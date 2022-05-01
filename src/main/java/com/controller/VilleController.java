package com.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.VilleBDDImpl;

@RestController
public class VilleController {


	// fonction pour récupérer le contenu de la BDD
	@RequestMapping(value = "/ville", method = RequestMethod.GET)
	public String get(@RequestParam(required = false, value = "codePostal") String codePostals) {
		
		return VilleBDDImpl.getVilles(codePostals);
		
	}
	

	// fonction pour enregistrer un element dans la BDD
	@RequestMapping(value = "/ville", method = RequestMethod.POST)
	public String post(@RequestBody String requete) {
		try {
			return VilleBDDImpl.ajouterVilles(VilleBDDImpl.jsonToMap(requete));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}

		
	}
	

	// fonction pour modifier un element dans la BDD
	@RequestMapping(value = "/ville", method = RequestMethod.PUT)
	public String put(@RequestBody String requete) {
		try {
			return VilleBDDImpl.modifVilleBDD(VilleBDDImpl.jsonToMap(requete));
		} catch (IOException e) {
			return e.getMessage();
		}
		
	}
	
	
	// fonction pour supprimer un element dans la BDD
	@RequestMapping(value = "/ville", method = RequestMethod.DELETE)
	public String delete(@RequestBody String requete) {
		try {
			return VilleBDDImpl.supprVilleBDD(VilleBDDImpl.jsonToMap(requete));
		} catch (IOException e) {
			return e.getMessage();
		}
	}
}