package src.rideplanning;

import src.classes.*;
import src.enums.*;

import java.util.ArrayList;

public class RidePlanningAvoidPlusStations extends RidePlanningNormal {
    public RidePlanningAvoidPlusStations(Double startLatitude, Double startLongitude, Double destinationLatitude, Double destinationLongitude) {
        super(startLatitude,startLongitude,destinationLatitude,destinationLongitude);

    }

    @Override
    public Station findDestinationStation(ArrayList<Station> stations) {
        Station destinationStation = stations.get(0);
        Double minDistance = stations.get(0).computeDistance(super.destinationLatitude,super.destinationLongitude);
        for (Station station : stations){
            if (station.getStationStatus() == StationStatus.OnService){
                if (station.getTypeOfStation() == TypeOfStation.Standard){
                    if (station.hasFreeParkingSlot()){
                        Double distance = station.computeDistance(super.destinationLatitude,super.destinationLongitude);
                        if (distance <= minDistance){
                            destinationStation = station;
                            minDistance = distance;
                        }
                    }
                }
            }
        }
        return destinationStation;
    }
}
