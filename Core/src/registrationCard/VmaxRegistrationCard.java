package src.registrationCard;

import src.coreClasses.User;
import src.enums.TypeOfBicycle;

public class VmaxRegistrationCard implements RegistrationCard{

    @Override
    public double computeRideCost(double rideDuration, TypeOfBicycle bicycleType, User user) {
        int cost = 0;
        if (rideDuration > 60.0) {
            // rounds at the lower integer
            cost = (int) rideDuration;
        }
        return cost;
    }
}
