package src.ridePlanning;

import src.coreClasses.Station;
import src.enums.*;

import java.util.ArrayList;

/**
 * This class describes a ride planning that preserves the uniformity of bicycles distribution amongst stations.
 *
 * A ride planning helps users to plan a ride from a starting location to a destination location :
 * Given the starting and destination GPS coordinates the ride planning functionality will identify the optimal start and end stations
 * from/to where the bike should be taken/dropped according to the following criteria:
 *
 * - the start, respectively the end, station, for a ride should be as close as possible to
 * the starting, respectively to the destination, location of the ride.
 *
 * - the start station should have one bike of the desired kind (electrical or mechanical)
 * available for renting
 *
 * - the end station should have at least one free parking slot
 * - with this policy the choice of the source and destination station is affected by the number
 * of available bikes (at source station) and free slots (at destination). Specifically, let
 * s0 be the closest station to the starting location with at least one available bike of the
 * wanted kind, and sd be the station closest to the destination location with at least
 * one free parking slot. Then if a station s'0 whose distance from the starting location
 * is no more than 105% the distance of s0 from the start location has a larger number
 * of bikes (of the wanted kind) than those available at s0 it should be selected in place
 * of s0. Similarly if a station s'd (whose distance from the destination location is at
 * most 105% of the distance of sd from the destination location) has a larger number
 * of free parking slots than sd it should be selected as the destination station in place
 * of sd.
 */
public class RidePlanningPreserveUniformityOfBicycle extends RidePlanningNormal {

    /**
     * Instantiates a new Ride planning that preserves uniformity of bicycle amongst stations.
     *
     * @param startLatitude        the start latitude
     * @param startLongitude       the start longitude
     * @param destinationLatitude  the destination latitude
     * @param destinationLongitude the destination longitude
     */
    public RidePlanningPreserveUniformityOfBicycle(Double startLatitude, Double startLongitude, Double destinationLatitude, Double destinationLongitude) {
        super(startLatitude,startLongitude,destinationLatitude,destinationLongitude);
    }

    /**
     * Helps the user to find the optimal start station.
     *
     * @param stations      the stations
     * @param typeOfBicycle the type of bicycle
     * @return the station as close as possible to the starting location of the ride while preserving uniformity of bicycle amongst stations, where a bicycle of the given type is available.
     */
    @Override
    public Station findStartStation(ArrayList<Station> stations, TypeOfBicycle typeOfBicycle) {
        Station startStation = null;
        Double minDistance = Double.POSITIVE_INFINITY;
        Station startStationWithMoreBicycle = stations.get(0);
        Double minDistanceWithMoreBicycle = stations.get(0).computeDistance(super.destinationLatitude, super.destinationLongitude);
        for (Station station : stations) { // Parsing through the stations
            if (station.getStationStatus() == StationStatus.OnService) { // If the station is on service
                if (station.hasBike(typeOfBicycle)) { // If the station has at least one bicycle of the chosen type
                    Double distance = station.computeDistance(super.startLatitude, super.startLongitude);
                    if (distance <= minDistance) { // To find the closest station
                        startStation = station;
                        minDistance = distance;
                    }
                    else if (distance <= minDistance*1.05) { // To find the closest station with more bicycle
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

    /**
     * Helps the user to find the optimal destination station.
     *
     * @param stations the stations
     * @return the station as close as possible to the destination location of the ride while preserving uniformity of bicycle amongst stations, where at least one parking slot is free.
     */
    @Override
    public Station findDestinationStation(ArrayList<Station> stations) {
        Station destinationStation = null;
        Double minDistance = Double.POSITIVE_INFINITY;
        Station destinationStationWithMoreBicycle = stations.get(0);
        Double minDistanceWithMoreBicycle = stations.get(0).computeDistance(super.destinationLatitude, super.destinationLongitude);

        for (Station station : stations) { // Parsing through the stations
            if (station.getStationStatus() == StationStatus.OnService) { // If the station is on service
                if (station.hasFreeParkingSlot()) { // If the station has at least one free parking slot
                    Double distance = station.computeDistance(super.destinationLatitude, super.destinationLongitude);
                    if (distance <= minDistance) { // To find the closest station
                        destinationStation = station;
                        minDistance = distance;
                    }
                    else if (distance <= minDistance*1.05) { // To find the closest station with more bicycle
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
