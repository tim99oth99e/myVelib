package src.classes;
import src.coreClasses.User;

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
        return commandName != "exit";
    }

    // main method
    public String eval() throws Exception {
        switch (commandName) {
            case "addUser":
                // write exception to deal with a wrong number of arguments
                // get arguments
                String name = arguments.get(0);
                // add method to parse card type
                // velibNetwork
                // create the user
                User userToAdd = new User(name, 20.0, 20.0,"1234123412341234");
                MyVelibSystem.myVelibRecord.addUserIfNotExists(userToAdd);
                break;
//            case "":
        }
        return "Finished evaluation";
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
