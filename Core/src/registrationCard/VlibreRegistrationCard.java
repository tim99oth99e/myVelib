package src.registrationCard;

import src.coreClasses.User;
import src.enums.TypeOfBicycle;

public class VlibreRegistrationCard extends RegistrationCard {

    public VlibreRegistrationCard() {
        super(true, 0, 1,1,2);
    }

    @Override
    public String toString() {
        return "Vlibre registration card";
    }

}

