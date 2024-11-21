package com.example.um.Component;

import java.util.List;

public class ComponentDTO {
    private Long id;
    private String code;
    private String name;
    private String manager;
    private List<Long> buildingIds; // IDs of exploited buildings

    // Constructors
    public ComponentDTO() {}

    public ComponentDTO(Long id, String code, String name, String manager, List<Long> buildingIds) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.manager = manager;
        this.buildingIds = buildingIds;
    }

    // Getters and Setters
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public List<Long> getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(List<Long> buildingIds) {
        this.buildingIds = buildingIds;
    }
}
