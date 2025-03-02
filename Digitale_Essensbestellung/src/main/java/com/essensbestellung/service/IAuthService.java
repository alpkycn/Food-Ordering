package com.essensbestellung.service;

import com.essensbestellung.dto.DtoUserIU;
import com.essensbestellung.jwt.AuthRequest;
import com.essensbestellung.jwt.AuthResponse;

public interface IAuthService 
{
	public AuthResponse authenticate(AuthRequest request);
	
	public DtoUserIU register(AuthRequest request);

}
