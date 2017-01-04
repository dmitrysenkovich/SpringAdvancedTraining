package com.epam.spring.core.dao;

import com.epam.spring.core.domain.statistics.CounterStatisticsEvent;

public interface ICounterAspectDao {

	public CounterStatisticsEvent getStatisticsById(Long id);
	public void updateStatistics(CounterStatisticsEvent cointerStatisticsEvent);
	
}
 