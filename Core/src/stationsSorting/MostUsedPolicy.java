package src.stationsSorting;

import src.coreClasses.Station;
import src.event.Event;

import java.util.*;

import static src.enums.EventType.RentBicycle;
import static src.enums.EventType.ReturnBicycle;

/**
 * This class describes a sorting policy for a stations hashmap, by most used (renting and returning bicycle).
 */
public class MostUsedPolicy extends StationSortingPolicy {

    /**
     * Gets a map of stations and their number of bicycle rent + returns.
     *
     * @param events   the events
     * @param stations the stations
     * @return the stations number of operations map
     */
    public Map<Integer, Double> getStationsOperations(ArrayList<Event> events, HashMap<Integer, Station> stations) {
        // < station id, number of operations >
        Map<Integer, Double> nbOfOperations = new LinkedHashMap<>();
        // fill the map with all the stations
        for (Integer stationId: stations.keySet()) {
            nbOfOperations.put(stationId, 0.0);
        }
        // add all operations to this map
        for (Event e : events) {
            // if the event is a rent or a park
            if (e.getEventType() == RentBicycle || e.getEventType() == ReturnBicycle) {
                int stationId = e.getStation().getId();
                nbOfOperations.put(stationId, nbOfOperations.get(stationId) + 1);
            }
        }
        return nbOfOperations;
    };

    public Map<Integer, Double> sortStations(ArrayList<Event> events, HashMap<Integer, Station> stations) {
        // get values based on which we sort the stations
        Map<Integer, Double> stationsScore = getStationsOperations(events, stations);
        return this.sortHashMap(stationsScore, true);
    };

}
