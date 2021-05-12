package src.manualTest;

import src.coreClasses.Bicycle;
import src.coreClasses.ParkingSlot;
import src.coreClasses.Station;
import src.enums.*;

public class StationManualTest {
    public static void main(String[] args) {
        Station station1 = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        //System.out.println(station1);

        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);


        ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupied = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null);

        station1.addParkingSlot(parkingSlotFree);
        station1.addParkingSlot(parkingSlotOccupied);
        station1.addParkingSlot(parkingSlotOutOfOrder);

        System.out.println(station1);
        System.out.println(parkingSlotFree);
        System.out.println(station1.getParkingSlotHashMap().get(0).getParkingSlotStatus() ==  parkingSlotFree.getParkingSlotStatus());

//        System.out.println(station1.hasBike(TypeOfBicycle.Mechanical));
//        System.out.println(station1.hasBike(TypeOfBicycle.Electrical));

        Station station2 = new Station(6.5, 7.5, StationStatus.OnService, TypeOfStation.Standard);
//        System.out.println(station2.hasBike());

//        System.out.println(station1);
//        System.out.println(station2);
//
//        Double distance1 = station1.computeDistance(56.5,17.5);
//        System.out.println(distance1);
////
//        Double distance2 = station1.computeDistance(56.5133,17.5);
//        System.out.println(distance2);


    }
}
