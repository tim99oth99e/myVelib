package src.classes;

import src.enums.*;

public class ParkingSlot {

    protected Integer Id;
    protected ParkingSlotStatus parkingSlotStatus;

    private static Integer uniqueId = 0; //Unique numerical ID

    //ToString
    @Override
    public String toString() {
        return "Parking slot number " + Id +
                " is " + parkingSlotStatus;
    }

    //Constructors
    public ParkingSlot(ParkingSlotStatus parkingSlotStatus) {
        Id = uniqueId;
        uniqueId++;
        this.parkingSlotStatus = parkingSlotStatus;
    }

    //Getters and Setters
    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public ParkingSlotStatus getParkingSlotStatus() {
        return parkingSlotStatus;
    }
    public void setParkingSlotStatus(ParkingSlotStatus parkingSlotStatus) {
        this.parkingSlotStatus = parkingSlotStatus;
    }
}
