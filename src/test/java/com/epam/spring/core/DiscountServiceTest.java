package com.epam.spring.core;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.EventRating;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IDiscountService;

@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
public class DiscountServiceTest extends AbstractTestNGSpringContextTests {

	// User
	private static final Long TEST_ID_USER = 1l;
	private static final String TEST_USER_FIRST_NAME = "Aleh";
	private static final String TEST_USER_LAST_NAME = "Struneuski";
	private static final String TEST_USER_EMAIL = "aleh_struneuski@epam.com";
	private static final LocalDateTime TEST_BIRTHDAY = LocalDateTime.of(1993, 8, 12, 10, 29);
		
	// Event
	private static final Long TEST_ID_EVENT = 1l;
	private static final String TEST_NAME = "EPAM";
	private static final double TEST_PRICE = 100.0;
	private static final EventRating TEST_RATING = EventRating.HIGH;
	
	//Expected discount
	private static final double EXPECTED_HAPPY_DISCOUNT = 0.5;
	private static final double EXPECTED_BIRTHDAY_DISCOUNT = 0.05;

	@Autowired
	private IDiscountService discountService;
	private User testUser;
	private Event testEvent;
	
	private static int dayOfMonthUserBirthday;
	private static int monthOfUserBirthday;
	
	@BeforeClass
	public void initTest() {
		testUser = new User();
		testUser.setId(TEST_ID_USER);
		testUser.setFirstName(TEST_USER_FIRST_NAME);
		testUser.setLastName(TEST_USER_LAST_NAME);
		testUser.setEmail(TEST_USER_EMAIL);
		testUser.setBirthday(TEST_BIRTHDAY);
		
		testEvent = new Event();
		testEvent.setId(TEST_ID_EVENT);
		testEvent.setName(TEST_NAME);
		testEvent.setBasePrice(TEST_PRICE);
		testEvent.setRating(TEST_RATING);
		
		dayOfMonthUserBirthday = TEST_BIRTHDAY.getMonthValue();
		monthOfUserBirthday = TEST_BIRTHDAY.getDayOfMonth();
	}
	
	@Test(description = "invoke lucky discount")
	public void getDiscountEachTenthTicket() {
		LocalDateTime airDateTime = LocalDateTime.of(0000, dayOfMonthUserBirthday, monthOfUserBirthday, 00, 00).minusDays(2);
		double discountActual = discountService.getDiscount(testUser, testEvent, airDateTime, 20);		
		Assert.assertEquals(discountActual, EXPECTED_HAPPY_DISCOUNT);
	}
	
	@Test(description = "invoke birthday discount")
	public void getDiscountBirthday() {		
		LocalDateTime airDateTimeEventOnTime = LocalDateTime.of(0000, dayOfMonthUserBirthday, monthOfUserBirthday, 00, 00);
		double discountActualWithinOneDay = discountService.getDiscount(testUser, testEvent, airDateTimeEventOnTime, 9);		
		Assert.assertEquals(discountActualWithinOneDay, EXPECTED_BIRTHDAY_DISCOUNT);
		
		LocalDateTime airDateTimeEventFiveDay = LocalDateTime.of(0000, dayOfMonthUserBirthday, monthOfUserBirthday, 00, 00).plusDays(5);
		double discountActualWithinFiveDay = discountService.getDiscount(testUser, testEvent, airDateTimeEventFiveDay, 9);		
		Assert.assertEquals(discountActualWithinFiveDay, EXPECTED_BIRTHDAY_DISCOUNT);
	}
	
	@Test(description = "invoke all discounts")
	public void getDiscountMatchAlllRequirements() {
		LocalDateTime airDateTimeEventFiveDay = LocalDateTime.of(0000, dayOfMonthUserBirthday, monthOfUserBirthday, 00, 00).plusDays(2);
		double discountActualWithinFiveDay = discountService.getDiscount(testUser, testEvent, airDateTimeEventFiveDay, 10);		
		Assert.assertEquals(discountActualWithinFiveDay, EXPECTED_HAPPY_DISCOUNT);
	}
	
}
