package com.epam.spring.core.dao.statistics.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.spring.core.dao.statistics.IDiscountAspectDao;
import com.epam.spring.core.dao.statistics.mappers.DiscountStatisticsMapper;
import com.epam.spring.core.domain.statistics.DiscountStatistics;

/**
 * @author alehstruneuski
 */
@Repository
public class DiscountAspectDaoImpl implements IDiscountAspectDao {
	
	private static final Log LOGGER = LogFactory.getLog(DiscountAspectDaoImpl.class);
	
	private static final String SELECT_DISCOUNT_STATISTICS_BY_ID = "SELECT * FROM discount_statistics WHERE user_id = ? ";
    private static final String UPDATE_DISCOUNT_STATISTICS_BY_ID = "UPDATE discount_statistics SET birthday_discount = ?, lucky_discount = ? WHERE event_id = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public DiscountStatistics getStatisticsById(Long id) {
		DiscountStatistics discountStatistics = null;
		try {
			discountStatistics = jdbcTemplate.queryForObject(SELECT_DISCOUNT_STATISTICS_BY_ID, new Object[] {id}, new DiscountStatisticsMapper());
		} catch (DataAccessException e) {
			String errorMsg = String.format("Unable to obtain discount statistics from DB by id: ", id);
			LOGGER.error(errorMsg, e);
		}
		return discountStatistics;
	}

	@Override
	public void updateStatistics(DiscountStatistics discountStatistics) {		
		try {
			jdbcTemplate.update(UPDATE_DISCOUNT_STATISTICS_BY_ID, discountStatistics.getBirthdayDiscount(), discountStatistics.getLuckyDiscount(), discountStatistics.getId());		
		} catch (DataAccessException e) {
			String errorMsg = String.format("Unable to update discount statistics by id: ", discountStatistics.getId());
			LOGGER.error(errorMsg, e);		
		}	
	}

}
