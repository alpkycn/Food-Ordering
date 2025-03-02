package com.essensbestellung.controller;


import com.essensbestellung.entities.User;
import com.essensbestellung.jwt.AuthRequest;
import com.essensbestellung.jwt.AuthResponse;


public interface IAuthController 
{
	public AuthResponse authenticate(AuthRequest request);
	
	public User register(AuthRequest request);
}
