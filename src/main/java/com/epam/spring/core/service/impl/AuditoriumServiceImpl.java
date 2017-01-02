package com.epam.spring.core.service.impl;

import java.util.Collection;
import java.util.Map;

import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.service.IAuditoriumService;

public class AuditoriumServiceImpl implements IAuditoriumService {

	private Map<String, Auditorium> auditoriums;

	@Override
	public Collection<Auditorium> getAll() {
		return auditoriums.values();
	}

	@Override
	public Auditorium getByName(String name) {
		return auditoriums.get(name);
	}
	
	public void setAuditoriums(Map<String, Auditorium> auditoriums) {
		this.auditoriums = auditoriums;
	}

}
