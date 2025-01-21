package com.example.um.Component;

import com.example.um.Campus.CampusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "/**")
@RequestMapping("/api/components")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    // Get all components
    @GetMapping
    public List<Component> getAllComponents() {
        return componentService.findAllComponents();
    }

    // Get component by ID
    @GetMapping("/{id}")
    public ResponseEntity<Component> getComponentById(@PathVariable Long id) {
        Optional<Component> component = componentService.findComponentById(id);
        return component.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new component
    @PostMapping
    public ResponseEntity<Component> createComponent(@RequestBody ComponentDTO component, @RequestHeader("Role") String role) {
        if (!role.equals("ADMINISTRATOR")) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        return ResponseEntity.status(200).body(componentService.createComponent(component));
    }

    // Update a component by ID
    @PutMapping("/{id}")
    public ResponseEntity<Component> updateComponent(@PathVariable Long id, @RequestBody ComponentDTO componentDetails, @RequestHeader("Role") String role) {
        if (!role.equals("ADMINISTRATOR")) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        return ResponseEntity.status(200).body(componentService.updateComponent(id, componentDetails));
    }

    // Delete a component by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Long id, @RequestHeader("Role") String role) {
        if (!role.equals("ADMINISTRATOR")) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        componentService.deleteComponent(id);
        return ResponseEntity.noContent().build();
    }
}
