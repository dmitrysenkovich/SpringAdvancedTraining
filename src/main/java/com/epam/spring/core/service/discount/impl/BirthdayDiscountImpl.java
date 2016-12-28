package com.epam.spring.core.service.discount.impl;

import java.time.LocalDateTime;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.discount.IDiscount;

public class BirthdayDiscountImpl implements IDiscount {

	private static final double BIRTHDAY_DISCOUNT = 0.05;
	
	@Override
	public double getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
		LocalDate dateAirDate= new LocalDate(0, airDateTime.getMonthValue(), airDateTime.getDayOfMonth());		
		LocalDate dateBirthdayUser = new LocalDate(0, user.getBirthday().getMonthValue(), user.getBirthday().getDayOfMonth());
		int diffDays = Days.daysBetween(dateAirDate ,dateBirthdayUser).getDays();
		return (diffDays >= 0 && 5 <= diffDays) ? BIRTHDAY_DISCOUNT : 0;
	}

}
