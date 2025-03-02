package com.essensbestellung.controller;

import java.util.List;

import com.essensbestellung.entities.GruppenMitglieder;

public interface IGruppenMitgliederController 
{
//	 public GruppenMitglieder getGruppenMitgliederbyId(Long id);
	 
	 public GruppenMitglieder saveGruppenMitglieder(GruppenMitglieder gruppenMitglieder);
	 
	 public List<GruppenMitglieder> getAllGruppenMitglieders();
	 
//	 public void deleteGruppenMitglieder(Long id);
}
