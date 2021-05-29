package src.classes;

public class MyVelibSystem {

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
        // ....
    }

    private static void readEvalPrintLoop() {
        do {
            // Read a command
            CMD = readFromConsole();

            // Eval the command
            STATUS = CMD.eval();

            // Print result (sysout) or error message (syserr)
            STATUS.printMessage();
        }
        while( CMD.isNotExit() );
    }

    private static VelibCommand readFromConsole() {
        // Read a commandd's String from Console
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

