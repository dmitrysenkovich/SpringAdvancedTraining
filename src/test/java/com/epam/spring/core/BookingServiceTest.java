package com.epam.spring.core;

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
import com.epam.spring.core.constants.TestConstants;
import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.EventRating;
import com.epam.spring.core.domain.Ticket;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IBookingService;

@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
public class BookingServiceTest extends AbstractTestNGSpringContextTests {
	
	// EVENT
	private static final String EVENT_TEST_NAME = "EPAM";
	private static final double EVENT_TEST_PRICE = 100.0;
	private static final EventRating EVENT_TEST_RATING = EventRating.HIGH;
	
	// USER
	private static final String USER_TEST_FIRST_NAME = "Aleh";
	private static final String USER_TEST_LAST_NAME = "Struneuski";
	private static final String USER_TEST_EMAIL = "aleh_struneuski@epam.com";
	private static final Date USER_TEST_BIRTHDAY = new DateTime(1993, 8, 12, 18, 30, 0, 0).toDate();
	
	private static final Date TEST_DATE_TIME = new DateTime(2017, 2, 3, 18, 30, 0, 0).toDate();
	
	private static final double EXPECTED_TICKETS_PRICE = 240.0;

	@Autowired
	private IBookingService bookingService;

	private Event event;
	private User user;

	@BeforeClass
	public void initEventTest() {
		event = new Event();
		event.setName(EVENT_TEST_NAME);
		event.setBasePrice(EVENT_TEST_PRICE);
		event.setRating(EVENT_TEST_RATING);
	}
	
	@BeforeClass
	public void initUserTest() {	
		user = new User();
		user.setFirstName(USER_TEST_FIRST_NAME);
		user.setLastName(USER_TEST_LAST_NAME);
		user.setEmail(USER_TEST_EMAIL);
		user.setBirthday(USER_TEST_BIRTHDAY);
	}

	@Test(dependsOnGroups = { TestConstants.GROUP_EVENT_SAVE, TestConstants.GROUP_USER_SAVE }, description = "getTicketsPrice()")
	public void getTicketsPriceTest() {
		Set<Long> seats = new HashSet<>(Arrays.asList(1l, 2l));
		double actualTicketsPrice = bookingService.getTicketsPrice(event, TEST_DATE_TIME, user, seats);
		Assert.assertEquals(actualTicketsPrice, EXPECTED_TICKETS_PRICE);
	}
	
	@Test(dependsOnGroups = { TestConstants.GROUP_EVENT_SAVE, TestConstants.GROUP_USER_SAVE }, description = "getPurchasedTicketsForEvent()")
	public void getPurchasedTicketsForEventTest() {
		Ticket firstTestTicket = new Ticket(user, event, 1, false); 
		Ticket secondTestTicket = new Ticket(user, event, 2, false);
		
		Set<Ticket> tickets = new HashSet<>();
		tickets.add(firstTestTicket);
		tickets.add(secondTestTicket);
		
		bookingService.bookTickets(tickets);
		Set<Ticket> ticketsActual = bookingService.getPurchasedTicketsForEvent(event, TEST_DATE_TIME);
		Assert.assertEquals(ticketsActual, tickets);
	}

}
