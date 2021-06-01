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

    // deal with the case where all ids are taken
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
     * Instantiates a new User.
     *
     * @param name             the name
     * @param latitude         the latitude
     * @param longitude        the longitude
     * @param creditCardNumber the credit card number
     * @param registrationCard the registration card
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
     * Instantiates a new User.
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
     * Add charge.
     *
     * @param charge the charge
     */
    public void addCharge(double charge) {
        this.setTotalCharges(this.getTotalCharges() + charge);
    }

    /**
     * Compute cost double.
     *
     * @param bicycleType    the bicycle type
     * @param rentDateTime   the rent date time
     * @param returnDateTime the return date time
     * @return the double
     */
    public double computeCost(TypeOfBicycle bicycleType, LocalDateTime rentDateTime, LocalDateTime returnDateTime) {
        int rideDurationInMinutes = (int) rentDateTime.until(returnDateTime, ChronoUnit.MINUTES);
        double rideCost = this.getRegistrationCard().computeRideCost(rideDurationInMinutes, bicycleType, this);
        this.addCharge(rideCost);
        return rideCost;
    }


    /**
     * Rent.
     *
     * @param parkingSlot  the parking slot
     * @param rentDateTime the rent date time
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
     * Rent.
     *
     * @param parkingSlot the parking slot
     */
    public void rent(ParkingSlot parkingSlot){
        this.rent(parkingSlot, LocalDateTime.now());
    }

    /**
     * Park.
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
     * Park.
     *
     * @param parkingSlot the parking slot
     */
    public void park(ParkingSlot parkingSlot) {
        this.park(parkingSlot, LocalDateTime.now());
    }

    // getters & setters

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets id.
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
     * Gets latitude.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     * @throws LatitudeOutOfBoundsException the latitude out of bounds exception
     */
    public void setLatitude(double latitude) throws LatitudeOutOfBoundsException {
        if (-90.0 <= latitude && latitude <= 90.0) {
            this.latitude = latitude;
        } else {
            throw new LatitudeOutOfBoundsException(latitude);
        }
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     * @throws LongitudeOutOfBoundsException the longitude out of bounds exception
     */
    public void setLongitude(double longitude) throws LongitudeOutOfBoundsException {
        if (-180.0 <= longitude && longitude <= 180.0) {
            this.longitude = longitude;
        } else {
            throw new LongitudeOutOfBoundsException(longitude);
        }
    }

    /**
     * Gets credit card number.
     *
     * @return the credit card number
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * Sets credit card number.
     *
     * @param creditCardNumber the credit card number
     * @throws CreditCardNotValidException the credit card not valid exception
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
     * Gets registration card.
     *
     * @return the registration card
     */
    public RegistrationCard getRegistrationCard() {
        return registrationCard;
    }

    /**
     * Sets registration card.
     *
     * @param registrationCard the registration card
     */
    public void setRegistrationCard(RegistrationCard registrationCard) {
        this.registrationCard = registrationCard;
    }

    /**
     * Gets time credit balance.
     *
     * @return the time credit balance
     */
    public double getTimeCreditBalance() {
        return timeCreditBalance;
    }

    /**
     * Sets time credit balance.
     *
     * @param timeCreditBalance the time credit balance
     */
    public void setTimeCreditBalance(double timeCreditBalance) {
        this.timeCreditBalance = timeCreditBalance;
    }

    /**
     * Gets total charges.
     *
     * @return the total charges
     */
    public double getTotalCharges() {
        return totalCharges;
    }

    /**
     * Sets total charges.
     *
     * @param totalCharges the total charges
     */
    public void setTotalCharges(double totalCharges) {
        this.totalCharges = totalCharges;
    }

    /**
     * Gets used ids.
     *
     * @return the used ids
     */
    public static ArrayList<Integer> getUsedIds() {
        return usedIds;
    }

    /**
     * Sets used ids.
     *
     * @param usedIds the used ids
     */
    public static void setUsedIds(ArrayList<Integer> usedIds) {
        User.usedIds = usedIds;
    }

    /**
     * Gets number of rides.
     *
     * @return the number of rides
     */
    public int getNumberOfRides() {
        return numberOfRides;
    }

    /**
     * Sets number of rides.
     *
     * @param numberOfRides the number of rides
     */
    public void setNumberOfRides(int numberOfRides) {
        this.numberOfRides = numberOfRides;
    }

    /**
     * Gets total rent time in minutes.
     *
     * @return the total rent time in minutes
     */
    public int getTotalRentTimeInMinutes() {
        return totalRentTimeInMinutes;
    }

    /**
     * Sets total rent time in minutes.
     *
     * @param totalRentTimeInMinutes the total rent time in minutes
     */
    public void setTotalRentTimeInMinutes(int totalRentTimeInMinutes) {
        this.totalRentTimeInMinutes = totalRentTimeInMinutes;
    }

    /**
     * Gets rented bicycle.
     *
     * @return the rented bicycle
     */
    public Bicycle getRentedBicycle() {
        return rentedBicycle;
    }

    /**
     * Sets rented bicycle.
     *
     * @param rentedBicycle the rented bicycle
     */
    public void setRentedBicycle(Bicycle rentedBicycle) {
        this.rentedBicycle = rentedBicycle;
    }
}
