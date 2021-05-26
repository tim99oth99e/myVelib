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

import java.time.LocalDateTime;

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
        assertThrows(Exception.class, () -> user2.setId(16));
        assertNotEquals(16, user2.getId()); // users cannot have the same id
        assertEquals(16, user1.getId());
    }

    @Test
    void setRegistrationCard() {
        user2.setRegistrationCard(new NoRegistrationCard()); // assert everything else is deleted
    }

    @Test
    void rent() {
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
        Bicycle mechanicalBicycle2 = new Bicycle(TypeOfBicycle.Mechanical);

        ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupiedMechanical = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedMechanical2 = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle2);

        Station station1 = new Station(5.43, 0.4, StationStatus.OnService, TypeOfStation.Standard);
        station1.addParkingSlot(parkingSlotFree);
        station1.addParkingSlot(parkingSlotOccupiedMechanical);

        user1.rent(parkingSlotOccupiedMechanical); // user1 rents the mechanical bicycle available on parkingSlotOccupiedMechanical.
        user1.rent(parkingSlotOccupiedMechanical2); // user1 cannot rent the mechanical bicycle 2 available on parkingSlotOccupiedMechanical.
        user2.rent(parkingSlotFree); // user2 cannot rent on parkingSlotFree because the parking slot is free.


        assertAll(
                () -> assertTrue(user1.getRentedBicycle() == mechanicalBicycle), // assert user1 has the mechanical bicycle.
                () -> assertTrue(user2.getRentedBicycle() == null), // assert user2 has no bicycle.
                () -> assertTrue(parkingSlotOccupiedMechanical.getParkingSlotStatus() == ParkingSlotStatus.Free), // assert the parking slot is now free.
                () -> assertTrue(parkingSlotOccupiedMechanical.getBicycle() == null), // assert the parking slot is now empty.
                () -> assertTrue(parkingSlotFree.getParkingSlotStatus() == ParkingSlotStatus.Free), // assert the parking slot remains free.
                () -> assertTrue(parkingSlotFree.getBicycle() == null) // assert the parking slot remains empty.
        );
    }

    @Test
    void park() {
        Bicycle mechanicalBicycle = new Bicycle(TypeOfBicycle.Mechanical);
        Bicycle mechanicalBicycle2 = new Bicycle(TypeOfBicycle.Mechanical);

        // Start station
        ParkingSlot parkingSlotFree = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupiedMechanical = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle);
        ParkingSlot parkingSlotOccupiedMechanical2 = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle2);

        Station station1 = new Station(5.43, 0.4, StationStatus.OnService, TypeOfStation.Standard);
        station1.addParkingSlot(parkingSlotFree);
        station1.addParkingSlot(parkingSlotOccupiedMechanical);

        user1.rent(parkingSlotOccupiedMechanical); // user1 rents the mechanical bicycle available on parkingSlotOccupiedMechanical.
        user2.rent(parkingSlotOccupiedMechanical2); // user2 rents the mechanical bicycle 2 available on parkingSlotOccupiedMechanical2.

        // Park station
        ParkingSlot parkingSlotFree2 = new ParkingSlot(ParkingSlotStatus.Free, null);
        ParkingSlot parkingSlotOccupiedMechanical3 = new ParkingSlot(ParkingSlotStatus.Occupied, mechanicalBicycle2);

        Station station2 = new Station(5.55, 0.401, StationStatus.OnService, TypeOfStation.Standard);
        station2.addParkingSlot(parkingSlotFree2);
        station2.addParkingSlot(parkingSlotOccupiedMechanical3);

        user1.park(parkingSlotFree2, LocalDateTime.now().plusMinutes(25)); // user1 parks his bicycle on a free parking slot 25 minutes later.
        user2.park(parkingSlotFree2, LocalDateTime.now().plusMinutes(145)); // user2 tries to park his bicycle on an occupied parking slot 145 minutes later.

        assertTrue(user1.getRentedBicycle() == null); // assert user1 has no more bicycle.
        assertTrue(parkingSlotFree2.getParkingSlotStatus() == ParkingSlotStatus.Occupied); // assert the parking slot is now Occupied.
        assertTrue(parkingSlotFree2.getBicycle() == mechanicalBicycle); // assert the parking slot is occupied with mechanicalBicycle.
        assertTrue(user2.getRentedBicycle() == mechanicalBicycle2); // assert user2 still has his bicycle.
        assertTrue(user1.getTotalCharges() == 1.0); // assert user1 has been charged for his ride.
        assertTrue(user2.getTotalCharges() == 0.0); // assert user2 has not been charged since his ride is not finished.
    }

}