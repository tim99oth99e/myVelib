package src.test;

import org.junit.jupiter.api.Test;
import src.coreClasses.User;
import src.enums.TypeOfBicycle;
import src.registrationCard.NoRegistrationCard;
import src.registrationCard.RegistrationCard;
import src.registrationCard.VlibreRegistrationCard;
import src.registrationCard.VmaxRegistrationCard;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationCardTest {
    RegistrationCard noCard = new NoRegistrationCard();
    RegistrationCard regCard1 = new VlibreRegistrationCard();
    RegistrationCard vMaxCard = new VmaxRegistrationCard();

    User noCardMarcus = new User("Marcus Zuckerberg", 22.0, 100.4, "3538493204740384",
            new NoRegistrationCard());

    User vlibreLarry = new User("Larry Pages", 22.0, 100.4, "3538493204740384",
            new VlibreRegistrationCard());

    User vMaxBilly = new User("Billy Gates", 22.0, 100.4, "3538493204740384",
            new VmaxRegistrationCard());

    RegistrationCardTest() throws Exception {
    }


    @Test
    void getTimeCredit(){
        assertEquals(27, regCard1.getTimeCredit(33));
        assertEquals(54, regCard1.getTimeCredit(126));
        // 60 mins of credit for the next hour
        assertEquals(60, regCard1.getTimeCredit(60));
    }

    @Test
    void getHoursCost() {
        assertEquals(0, regCard1.getHoursCost(1, TypeOfBicycle.Mechanical));
        assertEquals(2, regCard1.getHoursCost(3, TypeOfBicycle.Mechanical));
        assertEquals(3, regCard1.getHoursCost(2, TypeOfBicycle.Electrical));
        // one with 120 min
    }

    @Test
    void getFare() {
        assertEquals(0, vMaxCard.getFirstHourFareMechanical());
        assertEquals(0, vMaxCard.getFirstHourFareElectrical());
        assertEquals(1, vMaxCard.getNextHoursFareMechanical());
        assertEquals(1, vMaxCard.getNextHoursFareElectrical());
    }


    @Test
    void computeRideCost() {

        // No Registration
        assertEquals(0, noCard.computeRideCost(0, TypeOfBicycle.Mechanical, noCardMarcus));
        assertEquals(2, noCard.computeRideCost(60, TypeOfBicycle.Mechanical, noCardMarcus));
        assertEquals(4, noCard.computeRideCost(60, TypeOfBicycle.Electrical, noCardMarcus));
        assertEquals(3, noCard.computeRideCost(130, TypeOfBicycle.Mechanical, noCardMarcus));
        assertEquals(6, noCard.computeRideCost(130, TypeOfBicycle.Electrical, noCardMarcus));


        // Vlibre
        vlibreLarry.setTimeCreditBalance(50);
        assertEquals(1, regCard1.computeRideCost(130, TypeOfBicycle.Mechanical, vlibreLarry));
        assertEquals(40, vlibreLarry.getTimeCreditBalance());

        vlibreLarry.setTimeCreditBalance(70);
        assertEquals(0, regCard1.computeRideCost(130, TypeOfBicycle.Mechanical, vlibreLarry));
        assertEquals(0, vlibreLarry.getTimeCreditBalance());

        vlibreLarry.setTimeCreditBalance(0);
        // 3h billed, 1h free
        assertEquals(1, regCard1.computeRideCost(120, TypeOfBicycle.Mechanical, vlibreLarry));

        vlibreLarry.setTimeCreditBalance(0);
        assertEquals(1, regCard1.computeRideCost(60, TypeOfBicycle.Electrical, vlibreLarry));

        // Vmax (credit balance = 0)
        assertEquals(0, vMaxCard.computeRideCost(0, TypeOfBicycle.Mechanical, vMaxBilly));
        assertEquals(0, vMaxCard.computeRideCost(60, TypeOfBicycle.Mechanical, vMaxBilly));
        assertEquals(0, vMaxCard.computeRideCost(60, TypeOfBicycle.Electrical, vMaxBilly));
        assertEquals(2, vMaxCard.computeRideCost(130, TypeOfBicycle.Mechanical, vMaxBilly));
        vMaxBilly.setTimeCreditBalance(0);
        assertEquals(2, vMaxCard.computeRideCost(130, TypeOfBicycle.Electrical, vMaxBilly));
    }
}