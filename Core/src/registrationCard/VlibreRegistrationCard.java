package src.registrationCard;

import src.coreClasses.User;
import src.enums.TypeOfBicycle;

/**
 * This class describes a Vlibre registration card.
 */
public class VlibreRegistrationCard extends RegistrationCard {

    /**
     * Instantiates a new Vlibre registration card.
     */
    public VlibreRegistrationCard() {
        super(true, 0, 1,1,2);
    }

    @Override
    public String toString() {
        return "Vlibre registration card";
    }

}

