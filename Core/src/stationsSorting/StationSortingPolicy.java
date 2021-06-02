package src.stationsSorting;

import src.coreClasses.Station;
import src.event.Event;

import java.util.*;

/**
 * This class describes a sorting policy for a stations hashmap.
 */
public abstract class StationSortingPolicy {

    /**
     * Sorts and returns a stations map based on a policy (that is different on each subclass).
     *
     * @param events   the events that happened in this station
     * @param stations the stations to be sorted
     * @return the sorted map of stations
     */
    public abstract Map<Integer, Double> sortStations(ArrayList<Event> events, HashMap<Integer, Station> stations);

    /**
     * Sorts and returns a hash map based on its values.
     * Works by converting the map to a list, sorting the list, and then converting it back to a map.
     *
     * @param stationValues the station values
     * @param reversed      the reversed
     * @return the sorted map
     */
    public Map<Integer, Double> sortHashMap(Map<Integer, Double> stationValues, Boolean reversed) {
        // convert the map to list
        List<Map.Entry<Integer, Double>> stationValuesList = new ArrayList<>(stationValues.entrySet());
        // sort by values
        stationValuesList.sort(Map.Entry.comparingByValue());
        if (reversed) {
            Collections.reverse(stationValuesList);
        }

        // convert back to map
        Map<Integer, Double> sortedStations = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> entry : stationValuesList) {
            sortedStations.put(entry.getKey(), entry.getValue());
        }
        return sortedStations;
    }


}
