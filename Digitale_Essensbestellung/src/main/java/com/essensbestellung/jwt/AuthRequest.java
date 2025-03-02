package com.essensbestellung.jwt;


import com.essensbestellung.entities.Gruppen;
import com.essensbestellung.entities.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest 
{
	private String username;
    private String password;
    private String role;
    private String fullname;
    private Gruppen gruppe;
    private Location location;

}
