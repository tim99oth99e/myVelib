package src.coreClasses;
import src.event.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

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


    public double computeAvgOccupationRate(Station station, LocalDateTime ts, LocalDateTime te) {

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
        int nbOccupiedSlots = station.getNumberOfParkingSlots(); // we initialize it full of bikes
        for (Event event : events) {
            if (!event.getEventTime().isAfter(te) && event.getStation() == station) {
                // if the event happens between the two time limits
                if (!event.getEventTime().isBefore(ts)) {
                    // add time to station occupation accordingly
                    int minutesSpentSinceLastEvent = (int) lastEventDateTime.until(event.getEventTime(), ChronoUnit.MINUTES);
                    stationOccupationInMinutes += minutesSpentSinceLastEvent * nbOccupiedSlots;
                }
                else if (event.getEventTime().isAfter(te)) {
                    // we passed the end of time
                    // add the final occupation
                    int minutesSpentSinceLastEvent = (int) lastEventDateTime.until(te, ChronoUnit.MINUTES);
                    stationOccupationInMinutes += minutesSpentSinceLastEvent * nbOccupiedSlots;
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

        double avgOccupationRate = stationOccupationInMinutes / (delta * numberOfParkingSlots);
        return avgOccupationRate;
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
