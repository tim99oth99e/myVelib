package src.ridePlanning;

import src.coreClasses.Station;
import src.enums.*;

import java.util.ArrayList;

/**
 * This class describes ride planning.
 *
 * A ride planning helps users to plan a ride from a starting location to a destination location :
 * Given the starting and destination GPS coordinates the ride planning functionality will identify the optimal start and end stations
 * from/to where the bike should be taken/dropped according to the following criteria:
 * - the start, respectively the end, station, for a ride should be as close as possible to
 * the starting, respectively to the destination, location of the ride.
 * - the start station should have one bike of the desired kind (electrical or mechanical)
 * available for renting
 * - the end station should have at least one free parking slot.
 */
public class RidePlanningNormal {
    /**
     * The latitude of the starting point.
     */
    protected Double startLatitude;
    /**
     * The longitude of the starting point.
     */
    protected Double startLongitude;
    /**
     * The latitude of the destination point.
     */
    protected Double destinationLatitude;
    /**
     * The longitude of the destination point.
     */
    protected Double destinationLongitude;

    /**
     * Instantiates a new ride planning.
     *
     * @param startLatitude        the start latitude
     * @param startLongitude       the start longitude
     * @param destinationLatitude  the destination latitude
     * @param destinationLongitude the destination longitude
     */
    public RidePlanningNormal(Double startLatitude, Double startLongitude, Double destinationLatitude, Double destinationLongitude) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
    }

    /**
     * Helps the user to find the optimal start station.
     *
     * @param stations      the stations
     * @param typeOfBicycle the type of bicycle
     * @return the station as close as possible to the starting location of the ride, where a bicycle of the given type is available.
     */
    public Station findStartStation(ArrayList<Station> stations, TypeOfBicycle typeOfBicycle) {
        Station startStation = null;
        Double minDistance = Double.POSITIVE_INFINITY;
        for (Station station : stations){ // Parsing through the stations
            if (station.getStationStatus() == StationStatus.OnService){ // If the station is on service
                if (station.hasBike(typeOfBicycle)){ // If the station has the chosen type of bicycle
                    Double distance = station.computeDistance(startLatitude,startLongitude);
                    if (distance <= minDistance){ // To find the closest station
                        startStation = station;
                        minDistance = distance;
                    }
                }
            }
        }
        return startStation;
    }

    /**
     * Helps the user to find the optimal destination station.
     *
     * @param stations the stations
     * @return the station as close as possible to the destination location of the ride, where at least one parking slot is free.
     */
    public Station findDestinationStation(ArrayList<Station> stations) {
        Station destinationStation = null;
        Double minDistance = Double.POSITIVE_INFINITY;
        for (Station station : stations){ // Parsing through the stations
            if (station.getStationStatus() == StationStatus.OnService){ // If the station is on service
                if (station.hasFreeParkingSlot()){ // If the station has at least one parking slot
                    Double distance = station.computeDistance(destinationLatitude,destinationLongitude);
                    if (distance <= minDistance){ // To find the closest station
                        destinationStation = station;
                        minDistance = distance;
                    }
                }
            }
        }
        return destinationStation;
    }
}
