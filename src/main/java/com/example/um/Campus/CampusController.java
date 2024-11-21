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
    public Campus createCampus(@RequestBody CampusDTO campus) {
        return campusService.createCampus(campus);
    }

    @PutMapping("/{id}")
    public Campus updateCampus(@PathVariable Long id, @RequestBody CampusDTO campusDetails) {
//        Optional<Campus> campusOptional = campusService.findCampusById(id);
//        if (campusOptional.isPresent()) {
//            Campus campus = campusOptional.get();
//            campus.setName(campusDetails.getName());
//            campus.setCity(campusDetails.getCity());
//            return ResponseEntity.ok(campusService.saveCampus(campus));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//        return campusService.updateCampus(new CampusDTO(id, ...campusDetails));
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampus(@PathVariable Long id) {
        campusService.deleteCampus(id);
        return ResponseEntity.noContent().build();
    }
}