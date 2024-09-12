package com.example.um.Campus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
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
    public Campus createCampus(@RequestBody Campus campus) {
        return campusService.saveCampus(campus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campus> updateCampus(@PathVariable Long id, @RequestBody Campus campusDetails) {
        Optional<Campus> campusOptional = campusService.findCampusById(id);
        if (campusOptional.isPresent()) {
            Campus campus = campusOptional.get();
            campus.setName(campusDetails.getName());
            campus.setCity(campusDetails.getCity());
            return ResponseEntity.ok(campusService.saveCampus(campus));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampus(@PathVariable Long id) {
        campusService.deleteCampus(id);
        return ResponseEntity.noContent().build();
    }
}