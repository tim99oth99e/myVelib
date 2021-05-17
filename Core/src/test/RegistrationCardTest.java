package src.test;

import org.junit.jupiter.api.Test;
import src.coreClasses.User;
import src.enums.TypeOfBicycle;
import src.registrationCard.RegistrationCard;
import src.registrationCard.VlibreRegistrationCard;
import src.registrationCard.VmaxRegistrationCard;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationCardTest {
    VlibreRegistrationCard regCard1 = new VlibreRegistrationCard();
    User larry = new User("Larry Pages", 22.0, 100.4, "3538493204740384",
            new VlibreRegistrationCard());

    RegistrationCardTest() throws Exception {
    }

    @Test
    void getTimeCredit(){
        assertEquals(27, regCard1.getTimeCredit(33));
        assertEquals(54, regCard1.getTimeCredit(126));
        assertEquals(0, regCard1.getTimeCredit(60));
    }

    @Test
    void computeChargedHours() {
        assertEquals(1, regCard1.computeChargedHours(33));
        assertEquals(3 , regCard1.computeChargedHours(125));
    }

    @Test
    void getHoursCost() {
        assertEquals(0, regCard1.getHoursCost(1, TypeOfBicycle.Mechanical));
        assertEquals(2, regCard1.getHoursCost(3, TypeOfBicycle.Mechanical));
        assertEquals(3, regCard1.getHoursCost(2, TypeOfBicycle.Electrical));
        // one with 120 min
    }

    @Test
    void computeRideCost() {

        larry.setTimeCreditBalance(50);
        assertEquals(1, regCard1.computeRideCost(130, TypeOfBicycle.Mechanical, larry));
        assertEquals(40, larry.getTimeCreditBalance());

        larry.setTimeCreditBalance(70);
        assertEquals(0, regCard1.computeRideCost(130, TypeOfBicycle.Mechanical, larry));
        assertEquals(0, larry.getTimeCreditBalance());

        larry.setTimeCreditBalance(0);
        // 3h billed, 1h free
        assertEquals(1, regCard1.computeRideCost(120, TypeOfBicycle.Mechanical, larry));
    }
}