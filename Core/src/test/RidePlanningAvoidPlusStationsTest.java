package src.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import src.coreClasses.*;
import src.enums.*;
import src.ridePlanning.RidePlanningAvoidPlusStations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for class RidePlanningAvoidPlusStation
 */
public class RidePlanningAvoidPlusStationsTest {

    @Test
    @DisplayName("Test of the method findDestinationStation for RidePlanningAvoidPlusStations")
    public void testFindDestinationStation() {

        // Create bicycles of two different type
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
        Bicycle electricalBicycle = new Bicycle(TypeOfBicycle.Electrical);

        // Create free, occupied and out of order parking slot
        ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree2 = new ParkingSlot(ParkingSlotStatus.Free, null);
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

        // Create a ride planning with a destination point close to station2
        RidePlanningAvoidPlusStations ridePlanningAvoidPlusStations = new RidePlanningAvoidPlusStations(6.3, 1.4, 123.3, 30.4);
        Station destinationStation = ridePlanningAvoidPlusStations.findDestinationStation(stations);

        // station2 must not be returned as it is the closest station from destination point but it is a -plus- station
        assertTrue(destinationStation.equals(station1)); // Asserts that the destination station is station1

    }
}