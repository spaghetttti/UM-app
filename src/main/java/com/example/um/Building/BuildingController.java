package com.example.um.Building;

import com.example.um.Campus.CampusRepository;
import com.example.um.User.User;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
                        building.getCampus().getId(),
                        building.getLatitude(),
                        building.getLongitude()
                )) // Only return the Campus ID
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingDTO> getBuildingById(@PathVariable Long id) {
        return buildingService.findBuildingById(id)
                .map(building -> new BuildingDTO(
                        building.getId(),
                        building.getCode(),
                        building.getYearOfConstruction(),
                        building.getCampus().getId(),
                        building.getLatitude(),
                        building.getLongitude()))
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

    @GetMapping("distance/{buildingId1}/{buildingId2}")
    public ResponseEntity<Map<String, Object>> calculateDistanceBetweenBuildings(
            @PathVariable Long buildingId1,
            @PathVariable Long buildingId2) {
        try {
            Building building1 = buildingService.findBuildingById(buildingId1)
                    .orElseThrow(() -> new IllegalArgumentException("Building with ID " + buildingId1 + " not found."));
            Building building2 = buildingService.findBuildingById(buildingId2)
                    .orElseThrow(() -> new IllegalArgumentException("Building with ID " + buildingId2 + " not found."));

            if (building1.getLatitude() == null || building1.getLongitude() == null
                    || building2.getLatitude() == null || building2.getLongitude() == null) {
                Map<String, Object> errorResponse = Map.of(
                        "error", "Buildings must have valid coordinates for distance calculation."
                );
            }

            double distance = buildingService.calculateDistance(
                    building1.getLatitude(), building1.getLongitude(),
                    building2.getLatitude(), building2.getLongitude());
            Map<String, Object> response = Map.of(
                    "distance", distance,
                    "unit", "km"
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = Map.of(
                    "error", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = Map.of(
                    "error", "An unexpected error occurred: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
