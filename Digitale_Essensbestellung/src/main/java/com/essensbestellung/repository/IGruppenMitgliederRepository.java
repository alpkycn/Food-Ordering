package com.essensbestellung.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essensbestellung.entities.GroupMemberId;
import com.essensbestellung.entities.Gruppen;
import com.essensbestellung.entities.GruppenMitglieder;


@Repository
public interface IGruppenMitgliederRepository extends JpaRepository<GruppenMitglieder, GroupMemberId>{
	
    List<GruppenMitglieder> findByGruppeId(Long groupId);

    @Query("SELECT gm.gruppe FROM GruppenMitglieder gm WHERE gm.user.id = :userId")
    Gruppen findGroupByUserId(@Param("userId") Long userId);
    
   
    @Modifying
    @Query("DELETE FROM GruppenMitglieder gm WHERE gm.id.userid = :userId")
    void deleteById_UserId(@Param("userId") Long userId);
    
    List<GruppenMitglieder> findById_Userid(Long userid);

}