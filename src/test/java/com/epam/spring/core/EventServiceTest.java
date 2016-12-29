package com.epam.spring.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.EventRating;
import com.epam.spring.core.service.IEventService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EventServiceTest extends AbstractTestNGSpringContextTests {

	private static final Long TEST_ID = 1l;
	private static final String TEST_NAME = "EPAM";
	private static final double TEST_PRICE = 100.0;
	private static final EventRating TEST_RATING = EventRating.HIGH;
	
	@Autowired
	private IEventService eventService;	
	private Event expecteEvent;
	
	@BeforeClass
	public void initTest() {
		expecteEvent = new Event();
		expecteEvent.setId(TEST_ID);
		expecteEvent.setName(TEST_NAME);
		expecteEvent.setBasePrice(TEST_PRICE);
		expecteEvent.setRating(TEST_RATING);
	}
	
	@Test
	public void eventServiceGetByNameTest() {
		eventService.save(expecteEvent);
		
		Assert.assertTrue(eventService.getAll().size() == 1);
		Assert.assertEquals(expecteEvent, eventService.getById(TEST_ID));
	}
	
}
