package src.test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import src.coreClasses.Bicycle;
import src.enums.TypeOfBicycle;

class BicycleTest {

    Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
    Bicycle electricalBicycle = new Bicycle(TypeOfBicycle.Electrical);

    @Test
    @DisplayName("Test the type of bicycles")
    public void testTypeOfBicycle() {


        assertAll("Type",
                () -> assertTrue(mechanicalBicycle.getType() == TypeOfBicycle.Mechanical),
                () -> assertTrue(electricalBicycle.getType() == TypeOfBicycle.Electrical)
        );
    }

    @Test
    @DisplayName("Test unique ID for bicycles")
    public void testUniqueID() {
        Bicycle mechanicalBicycle1 = new Bicycle(TypeOfBicycle.Electrical);
        Bicycle mechanicalBicycle2 = new Bicycle(TypeOfBicycle.Electrical);
        Bicycle electricalBicycle = new Bicycle(TypeOfBicycle.Electrical);

        assertAll("unique ID",
                () -> assertTrue(mechanicalBicycle1.getId() != mechanicalBicycle2.getId()),
                () -> assertTrue(mechanicalBicycle2.getId() != electricalBicycle.getId())
        );
    }
}