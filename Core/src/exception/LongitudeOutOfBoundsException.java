package src.exception;

public class LongitudeOutOfBoundsException extends Exception{
    public LongitudeOutOfBoundsException(double longitude) {
        super("Longitude should be between -180 \u00B0 and +180\u00B0 but is :" + longitude + "\u00B0");
    }
}
