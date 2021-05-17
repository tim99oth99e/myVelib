package src.registrationCard;

import src.coreClasses.User;
import src.enums.TypeOfBicycle;

public class VlibreRegistrationCard implements RegistrationCard {

    public int getTimeCredit(int rideDuration){
            return 60 - (rideDuration % 60);
    }

    /**
     * computes how many hours are charged for this ride
     */
    public int computeChargedHours(int rideDurationInMinutes) {
        return rideDurationInMinutes / 60 + 1;
    }

    /**
     * computes the cost of the ride without timeCredit reduction
     */
    public int getHoursCost(int billedHours, TypeOfBicycle bicycleType){
        if (billedHours == 0) {
            return 0;
        }
        if (bicycleType == TypeOfBicycle.Mechanical) {
            return billedHours - 1;
        } else {
            if (billedHours > 1) {
                return 1 + 2 * (billedHours - 1);
            }
            return 1;
        }
    }

    @Override
    public double computeRideCost(double rideDuration, TypeOfBicycle bicycleType, User user) {
        // compute the timeCredit gained
        int earnedTimeCredit = this.getTimeCredit((int) rideDuration);
        // add it to the user balance
        user.setTimeCreditBalance(user.getTimeCreditBalance() + earnedTimeCredit);

        int billedHours = computeChargedHours((int) rideDuration);

        // if timeCredit > 60 and rideDuration > 60 : get a reduction
        if (user.getTimeCreditBalance() >= 60 && rideDuration > 60) {
            // compute how many hours can be removed from the final cost
            int possibleCreditHours = (int) user.getTimeCreditBalance() / 60;
            // and how many hours above 1 were ridden
            int discountableHours = (int) rideDuration / 60;

            int nbFreeHours = Math.min(possibleCreditHours, discountableHours);
            billedHours -= nbFreeHours;
            //update user balance
            user.setTimeCreditBalance(user.getTimeCreditBalance() - nbFreeHours * 60);
        }

        return getHoursCost(billedHours, bicycleType);
    }

    @Override
    public String toString() {
        return "Vlibre";
    }
}

