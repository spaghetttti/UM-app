package com.example.um.Building;

import com.example.um.Campus.Campus;
import com.example.um.Campus.CampusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final CampusRepository campusRepository;

    public BuildingService(BuildingRepository buildingRepository, CampusRepository campusRepository) {
        this.buildingRepository = buildingRepository;
        this.campusRepository = campusRepository;
    }

    public List<Building> findAllBuildings() {
        return buildingRepository.findAll();
    }

    public Optional<Building> findBuildingById(Long id) {
        return buildingRepository.findById(id);
    }

    public Building saveBuilding(Building building) {
        return buildingRepository.save(building);
    }

    public Building createBuilding(BuildingDTO buildingDTO) {
        Building building = new Building();
        building.setCode(buildingDTO.getCode());
        building.setYearOfConstruction(buildingDTO.getYearOfConstruction());

        // Set the campus based on the campus ID
        Campus campus = campusRepository.findById(buildingDTO.getCampusId())
                .orElseThrow(() -> new ResourceNotFoundException("Campus not found"));
        building.setCampus(campus);

        return buildingRepository.save(building);
    }

    public Building updateBuilding(Long id, BuildingDTO buildingDetails) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));

        building.setCode(buildingDetails.getCode());
        building.setYearOfConstruction(buildingDetails.getYearOfConstruction());

        // Updating the Campus if needed
        Campus campus = campusRepository.findById(buildingDetails.getCampusId())
                .orElseThrow(() -> new ResourceNotFoundException("Campus not found"));
        building.setCampus(campus);

        return buildingRepository.save(building);
    }


    public void deleteBuilding(Long id) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));
        buildingRepository.delete(building);
    }
}