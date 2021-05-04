package src.classes;

import src.enums.RegistrationCardType;

public class RegistrationCard {
    private String registrationCardNumber;
    private RegistrationCardType type;

    public RegistrationCard(String registrationCardNumber, RegistrationCardType type) {
        this.registrationCardNumber = registrationCardNumber;
        this.type = type;
    }

    // getters
    public String getRegistrationCardNumber() {
        return registrationCardNumber;
    }

    public RegistrationCardType getType() {
        return type;
    }

    // setters
    public void setRegistrationCardNumber(String registrationCardNumber) {
        this.registrationCardNumber = registrationCardNumber;
    }

    public void setType(RegistrationCardType type) {
        this.type = type;
    }
}
