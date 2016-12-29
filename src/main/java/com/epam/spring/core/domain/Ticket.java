package com.epam.spring.core.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author alehstruneuski
 */
public class Ticket extends DomainObject implements Comparable<Ticket> {

    private User user;
    private Event event;
    private LocalDateTime dateTime;
    private long seat;
    private boolean isVipSeat;

    public Ticket(User user, Event event, LocalDateTime dateTime, long seat, boolean isVipSeat) {
        this.user = user;
        this.event = event;
        this.dateTime = dateTime;
        this.seat = seat;
        this.isVipSeat = isVipSeat;
    }

    public boolean isVipSeat() {
    	return isVipSeat;
    }
    
    public User getUser() {
        return user;
    }

    public Event getEvent() {
        return event;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public long getSeat() {
        return seat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, event, seat);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Ticket other = (Ticket) obj;
        if (dateTime == null) {
            if (other.dateTime != null) {
                return false;
            }
        } else if (!dateTime.equals(other.dateTime)) {
            return false;
        }
        if (event == null) {
            if (other.event != null) {
                return false;
            }
        } else if (!event.equals(other.event)) {
            return false;
        }
        if (seat != other.seat) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Ticket other) {
        if (other == null) {
            return 1;
        }
        int result = dateTime.compareTo(other.getDateTime());

        if (result == 0) {
            result = event.getName().compareTo(other.getEvent().getName());
        }
        if (result == 0) {
            result = Long.compare(seat, other.getSeat());
        }
        return result;
    }

}
