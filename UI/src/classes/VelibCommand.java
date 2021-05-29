package src.classes;
import java.util.ArrayList;

public class VelibCommand {
    private String commandName;
    private ArrayList<String> arguments;

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
}
