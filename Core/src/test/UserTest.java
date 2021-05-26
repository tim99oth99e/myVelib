package src.test;

import src.coreClasses.Bicycle;
import src.coreClasses.ParkingSlot;
import src.coreClasses.Station;
import src.coreClasses.User;
import src.enums.ParkingSlotStatus;
import src.enums.StationStatus;
import src.enums.TypeOfBicycle;
import src.enums.TypeOfStation;
import src.registrationCard.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UserTest {
    User user1 = new User("Billy Gates", 0.0, 145.4, "1235384958374038",
            new NoRegistrationCard());
    User user2 = new User("Marcus Zuckerberg", 12.0, 15.4, "1235384939027403",
            new VlibreRegistrationCard());
    User user3 = new User("Larri Pages", 22.0, 100.4, "3538493204740384",
            new VmaxRegistrationCard());

    UserTest() throws Exception {
    }

    @Test
    void setLatitude() {
        assertThrows(Exception.class, () -> user1.setLatitude(-170.0));
        assertThrows(Exception.class, () -> user2.setLatitude(210.0));
    }

    @Test
    void setLongitude() {
        assertThrows(Exception.class, () -> user1.setLongitude(-193.0));
        assertThrows(Exception.class, () -> user2.setLongitude(345.0));
    }

    @Test
    void setCreditCardNumber() {
        assertThrows(Exception.class, () -> user1.setCreditCardNumber("abcdefghijklmnop"));
        assertThrows(Exception.class, () -> user2.setCreditCardNumber("392"));
    }

    @Test
    void setId() throws Exception {
//        assertThrows(new Exception(), user2.setId(0));
        user1.setId(16);
        assertEquals(1, user2.getId()); // users cannot have the same id
        assertEquals(16, user1.getId());
    }

    @Test
    void setRegistrationCard() {
        user2.setRegistrationCard(new NoRegistrationCard()); // assert everything else is deleted
    }

    @Test
    void rent() {
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
        ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupiedMechanical = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);

        Station station1 = new Station(5.43, 0.4, StationStatus.OnService, TypeOfStation.Standard);
        station1.addParkingSlot(parkingSlotFree);
        station1.addParkingSlot(parkingSlotOccupiedMechanical);

        user1.rent(parkingSlotOccupiedMechanical); // user1 rents the mechanical bicycle available on parkingSlotOccupiedMechanical.
        user2.rent(parkingSlotFree); // user2 cannot rent on parkingSlotFree because the parking slot is free.

        assertAll(
                () -> assertTrue(user1.getRentedBicycle() == mechanicalBicycle), // assert user1 has the mechanical bicycle
                () -> assertTrue(user2.getRentedBicycle() == null), // assert user2 has no bicycle
                () -> assertTrue(parkingSlotOccupiedMechanical.getParkingSlotStatus() == ParkingSlotStatus.Free), // assert the parking slot is now free
                () -> assertTrue(parkingSlotOccupiedMechanical.getBicycle() == null), // assert the parking slot is now empty
                () -> assertTrue(parkingSlotFree.getParkingSlotStatus() == ParkingSlotStatus.Free), // assert the parking slot remains free
                () -> assertTrue(parkingSlotFree.getBicycle() == null) // assert the parking slot remains empty
        );
    }

}