package src.stationsSorting;

import src.coreClasses.Station;
import src.event.Event;

import java.util.*;

public abstract class StationSortingPolicy {

    public abstract Map<Integer, Double> getStationsScore(ArrayList<Event> events, HashMap<Integer, Station> stations);

    public abstract Map<Integer, Double> sortStations(ArrayList<Event> events, HashMap<Integer, Station> stations);

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
