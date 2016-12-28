package com.epam.spring.core.dao;

import java.util.Collection;

import com.epam.spring.core.domain.Event;

public interface IEventServiceDao {

	public Event save(Event object);

	public void remove(Event object);

	public Event getById(Long id);

	public Collection<Event> getAll();

	public Event getByName(String name);
	
}
