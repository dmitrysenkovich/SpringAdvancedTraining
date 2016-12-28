package com.epam.spring.core.service.impl;

import java.util.Collection;

import com.epam.spring.core.dao.IEventServiceDao;
import com.epam.spring.core.domain.Event;
import com.epam.spring.core.service.IEventService;

public class EventServiceImpl implements IEventService {
	
	private IEventServiceDao eventServiceDao;

	@Override
	public Event save(Event object) {
		return eventServiceDao.save(object);
	}

	@Override
	public void remove(Event object) {
		eventServiceDao.remove(object);		
	}

	@Override
	public Event getById(Long id) {
		return eventServiceDao.getById(id);
	}

	@Override
	public Collection<Event> getAll() {
		return eventServiceDao.getAll();
	}

	@Override
	public Event getByName(String name) {
		return eventServiceDao.getByName(name);
	}

	public void setEventServiceDao(IEventServiceDao eventServiceDao) {
		this.eventServiceDao = eventServiceDao;
	}

}
