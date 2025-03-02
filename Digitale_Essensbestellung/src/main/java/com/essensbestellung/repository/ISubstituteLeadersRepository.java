package com.essensbestellung.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.essensbestellung.entities.SubstituteLeaders;

@Repository
public interface ISubstituteLeadersRepository extends JpaRepository<SubstituteLeaders, Long> {
   
}
