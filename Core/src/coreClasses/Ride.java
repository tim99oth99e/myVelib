package src.coreClasses;

import src.exception.IdAlreadyTakenException;
import src.exception.ReturnDateNotValidException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;

/** TODO
 * create a History class
 * code all statistics functions
 *
 * Exceptions : - avoid renting 2 bikes at the same time
 */

public class Ride {
    private User user;
    private Station rentStation;
    private Station returnStation;
    private LocalDateTime rentDateTime;
    private LocalDateTime returnDateTime;
    private int id;

    private static ArrayList<Integer> usedIds = new ArrayList<>(); // there are 2+ billion possible positive ids


    public Ride(User user, Station startStation, Station droppingStation, LocalDateTime rentDateTime, LocalDateTime returnDateTime) throws Exception {
        this.user = user;
        this.rentStation = startStation;
        this.returnStation = droppingStation;
        this.rentDateTime = rentDateTime;
        // check if the end date is greater than the start date
        this.setReturnDateTime(returnDateTime);
        this.id = getValidId();

    }

    @Override
    public String toString() {
        // define datetime format
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT).withLocale( Locale.ENGLISH );

        return "Ride started " + this.rentDateTime.format(formatter) + " by " + this.user.getName() + " at station #"
                + this.rentStation.getId() + " and ended at station #" + this.returnStation.getId() + ". Duration : " + this.getDurationInMinutes() + " min.";
    }

    private static int getValidId(){
        int tempId=0;
        // find the first id that is not used
        while (usedIds.contains(tempId)) {
            tempId ++;
        }
        // add this id to the list of used ones
        usedIds.add(tempId);
        return tempId;

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

    public int getId() {
        return this.id;
    }

    public void setId(int id) throws Exception {
        // if this id isn't used
        if (!usedIds.contains(id)) {
            // mark the new id as used
            usedIds.add(id);
            // remove the old id from the list
            usedIds.remove(this.id);
            this.id = id;
        } else {
            throw new IdAlreadyTakenException(id);
        }
    }

    public static ArrayList<Integer> getUsedIds() {
        return usedIds;
    }

    public static void setUsedIds(ArrayList<Integer> usedIds) {
        Ride.usedIds = usedIds;
    }
}
