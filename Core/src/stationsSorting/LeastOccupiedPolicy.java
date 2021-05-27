package src.stationsSorting;

import src.coreClasses.Record;
import src.coreClasses.Station;
import src.event.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LeastOccupiedPolicy extends StationSortingPolicy {
    public Map<Integer, Double> getStationsAvgOccupationRate(ArrayList<Event> events, HashMap<Integer, Station> stations) {
        // < station id, number of operations >
        Map<Integer, Double> numberOfOperations = new LinkedHashMap<>();

        // get first and last dates
        LocalDateTime startDateTime = events.get(0).getEventTime(); // what about before the first event ?
        LocalDateTime endDateTime = events.get(events.size() - 1).getEventTime();

        // compute each average occupation rate
        for (Station station : stations.values()) {
            // compute average usage
            double avgUsage = Record.computeAvgOccupationRate(station, startDateTime, endDateTime, events);
            numberOfOperations.put(station.getId(), avgUsage);
        }

        return numberOfOperations;
    }

    public Map<Integer, Double> sortStations(ArrayList<Event> events, HashMap<Integer, Station> stations) {
        // get values based on which we sort the stations
        Map<Integer, Double> stationsScore = getStationsAvgOccupationRate(events, stations);
        return this.sortHashMap(stationsScore, false);
    };
}
