package src.classes;

import java.util.ArrayList;

public class User {
    private String name;
    private int id;
    private double latitude;
    private double longitude;
    private String creditCardNumber;
    private RegistrationCard registrationCard;
    private double timeCreditBalance;
    private double totalCharges;

    private static ArrayList<Integer> usedIds; // there are 2+ billion possible positive ids

    // deal with the case where all ids are taken
    private static int getValidId(){
        int tempId=0;
        // find the first id that is not used
        while (usedIds.contains(tempId)) {
            tempId ++;
        }
        return tempId;
    }

    public User(String name, double latitude, double longitude, String creditCardNumber, RegistrationCard registrationCard) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.creditCardNumber = creditCardNumber;
        this.registrationCard = registrationCard;
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
        this.registrationCard = null; // can't we use enums directly ?
        this.timeCreditBalance = 0;
        this.totalCharges = 0;
        this.id = getValidId();
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", registrationCard=" + registrationCard +
                ", timeCreditBalance=" + timeCreditBalance +
                ", totalCharges=" + totalCharges +
                '}';
    }

    // getters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    // setters

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
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

}
