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

public class MyVelibSystem {
    static Record myVelibRecord = new Record();

    public static void main(String[] args) {
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
        myVelibRecord.addStationIfNotExists(station1); // add to the record
        station1.addParkingSlot(parkingSlotFree1);
        station1.addParkingSlot(parkingSlotOccupiedMechanical);
        station1.addParkingSlot(parkingSlotOccupiedMechanical2);
        station1.addParkingSlot(parkingSlotOutOfOrder);

        Station station2 = new Station(6.3, 1.4, StationStatus.OnService, TypeOfStation.Plus);
        myVelibRecord.addStationIfNotExists(station2); // add to the record
        station2.addParkingSlot(parkingSlotFree1);
        station2.addParkingSlot(parkingSlotOccupiedMechanical);

        Station station3 = new Station(10.0, 1.4, StationStatus.OnService, TypeOfStation.Standard);
        myVelibRecord.addStationIfNotExists(station3); // add to the record
        station3.addParkingSlot(parkingSlotOutOfOrder);
        station3.addParkingSlot(parkingSlotOccupiedElectrical);
        station3.addParkingSlot(parkingSlotOccupiedMechanical);
        station3.addParkingSlot(parkingSlotFree1);
    }

    private static void readEvalPrintLoop() {
        do {
            // Read a command
            VelibCommand CMD = readFromConsole();

            // Eval the command
            String STATUS = CMD.eval(myVelibRecord);

            // Print result (sysout) or error message (syserr)
            STATUS.printMessage();
        }
        while( CMD.isNotExit() );
    }

    private static VelibCommand readFromConsole() {
        // Read a command's String from Console
        // ....
        // Decode the String to determine the Command class to instantiate
        // ....
        // Return the instantiated command with user in-line parameters, and the Current System instance
        // ....
    }

    private static void finalization() {
        // Last chance for joining the last running threads (if there is !)
        // ....
        // Code for cleaning the system before leaving
        // ....
    }

}

