package com.epam.spring.core.service.impl;

import com.epam.spring.core.dao.EventRepository;
import com.epam.spring.core.domain.Event;
import com.epam.spring.core.service.IEventService;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author alehstruneuski
 */
@Service
public class EventServiceImpl implements IEventService {
	
	@Autowired
	private EventRepository eventRepository;

	@Override
	public Event save(Event object) {
		return eventRepository.save(object);
	}

	@Override
	public void save(List<Event> objects) {
		eventRepository.save(objects);
	}

	@Override
	public void remove(Event object) {
		eventRepository.delete(object);		
	}

    @Override
    public void remove(Long id) {
        eventRepository.delete(id);
    }

	@Override
	public Event getById(Long id) {
		return eventRepository.findOne(id);
	}

	@Override
	public Collection<Event> getAll() {
		List<Event> targetCollection = new ArrayList<Event>();
		Iterable<Event> eventIterator = eventRepository.findAll();
		CollectionUtils.addAll(targetCollection, eventIterator.iterator());
		return targetCollection;
	}

	@Override
	public Event getByName(String name) {
		return eventRepository.findByName(name);
	}

    @Override
    public boolean exists(Event object) {
        return eventRepository.findByName(object.getName()) != null;
    }
}
