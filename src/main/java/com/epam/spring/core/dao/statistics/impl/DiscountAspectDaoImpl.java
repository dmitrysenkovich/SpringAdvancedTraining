package com.epam.spring.core.dao.statistics.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.epam.spring.core.dao.statistics.IDiscountAspectDao;
import com.epam.spring.core.domain.statistics.DiscountStatistics;

/**
 * @author alehstruneuski
 */
@Repository
public class DiscountAspectDaoImpl implements IDiscountAspectDao {

	private Map<Long, DiscountStatistics>  statistics = new HashMap<>();
	
	@Override
	public DiscountStatistics getStatisticsById(Long id) {
		return statistics.get(id);
	}

	@Override
	public void updateStatistics(DiscountStatistics discountStatistics) {		
		statistics.put(discountStatistics.getId(), discountStatistics);
	}

}
