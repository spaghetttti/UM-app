package com.example.um.Building;

public class BuildingDTO {

    private Long id;
    private String code;
    private Integer yearOfConstruction;
    private Long campusId;  // ID of the associated Campus

    // Constructors
    public BuildingDTO(Long id, String code, Integer yearOfConstruction, Long campusId) {
        this.id = id;
        this.code = code;
        this.yearOfConstruction = yearOfConstruction;
        this.campusId = campusId;
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
}

