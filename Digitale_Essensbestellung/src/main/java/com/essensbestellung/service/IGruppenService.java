package com.essensbestellung.service;

import java.time.LocalDate;
import java.util.List;

import com.essensbestellung.entities.Gruppen;
import com.essensbestellung.entities.SubstituteLeaders;
import com.essensbestellung.entities.User;

public interface IGruppenService 
{
	public Gruppen getGruppenbyId(Long id);
	
	public Gruppen saveGruppen(Gruppen gruppen);
	
	public List<Gruppen> getAllGruppen();
	
	public void deleteGruppe(Long id);
	
	public List<User> getMembersByGroup(Long groupId);
	
	public Gruppen getGroupByGroupLeader(Long leaderID);

	public List<Gruppen> getSubstitutionGroupsForToday(Long leaderID);

	public SubstituteLeaders addSubstitution(Long groupId, Long substituteLeaderId, LocalDate substitutionDate);

}
