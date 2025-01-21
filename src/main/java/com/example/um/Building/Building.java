package com.example.um.Building;
import com.example.um.Campus.Campus;
import com.example.um.Component.Component;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private Integer yearOfConstruction;

    // Many buildings can belong to one campus
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id", nullable = false)
    @JsonIgnore  // Prevents infinite recursion
    private Campus campus;

    // Many buildings can be exploited by multiple components
    @ManyToMany(mappedBy = "exploitedBuildings")
    @JsonBackReference
    private Set<Component> components;
    @Column
    private Double latitude;
    @Column
    private Double longitude;

    // Constructors
    public Building() {}

    public Building(String code, int yearOfConstruction, Campus campus) {
        this.code = code;
        this.yearOfConstruction = yearOfConstruction;
        this.campus = campus;
    }

    public Building(String code, int yearOfConstruction, Campus campus, Set<Component> components, Double latitude, Double longitude) {
        this.code = code;
        this.yearOfConstruction = yearOfConstruction;
        this.campus = campus;
        this.components = components;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(Integer yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return this.id.toString() + "/" + this.campus.getId().toString() + "/" + this.yearOfConstruction;
    }
}