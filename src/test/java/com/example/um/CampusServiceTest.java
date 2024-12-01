package com.example.um;

import com.example.um.Building.Building;
import com.example.um.Building.BuildingRepository;
import com.example.um.Campus.Campus;
import com.example.um.Campus.CampusDTO;
import com.example.um.Campus.CampusRepository;
import com.example.um.Campus.CampusService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CampusServiceTest {

    @Mock
    private CampusRepository campusRepository;

    @Mock
    private BuildingRepository buildingRepository;

    @InjectMocks
    private CampusService campusService;

    CampusServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCampus_Success() {
        // Mock input
        CampusDTO campusDTO = new CampusDTO();
        campusDTO.setName("Science Campus");
        campusDTO.setCity("Nimes");

        Campus savedCampus = new Campus("Science Campus", "Nimes");
        savedCampus.setId(1L);

        when(campusRepository.save(any(Campus.class))).thenReturn(savedCampus);

        // Call service
        Campus createdCampus = campusService.createCampus(campusDTO);

        // Verify results
        assertNotNull(createdCampus);
        assertEquals("Science Campus", createdCampus.getName());
        assertEquals("Nimes", createdCampus.getCity());

        verify(campusRepository, times(1)).save(any(Campus.class));
    }

    @Test
    void createCampus_MissingNameOrCity_ThrowsException() {
        // Mock input with missing name
        CampusDTO campusDTO = new CampusDTO();
        campusDTO.setCity("Nimes");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            campusService.createCampus(campusDTO);
        });

        assertEquals("Campus name cannot be null or empty", exception.getMessage());
        verify(campusRepository, never()).save(any(Campus.class));
    }

    @Test
    void updateCampus_Success() {
        // Mock input
        Long campusId = 1L;
        CampusDTO campusDTO = new CampusDTO();
        campusDTO.setName("Updated Campus");
        campusDTO.setCity("Updated City");

        Campus existingCampus = new Campus("Old Campus", "Old City");
        existingCampus.setId(campusId);

        when(campusRepository.findById(campusId)).thenReturn(Optional.of(existingCampus));
        when(campusRepository.save(any(Campus.class))).thenReturn(existingCampus);

        // Call service
        Campus updatedCampus = campusService.updateCampus(campusId, campusDTO);

        // Verify results
        assertNotNull(updatedCampus);
        assertEquals("Updated Campus", updatedCampus.getName());
        assertEquals("Updated City", updatedCampus.getCity());

        verify(campusRepository, times(1)).findById(campusId);
        verify(campusRepository, times(1)).save(any(Campus.class));
    }

    @Test
    void updateCampus_NotFound_ThrowsException() {
        // Mock input
        Long campusId = 1L;
        CampusDTO campusDTO = new CampusDTO();

        when(campusRepository.findById(campusId)).thenReturn(Optional.empty());

        // Call service and verify exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            campusService.updateCampus(campusId, campusDTO);
        });

        assertEquals("Campus not found with id: 1", exception.getMessage());
        verify(campusRepository, times(1)).findById(campusId);
        verify(campusRepository, never()).save(any(Campus.class));
    }

    @Test
    void findCampusById_Success() {
        // Mock input
        Campus campus = new Campus("Science Campus", "Nimes");
        campus.setId(1L);

        when(campusRepository.findById(1L)).thenReturn(Optional.of(campus));

        // Call service
        Optional<Campus> foundCampus = campusService.findCampusById(1L);

        // Verify results
        assertTrue(foundCampus.isPresent());
        assertEquals("Science Campus", foundCampus.get().getName());

        verify(campusRepository, times(1)).findById(1L);
    }

    @Test
    void deleteCampus_Success() {
        // Mock input
        Long campusId = 1L;

        when(campusRepository.existsById(campusId)).thenReturn(true);

        // Call service
        campusService.deleteCampus(campusId);

        // Verify interactions
        verify(campusRepository, times(1)).deleteById(campusId);
    }

    @Test
    void deleteCampus_NotFound_ThrowsException() {
        // Mock input
        Long campusId = 1L;

        doThrow(new RuntimeException("Campus not found"))
                .when(campusRepository).deleteById(campusId);

        // Call service and verify exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            campusService.deleteCampus(campusId);
        });

        assertEquals("Campus not found", exception.getMessage());
        verify(campusRepository, times(1)).deleteById(campusId);
    }
}