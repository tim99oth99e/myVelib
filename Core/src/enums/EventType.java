package src.enums;

public enum EventType {
    RentBicycle,
    ReturnBicycle,

    SlotTurnsOutOfOrder, // if a Slot is out-of-order
    SlotRepairedToFree, // repaired
    SlotRepairedToOccupied, // repaired

    StationTurnsOffline,
    StationTurnsOnline
}
