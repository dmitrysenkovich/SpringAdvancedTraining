package com.epam.spring.core.service.impl;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.service.IAuditoriumService;

/**
 * @author alehstruneuski
 */
@Service
public class AuditoriumServiceImpl implements IAuditoriumService {

	@Resource(name="bunchOfAuditoriums")
	private Map<String, Auditorium> auditoriums;

	@Override
	public Collection<Auditorium> getAll() {
		return auditoriums.values();
	}

	@Override
	public Auditorium getByName(String name) {
		return auditoriums.get(name);
	}
	
}
