package src.CLUIclasses;
import src.coreClasses.*;
import src.enums.*;
import src.event.Event;
import src.registrationCard.VlibreRegistrationCard;
import src.registrationCard.VmaxRegistrationCard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.*;

/**
 * This class handles commands from CLUI
 */
public class VelibCommand {
    private String commandName;
    private ArrayList<String> arguments;

    /**
     * Instantiates a new Velib command.
     *
     * @param commandName the command name
     * @param arguments   the arguments
     */
// constructors
    public VelibCommand(String commandName, ArrayList<String> arguments) {
        this.commandName = commandName;
        this.arguments = arguments;
    }

    /**
     * Instantiates a new Velib command.
     */
    public VelibCommand() {
        this.commandName = null;
        this.arguments = null;
    }

    /**
     * Test if the user wants to exit the CLUI
     *
     * @return true if the user does not want to exit the CLUI, False otherwise
     */
// custom methods
    public boolean isNotExit(){
        return !commandName.equals("exit");
    }

    /**
     * Get random type of bicycle.
     *
     * @return the random type of bicycle
     */
    public TypeOfBicycle getRandomBicycleType(){
        if (Math.random() < 0.5){
            return TypeOfBicycle.Mechanical;
        }
        else {
            return TypeOfBicycle.Electrical;
        }
    }

    /**
     *  Setup a myVelib network.
     *
     * @param numberOfStation        the number of station
     * @param numberOfSlotPerStation the number of slot per station
     * @param sideLength             the side length
     * @param numberOfBike           the number of bike
     */
    public void setup(Integer numberOfStation,Integer  numberOfSlotPerStation,Double sideLength,Integer numberOfBike){
        ArrayList<ParkingSlot> parkingSlots= new ArrayList<>();
        // Place stations uniformly on a square grid whose the side is of length sideLength
        for (int i = 0; i < numberOfStation; i++) {
            Station station = new Station(Math.random()*sideLength,Math.random()*sideLength,StationStatus.OnService, TypeOfStation.Standard);
//            stations.add(station);
            MyVelibSystem.myVelibRecord.addStationIfNotExists(station);
            // Create numberOfSlotPerStation free slots per stations
            for (int j = 0; j < numberOfSlotPerStation; j++) {
                ParkingSlot parkingSlot = new ParkingSlot(ParkingSlotStatus.Free, null);
                station.addParkingSlot(parkingSlot);
                parkingSlots.add(parkingSlot);
            }
        }

        // Generate numberOfBike randomly distributed index between 0 and numberOfStation*numberOfSlotPerStation
        List<Integer> randomParkingSlot = new ArrayList<>();
        for (int i = 0; i < numberOfStation*numberOfSlotPerStation; i++) {
            randomParkingSlot.add(i);
        }
        Collections.shuffle(randomParkingSlot);
        for (int i = 0; i < numberOfBike; i++) {
            parkingSlots.get(randomParkingSlot.get(i)).setParkingSlotStatus(ParkingSlotStatus.Occupied);
            parkingSlots.get(randomParkingSlot.get(i)).setBicycle( new Bicycle(getRandomBicycleType()));
        }
    }

    /**
     * Evaluate the command the user entered in the CLUI and act accordingly.
     *
     * @return the result string
     * @throws Exception the exception
     */
// main method
    // executes the given command and returns a STATUS message
    public String eval() throws Exception {
        switch (commandName.toLowerCase()) {
            case "help":
                return " A command consists of the command-name followed by a blank-separated list of (string) arguments:\n" +
                        "command-name <arg1> <arg2> ... <argN> \n \n " + "Commands available : \n \n"

                        + "setup : " +
                        "\n \t to create a myVelib network consisting of 10 stations each of which has 10 parking slots and such that stations\n" +
                        "are arranged on a square grid whose of side 4km and initially populated with a 75%\n" +
                        "bikes randomly distributed over the 10 stations\n \n"

                        + "setup  <nstations> <nslots> <s> <nbikes> :" +
                        "\n  \t to create a myVelib network consisting of nstations stations each of which has nslots\n" +
                        "parking slots and such that stations are arranged in as uniform as possible manner\n" +
                        "over an squared area of side s. Furthermore the network should\n" +
                        "be initially populated with a nbikes bikes randomly distributed over the nstations stations\n \n"

                        + "addUser <userName, latitude, longitude, creditCardNumber, cardType> :" +
                        "\n \t to add a user with name\n" +
                        "userName and card cardType (i.e. ‘‘none’’ if the user has no card) to a myVelib network velibnetworkName \n \n"

                        + "offline <stationID> :" +
                        "\n \t to put offline the station stationID\n" +
                        "of the myVelib network \n \n"

                        + "online <stationID> :" +
                        "\n \t to put online the station stationID of\n" +
                        "the myVelib network \n \n"

                        + "rentBike <userID, stationID, typeOfBicycle> :" +
                        "\n \t to let the user userID renting a bike of type typeOfBicycle (mechanical or electrical) from station\n" +
                        "stationID (if no bikes are available should behave accordingly)\n \n"

                        + "returnBike <userID, stationID, timeInMinute> :" +
                        "\n \t to let the user userID returning a bike\n" +
                        "to station stationID at a given instant of time timeInMinute (if no parking bay is available\n" +
                        "should behave accordingly). This command should display the cost of the rent \n \n"

                        + "displayStation <stationID> :" +
                        "\n \t to display the statistics (as of\n" +
                        "Section 2.4) of station stationID of a myVelib network velibnetwork. \n \n"

                        + "displayUser <userID> :" +
                        "\n \t to display the statistics (as of Section 2.4) of user userID of the myVelib network.\n \n"

                        + "sortStation <sortpolicy> :" +
                        "\n \t to display the stations in increasing order w.r.t. to the sorting policy (as of Section 2.4) of user sortpolicy. \n \n"

                        + "display :" +
                        "\n \t  to display the entire status (stations, parking bays,\n" +
                        "users) of an a myVelib network velibnetworkName. \n \n"

                        + "exit :" +
                        "\n \t to exit the system. \n \n"

                        + "runTest <testScenarioFile.txt> :" +
                        "\n \t to run the test scenario described in testScenarioFile.txt\n \n"
                        ;

            case "":
                return "";


            case "runtest":
                if (arguments.size() == 1){
                    try {
                        // Get arguments
                        String testScenario = arguments.get(0);

                        File file = new File("./UI/src/test/"+testScenario);

                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String st;

                        while ((st = br.readLine()) != null) {
                            //Catch the arguments
                            String[] parts = st.split(" ");
                            String command = parts[0];
                            ArrayList<String> arguments = new ArrayList<String>();
                            for (int i=1; i<parts.length; i++){
                                arguments.add(parts[i]);
                            }
                            // Executing the command
                            VelibCommand velibCommandSetup = new VelibCommand(command, arguments);
                            System.out.println(velibCommandSetup.eval());;
                        }
                        return "Test scenario successfully ran";
                    }
                    catch (Exception e){
                        return "Wrong argument entered. Type help to display help.";
                    }
                }
                else {
                    return "Unknown command entered. Type help to display help.";
                }


            case "adduser":
                if (arguments.size() == 5 || arguments.size() == 6) {
                    try {
                        User userToAdd;
                        String name;
                        int shift;

                        if (arguments.size() == 5) {
                            name = arguments.get(0);
                            shift = 0;
                        } else { // 6 arguments = first name + last name
                            name = arguments.get(0) + " " + arguments.get(1);
                            shift = 1;
                        }

                        // get other parameters
                        double latitude = Double.parseDouble(arguments.get(1 + shift));
                        double longitude = Double.parseDouble(arguments.get(2 + shift));
                        String creditCardNumber = arguments.get(3 + shift);
                        // parse card type
                        String registrationCardType = arguments.get(4 + shift);
                        switch (registrationCardType.toLowerCase()) {
                            case "vlibre":
                                userToAdd = new User(name, latitude, longitude, creditCardNumber, new VlibreRegistrationCard());
                                break;
                            case "vmax":
                                userToAdd = new User(name, latitude, longitude, creditCardNumber, new VmaxRegistrationCard());
                                break;
                            default:
                            case "none":
                                userToAdd = new User(name, latitude, longitude, creditCardNumber);
                                break;
                        }
                        // Update the record
                        MyVelibSystem.myVelibRecord.addUserIfNotExists(userToAdd);
                        return "Added user " + userToAdd.getName() + " with id : " + userToAdd.getId();
                    } catch (Exception e) {
                        return "Wrong argument entered. Type help to display help.";
                    }
                } else {
                    return "Unknown command entered. Type help to display help.";
                }

            case "setup":
                if (arguments.size() == 0) {
                    setup(10, 10, 4000.0, 75);
                    return "Successfully setted up the default myVelib network.";
                }
                if (arguments.size() == 4){
                    try {
                        // Get arguments
                        Integer numberOfStation = Integer.parseInt(arguments.get(0));
                        Integer numberOfSlotPerStation = Integer.parseInt(arguments.get(1));
                        Double sideLength = Double.parseDouble(arguments.get(2));
                        Integer numberOfBike = Integer.parseInt(arguments.get(3));

                        // Setup
                        setup(numberOfStation, numberOfSlotPerStation, sideLength, numberOfBike);
                        return "Successfully setted up a myVelib network.";
                    }
                    catch (Exception e){
                        return "Wrong argument entered. Type help to display help.";
                    }
                }
                else {
                    return "Unknown command entered. Type help to display help.";
                }

            case "online":
                if (arguments.size() == 1){
                    try {
                        // Get arguments
                        int stationId = Integer.parseInt(arguments.get(0));
                        if (stationId >= MyVelibSystem.myVelibRecord.getStations().size()){
                            return "Unknown station";
                        }
                        else {
                            // The wanted station
                            Station station = MyVelibSystem.myVelibRecord.getStations().get(stationId);
                            if (station.getStationStatus() == StationStatus.Offline){
                                station.setStationStatus(StationStatus.OnService);
                                // Update the record
                                MyVelibSystem.myVelibRecord.addEventIfNotExists(new Event(LocalDateTime.now(), EventType.StationTurnsOnline,station));
                                return "Station number " + stationId   + " is now online";
                            }
                            else {
                                return "Error: station number " + stationId   + " was already online";
                            }
                        }
                    }
                    catch (Exception e){
                        return "Wrong argument entered. Type help to display help.";
                    }
                }
                else {
                    return "Unknown command entered. Type help to display help.";
                }

            case "offline":
                if (arguments.size() == 1){
                    try {
                        // Get arguments
                        int stationId = Integer.parseInt(arguments.get(0));

                        if (stationId >= MyVelibSystem.myVelibRecord.getStations().size()){
                            return "Unknown station";
                        }
                        else {
                            // The wanted station
                            Station station = MyVelibSystem.myVelibRecord.getStations().get(stationId);
                            if (station.getStationStatus() == StationStatus.OnService){
                                station.setStationStatus(StationStatus.Offline);
                                // Update the record
                                MyVelibSystem.myVelibRecord.addEventIfNotExists(new Event(LocalDateTime.now(), EventType.StationTurnsOffline,station));
                                return "Station number " + stationId   + " is now offline";
                            }
                            else {
                                return "Error: station number " + stationId   + " was already offline";
                            }
                        }
                    }
                    catch (Exception e){
                        return "Wrong argument entered. Type help to display help.";
                    }
                }
                else {
                    return "Unknown command entered. Type help to display help.";
                }

            case "rentbike":
                if (arguments.size() == 3){
                    try {
                        // Get arguments
                        int userId = Integer.parseInt(arguments.get(0));
                        int stationId = Integer.parseInt(arguments.get(1));
                        String type = arguments.get(2);

                        if (userId >= MyVelibSystem.myVelibRecord.getUsers().size()){
                            return "Unknown user";
                        }
                        else if (stationId >= MyVelibSystem.myVelibRecord.getStations().size()){
                            return "Unknown station";
                        }
                        else {
                            // The wanted user
                            User user = MyVelibSystem.myVelibRecord.getUsers().get(userId);
                            // The wanted station
                            Station station = MyVelibSystem.myVelibRecord.getStations().get(stationId);
                            if (type.equals("mechanical") && station.getParkingSlotWithOneBike(TypeOfBicycle.Mechanical) != null && station.getStationStatus() == StationStatus.OnService){
                                // A parking slot with mechanical bicycle
                                ParkingSlot parkingSlot = station.getParkingSlotWithOneBike(TypeOfBicycle.Mechanical);
                                user.rent(parkingSlot,LocalDateTime.now());
                                MyVelibSystem.myVelibRecord.addEventIfNotExists(new Event(LocalDateTime.now(), EventType.RentBicycle,station));
                                return MyVelibSystem.myVelibRecord.getUsers().get(userId).getName()  + " has a " + type + " bicycle.";
                            }
                            else if (type.equals("electrical") && station.getParkingSlotWithOneBike(TypeOfBicycle.Electrical) != null && station.getStationStatus() == StationStatus.OnService) {
                                // A parking slot with electrical bicycle
                                ParkingSlot parkingSlot = station.getParkingSlotWithOneBike(TypeOfBicycle.Electrical);
                                user.rent(parkingSlot,LocalDateTime.now());
                                // Update the record
                                MyVelibSystem.myVelibRecord.addEventIfNotExists(new Event(LocalDateTime.now(), EventType.RentBicycle,station));
                                return MyVelibSystem.myVelibRecord.getUsers().get(userId).getName() + " has a " + type + " bicycle.";
                            }
                            else {
                                return "No bicycle of type " + type + " available at station " + station.getId() + ".";
                            }
                        }
                    }
                    catch (Exception e){
                        return "Wrong argument entered. Type help to display help.";
                    }
                }
                else {
                    return "Unknown command entered. Type help to display help.";
                }

            case "returnbike":
                if (arguments.size() == 3){
                    try {
                        // Get arguments
                        int userId = Integer.parseInt(arguments.get(0));
                        int stationId = Integer.parseInt(arguments.get(1));
                        long timeInMinutes = Long.parseLong(arguments.get(2));

                        if (userId >= MyVelibSystem.myVelibRecord.getUsers().size()){
                            return "Unknown user";
                        }
                        else if (stationId >= MyVelibSystem.myVelibRecord.getStations().size()){
                            return "Unknown station";
                        }
                        else {
                            // The wanted user
                            User user = MyVelibSystem.myVelibRecord.getUsers().get(userId);
                            // The wanted station
                            Station station = MyVelibSystem.myVelibRecord.getStations().get(stationId);
                            if (user.getRentedBicycle() == null){
                                return "Error: this user has no bicycle to park.";
                            }
                            else if (station.hasFreeParkingSlot() && station.getStationStatus() == StationStatus.OnService){
                                // A free parking slot
                                ParkingSlot parkingSlot = station.getFreeParkingSlot();

                                double totalChargeBefore = user.getTotalCharges();
                                user.park(parkingSlot,LocalDateTime.now().plusMinutes(timeInMinutes));
                                double totalChargeAfter = user.getTotalCharges();
                                double cost = totalChargeAfter-totalChargeBefore;
                                // Update the record
                                MyVelibSystem.myVelibRecord.addEventIfNotExists(new Event(LocalDateTime.now(), EventType.ReturnBicycle,station));
                                return MyVelibSystem.myVelibRecord.getUsers().get(userId).getName()  + " has successfully returned his bicycle."
                                        + " Cost of the rent: " + cost + " €.";
                            }
                            else {
                                return "No free parking slot available at station " + station.getId() + ".";
                            }
                        }
                    }
                    catch (Exception e){
                        return "Wrong argument entered. Type help to display help.";
                    }
                }
                else {
                    return "Unknown command entered. Type help to display help.";
                }

            case "displaystation":
                if (arguments.size() == 1) {
                    try {
                        int stationId = Integer.parseInt(arguments.get(0));
                        if (MyVelibSystem.myVelibRecord.getStations().containsKey(stationId)) {
                            Station station = MyVelibSystem.myVelibRecord.getStations().get(stationId);
                            return MyVelibSystem.myVelibRecord.computeStationBalance(station) + "\nAverage occupation rate : " + MyVelibSystem.myVelibRecord.getGlobalAvgOccupationRate(station);
                        } else {
                            return "Station not found";
                        }
                    } catch (Exception e){
                        return "Wrong argument entered. Type help to display help.";
                    }
                } else {
                    return "Unknown command entered. Type help to display help.";
                }

            case "displayuser":
                if (arguments.size() == 1) {
                    try {
                        int userId = Integer.parseInt(arguments.get(0));
                        User user = MyVelibSystem.myVelibRecord.getUsers().get(userId);
                        return MyVelibSystem.myVelibRecord.computeUserStatistics(user);
                    } catch (Exception e){
                        return "Wrong argument entered. Type help to display help.";
                    }
                } else {
                    return "Unknown command entered. Type help to display help.";
                }

            case "display":
                int nbUsers = MyVelibSystem.myVelibRecord.getUsers().size();
                int nbStations = MyVelibSystem.myVelibRecord.getStations().size();
                int nbParkingSlots = 0;
                // count the parking slots
                for (Station s: MyVelibSystem.myVelibRecord.getStations().values()) {
                    nbParkingSlots += s.getParkingSlotHashMap().size();
                }

                return "myVelib network composed of :\n\t- " + nbUsers + " users,\n\t- " + nbStations + " stations,\n\t- " + nbParkingSlots + " parking slots.";

            case "exit":
                return "Exiting the system.";

            default:
                return "Unknown command entered. Type help to display help.";
        }
    }


    // getters and setters

    /**
     * Gets command name.
     *
     * @return the command name
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Sets command name.
     *
     * @param commandName the command name
     */
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    /**
     * Gets arguments.
     *
     * @return the arguments
     */
    public ArrayList<String> getArguments() {
        return arguments;
    }

    /**
     * Sets arguments.
     *
     * @param arguments the arguments
     */
    public void setArguments(ArrayList<String> arguments) {
        this.arguments = arguments;
    }


}
