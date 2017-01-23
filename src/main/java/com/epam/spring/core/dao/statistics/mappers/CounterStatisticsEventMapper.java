package com.epam.spring.core.dao.statistics.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.spring.core.domain.statistics.CounterStatisticsEvent;

/**
 * @author alehstruneuski
 */
public class CounterStatisticsEventMapper implements RowMapper<CounterStatisticsEvent> {

	private static final String EVENT_ID = "event_id";
	private static final String ACCESS_BY_NAME = "number_access_by_name";
	private static final String ACCESS_BY_PRICE = "number_access_by_price";
	private static final String NUMBER_OF_BOOKED_TICKETS = "number_of_booked_tickets";

	@Override
	public CounterStatisticsEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
		CounterStatisticsEvent statiscticsEvent = new CounterStatisticsEvent();
		statiscticsEvent.setId(rs.getLong(EVENT_ID));
		statiscticsEvent.setNumberOfAccessByName(rs.getLong(ACCESS_BY_NAME));
		statiscticsEvent.setNumberOfAccessByPrice(rs.getLong(ACCESS_BY_PRICE));
		statiscticsEvent.setNumberOfAccessBookedTickets(rs.getLong(NUMBER_OF_BOOKED_TICKETS));
		return statiscticsEvent;
	}

}
