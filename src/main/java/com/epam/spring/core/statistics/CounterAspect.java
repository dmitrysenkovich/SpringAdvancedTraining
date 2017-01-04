package com.epam.spring.core.statistics;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.ICounterAspectDao;
import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.statistics.CounterStatisticsEvent;

/**
 * @author alehstruneuski
 */
@Aspect
@Component
public class CounterAspect {

	@Autowired
	private ICounterAspectDao counterAspectDao;
	
	@Before("execution(* com.epam.spring.core.service.IBookingService.bookTickets(..))")
	public void accessToEventByBookedTickets(JoinPoint joinPoint) {
		Event event = (Event) joinPoint.getArgs()[0];
		Long id = event.getId();
	  	CounterStatisticsEvent cointerStatisticsEvent = counterAspectDao.getStatisticsById(id);
	  	if (cointerStatisticsEvent != null) {
	  		cointerStatisticsEvent.increaseNumberOfAccessBookedTickets();
	      	counterAspectDao.updateStatistics(cointerStatisticsEvent);
	  	} else {
	  		CounterStatisticsEvent counterStatisticsEvent = new CounterStatisticsEvent();
	  		counterStatisticsEvent.setId(id);
	  		counterStatisticsEvent.increaseNumberOfAccessBookedTickets();
	      	counterAspectDao.updateStatistics(counterStatisticsEvent);	
	  	}
	}
	  
	@Before("execution(* com.epam.spring.core.service.IBookingService.getTicketsPrice(..))")
	public void accessToEventByPrice(JoinPoint joinPoint) {
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
    public void accessToEventByName(Object retVal) {
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
