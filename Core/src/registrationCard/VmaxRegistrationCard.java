package src.registrationCard;

import src.coreClasses.User;
import src.enums.TypeOfBicycle;

/**
 * This class describes a Vmax registration card.
 */
public class VmaxRegistrationCard extends RegistrationCard {
    /**
     * Instantiates a new Vmax registration card.
     */
    public VmaxRegistrationCard() {
        super(true, 0, 1, 0, 1);
    }

    @Override
    public String toString() {
        return "Vmax registration card";
    }
}
