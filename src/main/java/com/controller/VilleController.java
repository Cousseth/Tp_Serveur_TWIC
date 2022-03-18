package com.controller;

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

	// TODO :
	// fonction pour enregistrer un element dans la BDD
	@RequestMapping(value = "/ville", method = RequestMethod.POST)
	public String post(@RequestBody String requete) {
		String[] listParams = requete.split("&");
		return VilleBDDImpl.ajouterVilles(listParams);
		
	}
}