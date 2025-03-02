package com.essensbestellung.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essensbestellung.entities.Location;
import com.essensbestellung.repository.ILocationRepository;
import com.essensbestellung.service.ILocationService;

@Service
public class LocationServiceImpl implements ILocationService
{
	@Autowired
	private ILocationRepository locationRepository;

	public Location getLocationbyId(Long id)
	{
		Optional<Location> optional = locationRepository.findById(id);
		
		if(optional.isEmpty())
		{
			return null;
		}
		
		return optional.get();	
	}
	
	public Location saveLocation(Location location)
	{
		return locationRepository.save(location);
	}

	public List<Location> getAllLocations()
	{
		List<Location> locationList = locationRepository.findAll();
		
		return locationList;
	}
	
	public void deleteLocation(Long id)
	{
		Location location = getLocationbyId(id);
		
		if(location != null)
		{
			locationRepository.delete(location);
		}
	}
}
