package com.example.um;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
@Suite
@SelectClasses({
        BuildingServiceTest.class,
        CampusServiceTest.class,
        ComponentServiceTest.class,
        RoomServiceTest.class
})
class UmApplicationTests {
//
//    @Test
//    void contextLoads() {
//        // Ensures the application context loads correctly
//    }
//
//    @Nested
//    class BuildingServiceTests extends BuildingServiceTest {
//        // Executes all tests from BuildingServiceTest
//    }
//
//    @Nested
//    class CampusServiceTests extends CampusServiceTest {
//        // Executes all tests from CampusServiceTest
//    }
//
//    @Nested
//    class ComponentServiceTests extends ComponentServiceTest {
//        // Executes all tests from ComponentServiceTest
//    }
//
//    @Nested
//    class RoomServiceTests extends RoomServiceTest {
//        // Executes all tests from RoomServiceTest
//    }
}