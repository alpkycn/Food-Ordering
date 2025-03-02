package com.essensbestellung.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essensbestellung.dto.DtoUserIU;
import com.essensbestellung.jwt.AuthRequest;
import com.essensbestellung.jwt.AuthResponse;
import com.essensbestellung.service.IAuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;   //von Justin

@CrossOrigin(origins = {"http://172.26.92.152", "http://172.26.92.152:80", "http://172.26.92.152:8080", "http://localhost:8081", "http://localhost:8080", "http://localhost:8081/"}) //von Justin
@RestController
@RequestMapping("/rest/api/auth")
public class AuthController 
{
    @Autowired
	private IAuthService authService;

    @PostMapping("/register")
	public DtoUserIU register(@Valid @RequestBody AuthRequest request)
	{
		return authService.register(request);
	}
 
    @PostMapping("/authenticate")
	public AuthResponse authenticate(@Valid @RequestBody AuthRequest request) 
    {

		return authService.authenticate(request);
	}
}

	

