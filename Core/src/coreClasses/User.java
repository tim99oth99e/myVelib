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

public class User {
    private String name;
    private int id;
    private double latitude;
    private double longitude;
    private String creditCardNumber;
    private RegistrationCard registrationCard;
    // statistics
    private double timeCreditBalance;
    private double totalCharges;
    private int numberOfRides;
    private int totalRentTimeInMinutes;
    private Bicycle rentedBicycle;

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
     * Constructor without registration card parameter
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

    public void addCharge(double charge) {
        this.setTotalCharges(this.getTotalCharges() + charge);
    }

    public double computeCost(TypeOfBicycle bicycleType, LocalDateTime rentDateTime, LocalDateTime returnDateTime) {
        int rideDurationInMinutes = (int) rentDateTime.until(returnDateTime, ChronoUnit.MINUTES);
        double rideCost = this.getRegistrationCard().computeRideCost(rideDurationInMinutes, bicycleType, this);
        this.addCharge(rideCost);
        return rideCost;
    }

    public String rent(ParkingSlot parkingSlot){
        if (parkingSlot.getParkingSlotStatus() == ParkingSlotStatus.Occupied){
            if (this.rentedBicycle == null){
                this.rentedBicycle = parkingSlot.getBicycle();
                parkingSlot.setParkingSlotStatus(ParkingSlotStatus.Free);
                parkingSlot.setBicycle(null);
                return rentedBicycle.getType() + " successfully rented.";
            }
            else {
                return "Error: a user can only rent at most one bicycle";
            }
        }
        else {
            return "Error: this parking slot has no available bicycle.";
        }
    }

    // getters & setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) throws LatitudeOutOfBoundsException {
        if (-90.0 <= latitude && latitude <= 90.0) {
            this.latitude = latitude;
        } else {
            throw new LatitudeOutOfBoundsException(latitude);
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) throws LongitudeOutOfBoundsException {
        if (-180.0 <= longitude && longitude <= 180.0) {
            this.longitude = longitude;
        } else {
            throw new LongitudeOutOfBoundsException(longitude);
        }
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) throws CreditCardNotValidException {
        // check if the credit card number is valid or not
        if (creditCardNumber.matches("[0-9]+") && creditCardNumber.length() == 16) {
            this.creditCardNumber = creditCardNumber;
        } else {
            throw new CreditCardNotValidException(creditCardNumber);
        }

    }

    public RegistrationCard getRegistrationCard() {
        return registrationCard;
    }

    public void setRegistrationCard(RegistrationCard registrationCard) {
        this.registrationCard = registrationCard;
    }

    public double getTimeCreditBalance() {
        return timeCreditBalance;
    }

    public void setTimeCreditBalance(double timeCreditBalance) {
        this.timeCreditBalance = timeCreditBalance;
    }

    public double getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(double totalCharges) {
        this.totalCharges = totalCharges;
    }

    public static ArrayList<Integer> getUsedIds() {
        return usedIds;
    }

    public static void setUsedIds(ArrayList<Integer> usedIds) {
        User.usedIds = usedIds;
    }

    public int getNumberOfRides() {
        return numberOfRides;
    }

    public void setNumberOfRides(int numberOfRides) {
        this.numberOfRides = numberOfRides;
    }

    public int getTotalRentTimeInMinutes() {
        return totalRentTimeInMinutes;
    }

    public void setTotalRentTimeInMinutes(int totalRentTimeInMinutes) {
        this.totalRentTimeInMinutes = totalRentTimeInMinutes;
    }

    public Bicycle getRentedBicycle() {
        return rentedBicycle;
    }

    public void setRentedBicycle(Bicycle rentedBicycle) {
        this.rentedBicycle = rentedBicycle;
    }
}
