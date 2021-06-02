package src.stationsSorting;

import src.coreClasses.Record;
import src.coreClasses.Station;
import src.event.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Least occupied policy.
 */
public class LeastOccupiedPolicy extends StationSortingPolicy {
    /**
     * Gets stations average occupation rate map.
     *
     * @param events   the events
     * @param stations the stations
     * @return the stations average occupation rate
     */
    public Map<Integer, Double> getStationsAvgOccupationRate(ArrayList<Event> events, HashMap<Integer, Station> stations) {
        // < station id, number of operations >
        Map<Integer, Double> avgOccupationRates = new LinkedHashMap<>();

        // get first and last dates
        LocalDateTime startDateTime = events.get(0).getEventTime(); // what about before the first event ?
        LocalDateTime endDateTime = events.get(events.size() - 1).getEventTime();

        // compute each average occupation rate
        for (Station station : stations.values()) {
            // compute average usage
            double avgUsage = Record.computeAvgOccupationRate(station, startDateTime, endDateTime, events);
            avgOccupationRates.put(station.getId(), avgUsage);
        }

        return avgOccupationRates;
    }

    public Map<Integer, Double> sortStations(ArrayList<Event> events, HashMap<Integer, Station> stations) {
        // get values based on which we sort the stations
        Map<Integer, Double> stationsScore = getStationsAvgOccupationRate(events, stations);
        return this.sortHashMap(stationsScore, false);
    };

    @Override
    public String toString() {
        return "smallest average occupation rate";
    }
}
