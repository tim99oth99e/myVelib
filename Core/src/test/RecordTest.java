package src.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import src.coreClasses.*;
import src.enums.*;
import src.event.Event;
import src.registrationCard.NoRegistrationCard;
import src.registrationCard.VlibreRegistrationCard;
import src.registrationCard.VmaxRegistrationCard;
import src.stationsSorting.LeastOccupiedPolicy;
import src.stationsSorting.MostUsedPolicy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
    Bicycle bicycle2 = new Bicycle(TypeOfBicycle.Electrical);
    // define parking slots
    ParkingSlot ps1 = new ParkingSlot(ParkingSlotStatus.Occupied, bicycle1);
    ParkingSlot ps2 = new ParkingSlot(ParkingSlotStatus.Occupied, bicycle2);
    // define dates
    LocalDateTime dateTime1 = LocalDateTime.of(2021, 2,11,8,20,30);
    LocalDateTime dateTime2 = LocalDateTime.of(2021, 2,11,10,20,30);
    LocalDateTime dateTime3 = LocalDateTime.of(2021, 2,11,12,20,30);
    // Test rides
    Event e1 = new Event(dateTime1, EventType.RentBicycle, station1);
//    Ride ride2 = new Ride(user1, station2, station1, dateTime2, dateTime3, bicycle1);

    RecordTest() throws Exception {
    }

    @BeforeAll
    public static void configureRecord(){
        System.out.println("Configuration finished");
    }

    @Test
    void addRideIfNotExists() {
        // configure station
        station1.addParkingSlot(ps1);
        station2.addParkingSlot(ps2);
        record1.addEventIfNotExists(e1);

        // print statistics
        record1.printUserStatistics(user1);
        System.out.println("");
        record1.printStationBalance(station1);
    }

    @Test
    void computeAvgOccupationRate() throws Exception {
        // create Record
        Record myVelibRecord = new Record();

        // add 2 users
        User thomas = new User("Thomas Muller", 30.0, 34.532,"1203948573849583", new NoRegistrationCard());
        myVelibRecord.addUserIfNotExists(thomas);
        User wilson = new User("Wilson Churchill", 89.0, 34.532,"1203948573849583", new VlibreRegistrationCard());
        myVelibRecord.addUserIfNotExists(wilson);
        User rachel = new User("Rachel Churchill", 89.0, 34.532,"1203948573849583", new VmaxRegistrationCard());
        myVelibRecord.addUserIfNotExists(rachel);

        // add 3 stations
        Station chatelet = new Station(20.345, 24.352, StationStatus.OnService, TypeOfStation.Standard);
        myVelibRecord.addStationIfNotExists(chatelet);
        Station barbes = new Station(20.345, 24.352, StationStatus.OnService, TypeOfStation.Standard);
        myVelibRecord.addStationIfNotExists(barbes);
        Station raspail = new Station(20.345, 24.352, StationStatus.OnService, TypeOfStation.Standard);
        myVelibRecord.addStationIfNotExists(raspail);

        // add parking slots to the stations
        ParkingSlot ps1Chatelet = new ParkingSlot(ParkingSlotStatus.Occupied, new Bicycle(TypeOfBicycle.Electrical));
        ParkingSlot ps2Chatelet = new ParkingSlot(ParkingSlotStatus.Occupied, new Bicycle(TypeOfBicycle.Mechanical));
        ParkingSlot ps3Chatelet = new ParkingSlot(ParkingSlotStatus.Free, null);
        chatelet.addParkingSlot(ps1Chatelet);
        chatelet.addParkingSlot(ps2Chatelet);
        chatelet.addParkingSlot(ps3Chatelet);

        ParkingSlot ps1Barbes = new ParkingSlot(ParkingSlotStatus.Occupied, new Bicycle(TypeOfBicycle.Electrical));
        ParkingSlot ps2Barbes = new ParkingSlot(ParkingSlotStatus.Occupied, new Bicycle(TypeOfBicycle.Mechanical));
        ParkingSlot ps3Barbes = new ParkingSlot(ParkingSlotStatus.Free, null);
        barbes.addParkingSlot(ps1Barbes);
        barbes.addParkingSlot(ps2Barbes);
        barbes.addParkingSlot(ps3Barbes);

        // do a few rents / parks
        LocalDateTime date1 = LocalDateTime.of(2021, 2,11,8,20);
        thomas.rent(ps1Chatelet, date1);
        myVelibRecord.addEventIfNotExists(new Event(date1, EventType.RentBicycle, chatelet));
        wilson.rent(ps2Chatelet, date1);
        myVelibRecord.addEventIfNotExists(new Event(date1, EventType.RentBicycle, chatelet));
//        rachel.rent(ps1Barbes);
//        myVelibRecord.addEventIfNotExists(new Event(date1, EventType.RentBicycle, barbes));

        LocalDateTime date2 = LocalDateTime.of(2021, 2,11,10,20);
        thomas.park(ps3Barbes, date2);
        myVelibRecord.addEventIfNotExists(new Event(date2, EventType.ReturnBicycle, barbes));
        wilson.park(ps1Chatelet, date2);
        myVelibRecord.addEventIfNotExists(new Event(date2, EventType.ReturnBicycle, chatelet));

        LocalDateTime date3 = LocalDateTime.of(2021, 2,11,12,20);


        // test avg occupation rate method
        assertEquals(0 , Record.computeAvgOccupationRate(chatelet, date1, date2, myVelibRecord.getEvents()));
        assertEquals(120.0 / (3 * 240) , Record.computeAvgOccupationRate(chatelet, date1, date3, myVelibRecord.getEvents()));
        assertEquals(( 240.00 + 240.0 + 120.0 ) / (3 * 240) , Record.computeAvgOccupationRate(barbes, date1, date3, myVelibRecord.getEvents()));
    }


    @Test
    void getSortedStation() throws Exception {
        // create Record
        Record myVelibRecord = new Record();

        // add 2 users
        User thomas = new User("Thomas Muller", 30.0, 34.532,"1203948573849583", new NoRegistrationCard());
        myVelibRecord.addUserIfNotExists(thomas);
        User wilson = new User("Wilson Churchill", 89.0, 34.532,"1203948573849583", new VlibreRegistrationCard());
        myVelibRecord.addUserIfNotExists(wilson);

        // add 3 stations
        Station chatelet = new Station(20.345, 24.352, StationStatus.OnService, TypeOfStation.Standard);
        myVelibRecord.addStationIfNotExists(chatelet);
        Station barbes = new Station(20.345, 24.352, StationStatus.OnService, TypeOfStation.Standard);
        myVelibRecord.addStationIfNotExists(barbes);
        Station raspail = new Station(20.345, 24.352, StationStatus.OnService, TypeOfStation.Standard);
        myVelibRecord.addStationIfNotExists(raspail);

        // add parking slots to the stations
        ParkingSlot ps1Chatelet = new ParkingSlot(ParkingSlotStatus.Occupied, new Bicycle(TypeOfBicycle.Electrical));
        ParkingSlot ps2Chatelet = new ParkingSlot(ParkingSlotStatus.Occupied, new Bicycle(TypeOfBicycle.Mechanical));
        ParkingSlot ps3Chatelet = new ParkingSlot(ParkingSlotStatus.Free, null);
        chatelet.addParkingSlot(ps1Chatelet);
        chatelet.addParkingSlot(ps2Chatelet);
        chatelet.addParkingSlot(ps3Chatelet);

        ParkingSlot ps1Barbes = new ParkingSlot(ParkingSlotStatus.Occupied, new Bicycle(TypeOfBicycle.Electrical));
        ParkingSlot ps2Barbes = new ParkingSlot(ParkingSlotStatus.Occupied, new Bicycle(TypeOfBicycle.Mechanical));
        ParkingSlot ps3Barbes = new ParkingSlot(ParkingSlotStatus.Free, null);
        barbes.addParkingSlot(ps1Barbes);
        barbes.addParkingSlot(ps2Barbes);
        barbes.addParkingSlot(ps3Barbes);

        // do a few rents / parks
        LocalDateTime date1 = LocalDateTime.of(2021, 2,11,8,20);
        thomas.rent(ps1Chatelet, date1);
        myVelibRecord.addEventIfNotExists(new Event(date1, EventType.RentBicycle, chatelet));
        wilson.rent(ps2Chatelet, date1);
        myVelibRecord.addEventIfNotExists(new Event(date1, EventType.RentBicycle, chatelet));

        LocalDateTime date2 = LocalDateTime.of(2021, 2,11,10,20);
        thomas.park(ps3Barbes, date2);
        myVelibRecord.addEventIfNotExists(new Event(date2, EventType.ReturnBicycle, barbes));
        wilson.park(ps2Chatelet, date2);
        myVelibRecord.addEventIfNotExists(new Event(date2, EventType.ReturnBicycle, chatelet));

        // compute stations sorting
        System.out.println(myVelibRecord.getSortedStations(new MostUsedPolicy()));
        HashMap<Integer, Double> expectedSortingHashMap = new LinkedHashMap<>();
        expectedSortingHashMap.put(chatelet.getId(), 3.0);
        expectedSortingHashMap.put(barbes.getId(), 1.0);
        expectedSortingHashMap.put(raspail.getId(), 0.0);
        assertEquals(expectedSortingHashMap, myVelibRecord.getSortedStations(new MostUsedPolicy()));

        System.out.println(myVelibRecord.getSortedStations(new LeastOccupiedPolicy()));
        HashMap<Integer, Double> expectedSortingHashMap2 = new LinkedHashMap<>();
        expectedSortingHashMap2.put(raspail.getId(), Record.computeAvgOccupationRate(chatelet, date1, date2, myVelibRecord.getEvents()));
        expectedSortingHashMap2.put(chatelet.getId(), Record.computeAvgOccupationRate(chatelet, date1, date2, myVelibRecord.getEvents()));
        expectedSortingHashMap2.put(barbes.getId(), Record.computeAvgOccupationRate(chatelet, date1, date2, myVelibRecord.getEvents()));
        assertEquals(expectedSortingHashMap2, myVelibRecord.getSortedStations(new LeastOccupiedPolicy()));
    }
}