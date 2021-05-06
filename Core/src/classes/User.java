package src.classes;

import src.enums.*;
import java.util.ArrayList;

public class User {
    private String name;
    private int id;
    private double latitude;
    private double longitude;
    private String creditCardNumber;
    private RegistrationCardType registrationCardType;
    private double timeCreditBalance;
    private double totalCharges;

    private static ArrayList<Integer> usedIds = new ArrayList<Integer>(); // there are 2+ billion possible positive ids

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


    public User(String name, double latitude, double longitude, String creditCardNumber, RegistrationCardType registrationCardType) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.creditCardNumber = creditCardNumber;
        this.registrationCardType = registrationCardType;
        this.timeCreditBalance = 0;
        this.totalCharges = 0;
        this.id = getValidId();
    }

    /**
     * Constructor without registration card parameter
     * @param name
     * @param latitude
     * @param longitude
     * @param creditCardNumber
     */
    public User(String name, double latitude, double longitude, String creditCardNumber) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.creditCardNumber = creditCardNumber;
        this.registrationCardType = RegistrationCardType.None;
        this.timeCreditBalance = 0;
        this.totalCharges = 0;
        this.id = getValidId();
    }

    @Override
    public String toString() {
        String baseString = "User " + name + " [id:" + id + "]\n" +
                "Location : (latitude : " + latitude + ", longitude : " + longitude + ") \n" +
                "Credit card : " + creditCardNumber + ", total charges : " + totalCharges + "\n";
        // if the user has a registration card
        if (this.registrationCardType != RegistrationCardType.None) {
            return baseString + "Registration card : " + registrationCardType +
                    ", time credit balance : " + timeCreditBalance;
        } else {
            return baseString + "No registration card.";
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

    public void setId(int id) {
        // if this id isn't used
        if (!usedIds.contains(id)) {
            // mark the new id as used
            usedIds.add(id);
            // remove the old id from the list
            usedIds.remove(this.id);
            this.id = id;
        }
        // else, return an error ?
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public RegistrationCardType getRegistrationCardType() {
        return registrationCardType;
    }

    public void setRegistrationCardType(RegistrationCardType registrationCardType) {
        this.registrationCardType = registrationCardType;
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

}
