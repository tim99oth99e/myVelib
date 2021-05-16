package src.ridePlanning;

import src.coreClasses.Station;
import src.enums.*;

import java.util.ArrayList;

public class RidePlanningPreserveUniformityOfBicycle extends RidePlanningNormal {

    public RidePlanningPreserveUniformityOfBicycle(Double startLatitude, Double startLongitude, Double destinationLatitude, Double destinationLongitude) {
        super(startLatitude,startLongitude,destinationLatitude,destinationLongitude);
    }

    @Override
    public Station findStartStation(ArrayList<Station> stations, TypeOfBicycle typeOfBicycle) {
        Station startStation = null;
        Double minDistance = Double.POSITIVE_INFINITY;
        Station startStationWithMoreBicycle = null;
        Double minDistanceWithMoreBicycle = Double.POSITIVE_INFINITY;
        for (Station station : stations) {
            if (station.getStationStatus() == StationStatus.OnService) {
                if (station.hasBike(typeOfBicycle)) {
                    Double distance = station.computeDistance(super.startLatitude, super.startLongitude);
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
        if (startStationWithMoreBicycle.getNumberOfBike(typeOfBicycle) > startStation.getNumberOfBike(typeOfBicycle)) {
            return startStationWithMoreBicycle;
        }
        else {
            return startStation;
        }
    }

    @Override
    public Station findDestinationStation(ArrayList<Station> stations) {
        Station destinationStation = stations.get(0);
        Double minDistance = stations.get(0).computeDistance(super.destinationLatitude, super.destinationLongitude);
        Station destinationStationWithMoreBicycle = stations.get(0);
        Double minDistanceWithMoreBicycle = stations.get(0).computeDistance(super.destinationLatitude, super.destinationLongitude);
        for (Station station : stations) {
            if (station.getStationStatus() == StationStatus.OnService) {
                if (station.hasFreeParkingSlot()) {
                    Double distance = station.computeDistance(super.destinationLatitude, super.destinationLongitude);
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
