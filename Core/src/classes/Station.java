package src.classes;

import src.enums.*;

import java.util.HashMap;


public class Station {

    protected Integer Id;
    protected Double latitude;
    protected Double longitude;
    protected StationStatus stationStatus;
    protected TypeOfStation typeOfStation;
    protected HashMap<Integer,ParkingSlot> parkingSlotHashMap;

    private static Integer uniqueId = 0; //Unique numerical ID

    //ToString
    public String DisplayHashMap(HashMap<Integer,ParkingSlot> parkingSlotHashMap){
        String string = new String();
        for (Integer keys : parkingSlotHashMap.keySet()) {
            string+= "\n" + parkingSlotHashMap.get(keys);
        }
        return string;
    }

    @Override
    public String toString() {
        return "Station number "+ Id + " located at "+
                 latitude + " latitude, " +
                 longitude + " longitude is " + stationStatus +
                ". Type of station : " + typeOfStation + "\n" +
                "Parking Slots : " + DisplayHashMap(parkingSlotHashMap) ;

    }

    //Constructors
    public Station(Double latitude, Double longitude, StationStatus stationStatus,
                   TypeOfStation typeOfStation) {
        Id = uniqueId;
        uniqueId++;

        this.latitude = latitude;
        this.longitude = longitude;
        this.stationStatus = stationStatus;
        this.typeOfStation = typeOfStation;
        this.parkingSlotHashMap = new HashMap<Integer,ParkingSlot>();
    }

    //Methods
    public void AddParkingSlot(ParkingSlot parkingSlot){
       this.parkingSlotHashMap.put(parkingSlot.getId(),parkingSlot);
    }

    //Getters and Setters
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public StationStatus getStationStatus() {
        return stationStatus;
    }

    public void setStationStatus(StationStatus stationStatus) {
        this.stationStatus = stationStatus;
    }

    public TypeOfStation getTypeOfStation() {
        return typeOfStation;
    }

    public void setTypeOfStation(TypeOfStation typeOfStation) {
        this.typeOfStation = typeOfStation;
    }

    public HashMap<Integer, ParkingSlot> getParkingSlotHashMap() {
        return parkingSlotHashMap;
    }

    public void setParkingSlotHashMap(HashMap<Integer, ParkingSlot> parkingSlotHashMap) {
        this.parkingSlotHashMap = parkingSlotHashMap;
    }
}
