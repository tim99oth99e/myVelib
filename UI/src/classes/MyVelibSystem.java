package src.classes;

import src.coreClasses.Bicycle;
import src.coreClasses.ParkingSlot;
import src.coreClasses.Record;
import src.coreClasses.Station;
import src.enums.ParkingSlotStatus;
import src.enums.StationStatus;
import src.enums.TypeOfBicycle;
import src.enums.TypeOfStation;

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

    private static void initialization() {
        // Code for Loading my_velib.ini  and the Systeme creation !
        // Create bicycles of two different type
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
        Bicycle electricalBicycle = new Bicycle(TypeOfBicycle.Electrical);

        // Create free, occupied and out of order parking slot
        ParkingSlot parkingSlotFree1 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupiedMechanical = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedMechanical2 = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedElectrical = new ParkingSlot(ParkingSlotStatus.Occupied, electricalBicycle);
        ParkingSlot parkingSlotOutOfOrder = new ParkingSlot(ParkingSlotStatus.OutOfOrder, null);

        // Create 3 stations and fill them with parking slots :
        Station station1 = new Station(6.3001, 1.4, StationStatus.OnService, TypeOfStation.Standard);
        myVelibRecord.addStationIfNotExists(station1); // add station to Record
        station1.addParkingSlot(parkingSlotFree1);
        station1.addParkingSlot(parkingSlotOccupiedMechanical);
        station1.addParkingSlot(parkingSlotOccupiedMechanical2);
        station1.addParkingSlot(parkingSlotOutOfOrder);

        Station station2 = new Station(6.3, 1.4, StationStatus.OnService, TypeOfStation.Plus);
        myVelibRecord.addStationIfNotExists(station2); // add station to Record
        station2.addParkingSlot(parkingSlotFree1);
        station2.addParkingSlot(parkingSlotOccupiedMechanical);

        Station station3 = new Station(10.0, 1.4, StationStatus.OnService, TypeOfStation.Standard);
        myVelibRecord.addStationIfNotExists(station3); // add station to Record
        station3.addParkingSlot(parkingSlotOutOfOrder);
        station3.addParkingSlot(parkingSlotOccupiedElectrical);
        station3.addParkingSlot(parkingSlotOccupiedMechanical);
        station3.addParkingSlot(parkingSlotFree1);
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
            System.out.println(STATUS);
        }
        while( CMD.isNotExit() );
    }

    private static VelibCommand readFromConsole() {
        VelibCommand velibCommand = new VelibCommand();
        // Read a command String from Console
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("Please input a line");
                String line = scanner.nextLine();
                System.out.printf("User input was: %s%n", line);
                velibCommand.setCommandName(line);
                // Decode the String to determine the Command class to instantiate
                String[] parts = line.split(" ");
            }
        } catch(IllegalStateException e) {
            // System.in has been closed
            System.out.println("System.in was closed; exiting");
        }

        // ....
        // Return the instantiated command with user in-line parameters, and the Current System instance
        // ....
        return velibCommand;
    }

    private static void finalization() {
        // Last chance for joining the last running threads (if there is !)
        // ....
        // Code for cleaning the system before leaving
        // ....
    }

}

