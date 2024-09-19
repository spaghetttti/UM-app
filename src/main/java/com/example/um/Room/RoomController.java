package com.example.um.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Get all rooms
    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.findAllRooms();
    }

    // Get room by ID
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Optional<Room> room = roomService.findRoomById(id);
        return room.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new room
    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomService.saveRoom(room);
    }

    // Update a room by ID
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        Optional<Room> roomOptional = roomService.findRoomById(id);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            room.setRoomNumber(roomDetails.getRoomNumber());
            room.setCapacity(roomDetails.getCapacity());
            room.setType(roomDetails.getType());
            room.setAccessible(roomDetails.isAccessible());
            room.setFloor(roomDetails.getFloor());
            return ResponseEntity.ok(roomService.saveRoom(room));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a room by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
