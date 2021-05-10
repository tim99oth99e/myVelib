package src.test;

import src.classes.*;
import src.enums.*;
import src.rideplanning.*;

import java.util.ArrayList;

public class RidePlanningNormalTest {
    public static void main(String[] args) {

        //Parking Slots
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);

        ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree2 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree3 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupied = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupied2 = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null);

        //Stations
        Station station1 = new Station(120.3, 31.4, StationStatus.OnService, TypeOfStation.Standard);
        station1.addParkingSlot(parkingSlotFree);
        station1.addParkingSlot(parkingSlotOccupied);
        station1.addParkingSlot(parkingSlotOutOfOrder);
        station1.addParkingSlot(parkingSlotFree2);

        Station station2 = new Station(6.5, 1.5, StationStatus.Offline, TypeOfStation.Plus);
        station2.addParkingSlot(parkingSlotFree);
        station2.addParkingSlot(parkingSlotOccupied);

        Station station3 = new Station(120.4,31.4, StationStatus.OnService, TypeOfStation.Plus);
        station3.addParkingSlot(parkingSlotFree);
        station3.addParkingSlot(parkingSlotOutOfOrder);
        station3.addParkingSlot(parkingSlotOccupied);
        station3.addParkingSlot(parkingSlotOccupied2);
        station3.addParkingSlot(parkingSlotFree2);
        station3.addParkingSlot(parkingSlotFree3);

        ArrayList<Station> stations = new ArrayList<>();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);


        //Ride Planning
        RidePlanningNormal ridePlanningNormal = new RidePlanningNormal(6.3,1.4,120.3,31.4);
        RidePlanningNormal ridePlanningAvoidPlus = new RidePlanningAvoidPlusStations(6.3,1.4,120.3,31.4);
        RidePlanningNormal ridePlanningPreferPlus = new RidePlanningPreferPlusStations(6.3,1.4,120.3,31.4);
        RidePlanningNormal ridePlanningPreserveUniformityOfBicycle = new RidePlanningPreserveUniformityOfBicycle(6.3,1.4,120.3,31.4);

        //Start
//        System.out.println(ridePlanningNormal.findStartStation(stations, TypeOfBicycle.Mechanical));
//        System.out.println(ridePlanningNormal.findStartStation(stations, TypeOfBicycle.Electrical));
//        System.out.println(ridePlanningAvoidPlus.findStartStation(stations, TypeOfBicycle.Mechanical));

//        Destination
//        System.out.println(ridePlanningNormal.findDestinationStation(stations));
//        System.out.println("\n _____________________________");
//        System.out.println(ridePlanningAvoidPlus.findDestinationStation(stations));
//        System.out.println("\n _____________________________");
//        System.out.println(ridePlanningPreferPlus.findDestinationStation(stations));

//        System.out.println(station1.getNumberOfFreeParkingSlot());

//        System.out.println(ridePlanningPreserveUniformityOfBicycle.findDestinationStation(stations));
//        System.out.println(station1.getNumberOfBike(TypeOfBicycle.Mechanical));
//        System.out.println(station3.getNumberOfBike(TypeOfBicycle.Mechanical));

//        System.out.println(ridePlanningPreserveUniformityOfBicycle.findStartStation(stations, TypeOfBicycle.Mechanical));
    }
}
