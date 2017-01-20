package com.epam.spring.core;

import java.util.Date;

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
import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.EventRating;
import com.epam.spring.core.service.IAuditoriumService;
import com.epam.spring.core.service.IEventService;

@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
public class EventServiceTest extends AbstractTestNGSpringContextTests {

	private static final String TEST_NAME = "EPAM";
	private static final double TEST_PRICE = 100.0;
	private static final EventRating TEST_RATING = EventRating.HIGH;
	private static final String TEST_NAME_OF_AUDITORIUM = "small";
	private static final Date TEST_DATE_TIME = new DateTime(2017, 2, 3, 18, 30, 0, 0).toDate();

	
	@Autowired
	private IEventService eventService;	
	@Autowired
	private IAuditoriumService auditoriumService;
	private Event expecteEvent;
	
	@BeforeClass
	public void initTest() {
		Auditorium auditorium = auditoriumService.getByName(TEST_NAME_OF_AUDITORIUM);
		expecteEvent = new Event();
		expecteEvent.setName(TEST_NAME);
		expecteEvent.setBasePrice(TEST_PRICE);
		expecteEvent.setRating(TEST_RATING);
		expecteEvent.assignAuditorium(TEST_DATE_TIME, auditorium);
	}
	
	@Test(groups = TestConstants.GROUP_EVENT_SAVE)
	public void eventServiceGetByNameTest() {
		Event persistedEvent = eventService.save(expecteEvent);
			
		Assert.assertTrue(eventService.getAll().size() == 1);
		Assert.assertEquals(persistedEvent, eventService.getByName(TEST_NAME));
		Assert.assertEquals(persistedEvent, eventService.getById(persistedEvent.getId()));
	}

}
