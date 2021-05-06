package src.rideplanning;

import src.classes.*;
import src.enums.*;

import java.util.ArrayList;

public class RidePlanningNormal implements RidePlanning {
    protected Double startLatitude;
    protected Double startLongitude;

    protected Double destinationtLatitude;
    protected Double destinationLongitude;

    public RidePlanningNormal(Double startLatitude, Double startLongitude, Double destinationtLatitude, Double destinationLongitude) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.destinationtLatitude = destinationtLatitude;
        this.destinationLongitude = destinationLongitude;
    }

    @Override
    public Station findStartStation(ArrayList<Station> stations, TypeOfBicycle typeOfBicycle) {
        Station startStation = null;
        Double minDistance = stations.get(0).computeDistance(startLatitude,startLongitude);
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

    @Override
    public Station findDestinationStation(ArrayList<Station> stations) {
        Station destinationStation = null;
        Double minDistance = stations.get(0).computeDistance(destinationtLatitude,destinationLongitude);
        for (Station station : stations){
            if (station.getStationStatus() == StationStatus.OnService){
                if (station.hasFreeParkingSlot()){
                    Double distance = station.computeDistance(destinationtLatitude,destinationLongitude);
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
