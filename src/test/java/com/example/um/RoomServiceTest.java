package com.example.um;

import com.example.um.Building.Building;
import com.example.um.Building.BuildingRepository;
import com.example.um.Room.Room;
import com.example.um.Room.RoomDTO;
import com.example.um.Room.RoomRepository;
import com.example.um.Room.RoomService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BuildingRepository buildingRepository;

    @InjectMocks
    private RoomService roomService;

    RoomServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRoom_Success() {
        // Mock input
        RoomDTO roomDTO = new RoomDTO(123L, "A101", 50, "Lecture", true, 1, 1L);
//        roomDTO.setRoomNumber("A101");
//        roomDTO.setCapacity(50);
//        roomDTO.setFloor(1);
//        roomDTO.setType("Lecture");
//        roomDTO.setAccessible(true);
//        roomDTO.setBuildingId(1L);

        Building building = new Building();
        building.setId(1L);

        when(buildingRepository.findById(1L)).thenReturn(Optional.of(building));

        Room savedRoom = new Room();
        savedRoom.setId(1L);
        savedRoom.setRoomNumber("A101");
        savedRoom.setCapacity(50);
        savedRoom.setFloor(1);
        savedRoom.setType("Lecture");
        savedRoom.setAccessible(true);
        savedRoom.setBuilding(building);

        when(roomRepository.save(any(Room.class))).thenReturn(savedRoom);

        // Call service
        Room createdRoom = roomService.createRoom(roomDTO);

        // Verify results
        assertNotNull(createdRoom);
        assertEquals("A101", createdRoom.getRoomNumber());
        assertEquals(50, createdRoom.getCapacity());
        assertEquals(1, createdRoom.getFloor());
        assertEquals("Lecture", createdRoom.getType());
        assertTrue(createdRoom.isAccessible());
        assertEquals(1L, createdRoom.getBuilding().getId());

        verify(buildingRepository, times(1)).findById(1L);
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void createRoom_BuildingNotFound_ThrowsException() {
        // Mock input
        RoomDTO roomDTO = new RoomDTO(123L, "A101", 50, "Lecture", true, 1, 1L);
//        roomDTO.setBuildingId(1L);

        when(buildingRepository.findById(1L)).thenReturn(Optional.empty());

        // Call service and verify exception
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            roomService.createRoom(roomDTO);
        });

        assertEquals("Building id is incorrect", exception.getMessage());
        verify(buildingRepository, times(1)).findById(1L);
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void updateRoom_Success() {
        // Mock input
        Long roomId = 1L;
        RoomDTO roomDTO = new RoomDTO(roomId, "A101", 50, "Lecture", true, 1, 1L);
//        RoomDTO roomDTO = new RoomDTO(3L, );
//        roomDTO.setRoomNumber("B202");
//        roomDTO.setCapacity(40);
//        roomDTO.setFloor(2);
//        roomDTO.setBuildingId(1L);

        Room existingRoom = new Room();
        existingRoom.setId(roomId);
        existingRoom.setRoomNumber("A101");
        existingRoom.setCapacity(30);
        existingRoom.setFloor(1);

        Building building = new Building();
        building.setId(1L);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(existingRoom));
        when(buildingRepository.findById(1L)).thenReturn(Optional.of(building));
        when(roomRepository.save(any(Room.class))).thenReturn(existingRoom);

        // Call service
        Room updatedRoom = roomService.updateRoom(roomId, roomDTO);

        // Verify results
        assertNotNull(updatedRoom);
        assertEquals("B202", updatedRoom.getRoomNumber());
        assertEquals(40, updatedRoom.getCapacity());
        assertEquals(2, updatedRoom.getFloor());
        assertEquals(1L, updatedRoom.getBuilding().getId());

        verify(roomRepository, times(1)).findById(roomId);
        verify(buildingRepository, times(1)).findById(1L);
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void updateRoom_RoomNotFound_ThrowsException() {
        // Mock input
        Long roomId = 1L;
//        RoomDTO roomDTO = new RoomDTO();
        RoomDTO roomDTO = new RoomDTO(roomId, "A101", 50, "Lecture", true, 1, 1L);


        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        // Call service and verify exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            roomService.updateRoom(roomId, roomDTO);
        });

        assertEquals("Room not found with id: 1", exception.getMessage());
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void findRoomById_Success() {
        // Mock input
        Room room = new Room();
        room.setId(1L);
        room.setRoomNumber("A101");

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        // Call service
        Optional<Room> foundRoom = roomService.findRoomById(1L);

        // Verify results
        assertTrue(foundRoom.isPresent());
        assertEquals("A101", foundRoom.get().getRoomNumber());

        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    void deleteRoom_Success() {
        // Mock input
        Long roomId = 1L;

        when(roomRepository.existsById(roomId)).thenReturn(true);

        // Call service
        roomService.deleteRoom(roomId);

        // Verify interactions
        verify(roomRepository, times(1)).deleteById(roomId);
    }

    @Test
    void deleteRoom_NotFound_ThrowsException() {
        // Mock input
        Long roomId = 1L;

        when(roomRepository.existsById(roomId)).thenReturn(false);

        // Call service and verify exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            roomService.deleteRoom(roomId);
        });

        assertEquals("Room not found with id: 1", exception.getMessage());
        verify(roomRepository, times(1)).existsById(roomId);
        verify(roomRepository, never()).deleteById(roomId);
    }
}
