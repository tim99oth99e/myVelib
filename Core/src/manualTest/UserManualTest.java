package src.manualTest;

import src.coreClasses.User;
import src.registrationCard.*;

public class UserManualTest {
    public static void main(String[] args) throws Exception {
        User user1 = new User("Jack Wilson", 45.0, 145.4, "2353849583740384",
                new NoRegistrationCard());
        System.out.println(User.getUsedIds());
        System.out.println(user1);

        User user2 = new User("Mark Zuckerberg", 12.0, 15.4, "1235384974038425",
                new VmaxRegistrationCard());
        System.out.println(User.getUsedIds());
        System.out.println(user2);
    }
}
