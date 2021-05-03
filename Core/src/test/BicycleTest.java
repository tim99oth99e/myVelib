package src.test;

import src.classes.Bicycle;
import src.classes.TypeOfBicycle;

public class BicycleTest {
    public static void main(String[] args) {
        Bicycle mechanicalBicycle = new Bicycle(new TypeOfBicycle("Mechanical"));
        System.out.println(mechanicalBicycle);

        Bicycle electricalBicycle = new Bicycle(new TypeOfBicycle("electrical"));
        System.out.println(electricalBicycle);

        Bicycle nothingBicycle = new Bicycle(new TypeOfBicycle("djfhadsjklf"));
        System.out.println(nothingBicycle);
    }
}
