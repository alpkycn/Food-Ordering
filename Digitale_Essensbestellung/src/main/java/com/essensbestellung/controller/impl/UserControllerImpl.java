package com.essensbestellung.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essensbestellung.controller.IUserController;
import com.essensbestellung.dto.DtoUser;
import com.essensbestellung.dto.DtoUserIU;
import com.essensbestellung.entities.Gruppen;
import com.essensbestellung.entities.User;
import com.essensbestellung.jwt.AuthRequest;
import com.essensbestellung.jwt.AuthResponse;
import com.essensbestellung.service.IAuthService;
import com.essensbestellung.service.IUserService;
import com.essensbestellung.enums.Role;
import org.springframework.web.bind.annotation.CrossOrigin; //von Justin

@CrossOrigin(origins = { "http://172.26.92.152", "http://172.26.92.152:80", "http://172.26.92.152:8080",
		"http://localhost:8081", "http://localhost:8080" }) // von Justin

@RestController
@RequestMapping("/rest/api/user")
public class UserControllerImpl implements IUserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IAuthService authService;

	@GetMapping(path = "/list/{id}")
	public DtoUser getUserbyId(@PathVariable(name = "id") Long id) {
		return userService.getUserbyId(id);
	}

	@GetMapping(path = "/role/{id}")
	public Role getUserRolebyId(@PathVariable(name = "id") Long id) {
		return userService.getUserbyId(id).getRole();
	}

	@GetMapping(path = "/gruppe/{id}")
	public Gruppen getUserGruppebyId(@PathVariable(name = "id") Long id) {
		return userService.getGruppe(id);
	}

	@PostMapping(path = "/save")
	public DtoUserIU saveUser(@RequestBody AuthRequest dtoUserIU) {
		return authService.register(dtoUserIU);
	}

	@GetMapping(path = "/list")
	public List<DtoUser> getAllUsers() {
        return userService.getAllUsersWithDetails();
    }

	
	@DeleteMapping(path = "/delete/{id}")
	public void deleteOrder(@PathVariable(name = "id") Long id) {
		userService.deleteOrder(id);
	}
	
	@PutMapping(path = "/update/{id}")
	public DtoUserIU updateUser(
	    @PathVariable(name = "id") Long userId,
	    @RequestBody DtoUser updateRequest
	) {
	    return userService.updateUser(userId, updateRequest);
	}


/*	@PutMapping(path = "/update/{id}")
	public DtoUserIU updateUser(@PathVariable(name = "id") Long id, @RequestBody User updateRequest) {
	    return userService.updateUser(id, updateRequest);
	}
*/	 
}
