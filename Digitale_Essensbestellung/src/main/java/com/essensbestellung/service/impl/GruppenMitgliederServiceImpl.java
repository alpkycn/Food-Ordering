package com.essensbestellung.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.essensbestellung.entities.Gruppen;
import com.essensbestellung.entities.GruppenMitglieder;
import com.essensbestellung.repository.IGruppenMitgliederRepository;
import com.essensbestellung.service.IGruppenMitgliederService;


@Service
public class GruppenMitgliederServiceImpl implements IGruppenMitgliederService
{
	@Autowired
	private IGruppenMitgliederRepository gruppenMitgliederRepository;

/*	public GruppenMitglieder getGruppenMitgliederbyId(Long id)
	{
		Optional<GruppenMitglieder> optional = gruppenMitgliederRepository.findById(id);
		
		if(optional.isEmpty())
		{
			return null;
		}
		
		return optional.get();	
	}
*/	
	public GruppenMitglieder saveGruppenMitglieder(GruppenMitglieder gruppenMitglieder)
	{
		return gruppenMitgliederRepository.save(gruppenMitglieder);
	}

	public List<GruppenMitglieder> getAllGruppenMitglieders()
	{
		List<GruppenMitglieder> gruppenMitglieders = gruppenMitgliederRepository.findAll();
		
		return gruppenMitglieders;
	}
	public Gruppen getGroupByUserId(Long userId) {
        return gruppenMitgliederRepository.findGroupByUserId(userId);
    }
	
/*	public void deleteGruppenMitglieder(Long id)
	{
		GruppenMitglieder gruppenMitglieder = getGruppenMitgliederbyId(id);
		
		if(gruppenMitglieder != null)
		{
			gruppenMitgliederRepository.delete(gruppenMitglieder);
		}
	}
*/	
}
