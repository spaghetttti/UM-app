package com.example.um;

import com.example.um.Building.Building;
import com.example.um.Building.BuildingRepository;
import com.example.um.Component.Component;
import com.example.um.Component.ComponentDTO;
import com.example.um.Component.ComponentRepository;
import com.example.um.Component.ComponentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComponentServiceTest {

    @Mock
    private ComponentRepository componentRepository;

    @Mock
    private BuildingRepository buildingRepository;

    @InjectMocks
    private ComponentService componentService;

    ComponentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createComponent_Success() {
        // Mock input
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setAcronym("CS");
        componentDTO.setName("Computer Science");
        componentDTO.setResponsiblePerson("Dr. Alice");
        componentDTO.setBuildingIds(List.of(1L, 2L));

        Building building1 = new Building();
        building1.setId(1L);
        Building building2 = new Building();
        building2.setId(2L);

        when(buildingRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(building1, building2));

        Component savedComponent = new Component();
        savedComponent.setId(1L);
        savedComponent.setAcronym("CS");
        savedComponent.setName("Computer Science");
        savedComponent.setResponsiblePerson("Dr. Alice");
        savedComponent.setExploitedBuildings(new HashSet<>(List.of(building1, building2)));

        when(componentRepository.save(any(Component.class))).thenReturn(savedComponent);

        // Call service
        Component createdComponent = componentService.createComponent(componentDTO);

        // Verify results
        assertNotNull(createdComponent);
        assertEquals("CS", createdComponent.getAcronym());
        assertEquals("Computer Science", createdComponent.getName());
        assertEquals("Dr. Alice", createdComponent.getResponsiblePerson());
        assertEquals(2, createdComponent.getExploitedBuildings().size());

        verify(buildingRepository, times(1)).findAllById(List.of(1L, 2L));
        verify(componentRepository, times(1)).save(any(Component.class));
    }

    @Test
    void createComponent_MissingFields_ThrowsException() {
        // Mock input with missing fields
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setName("Computer Science");

        // Call service and verify exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            componentService.createComponent(componentDTO);
        });

        assertEquals("Component acronym cannot be null or empty", exception.getMessage());
        verify(componentRepository, never()).save(any(Component.class));
    }

    @Test
    void updateComponent_Success() {
        // Mock input
        Long componentId = 1L;
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setAcronym("NEW_CS");
        componentDTO.setName("Updated Computer Science");
        componentDTO.setResponsiblePerson("Dr. Bob");
        componentDTO.setBuildingIds(List.of(1L));

        Component existingComponent = new Component();
        existingComponent.setId(componentId);
        existingComponent.setAcronym("CS");
        existingComponent.setName("Computer Science");
        existingComponent.setResponsiblePerson("Dr. Alice");

        Building building1 = new Building();
        building1.setId(1L);

        when(componentRepository.findById(componentId)).thenReturn(Optional.of(existingComponent));
        when(buildingRepository.findAllById(List.of(1L))).thenReturn(List.of(building1));
        when(componentRepository.save(any(Component.class))).thenReturn(existingComponent);

        // Call service
        Component updatedComponent = componentService.updateComponent(componentId, componentDTO);

        // Verify results
        assertNotNull(updatedComponent);
        assertEquals("NEW_CS", updatedComponent.getAcronym());
        assertEquals("Updated Computer Science", updatedComponent.getName());
        assertEquals("Dr. Bob", updatedComponent.getResponsiblePerson());
        assertEquals(1, updatedComponent.getExploitedBuildings().size());

        verify(componentRepository, times(1)).findById(componentId);
        verify(buildingRepository, times(1)).findAllById(List.of(1L));
        verify(componentRepository, times(1)).save(any(Component.class));
    }

    @Test
    void updateComponent_NotFound_ThrowsException() {
        // Mock input
        Long componentId = 1L;
        ComponentDTO componentDTO = new ComponentDTO();

        when(componentRepository.findById(componentId)).thenReturn(Optional.empty());

        // Call service and verify exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            componentService.updateComponent(componentId, componentDTO);
        });

        assertEquals("Component not found with id: 1", exception.getMessage());
        verify(componentRepository, times(1)).findById(componentId);
        verify(componentRepository, never()).save(any(Component.class));
    }

    @Test
    void findComponentById_Success() {
        // Mock input
        Component component = new Component();
        component.setId(1L);
        component.setAcronym("CS");

        when(componentRepository.findById(1L)).thenReturn(Optional.of(component));

        // Call service
        Optional<Component> foundComponent = componentService.findComponentById(1L);

        // Verify results
        assertTrue(foundComponent.isPresent());
        assertEquals("CS", foundComponent.get().getAcronym());

        verify(componentRepository, times(1)).findById(1L);
    }

    @Test
    void deleteComponent_Success() {
        // Mock input
        Long componentId = 1L;

        when(componentRepository.existsById(componentId)).thenReturn(true);

        // Call service
        componentService.deleteComponent(componentId);

        // Verify interactions
        verify(componentRepository, times(1)).deleteById(componentId);
    }
}