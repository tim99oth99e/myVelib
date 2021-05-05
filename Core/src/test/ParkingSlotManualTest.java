package src.test;

import src.classes.ParkingSlot;
import src.enums.*;

public class ParkingSlotManualTest {
    public static void main(String[] args) {
        ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free);
        System.out.println(parkingSlotFree);

        ParkingSlot parkingSlotOccupied = new ParkingSlot(ParkingSlotStatus.Occupied);
        System.out.println(parkingSlotOccupied);

        ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder);
        System.out.println(parkingSlotOutOfOrder);
    }
}
