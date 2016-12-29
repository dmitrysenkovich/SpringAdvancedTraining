package com.epam.spring.core.service.discount.impl;

import java.time.LocalDateTime;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.discount.IDiscount;

public class HappyDiscountStrategyImpl implements IDiscount {

	private static final double HAPPY_DISCOUNT = 0.50;
	private static final int EACH_TH_TICKET = 10; 

	@Override
	public double getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
		if (user.isRegistered()) {
			return numberOfTickets == EACH_TH_TICKET ? HAPPY_DISCOUNT : 0;
		} else {
			int numberOfUserTickets = (int) (user.getTickets().size() + numberOfTickets);
			return numberOfUserTickets % EACH_TH_TICKET == 0 ? HAPPY_DISCOUNT : 0;
		}
	}

}
