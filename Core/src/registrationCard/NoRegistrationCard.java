package src.registrationCard;

import src.classes.User;
import src.enums.TypeOfBicycle;

public class NoRegistrationCard implements RegistrationCard{

    @Override
    public double computeRideCost(double rideDuration, TypeOfBicycle bicycleType, User user) {
        double cost = ((int) (rideDuration/60)) + 1; // when you start a new hour, you pay for it
        // ride duration is in minutes
        if (bicycleType == TypeOfBicycle.Electrical) {
            cost *= 2;
        }
        return cost;
    }

}
