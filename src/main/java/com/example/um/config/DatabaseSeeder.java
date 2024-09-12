package com.example.um.config;


import com.example.um.Building.Building;
import com.example.um.Building.BuildingRepository;
import com.example.um.Campus.Campus;
import com.example.um.Campus.CampusRepository;
import com.example.um.Component.ComponentRepository;
import com.example.um.Room.Room;
import com.example.um.Room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    @Autowired
    private CampusRepository campusRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ComponentRepository componentRepository;
    @Override
    public void run(String... args) throws Exception {
        seedDatabase();
    }

    private void seedDatabase() {
        // Check if the database is already seeded
        if (campusRepository.count() == 0) {
            // Create a few campuses
            Campus campus1 = new Campus("Engineering Campus", "Montpellier");
            Campus campus2 = new Campus("Science Campus", "Nîmes");

            campusRepository.save(campus1);
            campusRepository.save(campus2);

            // Create some buildings for each campus
            Building building1 = new Building("ENG-101", 1990, campus1);
            Building building2 = new Building("SCI-202", 2000, campus2);

            buildingRepository.save(building1);
            buildingRepository.save(building2);

            // Create rooms in the buildings
            Room room1 = new Room("101", 50, "lecture", true, 1, building1);
            Room room2 = new Room("202", 30, "lab", false, 2, building2);

            roomRepository.save(room1);
            roomRepository.save(room2);

            // Create some components (departments)
            com.example.um.Component.Component component1 = new com.example.um.Component.Component("CS", "Computer Science", "Dr. Alice");
            com.example.um.Component.Component component2 = new com.example.um.Component.Component("PHY", "Physics", "Dr. Bob");

            componentRepository.save(component1);
            componentRepository.save(component2);

            System.out.println("Database seeded with sample data.");
        } else {
            System.out.println("Database already contains data, no seeding necessary.");
        }
    }
}