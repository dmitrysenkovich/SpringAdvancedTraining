package com.epam.spring.core.dao.statistics.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.epam.spring.core.dao.statistics.ICounterAspectDao;
import com.epam.spring.core.domain.statistics.CounterStatisticsEvent;

/**
 * @author alehstruneuski
 */
@Repository
public class CounterAspectDaoImpl implements ICounterAspectDao {

	private Map<Long, CounterStatisticsEvent> statistics = new HashMap<>();
	
	@Override
	public CounterStatisticsEvent getStatisticsById(Long id) {
		return statistics.get(id);
	}

	@Override
	public void updateStatistics(CounterStatisticsEvent cointerStatisticsEvent) {	
		statistics.put(cointerStatisticsEvent.getId(), cointerStatisticsEvent);
	}

}
