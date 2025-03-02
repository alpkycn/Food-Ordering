package com.essensbestellung.controller.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.essensbestellung.controller.IGruppenController;
import com.essensbestellung.entities.Gruppen;
import com.essensbestellung.entities.SubstituteLeaders;
import com.essensbestellung.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essensbestellung.service.IGruppenService;

import org.springframework.web.bind.annotation.CrossOrigin;   //von Justin

@CrossOrigin(origins = {"http://172.26.92.152", "http://172.26.92.152:80", "http://172.26.92.152:8080"}) //von Justin

@RestController
@RequestMapping("/rest/api/gruppen")
public class GruppenControllerImpl 
{
	 @Autowired
	 private IGruppenService gruppenService;

	 @GetMapping(path = "/list/{id}")
	 public Gruppen getGruppenbyId(@PathVariable(name = "id") Long id)
	 {
		 return gruppenService.getGruppenbyId(id);
	 }
	 
	 @GetMapping("/{groupId}/members")
	 public ResponseEntity<List<User>> getMembersByGroup(@PathVariable Long groupId) 
	 {
	        List<User> members = gruppenService.getMembersByGroup(groupId);
	        if (members.isEmpty()) 
	        {
	            return ResponseEntity.noContent().build(); 
	        }
	        return ResponseEntity.ok(members); 
	 }
	 
	 @PostMapping(path = "/save")
	 public Gruppen saveGruppen(@RequestBody Gruppen gruppen)
	 {
		 return gruppenService.saveGruppen(gruppen);
	 }
	 
	 @GetMapping(path = "/list")
	 public List<Gruppen> getAllGruppen()
	 {
		 return gruppenService.getAllGruppen();
	 }
	 
	 @DeleteMapping(path = "/delete/{id}")
	 public void deleteGruppen(@PathVariable(name = "id") Long id)
	 {
		 gruppenService.deleteGruppe(id);
	 }

	 @GetMapping(path = "/group-leader/{leaderId}")
    public ResponseEntity<List<Gruppen>> getGroupsByGroupLeader(@PathVariable(name = "leaderId") Long leaderId) {
        List<Gruppen> result = new ArrayList<>();

        // Fetch the leader's group
        Gruppen leaderGroup = gruppenService.getGroupByGroupLeader(leaderId);
        if (leaderGroup != null) {
            result.add(leaderGroup);
        }

        // Fetch substitution groups and add them to the result
        List<Gruppen> substitutionGroups = gruppenService.getSubstitutionGroupsForToday(leaderId);
        result.addAll(substitutionGroups);

        // Return the response
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

	@PostMapping("/{groupId}/substitutions")
	public ResponseEntity<SubstituteLeaders> addSubstitution(
        @PathVariable Long groupId,
        @RequestParam Long substituteLeaderId,
        @RequestParam("substitutionDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate substitutionDate) {
    	SubstituteLeaders substitution = gruppenService.addSubstitution(groupId, substituteLeaderId, substitutionDate);
    return ResponseEntity.ok(substitution);
}
}
