package com.epam.spring.core.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IDiscountService;
import com.epam.spring.core.service.discount.IDiscount;

public class DiscountServiceImpl implements IDiscountService {

	private List<IDiscount> discounts;

	@Override
	public double getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
		double discountCurrent = 0;
		for (IDiscount discount : discounts) {
			double obtainedDiscount = discount.getDiscount(user, event, airDateTime, numberOfTickets);
			if (discountCurrent < obtainedDiscount) {
				discountCurrent = obtainedDiscount;
			}
		}
		return discountCurrent;
	}
	
	public void setDiscounts(List<IDiscount> discounts) {
		this.discounts = discounts;
	}

}
