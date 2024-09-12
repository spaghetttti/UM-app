package com.example.um.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<Component, Long> {
    // Additional query methods can be added here if needed
}