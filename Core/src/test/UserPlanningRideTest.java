package src.test;

import src.classes.UserPlanningRide;
import src.enums.RegistrationCardType;

public class UserPlanningRideTest {
    public static void main(String[] args) {
        UserPlanningRide userPlanningRide1 = new UserPlanningRide("Jean",76.5, 45.4, "8750852345905",
                RegistrationCardType.Vlibre, false, true, false);

        System.out.println(userPlanningRide1);
    }
}
