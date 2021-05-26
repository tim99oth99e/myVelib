package src.coreClasses;

import src.enums.*;

/**
 * This class describes a parking slot.
 */
public class ParkingSlot {

    /**
     * The unique identifier of a parking slot.
     */
    protected Integer Id;
    /**
     * The Parking slot status.
     */
    protected ParkingSlotStatus parkingSlotStatus;
    /**
     * The bicycle eventually parked in the parking slot.
     */
    protected Bicycle bicycle;

    private static Integer uniqueId = 0; //Unique numerical ID

    //ToString
    @Override
    public String toString() {
        return "Parking slot number " + Id +
                " is " + parkingSlotStatus + " with " + bicycle;
    }

    /**
     * Instantiates a new Parking slot.
     *
     * @param parkingSlotStatus the parking slot status : Occupied, Free or OutOfOrder.
     * @param bicycle           the bicycle if one is parked, null otherwise.
     */
    public ParkingSlot(ParkingSlotStatus parkingSlotStatus, Bicycle bicycle) {
        Id = uniqueId;
        uniqueId++;
        this.parkingSlotStatus = parkingSlotStatus;
        if (parkingSlotStatus == ParkingSlotStatus.Occupied){ // One can only instantiates a bicycle to a parking slot if the parking slot is set Occupied
            this.bicycle = bicycle;
        }
        else { // Otherwise the bicycle is set to null
            this.bicycle = null;
        }
    }

    /**
     * Gets id.
     *
     * @return the id
     */
//Getters and Setters
    public Integer getId() {
        return Id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        Id = id;
    }

    /**
     * Gets parking slot status.
     *
     * @return the parking slot status
     */
    public ParkingSlotStatus    getParkingSlotStatus() {
        return parkingSlotStatus;
    }

    /**
     * Sets parking slot status.
     *
     * @param parkingSlotStatus the parking slot status
     */
    public void setParkingSlotStatus(ParkingSlotStatus parkingSlotStatus) {
        this.parkingSlotStatus = parkingSlotStatus;
    }

    /**
     * Gets bicycle.
     *
     * @return the bicycle
     */
    public Bicycle getBicycle() {
        return bicycle;
    }

    /**
     * Sets bicycle.
     *
     * @param bicycle the bicycle
     */
    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }
}
