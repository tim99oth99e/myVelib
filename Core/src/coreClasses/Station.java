package src.coreClasses;

import src.enums.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Station {

    protected Integer Id;
    protected Double latitude;
    protected Double longitude;
    protected StationStatus stationStatus;
    protected TypeOfStation typeOfStation;
    protected HashMap<Integer,ParkingSlot> parkingSlotHashMap;

    private static Integer uniqueId = 0; //Unique numerical ID

    //ToString
    public String displayHashMap(HashMap<Integer,ParkingSlot> parkingSlotHashMap){
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
                "Parking Slots : " + displayHashMap(parkingSlotHashMap) ;

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
    public void addParkingSlot(ParkingSlot parkingSlot){
       this.parkingSlotHashMap.put(parkingSlot.getId(),parkingSlot);
    }

    public Double computeDistance(Double currentLatitude, Double currentLongitude){
        Double lat_a = this.latitude;
        Double lng_a = this.longitude;
        Double lat_b = currentLatitude;
        Double lng_b = currentLongitude;

        Double pk =  180/3.14169;

        Double a1 = lat_a / pk;
        Double a2 = lng_a / pk;
        Double b1 = lat_b / pk;
        Double b2 = lng_b / pk;

        Double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
        Double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
        Double t3 = Math.sin(a1)*Math.sin(b1);
        Double tt = Math.acos(t1 + t2 + t3);

        return 6366000*tt;
    }

    public Boolean hasBike(TypeOfBicycle typeOfBicycle){
        Iterator it = parkingSlotHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer,ParkingSlot> entry = (Map.Entry)it.next();
            //System.out.println(entry.getKey() + " = " + entry.getValue());
            if (entry.getValue().getParkingSlotStatus() == ParkingSlotStatus.Occupied
            && entry.getValue().getBicycle().getType() == typeOfBicycle){
                return true;
            }
        }
        return false;
    }

    public Boolean hasFreeParkingSlot(){
        Iterator it = parkingSlotHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer,ParkingSlot> entry = (Map.Entry)it.next();
            //System.out.println(entry.getKey() + " = " + entry.getValue());
            if (entry.getValue().getParkingSlotStatus() == ParkingSlotStatus.Free){
                return true;
            }
        }
        return false;
    }

    public Integer getNumberOfFreeParkingSlot(){
        Integer numberOfFreeParkingSlot=0;
        Iterator it = parkingSlotHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer,ParkingSlot> entry = (Map.Entry)it.next();
            //System.out.println(entry.getKey() + " = " + entry.getValue());
            if (entry.getValue().getParkingSlotStatus() == ParkingSlotStatus.Free){
                numberOfFreeParkingSlot++;
            }
        }
        return numberOfFreeParkingSlot;
    }

    public Integer getNumberOfBike(TypeOfBicycle typeOfBicycle){
        Integer numberOfBike=0;
        Iterator it = parkingSlotHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer,ParkingSlot> entry = (Map.Entry)it.next();
            if (entry.getValue().getParkingSlotStatus() == ParkingSlotStatus.Occupied){
                if (entry.getValue().getBicycle().getType() == typeOfBicycle){
                    numberOfBike++;
                }
            }
        }
        return numberOfBike;
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
