package src.registrationCard;

import src.coreClasses.User;
import src.enums.TypeOfBicycle;

public abstract class RegistrationCard {
    private final boolean handlesTimeCredit;
    private final int firstHourFareMechanical;
    private final int nextHoursFareMechanical;
    private final int firstHourFareElectrical;
    private final int nextHoursFareElectrical;

    public RegistrationCard(boolean handlesTimeCredit, int firstHourFareMechanical, int nextHoursFareMechanical, int firstHourFareElectrical, int nextHoursFareElectrical) {
        this.handlesTimeCredit = handlesTimeCredit;
        this.firstHourFareMechanical = firstHourFareMechanical;
        this.nextHoursFareMechanical = nextHoursFareMechanical;
        this.firstHourFareElectrical = firstHourFareElectrical;
        this.nextHoursFareElectrical = nextHoursFareElectrical;
    }

    /**
     * returns the 1st hour fare for a ride
     */
    public int getFirstHourFare(TypeOfBicycle bicycleType) {
        if (bicycleType == TypeOfBicycle.Mechanical) {
            return getFirstHourFareMechanical();
        } else {
            return getFirstHourFareElectrical();
        }
    }

    /**
     * returns the 2+ hour fare for a ride
     */
    public int getNextHoursFare(TypeOfBicycle bicycleType) {
        if (bicycleType == TypeOfBicycle.Mechanical) {
            return getNextHoursFareMechanical();
        } else {
            return getNextHoursFareElectrical();
        }
    }

    /**
     * computes the cost of the ride after having dealt with the timeCredit
     */
    public int getHoursCost(int billedHours, TypeOfBicycle bicycleType){
        if (billedHours == 0) {
            return 0;
        }
        int basePrice = getFirstHourFare(bicycleType);
        if (billedHours > 1) {
            return basePrice + (billedHours - 1) * getNextHoursFare(bicycleType);
        }
        return basePrice;
    }

    /**
     * returns the time credit for a ride
     */
    public int getTimeCredit(int rideDuration){
        return 60 - (rideDuration % 60);
    }


    /**
     * returns the price the user has to pay for this ride
     */
    public double computeRideCost(double rideDuration, TypeOfBicycle bicycleType, User user) {

        // convert minutes in hours, (+1 because (int) floors)
        int billedHours = (int) rideDuration/60 + 1;

        // if the user has a card
        if (this.handlesTimeCredit()) {
            // compute the timeCredit gained
            int earnedTimeCredit = this.getTimeCredit((int) rideDuration);
            // add it to the user balance
            user.setTimeCreditBalance(user.getTimeCreditBalance() + earnedTimeCredit);

            // if timeCredit > 60 and rideDuration > 60 : get a reduction
            if (user.getTimeCreditBalance() >= 60 && rideDuration >= 60) {
                // compute how many hours can be removed from the final cost
                int possibleCreditHours = (int) user.getTimeCreditBalance() / 60;
                // and how many hours above 1 were ridden
                int discountableHours = (int) rideDuration / 60;

                int nbFreeHours = Math.min(possibleCreditHours, discountableHours);
                billedHours -= nbFreeHours;
                //update user balance
                user.setTimeCreditBalance(user.getTimeCreditBalance() - nbFreeHours * 60);
            }

        }

        return this.getHoursCost(billedHours, bicycleType);
    }


    // getters
    public boolean handlesTimeCredit() {
        return handlesTimeCredit;
    }

    public int getFirstHourFareMechanical() {
        return firstHourFareMechanical;
    }

    public int getNextHoursFareMechanical() {
        return nextHoursFareMechanical;
    }

    public int getFirstHourFareElectrical() {
        return firstHourFareElectrical;
    }

    public int getNextHoursFareElectrical() {
        return nextHoursFareElectrical;
    }

}
