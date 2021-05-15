package src.test;

import org.junit.jupiter.api.Test;
import src.coreClasses.Record;
import src.coreClasses.Ride;
import src.coreClasses.Station;
import src.coreClasses.User;
import src.enums.StationStatus;
import src.enums.TypeOfBicycle;
import src.enums.TypeOfStation;
import src.registrationCard.NoRegistrationCard;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RecordTest {
    Record record1 = new Record();

    @Test
    void addRideIfNotExists() throws Exception {
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
        Ride ride1 = new Ride(user1, station1, station2, dateTime1, dateTime2, TypeOfBicycle.Mechanical);
        record1.addRideIfNotExists(ride1);
        record1.getUserStatistics(user1);
//        System.out.println(record1);
    }
}