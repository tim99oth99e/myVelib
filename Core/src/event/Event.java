package src.event;

import src.coreClasses.Station;
import src.enums.EventType;

import java.time.LocalDateTime;

/**
 * The base class describing an event happening on a station.
 */
public class Event {
    /**
     * The date and time of the event
     */
    private LocalDateTime eventTime;
    /**
     * The event type (see enum EventType)
     */
    private EventType eventType;
    /**
     * The station on which the event happened
     */
    private Station station;

    /**
     * Instantiates a new Event.
     *
     * @param eventTime the event date and time
     * @param eventType the event type
     * @param station   the station on which it happened
     */
// Constructor
    public Event(LocalDateTime eventTime, EventType eventType, Station station) {
        this.eventTime = eventTime;
        this.eventType = eventType;
        this.station = station;
    }

    @Override
    public String toString() {
        return "Event of type " + eventType +
                " that took place " + eventTime + " at station #" + station.getId() ;
    }

    // getters & setters

    /**
     * Gets the event datetime.
     *
     * @return the event datetime
     */
    public LocalDateTime getEventTime() {
        return eventTime;
    }

    /**
     * Sets the event datetime.
     *
     * @param eventTime the event datetime
     */
    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * Gets event type.
     *
     * @return the event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Sets event type.
     *
     * @param eventType the event type
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Gets the station on which the event happened.
     *
     * @return the station
     */
    public Station getStation() {
        return station;
    }

    /**
     * Sets the station on which the event happened
     *
     * @param station the station
     */
    public void setStation(Station station) {
        this.station = station;
    }
}
