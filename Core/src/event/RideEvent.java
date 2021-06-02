package src.event;

import src.coreClasses.*;
import src.enums.EventType;

import java.time.LocalDateTime;

/**
 * This class describes a rent or return of a bike (subclass of a station event).
 */
public class RideEvent extends Event {
    private User user;

    /**
     * Instantiates a new Ride event.
     *
     * @param eventTime the event datetime
     * @param eventType the event type
     * @param station   the station on which the event happened
     * @param user      the user that rented or returned a bike
     */
    public RideEvent(LocalDateTime eventTime, EventType eventType, Station station, User user) {
        super(eventTime, eventType, station);
        this.user = user;
    }


    /**
     * Gets the user.
     *
     * @return the user
     */
// getter & setter
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
