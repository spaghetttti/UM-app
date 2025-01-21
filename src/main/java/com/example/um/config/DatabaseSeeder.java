package com.example.um.config;


import com.example.um.Building.Building;
import com.example.um.Building.BuildingRepository;
import com.example.um.Campus.Campus;
import com.example.um.Campus.CampusRepository;
import com.example.um.Component.ComponentRepository;
import com.example.um.Room.Room;
import com.example.um.Room.RoomRepository;
import com.example.um.User.User;
import com.example.um.User.UserRepository;
import com.example.um.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private UserRepository userRepository;
    @Override
    public void run(String... args) throws Exception {
        seedDatabase();
    }

    private void seedDatabase() {
        // Check if the database is already seeded
        if (campusRepository.count() == 0 || buildingRepository.count() == 0 || roomRepository.count() == 0 || componentRepository.count() == 0 )  {
//            campusRepository.deleteAll();
//            componentRepository.deleteAll();
//            roomRepository.deleteAll();
//            buildingRepository.deleteAll();
//            userRepository.deleteAll();
            // Create Campuses
            Campus triolet = new Campus("Triolet", "Montpellier");
            Campus stPriest = new Campus("St Priest", "Montpellier");
            Campus pharmacie = new Campus("Pharmacie", "Montpellier");
            Campus richter = new Campus("Richter", "Montpellier");
            Campus fdeMende = new Campus("FDE Mende", "Mende");
            Campus medecineNimes = new Campus("Medecine Nimes", "Nimes");

            campusRepository.saveAll(Arrays.asList(triolet, stPriest, pharmacie, richter, fdeMende, medecineNimes));

            // Create Buildings
            Building trioletB36 = new Building("triolet_b36", 2019, triolet);
            Building trioletB16 = new Building("triolet_b16", 1966, triolet);
            Building trioletB05 = new Building("triolet_b05", 1964, triolet);
            Building stPriestB02 = new Building("stPriest_b02", 1982, stPriest);

            buildingRepository.saveAll(Arrays.asList(trioletB36, trioletB16, trioletB05, stPriestB02));

            // Create Rooms
            List<Room> rooms = Arrays.asList(
                    new Room("A36.03", 120, "amphi", true, 0, trioletB36),
                    new Room("A36.02", 120, "amphi", true, 0, trioletB36),
                    new Room("A36.01", 120, "amphi", true, 0, trioletB36),
                    new Room("TD36.202", 40, "numerique", true, 2, trioletB36),
                    new Room("TD36.203", 40, "numerique", true, 2, trioletB36),
                    new Room("TD36.204", 40, "numerique", true, 2, trioletB36),
                    new Room("SC36.04", 80, "sc", true, 1, trioletB36),
                    new Room("TD36.101", 40, "td", true, 1, trioletB36),
                    new Room("TD36.302", 40, "td", true, 3, trioletB36),
                    new Room("TD36.402", 40, "td", true, 4, trioletB36),
                    new Room("SC16.03", 120, "amphi", true, 0, trioletB16),
                    new Room("TD16.02", 18, "td", true, 0, trioletB16),
                    new Room("TPDeptInfo", 40, "numerique", true, 0, trioletB16),
                    new Room("TPBio", 40, "tp", true, 0, trioletB16),
                    new Room("SC16.05", 48, "sc", true, 0, trioletB16),
                    new Room("A5.02", 275, "amphi", true, 1, trioletB05),
                    new Room("TD5.125", 20, "numerique", true, 0, trioletB05),
                    new Room("TD5.126", 31, "numerique", true, 0, trioletB05),
                    new Room("TD5.210", 40, "numerique", true, 1, trioletB05),
                    new Room("A_JJMoreau", 114, "amphi", true, 1, stPriestB02)
            );

            roomRepository.saveAll(rooms);

            // Create Components (Departments) --- com.example.um.Component.Component need to refactor
            com.example.um.Component.Component fds = new com.example.um.Component.Component("FDS", "Faculte des Sciences", "JM. Marin");
            com.example.um.Component.Component iae = new com.example.um.Component.Component("IAE", "Ecole Universitaire de Management", "E Houze");
            com.example.um.Component.Component polytech = new com.example.um.Component.Component("Polytech", "Polytech Montpellier", "L. Torres");

            componentRepository.saveAll(Arrays.asList(fds, iae, polytech));

            // Associate Components with Buildings (Exploitation)
            fds.setExploitedBuildings(new HashSet<>(Arrays.asList(trioletB16, trioletB36)));
            iae.setExploitedBuildings(new HashSet<>(Arrays.asList(trioletB16, trioletB05)));

            componentRepository.saveAll(Arrays.asList(fds, iae));

            User adminUser = new User("admin@example.com", PasswordUtil.hashPassword("admin123"), User.Role.ADMINISTRATOR);
            userRepository.save(adminUser);

            System.out.println("Database seeded with sample data.");
        } else {
            System.out.println("Database already contains data, no seeding necessary.");
        }
    }
}