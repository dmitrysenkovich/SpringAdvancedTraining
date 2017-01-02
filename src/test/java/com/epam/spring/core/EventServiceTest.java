package com.epam.spring.core;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.spring.core.constants.TestConstants;
import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.EventRating;
import com.epam.spring.core.service.IAuditoriumService;
import com.epam.spring.core.service.IEventService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EventServiceTest extends AbstractTestNGSpringContextTests {

	private static final Long TEST_ID = 1l;
	private static final String TEST_NAME = "EPAM";
	private static final double TEST_PRICE = 100.0;
	private static final EventRating TEST_RATING = EventRating.HIGH;
	private static final String TEST_NAME_OF_AUDITORIUM = "small";
	private static final LocalDateTime TEST_DATE_TIME = LocalDateTime.of(2017, 2, 3, 18, 30);

	
	@Autowired
	private IEventService eventService;	
	@Autowired
	private IAuditoriumService auditoriumService;
	private Event expecteEvent;
	
	@BeforeClass
	public void initTest() {
		Auditorium auditorium = auditoriumService.getByName(TEST_NAME_OF_AUDITORIUM);
		expecteEvent = new Event();
		expecteEvent.setId(TEST_ID);
		expecteEvent.setName(TEST_NAME);
		expecteEvent.setBasePrice(TEST_PRICE);
		expecteEvent.setRating(TEST_RATING);
		expecteEvent.addAirDateTime(TEST_DATE_TIME, auditorium);
	}
	
	@Test(groups = TestConstants.GROUP_EVENT_SAVE)
	public void eventServiceGetByNameTest() {
		eventService.save(expecteEvent);
			
		Assert.assertTrue(eventService.getAll().size() == 1);
		Assert.assertEquals(expecteEvent, eventService.getByName(TEST_NAME));
		Assert.assertEquals(expecteEvent, eventService.getById(TEST_ID));
	}
	
	
}
