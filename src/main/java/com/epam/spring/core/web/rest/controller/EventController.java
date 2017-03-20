package com.epam.spring.core.web.rest.controller;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.service.IEventService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@RestController("restEventController")
@RequestMapping("/api/event")
public class EventController {
    private static final Log LOGGER = LogFactory.getLog(EventController.class);

    private final IEventService eventService;

    @Autowired
    public EventController(IEventService eventService) {
        this.eventService = eventService;
        LOGGER.info("Initializing is completed");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Event> event(@PathVariable Long id) {
        LOGGER.info("event GET request");
        LOGGER.info("id: " + id);

        Event event = eventService.getById(id);
        if (event == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Collection<Event>> all() {
        LOGGER.info("all GET request");

        Collection<Event> events = eventService.getAll();
        if (events.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Event> create(@RequestBody Event event,
                                       UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.info("create POST request");

        boolean exists = eventService.exists(event);
        if (exists)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        eventService.save(event);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/{id}").buildAndExpand(event.getId()).toUri());

        return new ResponseEntity<>(event, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Event> update(@PathVariable Long id, @RequestBody Event event) {
        LOGGER.info("update POST request");
        LOGGER.info("id: " + id);
        LOGGER.info("event: " + event);

        Event persistedEvent = eventService.getById(id);
        if (persistedEvent == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        persistedEvent.setName(event.getName());
        persistedEvent.setBasePrice(event.getBasePrice());
        persistedEvent.setTicketPrice(event.getTicketPrice());
        persistedEvent.setRating(event.getRating());

        eventService.save(persistedEvent);

        return new ResponseEntity<>(persistedEvent, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        LOGGER.info("delete DELETE request");
        LOGGER.info("id: " + id);

        Event event = eventService.getById(id);
        if (event == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        eventService.remove(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
