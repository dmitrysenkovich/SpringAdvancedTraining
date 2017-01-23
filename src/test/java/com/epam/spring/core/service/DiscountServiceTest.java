package com.epam.spring.core.service;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.spring.core.app.AppConfig;
import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IDiscountService;
import com.epam.spring.core.service.IEventService;
import com.epam.spring.core.service.IUserService;

@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration
public class DiscountServiceTest extends AbstractTransactionalTestNGSpringContextTests {

	// User
	private static final String TEST_USER_EMAIL = "aleh_struneuski@epam.com";	

	// Event
	private static final String TEST_EVENT_NAME = "EPAM";

	
	//Expected discount
	private static final double EXPECTED_HAPPY_DISCOUNT = 0.5;
	private static final double EXPECTED_BIRTHDAY_DISCOUNT = 0.05;

	@Autowired
	private IDiscountService discountService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IEventService eventService;

	private Event testEvent;
	private User testUser;
	
	private int dayOfMonthUserBirthday;
	private int monthOfUserBirthday;
	
	@BeforeClass
	public void initTest() {
		testEvent = eventService.getByName(TEST_EVENT_NAME);
		testUser = userService.getUserByEmail(TEST_USER_EMAIL);
		
		String userBirthday = testUser.getBirthday().toString();
		dayOfMonthUserBirthday = getDate(userBirthday, "YYYY-MM-dd HH:mm:ss.SSS").getMonthOfYear();
		monthOfUserBirthday = getDate(userBirthday, "YYYY-MM-dd HH:mm:ss.SSS").getDayOfMonth();	
	}
	
	@Test(description = "invoke lucky discount")
	public void getDiscountEachTenthTicket() {
		Date airDateTime = new DateTime(0000, dayOfMonthUserBirthday, monthOfUserBirthday, 00, 00).minusDays(2).toDate();
		double discountActual = discountService.getDiscount(testUser, testEvent, airDateTime, 20);		
		Assert.assertEquals(discountActual, EXPECTED_HAPPY_DISCOUNT);
	}
	
	@Test(description = "invoke birthday discount")
	public void getDiscountBirthday() {		
		Date airDateTimeEventOnTime = new DateTime(0000, dayOfMonthUserBirthday, monthOfUserBirthday, 00, 00).toDate();
		double discountActualWithinOneDay = discountService.getDiscount(testUser, testEvent, airDateTimeEventOnTime, 9);		
		Assert.assertEquals(discountActualWithinOneDay, EXPECTED_BIRTHDAY_DISCOUNT);
		
		Date airDateTimeEventFiveDay = new DateTime(0000, dayOfMonthUserBirthday, monthOfUserBirthday, 00, 00).plusDays(5).toDate();
		double discountActualWithinFiveDay = discountService.getDiscount(testUser, testEvent, airDateTimeEventFiveDay, 9);		
		Assert.assertEquals(discountActualWithinFiveDay, EXPECTED_BIRTHDAY_DISCOUNT);
	}
	
	@Test(description = "invoke all discounts")
	public void getDiscountMatchAlllRequirements() {
		Date airDateTimeEventFiveDay = new DateTime(0000, dayOfMonthUserBirthday, monthOfUserBirthday, 00, 00).plusDays(2).toDate();
		double discountActualWithinFiveDay = discountService.getDiscount(testUser, testEvent, airDateTimeEventFiveDay, 10);		
		Assert.assertEquals(discountActualWithinFiveDay, EXPECTED_HAPPY_DISCOUNT);
	}
	
    private static DateTime getDate(String date, String pattern) {
        return DateTimeFormat.forPattern(pattern).parseDateTime(date).withZone(DateTimeZone.getDefault());
    }
	
}
