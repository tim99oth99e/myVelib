package src.test;

import src.classes.User;
import src.enums.RegistrationCardType;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user1 = new User("Billy Gates", 123.0, 145.4, "12353849583740384",
            RegistrationCardType.None);
    User user2 = new User("Marcus Zuckerberg", 12.0, 15.4, "12353849sfg740384",
            RegistrationCardType.Vmax);

    @org.junit.jupiter.api.Test
    void setId() throws Exception {
        user2.setId(0);
        user1.setId(16);
        assertEquals(1, user2.getId()); // users cannot have the same id
        assertEquals(16, user1.getId());
    }
}