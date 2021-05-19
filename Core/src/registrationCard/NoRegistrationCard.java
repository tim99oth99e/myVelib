package src.registrationCard;

import src.coreClasses.User;
import src.enums.TypeOfBicycle;

public class NoRegistrationCard extends RegistrationCard{

    public NoRegistrationCard() {
        super(false, 1, 1, 2, 2);
    }

    @Override
    public String toString() {
        return "No registration card";
    }
}

