package com.essensbestellung.repository;

import com.essensbestellung.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILocationRepository extends JpaRepository<Location, Long> {
    
    @Query("SELECT l FROM Location l WHERE LOWER(l.locationName) = LOWER(:name)")
    Optional<Location> findLocationByName(@Param("name") String name);
}
