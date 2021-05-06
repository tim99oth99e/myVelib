package src.test;

import src.classes.*;
import src.enums.*;

public class BicycleManualTest {
    public static void main(String[] args) {
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
        System.out.println(mechanicalBicycle);

        Bicycle electricalBicycle = new Bicycle(TypeOfBicycle.Electrical);
        System.out.println(electricalBicycle);
    }
}
