package com.example.um.Room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "/**")
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Get all rooms
    @GetMapping
    public List<RoomDTO> getAllRooms() {
        return roomService.findAllRooms().stream().map(room -> new RoomDTO(
                room.getId(),
                room.getRoomNumber(),
                room.getCapacity(),
                room.getType(),
                room.isAccessible(),
                room.getFloor(),
                room.getBuilding().getId()
        )).collect(Collectors.toList());
    }

    // Get room by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        return roomService.findRoomById(id).map(room -> new RoomDTO(room.getId(),
                room.getRoomNumber(),
                room.getCapacity(),
                room.getType(),
                room.isAccessible(),
                room.getFloor(),
                room.getBuilding().getId())).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new room
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody RoomDTO room, @RequestHeader("Role") String role) {
        if (!role.equals("ADMINISTRATOR") && !role.equals("MANAGER") && !role.equals("TEACHER")) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        return ResponseEntity.status(200).body(roomService.createRoom(room));
    }

    // Update a room by ID
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDetails, @RequestHeader("Role") String role) {
        if (!role.equals("ADMINISTRATOR") && !role.equals("MANAGER") && !role.equals("TEACHER")) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        return ResponseEntity.status(200).body(roomService.updateRoom(id, roomDetails));
    }

    // Delete a room by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id, @RequestHeader("Role") String role) {
        if (!role.equals("ADMINISTRATOR") && !role.equals("MANAGER") && !role.equals("TEACHER")) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
