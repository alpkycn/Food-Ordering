package com.essensbestellung.service;

import java.util.List;

import com.essensbestellung.entities.GruppenMitglieder;
import com.essensbestellung.entities.Gruppen;

public interface IGruppenMitgliederService 
{
//	public GruppenMitglieder getGruppenMitgliederbyId(Long id);
	
	public GruppenMitglieder saveGruppenMitglieder(GruppenMitglieder gruppenMitglieder);
	
	public List<GruppenMitglieder> getAllGruppenMitglieders();

	public Gruppen getGroupByUserId(Long userid);
	
//	public void deleteGruppenMitglieder(Long id);
}
