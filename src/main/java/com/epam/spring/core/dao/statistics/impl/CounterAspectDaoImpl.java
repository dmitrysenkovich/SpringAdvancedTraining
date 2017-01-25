package com.epam.spring.core.dao.statistics.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.spring.core.dao.statistics.ICounterAspectDao;
import com.epam.spring.core.dao.statistics.mappers.CounterStatisticsEventMapper;
import com.epam.spring.core.domain.statistics.CounterStatisticsEvent;

/**
 * @author alehstruneuski
 */
@Repository
public class CounterAspectDaoImpl implements ICounterAspectDao {
	
	private static final Log LOGGER = LogFactory.getLog(CounterAspectDaoImpl.class);
		
	private static final String SELECT_EVENT_STATISTICS_BY_ID = "SELECT * FROM statistics_event WHERE event_id = ?";
    private static final String UPDATE_EVENT_STATISTICS_BY_ID = "UPDATE statistics_event SET number_access_by_name = ?, number_access_by_price = ?, number_of_booked_tickets = ? WHERE event_id = ?";
    private static final String INSERT_EVENT_STATISTICS_BY_ID = "INSERT INTO statistics_event (number_access_by_name, number_access_by_price, number_of_booked_tickets, event_id) VALUES (?, ?, ?, ?)";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public CounterStatisticsEvent getStatisticsById(Long id) {
		List<CounterStatisticsEvent> statiscticsEvent = null;
		try {
			statiscticsEvent = jdbcTemplate.query(SELECT_EVENT_STATISTICS_BY_ID, new Object[] {id}, new CounterStatisticsEventMapper());
		} catch(DataAccessException e) {
			String errorMsg = String.format("Unable to obtain event statistics from DB by id: ", id);
			LOGGER.error(errorMsg, e);
		}
		
		if (!statiscticsEvent.isEmpty()) {
			statiscticsEvent.get(0);
		}
		
		return null;
	}

	@Override
	public void updateStatistics(CounterStatisticsEvent cointerStatisticsEvent) {	
		try {
			jdbcTemplate.update(UPDATE_EVENT_STATISTICS_BY_ID, 
					cointerStatisticsEvent.getNumberOfAccessByName(), cointerStatisticsEvent.getNumberOfAccessByPrice(), 
					cointerStatisticsEvent.getNumberOfAccessBookedTickets(), cointerStatisticsEvent.getId());		
		} catch (DataAccessException e) {
			String errorMsg = String.format("Unable to update event statistics from DB by id: ", cointerStatisticsEvent.getId());
			LOGGER.error(errorMsg, e);
		}	
	}

	@Override
	public void insertStatistics(CounterStatisticsEvent cointerStatisticsEvent) {	
		try {
			jdbcTemplate.update(INSERT_EVENT_STATISTICS_BY_ID, 
					cointerStatisticsEvent.getNumberOfAccessByName(), cointerStatisticsEvent.getNumberOfAccessByPrice(), 
					cointerStatisticsEvent.getNumberOfAccessBookedTickets(), cointerStatisticsEvent.getId());		
		} catch (DataAccessException e) {
			String errorMsg = String.format("Unable to insert event statistics from DB by id: ", cointerStatisticsEvent.getId());
			LOGGER.error(errorMsg, e);
		}	
	}

}
