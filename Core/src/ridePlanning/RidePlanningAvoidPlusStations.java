package src.ridePlanning;

import src.coreClasses.Station;
import src.enums.*;

import java.util.ArrayList;

public class RidePlanningAvoidPlusStations extends RidePlanningNormal {
    public RidePlanningAvoidPlusStations(Double startLatitude, Double startLongitude, Double destinationLatitude, Double destinationLongitude) {
        super(startLatitude,startLongitude,destinationLatitude,destinationLongitude);
    }

    @Override
    public Station findDestinationStation(ArrayList<Station> stations) {
        Station destinationStation = null;
        Double minDistance = Double.POSITIVE_INFINITY;
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
