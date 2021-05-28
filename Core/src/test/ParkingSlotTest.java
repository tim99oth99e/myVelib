package src.test;

import org.junit.jupiter.api.*;
import src.coreClasses.Bicycle;
import src.coreClasses.ParkingSlot;
import src.enums.ParkingSlotStatus;
import src.enums.TypeOfBicycle;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class ParkingSlot
 */
class ParkingSlotTest {

    ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free, null); // A free parking slot

    Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical); // A mechanical bicycle
    ParkingSlot parkingSlotOccupied = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle); // A parking slot occupied with the above mechanical bicycle

    ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null); // A parking slot which is out of order

    @Test
    @DisplayName("Test type of parking slot")
    public void testTypeOfParkingSlot() {
        assertAll("Assert all types are correct",
                () -> assertTrue(parkingSlotFree.getParkingSlotStatus() == ParkingSlotStatus.Free),
                () -> assertTrue(parkingSlotOccupied.getParkingSlotStatus() == ParkingSlotStatus.Occupied),
                () -> assertTrue(parkingSlotOutOfOrder.getParkingSlotStatus() == ParkingSlotStatus.OutOfOrder)
        );

    }

    @Test
    @DisplayName("Test unique ID for parking slots")
    public void testUniqueID() {
        assertAll("Assert all ID are unique",
                () -> assertTrue(parkingSlotFree.getId() != parkingSlotOccupied.getId()),
                () -> assertTrue(parkingSlotOccupied.getId() != parkingSlotOutOfOrder.getId())
        );
    }

}