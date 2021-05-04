package src.test;

import src.classes.Bicycle;
import src.enums.TypeOfBicycle;

public class BicycleTest {
    public static void main(String[] args) {
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
        System.out.println(mechanicalBicycle);

        Bicycle electricalBicycle = new Bicycle(TypeOfBicycle.Electrical);
        System.out.println(electricalBicycle);
    }
}
