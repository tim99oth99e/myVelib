package src.stationsSorting;

import src.coreClasses.Station;
import src.event.Event;

import java.util.*;

import static src.enums.EventType.RentBicycle;
import static src.enums.EventType.ReturnBicycle;

public class MostUsedPolicy extends StationSortingPolicy {

    public Map<Integer, Double> getStationsScore(ArrayList<Event> events, HashMap<Integer, Station> stations) {
        // < station id, number of operations >
        Map<Integer, Double> avgOccupationRates = new LinkedHashMap<>();
        // fill the map with all the stations
        for (Integer stationId: stations.keySet()) {
            avgOccupationRates.put(stationId, 0.0);
        }
        // add all operations to this map
        for (Event e : events) {
            // if the event is a rent or a park
            if (e.getEventType() == RentBicycle || e.getEventType() == ReturnBicycle) {
                int stationId = e.getStation().getId();
                avgOccupationRates.put(stationId, avgOccupationRates.get(stationId) + 1);
            }
        }
        return avgOccupationRates;
    };

    // method that does everything
    public Map<Integer, Double> sortStations(ArrayList<Event> events, HashMap<Integer, Station> stations) {
        // get values based on which we sort the stations
        Map<Integer, Double> stationsScore = getStationsScore(events, stations);
        return this.sortHashMap(stationsScore, true);
    };

}
