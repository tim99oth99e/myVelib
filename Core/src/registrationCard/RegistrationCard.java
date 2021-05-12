package src.registrationCard;

import src.coreClasses.User;
import src.enums.TypeOfBicycle;

public interface RegistrationCard {
    double computeRideCost(double rideDuration, TypeOfBicycle bicycleType, User user);
}
