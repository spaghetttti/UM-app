package com.example.um.Campus;

import java.util.List;

public class CampusDTO {
    private Long id;
    private String name;
    private String city;
    private List<Long> buildingIds; // IDs of associated buildings

    // Constructors
    public CampusDTO() {}

    public CampusDTO(Long id, String name, String city, List<Long> buildingIds) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.buildingIds = buildingIds;
    }

    // Getters and Setters
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

    public List<Long> getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(List<Long> buildingIds) {
        this.buildingIds = buildingIds;
    }
}