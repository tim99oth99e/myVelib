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

public class RidePlanningPreferPlusStationsTest {

    @Test
    @DisplayName("Test of the method findDestinationStation for RidePlanningPreferPlusStations")
    public void testFindDestinationStationNormal() {

        //Parking Slots
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
        Bicycle electricalBicycle = new Bicycle(TypeOfBicycle.Electrical);

        ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree2 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree3 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupiedMechanical = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedElectrical = new ParkingSlot(ParkingSlotStatus.Occupied, electricalBicycle);
        ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null);

        //Stations
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

        //Array of station
        ArrayList<Station> stations = new ArrayList<>();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);

        //Ride Planning
        RidePlanningPreferPlusStations ridePlanningPreferPlusStationLess10 = new RidePlanningPreferPlusStations(6.3, 1.4, 123.3, 30.4);
        RidePlanningPreferPlusStations ridePlanningPreferPlusStationMore10 = new RidePlanningPreferPlusStations(6.3, 1.4, 125.0, 30.4);

        Station destinationStationLess10 = ridePlanningPreferPlusStationLess10.findDestinationStation(stations);
        Station destinationStationMore10 = ridePlanningPreferPlusStationMore10.findDestinationStation(stations);

        assertAll("Return station should be a “plus” station (given a “plus” station no further away than 10% " +
                        "of the distance of the closest station to the destination location exists).",
                () -> assertTrue(destinationStationLess10.equals(station2)),
                () -> assertTrue(destinationStationMore10.equals(station3))
        );

    }
}