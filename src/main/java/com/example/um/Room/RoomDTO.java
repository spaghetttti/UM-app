package com.example.um.Room;

public class RoomDTO {

    private Long id;
    private String roomNumber;
    private Integer capacity;
    private String type;
    private boolean accessible;
    private Integer floor;
    private Long buildingId;  // ID of the associated Building

    // Constructors
    public RoomDTO(Long id, String roomNumber, Integer capacity, String type, boolean accessible, Integer floor, Long buildingId) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.type = type;
        this.accessible = accessible;
        this.floor = floor;
        this.buildingId = buildingId;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isAccessible() { return accessible; }
    public void setAccessible(boolean accessible) { this.accessible = accessible; }

    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }

    public Long getBuildingId() { return buildingId; }
    public void setBuildingId(Long buildingId) { this.buildingId = buildingId; }
}
