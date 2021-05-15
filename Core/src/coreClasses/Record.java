package src.coreClasses;

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

    public void getUserStatistics(User user) {
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
                            numberOfRides + " ride(s),\n\t" +
                            totalRentTimeInMinutes + " minute(s) spent on a bike,\n\t" +
                            "total charges : " + totalCharges + " euros,\n\t" +
                            timeCredit + " min of time-credit.");
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
