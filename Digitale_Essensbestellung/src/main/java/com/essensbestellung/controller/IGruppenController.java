package com.essensbestellung.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.essensbestellung.entities.Gruppen;


public interface IGruppenController 
{
	 public Gruppen getGruppenById(Long id);
	 
	 public Gruppen saveGruppen(Gruppen gruppen);
	 
	 public List<Gruppen> getAllGruppen();
	 
	 public void deleteGruppen(Long id);

	 //public ResponseEntity<List<Gruppen>> getGroupsByGroupLeader(Long leaderId);
}
