package src.coreClasses;
import src.event.*;
import src.stationsSorting.StationSortingPolicy;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static src.enums.EventType.*;

public class Record {
    private HashMap<Integer, User> users;
    private HashMap<Integer, Station> stations;
    private ArrayList<Event> events;

    // constructors

    public Record() {
        this.users = new HashMap<>();
        this.stations = new HashMap<>();
        this.events = new ArrayList<>();
    }

    public Record(HashMap<Integer, User> users, HashMap<Integer, Station> stations, ArrayList<Event> events) {
        this.users = users;
        this.stations = stations;
        this.events = events;
    }

    // custom methods
    public void addUserIfNotExists(User user) {
        if (!users.containsValue(user)) {
            users.put(user.getId(), user);
        }
    }

    /** Adds the station to the stations HashMap if it isn't already added
     *
     */
    public void addStationIfNotExists(Station station) {
        if (!stations.containsValue(station)) {
            stations.put(station.getId(), station);
        }
    }

    public void addEventIfNotExists(Event event) {
        if (!events.contains(event)) {

            // check if the station is registered
            this.addStationIfNotExists(event.getStation());

            if (event instanceof RideEvent) {
                // check if the user is registered
                this.addUserIfNotExists( ((RideEvent) event).getUser() );
            }

            events.add(event);
        }
    }


    /**
     * Prints statistics about a user
     */
    public void printUserStatistics(User user) {
        int numberOfRides = user.getNumberOfRides();
        int totalRentTimeInMinutes = user.getTotalRentTimeInMinutes();
        double totalCharges =user.getTotalCharges();
        double timeCredit = user.getTimeCreditBalance();

        // change the way it is displayed
        System.out.println(user.getName() + " statistics : \n\t" +
                            "- number of rides : " + numberOfRides + ",\n\t" +
                            "- time spent on a bike : " + totalRentTimeInMinutes/60 + " hour(s) and "+ totalRentTimeInMinutes%60 +" minute(s),\n\t" +
                            "- total charges : " + totalCharges + " \u20AC,\n\t" +
                            "- time-credit balance : " + timeCredit + " minute(s).");
    }

    public void printStationBalance(Station station){
        int numberOfRent = 0;
        int numberOfReturn = 0;
        for (Event event : this.events) {
            if (event.getStation() == station) {
                switch (event.getEventType()) {
                    case RentBicycle:
                        numberOfRent++;
                        break;
                    case ReturnBicycle:
                        numberOfReturn++;
                        break;
                }
            }
        }
        int balance = numberOfReturn - numberOfRent;
        System.out.println("Station balance : " + balance + " (+" + numberOfReturn + " return.s, -" + numberOfRent + " rent.s)");
    }


    public static double computeAvgOccupationRate(Station station, LocalDateTime ts, LocalDateTime te, ArrayList<Event> events) {

        double delta = ts.until(te, ChronoUnit.MINUTES);
        int numberOfParkingSlots = station.getNumberOfParkingSlots();

        // is there is no time or there are no parking slots
        if (delta == 0 || numberOfParkingSlots == 0){
            return 0;
        }

        int stationOccupationInMinutes = 0;
        LocalDateTime lastEventDateTime = ts;

        // from the creation of the station, rebuild the different states of the station
        // those variables represent the state of the station
        int nbOccupiedSlots = station.getInitialNumberOfBikes(); // we initialize it full of bikes

        for (Event event : events) {
            if (!event.getEventTime().isAfter(te) && event.getStation() == station) {
                // if the event happens between the two time limits
                if (!event.getEventTime().isBefore(ts)) {
                    // add time to station occupation accordingly
                    int minutesSpentSinceLastEvent = (int) lastEventDateTime.until(event.getEventTime(), ChronoUnit.MINUTES);
                    stationOccupationInMinutes += minutesSpentSinceLastEvent * nbOccupiedSlots;
                }
                // if the event happens after the end time
                else if (event.getEventTime().isAfter(te)) {
                    // exit the for loop
                    break;
                }

                // update state
                switch (event.getEventType()) { // put this in a function
                    // we suppose that all those events have been controlled (a station is not online 2 times)
                    case RentBicycle:
                    case SlotTurnsOutOfOrder:
                        nbOccupiedSlots -= 1;
                        break;
                    case ReturnBicycle:
                    case SlotRepairedToOccupied:
                        nbOccupiedSlots += 1;
                        break;
                    case SlotRepairedToFree:
                        ;
                        break;
                    // deal with Station turning offline
                }
                // update last event dateTime
                lastEventDateTime = event.getEventTime();
            }
        }

        // add the time between the last event and the stop time
        // add the final occupation
        int minutesSpentSinceLastEvent = (int) lastEventDateTime.until(te, ChronoUnit.MINUTES);
        stationOccupationInMinutes += minutesSpentSinceLastEvent * nbOccupiedSlots;

        // compute the complete rate
        double avgOccupationRate = stationOccupationInMinutes / (delta * numberOfParkingSlots);
        return avgOccupationRate;
    }

    public Map<Integer, Double> getSortedStations(StationSortingPolicy sortingPolicy) {
        return sortingPolicy.sortStations(this.events, this.stations);
    }


    public Map<Integer, Integer>  computeStationsOrder() {
        // < station id, number of operations >
        Map<Integer, Integer> numberOfOperations = new LinkedHashMap<>();
        // fill the map with all the stations
        for (Integer stationId: this.stations.keySet()) {
            numberOfOperations.put(stationId, 0);
        }
        // add all operations to this map
        for (Event e : events) {
            // if the event is a rent or a park
            if (e.getEventType() == RentBicycle || e.getEventType() == ReturnBicycle) {
                int stationId = e.getStation().getId();
                numberOfOperations.put(stationId, numberOfOperations.get(stationId) + 1);
            }
        }

        // convert the map to list
        List<Map.Entry<Integer, Integer>> stationsOperationsList = new ArrayList<>(numberOfOperations.entrySet());
        // sort by values
        stationsOperationsList.sort(Map.Entry.comparingByValue());

        // convert back to map
        Map<Integer, Integer> sortedStations = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : stationsOperationsList) {
            sortedStations.put(entry.getKey(), entry.getValue());
        }

        return sortedStations;
    }


    @Override
    public String toString() {
        return "Record containing :\n" +
                "\t " + users.size() + " users,\n" +
                "\t " + stations.size() + " stations,\n" +
                "\t " + events.size() + " events.";
    }

    // getters & setters

    public HashMap<Integer, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<Integer, User> users) {
        this.users = users;
    }

    public HashMap<Integer, Station> getStations() {
        return stations;
    }

    public void setStations(HashMap<Integer, Station> stations) {
        this.stations = stations;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
