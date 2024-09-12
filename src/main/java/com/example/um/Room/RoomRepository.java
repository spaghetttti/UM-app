package com.example.um.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    // Additional query methods can be added here if needed
}