package com.essensbestellung.service;

import java.util.List;

import com.essensbestellung.entities.Location;

public interface ILocationService 
{
	public Location getLocationbyId(Long id);
	
	public Location saveLocation(Location location);
	
	public List<Location> getAllLocations();
	
	public void deleteLocation(Long id);
}
