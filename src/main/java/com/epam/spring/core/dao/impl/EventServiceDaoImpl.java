package com.epam.spring.core.dao.impl;

import java.util.Collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.epam.spring.core.dao.IEventServiceDao;
import com.epam.spring.core.domain.Event;

public class EventServiceDaoImpl implements IEventServiceDao {

	private static Map<Long, Event> events = new HashMap<Long, Event>();
	
	@Override
	public Event save(Event object) {
		return events.put(object.getId(), object);
	}

	@Override
	public void remove(Event object) {
		events.remove(object.getId());
	}

	@Override
	public Event getById(Long id) {
		// TODO Auto-generated method stub
		return events.remove(id);
	}

	@Override
	public Collection<Event> getAll() {
		return events.values();
	}

	@Override
	public Event getByName(String name) {
		List<Event> eventsByName = events.values().stream().
				filter(event -> event.getName().equals(name)).
				collect(Collectors.toList());
		return eventsByName.isEmpty() ? null : eventsByName.get(0);
	}

}
