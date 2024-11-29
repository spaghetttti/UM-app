package com.example.um.Room;
import com.example.um.Building.Building;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String roomNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private String type; // E.g., "lecture", "lab", "computer"

    @Column(nullable = false)
    private boolean accessible; // Accessibility for disabled persons

    @Column(nullable = false)
    private Integer floor;

    // Many rooms belong to one building
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    @JsonIgnore  // Prevents infinite recursion
    private Building building;

    // Constructors
    public Room() {}

    public Room(String roomNumber, Integer capacity, String type, boolean accessible, Integer floor, Building building) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.type = type;
        this.accessible = accessible;
        this.floor = floor;
        this.building = building;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}