package com.epam.spring.core.domain.statistics;

import com.epam.spring.core.domain.DomainObject;

/**
 * @author alehstruneuski
 */
public class CounterStatisticsEvent extends DomainObject {

	private Long numberOfAccessByName;
	private Long numberOfAccessByPrice;
	private Long numberOfbookedTickets;
	
	public CounterStatisticsEvent() {
		this.numberOfAccessByName = 0l;
		this.numberOfAccessByPrice = 0l;
		this.numberOfbookedTickets = 0l;
	}
	
	public Long getNumberOfAccessByName() {
		return numberOfAccessByName;
	}
	public void setNumberOfAccessByName(Long numberOfAccessByName) {
		this.numberOfAccessByName = numberOfAccessByName;
	}
	public Long getNumberOfAccessByPrice() {
		return numberOfAccessByPrice;
	}
	public void setNumberOfAccessByPrice(Long numberOfAccessByPrice) {
		this.numberOfAccessByPrice = numberOfAccessByPrice;
	}
	public Long getNumberOfAccessBookedTickets() {
		return numberOfbookedTickets;
	}
	public void setNumberOfAccessBookedTickets(Long numberOfbookedTickets) {
		this.numberOfbookedTickets = numberOfbookedTickets;
	}
	
	public void increaseNumberOfAccessByName() {
		++numberOfAccessByName;
	}
	
	public void increaseNumberOfAccessByPrice() {
		++numberOfAccessByPrice;
	}
	
	public void increaseNumberOfAccessBookedTickets() {
		++numberOfbookedTickets;
	}
	
}
