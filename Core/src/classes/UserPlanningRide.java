package src.classes;

import src.enums.RegistrationCardType;

public class UserPlanningRide extends User {

    protected Boolean avoidPlusStation;
    protected Boolean preferPlusStation;
    protected Boolean preservationOfUniformityOfBicyclesDistributionAmongstStations;

    //ToString

    @Override
    public String toString() {
        return "User " + super.toString() + "\n" +
                "Plan to ride : " + "\n" +
                "Avoid \"Plus\" stations : " + avoidPlusStation + "\n" +
                "Prefer \"Plus\" stations : " + preferPlusStation + "\n" +
                "Preservation of uniformity of bicycles distribution amongst stations :" + preservationOfUniformityOfBicyclesDistributionAmongstStations;
    }

    //Constructors
    public UserPlanningRide(String name, double latitude, double longitude, String creditCardNumber) {
        super(name, latitude, longitude, creditCardNumber);
        this.avoidPlusStation = false;
        this.preservationOfUniformityOfBicyclesDistributionAmongstStations = false;
        this.preferPlusStation = false;
    }

    public UserPlanningRide(String name, double latitude, double longitude, String creditCardNumber, RegistrationCardType registrationCardType) {
        super(name, latitude, longitude, creditCardNumber, registrationCardType);
        this.avoidPlusStation = false;
        this.preservationOfUniformityOfBicyclesDistributionAmongstStations = false;
        this.preferPlusStation = false;
    }

    public UserPlanningRide(String name, double latitude, double longitude, String creditCardNumber, RegistrationCardType registrationCardType, Boolean avoidPlusStation
    ,Boolean preferPlusStation,Boolean preservationOfUniformityOfBicyclesDistributionAmongstStations ) {
        super(name, latitude, longitude, creditCardNumber, registrationCardType);
        this.avoidPlusStation = avoidPlusStation;
        this.preservationOfUniformityOfBicyclesDistributionAmongstStations = preservationOfUniformityOfBicyclesDistributionAmongstStations;
        this.preferPlusStation = preferPlusStation;
    }

    //Getters
    public Boolean getAvoidPlusStation() {
        return avoidPlusStation;
    }

    public Boolean getPreferPlusStation() {
        return preferPlusStation;
    }

    public Boolean getPreservationOfUniformityOfBicyclesDistributionAmongstStations() {
        return preservationOfUniformityOfBicyclesDistributionAmongstStations;
    }

    //Setters
    public void setAvoidPlusStation(Boolean avoidPlusStation) {
        this.avoidPlusStation = avoidPlusStation;
    }

    public void setPreferPlusStation(Boolean preferPlusStation) {
        this.preferPlusStation = preferPlusStation;
    }

    public void setPreservationOfUniformityOfBicyclesDistributionAmongstStations(Boolean preservationOfUniformityOfBicyclesDistributionAmongstStations) {
        this.preservationOfUniformityOfBicyclesDistributionAmongstStations = preservationOfUniformityOfBicyclesDistributionAmongstStations;
    }
}
