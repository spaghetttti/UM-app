package com.example.um.Room;

public class RoomDTO {

    private Long id;
    private String roomNumber;
    private int capacity;
    private String type;
    private boolean accessible;
    private int floor;
    private Long buildingId;  // ID of the associated Building

    // Constructors
    public RoomDTO(Long id, String roomNumber, int capacity, String type, boolean accessible, int floor, Long buildingId) {
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

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isAccessible() { return accessible; }
    public void setAccessible(boolean accessible) { this.accessible = accessible; }

    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }

    public Long getBuildingId() { return buildingId; }
    public void setBuildingId(Long buildingId) { this.buildingId = buildingId; }
}
