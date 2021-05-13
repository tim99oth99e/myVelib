package src.exception;

public class LatitudeOutOfBoundsException extends Exception{
    public LatitudeOutOfBoundsException(double latitude) {
        super("Latitude should be between -90\u00B0 and +90\u00B0 but is : " + latitude + "\u00B0");
    }
}
