package src.event;

import src.coreClasses.Station;
import src.enums.EventType;

import java.time.LocalDateTime;

public class Event {
    private LocalDateTime eventTime;
    private EventType eventType;
    private Station station;

    // Constructor
    public Event(LocalDateTime eventTime, EventType eventType, Station station) {
        this.eventTime = eventTime;
        this.eventType = eventType;
        this.station = station;
    }

    // getters & setters

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
