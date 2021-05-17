package src.ridePlanning;

import src.coreClasses.Station;
import src.enums.*;

import java.util.ArrayList;

public class RidePlanningPreferPlusStations extends RidePlanningNormal {

    public RidePlanningPreferPlusStations(Double startLatitude, Double startLongitude, Double destinationLatitude, Double destinationLongitude) {
        super(startLatitude,startLongitude,destinationLatitude,destinationLongitude);
    }

    @Override
    public Station findDestinationStation(ArrayList<Station> stations) {
        Station destinationStation = null;
        Double minDistance = Double.POSITIVE_INFINITY;
        Station destinationStationPlus = null;
        Double minDistancePlus = Double.POSITIVE_INFINITY;
        for (Station station : stations) {
            if (station.getStationStatus() == StationStatus.OnService) {
                if (station.hasFreeParkingSlot()) {
                    if (station.getTypeOfStation() == TypeOfStation.Standard) {
                        Double distance = station.computeDistance(super.destinationLatitude, super.destinationLongitude);
                        if (distance <= minDistance) {
                            destinationStation = station;
                            minDistance = distance;
                        }
                    }
                    if (station.getTypeOfStation() == TypeOfStation.Plus) {
                        Double distance = station.computeDistance(super.destinationLatitude, super.destinationLongitude);
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
