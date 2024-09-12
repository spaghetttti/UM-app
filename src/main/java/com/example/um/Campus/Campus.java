package com.example.um.Campus;
import com.example.um.Building.Building;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Campus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String city;

    // One-to-many relationship with Building
    @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference  // Manages the relationship on the parent side
    private Set<Building> buildings;

    // Constructors
    public Campus() {}

    public Campus(String name, String city) {
        this.name = name;
        this.city = city;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(Set<Building> buildings) {
        this.buildings = buildings;
    }
}