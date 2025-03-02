package com.essensbestellung.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.essensbestellung.entities.Gruppen;
import com.essensbestellung.entities.Location;
import com.essensbestellung.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser {

	private Long id;

	private String username;

	private String password;

	private Role role;
	
    private Long gruppenId;

	private Long locationId;
		
    private byte[] qrCode; 

}
