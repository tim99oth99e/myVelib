package src.classes;

import org.ini4j.*;
import src.coreClasses.*;

import java.io.File;
import java.util.*;

public class MyVelibSystem {
    public static Record myVelibRecord = new Record();

    public static void main(String[] args) throws Exception {
        // Initialization of the Velib System
        initialization();

        // Start the REPL
        readEvalPrintLoop();

        // Finalization (leaving) of the Velib System
        finalization();
    }

    private static <Ini> void initialization() throws Exception {
        // Code for Loading my_velib.ini
        try{
            Wini ini = new Wini(new File("UI/src/classes/my_velib.ini"));
            String nstations = ini.get("stations", "nstations", String.class);
            String nslots = ini.get("stations", "nslots", String.class);
            String s = ini.get("stations", "s", String.class);
            String nbikes = ini.get("stations", "nbikes", String.class);

            String namesString = ini.get("users", "names", String.class);
            List<String> names = new ArrayList<String>(Arrays.asList(namesString.split(",")));
            String latitudesString = ini.get("users", "latitudes", String.class);
            List<String> latitudes = new ArrayList<String>(Arrays.asList(latitudesString.split(",")));
            String longitudesString = ini.get("users", "longitudes", String.class);
            List<String> longitudes = new ArrayList<String>(Arrays.asList(longitudesString.split(",")));
            String creditCardNumbersString = ini.get("users", "creditCardNumbers", String.class);
            List<String> creditCardNumbers = new ArrayList<String>(Arrays.asList(creditCardNumbersString.split(",")));

            //Code for the myVelib network creation
            ArrayList<String> arguments =  new ArrayList<String>();
            arguments.add(nstations);
            arguments.add(nslots);
            arguments.add(s);
            arguments.add(nbikes);

            // Setting up the stations
            VelibCommand velibCommandsetup = new VelibCommand("setup", arguments);
            velibCommandsetup.eval();

            // Setting up the users
            ArrayList<User> users= new ArrayList<User>();
            for (int i = 0; i < names.size(); i++) {
                User user = new User(names.get(i), Double.parseDouble(latitudes.get(i)),Double.parseDouble(longitudes.get(i)),creditCardNumbers.get(i));
                users.add(user);
                MyVelibSystem.myVelibRecord.addUserIfNotExists(user);
            }

            // Display informations
            System.out.println("Successfully setted up a myVelib network from my_velib.ini.");
            System.out.print("Number of station: " + nstations + "\n");
            System.out.print("Number of parking slot per station: " +  nslots + "\n");
            System.out.print("Side of the area: " +  s + "\n");
            System.out.print("Total number of bicycle in the network: " +  nbikes + "\n");
            System.out.print("Total number of user in the network: " + names.size() + "\n");


        }catch(Exception e){
            // To catch basically any error related to finding the file e.g
            // (The system cannot find the file specified)
            System.err.println(e.getMessage());
        }
    }

    private static void readEvalPrintLoop() throws Exception {
        VelibCommand CMD = new VelibCommand();

        do {
            // Read a command
            CMD = readFromConsole();

            // Eval the command
            String STATUS = CMD.eval();

            // Print result (sysout) or error message (syserr)
//            STATUS.printMessage();
            if (STATUS != "") {
                System.out.println(STATUS);
            }

        }

        while(CMD.isNotExit());
    }

    private static VelibCommand readFromConsole() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                // Read a command String from Console
                System.out.print(">>> ");
                String line = scanner.nextLine();
                // Decode the String to determine the Command class to instantiate
                String[] parts = line.split(" "); // Split the string on spaces
                ArrayList<String> arguments = new ArrayList<String>();
                for (int i=1; i<parts.length; i++){
                    arguments.add(parts[i]);
                }
                VelibCommand velibCommand = new VelibCommand(parts[0],arguments);
                return velibCommand;
            }
        } catch(IllegalStateException e) {
            // System.in has been closed
            System.out.println("System.in was closed; exiting");
            return null;
        }
    }

    private static void finalization() {
        // Last chance for joining the last running threads (if there is !)
        // ....
        // Code for cleaning the system before leaving
        // ....
        System.out.println("Finalization complete.");
    }

}

