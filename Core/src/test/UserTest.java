package src.test;

import src.coreClasses.User;
import src.enums.TypeOfBicycle;
import src.registrationCard.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UserTest {
    User user1 = new User("Billy Gates", 0.0, 145.4, "1235384958374038",
            new NoRegistrationCard());
    User user2 = new User("Marcus Zuckerberg", 12.0, 15.4, "1235384939027403",
            new VlibreRegistrationCard());
    User user3 = new User("Larri Pages", 22.0, 100.4, "3538493204740384",
            new VmaxRegistrationCard());

    UserTest() throws Exception {
    }

    @Test
    void setLatitude() {
        assertThrows(Exception.class, () -> user1.setLatitude(-170.0));
        assertThrows(Exception.class, () -> user2.setLatitude(210.0));
    }

    @Test
    void setLongitude() {
        assertThrows(Exception.class, () -> user1.setLongitude(-193.0));
        assertThrows(Exception.class, () -> user2.setLongitude(345.0));
    }

    @Test
    void setCreditCardNumber() {
        assertThrows(Exception.class, () -> user1.setCreditCardNumber("abcdefghijklmnop"));
        assertThrows(Exception.class, () -> user2.setCreditCardNumber("392"));
    }

    @Test
    void setId() throws Exception {
//        assertThrows(new Exception(), user2.setId(0));
        user1.setId(16);
        assertEquals(1, user2.getId()); // users cannot have the same id
        assertEquals(16, user1.getId());
    }

    @Test
    void setRegistrationCard() {
        user2.setRegistrationCard(new NoRegistrationCard()); // assert everything else is deleted
    }

}