package src.rideplanning;

import src.classes.*;
import src.enums.*;

import java.util.ArrayList;

public class RidePlanningPreserveUniformityOfBicycle implements RidePlanning {
    protected Double startLatitude;
    protected Double startLongitude;

    protected Double destinationLatitude;
    protected Double destinationLongitude;

    public RidePlanningPreserveUniformityOfBicycle(Double startLatitude, Double startLongitude, Double destinationLatitude, Double destinationLongitude) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
    }

    @Override
    public Station findStartStation(ArrayList<Station> stations, TypeOfBicycle typeOfBicycle) {
        Station startStation = stations.get(0);
        Double minDistance = stations.get(0).computeDistance(startLatitude, startLongitude);
        Station startStationWithMoreBicycle = stations.get(0);
        Double minDistanceWithMoreBicycle = stations.get(0).computeDistance(startLatitude, startLongitude);
        for (Station station : stations) {
            if (station.getStationStatus() == StationStatus.OnService) {
                if (station.hasBike(typeOfBicycle)) {
                    Double distance = station.computeDistance(startLatitude, startLongitude);
                    if (distance <= minDistance) {
                        startStation = station;
                        minDistance = distance;
                    }
                    else if (distance <= minDistance*1.05) {
                        startStationWithMoreBicycle = station;
                        minDistanceWithMoreBicycle = distance;
                    }
                }
            }
        }
        System.out.println(startStationWithMoreBicycle.getNumberOfBike(typeOfBicycle));
        System.out.println(startStation.getNumberOfBike(typeOfBicycle));
        if (startStationWithMoreBicycle.getNumberOfBike(typeOfBicycle) > startStation.getNumberOfBike(typeOfBicycle)) {
            return startStationWithMoreBicycle;
        }
        else {
            return startStation;
        }
    }

    public Station findDestinationStation(ArrayList<Station> stations) {
        Station destinationStation = stations.get(0);
        Double minDistance = stations.get(0).computeDistance(destinationLatitude, destinationLongitude);
        Station destinationStationWithMoreBicycle = stations.get(0);
        Double minDistanceWithMoreBicycle = stations.get(0).computeDistance(destinationLatitude, destinationLongitude);
        for (Station station : stations) {
            if (station.getStationStatus() == StationStatus.OnService) {
                if (station.hasFreeParkingSlot()) {
                    Double distance = station.computeDistance(destinationLatitude, destinationLongitude);
                    if (distance <= minDistance) {
                        destinationStation = station;
                        minDistance = distance;
                    }
                    else if (distance <= minDistance*1.05) {
                        destinationStationWithMoreBicycle = station;
                        minDistanceWithMoreBicycle = distance;
                    }
                }
            }
        }
        if (destinationStationWithMoreBicycle.getNumberOfFreeParkingSlot() > destinationStation.getNumberOfFreeParkingSlot()) {
            return destinationStationWithMoreBicycle;
        }
        else {
            return destinationStation;
        }
    }
}
