package com.epam.spring.core.service.discount.impl;

import java.time.LocalDateTime;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.discount.IDiscount;

public class HappyDiscountStrategyImpl implements IDiscount {

	@Override
	public double getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
		// TODO Auto-generated method stub
		return 0;
	}

}
