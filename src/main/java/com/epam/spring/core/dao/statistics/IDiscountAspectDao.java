package com.epam.spring.core.dao.statistics;

import com.epam.spring.core.domain.statistics.DiscountStatistics;

/**
 * @author alehstruneuski
 */
public interface IDiscountAspectDao {

	public DiscountStatistics getStatisticsById(Long id);
	public void updateStatistics(DiscountStatistics cointerStatisticsEvent);
	public void insertStatistics(DiscountStatistics discountStatistics);
	
}
