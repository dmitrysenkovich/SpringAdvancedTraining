package com.epam.spring.core.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.spring.core.app.AppConfig;
import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.Ticket;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IBookingService;

@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
public class BookingServiceTest extends AbstractTestNGSpringContextTests {
	
	// EVENT
	private static final String EVENT_TEST_NAME = "EPAM";
	
	// USER
	private static final String USER_TEST_EMAIL = "aleh_struneuski@epam.com";

	
	private static final Date TEST_DATE_TIME = new DateTime(2017, 2, 3, 18, 30, 0, 0).toDate();
	private static final double EXPECTED_TICKETS_PRICE = 240.0;

	@Autowired
	private IBookingService bookingService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IEventService eventService;	

	private Event testEvent;
	private User testUser;

	@BeforeClass
	public void initEventTest() {
		testEvent = eventService.getByName(EVENT_TEST_NAME);
	}
	
	@BeforeClass
	public void initUserTest() {	
		testUser = userService.getUserByEmail(USER_TEST_EMAIL);
	}

	@Test(description = "getTicketsPrice()")
	public void getTicketsPriceTest() {
		Set<Long> seats = new HashSet<>(Arrays.asList(1l, 2l));
		double actualTicketsPrice = bookingService.getTicketsPrice(testEvent, TEST_DATE_TIME, testUser, seats);
		Assert.assertEquals(actualTicketsPrice, EXPECTED_TICKETS_PRICE);
	}
	
	@Test(description = "getPurchasedTicketsForEvent()")
	public void getPurchasedTicketsForEventTest() {
		Ticket firstTestTicket = new Ticket(testUser, testEvent, 1, false); 
		Ticket secondTestTicket = new Ticket(testUser, testEvent, 2, false);
		
		Set<Ticket> tickets = new HashSet<>();
		tickets.add(firstTestTicket);
		tickets.add(secondTestTicket);
		
		bookingService.bookTickets(tickets);
		Set<Ticket> ticketsActual = bookingService.getPurchasedTicketsForEvent(testEvent, TEST_DATE_TIME);
		Assert.assertEquals(ticketsActual, tickets);
	}

}
