package src.coreClasses;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

public class Record {
    private HashMap<Integer, User> users;
    private HashMap<Integer, Station> stations;
    private HashMap<Integer, Ride> rides;

    // constructors

    public Record() {
        this.users = new HashMap<>();
        this.stations = new HashMap<>();
        this.rides = new HashMap<>();
    }

    public Record(HashMap<Integer, User> users, HashMap<Integer, Station> stations, HashMap<Integer, Ride> rides) {
        this.users = users;
        this.stations = stations;
        this.rides = rides;
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

    public void addRideIfNotExists(Ride ride) {
        if (!rides.containsValue(ride)) {
            // check if the user is registered
            this.addUserIfNotExists(ride.getUser());
            // same with the stations
            this.addStationIfNotExists(ride.getRentStation());
            this.addStationIfNotExists(ride.getReturnStation());
            rides.put(ride.getId(), ride);
        }
    }

    public void printUserStatistics(User user) {
        int numberOfRides = 0;
        int totalRentTimeInMinutes = 0;
        // time credit
        for (Ride ride : this.rides.values()) {
            if (ride.getUser() == user) {
                numberOfRides ++;
                totalRentTimeInMinutes += ride.getDurationInMinutes();
            }
        }
        double totalCharges =user.getTotalCharges();
        double timeCredit = user.getTimeCreditBalance();

        // change the way it is displayed
        System.out.println(user.getName() + " statistics : \n\t" +
                            "- number of rides : " + numberOfRides + ",\n\t" +
                            "- time spent on a bike : " + (int) totalRentTimeInMinutes/60 + " hour(s) and "+ totalRentTimeInMinutes%60 +" minute(s),\n\t" +
                            "- total charges : " + totalCharges + " \u20AC,\n\t" +
                            "- time-credit balance : " + timeCredit + " minute(s).");
    }

    public void printStationBalance(Station station){
        int numberOfRent = 0;
        int numberOfReturn = 0;
        for (Ride ride : this.rides.values()) {
            if (ride.getRentStation() == station) {
                numberOfRent++;
            } else if (ride.getReturnStation() == station) {
                numberOfReturn++;
            }
        }
        int balance = numberOfReturn - numberOfRent;
        System.out.println("Station balance : " + balance + " (+"+ numberOfRent + " rent.s, -" + numberOfReturn + " return.s)");
    }

    public double computeAvgOccupationRate(Station station, LocalDateTime ts, LocalDateTime te) {
        double delta = ts.until(te, ChronoUnit.MINUTES);
        int numberOfParkingSlots = station.getNumberOfParkingSlots();

        // is there is no time or there are no parking slots
        if (delta == 0 || numberOfParkingSlots == 0){
            return 0;
        }

        int stationOccupation = 0;
        // for each ride in the system, (could be changed to for each ride between te and ts)
        for (Ride ride : this.rides.values()) {
            if (ride.getRentStation()==station) {
                // if the ride happened after the left window bound
                if (ride.getRentDateTime().isAfter(ts)) {
                    stationOccupation += ts.until(ride.getRentDateTime(), ChronoUnit.MINUTES);
                }
            }
            // not an else if because a ride can start and stop at the same place
            if (ride.getReturnStation()==station) {
                // te == returnDate, occupation = 0
                if (ride.getReturnDateTime().isBefore(te)) {
                    stationOccupation += ride.getReturnDateTime().until(te, ChronoUnit.MINUTES);
                }
            }
        }
        double avgOccupationRate = stationOccupation / (delta * numberOfParkingSlots);
        return avgOccupationRate;
    }

    @Override
    public String toString() {
        return "Record containing :\n" +
                "\t " + users.size() + " users,\n" +
                "\t " + stations.size() + " stations,\n" +
                "\t " + rides.size() + " rides.";
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

    public HashMap<Integer, Ride> getRides() {
        return rides;
    }

    public void setRides(HashMap<Integer, Ride> rides) {
        this.rides = rides;
    }
}
