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
        if (buildingDTO.getCampusId() != null) {
        Campus campus = campusRepository.findById(buildingDTO.getCampusId())
                .orElseThrow(() -> new ResourceNotFoundException("Campus not found"));
        building.setCampus(campus);
        }

        return buildingRepository.save(building);
    }

    public Building updateBuilding(Long id, BuildingDTO buildingDetails) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));

        if (buildingDetails.getCode() != null) {
            building.setCode(buildingDetails.getCode());
        }

        if (buildingDetails.getYearOfConstruction() != null) {
            building.setYearOfConstruction(buildingDetails.getYearOfConstruction());
        }

        if (buildingDetails.getCampusId() != null) {
            Campus campus = campusRepository.findById(buildingDetails.getCampusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Campus not found"));
            building.setCampus(campus);
        }

        return buildingRepository.save(building);
    }


    public void deleteBuilding(Long id) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));
        buildingRepository.delete(building);
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the Earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (double) Math.round(R * c * 100.0) / 100.0;
    };
}