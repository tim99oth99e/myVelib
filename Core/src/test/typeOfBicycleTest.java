package src.test;

import src.classes.TypeOfBicycle;

public class typeOfBicycleTest {
    public static void main(String[] args) {
        TypeOfBicycle mechanical1 = new TypeOfBicycle("Mechanical");
        System.out.println(mechanical1);

        TypeOfBicycle mechanical2 = new TypeOfBicycle("meChaniCAl");
        System.out.println(mechanical2);

        TypeOfBicycle electrical1 = new TypeOfBicycle("Electrical");
        System.out.println(electrical1);

        TypeOfBicycle electrical2 = new TypeOfBicycle("electrical");
        System.out.println(electrical2);

        TypeOfBicycle nothing = new TypeOfBicycle("ashjlfasd");
        System.out.println(nothing);

    }
}
