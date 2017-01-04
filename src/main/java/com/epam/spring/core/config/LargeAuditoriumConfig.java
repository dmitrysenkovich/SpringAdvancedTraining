package com.epam.spring.core.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/auditoriums.properties")
public class LargeAuditoriumConfig {

	@Value("${auditorium.second.name}")
	private String name;
	@Value("#{T(java.lang.Long).parseLong('${auditorium.second.numberOfSeats}')}")
	private Long numberOfSeats;
	@Value("#{'${auditorium.second.vipSeats}'.split(',')}")
	private Set<Long> vipSeats;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getNumberOfSeats() {
		return numberOfSeats;
	}
	public void setNumberOfSeats(Long numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	public Set<Long> getVipSeats() {
		return vipSeats;
	}
	public void setVipSeats(Set<Long> vipSeats) {
		this.vipSeats = vipSeats;
	}
}
