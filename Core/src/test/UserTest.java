package src.test;

import src.classes.User;
import src.enums.RegistrationCardType;

public class UserTest {
    public static void main(String[] args) {
        User user1 = new User("Jack", 123.0, 145.4, "12353849583740384",
                RegistrationCardType.None);
        System.out.println(User.getUsedIds());
        System.out.println(user1);
    }
}
