package com.essensbestellung.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.essensbestellung.entities.Gruppen;



@Repository
public interface IGruppenRepository extends JpaRepository<Gruppen, Long>
{

    @Query("SELECT g FROM Gruppen g WHERE g.groupLeader.id = :groupLeaderid")
    Gruppen getGroupByGroupManager(@Param("groupLeaderid") Long groupLeaderid);

    @Query("""
       SELECT g 
       FROM SubstituteLeaders s
       JOIN s.group g
       WHERE g.groupLeader.id = :groupLeaderid
         AND s.substitutionDate = CURRENT_DATE
       """)
    List<Gruppen> getGroupsWithSubstitutionForToday(@Param("groupLeaderid") Long groupLeaderid);




}
