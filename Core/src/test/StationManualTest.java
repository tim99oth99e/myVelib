package src.test;

import src.classes.ParkingSlot;
import src.classes.Station;
import src.enums.*;

public class StationManualTest {
    public static void main(String[] args) {
        Station station1 = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        //System.out.println(station1);

        ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free);
        ParkingSlot parkingSlotOccupied = new ParkingSlot(ParkingSlotStatus.Occupied);
        ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder);

        station1.AddParkingSlot(parkingSlotFree);
        station1.AddParkingSlot(parkingSlotOccupied);
        station1.AddParkingSlot(parkingSlotOutOfOrder);
        System.out.println(station1);
    }
}
