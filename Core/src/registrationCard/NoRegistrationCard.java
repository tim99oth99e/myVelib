package src.registrationCard;

import src.coreClasses.User;
import src.enums.TypeOfBicycle;

/**
 * This class deals with a user having no registration card.
 */
public class NoRegistrationCard extends RegistrationCard{

    /**
     * Instantiates a new No registration card.
     */
    public NoRegistrationCard() {
        super(false, 1, 1, 2, 2);
    }

    @Override
    public String toString() {
        return "No registration card";
    }
}

