package src.test;

import org.junit.jupiter.api.*;
import src.coreClasses.Bicycle;
import src.coreClasses.ParkingSlot;
import src.enums.ParkingSlotStatus;
import src.enums.TypeOfBicycle;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSlotTest {

    ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free, null);

    Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
    ParkingSlot parkingSlotOccupied = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);

    ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null);

    @Test
    @DisplayName("Test type of parking slot")
    public void testTypeOfParkingSlot() {
        assertAll("Type",
                () -> assertTrue(parkingSlotFree.getParkingSlotStatus() == ParkingSlotStatus.Free),
                () -> assertTrue(parkingSlotOccupied.getParkingSlotStatus() == ParkingSlotStatus.Occupied),
                () -> assertTrue(parkingSlotOutOfOrder.getParkingSlotStatus() == ParkingSlotStatus.OutOfOrder)
        );

    }

    @Test
    @DisplayName("Test unique ID for parking slots")
    public void testUniqueID() {
        assertAll("unique ID",
                () -> assertTrue(parkingSlotFree.getId() != parkingSlotOccupied.getId()),
                () -> assertTrue(parkingSlotOccupied.getId() != parkingSlotOutOfOrder.getId())
        );
    }

}