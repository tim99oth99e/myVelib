package src.rideplanning;

import src.classes.*;
import src.enums.*;

import java.util.ArrayList;

public class RidePlanningPreferPlusStations implements RidePlanning {
    protected Double startLatitude;
    protected Double startLongitude;

    protected Double destinationLatitude;
    protected Double destinationLongitude;

    public RidePlanningPreferPlusStations(Double startLatitude, Double startLongitude, Double destinationLatitude, Double destinationLongitude) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.destinationLatitude = destinationLatitude;
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
        Station destinationStation = stations.get(0);
        Double minDistance = stations.get(0).computeDistance(destinationLatitude, destinationLongitude);
        Station destinationStationPlus = stations.get(0);
        Double minDistancePlus = stations.get(0).computeDistance(destinationLatitude, destinationLongitude);
        for (Station station : stations) {
            if (station.getStationStatus() == StationStatus.OnService) {
                if (station.hasFreeParkingSlot()) {
                    if (station.getTypeOfStation() == TypeOfStation.Standard) {
                        Double distance = station.computeDistance(destinationLatitude, destinationLongitude);
                        if (distance <= minDistance) {
                            destinationStation = station;
                            minDistance = distance;
                        }
                    }
                    if (station.getTypeOfStation() == TypeOfStation.Plus) {
                        Double distance = station.computeDistance(destinationLatitude, destinationLongitude);
                        if (distance <= minDistancePlus) {
                            destinationStationPlus = station;
                            minDistancePlus = distance;
                        }
                    }
                }
            }
        }
        if (minDistancePlus <= 1.1 * minDistance) {
            return destinationStationPlus;
        } else {
            return destinationStation;
        }
    }
}
