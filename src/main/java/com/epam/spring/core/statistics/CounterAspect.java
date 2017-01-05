package com.epam.spring.core.statistics;

import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.statistics.ICounterAspectDao;
import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.Ticket;
import com.epam.spring.core.domain.statistics.CounterStatisticsEvent;

/**
 * @author alehstruneuski
 */
@Aspect
@Component
public class CounterAspect {

	@Autowired
	private ICounterAspectDao counterAspectDao;
	
	@SuppressWarnings("unchecked")
	@Before("execution(* com.epam.spring.core.service.IBookingService.bookTickets(..))")
	public void accessEventByBookedTickets(JoinPoint joinPoint) {
		Set<Ticket> tickets = (Set<Ticket>) joinPoint.getArgs()[0];
		for (Ticket ticket : tickets) { 
			Long id = ticket.getEvent().getId();
		  	CounterStatisticsEvent counterStatisticsEvent = counterAspectDao.getStatisticsById(id);
		  	if (counterStatisticsEvent != null) {
		  		counterStatisticsEvent.increaseNumberOfAccessBookedTickets();
		      	counterAspectDao.updateStatistics(counterStatisticsEvent);
		  	} else {
		  		CounterStatisticsEvent counterStatisticsEventNew = new CounterStatisticsEvent();
		  		counterStatisticsEventNew.setId(id);
		  		counterStatisticsEventNew.increaseNumberOfAccessBookedTickets();
		      	counterAspectDao.updateStatistics(counterStatisticsEventNew);	
		  	}	
		}
	}
	  
	@Before("execution(* com.epam.spring.core.service.IBookingService.getTicketsPrice(..))")
	public void accessEventByPrice(JoinPoint joinPoint) {
		Event event = (Event) joinPoint.getArgs()[0];
		Long id = event.getId();
	  	CounterStatisticsEvent cointerStatisticsEvent = counterAspectDao.getStatisticsById(id);
	  	if (cointerStatisticsEvent != null) {
	  		cointerStatisticsEvent.increaseNumberOfAccessByPrice();
	      	counterAspectDao.updateStatistics(cointerStatisticsEvent);
	  	} else {
	  		CounterStatisticsEvent counterStatisticsEvent = new CounterStatisticsEvent();
	  		counterStatisticsEvent.setId(id);
	  		counterStatisticsEvent.increaseNumberOfAccessByPrice();
	      	counterAspectDao.updateStatistics(counterStatisticsEvent);	
	  	}
	}
	  	
    @AfterReturning(pointcut = "execution(* com.epam.spring.core.service.IEventService.getByName(String))", 
    		returning = "retVal")
    public void accessEventByName(Object retVal) {
        Event event = (Event) retVal;
        Long id = event.getId();
        
    	CounterStatisticsEvent cointerStatisticsEvent = counterAspectDao.getStatisticsById(id);
    	if (cointerStatisticsEvent != null) {
    		cointerStatisticsEvent.increaseNumberOfAccessByName();
        	counterAspectDao.updateStatistics(cointerStatisticsEvent);
    	} else {
    		CounterStatisticsEvent counterStatisticsEvent = new CounterStatisticsEvent();
    		counterStatisticsEvent.setId(id);
    		counterStatisticsEvent.increaseNumberOfAccessByName();
        	counterAspectDao.updateStatistics(counterStatisticsEvent);
    	}
    }
    

}
