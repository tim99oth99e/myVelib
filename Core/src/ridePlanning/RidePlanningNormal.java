package src.ridePlanning;

import src.coreClasses.Station;
import src.enums.*;

import java.util.ArrayList;

public class RidePlanningNormal {
    protected Double startLatitude;
    protected Double startLongitude;

    protected Double destinationLatitude;
    protected Double destinationLongitude;

    public RidePlanningNormal(Double startLatitude, Double startLongitude, Double destinationLatitude, Double destinationLongitude) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
    }

    public Station findStartStation(ArrayList<Station> stations, TypeOfBicycle typeOfBicycle) {
        Station startStation = null;
        Double minDistance = Double.POSITIVE_INFINITY;
        for (Station station : stations){
            if (station.getStationStatus() == StationStatus.OnService){
                if (station.hasBike(typeOfBicycle)){
                    Double distance = station.computeDistance(startLatitude,startLongitude);
                    if (distance <= minDistance){
                        startStation = station;
                        minDistance = distance;
                    }
                }
            }
        }
        return startStation;
    }

    public Station findDestinationStation(ArrayList<Station> stations) {
        Station destinationStation = null;
        Double minDistance = Double.POSITIVE_INFINITY;
        for (Station station : stations){
            if (station.getStationStatus() == StationStatus.OnService){
                if (station.hasFreeParkingSlot()){
                    Double distance = station.computeDistance(destinationLatitude,destinationLongitude);
                    if (distance <= minDistance){
                        destinationStation = station;
                        minDistance = distance;
                    }
                }
            }
        }
        return destinationStation;
    }
}
