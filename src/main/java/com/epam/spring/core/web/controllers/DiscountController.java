package com.epam.spring.core.web.controllers;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.spring.core.service.IDiscountService;
import com.epam.spring.core.web.beans.UserEventBean;

@Controller
@RequestMapping("/discount")
public class DiscountController {
	
	private static final String DISCOUNT_VIEW = "discount/discount_view";

	@Autowired
	private IDiscountService discountService;
	
	@RequestMapping(method = RequestMethod.POST)
	private ModelAndView getDiscount(
			@RequestBody(required = false) UserEventBean userEventBean, 
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) String airDateTime, 
			@RequestParam(required = false) long number) 
	{
		DateTime date = DateTime.parse(airDateTime);
		double discount = discountService.getDiscount(userEventBean.getUser(), userEventBean.getEvent(), date.toDate(), number);

		ModelAndView discountView = new ModelAndView(DISCOUNT_VIEW);
		discountView.addObject("entity", "discount");
		discountView.addObject("discount", discount);
		return discountView;
	}
	
}
