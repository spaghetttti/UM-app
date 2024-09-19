package com.example.um.Building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/buildings")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;


    @GetMapping
    public List<Building> getAllBuildings() {
        return buildingService.findAllBuildings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Building> getBuildingById(@PathVariable Long id) {
        Optional<Building> building = buildingService.findBuildingById(id);
        return building.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public Building createBuilding(@RequestBody Building building) {
        return buildingService.saveBuilding(building);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Building> updateBuilding(@PathVariable Long id, @RequestBody Building buildingDetails) {
        Optional<Building> buildingOptional = buildingService.findBuildingById(id);
        if (buildingOptional.isPresent()) {
            Building building = buildingOptional.get();
            building.setCode(buildingDetails.getCode());
            building.setYearOfConstruction(buildingDetails.getYearOfConstruction());
            building.setCampus(buildingDetails.getCampus());
            return ResponseEntity.ok(buildingService.saveBuilding(building));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.noContent().build();
    }
}