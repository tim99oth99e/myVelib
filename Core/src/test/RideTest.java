package src.test;

import org.junit.jupiter.api.Test;
import src.coreClasses.*;
import src.enums.*;
import src.event.Event;
import src.registrationCard.NoRegistrationCard;

import java.time.LocalDateTime;

class EventTest {
    // define users
    User user1 = new User("Billy Gates", 0.0, 145.4, "1235384958374038",
            new NoRegistrationCard());
    // define stations
    Station station1 = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
    Station station2 = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Plus);
    // define parking Slots
    ParkingSlot ps1 = new ParkingSlot(ParkingSlotStatus.Occupied, new Bicycle(TypeOfBicycle.Electrical));
    ParkingSlot ps2 = new ParkingSlot(ParkingSlotStatus.Occupied, new Bicycle(TypeOfBicycle.Electrical));
    ParkingSlot ps3 = new ParkingSlot(ParkingSlotStatus.Free, new Bicycle(TypeOfBicycle.Mechanical));
    // add it to the station
//    station1.addParkingSlot(ps1);

    // define dates
    LocalDateTime dateTime1 = LocalDateTime.of(2021, 2,11,8,20,30);
    LocalDateTime dateTime2 = LocalDateTime.of(2021, 2,11,10,20,44);
    // define bicycle
    Bicycle bicycle1 = new Bicycle(TypeOfBicycle.Electrical);
    // Test events
    Event e1 = new Event(dateTime1, EventType.RentBicycle, station1);

    EventTest() throws Exception {
    }

}