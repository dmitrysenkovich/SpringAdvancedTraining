package com.epam.spring.core.dao.statistics.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.spring.core.domain.statistics.DiscountStatistics;

/**
 * @author alehstruneuski
 */
public class DiscountStatisticsMapper implements RowMapper<DiscountStatistics> {

	private static final String USER_ID = "user_id";
	private static final String BIRTHDAY_DISCOUNT = "birthday_discount";
	private static final String LUCKY_DISCOUNT = "lucky_discount";
	
	@Override
	public DiscountStatistics mapRow(ResultSet rs, int rowNum) throws SQLException {
		DiscountStatistics discountStatistics = new DiscountStatistics();
		discountStatistics.setId(rs.getLong(USER_ID));
		discountStatistics.setBirthdayDiscount(rs.getLong(BIRTHDAY_DISCOUNT));
		discountStatistics.setLuckyDiscount(rs.getLong(LUCKY_DISCOUNT));
		return discountStatistics;
	}

}
