package src.exception;

public class IdAlreadyTakenException extends Exception{
    public IdAlreadyTakenException(int id) {
        super("The id " + id + " is already used.");
    }
}
