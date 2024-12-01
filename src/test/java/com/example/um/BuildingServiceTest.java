package com.example.um;

import com.example.um.Building.Building;
import com.example.um.Building.BuildingDTO;
import com.example.um.Building.BuildingRepository;
import com.example.um.Building.BuildingService;
import com.example.um.Campus.Campus;
import com.example.um.Campus.CampusRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuildingServiceTest {

    @Mock
    private BuildingRepository buildingRepository;

    @Mock
    private CampusRepository campusRepository;

    @InjectMocks
    private BuildingService buildingService;

    BuildingServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBuilding_Success() {
        // Mock input
        BuildingDTO buildingDTO = new BuildingDTO(1L,"B101", 2020, 1L);
//        buildingDTO.setCode("B101");
//        buildingDTO.setYearOfConstruction(2020);
//        buildingDTO.setCampusId(1L);

        Campus campus = new Campus("Test Campus", "Test City");
        when(campusRepository.findById(1L)).thenReturn(Optional.of(campus));

        Building savedBuilding = new Building();
        savedBuilding.setId(1L);
        savedBuilding.setCode("B101");
        savedBuilding.setYearOfConstruction(2020);
        savedBuilding.setCampus(campus);

        when(buildingRepository.save(any(Building.class))).thenReturn(savedBuilding);

        // Call service
        Building createdBuilding = buildingService.createBuilding(buildingDTO);

        // Verify results
        assertNotNull(createdBuilding);
        assertEquals("B101", createdBuilding.getCode());
        assertEquals(2020, createdBuilding.getYearOfConstruction());
        assertEquals("Test Campus", createdBuilding.getCampus().getName());

        verify(campusRepository, times(1)).findById(1L);
        verify(buildingRepository, times(1)).save(any(Building.class));
    }

    @Test
    void createBuilding_CampusNotFound() {
        // Mock input
        BuildingDTO buildingDTO = new BuildingDTO(1L,"B101", 2020, 1L);
//        buildingDTO.setCode("B101");
//        buildingDTO.setYearOfConstruction(2020);
//        buildingDTO.setCampusId(1L);

        when(campusRepository.findById(1L)).thenReturn(Optional.empty());

        // Call service and verify exception
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            buildingService.createBuilding(buildingDTO);
        });

        assertEquals("Campus not found", exception.getMessage());
        verify(campusRepository, times(1)).findById(1L);
        verify(buildingRepository, never()).save(any(Building.class));
    }

    @Test
    void findBuildingById_Success() {
        // Mock input
        Building building = new Building();
        building.setId(1L);
        building.setCode("B101");

        when(buildingRepository.findById(1L)).thenReturn(Optional.of(building));

        // Call service
        Optional<Building> foundBuilding = buildingService.findBuildingById(1L);

        // Verify results
        assertTrue(foundBuilding.isPresent());
        assertEquals("B101", foundBuilding.get().getCode());

        verify(buildingRepository, times(1)).findById(1L);
    }

    @Test
    void deleteBuilding_BuildingNotFound() {
        // Mock input
        when(buildingRepository.findById(1L)).thenReturn(Optional.empty());

        // Call service and verify exception
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            buildingService.deleteBuilding(1L);
        });

        assertEquals("Building not found", exception.getMessage());
        verify(buildingRepository, times(1)).findById(1L);
        verify(buildingRepository, never()).delete(any(Building.class));
    }
}