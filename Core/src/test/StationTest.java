package src.test;

import org.junit.jupiter.api.*;
import src.coreClasses.*;
import src.enums.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Station
 */
class StationTest {

    @Test
    @DisplayName("Test type of stations")
    public void testTypeOfStations() {
        Station stationStandard = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        Station stationPlus = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Plus);

        assertAll("Assert all types are correct",
                () -> assertTrue(stationPlus.getTypeOfStation() == TypeOfStation.Plus),
                () -> assertTrue(stationStandard.getTypeOfStation() == TypeOfStation.Standard)
        );

    }

    @Test
    @DisplayName("Test status of stations")
    public void testStatusOfStations() {
        Station stationStandard = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        Station stationStandardOffline = new Station(56.5, 17.5, StationStatus.Offline, TypeOfStation.Standard);

        assertAll("Assert all status are correct",
                () -> assertTrue(stationStandardOffline.getStationStatus() == StationStatus.Offline),
                () -> assertTrue(stationStandard.getStationStatus() == StationStatus.OnService)
        );
    }

    @Test
    @DisplayName("Test unique ID for stations")
    public void testUniqueID() {
        Station stationStandard = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        Station stationStandardOffline = new Station(56.5, 17.5, StationStatus.Offline, TypeOfStation.Standard);
        Station stationPlus = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Plus);

        assertAll("Assert all ID are unique",
                () -> assertTrue(stationStandard.getId() != stationStandardOffline.getId()),
                () -> assertTrue(stationStandard.getId() != stationPlus.getId())
        );
    }

    @Test
    @DisplayName("Test of the method computeDistance")
    public void testComputeDistance() {
        Station stationStandard = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        Station stationPlus = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Plus);

        assertAll("Distance must be calculated with a maximum error of 10 meters",
                () -> assertTrue(stationStandard.computeDistance(56.5, 17.5) >= 0
                        && stationStandard.computeDistance(56.5, 17.5) <= 10),
                () -> assertTrue(stationStandard.computeDistance(56.5133, 17.5) >= 1469
                        && stationStandard.computeDistance(56.5133, 17.5) <= 1489)
        );
    }

    @Test
    @DisplayName("Test of the method hasBike")
    public void testHasBike() {

        // Create bicycles of two different type
        Bicycle electricalBicycle1 = new Bicycle(TypeOfBicycle.Electrical);
        Bicycle electricalBicycle2 = new Bicycle(TypeOfBicycle.Electrical);
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);

        // Create free and occupied parking slot
        ParkingSlot parkingSlotFree1 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree2 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupiedMechanical = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedElectrical1 = new ParkingSlot(ParkingSlotStatus.Occupied, electricalBicycle1);
        ParkingSlot parkingSlotOccupiedElectrical2 = new ParkingSlot(ParkingSlotStatus.Occupied, electricalBicycle2);

        // Create 3 stations and fill them with parking slots :
        Station stationFree = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        Station stationFull = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        Station stationMix = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Plus);

        stationFree.addParkingSlot(parkingSlotFree1);
        stationFree.addParkingSlot(parkingSlotFree2);

        stationFull.addParkingSlot(parkingSlotOccupiedElectrical1);
        stationFull.addParkingSlot(parkingSlotOccupiedElectrical2);

        stationMix.addParkingSlot(parkingSlotFree1);
        stationMix.addParkingSlot(parkingSlotOccupiedElectrical1);
        stationMix.addParkingSlot(parkingSlotOccupiedMechanical);
        stationMix.addParkingSlot(parkingSlotFree2);

        assertAll("Correct number of bicycle within the station",
                () -> assertFalse(stationFree.hasBike(TypeOfBicycle.Mechanical)),
                () -> assertFalse(stationFull.hasBike(TypeOfBicycle.Mechanical)),
                () -> assertTrue(stationFull.hasBike(TypeOfBicycle.Electrical)),
                () -> assertTrue(stationMix.hasBike(TypeOfBicycle.Electrical)),
                () -> assertTrue(stationMix.hasBike(TypeOfBicycle.Mechanical))
        );
    }

    @Test
    @DisplayName("Test of the method hasFreeParkingSlot")
    void testHasFreeParkingSlot() {
        // Create bicycles of two different type
        Bicycle electricalBicycle1 = new Bicycle(TypeOfBicycle.Electrical);
        Bicycle electricalBicycle2 = new Bicycle(TypeOfBicycle.Electrical);
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);

        // Create free, occupied and out of order parking slot
        ParkingSlot parkingSlotFree1 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree2 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupiedMechanical = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedElectrical1 = new ParkingSlot(ParkingSlotStatus.Occupied, electricalBicycle1);
        ParkingSlot parkingSlotOccupiedElectrical2 = new ParkingSlot(ParkingSlotStatus.Occupied, electricalBicycle2);

        // Create 3 stations and fill them with parking slots :
        Station stationFree = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        Station stationFull = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        Station stationMix = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Plus);

        stationFree.addParkingSlot(parkingSlotFree1);
        stationFree.addParkingSlot(parkingSlotFree2);

        stationFull.addParkingSlot(parkingSlotOccupiedElectrical1);
        stationFull.addParkingSlot(parkingSlotOccupiedElectrical2);

        stationMix.addParkingSlot(parkingSlotFree1);
        stationMix.addParkingSlot(parkingSlotOccupiedElectrical1);
        stationMix.addParkingSlot(parkingSlotOccupiedMechanical);
        stationMix.addParkingSlot(parkingSlotFree2);

        assertAll("Correct number of bicycle within the station",
                () -> assertTrue(stationFree.hasFreeParkingSlot()),
                () -> assertFalse(stationFull.hasFreeParkingSlot()),
                () -> assertTrue(stationMix.hasFreeParkingSlot())
        );
    }

    @Test
    @DisplayName("Test of the method getNumberOfFreeParkingSlot")
    void testGetNumberOfFreeParkingSlot() {
        // Create bicycles of two different type
        Bicycle electricalBicycle1 = new Bicycle(TypeOfBicycle.Electrical);
        Bicycle electricalBicycle2 = new Bicycle(TypeOfBicycle.Electrical);
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);

        // Create free, occupied and out of order parking slot
        ParkingSlot parkingSlotFree1 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree2 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupiedMechanical = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedElectrical1 = new ParkingSlot(ParkingSlotStatus.Occupied, electricalBicycle1);
        ParkingSlot parkingSlotOccupiedElectrical2 = new ParkingSlot(ParkingSlotStatus.Occupied, electricalBicycle2);

        // Create 3 stations and fill them with parking slots :
        Station stationFree = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        Station stationFull = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        Station stationMix = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Plus);

        stationFree.addParkingSlot(parkingSlotFree1);
        stationFree.addParkingSlot(parkingSlotFree2);

        stationFull.addParkingSlot(parkingSlotOccupiedElectrical1);
        stationFull.addParkingSlot(parkingSlotOccupiedElectrical2);

        stationMix.addParkingSlot(parkingSlotFree1);
        stationMix.addParkingSlot(parkingSlotOccupiedElectrical1);
        stationMix.addParkingSlot(parkingSlotOccupiedMechanical);
        stationMix.addParkingSlot(parkingSlotFree2);

        assertAll("Correct number of bicycle within the station",
                () -> assertTrue(stationFree.getNumberOfFreeParkingSlot() == 2),
                () -> assertTrue(stationFull.getNumberOfFreeParkingSlot() == 0),
                () -> assertTrue(stationMix.getNumberOfFreeParkingSlot() == 2)
        );
    }

    @Test
    @DisplayName("Test of the method addParkingSlot")
    public void testAddParkingSlot() {
        // Create mechanical bicycle
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);

        // Create free, occupied and out of order parking slot
        ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupied = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null);

        // Create a station and fill it with parking slots :
        Station stationStandard = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);

        stationStandard.addParkingSlot(parkingSlotFree);
        stationStandard.addParkingSlot(parkingSlotOccupied);
        stationStandard.addParkingSlot(parkingSlotOutOfOrder);

        assertEquals(stationStandard.getParkingSlotHashMap().size(),3);
    }


    @Test
    @DisplayName("Test of the method getNumberOfBike")
    public void testGetNumberOfBike() {
        // Create bicycles of two different type
        Bicycle electricalBicycle1 = new Bicycle(TypeOfBicycle.Electrical);
        Bicycle electricalBicycle2 = new Bicycle(TypeOfBicycle.Electrical);
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);

        // Create free, occupied and out of order parking slot
        ParkingSlot parkingSlotFree1 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree2 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupiedMechanical = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedElectrical1 = new ParkingSlot(ParkingSlotStatus.Occupied, electricalBicycle1);
        ParkingSlot parkingSlotOccupiedElectrical2 = new ParkingSlot(ParkingSlotStatus.Occupied, electricalBicycle2);

        // Create 3 stations and fill them with parking slots :
        Station stationFree = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        Station stationFull = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        Station stationMix = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Plus);

        stationFree.addParkingSlot(parkingSlotFree1);
        stationFree.addParkingSlot(parkingSlotFree2);

        stationFull.addParkingSlot(parkingSlotOccupiedElectrical1);
        stationFull.addParkingSlot(parkingSlotOccupiedElectrical2);
        stationFull.addParkingSlot(parkingSlotOccupiedMechanical);

        stationMix.addParkingSlot(parkingSlotFree1);
        stationMix.addParkingSlot(parkingSlotOccupiedElectrical1);
        stationMix.addParkingSlot(parkingSlotOccupiedMechanical);
        stationMix.addParkingSlot(parkingSlotFree2);

        assertAll("Correct number of bicycle within the station",
                () -> assertTrue(stationFree.getNumberOfBike(TypeOfBicycle.Mechanical) == 0),
                () -> assertTrue(stationFull.getNumberOfBike(TypeOfBicycle.Mechanical) == 1),
                () -> assertTrue(stationFull.getNumberOfBike(TypeOfBicycle.Electrical) == 2),
                () -> assertTrue(stationMix.getNumberOfBike(TypeOfBicycle.Mechanical) == 1)
        );
    }
}