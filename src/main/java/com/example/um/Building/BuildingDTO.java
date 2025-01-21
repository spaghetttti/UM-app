package com.example.um.Building;

import jakarta.persistence.Column;

public class BuildingDTO {

    private Long id;
    private String code;
    private Integer yearOfConstruction;
    private Long campusId;  // ID of the associated Campus
    private Double latitude;
    private Double longitude;

    // Constructors
    public BuildingDTO(Long id, String code, Integer yearOfConstruction, Long campusId, Double latitude, Double longitude) {
        this.id = id;
        this.code = code;
        this.yearOfConstruction = yearOfConstruction;
        this.campusId = campusId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Integer getYearOfConstruction() { return yearOfConstruction; }
    public void setYearOfConstruction(Integer yearOfConstruction) { this.yearOfConstruction = yearOfConstruction; }

    public Long getCampusId() { return campusId; }
    public void setCampusId(Long campusId) { this.campusId = campusId; }

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
}

