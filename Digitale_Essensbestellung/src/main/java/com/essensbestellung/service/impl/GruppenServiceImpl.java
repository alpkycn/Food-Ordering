package com.essensbestellung.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essensbestellung.entities.Gruppen;
import com.essensbestellung.entities.GruppenMitglieder;
import com.essensbestellung.entities.SubstituteLeaders;
import com.essensbestellung.entities.User;
import com.essensbestellung.repository.IGruppenMitgliederRepository;
import com.essensbestellung.repository.IGruppenRepository;
import com.essensbestellung.repository.ISubstituteLeadersRepository;
import com.essensbestellung.repository.IUserRepository;
import com.essensbestellung.service.IGruppenService;

@Service
public class GruppenServiceImpl implements IGruppenService
{
	@Autowired
	private IGruppenRepository gruppenRepository;
	
	@Autowired
	private IGruppenMitgliederRepository gruppenMitgliederRepository;

	@Autowired
    private ISubstituteLeadersRepository substituteLeadersRepository;

	@Autowired
    private IUserRepository userRepository;


	public Gruppen getGruppenbyId(Long id)
	{
		Optional<Gruppen> optional = gruppenRepository.findById(id);
		
		if(optional.isEmpty())
		{
			return null;
		}
		
		return optional.get();	
	}
	
	public List<User> getMembersByGroup(Long groupId) {
        List<GruppenMitglieder> groupMembers = gruppenMitgliederRepository.findByGruppeId(groupId);
        return groupMembers.stream()
                .map(gm -> gm.getUser())    
                .collect(Collectors.toList());
    }
	
	public Gruppen saveGruppen(Gruppen gruppen)
	{
		return gruppenRepository.save(gruppen);
	}

	public List<Gruppen> getAllGruppen()
	{
		List<Gruppen> gruppens = gruppenRepository.findAll();
		
		return gruppens;
	}


	public void deleteGruppe(Long id)
	{
		Gruppen gruppen = getGruppenbyId(id);
		
		if(gruppen != null)
		{
			gruppenRepository.delete(gruppen);
		}
	}

	@Override
    public Gruppen getGroupByGroupLeader(Long leaderID) {
        return gruppenRepository.getGroupByGroupManager(leaderID);
    }

    @Override
    public List<Gruppen> getSubstitutionGroupsForToday(Long leaderID) {
        return gruppenRepository.getGroupsWithSubstitutionForToday(leaderID);
    }

	@Override
    public SubstituteLeaders addSubstitution(Long groupId, Long substituteLeaderId, LocalDate substitutionDate) {
        
        Gruppen group = gruppenRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found for ID: " + groupId));

        
        User substituteLeader = userRepository.findById(substituteLeaderId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + substituteLeaderId));

        
        SubstituteLeaders substitution = new SubstituteLeaders();
        substitution.setGroup(group);
        substitution.setStellvertreter(substituteLeader);
        substitution.setSubstitutionDate(substitutionDate);

        return substituteLeadersRepository.save(substitution);
    }


}
