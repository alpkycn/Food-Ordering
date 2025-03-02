package com.essensbestellung.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essensbestellung.dto.DtoUser;
import com.essensbestellung.dto.DtoUserIU;
import com.essensbestellung.entities.GroupMemberId;
import com.essensbestellung.entities.Gruppen;
import com.essensbestellung.entities.GruppenMitglieder;
import com.essensbestellung.entities.Location;
import com.essensbestellung.entities.User;
import com.essensbestellung.enums.Role;
import com.essensbestellung.repository.IGruppenMitgliederRepository;
import com.essensbestellung.repository.IGruppenRepository;
import com.essensbestellung.repository.ILocationRepository;
import com.essensbestellung.repository.IUserRepository;
import com.essensbestellung.service.IUserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
    private IUserRepository userRepository;

	@Autowired
	private IGruppenMitgliederRepository gruppenMitgliederRepository;
	
	@Autowired
	private IGruppenRepository gruppenRepository;
	
    @Autowired
	private ILocationRepository locationRepository;
	
	
	public DtoUser getUserbyId(Long id) {
	    Optional<User> optional = userRepository.findById(id);
	    
	    if (optional.isEmpty()) {
	        return null; 
	    }
	    
	    User user = optional.get(); 
	    DtoUser dtoUser = new DtoUser();
	    
	    BeanUtils.copyProperties(user, dtoUser);
	    return dtoUser;    
	}
	
	 public List<DtoUser> getAllUsersWithDetails() {
	        return userRepository.findAllUsersWithDetails();
	    }
	
	public DtoUser saveUser(DtoUserIU dtoUserIU)
	{
		
		DtoUser dtoUser = new DtoUser();
		User user = new User();
		
		BeanUtils.copyProperties(dtoUserIU, user);
		User dpUser = userRepository.save(user);
		
		BeanUtils.copyProperties(dpUser, dtoUser);
		
		return dtoUser;
	}


	public List<DtoUser> getAllUsers()
	{
		List<DtoUser> dtolist = new ArrayList<DtoUser>();
		
		List<User> UserList = userRepository.findAll();
		
		for(User user : UserList)
		{
			DtoUser dto = new DtoUser();
			BeanUtils.copyProperties(user, dto);
			
			dtolist.add(dto);
		}
		
		return dtolist;
		
	}
	
	public void deleteOrder(Long id)
	{
		getUserbyId(id);
		
		userRepository.deleteById(id);

	}

	public Gruppen getGruppe(Long id){

		return gruppenMitgliederRepository.findGroupByUserId(id);

	}
	
	@Override
	@Transactional
	public DtoUserIU updateUser(Long userId, DtoUser updateRequest) {

		User user = userRepository.findById(userId)
	        .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));


		if (updateRequest.getUsername() != null && !updateRequest.getUsername().isEmpty()) {
	        user.setUsername(updateRequest.getUsername());
	    }

		if (updateRequest.getRole() != null) {
		    user.setRole(updateRequest.getRole());
		}

	    if (updateRequest.getUsername() != null && !updateRequest.getUsername().isEmpty()) {
	        user.setFullname(updateRequest.getUsername());
	    }

	    if (updateRequest.getGruppenId() != null) {
	        Long newGroupId = updateRequest.getGruppenId();

	        gruppenMitgliederRepository.deleteById_UserId(userId);

	        Gruppen newGroup = gruppenRepository.findById(newGroupId)
	            .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + newGroupId));

	        GruppenMitglieder newGroupMember = new GruppenMitglieder();
	        GroupMemberId newGroupMemberId = new GroupMemberId(newGroup.getId(), userId);

	        newGroupMember.setId(newGroupMemberId);
	        newGroupMember.setGruppe(newGroup);
	        newGroupMember.setUser(user);

	        gruppenMitgliederRepository.save(newGroupMember);
	    }

	    if (updateRequest.getLocationId() != null) {
	        Long newLocationId = updateRequest.getLocationId();

	        Location newLocation = locationRepository.findById(newLocationId)
	            .orElseThrow(() -> new IllegalArgumentException("Location not found with ID: " + newLocationId));

	        newLocation.setSiteManager(user);
	        locationRepository.save(newLocation);
	    }

	    userRepository.save(user);

	    DtoUserIU dto = new DtoUserIU();
	    BeanUtils.copyProperties(user, dto);

	    return dto;
	}

	

	
/*	public DtoUserIU updateUser(Long id, User updateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (updateRequest.getUsername() != null) {
            user.setUsername(updateRequest.getUsername());
        }

        if (updateRequest.getRole() != null) {
            user.setRole(updateRequest.getRole());
        }

        if (updateRequest.getGruppenId() != null) {
            Gruppen gruppe = gruppenRepository.findById(updateRequest.getGruppenId())
                    .orElseThrow(() -> new IllegalArgumentException("Group not found"));
            user.setGruppen(gruppe);
        }

        if (updateRequest.getLocationId() != null) {
            Location location = locationRepository.findById(updateRequest.getLocationId())
                    .orElseThrow(() -> new IllegalArgumentException("Location not found"));
            user.setLocation(location);
        }

        User updatedUser = userRepository.save(user);

        DtoUserIU dto = new DtoUserIU();
        BeanUtils.copyProperties(updatedUser, dto);
        return dto;
    }
*/
   
}

