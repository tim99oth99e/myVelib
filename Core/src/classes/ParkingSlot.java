package src.classes;

import src.enums.*;

public class ParkingSlot {

    protected Integer Id;
    protected ParkingSlotStatus parkingSlotStatus;
    protected Bicycle bicycle;

    private static Integer uniqueId = 0; //Unique numerical ID

    //ToString
    @Override
    public String toString() {
        return "Parking slot number " + Id +
                " is " + parkingSlotStatus + " with " + bicycle;
    }

    //Constructors
    public ParkingSlot(ParkingSlotStatus parkingSlotStatus, Bicycle bicycle) {
        Id = uniqueId;
        uniqueId++;
        this.parkingSlotStatus = parkingSlotStatus;
        if (parkingSlotStatus == ParkingSlotStatus.Occupied){
            this.bicycle = bicycle;
        }
        else {
            this.bicycle = null;
        }
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
    public Bicycle getBicycle() {
        return bicycle;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }
}
