package com.essensbestellung.controller.impl;

import java.util.List;

import com.essensbestellung.entities.GruppenMitglieder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essensbestellung.controller.IGruppenMitgliederController;
import com.essensbestellung.service.IGruppenMitgliederService;

@RestController
	@RequestMapping("/rest/api/gruppenmitglieder")
	public class GruppenMitgliederControllerImpl implements IGruppenMitgliederController
	{
		 @Autowired
		 private IGruppenMitgliederService gruppenMitgliederService;

/*		 @GetMapping(path = "/list/{id}")
		 public GruppenMitglieder getGruppenMitgliederbyId(@PathVariable(name = "id") Long id)
		 {
			 return gruppenMitgliederService.getGruppenMitgliederbyId(id);
		 }
*/		 
		 @PostMapping(path = "/save")
		 public GruppenMitglieder saveGruppenMitglieder(@RequestBody GruppenMitglieder gruppenMitglieder)
		 {
			 return gruppenMitgliederService.saveGruppenMitglieder(gruppenMitglieder);
		 }
		 
		 @GetMapping(path = "/list/{id}")
		 public List<GruppenMitglieder> getAllGruppenMitglieders()
		 {
			 return gruppenMitgliederService.getAllGruppenMitglieders();
		 }
		 
/*		 @DeleteMapping(path = "/delete/{id}")
		 public void deleteGruppenMitglieder(@PathVariable(name = "id") Long id)
		 {
			 gruppenMitgliederService.deleteGruppenMitglieder(id);
		 }
*/		 
	}

