package src.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import src.coreClasses.Bicycle;
import src.coreClasses.ParkingSlot;
import src.coreClasses.Station;
import src.enums.ParkingSlotStatus;
import src.enums.StationStatus;
import src.enums.TypeOfBicycle;
import src.enums.TypeOfStation;
import src.ridePlanning.RidePlanningPreserveUniformityOfBicycle;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for class RidePlanningPreserveUniformityOfBicycle
 */
public class RidePlanningPreserveUniformityOfBicycleTest {

    @Test
    @DisplayName("Test of the method findStartStation for RidePlanningPreserveUniformityOfBicycle")
    public void testFindStartStation() {

        // Create bicycles of two different type
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
        Bicycle electricalBicycle = new Bicycle(TypeOfBicycle.Electrical);

        // Create free, occupied and out of order parking slot
        ParkingSlot parkingSlotFree1 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupiedMechanical = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedMechanical2 = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedElectrical = new ParkingSlot(ParkingSlotStatus.Occupied, electricalBicycle);
        ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null);

        // Create 3 stations and fill them with parking slots :
        Station station1 = new Station(6.3001, 1.4, StationStatus.OnService, TypeOfStation.Standard);
        station1.addParkingSlot(parkingSlotFree1);
        station1.addParkingSlot(parkingSlotOccupiedMechanical);
        station1.addParkingSlot(parkingSlotOccupiedMechanical2);
        station1.addParkingSlot(parkingSlotOutOfOrder);

        Station station2 = new Station(6.3, 1.4, StationStatus.OnService, TypeOfStation.Plus);
        station2.addParkingSlot(parkingSlotFree1);
        station2.addParkingSlot(parkingSlotOccupiedMechanical);

        Station station3 = new Station(10.0, 1.4, StationStatus.OnService, TypeOfStation.Standard);
        station3.addParkingSlot(parkingSlotOutOfOrder);
        station3.addParkingSlot(parkingSlotOccupiedElectrical);
        station3.addParkingSlot(parkingSlotOccupiedMechanical);
        station3.addParkingSlot(parkingSlotFree1);

        // Create an Array of stations
        ArrayList<Station> stations = new ArrayList<>();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);

        // Create a ride planning with a starting point less than 5% further of the closest station station1
        RidePlanningPreserveUniformityOfBicycle ridePlanningPreserveUniformityOfBicycleLess5 = new RidePlanningPreserveUniformityOfBicycle(6.3, 1.4, 123.3, 30.4);
        // Create a ride planning with a starting point more than 5% further of the closest station station1
        RidePlanningPreserveUniformityOfBicycle ridePlanningPreserveUniformityOfBicycleMore5 = new RidePlanningPreserveUniformityOfBicycle(10.0, 1.4, 123.3, 30.4);

        Station startStationLess5 = ridePlanningPreserveUniformityOfBicycleLess5.findStartStation(stations, TypeOfBicycle.Mechanical);
        Station startStationMore5 = ridePlanningPreserveUniformityOfBicycleMore5.findStartStation(stations, TypeOfBicycle.Mechanical);

        assertAll("With this policy the choice of the source and destination station is affected by the number of available bikes",
                () -> assertTrue(startStationLess5.equals(station1)), // station1 must be returned even if station2 is closer because station1 has more mechanical bicycle and is less than 5% further of the closest station station2
                () -> assertTrue(startStationMore5.equals(station3)) // station3 must be returned even if station2 has more mechanical bicycle because station2 is more than 5% further of the closest station station3
        );
    }

    @Test
    @DisplayName("Test of the method findDestinationStation for RidePlanningPreserveUnifromityOfBicycle")
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
        Station station1 = new Station(123.32, 30.4, StationStatus.OnService, TypeOfStation.Standard);
        station1.addParkingSlot(parkingSlotFree);
        station1.addParkingSlot(parkingSlotOccupiedMechanical);
        station1.addParkingSlot(parkingSlotOutOfOrder);
        station1.addParkingSlot(parkingSlotFree2);

        Station station2 = new Station(123.3, 30.4, StationStatus.OnService, TypeOfStation.Plus);
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

        //Ride Planning
        RidePlanningPreserveUniformityOfBicycle ridePlanningPreferPlusStationLess5 = new RidePlanningPreserveUniformityOfBicycle(6.3, 1.4, 123.3, 30.4);
        RidePlanningPreserveUniformityOfBicycle ridePlanningPreferPlusStationMore5 = new RidePlanningPreserveUniformityOfBicycle(6.3, 1.4, 125.0, 30.4);

        // Create a ride planning with a destination point less than 5% further of the closest station station2
        Station destinationStationLess5 = ridePlanningPreferPlusStationLess5.findDestinationStation(stations);
        // Create a ride planning with a destination point more than 5% further of the closest station station3
        Station destinationStationMore5 = ridePlanningPreferPlusStationMore5.findDestinationStation(stations);

        assertAll("With this policy the choice of the source and destination station is affected by the number of available bikes",
                () -> assertTrue(destinationStationLess5.equals(station1)), // station1 must be returned even if station2 is closer because station1 has more free parking slot and is less than 5% further of the closest station station2
                () -> assertTrue(destinationStationMore5.equals(station3)) // station3 must be returned even if station1 has more free parking slot available because station1 is more than 5% further of the closest station station3
        );

    }
}