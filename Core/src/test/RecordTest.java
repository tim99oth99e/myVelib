package src.test;

import org.junit.jupiter.api.Test;
import src.coreClasses.*;
import src.enums.ParkingSlotStatus;
import src.enums.StationStatus;
import src.enums.TypeOfBicycle;
import src.enums.TypeOfStation;
import src.registrationCard.NoRegistrationCard;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RecordTest {
    Record record1 = new Record();
    // define users
    User user1 = new User("Billy Gates", 0.0, 145.4, "1235384958374038",
            new NoRegistrationCard());
    // define stations
    Station station1 = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
    Station station2 = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Plus);
    // define bicycle
    Bicycle bicycle1 = new Bicycle(TypeOfBicycle.Electrical);
    // define parking slots
    ParkingSlot ps1 = new ParkingSlot(ParkingSlotStatus.Occupied, bicycle1);
    // define dates
    LocalDateTime dateTime1 = LocalDateTime.of(2021, 2,11,8,20,30);
    LocalDateTime dateTime2 = LocalDateTime.of(2021, 2,11,10,20,30);
    LocalDateTime dateTime3 = LocalDateTime.of(2021, 2,11,12,20,30);
    // Test rides
    Ride ride1 = new Ride(user1, station1, station2, dateTime1, dateTime2, bicycle1);
//    Ride ride2 = new Ride(user1, station2, station1, dateTime2, dateTime3, bicycle1);

    RecordTest() throws Exception {
    }

    @Test
    void addRideIfNotExists() throws Exception {
        // configure station
        station1.addParkingSlot(ps1);
        record1.addRideIfNotExists(ride1);
//        record1.addRideIfNotExists(ride2);

        // print statistics
        record1.printUserStatistics(user1);
        System.out.println("");
        record1.printStationBalance(station1);
    }

    @Test
    void computeAvgOccupationRate() {
        // configure station
        station1.addParkingSlot(ps1);
        station2.addParkingSlot(ps1);
        record1.addRideIfNotExists(ride1);
//        record1.addRideIfNotExists(ride2);
        assertEquals(0.0, record1.computeAvgOccupationRate(station1, dateTime1, dateTime3));
        assertEquals(0.5, record1.computeAvgOccupationRate(station2, dateTime1, dateTime3));

    }

}