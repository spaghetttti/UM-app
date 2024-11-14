package com.example.um.Component;
import com.example.um.Building.Building;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String acronym;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String responsiblePerson;

    // One component can exploit many buildings
    @ManyToMany
    @JoinTable(
            name = "component_building",
            joinColumns = @JoinColumn(name = "component_id"),
            inverseJoinColumns = @JoinColumn(name = "building_id")
    )
    private Set<Building> exploitedBuildings;

    // Constructors
    public Component() {}

    public Component(String acronym, String name, String responsiblePerson) {
        this.acronym = acronym;
        this.name = name;
        this.responsiblePerson = responsiblePerson;
    }

    public Component(String acronym, String name, String responsiblePerson, Set<Building> exploitedBuildings) {
        this.acronym = acronym;
        this.name = name;
        this.responsiblePerson = responsiblePerson;
        this.exploitedBuildings = exploitedBuildings;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public Set<Building> getExploitedBuildings() {
        return exploitedBuildings;
    }

    public void setExploitedBuildings(Set<Building> exploitedBuildings) {
        this.exploitedBuildings = exploitedBuildings;
    }
}