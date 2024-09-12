package com.example.um.Building;
import com.example.um.Campus.Campus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private int yearOfConstruction;

    // Many buildings can belong to one campus
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id", nullable = false)
    @JsonIgnore  // Prevents infinite recursion
    private Campus campus;

    // Constructors
    public Building() {}

    public Building(String code, int yearOfConstruction, Campus campus) {
        this.code = code;
        this.yearOfConstruction = yearOfConstruction;
        this.campus = campus;
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

    public void setYearOfConstruction(int yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }
}