package src.coreClasses;

import src.enums.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class describes a station.
 */
public class Station {

    /**
     * The Id.
     */
    protected Integer Id;
    /**
     * The latitude of the station.
     */
    protected Double latitude;
    /**
     * The longitude of the station.
     */
    protected Double longitude;
    /**
     * The status of the station.
     */
    protected StationStatus stationStatus;
    /**
     * The type of station.
     */
    protected TypeOfStation typeOfStation;
    /**
     * The HashMap that contains the parking slot(s) within the station.
     */
    protected HashMap<Integer,ParkingSlot> parkingSlotHashMap;

    private static Integer uniqueId = 0; //Unique numerical ID

    /**
     * Display the HashMap that contains the parking slot(s) properly.
     *
     * @param parkingSlotHashMap the parking slot HashMap
     * @return the well-formed string
     */
    //ToString
    public String displayHashMap(HashMap<Integer,ParkingSlot> parkingSlotHashMap){
        String string = "";
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

    /**
     * Instantiates a new station.
     *
     * @param latitude      the latitude of the station
     * @param longitude     the longitude of the station
     * @param stationStatus the status of the station : on service or offline
     * @param typeOfStation the type of station : plus or standard
     */
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
    /**
     * Add a parking slot to a station.
     *
     * @param parkingSlot the parking slot
     */
    public void addParkingSlot(ParkingSlot parkingSlot){
       this.parkingSlotHashMap.put(parkingSlot.getId(),parkingSlot);
    }

    /**
     * Compute the distance between the station and the current position.
     *
     * @param currentLatitude  the current latitude
     * @param currentLongitude the current longitude
     * @return the distance in meters
     */
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

    /**
     * Check if te station has a bike of the given type available.
     *
     * @param typeOfBicycle the type of bicycle
     * @return true if the station has a bike of the given type available, false otherwise
     */
    public Boolean hasBike(TypeOfBicycle typeOfBicycle){
        Iterator it = parkingSlotHashMap.entrySet().iterator(); // Parsing the HashMap composed of parking slot(s)
        while (it.hasNext()) {
            Map.Entry<Integer,ParkingSlot> entry = (Map.Entry)it.next();
            if (entry.getValue().getParkingSlotStatus() == ParkingSlotStatus.Occupied // If the parking slot is occupied with the given type of bicycle
            && entry.getValue().getBicycle().getType() == typeOfBicycle){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if te station has a free parking slot available.
     *
     * @return true if the station has a free parking slot available, false otherwise
     */
    public Boolean hasFreeParkingSlot(){
        Iterator it = parkingSlotHashMap.entrySet().iterator(); // Parsing the HashMap composed of parking slot(s)
        while (it.hasNext()) {
            Map.Entry<Integer,ParkingSlot> entry = (Map.Entry)it.next();
            if (entry.getValue().getParkingSlotStatus() == ParkingSlotStatus.Free){ // If the parking slot is free
                return true;
            }
        }
        return false;
    }

    /**
     * Get the total number of parking slots within the station.
     *
     * @return the total number of parking slots
     */
    public int getNumberOfParkingSlots(){
        return this.parkingSlotHashMap.size();
    }

    /**
     * Get the number of free parking slot within the station.
     *
     * @return the number of free parking slot
     */
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

    /**
     * Get the number of bicycle with the given type available.
     *
     * @param typeOfBicycle the type of bicycle
     * @return the number of bicycle with the given type available
     */
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
    /**
     * Gets id.
     *
     * @return the id
     */
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
     * Gets latitude.
     *
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets station status.
     *
     * @return the station status
     */
    public StationStatus getStationStatus() {
        return stationStatus;
    }

    /**
     * Sets station status.
     *
     * @param stationStatus the station status
     */
    public void setStationStatus(StationStatus stationStatus) {
        this.stationStatus = stationStatus;
    }

    /**
     * Gets type of station.
     *
     * @return the type of station
     */
    public TypeOfStation getTypeOfStation() {
        return typeOfStation;
    }

    /**
     * Sets type of station.
     *
     * @param typeOfStation the type of station
     */
    public void setTypeOfStation(TypeOfStation typeOfStation) {
        this.typeOfStation = typeOfStation;
    }

    /**
     * Gets parking slot hash map.
     *
     * @return the parking slot hash map
     */
    public HashMap<Integer, ParkingSlot> getParkingSlotHashMap() {
        return parkingSlotHashMap;
    }

    /**
     * Sets parking slot hash map.
     *
     * @param parkingSlotHashMap the parking slot hash map
     */
    public void setParkingSlotHashMap(HashMap<Integer, ParkingSlot> parkingSlotHashMap) {
        this.parkingSlotHashMap = parkingSlotHashMap;
    }
}
