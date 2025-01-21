package com.example.um.Component;

import java.util.List;

public class ComponentDTO {
    private Long id;
    private String acronym;
    private String name;
    private String responsiblePerson;
    private List<Long> buildingIds;

    // Constructors
    public ComponentDTO() {}

    public ComponentDTO(Long id, String acronym, String name, String responsiblePerson, List<Long> buildingIds) {
        this.id = id;
        this.acronym = acronym;
        this.name = name;
        this.responsiblePerson = responsiblePerson;
        this.buildingIds = buildingIds;
    }

    // Getters and Setters
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

    public List<Long> getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(List<Long> buildingIds) {
        this.buildingIds = buildingIds;
    }
}