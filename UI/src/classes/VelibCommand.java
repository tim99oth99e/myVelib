package src.classes;
import java.util.ArrayList;

public class VelibCommand {
    private String commandName;
    private ArrayList<String> arguments;

    public VelibCommand() {
        this.commandName = null;
        this.arguments = null;
    }

    public VelibCommand(String commandName, ArrayList<String> arguments) {
        this.commandName = commandName;
        this.arguments = arguments;
    }

    public String eval() {
        switch (commandName) {
            case "addUser":
                MyVelibSystem.myVelibRecord.addUserIfNotExists();
                break;
            case "":

        }
    }

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
