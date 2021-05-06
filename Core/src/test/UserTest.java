package src.test;

import src.classes.*;
import src.enums.*;

public class UserTest {
    public static void main(String[] args) {
        User user1 = new User("Jack", 123.0, 145.4, "12353849583740384",
                RegistrationCardType.None);
        System.out.println(user1.getUsedIds());
        System.out.println(user1);

        User user2 = new User("Mark", 12.0, 15.4, "12353849sfg740384",
                RegistrationCardType.None);
        System.out.println(user2.getUsedIds());
        System.out.println(user2);
    }
}
