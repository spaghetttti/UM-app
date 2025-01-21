package com.example.um.Component;
import com.example.um.Building.Building;
import com.example.um.Building.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ComponentService {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    public List<Component> findAllComponents() {
        return componentRepository.findAll();
    }

    public Optional<Component> findComponentById(Long id) {
        return componentRepository.findById(id);
    }

    public Component saveComponent(Component component) {
        return componentRepository.save(component);
    }

    public Component createComponent(ComponentDTO componentDTO) {
        if (componentDTO.getAcronym() == null || componentDTO.getAcronym().isEmpty()) {
            throw new IllegalArgumentException("Component acronym cannot be null or empty");
        }
        if (componentDTO.getName() == null || componentDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Component name cannot be null or empty");
        }
        if (componentDTO.getResponsiblePerson() == null || componentDTO.getResponsiblePerson().isEmpty()) {
            throw new IllegalArgumentException("Responsible person cannot be null or empty");
        }

        Component createdComponent = new Component();
        createdComponent.setAcronym(componentDTO.getAcronym());
        createdComponent.setName(componentDTO.getName());
        createdComponent.setResponsiblePerson(componentDTO.getResponsiblePerson());

        if (componentDTO.getBuildingIds() != null) {
            Set<Building> buildings = new HashSet<>(buildingRepository.findAllById(componentDTO.getBuildingIds()));
            createdComponent.setExploitedBuildings(buildings);
        }

        return componentRepository.save(createdComponent);
    }

    public Component updateComponent(Long id, ComponentDTO componentDTO) {
        // Validate the input DTO
        if (id == null) {
            throw new IllegalArgumentException("Component ID cannot be null");
        }

        // Fetch the existing component from the repository
        Component existingComponent = componentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Component not found with id: " + id));

        // Update fields with null checking and data validation
        if (componentDTO.getAcronym() != null && !componentDTO.getAcronym().isEmpty()) {
            existingComponent.setAcronym(componentDTO.getAcronym());
        }
        if (componentDTO.getName() != null && !componentDTO.getName().isEmpty()) {
            existingComponent.setName(componentDTO.getName());
        }
        if (componentDTO.getResponsiblePerson() != null && !componentDTO.getResponsiblePerson().isEmpty()) {
            existingComponent.setResponsiblePerson(componentDTO.getResponsiblePerson());
        }

        // Update exploited buildings if provided
        if (componentDTO.getBuildingIds() != null) {
            Set<Building> buildings = new HashSet<>(buildingRepository.findAllById(componentDTO.getBuildingIds()));
            existingComponent.setExploitedBuildings(buildings);
        }

        // Save the updated component
        return componentRepository.save(existingComponent);
    }
    public void deleteComponent(Long id) {
        componentRepository.deleteById(id);
    }
}