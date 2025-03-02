package com.essensbestellung.controller;

import java.util.List;

import com.essensbestellung.dto.DtoUser;
import com.essensbestellung.dto.DtoUserIU;

public interface IUserController {

	public DtoUser getUserbyId(Long id);

//	public DtoUser saveUser(DtoUserIU dtoUserIU);

	public List<DtoUser> getAllUsers();
	
	public void deleteOrder(Long id);
	
	public DtoUserIU updateUser(Long userId, DtoUser updateRequest);
	
//	public User updateUser(Long id,User updateUser);
}
