package com.example.um.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    // Additional query methods can be added here if needed
}