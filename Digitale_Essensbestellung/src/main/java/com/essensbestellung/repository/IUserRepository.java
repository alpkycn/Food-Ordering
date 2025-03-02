package com.essensbestellung.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essensbestellung.dto.DtoUser;
import com.essensbestellung.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
	//@Query(value = "from User where username = :username")
		Optional<User> findByUsername(String username);

	// Check if a user exists by username
	  	boolean existsByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:name)")
    Optional<User> findUserByUsername(@Param("name") String name);
	
	@Query("SELECT new com.essensbestellung.dto.DtoUser(u.id, u.username, u.password, u.role, " +
	           "g.id, l.id, u.qrCode) " +
	           "FROM User u " +
	           "LEFT JOIN GruppenMitglieder gm ON gm.user.id = u.id " +
	           "LEFT JOIN Gruppen g ON g.id = gm.gruppe.id " +
	           "LEFT JOIN Location l ON l.siteManager.id = u.id")
	List<DtoUser> findAllUsersWithDetails();
	


}
