package com.example.um.Campus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "/**")
@RequestMapping("/api/campuses")
public class CampusController {

    @Autowired
    private CampusService campusService;

    @GetMapping
    public List<Campus> getAllCampuses() {
        return campusService.findAllCampuses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campus> getCampusById(@PathVariable Long id) {
        Optional<Campus> campus = campusService.findCampusById(id);
        return campus.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Campus> createCampus(@RequestBody CampusDTO campus, @RequestHeader("Role") String role) {
        if (!role.equals("ADMINISTRATOR") && !role.equals("MANAGER")) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        return ResponseEntity.status(200).body(campusService.createCampus(campus));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campus> updateCampus(@PathVariable Long id, @RequestBody CampusDTO campusDetails, @RequestHeader("Role") String role) {
        if (!role.equals("ADMINISTRATOR") && !role.equals("MANAGER")) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        return ResponseEntity.status(200).body(campusService.updateCampus(id, campusDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampus(@PathVariable Long id, @RequestHeader("Role") String role) {
        if (!role.equals("ADMINISTRATOR") && !role.equals("MANAGER")) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        campusService.deleteCampus(id);
        return ResponseEntity.noContent().build();
    }
}