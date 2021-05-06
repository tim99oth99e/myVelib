package src.test;

import src.classes.*;
import src.enums.*;

public class ParkingSlotManualTest {
    public static void main(String[] args) {
        ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free, null);
        System.out.println(parkingSlotFree);

        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
        ParkingSlot parkingSlotOccupied = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        System.out.println(parkingSlotOccupied);

        ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null);
        System.out.println(parkingSlotOutOfOrder);
    }
}
