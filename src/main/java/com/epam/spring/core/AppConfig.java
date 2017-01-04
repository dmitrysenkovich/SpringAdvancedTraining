package com.epam.spring.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.epam.spring.core.config.LargeAuditoriumConfig;
import com.epam.spring.core.config.SmallAuditoriumConfig;
import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.service.discount.IDiscount;
import com.epam.spring.core.service.discount.impl.BirthdayDiscountImpl;
import com.epam.spring.core.service.discount.impl.LuckyDiscountStrategyImpl;

@Configuration
@ComponentScan("com.epam.spring.core")
public class AppConfig {
	
	@Autowired
	private SmallAuditoriumConfig smallAuditoriumConfig;
	@Autowired
	private LargeAuditoriumConfig largeAuditoriumConfig;
	
	  
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholder() {
	    return new PropertySourcesPlaceholderConfigurer();  
	}

	@Bean
	public List<IDiscount> bunchOfDiscounts() {
		List<IDiscount> bunchOfDiscounts = new ArrayList<>();
		bunchOfDiscounts.add(new BirthdayDiscountImpl());
		bunchOfDiscounts.add(new LuckyDiscountStrategyImpl());
		return bunchOfDiscounts;
	}
	
	@Bean
	public Map<String, Auditorium> bunchOfAuditoriums() {
		Map<String, Auditorium> bunchOfAuditoriums = new HashMap<>();
		
		Auditorium smallAuditorium = new Auditorium();
		smallAuditorium.setName(smallAuditoriumConfig.getName());
		smallAuditorium.setNumberOfSeats(smallAuditoriumConfig.getNumberOfSeats());
		smallAuditorium.setVipSeats(smallAuditoriumConfig.getVipSeats());

		Auditorium largeAuditorium = new Auditorium();
		largeAuditorium.setName(largeAuditoriumConfig.getName());
		largeAuditorium.setNumberOfSeats(largeAuditoriumConfig.getNumberOfSeats());
		largeAuditorium.setVipSeats(largeAuditoriumConfig.getVipSeats());
		
		bunchOfAuditoriums.put(smallAuditorium.getName(), smallAuditorium);
		bunchOfAuditoriums.put(largeAuditorium.getName(), largeAuditorium);
	
		return bunchOfAuditoriums;
	}

}
