package com.example.um.Campus;

import com.example.um.Building.Building;
import com.example.um.Building.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CampusService {

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    public List<Campus> findAllCampuses() {
        return campusRepository.findAll();
    }

    public Optional<Campus> findCampusById(Long id) {
        return campusRepository.findById(id);
    }

    public Campus saveCampus(Campus campus) {
        return campusRepository.save(campus);
    }

    public Campus createCampus(CampusDTO campusDTO) {
        // Validate the input DTO
        if (campusDTO.getName() == null || campusDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Campus name cannot be null or empty");
        }
        if (campusDTO.getCity() == null || campusDTO.getCity().isEmpty()) {
            throw new IllegalArgumentException("Campus city cannot be null or empty");
        }

        // Create a new Campus entity
        Campus createdCampus = new Campus();
        createdCampus.setName(campusDTO.getName());
        createdCampus.setCity(campusDTO.getCity());

        // If building IDs are provided, fetch and associate them
        if (campusDTO.getBuildingIds() != null) {
            Set<Building> buildings = new HashSet<>(buildingRepository.findAllById(campusDTO.getBuildingIds()));
            createdCampus.setBuildings(buildings);
        }

        // Save the campus to the repository
        return campusRepository.save(createdCampus);
    }

    public Campus updateCampus(CampusDTO campusDTO) {
        // Validate the input DTO
        if (campusDTO.getId() == null) {
            throw new IllegalArgumentException("Campus ID cannot be null");
        }

        // Fetch the existing campus from the repository
        Campus existingCampus = campusRepository.findById(campusDTO.getId())
                .orElseThrow(() -> new RuntimeException("Campus not found with id: " + campusDTO.getId()));

        // Update fields with null checking and data validation
        if (campusDTO.getName() != null && !campusDTO.getName().isEmpty()) {
            existingCampus.setName(campusDTO.getName());
        }
        if (campusDTO.getCity() != null && !campusDTO.getCity().isEmpty()) {
            existingCampus.setCity(campusDTO.getCity());
        }

        // Update buildings if provided
        if (campusDTO.getBuildingIds() != null) {
            Set<Building> buildings = new HashSet<>(buildingRepository.findAllById(campusDTO.getBuildingIds()));
            existingCampus.setBuildings(buildings);
        }

        // Save the updated campus
        return campusRepository.save(existingCampus);
    }

    public void deleteCampus(Long id) {
        campusRepository.deleteById(id);
    }
}
