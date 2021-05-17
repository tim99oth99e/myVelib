package src.test;

import org.junit.jupiter.api.*;
import src.coreClasses.*;
import src.enums.*;
import src.ridePlanning.RidePlanningNormal;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RidePlanningNormalTest {

    @Test
    @DisplayName("Test of the method findStartStation for for RidePlanningNormal")
    public void testFindStartStationNormal() {

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

        //Array of station
        ArrayList<Station> stations = new ArrayList<>();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);

        //Ride Planning
        RidePlanningNormal ridePlanningNormal = new RidePlanningNormal(6.3,1.4,120.3,31.4);
        Station startStationMechanical = ridePlanningNormal.findStartStation(stations, TypeOfBicycle.Mechanical);
        Station startStationElectrical = ridePlanningNormal.findStartStation(stations, TypeOfBicycle.Electrical);

        assertAll("Station returned must be the closest stations from start with the choosen type of bicycle available",
                () -> assertTrue(startStationMechanical.equals(station1)),
                () -> assertTrue(startStationElectrical.equals(station3))
        );
    }
    @Test
    @DisplayName("Test of the method findDestinationStation for RidePlanningNormal")
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
        RidePlanningNormal ridePlanningNormal = new RidePlanningNormal(6.3, 1.4, 120.3, 31.4);
        Station destinationStation = ridePlanningNormal.findDestinationStation(stations);

        assertTrue(destinationStation.equals(station1));
    }
}