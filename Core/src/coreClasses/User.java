package src.coreClasses;

import src.enums.*;
import src.exception.CreditCardNotValidException;
import src.exception.IdAlreadyTakenException;
import src.exception.LatitudeOutOfBoundsException;
import src.exception.LongitudeOutOfBoundsException;
import src.registrationCard.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * This class represents a user of myVelib platform
 */
public class User {
    /**
     * The name or username of the represented user.
     */
    private String name;
    /**
     * The unique identifier of a User object.
     */
    private int id;
    /**
     * The latitude coordinate of the actual location of the user.
     */
    private double latitude;
    /**
     * The longitude coordinate of the actual location of the user.
     */
    private double longitude;
    /**
     * The credit card number of the user.
     */
    private String creditCardNumber;
    /**
     * The registration card type of the user : none, Vlibre, Vmax.
     */
    private RegistrationCard registrationCard;

    // statistics
    /**
     * The user time-credit balance : positive number of minutes from which the user can get a discount in his next ride.
     */
    private double timeCreditBalance;
    /**
     * Sum of all fares the user paid for having rented bikes.
     */
    private double totalCharges;
    /**
     * The total number of rides the user has done since he created his account.
     */
    private int numberOfRides;
    /**
     * The total time in minutes during which the user was renting a bike.
     */
    private int totalRentTimeInMinutes;

    // rental
    /**
     * If null, the user is not currently renting a bike, else, the bike that the user if renting.
     */
    private Bicycle rentedBicycle;
    /**
     * THe last date time the user has rented a bike.
     */
    private LocalDateTime rentDateTime;
    /**
     * List of all currently used User IDs, to keep track of which ids are available.
     */
    private static ArrayList<Integer> usedIds = new ArrayList<>(); // there are 2+ billion possible positive ids

    /**
     * Gets an ID that is not already used by a user.
     *
     * @return The first ID that is not in the usedIds ArrayList.
     */
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

    /**
     * Instantiates a new User with a registration card
     *
     * @param name             the name (first name and last name or username) of the user
     * @param latitude         the latitude coordinate of the user's location
     * @param longitude        the longitude coordinate of the user's location
     * @param creditCardNumber the credit card number of the user
     * @param registrationCard the registration that the user has
     * @throws Exception the exception
     */
    public User(String name, double latitude, double longitude, String creditCardNumber, RegistrationCard registrationCard) throws Exception {
        this.name = name;
        // check the values of lat and long
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        // check if the card number is correct
        this.setCreditCardNumber(creditCardNumber);
        this.registrationCard = registrationCard;
        this.timeCreditBalance = 0;
        this.totalCharges = 0;
        this.numberOfRides = 0;
        this.totalRentTimeInMinutes = 0;
        this.id = getValidId();
        this.rentedBicycle = null;
    }

    /**
     * Instantiates a new User with no registration card.
     *
     * @param name             the name
     * @param latitude         the latitude
     * @param longitude        the longitude
     * @param creditCardNumber the credit card number
     * @throws Exception the exception
     */
    public User(String name, double latitude, double longitude, String creditCardNumber) throws Exception {
        this.name = name;
        // check the values of lat and long
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        // check if the card number is correct
        this.setCreditCardNumber(creditCardNumber);
        this.registrationCard = new NoRegistrationCard();
        this.timeCreditBalance = 0;
        this.totalCharges = 0;
        this.numberOfRides = 0;
        this.totalRentTimeInMinutes = 0;
        this.id = getValidId();
        this.rentedBicycle = null;
    }

    @Override
    public String toString() {
        String baseString = "User " + name + " [id:" + id + "]\n" +
                "Location : (latitude : " + latitude + "\u00B0, longitude : " + longitude + "\u00B0) \n" +
                "Credit card : " + creditCardNumber + ", total charges : " + totalCharges + "\n" + "rented " + rentedBicycle + "\n";
        // if the user has no registration card
        if (this.registrationCard instanceof NoRegistrationCard) {
            return baseString + "No registration card.";
        } else {
            return baseString + "Registration card : " + registrationCard +
                    ", time credit balance : " + timeCreditBalance;
        }

    }

    /**
     * Adds a charge to the total of all charges paid by the user.
     *
     * @param charge the charge to add
     */
    public void addCharge(double charge) {
        this.setTotalCharges(this.getTotalCharges() + charge);
    }

    /**
     * Computes the cost of a ride.
     *
     * @param bicycleType    the type of the bicycle rented
     * @param rentDateTime   the rent datetime
     * @param returnDateTime the return datetime
     * @return the ride cost
     */
    public double computeCost(TypeOfBicycle bicycleType, LocalDateTime rentDateTime, LocalDateTime returnDateTime) {
        int rideDurationInMinutes = (int) rentDateTime.until(returnDateTime, ChronoUnit.MINUTES);
        double rideCost = this.getRegistrationCard().computeRideCost(rideDurationInMinutes, bicycleType, this);
        this.addCharge(rideCost);
        return rideCost;
    }


    /**
     * Rents a bike if possible at a particular date.
     * This means : if the user isn't renting a bike already, and if the parking slot has a bicycle on it.
     *
     * @param parkingSlot  the parking slot
     * @param rentDateTime the rent datetime
     */
    public void rent(ParkingSlot parkingSlot, LocalDateTime rentDateTime){
        if (parkingSlot.getParkingSlotStatus() == ParkingSlotStatus.Occupied){
            if (this.rentedBicycle == null){
                this.rentedBicycle = parkingSlot.getBicycle();
                parkingSlot.setParkingSlotStatus(ParkingSlotStatus.Free);
                parkingSlot.setBicycle(null);
                this.rentDateTime = rentDateTime;
                System.out.println("Bicycle successfully rented.");
            }
            else {
                System.out.println("Error: a user can only rent at most one bicycle");
            }
        }
        else {
            System.out.println("Error: this parking slot has no available bicycle.");
        }
    }


    /**
     * Rents a bike if possible at the exact datetime this method was called.
     * This means : if the user isn't renting a bike already, and if the parking slot has a bicycle on it.
     *
     * @param parkingSlot the parking slot
     */
    public void rent(ParkingSlot parkingSlot){
        this.rent(parkingSlot, LocalDateTime.now());
    }


    /**
     * Parks a bike if possible at a particular date.
     * This means : if the user is currently renting a bike, and if the parking slot has no bicycle on it.
     *
     * @param parkingSlot    the parking slot
     * @param returnDateTime the return date time
     */
    public void park(ParkingSlot parkingSlot, LocalDateTime returnDateTime){
        if (this.rentedBicycle == null){
            System.out.println("Error: this user has no bicycle to park.");
        }
        else {
            if (parkingSlot.getParkingSlotStatus() == ParkingSlotStatus.Free){
                parkingSlot.setParkingSlotStatus(ParkingSlotStatus.Occupied);
                parkingSlot.setBicycle(rentedBicycle);
                double computeCost = computeCost(rentedBicycle.getType(), this.rentDateTime, returnDateTime);
                System.out.println("Bicycle successfully parked.");
                // update user statistics
                // time credit and charges are updated when computing cost
                this.numberOfRides ++;
                this.totalRentTimeInMinutes += this.rentDateTime.until(returnDateTime, ChronoUnit.MINUTES);

                // reset rent attributes
                this.rentedBicycle = null;
                this.rentDateTime = null;
            }
            else {
                System.out.println("Error: you can only park bicycle on free parking slot.");
            }

        }
    }

    /**
     * Parks a bike if possible at the exact datetime this method was called.
     * This means : if the user is currently renting a bike, and if the parking slot has no bicycle on it.
     *
     * @param parkingSlot    the parking slot
     */
    public void park(ParkingSlot parkingSlot) {
        this.park(parkingSlot, LocalDateTime.now());
    }


    // getters & setters

    /**
     * Gets user's username.
     *
     * @return the username
     */
    public String getName() {
        return name;
    }

    /**
     * Sets user's username.
     *
     * @param name the username
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's id.
     *
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the user's id.
     *
     * @param id the id
     * @throws Exception the exception
     */
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

    /**
     * Gets the user's latitude.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the user's latitude.
     *
     * @param latitude the latitude
     * @throws LatitudeOutOfBoundsException if the latitude value entered is out of bounds
     */
    public void setLatitude(double latitude) throws LatitudeOutOfBoundsException {
        if (-90.0 <= latitude && latitude <= 90.0) {
            this.latitude = latitude;
        } else {
            throw new LatitudeOutOfBoundsException(latitude);
        }
    }

    /**
     * Gets the user's longitude.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the user's longitude.
     *
     * @param longitude the longitude
     * @throws LongitudeOutOfBoundsException if the longitude value entered is out of bounds
     */
    public void setLongitude(double longitude) throws LongitudeOutOfBoundsException {
        if (-180.0 <= longitude && longitude <= 180.0) {
            this.longitude = longitude;
        } else {
            throw new LongitudeOutOfBoundsException(longitude);
        }
    }

    /**
     * Gets the user's credit card number.
     *
     * @return the credit card number
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * Sets the user's credit card number.
     *
     * @param creditCardNumber the credit card number
     * @throws CreditCardNotValidException if the credit card number is not valid (length or content)
     */
    public void setCreditCardNumber(String creditCardNumber) throws CreditCardNotValidException {
        // check if the credit card number is valid or not
        if (creditCardNumber.matches("[0-9]+") && creditCardNumber.length() == 16) {
            this.creditCardNumber = creditCardNumber;
        } else {
            throw new CreditCardNotValidException(creditCardNumber);
        }

    }

    /**
     * Gets the user's registration card.
     *
     * @return the registration card
     */
    public RegistrationCard getRegistrationCard() {
        return registrationCard;
    }

    /**
     * Sets the user's registration card.
     *
     * @param registrationCard the registration card
     */
    public void setRegistrationCard(RegistrationCard registrationCard) {
        this.registrationCard = registrationCard;
    }

    /**
     * Gets the user's time credit balance.
     *
     * @return the time credit balance
     */
    public double getTimeCreditBalance() {
        return timeCreditBalance;
    }

    /**
     * Sets the user's time credit balance.
     *
     * @param timeCreditBalance the time credit balance
     */
    public void setTimeCreditBalance(double timeCreditBalance) {
        this.timeCreditBalance = timeCreditBalance;
    }

    /**
     * Gets the user's total charges.
     *
     * @return the total charges
     */
    public double getTotalCharges() {
        return totalCharges;
    }

    /**
     * Sets the user's total charges.
     *
     * @param totalCharges the total charges
     */
    public void setTotalCharges(double totalCharges) {
        this.totalCharges = totalCharges;
    }

    /**
     * Gets the used id's ArrayList.
     *
     * @return the used ids
     */
    public static ArrayList<Integer> getUsedIds() {
        return usedIds;
    }

    /**
     * Sets the used id's ArrayList.
     *
     * @param usedIds the used ids
     */
    public static void setUsedIds(ArrayList<Integer> usedIds) {
        User.usedIds = usedIds;
    }

    /**
     * Gets the total number of rides the user has done.
     *
     * @return the number of rides
     */
    public int getNumberOfRides() {
        return numberOfRides;
    }

    /**
     * Sets the total number of rides the user has done.
     *
     * @param numberOfRides the number of rides
     */
    public void setNumberOfRides(int numberOfRides) {
        this.numberOfRides = numberOfRides;
    }

    /**
     * Gets the total rent time of the user in minutes.
     *
     * @return the total rent time in minutes
     */
    public int getTotalRentTimeInMinutes() {
        return totalRentTimeInMinutes;
    }

    /**
     * Sets the total rent time of the user in minutes.
     *
     * @param totalRentTimeInMinutes the total rent time in minutes
     */
    public void setTotalRentTimeInMinutes(int totalRentTimeInMinutes) {
        this.totalRentTimeInMinutes = totalRentTimeInMinutes;
    }

    /**
     * Gets the current bicycle that the user is renting, or null if he is not doing so.
     *
     * @return the rented bicycle
     */
    public Bicycle getRentedBicycle() {
        return rentedBicycle;
    }

    /**
     * Sets the current bicycle that the user is renting or null if he is not doing so.
     *
     * @param rentedBicycle the rented bicycle
     */
    public void setRentedBicycle(Bicycle rentedBicycle) {
        this.rentedBicycle = rentedBicycle;
    }
}
