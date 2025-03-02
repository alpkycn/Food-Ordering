package com.essensbestellung.controller;

import java.util.List;


import com.essensbestellung.entities.Location;

public interface ILocationController 
{
	 public Location getLocationbyId(Long id);
	 
	 public Location saveLocation(Location location);
	 
	 public List<Location> getAllLocations();
	 
	 public void deleteLocation(Long id);
}
