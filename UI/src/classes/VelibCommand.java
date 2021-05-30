package src.classes;
import src.coreClasses.ParkingSlot;
import src.coreClasses.Station;
import src.coreClasses.User;
import src.enums.EventType;
import src.enums.StationStatus;
import src.enums.TypeOfBicycle;
import src.event.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class VelibCommand {
    private String commandName;
    private ArrayList<String> arguments;

    // constructors
    public VelibCommand(String commandName, ArrayList<String> arguments) {
        this.commandName = commandName;
        this.arguments = arguments;
    }

    public VelibCommand() {
        this.commandName = null;
        this.arguments = null;
    }

    // custom methods
    public boolean isNotExit(){
        return !commandName.equals("exit");
    }

    // main method
    public String eval() throws Exception {
        switch (commandName) {
            case "help":
                return " A command consists of the command-name followed by a blank-separated list of (string) arguments:\n" +
                        "command-name <arg1> <arg2> ... <argN> \n \n " + "Command available : \n \n"

                        + "setup <velibnetworkName> : " +
                        "\n \t to create a myVelib network with given name and\n" +
                        "consisting of 10 stations each of which has 10 parking slots and such that stations\n" +
                        "are arranged on a square grid whose of side 4km and initially populated with a 75%\n" +
                        "bikes randomly distributed over the 10 stations\n \n"

                        + "setup <name> <nstations> <nslots> <s> <nbikes> :" +
                        "\n  \t to create a myVelib network with given name and consisting of nstations stations each of which has nslots\n" +
                        "parking slots and such that stations are arranged in as uniform as possible manner\n" +
                        "over an squared area of side s. Furthermore the network should\n" +
                        "be initially populated with a nbikes bikes randomly distributed over the nstations stations\n \n"

                        + "addUser <userName,cardType, velibnetworkName> :" +
                        "\n \t to add a user with name\n" +
                        "userName and card cardType (i.e. ‘‘none’’ if the user has no card) to a myVelib network velibnetworkName \n \n"

                        + "offline <velibnetworkName, stationID> :" +
                        "\n \t to put offline the station stationID\n" +
                        "of the myVelib network velibnetworkName \n \n"

                        + "online <velibnetworkName, stationID> :" +
                        "\n \t to put online the station stationID of\n" +
                        "the myVelib network velibnetworkName\n \n"

                        + "rentBike <userID, stationID, typeOfBicycle> :" +
                        "\n \t to let the user userID renting a bike of type typeOfBicycle (mechanical or electrical) from station\n" +
                        "stationID (if no bikes are available should behave accordingly)\n \n"

                        + "returnBike <userID, stationID, timeInMinute> :" +
                        "\n \t to let the user userID returning a bike\n" +
                        "to station stationID at a given instant of time timeInMinute (if no parking bay is available\n" +
                        "should behave accordingly). This command should display the cost of the rent \n \n"

                        + "displayStation<velibnetworkName, stationID> :" +
                        "\n \t to display the statistics (as of\n" +
                        "Section 2.4) of station stationID of a myVelib network velibnetwork. \n \n"

                        + "displayUser<velibnetworkName, userID> :" +
                        "\n \t to display the statistics (as of Section 2.4) of user userID of a myVelib network velibnetwork.\n \n"

                        + "sortStation<velibnetworkName, sortpolicy> :" +
                        "\n \t to display the stations in increasing order w.r.t. to the sorting policy (as of Section 2.4) of user sortpolicy. \n \n"

                        + "display <velibnetworkName> :" +
                        "\n \t  to display the entire status (stations, parking bays,\n" +
                        "users) of an a myVelib network velibnetworkName. \n \n";

            case "":
                return "";

            case "setup":
                return "Setup command completed.";

            case "addUser":
                if (arguments.size() == 1){
                    // get arguments
                    String name = arguments.get(0);
                    // add method to parse card type
                    // velibNetwork
                    // create the user
                    User userToAdd = new User(name, 20.0, 20.0,"1234123412341234");
                    MyVelibSystem.myVelibRecord.addUserIfNotExists(userToAdd);
                    return "Added user " + userToAdd.getName() + ", id = " + userToAdd.getId();
                }
                else {
                    return "Unknown command entered. Type help to display help.";
                }

            case "rentBike":
                if (arguments.size() == 3){
                    try {
                        // Get arguments
                        Integer userId = Integer.parseInt(arguments.get(0));
                        Integer stationId = Integer.parseInt(arguments.get(1));
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

            case "parkBike":
                if (arguments.size() == 3){
                    try {
                        // Get arguments
                        Integer userId = Integer.parseInt(arguments.get(0));
                        Integer stationId = Integer.parseInt(arguments.get(1));
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

                                Double totalChargeBefore = user.getTotalCharges();
                                user.park(parkingSlot,LocalDateTime.now().plusMinutes(timeInMinutes));
                                Double totalChargeAfter = user.getTotalCharges();
                                Double cost = totalChargeAfter-totalChargeBefore;
                                MyVelibSystem.myVelibRecord.addEventIfNotExists(new Event(LocalDateTime.now(), EventType.ReturnBicycle,station));
                                return MyVelibSystem.myVelibRecord.getUsers().get(userId).getName()  + " has successfully returned his bicycle."
                                        + " Cost of the rent: " + cost + " $.";
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

            case "offline":
                return "Offline command entered.";


            case "exit":
                return "Exiting the system.";

            default:
                return "Unknown command entered. Type help to display help.";
        }
    }


    // getters and setters

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public ArrayList<String> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayList<String> arguments) {
        this.arguments = arguments;
    }


}
