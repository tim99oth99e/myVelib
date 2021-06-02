package src.registrationCard;

import src.coreClasses.User;
import src.enums.TypeOfBicycle;

/**
 * This class describes a base Registration card.
 */
public abstract class RegistrationCard {
    private final boolean handlesTimeCredit;
    private final int firstHourFareMechanical;
    private final int nextHoursFareMechanical;
    private final int firstHourFareElectrical;
    private final int nextHoursFareElectrical;

    /**
     * Instantiates a new Registration card.
     *
     * @param handlesTimeCredit       does the card handles time credit or not
     * @param firstHourFareMechanical the first hour fare when renting a mechanical bike
     * @param nextHoursFareMechanical the next hours fare when renting a mechanical bike
     * @param firstHourFareElectrical the first hour fare when renting a electrical bike
     * @param nextHoursFareElectrical the next hours fare when renting a electrical bike
     */
    public RegistrationCard(boolean handlesTimeCredit, int firstHourFareMechanical, int nextHoursFareMechanical, int firstHourFareElectrical, int nextHoursFareElectrical) {
        this.handlesTimeCredit = handlesTimeCredit;
        this.firstHourFareMechanical = firstHourFareMechanical;
        this.nextHoursFareMechanical = nextHoursFareMechanical;
        this.firstHourFareElectrical = firstHourFareElectrical;
        this.nextHoursFareElectrical = nextHoursFareElectrical;
    }

    /**
     * returns the 1st hour fare for a ride
     *
     * @param bicycleType the bicycle type
     * @return the first hour fare
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
     *
     * @param bicycleType the bicycle type
     * @return the next hours fare
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
     *
     * @param billedHours the billed hours
     * @param bicycleType the bicycle type
     * @return the final cost of the ride, including discounts
     */
    public int getHoursCost(int billedHours, TypeOfBicycle bicycleType){
        int basePrice = getFirstHourFare(bicycleType);
        if (billedHours > 1) {
            return basePrice + (billedHours - 1) * getNextHoursFare(bicycleType);
        }
        return basePrice;
    }

    /**
     * returns the time credit gained during a ride
     *
     * @param rideDuration the ride duration
     * @return the time credit won
     */
    public int getTimeCredit(int rideDuration){
        return 60 - (rideDuration % 60);
    }


    /**
     * returns the price the user has to pay for a ride
     *
     * @param rideDuration the ride duration
     * @param bicycleType  the bicycle type
     * @param user         the user
     * @return the price the user will pay
     */
    public double computeRideCost(double rideDuration, TypeOfBicycle bicycleType, User user){
        if (rideDuration == 0) {
            return 0;
        }

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


    /**
     * Returns true if the card handles time=credit.
     *
     * @return a boolean value telling if the class handles time-credit or not
     */
    public boolean handlesTimeCredit() {
        return handlesTimeCredit;
    }

    /**
     * Gets first hour fare for a ride with a mechanical bike.
     *
     * @return the first hour fare
     */
    public int getFirstHourFareMechanical() {
        return firstHourFareMechanical;
    }

    /**
     * Gets next hours fare for a ride with a mechanical bike.
     *
     * @return the next hours fare
     */
    public int getNextHoursFareMechanical() {
        return nextHoursFareMechanical;
    }

    /**
     * Gets first hour fare for a ride with a electrical bike.
     *
     * @return the first hour fare
     */
    public int getFirstHourFareElectrical() {
        return firstHourFareElectrical;
    }

    /**
     * Gets next hours fare for a ride with a electrical bike.
     *
     * @return the next hours fare
     */
    public int getNextHoursFareElectrical() {
        return nextHoursFareElectrical;
    }

}
