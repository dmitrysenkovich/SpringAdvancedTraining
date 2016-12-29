package com.epam.spring.core.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author alehstruneuski
 */
public class Auditorium {

    private String name;
    private long numberOfSeats;
	private NavigableSet<Ticket> tickets = new TreeSet<>();
    private Set<Long> vipSeats = Collections.emptySet();

    public Auditorium() {
    }
    
    public void addTicket(Ticket ticket) {
    	tickets.add(ticket);
    }
    
    public void remove(Ticket ticket) {
    	tickets.remove(ticket);
    }
    
    public NavigableSet<Ticket> getTickets() {
    	return tickets;
    }

    /**
     * Counts how many vip seats are there in supplied <code>seats</code>
     * 
     * @param seats
     *            Seats to process
     * @return number of vip seats in request
     */
    public long countVipSeats(Collection<Long> seats) {
        return seats.stream().filter(seat -> vipSeats.contains(seat)).count();
    }
    
    /**
     * Checks whether passed id of seat is VIP
     * @param id - id of seat
     * @return whether id is VIP seat or not
     */
    public boolean isSeatVip(Long id) {
    	return vipSeats.contains(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    
    public Set<Long> getAllSeats() {
        return LongStream.range(1, numberOfSeats + 1).boxed().collect(Collectors.toSet());
    }

    public Set<Long> getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(Set<Long> vipSeats) {
        this.vipSeats = vipSeats;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
        Auditorium other = (Auditorium) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
