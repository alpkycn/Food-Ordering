package com.essensbestellung.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essensbestellung.controller.ILocationController;
import com.essensbestellung.entities.Location;
import com.essensbestellung.service.ILocationService;

@RestController
@RequestMapping("/rest/api/location")
public class LocationControllerImpl implements ILocationController
{
	 @Autowired
	 private ILocationService locationService;

	 @GetMapping(path = "/list/{id}")
	 public Location getLocationbyId(@PathVariable(name = "id") Long id)
	 {
		 return locationService.getLocationbyId(id);
	 }
	 
	 @PostMapping(path = "/save")
	 public Location saveLocation(@RequestBody Location location)
	 {
		 return locationService.saveLocation(location);
	 }
	 
	 @GetMapping(path = "/list")
	 public List<Location> getAllLocations()
	 {
		 return locationService.getAllLocations();
	 }
	 
	 @DeleteMapping(path = "/delete/{id}")
	 public void deleteLocation(@PathVariable(name = "id") Long id)
	 {
		 locationService.deleteLocation(id);
	 }
}
