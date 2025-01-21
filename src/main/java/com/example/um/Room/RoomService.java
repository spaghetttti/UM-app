package com.example.um.Room;
import com.example.um.Building.Building;
import com.example.um.Building.BuildingRepository;
import com.example.um.Component.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BuildingRepository buildingRepository;

    public RoomService(RoomRepository roomRepository, BuildingRepository buildingRepository) {
        this.roomRepository = roomRepository;
        this.buildingRepository = buildingRepository;
    }


    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> findRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room createRoom(RoomDTO roomDetails) {
        Room room = new Room();
        room.setRoomNumber(roomDetails.getRoomNumber());
        room.setCapacity(roomDetails.getCapacity());
        room.setAccessible(roomDetails.isAccessible());
        room.setType(roomDetails.getType());
        room.setFloor(roomDetails.getFloor());
        Building building = buildingRepository.findById(roomDetails.getBuildingId())
                .orElseThrow(() -> new ResourceNotFoundException("Building id is incorrect"));

        room.setBuilding(building);
        return roomRepository.save((room));
    }

    public Room updateRoom(Long id, RoomDTO roomDetails) {
        if (id == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }

        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        if (roomDetails.getRoomNumber() != null && !roomDetails.getRoomNumber().isEmpty()) {
            existingRoom.setRoomNumber(roomDetails.getRoomNumber());
        }
        if (roomDetails.getCapacity() != null) {
            existingRoom.setCapacity(roomDetails.getCapacity());
        }
        if (roomDetails.getFloor() != null) {
            existingRoom.setFloor(roomDetails.getFloor());
        }

        if (roomDetails.getBuildingId() != null) {
            Building building = buildingRepository.findById(roomDetails.getBuildingId())
                    .orElseThrow(() -> new ResourceNotFoundException("Building id is incorrect"));
            existingRoom.setBuilding(building);
        }

        // Save the updated component
        return roomRepository.save(existingRoom);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}