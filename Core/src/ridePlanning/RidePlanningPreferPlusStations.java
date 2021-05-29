package src.ridePlanning;

import src.coreClasses.Station;
import src.enums.*;

import java.util.ArrayList;

/**
 * This class describes a ride planning that prefer -plus- stations.
 *
 * A ride planning helps users to plan a ride from a starting location to a destination location :
 * Given the starting and destination GPS coordinates the ride planning functionality will identify the -optimal- start and end stations
 * from/to where the bike should be taken/dropped according to the following criteria:
 * - the start, respectively the end, station, for a ride should be as close as possible to
 * the starting, respectively to the destination, location of the ride.
 * - the start station should have one bike of the desired kind (electrical or mechanical)
 * available for renting
 * - the end station should have at least one free parking slot and the return station should be a -plus-
 * station (given a -plus- station no further away than 10% of the distance of the
 * closest station to the destination location exists). If no such a -plus- station exists
 * then this policy behaves normally (as a minimal walking distance).
 */
public class RidePlanningPreferPlusStations extends RidePlanningNormal {

    /**
     * Instantiates a new Ride planning that prefer -plus- stations.
     *
     * @param startLatitude        the start latitude
     * @param startLongitude       the start longitude
     * @param destinationLatitude  the destination latitude
     * @param destinationLongitude the destination longitude
     */
    public RidePlanningPreferPlusStations(Double startLatitude, Double startLongitude, Double destinationLatitude, Double destinationLongitude) {
        super(startLatitude,startLongitude,destinationLatitude,destinationLongitude);
    }

    /**
     * Helps the user to find the optimal destination station.
     *
     * @param stations the stations
     * @return the station as close as possible to the destination location of the ride, where at least one parking slot is free. The return station should be a -plus-
     * station (given a -plus- station no further away than 10% of the distance of the
     * closest station to the destination location exists). If no such a -plus- station exists
     * then this policy behaves normally (as a minimal walking distance).
     */
    @Override
    public Station findDestinationStation(ArrayList<Station> stations) {
        Station destinationStation = null;
        Double minDistance = Double.POSITIVE_INFINITY;
        Station destinationStationPlus = null;
        Double minDistancePlus = Double.POSITIVE_INFINITY;
        for (Station station : stations) { // Parsing through the stations
            if (station.getStationStatus() == StationStatus.OnService) { // If the station is on service
                if (station.hasFreeParkingSlot()) { // If the station has at least one parking slot
                    if (station.getTypeOfStation() == TypeOfStation.Standard) { // If the station his not a -plus- station
                        Double distance = station.computeDistance(super.destinationLatitude, super.destinationLongitude);
                        if (distance <= minDistance) { // To find the closest -plus- station
                            destinationStation = station;
                            minDistance = distance;
                        }
                    }
                    if (station.getTypeOfStation() == TypeOfStation.Plus) { // If the station his a -plus- station
                        Double distance = station.computeDistance(super.destinationLatitude, super.destinationLongitude);
                        if (distance <= minDistancePlus) { // To find the closest station
                            destinationStationPlus = station;
                            minDistancePlus = distance;
                        }
                    }
                }
            }
        }
        if (minDistancePlus <= 1.1 * minDistance) {  // If a -plus- station is no further away than 10% of the distance of the closest station to the destination location
            return destinationStationPlus;

        } else {
            return destinationStation;
        }
    }
}
