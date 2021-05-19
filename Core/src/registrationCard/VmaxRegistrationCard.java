package src.registrationCard;

import src.coreClasses.User;
import src.enums.TypeOfBicycle;

public class VmaxRegistrationCard extends RegistrationCard {
    public VmaxRegistrationCard() {
        super(true, 0, 1, 0, 1);
    }

    @Override
    public String toString() {
        return "Vmax registration card";
    }
}
