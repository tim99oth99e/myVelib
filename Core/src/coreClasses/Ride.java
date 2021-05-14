package src.coreClasses;

import src.exception.ReturnDateNotValidException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/** TODO
 * code test
 * create a History class
 * code all statistics functions
 */

public class Ride {
    private User user;
    private Station rentStation;
    private Station returnStation;
    private LocalDateTime rentDateTime;
    private LocalDateTime returnDateTime;

    public Ride(User user, Station startStation, Station droppingStation, LocalDateTime rentDateTime, LocalDateTime returnDateTime) {
        this.user = user;
        this.rentStation = startStation;
        this.returnStation = droppingStation;
        this.rentDateTime = rentDateTime;
        // add exception if the end date is greater than the start date
        this.returnDateTime = returnDateTime;
    }

    @Override
    public String toString() {
        // define datetime format
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT).withLocale( Locale.ENGLISH );

        return "Ride started " + this.rentDateTime.format(formatter) + " by " + this.user.getName() + " at station #"
                + this.rentStation.getId() + " and ended at station #" + this.returnStation.getId() + ". Duration : " + this.getDurationInMinutes() + " min.";
    }

    public int getDurationInMinutes(){
        // seconds of difference are rounded
        return (int) this.rentDateTime.until(this.returnDateTime, ChronoUnit.MINUTES);
    }

    // getters & setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Station getRentStation() {
        return rentStation;
    }

    public void setRentStation(Station rentStation) {
        this.rentStation = rentStation;
    }

    public Station getReturnStation() {
        return returnStation;
    }

    public void setReturnStation(Station returnStation) {
        this.returnStation = returnStation;
    }

    public LocalDateTime getRentDateTime() {
        return rentDateTime;
    }

    public void setRentDateTime(LocalDateTime rentDateTime) {
        this.rentDateTime = rentDateTime;
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) throws Exception {
        // check if the return date is greater thant the rent date
        if (!returnDateTime.isBefore(this.rentDateTime)) {
            this.returnDateTime = returnDateTime;
        } else {
            throw new ReturnDateNotValidException(returnDateTime);
        }
        // if rentdate is not set ??
    }
}
