package com.epam.spring.core.web.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(method = RequestMethod.GET)
	private ModelAndView getDiscount(@RequestBody UserEventBean userEventBean, @RequestParam String airDateTime, @RequestParam long number) {
		double discount = discountService.getDiscount(userEventBean.getUser(), userEventBean.getEvent(), new Date(), number);
		
		ModelAndView discountView = new ModelAndView(DISCOUNT_VIEW);
		discountView.addObject("discount", discount);
		return discountView;
	}
	
}
