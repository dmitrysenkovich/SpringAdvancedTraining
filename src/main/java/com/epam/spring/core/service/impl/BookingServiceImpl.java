package com.epam.spring.core.service.impl;

import java.time.LocalDateTime;
import java.util.NavigableMap;
import java.util.Set;

import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.EventRating;
import com.epam.spring.core.domain.Ticket;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IAuditoriumService;
import com.epam.spring.core.service.IBookingService;
import com.epam.spring.core.service.IDiscountService;
import com.epam.spring.core.service.IEventService;
import com.epam.spring.core.service.IUserService;

public class BookingServiceImpl implements IBookingService {
	
	private static final double RATE_FOR_HIGHT_EVENT_RAITING = 1.2;
	private static final double RATE_VIP_SEAT = 2;

	private IDiscountService discountService;
	private IAuditoriumService auditoriumService;
	private IEventService eventService;
	private IUserService userService;
	
	@Override
	public double getTicketsPrice(Event event, LocalDateTime dateTime, User user, Set<Long> seats) {	
		double discount = discountService.getDiscount(user, event, dateTime, seats.size());
		double basePrice = event.getBasePrice();
		if (event.getRating() == EventRating.HIGH) {
			basePrice = basePrice * RATE_FOR_HIGHT_EVENT_RAITING;
		}
		
		basePrice = (basePrice * discount) + basePrice;

		double totalPrice = 0;
		for (Long id : seats) {
			Auditorium auditorium = eventService.getById(event.getId()).getAuditoriums().get(dateTime);
			boolean isSeatVip = auditoriumService.getByName(auditorium.getName()).isSeatVip(id);	
			if (isSeatVip) {
				totalPrice += basePrice * RATE_VIP_SEAT;
			} else {
				totalPrice += basePrice; 
			}
		}
		return totalPrice;
	}

	@Override
	public void bookTickets(Set<Ticket> tickets) {		
		for (Ticket ticket : tickets) {
			Event event = eventService.getById(ticket.getEvent().getId());
			event.getAuditoriums().get(ticket.getDateTime()).addTicket(ticket);
			
			User userTicket = ticket.getUser();
			if (userService.getById(userTicket.getId()) != null) {
				userTicket.getTickets().add(ticket);
				userService.save(userTicket);
			}
		}		
	}

	@Override
	public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
		Event choosenEvent = eventService.getById(event.getId());
		NavigableMap<LocalDateTime, Auditorium> auditoriums = choosenEvent.getAuditoriums();
		Auditorium auditorium = auditoriums.get(dateTime);
		return auditorium.getTickets();
	}
	
}
