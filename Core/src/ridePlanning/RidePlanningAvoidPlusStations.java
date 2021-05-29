package src.ridePlanning;

import src.coreClasses.Station;
import src.enums.*;

import java.util.ArrayList;

/**
 * This class describes a ride planning that avoid -plus- stations.
 *
 * A ride planning helps users to plan a ride from a starting location to a destination location :
 * Given the starting and destination GPS coordinates the ride planning functionality will identify the optimal start and end stations
 * from/to where the bike should be taken/dropped according to the following criteria:
 * - the start, respectively the end, station, for a ride should be as close as possible to
 * the starting, respectively to the destination, location of the ride.
 * - the start station should have one bike of the desired kind (electrical or mechanical)
 * available for renting
 * - the end station should have at least one free parking slot and cannot be a -plus- station.
 */
public class RidePlanningAvoidPlusStations extends RidePlanningNormal {

    /**
     * Instantiates a new ride planning that avoid -plus- stations.
     *
     * @param startLatitude        the start latitude
     * @param startLongitude       the start longitude
     * @param destinationLatitude  the destination latitude
     * @param destinationLongitude the destination longitude
     */
    public RidePlanningAvoidPlusStations(Double startLatitude, Double startLongitude, Double destinationLatitude, Double destinationLongitude) {
        super(startLatitude,startLongitude,destinationLatitude,destinationLongitude);
    }

    /**
     * Helps the user to find the optimal destination station.
     *
     * @param stations the stations
     * @return the station as close as possible to the destination location of the ride, where at least one parking slot is free and which is not a -plus- station.
     */
    @Override
    public Station findDestinationStation(ArrayList<Station> stations) {
        Station destinationStation = null;
        Double minDistance = Double.POSITIVE_INFINITY;
        for (Station station : stations){ // Parsing through the stations
            if (station.getStationStatus() == StationStatus.OnService){ // If the station is on service
                if (station.getTypeOfStation() == TypeOfStation.Standard){  // If the station his not a -plus- station
                    if (station.hasFreeParkingSlot()){ // If the station has at least one parking slot
                        Double distance = station.computeDistance(super.destinationLatitude,super.destinationLongitude);
                        if (distance <= minDistance){ // To find the closest station
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
