package src.registrationCard;

import src.coreClasses.User;
import src.enums.TypeOfBicycle;

public class VlibreRegistrationCard implements RegistrationCard {
    @Override
    public double computeRideCost(double rideDuration, TypeOfBicycle bicycleType, User user) {
        double rideDurationToPay = rideDuration;
        double cost;
        double timeCreditBalance = user.getTimeCreditBalance();

        // compute time-credit reduction
        if (timeCreditBalance > 0.0 && rideDuration > 60.0) {
            double minutesAfter1h = rideDuration - 60.0;
            if (timeCreditBalance > minutesAfter1h) {
                rideDurationToPay =  60.0;
                // update timeCredit
                timeCreditBalance -= minutesAfter1h;
            } else {
                // time credit doesn't cover everything
                rideDurationToPay = 60.0 + (minutesAfter1h - timeCreditBalance);
                timeCreditBalance = 0;
            }
        }
        // update user's time credit balance
        user.setTimeCreditBalance(timeCreditBalance);

        // compute base cost
        if (bicycleType == TypeOfBicycle.Mechanical) {
            cost = (int) (rideDurationToPay/60);
        }   else {
            cost = 1;
            if (rideDurationToPay > 60.0) {
                cost += 2 * (int) (rideDurationToPay / 60);
            }
        }

        return cost;
    }
}

