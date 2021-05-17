package src.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import src.coreClasses.*;
import src.enums.*;
import src.ridePlanning.RidePlanningAvoidPlusStations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RidePlanningAvoidPlusStationsTest {

    @Test
    @DisplayName("Test of the method findDestinationStation for RidePlanningAvoidPlusStations")
    public void testFindDestinationStation() {

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

        //Array of station
        ArrayList<Station> stations = new ArrayList<>();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);

        //Ride Planning
        RidePlanningAvoidPlusStations ridePlanningAvoidPlusStations = new RidePlanningAvoidPlusStations(6.3, 1.4, 123.3, 30.4);
        Station destinationStation = ridePlanningAvoidPlusStations.findDestinationStation(stations);

        assertTrue(destinationStation.equals(station1));
    }
}