package src.test;

import src.classes.*;
import src.registrationCard.*;
import src.enums.*;

public class UserManualTest {
    public static void main(String[] args) {
        User user1 = new User("Jack Wilson", 123.0, 145.4, "12353849583740384",
                new NoRegistrationCard());
        System.out.println(User.getUsedIds());
        System.out.println(user1);

        User user2 = new User("Mark Zuckerberg", 12.0, 15.4, "12353849sfg740384",
                new VmaxRegistrationCard());
        System.out.println(User.getUsedIds());
        System.out.println(user2);
    }
}
