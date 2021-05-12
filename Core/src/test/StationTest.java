package src.test;

import org.junit.jupiter.api.*;
import src.coreClasses.*;
import src.enums.*;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {
    Station stationStandard = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
    Station stationStandardOffline = new Station(56.5, 17.5, StationStatus.Offline, TypeOfStation.Standard);
    Station stationPlus = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Plus);

    Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);

    ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free, null);
    ParkingSlot parkingSlotOccupied = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
    ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null);

    @Test
    @DisplayName("Test type of station")
    public void testTypeOfParkingSlot() {
        assertAll("Type",
                () -> assertTrue(stationPlus.getTypeOfStation() == TypeOfStation.Plus),
                () -> assertTrue(stationStandard.getTypeOfStation() == TypeOfStation.Standard)
        );

    }

    @Test
    @DisplayName("Test status of stations")
    public void testStatusOfParkingSlot() {
        assertAll("Type",
                () -> assertTrue(stationStandardOffline.getStationStatus() == StationStatus.Offline),
                () -> assertTrue(stationStandard.getStationStatus() == StationStatus.OnService)
        );
    }

    @Test
    @DisplayName("Test unique ID for stations")
    public void testUniqueID() {
        assertAll("unique ID",
                () -> assertTrue(stationStandard.getId() != stationStandardOffline.getId()),
                () -> assertTrue(stationStandard.getId() != stationPlus.getId())
        );
    }

    @Test
    void addParkingSlot() {
    }

    @Test
    void computeDistance() {
    }

    @Test
    void hasBike() {
    }

    @Test
    void hasFreeParkingSlot() {
    }

    @Test
    void getNumberOfFreeParkingSlot() {
    }

    @Test
    void getNumberOfBike() {
    }
}