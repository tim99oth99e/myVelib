package src.coreClasses;
import src.event.*;
import src.stationsSorting.StationSortingPolicy;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static src.enums.EventType.*;

/**
 * This class describes a record of users, stations and events for a velib network.
 */
public class Record {
    /**
     * hash map of { user id : user object }
     */
    private HashMap<Integer, User> users;
    /**
     * hash map of { station id : station object }
     */
    private HashMap<Integer, Station> stations;
    /**
     * Array list of events (eg. rent a bike, return a bike, ...)
     */
    private ArrayList<Event> events;

    // constructors

    /**
     * Instantiates a new Record with empty lists.
     */
    public Record() {
        this.users = new HashMap<>();
        this.stations = new HashMap<>();
        this.events = new ArrayList<>();
    }

    /**
     * Instantiates a new Record with users, stations and events.
     *
     * @param users    the users
     * @param stations the stations
     * @param events   the events
     */
    public Record(HashMap<Integer, User> users, HashMap<Integer, Station> stations, ArrayList<Event> events) {
        this.users = users;
        this.stations = stations;
        this.events = events;
    }

    /**
     * Add user to the users HashMap if it does not exists yet.
     *
     * @param user the user to add
     */
// custom methods
    public void addUserIfNotExists(User user) {
        if (!users.containsValue(user)) {
            users.put(user.getId(), user);
        }
    }

    /**
     * Adds the station to the stations HashMap if it isn't already added.
     *
     * @param station the station
     */
    public void addStationIfNotExists(Station station) {
        if (!stations.containsValue(station)) {
            stations.put(station.getId(), station);
        }
    }

    /**
     * Add an event to the list if it isn't already added.
     *
     * @param event the event
     */
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
     * Prints statistics about a user.
     *
     * @param user the user
     */
    public String computeUserStatistics(User user) {
        int numberOfRides = user.getNumberOfRides();
        int totalRentTimeInMinutes = user.getTotalRentTimeInMinutes();
        double totalCharges =user.getTotalCharges();
        double timeCredit = user.getTimeCreditBalance();

        // change the way it is displayed
        return user.getName() + " statistics : \n\t" +
                            "- number of rides : " + numberOfRides + ",\n\t" +
                            "- time spent on a bike : " + totalRentTimeInMinutes/60 + " hour(s) and "+ totalRentTimeInMinutes%60 +" minute(s),\n\t" +
                            "- total charges : " + totalCharges + " \u20AC,\n\t" +
                            "- time-credit balance : " + timeCredit + " minute(s).";
    }

    /**
     * Computes a station's current balance of bikes.
     *
     * @param station the station
     */
    public String computeStationBalance(Station station){
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
        return "Station balance : " + balance + " (+" + numberOfReturn + " return.s, -" + numberOfRent + " rent.s)";
    }

    /**
     * Computes the average occupation rate on the max time period of a station.
     *
     * @param station the station
     * @return the average occupation rate of that station
     */
    public double getGlobalAvgOccupationRate(Station station){
        if (this.events.isEmpty()) {
            return 0.0;
        }
        LocalDateTime startDateTime = this.events.get(0).getEventTime(); // what about before the first event ?
        LocalDateTime endDateTime = this.events.get(this.events.size() - 1).getEventTime();
        return Record.computeAvgOccupationRate(station, startDateTime, endDateTime, this.events);
    }

    /**
     * Computes and returns the average occupation rate of a station between two dates.
     *
     * @param station the station
     * @param ts      the start datetime
     * @param te      the end datetime
     * @param events  the events
     * @return the average occupation rate
     */
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

    /**
     * Gets a hashmap of stations sorted according to a specific policy.
     *
     * @param sortingPolicy the sorting policy
     * @return the sorted stations
     */
    public Map<Integer, Double> getSortedStations(StationSortingPolicy sortingPolicy) {
        return sortingPolicy.sortStations(this.events, this.stations);
    }


    @Override
    public String toString() {
        return "Record containing :\n" +
                "\t " + users.size() + " users,\n" +
                "\t " + stations.size() + " stations,\n" +
                "\t " + events.size() + " events.";
    }


    // getters & setters

    /**
     * Gets the users hashmap.
     *
     * @return the users hashmap
     */
    public HashMap<Integer, User> getUsers() {
        return users;
    }

    /**
     * Sets the users hashmap.
     *
     * @param users the users hashmap
     */
    public void setUsers(HashMap<Integer, User> users) {
        this.users = users;
    }

    /**
     * Gets the stations hashmap.
     *
     * @return the stations hashmap
     */
    public HashMap<Integer, Station> getStations() {
        return stations;
    }

    /**
     * Sets stations hashmap.
     *
     * @param stations the stations hashmap
     */
    public void setStations(HashMap<Integer, Station> stations) {
        this.stations = stations;
    }

    /**
     * Gets the events list.
     *
     * @return the events Arraylist
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Sets the events list.
     *
     * @param events the events Arraylist
     */
    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
