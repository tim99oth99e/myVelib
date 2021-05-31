package src.classes;

import src.coreClasses.*;
import src.enums.ParkingSlotStatus;
import src.enums.StationStatus;
import src.enums.TypeOfBicycle;
import src.enums.TypeOfStation;
import src.registrationCard.NoRegistrationCard;
import src.registrationCard.VlibreRegistrationCard;
import src.registrationCard.VmaxRegistrationCard;

import java.util.ArrayList;
import java.util.Scanner;

public class MyVelibSystem {
    static Record myVelibRecord = new Record();

    public static void main(String[] args) throws Exception {
        // Initialization of the Velib System
        initialization();

        // Start the REPL
        readEvalPrintLoop();

        // Finalization (leaving) of the Velib System
        finalization();
    }

    private static void initialization() throws Exception {
        // Code for Loading my_velib.ini  and the Systeme creation !
        // Create bicycles of two different type
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
        Bicycle electricalBicycle = new Bicycle(TypeOfBicycle.Electrical);

        // Create free, occupied and out of order parking slot
        ParkingSlot parkingSlotFree1 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree2 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotFree3 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupiedMechanical1 = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedMechanical2 = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedMechanical3 = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedMechanical4 = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedElectrical = new ParkingSlot(ParkingSlotStatus.Occupied, electricalBicycle);
        ParkingSlot parkingSlotOutOfOrder1 = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null);
        ParkingSlot parkingSlotOutOfOrder2 = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null);

        // Create 3 stations and fill them with parking slots :
        Station station1 = new Station(6.3001, 1.4, StationStatus.OnService, TypeOfStation.Standard);
        myVelibRecord.addStationIfNotExists(station1); // add station to Record
        station1.addParkingSlot(parkingSlotFree1);
        station1.addParkingSlot(parkingSlotOccupiedMechanical1);
        station1.addParkingSlot(parkingSlotOccupiedMechanical2);
        station1.addParkingSlot(parkingSlotOutOfOrder1);

        Station station2 = new Station(6.3, 1.4, StationStatus.Offline, TypeOfStation.Plus);
        myVelibRecord.addStationIfNotExists(station2); // add station to Record
        station2.addParkingSlot(parkingSlotFree2);
        station2.addParkingSlot(parkingSlotOccupiedMechanical3);

        Station station3 = new Station(10.0, 1.4, StationStatus.OnService, TypeOfStation.Standard);
        myVelibRecord.addStationIfNotExists(station3); // add station to Record
        station3.addParkingSlot(parkingSlotOutOfOrder2);
        station3.addParkingSlot(parkingSlotOccupiedElectrical);
        station3.addParkingSlot(parkingSlotOccupiedMechanical4);
        station3.addParkingSlot(parkingSlotFree3);

        // Create 3 users
        User user1 = new User("Billy Gates", 0.0, 145.4, "1235384958374038",
                new NoRegistrationCard());
        myVelibRecord.addUserIfNotExists(user1);
        User user2 = new User("Marcus Zuckerberg", 12.0, 15.4, "1235384939027403",
                new VlibreRegistrationCard());
        myVelibRecord.addUserIfNotExists(user2);
        User user3 = new User("Larri Pages", 22.0, 100.4, "3538493204740384",
                new VmaxRegistrationCard());
        myVelibRecord.addUserIfNotExists(user3);
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

        // ....
        // Return the instantiated command with user in-line parameters, and the Current System instance
        // ....

    }

    private static void finalization() {
        // Last chance for joining the last running threads (if there is !)
        // ....
        // Code for cleaning the system before leaving
        // ....
        System.out.println("Finalization complete.");
    }

}

