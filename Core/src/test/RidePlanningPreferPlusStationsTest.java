package src.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import src.coreClasses.*;
import src.enums.*;
import src.ridePlanning.RidePlanningAvoidPlusStations;
import src.ridePlanning.RidePlanningPreferPlusStations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for class RidePlanningPreferPlusStation
 */
public class RidePlanningPreferPlusStationsTest {

    @Test
    @DisplayName("Test of the method findDestinationStation for RidePlanningPreferPlusStations")
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
        Station station1 = new Station(123.301, 30.4, StationStatus.OnService, TypeOfStation.Standard);
        station1.addParkingSlot(parkingSlotFree);
        station1.addParkingSlot(parkingSlotOccupiedMechanical);
        station1.addParkingSlot(parkingSlotOutOfOrder);
        station1.addParkingSlot(parkingSlotFree2);

        Station station2 = new Station(123.30109, 30.4, StationStatus.OnService, TypeOfStation.Plus);
        station2.addParkingSlot(parkingSlotFree);
        station2.addParkingSlot(parkingSlotOccupiedMechanical);

        Station station3 = new Station(125.0, 30.4, StationStatus.OnService, TypeOfStation.Standard);
        station3.addParkingSlot(parkingSlotOutOfOrder);
        station3.addParkingSlot(parkingSlotOccupiedElectrical);
        station3.addParkingSlot(parkingSlotFree);

        // Create an Array of stations
        ArrayList<Station> stations = new ArrayList<>();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);

        // Create a ride planning with a destination point less than 10% further of the closest station station1
        RidePlanningPreferPlusStations ridePlanningPreferPlusStationLess10 = new RidePlanningPreferPlusStations(6.3, 1.4, 123.3, 30.4);
        // Create a ride planning with a destination point more than 10% further of the closest station station3
        RidePlanningPreferPlusStations ridePlanningPreferPlusStationMore10 = new RidePlanningPreferPlusStations(6.3, 1.4, 125.0, 30.4);

        Station destinationStationLess10 = ridePlanningPreferPlusStationLess10.findDestinationStation(stations);
        Station destinationStationMore10 = ridePlanningPreferPlusStationMore10.findDestinationStation(stations);


        assertAll("Return station should be a plus station (given a plus station no further away than 10% " +
                        "of the distance of the closest station to the destination location exists).",
                () -> assertTrue(destinationStationLess10.equals(station2)), // station1 must not be returned even if it is the closest station from destination point because station2 is a -plus- station that is not 10% further from destination point
                () -> assertTrue(destinationStationMore10.equals(station3)) // Since station2 is more than 10% further of station3 from the destination point, station3 must be returned even if station2 is a plus -station-
        );

    }
}