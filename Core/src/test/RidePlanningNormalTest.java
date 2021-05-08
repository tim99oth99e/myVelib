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
        ParkingSlot parkingSlotOccupied = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null);

        //Stations
        Station station1 = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
        station1.addParkingSlot(parkingSlotFree);
        station1.addParkingSlot(parkingSlotOccupied);
        station1.addParkingSlot(parkingSlotOutOfOrder);

        Station station2 = new Station(6.5, 1.5, StationStatus.Offline, TypeOfStation.Plus);
        station2.addParkingSlot(parkingSlotFree);
        station2.addParkingSlot(parkingSlotOccupied);

        Station station3 = new Station(126.5, 34.5, StationStatus.OnService, TypeOfStation.Plus);
        station3.addParkingSlot(parkingSlotFree);
        station3.addParkingSlot(parkingSlotOutOfOrder);

        ArrayList<Station> stations = new ArrayList<>();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);


        //Ride Planning
        RidePlanning ridePlanningNormal = new RidePlanningNormal(6.3,1.4,120.3,31.4);
        RidePlanning ridePlanningAvoidPlus = new RidePlanningAvoidPlusStations(6.3,1.4,120.3,31.4);

        //Start
//        System.out.println(ridePlanning.findStartStation(stations, TypeOfBicycle.Mechanical));
//        System.out.println(ridePlanning.findStartStation(stations, TypeOfBicycle.Electrical));

        //Destination
        System.out.println(ridePlanningNormal.findDestinationStation(stations));
        System.out.println("\n _____________________________");
        System.out.println(ridePlanningAvoidPlus.findDestinationStation(stations));

    }
}
