package com.essensbestellung.Digitale_Essensbestellung;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.essensbestellung.entities.Location;
import com.essensbestellung.repository.ILocationRepository;
import com.essensbestellung.service.impl.LocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

class LocationServiceImplTest {

    @Mock
    private ILocationRepository locationRepository; // Mock für das Repository

    @InjectMocks
    private LocationServiceImpl locationService; // Service mit gemocktem Repository

   
    @BeforeEach
    void setUp() {
        // Initialisiere Mocks und injiziere sie in den Service
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnLocationWhenIdExists() {
        // Arrange
        Long locationId = 1L;
        Location mockLocation = new Location(locationId, "Test Location", "Test Address", null, null);
        when(locationRepository.findById(locationId)).thenReturn(Optional.of(mockLocation));

        // Act
        Location result = locationService.getLocationbyId(locationId);

        // Assert
        assertNotNull(result, "Location sollte zurückgegeben werden.");
        assertEquals(locationId, result.getId(), "Die ID sollte übereinstimmen.");
        assertEquals("Test Location", result.getLocationName(), "Der Name sollte korrekt sein.");
    }

    @Test
    void shouldReturnNullWhenIdDoesNotExist() {
        // Arrange
        Long locationId = 99L;
        when(locationRepository.findById(locationId)).thenReturn(Optional.empty());

        // Act
        Location result = locationService.getLocationbyId(locationId);

        // Assert
        assertNull(result, "Wenn die Location nicht existiert, sollte null zurückgegeben werden.");
    }

    @Test
    void shouldSaveLocation() {
        // Arrange
        Location locationToSave = new Location(null, "New Location", "New Address", null, null);
        Location savedLocation = new Location(1L, "New Location", "New Address", null, null);
        when(locationRepository.save(locationToSave)).thenReturn(savedLocation);

        // Act
        Location result = locationService.saveLocation(locationToSave);

        // Assert
        assertNotNull(result, "Die gespeicherte Location sollte zurückgegeben werden.");
        assertEquals(1L, result.getId(), "Die gespeicherte Location sollte eine ID haben.");
    }

    @Test
    void shouldReturnAllLocations() {
        // Arrange
        List<Location> mockLocations = List.of(
            new Location(1L, "Location 1", "Address 1", null, null),
            new Location(2L, "Location 2", "Address 2", null, null)
        );
        when(locationRepository.findAll()).thenReturn(mockLocations);

        // Act
        List<Location> result = locationService.getAllLocations();

        // Assert
        assertNotNull(result, "Die Liste der Locations sollte nicht null sein.");
        assertEquals(2, result.size(), "Die Liste sollte 2 Locations enthalten.");
    }

    @Test
    void shouldDeleteLocationWhenIdExists() {
        // Arrange
        Long locationId = 1L;
        Location mockLocation = new Location(locationId, "Location to delete", "Address", null, null);
        when(locationRepository.findById(locationId)).thenReturn(Optional.of(mockLocation));

        // Act
        locationService.deleteLocation(locationId);

        // Assert
        verify(locationRepository, times(1)).delete(mockLocation);
    }

    @Test
    void shouldNotDeleteLocationWhenIdDoesNotExist() {
        // Arrange
        Long locationId = 99L;
        when(locationRepository.findById(locationId)).thenReturn(Optional.empty());

        // Act
        locationService.deleteLocation(locationId);

        // Assert
        verify(locationRepository, never()).delete(any());
    }
}
