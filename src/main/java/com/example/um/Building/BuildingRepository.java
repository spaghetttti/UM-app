package com.example.um.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    // Additional query methods can be added here if needed
}