package src.test;

import org.junit.jupiter.api.*;
import src.coreClasses.*;
import src.enums.*;
import src.ridePlanning.RidePlanningNormal;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class RidePlanningNormal
 */
public class RidePlanningNormalTest {

    @Test
    @DisplayName("Test of the method findStartStation for for RidePlanningNormal")
    public void testFindStartStation() {

        // Create bicycles of two different type
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
        Bicycle electricalBicycle = new Bicycle(TypeOfBicycle.Electrical);

        // Create free, occupied and out of order parking slot
        ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree2 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree3 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupiedMechanical = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedElectrical = new ParkingSlot(ParkingSlotStatus.Occupied, electricalBicycle);
        ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null);

        // Create 3 stations and fill them with parking slots :
        Station station1 = new Station(5.43, 0.4, StationStatus.OnService, TypeOfStation.Standard);
        station1.addParkingSlot(parkingSlotFree);
        station1.addParkingSlot(parkingSlotOccupiedMechanical);
        station1.addParkingSlot(parkingSlotOutOfOrder);
        station1.addParkingSlot(parkingSlotFree2);

        Station station2 = new Station(6.5, 1.5, StationStatus.Offline, TypeOfStation.Plus);
        station2.addParkingSlot(parkingSlotFree);
        station2.addParkingSlot(parkingSlotOccupiedMechanical);

        Station station3 = new Station(10.4,1.4, StationStatus.OnService, TypeOfStation.Plus);
        station3.addParkingSlot(parkingSlotFree);
        station3.addParkingSlot(parkingSlotFree2);
        station3.addParkingSlot(parkingSlotFree3);
        station3.addParkingSlot(parkingSlotOutOfOrder);
        station3.addParkingSlot(parkingSlotOccupiedElectrical);

        // Create an Array of stations
        ArrayList<Station> stations = new ArrayList<>();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);

        // Create 2 ride plannings : one with a mechanical bicycle and one with an electrical bicycle.
        RidePlanningNormal ridePlanningNormal = new RidePlanningNormal(6.3,1.4,120.3,31.4);
        Station startStationMechanical = ridePlanningNormal.findStartStation(stations, TypeOfBicycle.Mechanical);
        Station startStationElectrical = ridePlanningNormal.findStartStation(stations, TypeOfBicycle.Electrical);

        assertAll("Station returned must be the closest stations from start with the chosen type of bicycle available",
                () -> assertTrue(startStationMechanical.equals(station1)),
                () -> assertTrue(startStationElectrical.equals(station3))
        );
    }
    @Test
    @DisplayName("Test of the method findDestinationStation for RidePlanningNormal")
    public void testFindDestinationStation() {

        // Create bicycles of two different type
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
        Bicycle electricalBicycle = new Bicycle(TypeOfBicycle.Electrical);

        // Create free, occupied and out of order parking slot
        ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree2 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree3 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupiedMechanical = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedElectrical = new ParkingSlot(ParkingSlotStatus.Occupied, electricalBicycle);
        ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null);

        // Create 3 stations and fill them with parking slots :
        Station station1 = new Station(125.4, 30.1, StationStatus.OnService, TypeOfStation.Standard);
        station1.addParkingSlot(parkingSlotFree);
        station1.addParkingSlot(parkingSlotOccupiedMechanical);
        station1.addParkingSlot(parkingSlotOutOfOrder);
        station1.addParkingSlot(parkingSlotFree2);

        Station station2 = new Station(123.2, 30.5, StationStatus.Offline, TypeOfStation.Plus);
        station2.addParkingSlot(parkingSlotFree);
        station2.addParkingSlot(parkingSlotOccupiedMechanical);

        Station station3 = new Station(120.3, 31.4, StationStatus.OnService, TypeOfStation.Plus);
        station3.addParkingSlot(parkingSlotOutOfOrder);
        station3.addParkingSlot(parkingSlotOccupiedElectrical);

        // Create an Array of stations
        ArrayList<Station> stations = new ArrayList<>();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);

        // Create a ride planning with a destination point close to station3
        RidePlanningNormal ridePlanningNormal = new RidePlanningNormal(6.3, 1.4, 120.3, 31.4);
        Station destinationStation = ridePlanningNormal.findDestinationStation(stations);

        // station3 must not be returned as it is the closest station from destination point but there are no free parking slot
        // station2 must not be returned as it is offline
        assertTrue(destinationStation.equals(station1)); // Asserts that the destination station is station1
    }
}