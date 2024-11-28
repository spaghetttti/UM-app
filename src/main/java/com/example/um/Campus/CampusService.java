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
        if (campusDTO.getName() == null || campusDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Campus name cannot be null or empty");
        }
        if (campusDTO.getCity() == null || campusDTO.getCity().isEmpty()) {
            throw new IllegalArgumentException("Campus city cannot be null or empty");
        }

        Campus createdCampus = new Campus();
        createdCampus.setName(campusDTO.getName());
        createdCampus.setCity(campusDTO.getCity());

            if (campusDTO.getBuildingIds() != null) {
            Set<Building> buildings = new HashSet<>(buildingRepository.findAllById(campusDTO.getBuildingIds()));
            createdCampus.setBuildings(buildings);
        }

        return campusRepository.save(createdCampus);
    }

    public Campus updateCampus(Long id, CampusDTO campusDTO) {
        if (id == null) {
            throw new IllegalArgumentException("Campus ID cannot be null");
        }

        Campus existingCampus = campusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campus not found with id: " + id));

        if (campusDTO.getName() != null && !campusDTO.getName().isEmpty()) {
            existingCampus.setName(campusDTO.getName());
        }
        if (campusDTO.getCity() != null && !campusDTO.getCity().isEmpty()) {
            existingCampus.setCity(campusDTO.getCity());
        }
        if (campusDTO.getBuildingIds() != null) {
            Set<Building> buildings = new HashSet<>(buildingRepository.findAllById(campusDTO.getBuildingIds()));
            existingCampus.setBuildings(buildings);
        }

        return campusRepository.save(existingCampus);
    }

    public void deleteCampus(Long id) {
        campusRepository.deleteById(id);
    }
}
