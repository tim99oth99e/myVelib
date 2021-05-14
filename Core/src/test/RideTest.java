package src.test;

import org.junit.jupiter.api.Test;
import src.coreClasses.Ride;
import src.coreClasses.Station;
import src.coreClasses.User;
import src.enums.StationStatus;
import src.enums.TypeOfStation;
import src.registrationCard.NoRegistrationCard;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class RideTest {
    // define users
    User user1 = new User("Billy Gates", 0.0, 145.4, "1235384958374038",
            new NoRegistrationCard());
    // define stations
    Station station1 = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Standard);
    Station station2 = new Station(56.5, 17.5, StationStatus.OnService, TypeOfStation.Plus);
    // define dates
    LocalDateTime dateTime1 = LocalDateTime.of(2021, 2,11,8,20,30);
    LocalDateTime dateTime2 = LocalDateTime.of(2021, 2,11,10,20,44);
    // Test rides
    Ride ride1 = new Ride(user1, station1, station2, dateTime1, dateTime2);

    RideTest() throws Exception {
    }

    @Test
    void getDurationInMinutes() {
        assertEquals(120, ride1.getDurationInMinutes());
        System.out.println(ride1);
    }

    @Test
    void setReturnDateTime() throws Exception {
        ride1.setReturnDateTime(dateTime1);
    }
}