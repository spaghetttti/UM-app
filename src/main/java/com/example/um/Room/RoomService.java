package com.example.um.Room;
import com.example.um.Building.Building;
import com.example.um.Building.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Room updateRoom(Long id) {return null;}

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}