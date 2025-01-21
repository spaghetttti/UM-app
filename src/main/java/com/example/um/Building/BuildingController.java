package com.example.um.Building;

import com.example.um.Campus.CampusRepository;
import com.example.um.User.User;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "/**",  allowedHeaders = "Content-Type, Authorization")
@RequestMapping("/api/buildings")
public class BuildingController {
    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping
    public List<BuildingDTO> getAllBuildings() {
        return buildingService.findAllBuildings().stream().map(building -> new BuildingDTO(
                        building.getId(),
                        building.getCode(),
                        building.getYearOfConstruction(),
                        building.getCampus().getId()))  // Only return the Campus ID
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingDTO> getBuildingById(@PathVariable Long id) {
        return buildingService.findBuildingById(id)
                .map(building -> new BuildingDTO(
                        building.getId(),
                        building.getCode(),
                        building.getYearOfConstruction(),
                        building.getCampus().getId()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Building> createBuilding(@RequestBody BuildingDTO building, @RequestHeader("Role") String role) {
        User.Role enumRole = User.Role.valueOf(role);
        if (!enumRole.equals(User.Role.ADMINISTRATOR) && !enumRole.equals(User.Role.MANAGER)) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        return ResponseEntity.status(200).body(buildingService.createBuilding(building));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Building> updateBuilding(@PathVariable Long id, @RequestBody BuildingDTO buildingDetails, @RequestHeader("Role") String role) {
        try {
            Building updatedBuilding = buildingService.updateBuilding(id, buildingDetails);
            User.Role enumRole = User.Role.valueOf(role);
            if (!enumRole.equals(User.Role.ADMINISTRATOR) && !enumRole.equals(User.Role.MANAGER)) {
                return ResponseEntity.status(403).body(null); // Forbidden
            }
            return ResponseEntity.ok(updatedBuilding);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id, @RequestHeader("Role") String role) {
        try {
            User.Role enumRole = User.Role.valueOf(role);
            if (!enumRole.equals(User.Role.ADMINISTRATOR) && !enumRole.equals(User.Role.MANAGER)) {
                return ResponseEntity.status(403).body(null); // Forbidden
            }
            buildingService.deleteBuilding(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
